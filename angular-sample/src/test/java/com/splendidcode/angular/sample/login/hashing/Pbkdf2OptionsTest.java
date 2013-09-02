package com.splendidcode.angular.sample.login.hashing;

import com.splendidcode.angular.sample.login.hashing.Pbkdf2Options;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class Pbkdf2OptionsTest {
   

   @Test
   public void pbkdf2_options_equals_and_hash_code_works(){
      EqualsVerifier.forClass(Pbkdf2Options.class).suppress(Warning.NONFINAL_FIELDS).usingGetClass().verify();
   }
}
