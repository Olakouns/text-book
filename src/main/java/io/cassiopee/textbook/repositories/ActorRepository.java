package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ActorRepository extends JpaRepository<Actor, UUID>, JpaSpecificationExecutor<Actor> {
    Optional<Actor> findByUserId(UUID toString);

    Page<Actor> findByPhoneLike(String phoneNumber, Pageable of);


    Actor findByUser(User user);
}
