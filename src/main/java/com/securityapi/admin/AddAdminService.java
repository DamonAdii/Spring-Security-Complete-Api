package com.securityapi.admin;

import com.securityapi.user.Role;
import com.securityapi.user.User;
import com.securityapi.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AddAdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @PostConstruct
    public void addAdmin(){

        Optional<User>  adminuser= this.userRepository.findByEmail("admin@test.com");

        if(!adminuser.isPresent()){

            User user = new User();

            user.setEmail("admin@test.com");
            user.setFirstname("Admin");
            user.setLastname("Boss");
            user.setRole(Role.ADMIN);
            user.setPassword(encoder.encode("Admin123"));

            this.userRepository.save(user);
        }
        else{

            throw new RuntimeException("Admin is already present");
        }

    }

}
