package com.splendidcode.angular.sample.login.Shiro;

import com.splendidcode.angular.sample.login.AuthInfo;
import com.splendidcode.angular.sample.login.AuthInfoRepository;
import com.splendidcode.angular.sample.util.builders.Builders;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class Pbkdf2AuthenticationRealmTest {


   @Mock
   private AuthInfoRepository authInfoRepository;
   private Pbkdf2AuthenticationRealm authRealm;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      authRealm = new Pbkdf2AuthenticationRealm(authInfoRepository, new Pbkdf2CredentialMatcher(Builders.newPbkdf2Utils().build()));
   }

   @Test
   public void when_user_does_not_exist_returns_null() {
      when(authInfoRepository.getAuthInfo("username")).thenReturn(null);
      AuthenticationInfo result = authRealm.doGetAuthenticationInfo(new UsernamePasswordToken("username", "password"));
      assertThat(result).isNull();
   }
   
   @Test
   public void when_user_exist_returns_authentication_info_with_right_credentials() {
      AuthInfo authInfo = new AuthInfo("username", "0000", "0000", 1);
      when(authInfoRepository.getAuthInfo("username")).thenReturn(authInfo);
      AuthenticationInfo result = authRealm.doGetAuthenticationInfo(new UsernamePasswordToken("username", "password"));
      assertThat(result.getCredentials()).isEqualTo(authInfo);
   }

}
