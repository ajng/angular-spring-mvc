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
   private final AuthInfoFactory authInfoFactory;
   private final HashSecurityUpdater hashSecurityUpdater;

   @Inject
   public UserAuthenticationService(AuthInfoRepository authInfoRepository, Pbkdf2Utils hasher, AuthInfoFactory authInfoFactory, HashSecurityUpdater hashSecurityUpdater) {
      this.authInfoRepository = authInfoRepository;
      this.hasher = hasher;
      this.authInfoFactory = authInfoFactory;
      this.hashSecurityUpdater = hashSecurityUpdater;
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
      AuthInfo authInfo = authInfoFactory.createAuthInfo(loginRequest.getUsername(), loginRequest.getPassword());
      authInfoRepository.add(authInfo);
   }

   @Override
   public void onSuccess(AuthenticationToken genericToken, AuthenticationInfo authenticationInfo) {
      hashSecurityUpdater.updateAuthInfoIfNecessary(genericToken, authenticationInfo);
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
