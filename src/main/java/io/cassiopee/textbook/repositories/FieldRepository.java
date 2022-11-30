package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.Field;
import io.cassiopee.textbook.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field, Long> , JpaSpecificationExecutor<Field> {
    List<Field> findByHeadIsNull();

    Optional<Field> findByHead(Actor head);
}
