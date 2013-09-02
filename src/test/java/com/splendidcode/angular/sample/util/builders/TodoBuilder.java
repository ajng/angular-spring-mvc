package com.splendidcode.angular.sample.util.builders;

import com.splendidcode.angular.sample.todo.Todo;

import java.util.UUID;

public class TodoBuilder {
   private String text;
   private UUID id;
   private String username;
   private boolean done;


   public TodoBuilder() {
      text = "";
      id = UUID.randomUUID();
      username = "test";
      done = false;
   }

   public Todo build() {
      Todo todo = new Todo();
      todo.setText(text);
      todo.setId(id);
      todo.setUsername(username);
      todo.setDone(done);
      return todo;
   }

   public TodoBuilder withText(String text) {
      this.text = text;
      return this;
   }

   public TodoBuilder thatIsDone() {
      done = true;
      return this;
   }

   public TodoBuilder withUsername(String username) {
      this.username = username;
      return this;
   }

   public TodoBuilder thatIsNotDone() {
      this.done = false;
      return this;
   }

   public TodoBuilder withSameIdAs(Todo other) {
      id = other.getId();
      return this;
   }
}
