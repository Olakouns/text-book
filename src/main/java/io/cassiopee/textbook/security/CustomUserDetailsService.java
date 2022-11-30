package io.cassiopee.textbook.security;


import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.User;
import io.cassiopee.textbook.repositories.ActorRepository;
import io.cassiopee.textbook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActorRepository actorRepository;

    @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
//                );

        User user =  userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + username)
                );
        Actor actor = actorRepository.findByUser(user);
        return UserPrincipal.create(user, actor);
    }

    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        Actor actor = actorRepository.findByUser(user);

        return UserPrincipal.create(user, actor);
    }

}
