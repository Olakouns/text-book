package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.ProCalendar;
import io.cassiopee.textbook.entities.ProgrammeCourse;
import io.cassiopee.textbook.entities.Seance;
import io.cassiopee.textbook.entities.SubjectField;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.TeacherOperator;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface HeadClassService {

    Seance addSeance(UUID subjectFieldId, Seance seance);
    Seance updateSeance(UUID seanceId, Seance seance);
    ApiResponse validateSeance(UUID seanceId);
    ApiResponse deleteResponse(UUID seanceId);
    Page<Seance> getSeance(UUID subjectFieldId, String search, int page, int size, long startedDate, long endedDate);
    List<ProgrammeCourse> getSubjectFieldProgram();
    List<ProCalendar> getMyCalendar();

//    TODO : for teacher
    List<TeacherOperator> getTeacherOperator();
}
