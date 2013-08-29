package com.splendidcode.angular.sample.login.Shiro;

import com.splendidcode.angular.sample.login.AuthInfo;
import com.splendidcode.angular.sample.login.AuthInfoRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class Pbkdf2AuthenticationRealm extends AuthenticatingRealm implements Realm {

   private final AuthInfoRepository authInfoRepository;

   @Inject
   public Pbkdf2AuthenticationRealm(AuthInfoRepository authInfoRepository, Pbkdf2CredentialMatcher credentialMatcher) {
      this.authInfoRepository = authInfoRepository;
      this.setCredentialsMatcher(credentialMatcher);
   }

   @Override
   protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken genericToken) throws AuthenticationException {
      UsernamePasswordToken token = (UsernamePasswordToken)genericToken;
      AuthInfo authInfo = authInfoRepository.getAuthInfo(token.getUsername());
      return authInfo == null ? null :  new SimpleAuthenticationInfo(token.getUsername(), authInfo, this.getName());
   }


}
