package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.*;
import io.cassiopee.textbook.entities.enums.TypeRole;
import io.cassiopee.textbook.entities.enums.Years;
import io.cassiopee.textbook.exceptions.RequestNotAcceptableException;
import io.cassiopee.textbook.exceptions.ResourceNotFoundException;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.StatByField;
import io.cassiopee.textbook.payload.StatFirst;
import io.cassiopee.textbook.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService{

    private final PermissionRepository permissionRepository;
    private final FieldRepository fieldRepository;
    private final OptionRepository optionRepository;
    private final SubjectRepository subjectRepository;
    private final LoggerUser loggerUser;
    private final AcademicYearRepository academicYearRepository;
    private final ClassRoomRepository classRoomRepository;
    private final CTextRepository cTextRepository;
    private final ActorRepository actorRepository;


    public ResourceServiceImpl(PermissionRepository permissionRepository, FieldRepository fieldRepository,
                               OptionRepository optionRepository, SubjectRepository subjectRepository, LoggerUser loggerUser,
                               AcademicYearRepository academicYearRepository,ClassRoomRepository classRoomRepository,
                               CTextRepository cTextRepository, ActorRepository actorRepository) {
        this.permissionRepository = permissionRepository;
        this.fieldRepository = fieldRepository;
        this.optionRepository = optionRepository;
        this.subjectRepository = subjectRepository;
        this.loggerUser = loggerUser;
        this.academicYearRepository = academicYearRepository;
        this.classRoomRepository = classRoomRepository;
        this.cTextRepository = cTextRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public Permission addPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public List<Permission> getPermission() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission updatePermission(long permissionId, Permission permission) {
        return null;
    }

    @Override
    public ApiResponse deletePermission(long permissionId) {
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(()-> new ResourceNotFoundException("permission", "id", permissionId));
        return new ApiResponse(true, "done") ;
    }

    @Override
    public Field getCurrentField() {
        return loggerUser.getCurrentField();
    }


    @Override
    public Field addField(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    public Page<Field> getFields(String search, int page, int size) {
        Specification<Field> fieldSpecification = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                like(criteriaBuilder.lower(root.get("name")), criteriaBuilder.lower(criteriaBuilder.literal("%" + search + "%")));
        return fieldRepository.findAll(fieldSpecification, PageRequest.of(page, size));
    }

    @Override
    public List<Field> getFields(String search) {
        Specification<Field> fieldSpecification = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                like(criteriaBuilder.lower(root.get("name")), criteriaBuilder.lower(criteriaBuilder.literal("%" + search + "%")));
        return fieldRepository.findAll(fieldSpecification);
    }

    @Override
    public List<Field> getFieldsNotHaveHead() {
        return fieldRepository.findByHeadIsNull();
    }

    @Override
    public Field updateField(long fieldId, Field field) {
        Field fieldDb = fieldRepository.findById(fieldId).orElseThrow(()-> new ResourceNotFoundException("filed", "id", fieldId));
        fieldDb.setName(field.getName());
        fieldDb.setCode(field.getCode());
        return fieldRepository.save(fieldDb);
    }

    @Override
    public ApiResponse deleteField(long fieldId) {
        Field field = fieldRepository.findById(fieldId).orElseThrow(()-> new ResourceNotFoundException("filed", "id", fieldId));
        field.getOptions().forEach(option -> {
            optionRepository.deleteById(option.getId());
        });
        fieldRepository.deleteById(fieldId);
        return new ApiResponse(true, "done");
    }

    @Override
    public Option addOption(long fieldId, Option option) {
        Field field = fieldRepository.findById(fieldId).orElseThrow(()-> new ResourceNotFoundException("filed", "id", fieldId));
        option.setField(field);
        option = optionRepository.save(option);
        if (field.getOptions() == null){
            field.setOptions(Collections.singletonList(option));
        }else {
            field.getOptions().add(option);
        }
        fieldRepository.save(field);
        return option;
    }

    @Override
    public List<Option> getOptions(long fieldId) {
        if (!fieldRepository.existsById(fieldId)) throw new ResourceNotFoundException("field", "id", fieldId);
        return optionRepository.findByFieldId(fieldId);
    }

    @Override
    public Option updateOption(long fieldId, long optionId, Option option) {
        if (!fieldRepository.existsById(fieldId)) throw new ResourceNotFoundException("field", "id", fieldId);
        Option optionDb = optionRepository.findById(optionId).orElseThrow(()-> new ResourceNotFoundException("Option", "id", optionId));
        optionDb.setName(option.getName());
        optionDb.setCode(option.getCode());
        return optionRepository.save(optionDb);
    }

    @Override
    public ApiResponse deleteOption(long fieldId, long optionId) {
        if (!optionRepository.existsById(optionId)) throw new ResourceNotFoundException("Option", "id", optionId);
        optionRepository.deleteById(optionId);
        return new ApiResponse(true, "done");
    }

    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Page<Subject> getSubjects(String search, int page, int size) {
        Specification<Subject> subjectSpecification = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.
                like(criteriaBuilder.lower(root.get("name")), criteriaBuilder.lower(criteriaBuilder.literal("%" + search + "%")));
        return subjectRepository.findAll(subjectSpecification, PageRequest.of(page, size));
    }

    @Override
    public Subject updateSubject(UUID subjectId, Subject subject) {
        Subject subjectDb = subjectRepository.findById(subjectId).orElseThrow(()-> new ResourceNotFoundException("Subject", "id", subjectId));
        subjectDb.setName(subject.getName());
        subjectDb.setCode(subject.getCode());
        return subjectRepository.save(subjectDb);
    }

    @Override
    public ApiResponse deleteSubject(UUID subjectId) {
        if (!subjectRepository.existsById(subjectId)) throw new ResourceNotFoundException("Option", "id", subjectId);
        subjectRepository.deleteById(subjectId);
        return new ApiResponse(true, "done");
    }

    @Override
    public AcademicYear createAcademicYear(AcademicYear academicYear) {
        academicYear.setCurrent(true);
        academicYear = academicYearRepository.save(academicYear);

        List<Field> fields = fieldRepository.findAll();
        AcademicYear finalAcademicYear = academicYear;
        fields.forEach(field -> {
            for (int i = 0; i <3 ; i++) {
                ClassRoom classRoom = new ClassRoom();
                classRoom.setField(field);
                classRoom.setYears(Years.values()[i]);
                classRoom.setAcademicYear(finalAcademicYear);
                classRoom.setName(field.getCode()+ "-"+(i+1));
                classRoom.setClassRepresentative(new ArrayList<>());
                classRoom = classRoomRepository.save(classRoom);
                CText cText = new CText(classRoom, finalAcademicYear);
                cTextRepository.save(cText);
            }
        });
        return academicYear;
    }

    @Override
    public ClassRoom createClassRoom(long fieldId, long optionId, ClassRoom classRoom) {
        Field field = fieldRepository.findById(fieldId).orElseThrow(()-> new ResourceNotFoundException("filed", "id", fieldId));
        if (optionId != 0) {
            Option option = optionRepository.findById(optionId).orElseThrow(()-> new ResourceNotFoundException("Option", "id", optionId));
            classRoom.setOption(option);
        }
        classRoom.setField(field);
        classRoom.setAcademicYear(loggerUser.getCurrentAcademicYear());
        classRoom.setClassRepresentative(new ArrayList<>());
        classRoom = classRoomRepository.save(classRoom);
        CText cText = new CText(classRoom, loggerUser.getCurrentAcademicYear());
        cTextRepository.save(cText);

        return classRoom;
    }

    @Override
    public ApiResponse addClassRepresentative(UUID actorId, long classRoomId) {
        ClassRoom classRoom = classRoomRepository.findById(classRoomId).orElseThrow(()-> new ResourceNotFoundException("classRoom", "id", classRoomId));
        Actor actorDb = actorRepository.findById(actorId).orElseThrow(()-> new ResourceNotFoundException("actor", "id", actorId));
        classRoom.getClassRepresentative().add(actorDb);
        classRoomRepository.save(classRoom);

        return new ApiResponse(true, "done");
    }

    @Override
    public List<ClassRoom> getClassRoom(long fieldId) {
        AcademicYear academicYear  = loggerUser.getCurrentAcademicYear();
        Field field = fieldRepository.findById(fieldId).orElseThrow(()-> new ResourceNotFoundException("filed", "id", fieldId));

        return classRoomRepository.findByAcademicYearAndField(academicYear, field, Sort.by("years").descending());
    }

    @Override
    public ClassRoom getOneClassRoom(long classRoomId) {
        return classRoomRepository.findById(classRoomId).orElseThrow(()-> new ResourceNotFoundException("classRoom", "id", classRoomId));

    }

    @Override
    public ClassRoom updateClassRoom(long classRoomId, long fieldId, long optionId, ClassRoom classRoom) {
        ClassRoom classRoomDb = classRoomRepository.findById(classRoomId)
                .orElseThrow(()-> new ResourceNotFoundException("classRoom", "id", classRoomId));
        Field field = fieldRepository.findById(fieldId).orElseThrow(()-> new ResourceNotFoundException("filed", "id", fieldId));
        if (optionId != 0) {
            Option option = optionRepository.findById(optionId).orElseThrow(()-> new ResourceNotFoundException("Option", "id", optionId));
            classRoomDb.setOption(option);
        }
        classRoomDb.setField(field);
        classRoomDb.setName(classRoom.getName());
        return classRoomRepository.save(classRoomDb);
    }

    @Override
    public ApiResponse deleteClassRoom(long classRoomId) {
        ClassRoom classRoom = classRoomRepository.findById(classRoomId).orElseThrow(()-> new ResourceNotFoundException("classRoom", "id", classRoomId));
        CText cText = cTextRepository.findByClassRoom(classRoom);
        cTextRepository.deleteById(cText.getId());
        classRoomRepository.deleteById(classRoomId);
        return new ApiResponse(true, "Done");
    }

    @Override
    public StatFirst getStatFirst() {
        Specification<Actor> actorSpecification = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("type"), TypeRole.TEACHER);
        actorSpecification = actorSpecification.or((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("type"), TypeRole.HEAD_OF_DEPARTMENT));

        long nbrTeacher = actorRepository.count(actorSpecification);
        long nbrField = fieldRepository.count();
        long nbrSubject = subjectRepository.count();
        return new StatFirst(nbrTeacher, nbrField, nbrSubject);
    }

    @Override
    public List<StatByField> getAllStatByField() {
        List<StatByField> statByFields = new ArrayList<>();
        List<Field> fields = fieldRepository.findAll();
        fields.forEach(field -> {
             Specification<Option> optionSpecification = (root, criteriaQuery, criteriaBuilder) ->
                     criteriaBuilder.equal(root.get("field").get("id"), field.getId());
             
             long nbrOption = optionRepository.count(optionSpecification);
             Actor head = field.getHead();
             Field insertField = field;
             Specification<ClassRoom> classRoomSpecification = (root, criteriaQuery, criteriaBuilder) ->
                     criteriaBuilder.equal(root.get("field").get("id"), field.getId());
             classRoomSpecification = classRoomSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                     criteriaBuilder.equal(root.get("academicYear").get("id"), loggerUser.getCurrentAcademicYear().getId()));
             
             long nbrClass = classRoomRepository.count(classRoomSpecification);

             Specification<ClassRoom> classRoomSpecificationFirst = (root, criteriaQuery, criteriaBuilder) ->
                     criteriaBuilder.equal(root.get("years"), Years.THIRST_YEAR);

            classRoomSpecificationFirst = classRoomSpecificationFirst.and(classRoomSpecification);

             long nbrClassRoomFirst = classRoomRepository.count(classRoomSpecificationFirst);

             Specification<ClassRoom> classRoomSpecificationSecond = (root, criteriaQuery, criteriaBuilder) ->
                     criteriaBuilder.equal(root.get("years"), Years.SECOND_YEAR);
            classRoomSpecificationSecond = classRoomSpecificationSecond.and(classRoomSpecification);

            long nbrClassRoomSecond = classRoomRepository.count(classRoomSpecificationSecond);

            Specification<ClassRoom> classRoomSpecificationThird = (root, criteriaQuery, criteriaBuilder) ->
                   criteriaBuilder.equal(root.get("years"), Years.THIRD_YEAR);
            classRoomSpecificationThird = classRoomSpecificationThird.and(classRoomSpecification);

            long nbrClassRooThird = classRoomRepository.count(classRoomSpecificationThird);
            statByFields.add(new StatByField(head, insertField, nbrOption, nbrClass, nbrClassRoomFirst, nbrClassRoomSecond, nbrClassRooThird)) ;

        });
        return statByFields;
    }
}
