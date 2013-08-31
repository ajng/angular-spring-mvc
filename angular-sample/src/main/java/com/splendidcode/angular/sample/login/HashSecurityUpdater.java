package com.splendidcode.angular.sample.login;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class HashSecurityUpdater {


   private final Pbkdf2UtilOptions hasherOptions;
   private final AuthInfoFactory authInfoFactory;
   private final AuthInfoRepository authInfoRepository;

   @Inject
   public HashSecurityUpdater(Pbkdf2UtilOptions hasherOptions, AuthInfoFactory authInfoFactory, AuthInfoRepository authInfoRepository) {
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
      boolean iterationsChanged = authInfo.getIterations() != hasherOptions.getPasswordHashIterations();
      boolean hashBytesChanged = authInfo.getHashBytes().length != hasherOptions.getPasswordHashBytes();
      boolean saltLengthChanged = authInfo.getSaltBytes().length != hasherOptions.getPasswordSaltBytes();
      return iterationsChanged || hashBytesChanged || saltLengthChanged;
   }

}
