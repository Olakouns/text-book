package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.Role;
import io.cassiopee.textbook.entities.enums.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    Role findFirstByType(TypeRole administrationManager);
}
