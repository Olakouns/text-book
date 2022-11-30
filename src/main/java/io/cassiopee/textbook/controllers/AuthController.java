package io.cassiopee.textbook.controllers;


import io.cassiopee.textbook.entities.ActivatorToken;
import io.cassiopee.textbook.entities.User;
import io.cassiopee.textbook.entities.enums.StatusUser;
import io.cassiopee.textbook.entities.enums.TypeToken;
import io.cassiopee.textbook.exceptions.RequestNotAcceptableException;
import io.cassiopee.textbook.exceptions.ResourceNotFoundException;
import io.cassiopee.textbook.payload.ApiResponse;
import io.cassiopee.textbook.payload.ChangePasswordTemplate;
import io.cassiopee.textbook.payload.JwtAuthenticationResponse;
import io.cassiopee.textbook.payload.LoginRequest;
import io.cassiopee.textbook.repositories.RoleRepository;
import io.cassiopee.textbook.repositories.UserRepository;
import io.cassiopee.textbook.security.CurrentUser;
import io.cassiopee.textbook.security.JwtTokenProvider;
import io.cassiopee.textbook.security.UserPrincipal;
import io.cassiopee.textbook.services.ActivatorTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ActivatorTokenService activatorTokenService;



    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated @Valid LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        UserPrincipal userP = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userP.getId()).orElseThrow();
        if (user.isActive()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            JwtAuthenticationResponse token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(token);
        } else if (user.getStatus() == StatusUser.ACTIVATION_PENDING){
            return ResponseEntity.ok(new ApiResponse(true, "Le compte est en attente de confirmation."));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, loginRequest.getUsernameOrEmail()));
        }

    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        // Creating user's account
//        User user = new User();
//        user.setName(signUpRequest.getName());
//        user.setEmail(signUpRequest.getEmail());
//        user.setUsername(signUpRequest.getUsername());
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//                .orElseThrow(() -> new AppException("User Role not set."));
//
//        user.setRoles(Collections.singleton(userRole));
//
//        User result = userRepository.save(user);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/api/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();
//
//        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
//    }


//    @PostMapping("mail/password-forgot")
//    @Transactional
//    public ResponseEntity<?> passwordForgot(@RequestBody @Validated @Valid MailTemplate mailTemplate) {
//        ApiResponse response;
//        User user = userRepository.findByEmail(mailTemplate.getEmail())
//                .orElseThrow(()-> new ResourceNotFoundException("User", "Email", mailTemplate.getEmail()));
//        ActivatorToken token = activatorTokenService.generateUUIDToken(user.getId().toString(),
//                TypeToken.PASSWORD_FORGET);
//
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setFrom("no-reply@marches-publics.bj");
////        message.setTo(user.getEmail());
////        message.setSubject("Mot de passe oublié? ");
////        message.setText("Cliquez sur le lien suivant pour choisir un nouveau mot de passe : http://portail.artcreativity.io/auth/password-forgot?token=" + token.getToken());
////        emailSender.send(message);
//
//        try {
//            emailService.sendPasswordForgotEmail(user.getEmail(), token.getToken());
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        response = new ApiResponse(true, "send email to  " + user.getEmail() + ".");
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CurrentUser UserPrincipal principal) {
        JwtAuthenticationResponse token = tokenProvider.generateToken(principal);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/checked-token")
    public ApiResponse checkedToken(@RequestParam String type, @RequestParam String token) {
        TypeToken typeToken;
        if(type.equals("register")){
            typeToken = TypeToken.VERIFICATION_ACCOUNT;
        } else if(type.equals("forgot-your-password")) {
            typeToken = TypeToken.PASSWORD_FORGET;
        } else {
//			System.out.println(type);
            throw new RequestNotAcceptableException("type not exists");
        }

        ActivatorToken at = activatorTokenService.checkToken(token, typeToken);
        if(at.getExpiration().isBefore(Instant.now())) {
            return  new ApiResponse(false, "Token expired!");
        } else {
            User user = userRepository.findById(UUID.fromString(at.getRef())).orElseThrow(()->new ResourceNotFoundException("User", "email", at.getRef()));
            if(type.equals("register")) {
                user.setStatus(StatusUser.ACTIVE);
                user.setActive(true);
                userRepository.save(user);
                activatorTokenService.deleteToken(at.getId());
            }
            return  new ApiResponse(true, user.getEmail());
        }


    }

    @PostMapping("/defined-new-password")
    public ApiResponse changedPassword(@CurrentUser UserPrincipal principal, @RequestBody @Validated ChangePasswordTemplate changed) {
        User user = userRepository.findByEmail(principal.getEmail()).orElseThrow();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        changed.getOldPassword()
                )
        );
        if(authentication.isAuthenticated()) {
            user.setPassword(passwordEncoder.encode(changed.getNewPassword()));
            userRepository.save(user);
        }
        return  new ApiResponse(true, user.getEmail());
    }

    @PostMapping("/new-password")
    public ResponseEntity<?> setNewPassword(@RequestBody @Validated @Valid ChangePasswordTemplate changed) {
        if(changed.getEmail().isBlank())
            throw new RequestNotAcceptableException("Email require");
        if(changed.getToken().isBlank())
            throw new RequestNotAcceptableException("Token require");

        ActivatorToken at = activatorTokenService.checkToken(changed.getToken(), TypeToken.PASSWORD_FORGET);
        User user = userRepository.findById(UUID.fromString(at.getRef())).orElseThrow(()->new ResourceNotFoundException("User", "email", at.getRef()));
        user.setPassword(passwordEncoder.encode(changed.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Mot de passe changé."));
    }


}
