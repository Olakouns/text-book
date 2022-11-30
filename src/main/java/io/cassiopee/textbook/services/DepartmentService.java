package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.ClassRoom;
import io.cassiopee.textbook.entities.ProCalendar;
import io.cassiopee.textbook.entities.ProgrammeCourse;
import io.cassiopee.textbook.entities.SubjectField;
import io.cassiopee.textbook.entities.enums.Semester;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.StatClassRoom;
import io.cassiopee.textbook.payload.StatProgram;
import io.cassiopee.textbook.payload.TimesTables;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    SubjectField addSubjectField(UUID subjectId, Semester semester, SubjectField subjectField);
    List<SubjectField> getSubjectField();
    ApiResponse deleteSubjectField(UUID subjectFieldId);

    ProgrammeCourse makeProgrammeCourse(UUID profId, UUID subjectFieldId, long classRoomId , ProgrammeCourse  programmeCourse);
    ProgrammeCourse updateProgrammeCourse(UUID programmeCourseId, ProgrammeCourse  programmeCourse);
    List<ProgrammeCourse> getProgrammeCourses(long classRoomId);
    ApiResponse makeStarted(UUID programmeCourseId);
    ApiResponse deleteProgramming(UUID programmeCourseId);
    ProCalendar makeProCalendar(UUID programmeCourseId, ProCalendar  proCalendar);
    List<ProCalendar> getAllProCalendarField(long classRoomId);
    List<ProCalendar> getProCalendarField(UUID programmeCourseId, long classRoomId);
    List<TimesTables> getAllTimesTables();
    StatProgram getTimesDone(UUID programmeCourseId);
    StatClassRoom getStatClassRoom(long classRoomId);
    StatClassRoom getStatClassRoomGeneral();

}
