package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.*;
import io.cassiopee.textbook.entities.enums.TypeRole;
import io.cassiopee.textbook.exceptions.RequestNotAcceptableException;
import io.cassiopee.textbook.exceptions.ResourceNotFoundException;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.TeacherOperator;
import io.cassiopee.textbook.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HeadClassServiceImpl implements HeadClassService{
    private final ClassRoomRepository classRoomRepository;
    private final ActorRepository actorRepository;
    private final SeanceRepository seanceRepository;
    private final FieldRepository fieldRepository;
    private final ProgrammeCourseRepository programmeCourseRepository;
    private final SubjectFieldRepository subjectFieldRepository;
    private final LoggerUser loggerUser;
    private final ProCalendarRepository proCalendarRepository;

    public HeadClassServiceImpl(ClassRoomRepository classRoomRepository, ActorRepository actorRepository, SeanceRepository seanceRepository, FieldRepository fieldRepository, ProgrammeCourseRepository programmeCourseRepository, SubjectFieldRepository subjectFieldRepository,
                                LoggerUser loggerUser, ProCalendarRepository proCalendarRepository) {
        this.classRoomRepository = classRoomRepository;
        this.actorRepository = actorRepository;
        this.seanceRepository = seanceRepository;
        this.fieldRepository = fieldRepository;
        this.programmeCourseRepository = programmeCourseRepository;
        this.subjectFieldRepository = subjectFieldRepository;
        this.loggerUser = loggerUser;
        this.proCalendarRepository = proCalendarRepository;
    }

    @Override
    public Seance addSeance(UUID subjectFieldId, Seance seance) {
        SubjectField subjectField = subjectFieldRepository.findById(subjectFieldId).orElseThrow(()-> new ResourceNotFoundException("subjectField", "id", subjectFieldId));
        ClassRoom classRoom = loggerUser.getCurrentClassRoom();
        AcademicYear academicYear = loggerUser.getCurrentAcademicYear();
//        ProgrammeCourse programmeCourse = programmeCourseRepository.findBySubjectFieldAnd
        Specification<ProgrammeCourse> courseSpecification = (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("subjectField").get("id"), subjectFieldId);
        courseSpecification = courseSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("classRoom").get("id"), classRoom.getId()));
        courseSpecification = courseSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("academicYear").get("id"), academicYear.getId()));

        List<ProgrammeCourse> programmeCourses = programmeCourseRepository.findAll(courseSpecification);
        if (programmeCourses.isEmpty()) throw new RequestNotAcceptableException("Non disponible");

        seance.setSupervisor(programmeCourses.get(0).getProfessor());
        seance.setHeadClass(loggerUser.getCurrentActor());
        seance.setSubjectField(subjectField);
        seance.setProgrammeCourse(programmeCourses.get(0));
        seance.setDay(new Date());
        seance.setValidate(false);
        seance.setcText(loggerUser.getCurrentCText());
        return seanceRepository.save(seance);
    }

    @Override
    public Seance updateSeance(UUID seanceId, Seance seance) {
        Seance seanceDb = seanceRepository.findById(seanceId).orElseThrow(()-> new ResourceNotFoundException("seance", "id", seanceId));
        seanceDb.setWorkDone(seance.getWorkDone());
        seanceDb.setStartedDate(seance.getStartedDate());
        seanceDb.setEndedDate(seance.getEndedDate());
        seanceDb.setDuration(seance.getDuration());
        return seanceRepository.save(seanceDb);
    }

    @Override
    public ApiResponse validateSeance(UUID seanceId) {
        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new ResourceNotFoundException("seance", "id", seanceId));
        seance.setValidate(true);
        seanceRepository.save(seance);
        return new ApiResponse(true, "Done");
    }

    @Override
    public ApiResponse deleteResponse(UUID seanceId) {
        Seance seance = seanceRepository.findById(seanceId).orElseThrow(()-> new ResourceNotFoundException("seance", "id", seanceId));
        Actor actor = loggerUser.getCurrentActor();
        if (seance.isValidate()){
            if (actor.getType() == TypeRole.TEACHER){
                seanceRepository.deleteById(seanceId);
            }
        }else {
            seanceRepository.deleteById(seanceId);
        }
        return new ApiResponse(true, "Done");
    }

//    TODO set default value page = 0, size = 10, startedDate = 0, endedDate = 0, search = ""
    @Override
    public Page<Seance> getSeance(UUID programmeCourseId, String search, int page, int size, long startedDate, long endedDate) {

        ProgrammeCourse programmeCourse = programmeCourseRepository.findById(programmeCourseId)
                .orElseThrow(()-> new ResourceNotFoundException("classRoom", "id", programmeCourseId));

        Specification<Seance> seanceSpecification = (root, criteriaQuery, criteriaBuilder) ->{
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("day")));
            return  criteriaBuilder.equal(root.get("programmeCourse").get("id"), programmeCourseId);
        };

        if (!search.isEmpty()){
            seanceSpecification = seanceSpecification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("workDone")), criteriaBuilder.lower(criteriaBuilder.literal("%" + search + "%"))));
        }
        if (startedDate != 0){
            seanceSpecification = seanceSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("startedDate"), new Date(startedDate)));
        }
        if (endedDate != 0){
            seanceSpecification = seanceSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("endedDate"), new Date(endedDate)));
        }
        return seanceRepository.findAll(seanceSpecification, PageRequest.of(page,size));
    }

    @Override
    public List<ProgrammeCourse> getSubjectFieldProgram() {
        Specification<ProgrammeCourse> programmeCourseSpecification = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("classRoom").get("id"), loggerUser.getCurrentClassRoom().getId());

        programmeCourseSpecification  = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("academicYear").get("id"), loggerUser.getCurrentAcademicYear().getId()));

        assert programmeCourseSpecification != null;
        programmeCourseSpecification  = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("started"), true));

//        List<ProgrammeCourse> programmeCourses = programmeCourseRepository.findAll(programmeCourseSpecification);
        return programmeCourseRepository.findAll(programmeCourseSpecification);
    }

    @Override
    public List<ProCalendar> getMyCalendar() {
        Specification<ProCalendar> proCalendarSpecification = (root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("academicYear"), loggerUser.getCurrentAcademicYear());

        proCalendarSpecification = proCalendarSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("programmeCourse").get("classRoom").get("id"), loggerUser.getCurrentClassRoom().getId()));

        return proCalendarRepository.findAll(proCalendarSpecification);
    }

    @Override
    public List<TeacherOperator> getTeacherOperator() {
        List<TeacherOperator> teacherOperators = new ArrayList<>();
        Specification<ProgrammeCourse> programmeCourseSpecification = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("professor").get("id"), loggerUser.getCurrentActor().getId());

        programmeCourseSpecification  = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("academicYear").get("id"), loggerUser.getCurrentAcademicYear().getId()));

        assert programmeCourseSpecification != null;
        programmeCourseSpecification  = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("started"), true));

        List<ProgrammeCourse> programmeCourses = programmeCourseRepository.findAll(programmeCourseSpecification);
        programmeCourses.forEach(programmeCourse -> {
            teacherOperators.add(new TeacherOperator(programmeCourse.getId(), programmeCourse.getSubjectField(),
                    programmeCourse.getStartedDate(), programmeCourse.getEndedDate(), programmeCourse.getField(),
                    programmeCourse.getClassRoom()));
        });
        return teacherOperators;
    }
}
