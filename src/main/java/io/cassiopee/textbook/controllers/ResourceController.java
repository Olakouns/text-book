package io.cassiopee.textbook.controllers;

import io.cassiopee.textbook.entities.*;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.StatByField;
import io.cassiopee.textbook.payload.StatFirst;
import io.cassiopee.textbook.services.LoggerUser;
import io.cassiopee.textbook.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;
    @Autowired
    private LoggerUser loggerUser;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/permissions")
    public Permission addPermission(@RequestBody Permission permission) {
        return resourceService.addPermission(permission);
    }

    @GetMapping("/permissions")
    public List<Permission> getPermissions() {
        return resourceService.getPermission();
    }

    @PutMapping("/permissions/{permissionId}")
    public Permission updatePermission(@PathVariable long permissionId, @RequestBody Permission permission ) {
        return resourceService.updatePermission(permissionId, permission);
    }


    @PostMapping("/fields")
    public Field addField(@RequestBody Field field) {
        return resourceService.addField(field);
    }

    @GetMapping("/fields/page")
    public Page<Field> getFields(@RequestParam(defaultValue = "") String search,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size) {
        return resourceService.getFields(search, page, size);
    }

    @GetMapping("/fields")
    public List<Field> getFields(@RequestParam(defaultValue = "") String search) {
        return resourceService.getFields(search);
    }

    @GetMapping("/field/current")
    public Field getCurrentField() {
        return resourceService.getCurrentField();
    }

    @GetMapping("/fields/have-not-head")
    public List<Field> getFieldsNotHaveHead() {
        return resourceService.getFieldsNotHaveHead();
    }

    @PostMapping("/fields/{fieldId}")
    public Field updateField(@PathVariable long fieldId, @RequestBody Field field) {
        return resourceService.updateField(fieldId, field);
    }

    @DeleteMapping("/fields/{fieldId}")
    public ApiResponse deleteField(@PathVariable long fieldId) {
        return resourceService.deleteField(fieldId);
    }

    @PostMapping("/fields/{fieldId}/options")
    public Option addOption(@PathVariable long fieldId, @RequestBody Option option) {
        return resourceService.addOption(fieldId, option);
    }

    @GetMapping("/fields/{fieldId}/options")
    public List<Option> getOptions(@PathVariable long fieldId) {
        return resourceService.getOptions(fieldId);
    }

    @PutMapping("/fields/{fieldId}/options/{optionId}")
    public Option updateOption(@PathVariable long fieldId, @PathVariable long optionId, @RequestBody Option option) {
        return resourceService.updateOption(fieldId, optionId, option);
    }

    @DeleteMapping("/fields/{fieldId}/options/{optionId}")
    public ApiResponse deleteOption(@PathVariable long fieldId, @PathVariable long optionId) {
        return resourceService.deleteOption(fieldId, optionId);
    }

    @PostMapping("/subjects")
    public Subject addSubject(@RequestBody Subject subject) {
        return resourceService.addSubject(subject);
    }

    @GetMapping("/subjects/list")
    public List<Subject> getSubjects() {
        return resourceService.getSubjects();
    }

    @GetMapping("/subjects")
    public Page<Subject> getSubjects(@RequestParam(defaultValue = "") String search,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        return resourceService.getSubjects(search, page, size);
    }

    @PutMapping("/subjects/{subjectId}")
    public Subject updateSubject(@PathVariable String subjectId, @RequestBody Subject subject) {
        return resourceService.updateSubject(UUID.fromString(subjectId),subject);
    }

    @DeleteMapping("/subjects/{subjectId}")
    public ApiResponse deleteSubject(@PathVariable String subjectId) {
        return resourceService.deleteSubject(UUID.fromString(subjectId));
    }

    @PostMapping("/academic-year")
    public AcademicYear createAcademicYear(@RequestBody AcademicYear academicYear) {
        return resourceService.createAcademicYear(academicYear);
    }

    @GetMapping("/academic-year/current")
    public AcademicYear getCurrentAcademicYear() {
        return loggerUser.getCurrentAcademicYear();
    }

    @PostMapping("/classRoom")
    public ClassRoom createClassRoom(@RequestParam long fieldId, @RequestParam(defaultValue = "0", required = false) long optionId, @RequestBody ClassRoom classRoom) {
        return resourceService.createClassRoom(fieldId, optionId, classRoom);
    }

    @PutMapping("/classRoom/{classRoomId}/class-representative")
    public ApiResponse addClassRepresentative(@PathVariable long classRoomId, @RequestParam String actorId) {
        return resourceService.addClassRepresentative(UUID.fromString(actorId),classRoomId );
    }

    @GetMapping("field/{fieldId}/classRoom")
    public List<ClassRoom> getClassRoom(@PathVariable long fieldId) {
        return resourceService.getClassRoom(fieldId);
    }

    @PutMapping("/classRoom/{classRoomId}")
    public ClassRoom updateClassRoom(@PathVariable long classRoomId, @RequestParam int fieldId,
                                     @RequestParam(defaultValue = "0", required = false) long optionId, @RequestBody ClassRoom classRoom) {
        return resourceService.updateClassRoom(classRoomId, fieldId, optionId, classRoom);
    }

    @DeleteMapping("/classRoom/{classRoomId}")
    public ApiResponse deleteClassRoom(@PathVariable long classRoomId) {
        return resourceService.deleteClassRoom(classRoomId);
    }

    @GetMapping("/field/classRoom/{classRoomId}")
    public ClassRoom getOneClassRoom(@PathVariable long classRoomId) {
        return resourceService.getOneClassRoom(classRoomId);
    }

    @GetMapping("/stat")
    public StatFirst getStatFirst() {
        return resourceService.getStatFirst();
    }

    @GetMapping("/stat/field")
    public List<StatByField> getAllStatByField() {
        return resourceService.getAllStatByField();
    }

}
