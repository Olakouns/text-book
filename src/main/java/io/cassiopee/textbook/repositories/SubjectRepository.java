package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.Role;
import io.cassiopee.textbook.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> , JpaSpecificationExecutor<Subject> {
}
