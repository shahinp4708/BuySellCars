package com.buysellcars.services.auth;


import com.buysellcars.dto.SignupRequest;
import com.buysellcars.dto.UserDto;
import com.buysellcars.entities.User;
import com.buysellcars.enums.UserRole;
import com.buysellcars.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;


    @PostConstruct
    public  void createAnAdminAccount(){
        Optional<User> optionalAdmin=userRepository.findByUserRole(UserRole.ADMIN);
        if(optionalAdmin.isEmpty()){
            User admin= new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setUserRole(UserRole.ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(admin);
        }else{
            System.out.println("Admin account already exists.");
        }
    }

    public  Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public UserDto signup(SignupRequest signupRequest) {
       User user = new User();
       user.setEmail(signupRequest.getEmail());
       user.setName(signupRequest.getName());
       user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
       user.setUserRole(UserRole.CUSTOMER);
       return userRepository.save(user).getUserDto();
    }
}
