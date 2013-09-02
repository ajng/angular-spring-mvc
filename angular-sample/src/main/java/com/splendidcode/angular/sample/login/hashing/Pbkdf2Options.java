package com.splendidcode.angular.sample.login.hashing;

import com.splendidcode.angular.sample.login.AuthInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Pbkdf2Options {
   private final int passwordHashIterations;

   private final int passwordSaltBytes;

   private final int passwordHashBytes;

   public Pbkdf2Options(
         @Value("${sample.security.password_hash_iterations: 10000}") int passwordHashIterations,
         @Value("${sample.security.password_salt_bytes: 24}") int passwordSaltBytes,
         @Value("${sample.security.password_hash_bytes: 24}") int passwordHashBytes) {

      this.passwordHashIterations = passwordHashIterations;
      this.passwordSaltBytes = passwordSaltBytes;
      this.passwordHashBytes = passwordHashBytes;
   }

   public static Pbkdf2Options copyFrom(AuthInfo authInfo) {
      return new Pbkdf2Options(authInfo.getIterations(), authInfo.getSaltBytes().length, authInfo.getHashBytes().length);
   }

   public int getPasswordHashBytes() {
      return passwordHashBytes;
   }

   public int getPasswordHashIterations() {
      return passwordHashIterations;
   }

   public int getPasswordSaltBytes() {
      return passwordSaltBytes;
   }

   @Override
   public boolean equals(Object o) {
      if(this == o) return true;
      if(o == null || getClass() != o.getClass()) return false;

      Pbkdf2Options that = (Pbkdf2Options)o;

      if(passwordHashBytes != that.passwordHashBytes) return false;
      if(passwordHashIterations != that.passwordHashIterations) return false;
      if(passwordSaltBytes != that.passwordSaltBytes) return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = passwordHashIterations;
      result = 31 * result + passwordSaltBytes;
      result = 31 * result + passwordHashBytes;
      return result;
   }
}
