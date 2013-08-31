package com.splendidcode.angular.sample.login;

import org.apache.shiro.codec.Hex;

public class AuthInfo {

   private final String username;
   private final byte[] hash;
   private final byte[] salt;
   private final int iterations;

   public AuthInfo(String username, String hash, String salt, int iterations) {
      this(username, Hex.decode(hash), Hex.decode(salt), iterations);
   }
   
   public AuthInfo(String username, byte[] hash, byte[] salt, int iterations) {
      this.username = username;
      this.hash = hash;
      this.salt = salt;
      this.iterations = iterations;
   }

   public String getHash() {
      return Hex.encodeToString(hash);
   }

   public String getSalt() {
      return Hex.encodeToString(salt);
   }

   public byte[] getHashBytes() {
      return hash;
   }

   public byte[] getSaltBytes() {
      return salt;
   }

   public int getIterations() {
      return iterations;
   }

   public String getUsername() {
      return username;
   }
   
   

}