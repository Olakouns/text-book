package io.cassiopee.textbook.controllers;

import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.Field;
import io.cassiopee.textbook.entities.Permission;
import io.cassiopee.textbook.entities.User;
import io.cassiopee.textbook.payload.AddActor;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.services.ActorService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/me")
    public Actor getMe() {
        return actorService.getMe();
    }

    @GetMapping("/{actorId}")
    public Actor getActor(@PathVariable String actorId) {
        return actorService.getActor(UUID.fromString(actorId));
    }


    @GetMapping("/all")
    public Page<Actor> getActors(@RequestParam(defaultValue = "") String phone,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size) {
        return actorService.getActors(phone , page, size);
    }

    @PutMapping("/me")
    public Actor updateMe(@RequestBody  Actor actor){
        return  actorService.updateMe(actor);
    }

    @PostMapping("/administration")
    public Actor addAdministration(@RequestBody  AddActor actor){
        return  actorService.addAdministration(actor);
    }

    @PostMapping("/teachers")
    public Actor addTeacher(@RequestBody  AddActor actor){
        return  actorService.addTeacher(actor);
    }

    @GetMapping("/{actorId}/user")
    public User getUserOfActor(@PathVariable  String actorId){
        return  actorService.getUserOfActor(UUID.fromString(actorId));
    }

    @GetMapping("/teachers")
    public Page<Actor> getAllTeacher(@RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size){
        return  actorService.getAllTeacher(search, page, size);
    }

    @PutMapping("/teachers/{actorId}")
    public Actor updateTeacher(@PathVariable String actorId, @RequestBody AddActor actor){
        return  actorService.updateTeacher(UUID.fromString(actorId), actor);
    }

    @PutMapping("/teachers/{actorId}/head")
    public Actor definedActorAsHead(@PathVariable String actorId, @RequestParam int fieldId){
        return  actorService.definedActorAsHead(UUID.fromString(actorId), fieldId);
    }

    @DeleteMapping("/teachers/{actorId}")
    public ApiResponse deleteTeacher(@PathVariable String actorId){
        return  actorService.deleteTeacher(UUID.fromString(actorId));
    }


    @PostMapping("/class-representative")
    public Actor addClassRepresentative(@RequestBody  AddActor actor){
        return  actorService.addClassRepresentative(actor);
    }


}
