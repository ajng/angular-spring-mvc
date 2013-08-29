package com.splendidcode.angular.sample.todo;

import java.util.UUID;

public class Todo {
   private UUID id;
   private String text;
   private boolean done;
   private String username;


   public boolean isDone() {
      return done;
   }

   public void setDone(boolean done) {
      this.done = done;
   }

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }
}
