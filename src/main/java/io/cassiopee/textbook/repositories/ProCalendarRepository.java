package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.ProCalendar;
import io.cassiopee.textbook.entities.ProgrammeCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ProCalendarRepository extends JpaRepository<ProCalendar, UUID>, JpaSpecificationExecutor<ProCalendar> {
    List<ProCalendar> findByProgrammeCourse(ProgrammeCourse programmeCourse);
}
