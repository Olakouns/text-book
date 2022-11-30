package io.cassiopee.textbook.repositories;


import io.cassiopee.textbook.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
