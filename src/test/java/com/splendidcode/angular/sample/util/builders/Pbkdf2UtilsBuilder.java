package com.splendidcode.angular.sample.util.builders;

import com.splendidcode.angular.sample.login.hashing.Pbkdf2Options;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Utils;

public class Pbkdf2UtilsBuilder {

   private Pbkdf2Options options;


   public Pbkdf2UtilsBuilder() {
      withDefaultOptions();
   }

   public Pbkdf2UtilsBuilder withDefaultOptions() {
      Builders.newPbkdf2Options();
     return this; 
   }

   public Pbkdf2Utils build(){
      return new Pbkdf2Utils(options);
   }

   public Pbkdf2UtilsBuilder withOptions(Pbkdf2Options pbkdf2Options) {
      this.options = pbkdf2Options;
      return this;
   }
}
