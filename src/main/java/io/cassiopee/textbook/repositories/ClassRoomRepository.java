package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.AcademicYear;
import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.ClassRoom;
import io.cassiopee.textbook.entities.Field;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long>, JpaSpecificationExecutor<ClassRoom> {
//    List<ClassRoom> findByAcademicYearAndField(AcademicYear academicYear, Field field);

    List<ClassRoom> findByAcademicYearAndFieldOrderByYears(AcademicYear academicYear, Field field);

    List<ClassRoom> findByAcademicYearAndField(AcademicYear academicYear, Field field, Sort years);

    Optional<ClassRoom> findByClassRepresentativeContains(Actor actor);
}
