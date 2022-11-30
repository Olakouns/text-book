package io.cassiopee.textbook.repositories;


import io.cassiopee.textbook.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SeanceRepository extends JpaRepository<Seance, UUID>, JpaSpecificationExecutor<Seance> {
}
