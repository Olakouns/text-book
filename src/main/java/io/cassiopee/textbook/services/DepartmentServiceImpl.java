package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.*;
import io.cassiopee.textbook.entities.enums.Semester;
import io.cassiopee.textbook.entities.enums.TypeRole;
import io.cassiopee.textbook.exceptions.RequestNotAcceptableException;
import io.cassiopee.textbook.exceptions.ResourceNotFoundException;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.StatClassRoom;
import io.cassiopee.textbook.payload.StatProgram;
import io.cassiopee.textbook.payload.TimesTables;
import io.cassiopee.textbook.repositories.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private final ProgrammeCourseRepository programmeCourseRepository;
    private final ProCalendarRepository proCalendarRepository;
    private final SubjectFieldRepository subjectFieldRepository;
    private final LoggerUser loggerUser;
    private final ActorRepository actorRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRoomRepository classRoomRepository;
    private final SeanceRepository seanceRepository;

    public DepartmentServiceImpl(ProgrammeCourseRepository programmeCourseRepository, ProCalendarRepository proCalendarRepository, SubjectFieldRepository subjectFieldRepository, LoggerUser loggerUser, ActorRepository actorRepository,
                                 SubjectRepository subjectRepository, ClassRoomRepository classRoomRepository, SeanceRepository seanceRepository) {
        this.programmeCourseRepository = programmeCourseRepository;
        this.proCalendarRepository = proCalendarRepository;
        this.subjectFieldRepository = subjectFieldRepository;
        this.loggerUser = loggerUser;
        this.actorRepository = actorRepository;
        this.subjectRepository = subjectRepository;
        this.classRoomRepository = classRoomRepository;
        this.seanceRepository = seanceRepository;
    }

    @Override
    public SubjectField addSubjectField(UUID subjectId, Semester semester, SubjectField subjectField) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()-> new ResourceNotFoundException("Subject", "id", subjectId));
        subjectField.setSubject(subject);
        subjectField.setAcademicYear(loggerUser.getCurrentAcademicYear());
        subjectField.setField(loggerUser.getCurrentField());
        subjectField.setSemester(semester);
        return subjectFieldRepository.save(subjectField);
    }

    @Override
    public List<SubjectField> getSubjectField() {
        Specification<SubjectField> fieldSpecification = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("field").get("id"), loggerUser.getCurrentField().getId());

        fieldSpecification = fieldSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("academicYear"), loggerUser.getCurrentAcademicYear()));

        return subjectFieldRepository.findAll(fieldSpecification);
    }



    @Override
    public ApiResponse deleteSubjectField(UUID subjectFieldId) {
        SubjectField subjectField = subjectFieldRepository.findById(subjectFieldId).orElseThrow(()-> new ResourceNotFoundException("subjectField", "id", subjectFieldId));
        if(programmeCourseRepository.existsBySubjectField(subjectField))
            throw new RequestNotAcceptableException("cette matiere est associe a un programme");
        subjectFieldRepository.deleteById(subjectFieldId);

        return new ApiResponse(true, "done");
    }

    @Override
    public ProgrammeCourse makeProgrammeCourse(UUID profId, UUID subjectFieldId, long classRoomId, ProgrammeCourse programmeCourse) {
        Actor actor = actorRepository.findById(profId).orElseThrow(()-> new ResourceNotFoundException("actor", "id", profId));
        SubjectField subjectField = subjectFieldRepository.findById(subjectFieldId).orElseThrow(()-> new ResourceNotFoundException("subjectField", "id", subjectFieldId));
        ClassRoom classRoom = classRoomRepository.findById(classRoomId).orElseThrow(()-> new ResourceNotFoundException("classRoom", "id", classRoomId));
        programmeCourse.setProfessor(actor);
        programmeCourse.setSubjectField(subjectField);
        programmeCourse.setAcademicYear(loggerUser.getCurrentAcademicYear());
        programmeCourse.setField(loggerUser.getCurrentField());
        programmeCourse.setClassRoom(classRoom);
        programmeCourse.setStatus(false);
        return programmeCourseRepository.save(programmeCourse);
    }

    @Override
    public ProgrammeCourse updateProgrammeCourse(UUID programmeCourseId, ProgrammeCourse programmeCourse) {
        ProgrammeCourse programmeCourseDb = programmeCourseRepository.findById(programmeCourseId).orElseThrow(()-> new ResourceNotFoundException("programmeCourse", "id", programmeCourseId));
        programmeCourseDb.setProgrammeDuration(programmeCourse.getProgrammeDuration());
        if (!programmeCourseDb.isStarted()){
            programmeCourseDb.setStartedDate(programmeCourse.getStartedDate());
        }
        programmeCourseDb.setEndedDate(programmeCourse.getEndedDate());

        return programmeCourseRepository.save(programmeCourseDb);
    }

    @Override
    public List<ProgrammeCourse> getProgrammeCourses(long classRoomId) {
        Specification<ProgrammeCourse> programmeCourseSpecification = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("classRoom").get("id"), classRoomId);

        programmeCourseSpecification = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("academicYear"), loggerUser.getCurrentAcademicYear()));

        programmeCourseSpecification = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("field").get("id"), loggerUser.getCurrentField().getId()));

        return programmeCourseRepository.findAll(programmeCourseSpecification);
    }

    @Override
    public ApiResponse makeStarted(UUID programmeCourseId) {
        ProgrammeCourse programmeCourse = programmeCourseRepository.findById(programmeCourseId).orElseThrow(()-> new ResourceNotFoundException("programmeCourse", "id", programmeCourseId));
        programmeCourse.setStarted(true);
        programmeCourseRepository.save(programmeCourse);
        return new ApiResponse(true, "Done");
    }

    @Override
    public ApiResponse deleteProgramming(UUID programmeCourseId) {
        ProgrammeCourse programmeCourse = programmeCourseRepository.findById(programmeCourseId).orElseThrow(()-> new ResourceNotFoundException("programmeCourse", "id", programmeCourseId));
        List<ProCalendar> proCalendars = proCalendarRepository.findByProgrammeCourse(programmeCourse);
        proCalendars.forEach(proCalendar -> {
            proCalendarRepository.deleteById(proCalendar.getId());
        });
        programmeCourseRepository.deleteById(programmeCourseId);
        return new ApiResponse(true, "Done");
    }

    @Override
    public ProCalendar makeProCalendar(UUID programmeCourseId, ProCalendar proCalendar) {
        ProgrammeCourse programmeCourse = programmeCourseRepository.findById(programmeCourseId).orElseThrow(()-> new ResourceNotFoundException("programmeCourse", "id", programmeCourseId));
        proCalendar.setProgrammeCourse(programmeCourse);
        proCalendar.setSubjectField(programmeCourse.getSubjectField());
        proCalendar.setAcademicYear(programmeCourse.getAcademicYear());
        return proCalendarRepository.save(proCalendar);
    }

    @Override
    public List<ProCalendar> getAllProCalendarField(long classRoomId) {
        Field field = loggerUser.getCurrentField();
        Specification<ProCalendar> proCalendarSpecification = (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("startedHours")));
            return criteriaBuilder.equal(root.get("programmeCourse").get("field").get("id"), field.getId());
        };


        proCalendarSpecification = proCalendarSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("academicYear"), loggerUser.getCurrentAcademicYear()));

        assert proCalendarSpecification != null;
        proCalendarSpecification = proCalendarSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("programmeCourse").get("classRoom").get("id"), classRoomId));

        return proCalendarRepository.findAll(proCalendarSpecification);
    }

    @Override
    public List<ProCalendar> getProCalendarField(UUID programmeCourseId, long classRoomId) {
        Field field = loggerUser.getCurrentField();
        Specification<ProCalendar> proCalendarSpecification = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("programmeCourse").get("id"), programmeCourseId);

        proCalendarSpecification = proCalendarSpecification.and((root, criteriaQuery, criteriaBuilder)  ->
                criteriaBuilder.equal(root.get("programmeCourse").get("field").get("id"), field.getId()));

        proCalendarSpecification = proCalendarSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("academicYear"), loggerUser.getCurrentAcademicYear()));

        assert proCalendarSpecification != null;
        proCalendarSpecification = proCalendarSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("programmeCourse").get("classRoom").get("id"), classRoomId));

        return proCalendarRepository.findAll(proCalendarSpecification);
    }

    @Override
    public List<TimesTables> getAllTimesTables() {
        List<TimesTables> timesTables = new ArrayList<>();
        Field field = loggerUser.getCurrentField();
        AcademicYear academicYear  = loggerUser.getCurrentAcademicYear();
        List<ClassRoom> classRooms = classRoomRepository.findByAcademicYearAndField(academicYear, field, Sort.by("years").descending());
        classRooms.forEach(classRoom -> {
            List<ProCalendar> proCalendars = getAllProCalendarField(classRoom.getId());
            timesTables.add(new TimesTables(classRoom,proCalendars));
        });

        return timesTables;
    }

    @Override
    public StatProgram getTimesDone(UUID programmeCourseId) {
        Specification<Seance> seanceSpecification = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("programmeCourse").get("id"), programmeCourseId);
        long value;
        List<Seance> seances = seanceRepository.findAll(seanceSpecification);
        StatProgram statProgram = new StatProgram();
        value = seances.stream().mapToLong(seance -> (long) seance.getDuration()).sum();
        statProgram.setNbrTimeDone(value);
        statProgram.setNbrSeance(seanceRepository.count(seanceSpecification));
        return statProgram;
    }

    @Override
    public StatClassRoom getStatClassRoom(long classRoomId) {
        Specification<ProgrammeCourse> programmeCourseSpecification = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("classRoom").get("id"), classRoomId);

        programmeCourseSpecification = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("academicYear"), loggerUser.getCurrentAcademicYear()));

        programmeCourseSpecification = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("field").get("id"), loggerUser.getCurrentField().getId()));

        long nbrTotalProgramming = programmeCourseRepository.count(programmeCourseSpecification);

        Specification<ProgrammeCourse> courseSpecificationLoad = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("started"), true);
        courseSpecificationLoad = courseSpecificationLoad.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), false));

        courseSpecificationLoad = courseSpecificationLoad.and(programmeCourseSpecification);

        long nbrTotalProgramLoad = programmeCourseRepository.count(courseSpecificationLoad);

        Specification<ProgrammeCourse> courseSpecificationEnd = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), true);

        long nbrTotalProEnd = programmeCourseRepository.count(courseSpecificationEnd);

        return new StatClassRoom(nbrTotalProgramming, nbrTotalProgramLoad, nbrTotalProEnd);
    }

    @Override
    public StatClassRoom getStatClassRoomGeneral() {
        Specification<ProgrammeCourse> programmeCourseSpecification = (root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("academicYear"), loggerUser.getCurrentAcademicYear());


        programmeCourseSpecification = programmeCourseSpecification.and((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("field").get("id"), loggerUser.getCurrentField().getId()));

        long nbrTotalProgramming = programmeCourseRepository.count(programmeCourseSpecification);

        Specification<ProgrammeCourse> courseSpecificationLoad = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("started"), true);
        courseSpecificationLoad = courseSpecificationLoad.and((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), false));

        courseSpecificationLoad = courseSpecificationLoad.and(programmeCourseSpecification);

        long nbrTotalProgramLoad = programmeCourseRepository.count(courseSpecificationLoad);

        Specification<ProgrammeCourse> courseSpecificationEnd = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), true);

        long nbrTotalProEnd = programmeCourseRepository.count(courseSpecificationEnd);

        return new StatClassRoom(nbrTotalProgramming, nbrTotalProgramLoad, nbrTotalProEnd);
    }
}
