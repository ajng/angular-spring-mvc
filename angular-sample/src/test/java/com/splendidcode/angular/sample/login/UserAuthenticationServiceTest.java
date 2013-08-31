package com.splendidcode.angular.sample.login;

import com.splendidcode.angular.sample.util.ShiroAwareTest;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UserAuthenticationServiceTest extends ShiroAwareTest {

   @Mock
   private AuthInfoRepository authInfoRepository;
   @Mock
   private Pbkdf2Utils hasher;
   @Mock
   private Subject subject;
   @Mock
   private AuthenticationToken token;
   @Mock
   private AuthenticationInfo authInfo;
   
   private UserAuthenticationService authService;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      setSubject(subject);
      authService = new UserAuthenticationService(authInfoRepository, hasher, authInfoFactory, hashSecurityUpdater);
   }

   @Test
   public void logout_logs_out_the_subject() {
      authService.logout();
      verify(subject).logout();
   }

   @Test
   public void create_user() {
      LoginRequest loginRequest = new LoginRequest("username", "password");
      byte[] salt = new byte[]{0, 0, 0, 0};
      when(hasher.generateRandomSalt()).thenReturn(salt);
      authService.createUser(loginRequest);
      
      verify(hasher).calculateHash("password".toCharArray(),salt);
      verify(authInfoRepository).add();
   }
   
   @Test
   public void on_succesful_auth_if_the_system_has_not_changed_their_security_requirements_it_should_not_update_the_auth(){
      when(authInfo.getCredentials()).thenReturn()
      authService.onSuccess(token, authInfo);
      verify(authInfoRepository, never()).update(any(AuthInfo.class));
   }
   
}
