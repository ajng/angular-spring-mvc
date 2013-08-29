package com.splendidcode.angular.sample.todo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("todos")
public class TodoController {


   private TodoRepository todoRepository;

   @Inject
   public TodoController(TodoRepository todoRepository) {
      this.todoRepository = todoRepository;
   }


   @RequestMapping(method = GET)
   @ResponseBody
   public List<Todo> getTodosForCurrentUser() {
      return todoRepository.getAllTodosForCurrentUser();
   }

   @RequestMapping(method = POST)
   @ResponseBody
   public Todo addTodo(@RequestBody Todo todo) {
      return todoRepository.addTodo(todo);
   }

   @RequestMapping(value = "{id}", method = PUT)
   @ResponseBody
   public Todo updateTodo(@PathVariable UUID id, @RequestBody Todo todo) {
      return todoRepository.updateTodo(id, todo);
   }

   @RequestMapping(value = "completed", method = DELETE)
   @ResponseBody
   public List<Todo> clearCompleted() {
      todoRepository.removeCompleted();
      return getTodosForCurrentUser();
   }


}
