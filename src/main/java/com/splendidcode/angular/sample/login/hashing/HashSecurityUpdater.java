package com.splendidcode.angular.sample.login.hashing;

import com.splendidcode.angular.sample.login.AuthInfo;
import com.splendidcode.angular.sample.login.AuthInfoFactory;
import com.splendidcode.angular.sample.login.AuthInfoRepository;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class HashSecurityUpdater {


   private final Pbkdf2Options hasherOptions;
   private final AuthInfoFactory authInfoFactory;
   private final AuthInfoRepository authInfoRepository;

   @Inject
   public HashSecurityUpdater(Pbkdf2Options hasherOptions, AuthInfoFactory authInfoFactory, AuthInfoRepository authInfoRepository) {
      this.hasherOptions = hasherOptions;
      this.authInfoFactory = authInfoFactory;
      this.authInfoRepository = authInfoRepository;
   }


   public void updateAuthInfoIfNecessary(AuthenticationToken genericToken, AuthenticationInfo authenticationInfo) {
      UsernamePasswordToken token = (UsernamePasswordToken)genericToken;
      AuthInfo authInfo = (AuthInfo)authenticationInfo.getCredentials();
      if(shouldUpdateHashSecurity(authInfo)) {
         updateAuthInfo(authInfo, token.getPassword());
      }
   }

   private void updateAuthInfo(AuthInfo authInfo, char[] password) {
      AuthInfo newAuthInfo = authInfoFactory.newAuthInfoFromUsernameAndPassword(authInfo.getUsername(), password);
      authInfoRepository.update(newAuthInfo);
   }

   private boolean shouldUpdateHashSecurity(AuthInfo authInfo) {
      Pbkdf2Options newOptions = Pbkdf2Options.copyFrom(authInfo);
      return !newOptions.equals(hasherOptions);
   }

}
