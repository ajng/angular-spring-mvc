package com.splendidcode.angular.sample.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

@Component
public class Pbkdf2Utils {
   @Value("${sample.security.password_hash_iterations: 10000}")
   private int PASSWORD_HASH_ITERATIONS;

   @Value("${sample.security.password_salt_bytes: 24}")
   private int PASSWORD_SALT_BYTES;

   @Value("${sample.security.password_hash_bytes: 24}")
   private int PASSWORD_HASH_BYTES;

   public byte[] calculateHash(char[] password, byte[] salt) {
      return calculateHash(password, salt, PASSWORD_HASH_ITERATIONS, PASSWORD_HASH_BYTES);
   }

   public byte[] calculateHash(char[] password, byte[] salt, int iterations, int hashSize) {
      try {
         PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, hashSize * 8);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
         return keyFactory.generateSecret(spec).getEncoded();
      } catch (Exception e) {
         throw new RuntimeException("Error calculating hash for user", e);
      }
   }
   
   public boolean constantTimeCompare(byte[] submitted, byte[] actual) {
      int differenceFound = submitted.length ^ actual.length;
      for(int i = 0; i < submitted.length && i < actual.length; i++) {
         differenceFound |= submitted[i] ^ actual[i];
      }
      return differenceFound == 0;
   }
   
   public byte[] generateRandomSalt() {
      SecureRandom random = new SecureRandom();
      byte[] salt = new byte[PASSWORD_SALT_BYTES];
      random.nextBytes(salt);
      return salt;
   }



   public int getDefaultIterations() {
      return PASSWORD_HASH_ITERATIONS;
   }

   public int getDefaultHashLength() {
      return PASSWORD_HASH_BYTES;
   }

   public int getDefaultSaltLength() {
      return PASSWORD_SALT_BYTES;
   }
}
