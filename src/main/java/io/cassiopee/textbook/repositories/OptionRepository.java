package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.Field;
import io.cassiopee.textbook.entities.Option;
import io.cassiopee.textbook.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> , JpaSpecificationExecutor<Option> {
    List<Option> findByFieldId(long fieldId);
}
