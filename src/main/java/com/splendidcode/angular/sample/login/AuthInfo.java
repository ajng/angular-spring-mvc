package com.splendidcode.angular.sample.login;

import org.apache.shiro.codec.Hex;

import java.util.Arrays;

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

   @Override
   public boolean equals(Object o) {
      if(this == o) return true;
      if(o == null || getClass() != o.getClass()) return false;

      AuthInfo authInfo = (AuthInfo)o;

      if(iterations != authInfo.iterations) return false;
      if(!Arrays.equals(hash, authInfo.hash)) return false;
      if(!Arrays.equals(salt, authInfo.salt)) return false;
      if(username != null ? !username.equals(authInfo.username) : authInfo.username != null) return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = username != null ? username.hashCode() : 0;
      result = 31 * result + (hash != null ? Arrays.hashCode(hash) : 0);
      result = 31 * result + (salt != null ? Arrays.hashCode(salt) : 0);
      result = 31 * result + iterations;
      return result;
   }
}