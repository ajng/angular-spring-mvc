package com.splendidcode.angular.sample.login;

import com.splendidcode.angular.sample.login.hashing.HashSecurityUpdater;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Utils;
import com.splendidcode.angular.sample.util.ShiroAwareTest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
   private AuthenticationInfo authenticationInfo;
   @Mock
   private AuthInfo authInfo;
   @Mock
   private AuthInfoFactory authInfoFactory;
   @Mock
   private HashSecurityUpdater securityUpdater;
   @Captor
   private ArgumentCaptor<UsernamePasswordToken> tokenCaptor;


   private UserAuthenticationService authService;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      setSubject(subject);
      authService = new UserAuthenticationService(authInfoRepository, authInfoFactory, securityUpdater);
   }

   @Test
   public void logout_logs_out_the_subject() {
      authService.logout();
      verify(subject).logout();
   }

   @Test
   public void authenticate_user_logs_out_current_user_then_logs_in_the_subject() {
      LoginRequest loginRequest = new LoginRequest("username", "password");
      when(subject.isAuthenticated()).thenReturn(true);
      
      boolean success = authService.authenticateUser(loginRequest);
      verify(subject).logout();
      verify(subject).login(tokenCaptor.capture());
      
      assertThat(tokenCaptor.getValue().getUsername()).isEqualTo("username");
      assertThat(tokenCaptor.getValue().getPassword()).isEqualTo("password".toCharArray());
      assertThat(success).isTrue();
   }

   @Test
   public void authenticate_user_returns_false_on_failure() {
      LoginRequest loginRequest = new LoginRequest("username", "password");
      doThrow(new AuthenticationException()).when(subject).login(any(AuthenticationToken.class));
      boolean success = authService.authenticateUser(loginRequest);
      assertThat(success).isFalse();
   }

   @Test
   public void create_user() {
      LoginRequest loginRequest = new LoginRequest("username", "password");
      when(authInfoFactory.createAuthInfo("username", "password")).thenReturn(authInfo);
      authService.createUser(loginRequest);
      verify(authInfoRepository).add(authInfo);
   }

   @Test
   public void on_successful_it_should_try_to_update_the_auth() {
      authService.onSuccess(token, authenticationInfo);
      verify(securityUpdater).updateAuthInfoIfNecessary(token, authenticationInfo);
   }

   @Test
   public void on_logout_does_nothing() {
      authService.onLogout(null);
   }

   @Test
   public void on_failure_does_nothing() {
      authService.onFailure(null, null);
   }

}
