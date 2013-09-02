package com.splendidcode.angular.sample.util.builders;

import com.splendidcode.angular.sample.login.hashing.Pbkdf2Options;

public class Pbkdf2OptionsBuilder {

   private int passwordHashIterations;
   private int passwordSaltByes;
   private int passwordsHashBytes;

   public Pbkdf2OptionsBuilder() {
      passwordHashIterations = 1;
      passwordSaltByes = 24;
      passwordsHashBytes = 24;
   }

   public Pbkdf2Options build() {
      return new Pbkdf2Options(passwordHashIterations, passwordSaltByes, passwordsHashBytes);
   }

   public Pbkdf2OptionsBuilder thatAreAllDifferentThanDefault() {
      passwordHashIterations+=1;
      passwordSaltByes+=2;
      passwordSaltByes+=2;
      return this;
   }

   public Pbkdf2OptionsBuilder withPasswordHashBytes(int passwordsHashBytes) {
      this.passwordsHashBytes = passwordsHashBytes;
      return this;
   }

   public Pbkdf2OptionsBuilder withSaltHashBytes(int saltHashBytes) {
      this.passwordSaltByes = saltHashBytes;
      return this;
   }

   public Pbkdf2OptionsBuilder withIterations(int iterations) {
      this.passwordHashIterations = iterations;
      return this;
   }
}
