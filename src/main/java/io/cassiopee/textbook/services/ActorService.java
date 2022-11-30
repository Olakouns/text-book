package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.Permission;
import io.cassiopee.textbook.entities.User;
import io.cassiopee.textbook.payload.AddActor;
import io.cassiopee.textbook.payload.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ActorService {
    Actor getMe();
    Actor getActor(UUID actorId);
    Page<Actor> getActorsByPhoneNumber(String phoneNumber, int page, int size);
    Page<Actor> getActors(String search, int page,  int size);
    Actor updateMe(Actor actor);
    Actor addAdministration(AddActor addActor);

    User getUserOfActor(UUID actorId);
//    Actor updateActor(UUID actorId, Actor actor);

    Page<Actor> getAllTeacher(String search, int page,  int size);
    Actor addTeacher(AddActor addActor);
    Actor updateTeacher(UUID actorId, AddActor actor);
    ApiResponse deleteTeacher(UUID actorId);
    Actor definedActorAsHead(UUID actorId, long fieldId);

    Actor addClassRepresentative(AddActor addActor);
}
