package com.buysellcars.controllers;


import com.buysellcars.dto.AuthenticationRequest;
import com.buysellcars.dto.AuthenticationResponse;
import com.buysellcars.dto.SignupRequest;
import com.buysellcars.dto.UserDto;
import com.buysellcars.entities.User;
import com.buysellcars.repositories.UserRepository;
import com.buysellcars.services.auth.AuthService;
import com.buysellcars.services.jwt.UserService;
import com.buysellcars.utils.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private  final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private  final JWTUtil jwtUtil;
    private  final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.badRequest().body("Email already exists.");
        UserDto userDto =authService.signup(signupRequest);
        if(userDto!=null)
            return ResponseEntity.ok(userDto);
        return ResponseEntity.badRequest().body("Failed to signup.");
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate((
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail()
                            ,authenticationRequest.getPassword())));

        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect credentials");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail((authenticationRequest.getEmail()));
        final String jwt = jwtUtil.generateToken((userDetails));
        AuthenticationResponse response = new AuthenticationResponse();
        if(optionalUser.isPresent()){
            response.setJwt(jwt);
            response.setUserRole(optionalUser.get().getUserRole());
            response.setUserId(optionalUser.get().getId());
        }
        return response;
    }
}
