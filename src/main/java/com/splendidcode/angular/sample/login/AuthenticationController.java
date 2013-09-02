package com.splendidcode.angular.sample.login;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AuthenticationController {
   public static final String LOGIN_URL = "/login";

   private final UserAuthenticationService authenticationService;

   @Inject
   public AuthenticationController(UserAuthenticationService authenticationService) {
      this.authenticationService = authenticationService;
   }

   @RequestMapping(LOGIN_URL)
   public HttpEntity<String> login(@RequestBody LoginRequest loginRequest, BindingResult results) {
      if(authenticationService.authenticateUser(loginRequest)) {
         return okStatus();
      }
      else {
         return new ResponseEntity<String>("Authentication Failure", HttpStatus.UNAUTHORIZED);
      }
   }

   @RequestMapping(value =  LOGIN_URL+"/add-user", method = POST)
   public HttpEntity<String> newUser(@RequestBody LoginRequest loginRequest) {
      authenticationService.createUser(loginRequest);
      return okStatus();
   }
   
   @RequestMapping("/logout")
   public HttpEntity<String> logout(){
      authenticationService.logout();
      return okStatus();
   }

   private ResponseEntity<String> okStatus() {
      return new ResponseEntity<String>("OK", HttpStatus.OK);
   }
}
