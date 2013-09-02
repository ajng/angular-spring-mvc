package com.splendidcode.angular.sample.util.builders;

public class Builders {
   public static TodoBuilder newTodo() {
      return new TodoBuilder();
   }

   public static AuthInfoBuilder newAuthInfo() {
      return new AuthInfoBuilder();
   }

   public static AuthInfoFactoryBuilder newAuthInfoFactory() {
      return new AuthInfoFactoryBuilder();
   }

   public static Pbkdf2UtilsBuilder newPbkdf2Utils() {
      return new Pbkdf2UtilsBuilder();
   }

   public static Pbkdf2OptionsBuilder newPbkdf2Options() {
      return new Pbkdf2OptionsBuilder();
   }
}
