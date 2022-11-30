package io.cassiopee.textbook.services;


import io.cassiopee.textbook.entities.*;
import io.cassiopee.textbook.exceptions.ResourceNotFoundException;
import io.cassiopee.textbook.repositories.*;
import io.cassiopee.textbook.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggerUser {

    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final AcademicYearRepository academicYearRepository;
    private final FieldRepository fieldRepository;
    private final ClassRoomRepository classRoomRepository;
    private final CTextRepository cTextRepository;

    public LoggerUser(UserRepository userRepository, ActorRepository actorRepository, AcademicYearRepository academicYearRepository,
                      FieldRepository fieldRepository, ClassRoomRepository classRoomRepository, CTextRepository cTextRepository) {
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
        this.academicYearRepository = academicYearRepository;
        this.fieldRepository = fieldRepository;
        this.classRoomRepository = classRoomRepository;
        this.cTextRepository = cTextRepository;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getOne(((UserPrincipal) auth.getPrincipal()).getId());
    }

    public Actor getCurrentActor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getOne(((UserPrincipal) auth.getPrincipal()).getId());
        return actorRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Actor", "current user", user.getId()));
    }

    public AcademicYear getCurrentAcademicYear(){
        return  academicYearRepository.findByCurrent(true)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "current AcademicYear", ""));
    }

    public Field getCurrentField(){
        Actor head = getCurrentActor();
        return  fieldRepository.findByHead(head)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "current AcademicYear", ""));
    }

    public ClassRoom getCurrentClassRoom(){
        Actor actor = getCurrentActor();
        return classRoomRepository.findByClassRepresentativeContains(actor).
                orElseThrow(() -> new ResourceNotFoundException("ClassRoom", "current ClassRoom", ""));
    }

    public CText getCurrentCText(){
        ClassRoom classRoom = getCurrentClassRoom();
        return cTextRepository.findByClassRoom(classRoom);
    }
}
