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


   @Override
   public boolean equals(Object o) {
      if(this == o) return true;
      if(o == null || getClass() != o.getClass()) return false;

      Todo todo = (Todo)o;

      if(done != todo.done) return false;
      if(id != null ? !id.equals(todo.id) : todo.id != null) return false;
      if(text != null ? !text.equals(todo.text) : todo.text != null) return false;
      if(username != null ? !username.equals(todo.username) : todo.username != null) return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (text != null ? text.hashCode() : 0);
      result = 31 * result + (done ? 1 : 0);
      result = 31 * result + (username != null ? username.hashCode() : 0);
      return result;
   }
}
