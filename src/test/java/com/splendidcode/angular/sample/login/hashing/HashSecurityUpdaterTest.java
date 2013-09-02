package com.splendidcode.angular.sample.login.hashing;

import com.splendidcode.angular.sample.login.AuthInfo;
import com.splendidcode.angular.sample.login.AuthInfoFactory;
import com.splendidcode.angular.sample.login.AuthInfoRepository;
import com.splendidcode.angular.sample.login.hashing.HashSecurityUpdater;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Options;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.splendidcode.angular.sample.util.builders.Builders.newAuthInfoFactory;
import static com.splendidcode.angular.sample.util.builders.Builders.newPbkdf2Options;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class HashSecurityUpdaterTest {

   private Pbkdf2Options newerOptions;
   private AuthInfoFactory newerAuthInfoFactory;
   private HashSecurityUpdater hashSecurityUpdater;
   @Mock
   private AuthInfoRepository authInfoRepository;
   @Mock
   private AuthenticationInfo authenticationInfo;
   private Pbkdf2Options differentHasherOptions;
   private AuthInfoFactory olderAuthInfoFactory;
   private Pbkdf2Options differnetOptions;
   @Captor
   private ArgumentCaptor<AuthInfo> authInfoCaptor;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      newerOptions = newPbkdf2Options().build();
      differnetOptions = newPbkdf2Options().thatAreAllDifferentThanDefault().build();
      newerAuthInfoFactory = newAuthInfoFactory().withHasherWithOptions(newerOptions).build();
      olderAuthInfoFactory = newAuthInfoFactory().withHasherWithOptions(differnetOptions).build();
      hashSecurityUpdater = new HashSecurityUpdater(newerOptions, newerAuthInfoFactory, authInfoRepository);
   }


   @Test
   public void when_auth_info_was_made_with_same_options_as_current_it_should_not_update_the_credentials() {
      when(authenticationInfo.getCredentials()).thenReturn(newerAuthInfoFactory.createAuthInfo("username", "password"));
      hashSecurityUpdater.updateAuthInfoIfNecessary(new UsernamePasswordToken("username", "password".toCharArray()), authenticationInfo);
      verify(authInfoRepository, never()).update(any(AuthInfo.class));
   }

   @Test
   public void when_auth_info_was_made_with_different_options_as_current_it_should_not_update_the_credentials() {
      AuthInfo differentAuthInfo = olderAuthInfoFactory.createAuthInfo("username", "password");
      when(authenticationInfo.getCredentials()).thenReturn(differentAuthInfo);
      hashSecurityUpdater.updateAuthInfoIfNecessary(new UsernamePasswordToken("username", "password".toCharArray()), authenticationInfo);
      verify(authInfoRepository).update(authInfoCaptor.capture());
      AuthInfo updatedAuthInfo = authInfoCaptor.getValue();
      assertThat(updatedAuthInfo.getUsername()).isEqualTo("username");
      assertThat(updatedAuthInfo.getIterations()).isEqualTo(newerOptions.getPasswordHashIterations());
      assertThat(updatedAuthInfo.getHashBytes().length).isEqualTo(newerOptions.getPasswordHashBytes());
      assertThat(updatedAuthInfo.getSaltBytes().length).isEqualTo(newerOptions.getPasswordSaltBytes());
   }


}
