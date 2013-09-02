package com.splendidcode.angular.sample.util.builders;

import com.splendidcode.angular.sample.login.AuthInfoFactory;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Options;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Utils;

import static com.splendidcode.angular.sample.util.builders.Builders.newPbkdf2Utils;

public class AuthInfoFactoryBuilder {

   private Pbkdf2Utils hasher;
   private Pbkdf2Options hasherOptions;

   public AuthInfoFactoryBuilder() {
      withDefaultHasher();
   }

   public AuthInfoFactoryBuilder withDefaultHasher() {
      hasher = newPbkdf2Utils().build();
      return this;
   }

   public AuthInfoFactory build(){
      return new AuthInfoFactory(hasher, hasherOptions);
   }

   public AuthInfoFactoryBuilder withHasher(Pbkdf2Utils hasher) {
       this.hasher = hasher;
      return this;
   }

   public AuthInfoFactoryBuilder withHasherWithOptions(Pbkdf2Options hasherOptions) {
      this.hasher= newPbkdf2Utils().withOptions(hasherOptions).build();
      this.hasherOptions = hasherOptions;
      return this;
   }
}
