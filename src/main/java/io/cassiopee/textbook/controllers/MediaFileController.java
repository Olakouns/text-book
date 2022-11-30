package io.cassiopee.textbook.controllers;


import io.cassiopee.textbook.entities.MediaFile;
import io.cassiopee.textbook.exceptions.ResourceNotFoundException;
import io.cassiopee.textbook.exceptions.StorageFileNotFoundException;
import io.cassiopee.textbook.repositories.MediaFileRepository;
import io.cassiopee.textbook.storages.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Optional;

@Controller
public class MediaFileController {

	@Autowired
	private StorageService storageService;
	@Autowired
	private MediaFileRepository mediaRepository;
	
	@RequestMapping(value = "/medias/{type}/{filename:.+}", method = RequestMethod.GET)
	public @ResponseBody
    ResponseEntity<byte[]> getImageFile(@PathVariable("type") String type, @PathVariable("filename") String filename) {
		Resource image;
		try{
			image = storageService.loadAsResource(type + "/" +filename);
			HttpHeaders header = new HttpHeaders();
			Optional<MediaFile> mediaFile = mediaRepository.findByPath(type + "/" +filename);
			MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
			if(mediaFile.isPresent()) {
				header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+mediaFile.get().getOriginalName());
				header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
				if (mediaFile.get().getOriginalName().endsWith(".pdf")){
					mediaType = MediaType.APPLICATION_PDF;
				}
			}
//	        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
//	        header.add("Pragma", "no-cache");
//	        header.add("Expires", "0");
//			return ResponseEntity.ok()
//					.headers(header)
//					.contentLength(image.contentLength())
//		            .contentType(mediaType)
//		            .body(image);
			return new ResponseEntity<>(image.getInputStream().readAllBytes(), header, HttpStatus.OK);
		}catch(StorageFileNotFoundException | IOException ex){
			throw new ResourceNotFoundException("file", filename, "resource");
		}
		
	}
	
	@RequestMapping(value = "/medias/images/thumbnails/{filename:.+}", method = RequestMethod.GET)
	public @ResponseBody
    ResponseEntity<byte[]> getImageFile(@PathVariable("filename") String filename) {
		Resource image;
		try{
			image = storageService.loadAsResource("images/thumbnails/" +filename);

			HttpHeaders header = new HttpHeaders();
			Optional<MediaFile> mediaFile = mediaRepository.findByPathThumbnail("images/thumbnails/" +filename);
			mediaFile.ifPresent(file -> header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getOriginalName()));
	        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        header.add("Pragma", "no-cache");
	        header.add("Expires", "0");
	        header.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
//			return ResponseEntity.ok()
//					.headers(header)
//					.contentLength(image.contentLength())
//		            .contentType(MediaType.IMAGE_JPEG)
//		            .body(image);
			return new ResponseEntity<>(image.getInputStream().readAllBytes(), header, HttpStatus.OK);
		}catch(StorageFileNotFoundException | IOException ex){
			throw new ResourceNotFoundException("file", filename, "resource");
		}
		
	}

	@RequestMapping(value = "/medias/{type}/{rep}/{filename:.+}", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<byte[]> getImageFile(@PathVariable("type") String type, @PathVariable("rep") String rep, @PathVariable("filename") String filename) {
		Resource image;
		try{
			String path = type + "/" + rep + "/" +filename;
//			System.out.println("path = " + path);
			image = storageService.loadAsResource(path);

			HttpHeaders header = new HttpHeaders();
			Optional<MediaFile> mediaFile = mediaRepository.findByPathThumbnail(path);
			mediaFile.ifPresentOrElse(file -> header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getOriginalName()),
					() -> header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename));
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			header.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity<>(image.getInputStream().readAllBytes(), header, HttpStatus.OK);
//					.headers(header)
//					.contentLength(image.contentLength())
//					.contentType(MediaType.IMAGE_JPEG)
//					.body(image);
		}catch(StorageFileNotFoundException | IOException ex){
			throw new ResourceNotFoundException("file", filename, "resource");
		}

	}
}
