package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.ProgrammeCourse;
import io.cassiopee.textbook.entities.Role;
import io.cassiopee.textbook.entities.SubjectField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProgrammeCourseRepository extends JpaRepository<ProgrammeCourse, UUID>, JpaSpecificationExecutor<ProgrammeCourse> {
    boolean existsBySubjectField(SubjectField subjectField);
}
