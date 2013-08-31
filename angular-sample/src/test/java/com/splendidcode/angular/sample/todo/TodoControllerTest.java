package com.splendidcode.angular.sample.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.splendidcode.angular.sample.util.builders.Builders.newTodo;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TodoControllerTest {


   MockMvc mockMvc;

   @Mock
   TodoRepository todoRepository;
   private ObjectMapper json;

   @Before
   public void setup() {
      initMocks(this);
      mockMvc = MockMvcBuilders.standaloneSetup(new TodoController(todoRepository)).build();
      json = new ObjectMapper();
   }

   @Test
   public void get_todos_gets_them_from_repository() throws Exception {
      mockMvc.perform(get("/todos")).andExpect(status().isOk());
      verify(todoRepository).getAllTodosForCurrentUser();
   }
   
   @Test
   public void posting_todo_adds_it_to_the_repository() throws Exception {
      Todo todo = newTodo().build();
      mockMvc.perform(post("/todos").content(json.writeValueAsString(todo)).contentType(APPLICATION_JSON)).andExpect(status().isOk());
      verify(todoRepository).addTodo(todo);
   }
   
   @Test
   public void put_todo_updates_it_in_teh_repository() throws Exception {
      Todo todo = newTodo().build();
      mockMvc.perform(put("/todos/{id}",todo.getId()).content(json.writeValueAsString(todo)).contentType(APPLICATION_JSON)).andExpect(status().isOk());
      verify(todoRepository).updateTodo(todo.getId(), todo);
   }
   
   @Test
   public void delete_completed_todos_deletes_from_repositories_and_queries_new_list() throws Exception {
      MvcResult result  = mockMvc.perform(delete("/todos/completed")).andExpect(status().isOk()).andReturn();
      verify(todoRepository).removeCompleted();
      verify(todoRepository).getAllTodosForCurrentUser();
   }
}
