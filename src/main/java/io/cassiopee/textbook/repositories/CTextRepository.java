package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.CText;
import io.cassiopee.textbook.entities.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CTextRepository extends JpaRepository<CText, UUID>, JpaSpecificationExecutor<CText> {
    CText findByClassRoom(ClassRoom classRoom);
}
