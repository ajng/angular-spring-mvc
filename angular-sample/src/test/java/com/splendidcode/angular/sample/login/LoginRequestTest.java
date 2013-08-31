package com.splendidcode.angular.sample.login;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class LoginRequestTest {
   
   @Test
   public void test_login_request_equals_and_hashcode(){
      EqualsVerifier.forClass(LoginRequest.class).usingGetClass().suppress(Warning.NONFINAL_FIELDS).verify(); 
   }
}
