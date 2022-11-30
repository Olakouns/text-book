package io.cassiopee.textbook.controllers;


import io.cassiopee.textbook.entities.MediaFile;
import io.cassiopee.textbook.entities.User;
import io.cassiopee.textbook.repositories.MediaFileRepository;
import io.cassiopee.textbook.repositories.UserRepository;
import io.cassiopee.textbook.security.CurrentUser;
import io.cassiopee.textbook.security.UserPrincipal;
import io.cassiopee.textbook.storages.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("/api/medias")
public class MediaController {

	@Autowired
	private StorageService storageService;
	@Autowired
	private MediaFileRepository mediaRepository;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping()
	public ResponseEntity<?> saveMedia(@RequestParam(value = "file", required= true) MultipartFile file,
                                       @RequestParam(value = "type", required= true) String type, @RequestParam("description") String description,
                                       @CurrentUser UserPrincipal principal){

		MediaFile mediaFile = saveMediaFile(file, type, description, principal);

		return new ResponseEntity<Object>(mediaFile, HttpStatus.OK);
	}
	
	@GetMapping()
	public Page<MediaFile> getMediaFiles(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="30") int size,
                                         @RequestParam(defaultValue="") String type){
		if(type.isBlank()) {
			return mediaRepository.findAll(PageRequest.of(page, size));
		}else {
			return mediaRepository.findByType(type, PageRequest.of(page, size));
		}
	}
	
	@DeleteMapping("{id}")
	public boolean deleteMedia(@PathVariable(required=true) long id) {
		return true;
	}

	private MediaFile saveMediaFile(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "type", required = true) String type, @RequestParam("description") String description, @CurrentUser UserPrincipal principal) {
		String filename = UUID.randomUUID().toString() +
				Objects.requireNonNull(file.getOriginalFilename()).toLowerCase()
						.substring(file.getOriginalFilename().toLowerCase().lastIndexOf("."));
		String realType = type;
		if(type.equals("crops")) {
			realType = "images";
		}
		storageService.store(file, realType, filename);
		MediaFile mediaFile = new MediaFile();
		mediaFile.setOriginalName(file.getOriginalFilename());
		mediaFile.setType(type);
		mediaFile.setDescription(description);
		mediaFile.setPath(realType + "/" + filename);
		mediaFile.setUrl("medias/" + realType + "/" + filename);
		mediaFile.setSize(file.getSize());
		if(type.equals("images")||type.equals("profiles")) {
			try {
				storageService.createThumbnail(file, type, filename, 500);
				mediaFile.setPathThumbnail("images/thumbnails/" + filename);
				mediaFile.setThumbnailUrl("medias/images/thumbnails/" + filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		User user = userRepository.getOne(principal.getId());
		mediaFile.setPostedBy(user.getId().toString());
		return mediaRepository.save(mediaFile);
	}

}
