package io.cassiopee.textbook.configs;

import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.Privilege;
import io.cassiopee.textbook.entities.Role;
import io.cassiopee.textbook.entities.User;
import io.cassiopee.textbook.entities.enums.TypePrivilege;
import io.cassiopee.textbook.entities.enums.TypeRole;
import io.cassiopee.textbook.repositories.ActorRepository;
import io.cassiopee.textbook.repositories.PrivilegeRepository;
import io.cassiopee.textbook.repositories.RoleRepository;
import io.cassiopee.textbook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class DataLoadConfig {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActorRepository actorRepository;

    @PostConstruct
    public void loadRole() {
        if (roleRepository.count()>0) return;

        List<Privilege> privilegeSuperAdmin = new ArrayList<>();
        for(int i = 0; i< 2; i++) {
            String description = TypePrivilege.values()[i].toString().replace("_", " ");
            privilegeSuperAdmin.add(privilegeRepository.save(new Privilege(TypePrivilege.values()[i], description, TypeRole.AUTHENTICATION)));
        }
        Role roleAdmin = new Role("SUPER ADMIN", TypeRole.AUTHENTICATION);
        roleAdmin.setPrivileges(privilegeSuperAdmin);
        roleAdmin = roleRepository.save(roleAdmin);



        List<Privilege> privilegeAdmin = new ArrayList<>();
        for(int i = 2; i< 20; i++) {
            String description = TypePrivilege.values()[i].toString().replace("_", " ");
            privilegeAdmin.add(privilegeRepository.save(new Privilege(TypePrivilege.values()[i], description, TypeRole.ADMINISTRATION_MANAGER)));
        }
        Role administrationRole = new Role("ADMIN", TypeRole.ADMINISTRATION_MANAGER);
        administrationRole.setPrivileges(privilegeAdmin);
        roleRepository.save(administrationRole);


        List<Privilege> privilegeHeadDepartment = new ArrayList<>();
        for(int i = 20; i<= 24; i++) {
            String description = TypePrivilege.values()[i].toString().replace("_", " ");
            privilegeHeadDepartment.add(privilegeRepository.save(new Privilege(TypePrivilege.values()[i], description, TypeRole.HEAD_OF_DEPARTMENT)));
        }
        Role headDepartmentRole = new Role("HEADER DEPARTMENT", TypeRole.HEAD_OF_DEPARTMENT);
        headDepartmentRole.setPrivileges(privilegeHeadDepartment);
        roleRepository.save(headDepartmentRole);


        List<Privilege> privilegeTeacher = new ArrayList<>();
        for(int i = 25; i<= 27; i++) {
            String description = TypePrivilege.values()[i].toString().replace("_", " ");
            privilegeTeacher.add(privilegeRepository.save(new Privilege(TypePrivilege.values()[i], description, TypeRole.HEAD_OF_DEPARTMENT)));
        }
        Role teacherRole = new Role("TEACHER", TypeRole.TEACHER);
        teacherRole.setPrivileges(privilegeTeacher);
        roleRepository.save(teacherRole);


        List<Privilege> privilegeStudent = new ArrayList<>();
        for(int i = 28; i<= 29; i++) {
            String description = TypePrivilege.values()[i].toString().replace("_", " ");
            privilegeStudent.add(privilegeRepository.save(new Privilege(TypePrivilege.values()[i], description, TypeRole.HEAD_OF_DEPARTMENT)));
        }
        Role studentRole = new Role("HEADER OF CLASS", TypeRole.HEAD_OF_CLASS);
        studentRole.setPrivileges(privilegeStudent);
        roleRepository.save(studentRole);


        User admin = new User();
        admin.setName("admin");
        admin.setUsername("admin@gmail.com");
        admin.setEmail("admin@gmail.com");
        admin.setActive(true);
        admin.setPassword(passwordEncoder.encode("mot2P@ss"));
        admin.setRoles(Collections.singletonList(roleAdmin));
        admin = userRepository.save(admin);

        Actor actorAdmin = new Actor();
        actorAdmin.setUser(admin);
        actorAdmin.setFirstName("admin");
        actorAdmin.setType(TypeRole.AUTHENTICATION);
        actorAdmin.setLastName("admin");
        actorAdmin.setPhone("66248866");
        actorAdmin.setTitle("Adminisrateur");
        actorAdmin.setTitle("art");
        actorRepository.save(actorAdmin);
    }
}
