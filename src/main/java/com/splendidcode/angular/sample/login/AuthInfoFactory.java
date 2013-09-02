package com.splendidcode.angular.sample.login;

import com.splendidcode.angular.sample.login.hashing.Pbkdf2Options;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Utils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AuthInfoFactory {

   private final Pbkdf2Utils hasher;
   private final Pbkdf2Options options;

   @Inject
   public AuthInfoFactory(Pbkdf2Utils hasher, Pbkdf2Options options) {
      this.hasher = hasher;
      this.options = options;
   }


   public AuthInfo createAuthInfo(String username, String password) {
      return newAuthInfoFromUsernameAndPassword(username, password.toCharArray());
   }

   public AuthInfo newAuthInfoFromUsernameAndPassword(String username, char[] password) {
      byte[] salt = hasher.generateRandomSalt();
      byte[] hash = hasher.calculateHash(password, salt);
      return new AuthInfo(username, hash, salt, options.getPasswordHashIterations());
   }
}
