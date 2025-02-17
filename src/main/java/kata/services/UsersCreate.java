package kata.services;

import kata.model.Role;
import kata.model.User;
import kata.repositories.RoleRepository;
import kata.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Set;

@Component
public class UsersCreate {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {

        String userPassword = "user";
        String adminPassword = "admin";

        String encryptedUserPassword = passwordEncoder.encode(userPassword);
        String encryptedAdminPassword = passwordEncoder.encode(adminPassword);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);


        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(encryptedAdminPassword);
        adminUser.setAge((byte) 30);
        adminUser.setLastName("Admin");
        adminUser.setRoles(Set.of(adminRole));
        userRepository.save(adminUser);

        User regularUser = new User();
        regularUser.setUsername("user");
        regularUser.setPassword(encryptedUserPassword);
        regularUser.setAge((byte) 25);
        regularUser.setLastName("User");
        regularUser.setRoles(Set.of(userRole));
        userRepository.save(regularUser);
    }
}