package com.buysellcars.services.auth;

import com.buysellcars.dto.SignupRequest;
import com.buysellcars.dto.UserDto;

public interface AuthService {
   public  void createAnAdminAccount();
   public  Boolean hasUserWithEmail(String email);

   UserDto signup(SignupRequest signupRequest);
}
