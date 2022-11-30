package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, UUID> {
    Optional<AcademicYear> findByCurrent(boolean b);
}
