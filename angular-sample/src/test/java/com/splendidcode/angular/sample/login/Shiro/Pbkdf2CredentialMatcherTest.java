package com.splendidcode.angular.sample.login.Shiro;

import com.splendidcode.angular.sample.login.AuthInfo;
import com.splendidcode.angular.sample.login.AuthInfoFactory;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Options;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Utils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;

import static com.splendidcode.angular.sample.util.builders.Builders.newPbkdf2Options;
import static com.splendidcode.angular.sample.util.builders.Builders.newPbkdf2Utils;
import static org.fest.assertions.api.Assertions.assertThat;

public class Pbkdf2CredentialMatcherTest {

   private Pbkdf2CredentialMatcher matcher;
   private Pbkdf2Utils hasher;
   private Pbkdf2Options options;

   @Before
   public void setup() {
      options = newPbkdf2Options().build();
      hasher = newPbkdf2Utils().withOptions(options).build();
      matcher = new Pbkdf2CredentialMatcher(hasher);
   }

   @Test
   public void credentials_with_same_password_as_auth_info_match() {
      UsernamePasswordToken token = new UsernamePasswordToken("username", "password");
      AuthInfoFactory authInfoFactory = new AuthInfoFactory(hasher, options);
      AuthInfo authInfo = authInfoFactory.createAuthInfo("username", "password");
      SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("username", authInfo, "realm");
      boolean matches = matcher.doCredentialsMatch(token, authenticationInfo);
      assertThat(matches).isTrue();
   }

   @Test
   public void if_provided_credentials_are_null_it_should_not_match() {
      UsernamePasswordToken token = new UsernamePasswordToken("username", "password");
      SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("username", null, "realm");
      boolean matches = matcher.doCredentialsMatch(token, authenticationInfo);
      assertThat(matches).isFalse();
   }
   
   @Test
   public void credentials_with_different_password_as_auth_info_match() {
      UsernamePasswordToken token = new UsernamePasswordToken("username", "wrongPassword");
      AuthInfoFactory authInfoFactory = new AuthInfoFactory(hasher, options);
      AuthInfo authInfo = authInfoFactory.createAuthInfo("username", "password");
      SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("username", authInfo, "realm");
      boolean matches = matcher.doCredentialsMatch(token, authenticationInfo);
      assertThat(matches).isFalse();
   }
}
