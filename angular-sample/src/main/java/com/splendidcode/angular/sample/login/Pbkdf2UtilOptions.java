package com.splendidcode.angular.sample.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Pbkdf2UtilOptions {
   private final int passwordHashIterations;

   private final int passwordSaltBytes;

   private final int passwordHashBytes;

   public Pbkdf2UtilOptions(
         @Value("${sample.security.password_hash_iterations: 10000}") int passwordHashIterations,
         @Value("${sample.security.password_salt_bytes: 24}") int passwordSaltBytes,
         @Value("${sample.security.password_hash_bytes: 24}") int passwordHashBytes) {
      
      this.passwordHashIterations = passwordHashIterations;
      this.passwordSaltBytes = passwordSaltBytes;
      this.passwordHashBytes = passwordHashBytes;
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
}
