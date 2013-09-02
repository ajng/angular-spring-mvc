package com.splendidcode.angular.sample.login;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class AuthInfoTest {
   
   
   @Test 
   public void testEqualsAndHashcode(){
      EqualsVerifier.forClass(AuthInfo.class).suppress(Warning.NONFINAL_FIELDS).usingGetClass().verify();
   }
}
