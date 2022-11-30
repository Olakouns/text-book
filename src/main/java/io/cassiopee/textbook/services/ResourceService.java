package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.*;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.StatByField;
import io.cassiopee.textbook.payload.StatFirst;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ResourceService {
    Permission addPermission(Permission permission);
    List<Permission> getPermission();
    Permission updatePermission(long permissionId, Permission permission);
    ApiResponse deletePermission(long permissionId);

    Field getCurrentField();
    Field addField(Field field);
    Page<Field> getFields(String search, int page, int size);
    List<Field> getFields(String search);
    List<Field> getFieldsNotHaveHead();
    Field updateField(long fieldId, Field field);
    ApiResponse deleteField(long fieldId);


    Option addOption(long fieldId, Option option);
    List<Option> getOptions(long fieldId);
    Option updateOption(long fieldId, long optionId, Option option);
    ApiResponse deleteOption(long fieldId, long optionId);

    Subject addSubject(Subject subject);
    List<Subject> getSubjects();
    Page<Subject> getSubjects(String search, int page, int size);
    Subject updateSubject(UUID subjectId, Subject subject);
    ApiResponse deleteSubject(UUID subjectId);

    AcademicYear createAcademicYear(AcademicYear academicYear);

    ClassRoom createClassRoom(long fieldId, long optionId, ClassRoom classRoom);
    ApiResponse addClassRepresentative(UUID actorId, long classRoomId);
    List<ClassRoom> getClassRoom(long fieldId);
    ClassRoom getOneClassRoom(long classRoomId);
    ClassRoom updateClassRoom(long classRoomId, long fieldId, long optionId, ClassRoom classRoom);
    ApiResponse deleteClassRoom(long classRoomId);
    StatFirst getStatFirst();
    List<StatByField> getAllStatByField();
}
