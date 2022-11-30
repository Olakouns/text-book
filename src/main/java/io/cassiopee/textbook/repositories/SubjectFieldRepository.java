package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.ProgrammeCourse;
import io.cassiopee.textbook.entities.SubjectField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SubjectFieldRepository extends JpaRepository<SubjectField, UUID>, JpaSpecificationExecutor<SubjectField> {
}
