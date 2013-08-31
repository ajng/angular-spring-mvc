package com.splendidcode.angular.sample.login;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import java.security.SecureRandom;

@Component
public class Pbkdf2Utils {


   private final Pbkdf2UtilOptions options;

   @Inject
   public Pbkdf2Utils(Pbkdf2UtilOptions options) {
      this.options = options;
   }

   public byte[] calculateHash(char[] password, byte[] salt) {
      return calculateHash(password, salt, options.getPasswordHashIterations(), options.getPasswordHashBytes());
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
      byte[] salt = new byte[options.getPasswordSaltBytes()];
      random.nextBytes(salt);
      return salt;
   }
}
