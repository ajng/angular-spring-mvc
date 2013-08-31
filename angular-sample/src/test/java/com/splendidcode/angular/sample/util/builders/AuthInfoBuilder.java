package com.splendidcode.angular.sample.util.builders;

import com.splendidcode.angular.sample.login.AuthInfo;

public class AuthInfoBuilder {
   private String username;
   private String hash;
   private String salt;
   private int iterations;

   public AuthInfoBuilder() {
      username = "jdoe";
      hash = "0A";
      salt = "42";
      iterations = 1;
   }

   public AuthInfo build() {
      return new AuthInfo(username, hash, salt, iterations);
   }

   public AuthInfoBuilder withUsername(String username) {
      this.username = username;
      return this;
   }

   public AuthInfoBuilder withHash(String hash) {
      this.hash = hash;
      return this;
   }

   public AuthInfoBuilder withSalt(String salt) {
      this.salt = salt;
      return this;
   }

   public AuthInfoBuilder withIterations(int iterations) {
      this.iterations = iterations;
      return this;
   }

   public AuthInfoBuilder withSameUsernameAs(AuthInfo authInfo) {
      this.username = authInfo.getUsername();
      return this;
   }
}
