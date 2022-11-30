package io.cassiopee.textbook.repositories;


import io.cassiopee.textbook.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
