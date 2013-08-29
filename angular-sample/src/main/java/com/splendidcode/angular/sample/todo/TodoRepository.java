package com.splendidcode.angular.sample.todo;

import org.apache.shiro.subject.Subject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class TodoRepository {

   private final NamedParameterJdbcTemplate jdbcTemplate;
   private final Subject currentUser;

   @Inject
   public TodoRepository(NamedParameterJdbcTemplate jdbcTemplate, Subject currentUser) {
      this.jdbcTemplate = jdbcTemplate;
      this.currentUser = currentUser;
   }

   public List<Todo> getAllTodosForCurrentUser() {
      Map<String, Object> params = new HashMap<String, Object>() {
         {
            put("username", getCurrentUsername());
         }
      };

      return jdbcTemplate.query("SELECT id, text, done FROM todos WHERE username = :username", params, new BeanPropertyRowMapper(Todo.class));
   }

   private String getCurrentUsername() {
      return currentUser.getPrincipal().toString();
   }

   public Todo addTodo(Todo todo) {
      todo.setId(UUID.randomUUID());
      todo.setUsername(getCurrentUsername());
      jdbcTemplate.update("INSERT INTO todos (id, text, done, username) VALUES (:id, :text, :done, :username)", new BeanPropertySqlParameterSource(todo));
      return todo;
   }

   public Todo updateTodo(UUID id, Todo todo) {
      todo.setId(id);
      todo.setUsername(getCurrentUsername());
      jdbcTemplate.update("UPDATE todos SET text = :text, done = :done WHERE id = :id AND username = :username", new BeanPropertySqlParameterSource(todo));
      return todo;
   }

   public void removeCompleted() {
      Map<String, Object> params = new HashMap<String, Object>() {
         {
            put("username", getCurrentUsername());
         }
      };
      jdbcTemplate.update("DELETE FROM todos WHERE username = :username AND done = 'true' ", params);
   }
}
