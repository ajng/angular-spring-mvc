package com.splendidcode.angular.sample.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.splendidcode.angular.sample.login.AuthenticationController.LOGIN_URL;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest {


   @Mock
   private UserAuthenticationService authenticationService;
   private MockMvc mockMvc;
   private ObjectMapper json;

   @Before
   public void setup() {
      initMocks(this);
      mockMvc = MockMvcBuilders.standaloneSetup(new AuthenticationController(authenticationService)).build();
      json = new ObjectMapper();
   }

   @Test
   public void when_valid_login_request_returns_OK() throws Exception {
      LoginRequest loginRequest = new LoginRequest("jdoe", "password");
      when(authenticationService.authenticateUser(loginRequest)).thenReturn(true);
      mockMvc.perform(get(LOGIN_URL).content(json.writeValueAsString(loginRequest)).contentType(APPLICATION_JSON)).andExpect(status().isOk());
   }
   
   @Test
   public void when_invalid_login_request_returns_unauthorized() throws Exception {
      LoginRequest loginRequest = new LoginRequest("jdoe", "password");
      when(authenticationService.authenticateUser(loginRequest)).thenReturn(false);
      mockMvc.perform(get("/login").content(json.writeValueAsString(loginRequest)).contentType(APPLICATION_JSON)).andExpect(status().isUnauthorized());
   }
   
   @Test
   public void when_posting_to_add_user_url_it_should_create_the_user() throws Exception {
      LoginRequest loginRequest = new LoginRequest("jdoe", "password");
      mockMvc.perform(post("/login/add-user").content(json.writeValueAsString(loginRequest)).contentType(APPLICATION_JSON)).andExpect(status().isOk());
      verify(authenticationService).createUser(loginRequest);
   }
   
   @Test
   public void when_getting_the_logout_url_it_should_logout() throws Exception {
      mockMvc.perform(get("/logout")).andExpect(status().isOk());
      verify(authenticationService).logout();
   }
   


}
