package com.splendidcode.angular.sample.todo;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class TodoTest {
   
   @Test
   public void todo_equals_and_hash_code_works(){
      EqualsVerifier.forClass(Todo.class).suppress(Warning.NONFINAL_FIELDS).usingGetClass().verify();
   }
}
