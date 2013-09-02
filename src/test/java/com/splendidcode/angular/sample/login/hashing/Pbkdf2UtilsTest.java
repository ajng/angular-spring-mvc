package com.splendidcode.angular.sample.login.hashing;

import com.google.common.io.BaseEncoding;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Options;
import com.splendidcode.angular.sample.login.hashing.Pbkdf2Utils;
import org.junit.Before;
import org.junit.Test;

import static com.splendidcode.angular.sample.util.builders.Builders.newPbkdf2Options;
import static org.fest.assertions.api.Assertions.assertThat;

public class Pbkdf2UtilsTest {

   private Pbkdf2Utils hasher;

   @Before
   public void setup(){
      Pbkdf2Options options = newPbkdf2Options()
            .withPasswordHashBytes(24)
            .withSaltHashBytes(4)
            .withIterations(1000)
            .build();

      hasher = new Pbkdf2Utils(options);

   }
   
   
   @Test
   public void hash_produced_should_match_expected(){
      "password".toCharArray();
      byte[] hash = hasher.calculateHash("password".toCharArray(), "salt".getBytes());
      byte[] expectedHash = BaseEncoding.base16().lowerCase().decode("6e88be8bad7eae9d9e10aa061224034fed48d03fcbad968b");
      assertThat(hash).isEqualTo(expectedHash);
   }
   
   @Test
   public void generate_random_salt_genereates_a_new_one_each_Time(){
      assertThat(hasher.generateRandomSalt()).isNotEqualTo(hasher.generateRandomSalt());
   }
   
   @Test
   public void constant_time_compare_compares_correctly(){
      byte[] hash1 = BaseEncoding.base16().lowerCase().decode("6e88be8bad7eae9d9e10aa061224034fed48d03fcbad968b");
      byte[] hash2 = BaseEncoding.base16().lowerCase().decode("6e88be8bad7eae9d9e10aa061224034fed48d03fcbad968b");
      byte[] differentHash = BaseEncoding.base16().lowerCase().decode("6e88be8bad7eae9d9e10aa061224034fed48d03fcbad968f");
      assertThat(hasher.constantTimeCompare(hash1,hash2)).isTrue();
      assertThat(hasher.constantTimeCompare(hash1,differentHash)).isFalse();
      assertThat(hasher.generateRandomSalt()).isNotEqualTo(hasher.generateRandomSalt());
   }
   

}
