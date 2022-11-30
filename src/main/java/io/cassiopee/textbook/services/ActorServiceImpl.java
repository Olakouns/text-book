package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.*;
import io.cassiopee.textbook.entities.enums.TypeRole;
import io.cassiopee.textbook.exceptions.RequestNotAcceptableException;
import io.cassiopee.textbook.exceptions.ResourceNotFoundException;
import io.cassiopee.textbook.payload.AddActor;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.repositories.ActorRepository;
import io.cassiopee.textbook.repositories.FieldRepository;
import io.cassiopee.textbook.repositories.RoleRepository;
import io.cassiopee.textbook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ActorServiceImpl implements ActorService{
    private final LoggerUser loggerUser;
    private final ActorRepository actorRepository;
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final FieldRepository fieldRepository;

    public ActorServiceImpl(LoggerUser loggerUser, ActorRepository actorRepository,
                            UserRepository userRepository, RoleRepository roleRepository, FieldRepository fieldRepository) {
        this.loggerUser = loggerUser;
        this.actorRepository = actorRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Actor getMe() {
        return loggerUser.getCurrentActor();
    }

    @Override
    public Actor getActor(UUID actorId) {
        return actorRepository.findById(actorId).orElseThrow(()-> new ResourceNotFoundException("permission", "id", actorId));
    }


    @Override
    public Page<Actor> getActorsByPhoneNumber(String phoneNumber, int page, int size) {
        return actorRepository.findByPhoneLike(phoneNumber, PageRequest.of(page, size));
    }

    @Override
    public Page<Actor> getActors(String search, int page, int size) {
//        TODO : search here
        return actorRepository.findAll(PageRequest.of(page, size));
    }


    @Override
    public Actor updateMe(Actor actor) {
        Actor actorDb = loggerUser.getCurrentActor();
        actor.setUser(actorDb.getUser());
        return actorRepository.save(actor);
    }

    @Override
    public Actor addAdministration(AddActor addActor) {

        User user = new User();
        user.setEmail(addActor.getEmail());
        user.setRoles(Collections.singletonList(roleRepository.findFirstByType(TypeRole.ADMINISTRATION_MANAGER)));
        user.setUsername(addActor.getEmail());
        user.setName(addActor.getFirstName());
        user.setPassword(passwordEncoder.encode(addActor.getPassword()));
        user.setActive(true);
        user = userRepository.save(user);

        Actor actor = new Actor();
        actor.setUser(user);
        actor.setPhone(addActor.getPhone());
        actor.setType(TypeRole.ADMINISTRATION_MANAGER);
        actor.setFirstName(addActor.getFirstName());
        actor.setLastName(addActor.getLastName());
        actor.setTitle(addActor.getTitle());
        actor.setAddress(addActor.getAddress());
        return actorRepository.save(actor);
    }

    @Override
    public User getUserOfActor(UUID actorId) {
        Actor actorDb = actorRepository.findById(actorId).orElseThrow(()-> new ResourceNotFoundException("actor", "id", actorId));
        //        passwordEncoder.matches()
//        BCryptPasswordEncoder
        return actorDb.getUser();
    }


    @Override
    public Page<Actor> getAllTeacher(String search, int page, int size) {
        Specification<Actor> actorSpecification = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
            .equal(root.get("type"), TypeRole.TEACHER);

        actorSpecification = actorSpecification.or((root, criteriaQuery, criteriaBuilder)  -> criteriaBuilder
                .equal(root.get("type"), TypeRole.HEAD_OF_DEPARTMENT));

        Specification<Actor> filter = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("firstName")), criteriaBuilder.lower(criteriaBuilder.literal("%" + search + "%")));

        filter = filter.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("lastName")), criteriaBuilder.lower(criteriaBuilder.literal("%" + search + "%"))));

        assert filter != null;
        filter = filter.or((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("phone")), criteriaBuilder.lower(criteriaBuilder.literal("%" + search + "%"))));

        assert actorSpecification != null;
        actorSpecification.and(filter);


        return actorRepository.findAll(actorSpecification, PageRequest.of(page, size));
    }

    @Override
    public Actor addTeacher(AddActor addActor) {
        User user = new User();
        user.setEmail(addActor.getEmail());
        user.setRoles(Collections.singletonList(roleRepository.findFirstByType(TypeRole.TEACHER)));
        user.setUsername(addActor.getEmail());
        user.setName(addActor.getFirstName());
        user.setPassword(passwordEncoder.encode(addActor.getPassword()));
        user.setActive(true);
        user = userRepository.save(user);

        Actor actor = new Actor();
        actor.setUser(user);
        actor.setType(TypeRole.TEACHER);
        actor.setMediaFile(addActor.getMedia());
        actor.setEmail(addActor.getEmail());
        actor.setFirstName(addActor.getFirstName());
        actor.setPhone(addActor.getPhone());
        actor.setLastName(addActor.getLastName());
        actor.setTitle(addActor.getTitle());
        actor.setAddress(addActor.getAddress());
        return actorRepository.save(actor);
    }

    @Override
    public Actor updateTeacher(UUID actorId, AddActor actor) {
        Actor actorDb = actorRepository.findById(actorId).orElseThrow(()-> new ResourceNotFoundException("actor", "id", actorId));
        User user = actorDb.getUser();
        user.setEmail(actor.getEmail());
        user.setUsername(actor.getEmail());
        user.setName(actor.getFirstName());
        user = userRepository.save(user);

        actorDb.setFirstName(actor.getFirstName());
        actorDb.setLastName(actor.getLastName());
        actorDb.setMediaFile(actor.getMedia());
        actorDb.setEmail(actor.getEmail());
        actorDb.setTitle(actor.getTitle());
        actorDb.setPhone(actor.getPhone());
        actorDb.setAddress(actor.getAddress());
        actorDb.setUser(user);
        return actorRepository.save(actorDb);
    }

    @Override
    public ApiResponse deleteTeacher(UUID actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(()-> new ResourceNotFoundException("actor", "id", actorId));
        User user = actor.getUser();
        actorRepository.deleteById(actor.getId());
        userRepository.deleteById(user.getId());
        return new ApiResponse(true, "done") ;
    }

    @Override
    public Actor definedActorAsHead(UUID actorId, long fieldId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(()-> new ResourceNotFoundException("actor", "id", actorId));
        Field field = fieldRepository.findById(fieldId).orElseThrow(()-> new ResourceNotFoundException("filed", "id", fieldId));
        if (field.getHead() != null) throw new RequestNotAcceptableException("La filiere a deja un chef depatement");
        User user = actor.getUser();
        user.setRoles(new ArrayList<>());
        user.getRoles().add(roleRepository.findFirstByType(TypeRole.HEAD_OF_DEPARTMENT));
        user = userRepository.save(user);

        actor.setUser(user);
        actor.setType(TypeRole.HEAD_OF_DEPARTMENT);
        actor = actorRepository.save(actor);
        field.setHead(actor);
        fieldRepository.save(field);
        return actor;
    }

    @Override
    public Actor addClassRepresentative(AddActor addActor) {
        User user = new User();
        user.setEmail(addActor.getEmail());
        user.setRoles(Collections.singletonList(roleRepository.findFirstByType(TypeRole.HEAD_OF_CLASS)));
        user.setUsername(addActor.getEmail());
        user.setName(addActor.getFirstName());
        user.setPassword(passwordEncoder.encode(addActor.getPassword()));
        user.setActive(true);
        user = userRepository.save(user);

        Actor actor = new Actor();
        actor.setUser(user);
        actor.setType(TypeRole.HEAD_OF_CLASS);
        actor.setMediaFile(addActor.getMedia());
        actor.setEmail(addActor.getEmail());
        actor.setFirstName(addActor.getFirstName());
        actor.setPhone(addActor.getPhone());
        actor.setLastName(addActor.getLastName());
        actor.setTitle(addActor.getTitle());
        actor.setAddress(addActor.getAddress());
        return actorRepository.save(actor);
    }

}
