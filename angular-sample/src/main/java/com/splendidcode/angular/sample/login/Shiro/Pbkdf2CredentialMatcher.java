package com.splendidcode.angular.sample.login.Shiro;

import com.splendidcode.angular.sample.login.AuthInfo;
import com.splendidcode.angular.sample.login.Pbkdf2Utils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class Pbkdf2CredentialMatcher implements CredentialsMatcher {


   private final Pbkdf2Utils hasher;

   @Inject
   public Pbkdf2CredentialMatcher(Pbkdf2Utils hasher) {
      this.hasher = hasher;
   }

   @Override
   public boolean doCredentialsMatch(AuthenticationToken genericToken, AuthenticationInfo info) {
      if(info.getCredentials() == null){
         return false;
      }
      UsernamePasswordToken token = (UsernamePasswordToken)genericToken;
      AuthInfo authInfo = (AuthInfo)info.getCredentials();
      char[] password = token.getPassword();

      byte[] submittedValuesHash = hasher.calculateHash(password,
            authInfo.getSaltBytes(),
            authInfo.getIterations(),
            authInfo.getHashBytes().length);

      return hasher.constantTimeCompare(submittedValuesHash, authInfo.getHashBytes());
   }
}
