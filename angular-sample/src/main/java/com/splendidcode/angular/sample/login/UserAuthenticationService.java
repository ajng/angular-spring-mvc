package com.splendidcode.angular.sample.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/*
 * Inspired by java implementation found here:
 * http://crackstation.net/hashing-security.htm
 */
@Service
public class UserAuthenticationService implements AuthenticationListener {


   private final AuthInfoRepository authInfoRepository;
   private final Pbkdf2Utils hasher;

   @Inject
   public UserAuthenticationService(AuthInfoRepository authInfoRepository, Pbkdf2Utils hasher) {
      this.authInfoRepository = authInfoRepository;
      this.hasher = hasher;
   }


   public boolean authenticateUser(LoginRequest loginRequest) {
      Subject currentUser = SecurityUtils.getSubject();
      currentUser.logout();
      try {
         currentUser.login(new UsernamePasswordToken(loginRequest.getUsername(), loginRequest.getPassword()));
      } catch (AuthenticationException ex) {
         return false;
      }

      return currentUser.isAuthenticated();
   }

   public void createUser(LoginRequest loginRequest) {
      AuthInfo authInfo = newAuthInfoFromUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword().toCharArray());
      authInfoRepository.add(authInfo);
   }


   @Override
   public void onSuccess(AuthenticationToken genericToken, AuthenticationInfo authenticationInfo) {
      UsernamePasswordToken token = (UsernamePasswordToken)genericToken;
      AuthInfo authInfo = (AuthInfo)authenticationInfo.getCredentials();
      if(shouldUpdateHashSecurity(authInfo)) {
         updateAuthInfo(token.getUsername(), token.getPassword());
      }
   }


   private void updateAuthInfo(String username, char[] password) {
      AuthInfo authInfo = newAuthInfoFromUsernameAndPassword(username, password);
      authInfoRepository.update(authInfo);
   }

   private AuthInfo newAuthInfoFromUsernameAndPassword(String username, char[] password) {
      byte[] salt = hasher.generateRandomSalt();
      byte[] hash = hasher.calculateHash(password, salt);
      return new AuthInfo(username, hash, salt, hasher.getDefaultIterations());
   }

   private boolean shouldUpdateHashSecurity(AuthInfo authInfo) {
      boolean iterationsChanged = authInfo.getIterations() != hasher.getDefaultIterations();
      boolean hashBytesChanged = authInfo.getHashBytes().length != hasher.getDefaultHashLength();
      boolean saltLengthChanged = authInfo.getSaltBytes().length != hasher.getDefaultSaltLength();
      return iterationsChanged || hashBytesChanged || saltLengthChanged;
   }

   @Override
   public void onFailure(AuthenticationToken token, AuthenticationException ae) {
      return;
   }

   @Override
   public void onLogout(PrincipalCollection principals) {
      return;
   }

   public void logout() {
      SecurityUtils.getSubject().logout();
   }
}
