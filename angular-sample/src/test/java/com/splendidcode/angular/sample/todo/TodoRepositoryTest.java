package com.splendidcode.angular.sample.todo;

import com.splendidcode.angular.sample.util.RepositoryTest;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static com.splendidcode.angular.sample.util.builders.Builders.newTodo;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TodoRepositoryTest extends RepositoryTest {


   @Inject
   private NamedParameterJdbcTemplate jdbcTemplate;

   @Mock
   private Subject subject;
   private TodoRepository repository;

   @Before
   public void setup() {
      initMocks(this);
      repository = new TodoRepository(jdbcTemplate, subject);
   }


   @Test
   @Transactional
   public void todo_roundtrip() {
      when(subject.getPrincipal()).thenReturn("jdoe");
      Todo todo = newTodo().withText("Test").thatIsDone().withUsername("jdoe").build();
      repository.addTodo(todo);
      Todo databaseTodo = repository.getTodo(todo.getId());

      assertThat(databaseTodo.getText()).isEqualTo("Test");
      assertThat(databaseTodo.isDone()).isEqualTo(true);
      assertThat(databaseTodo.getUsername()).isEqualTo("jdoe");
      assertThat(databaseTodo.getId()).isEqualTo(todo.getId());
   }

   @Test
   @Transactional
   public void getting_all_todos_for_current_user() {
      when(subject.getPrincipal()).thenReturn("jdoe", "jdoe", "somebodyElse", "jdoe");
      Todo userTodo1 = newTodo().build();
      Todo userTodo2 = newTodo().build();
      Todo otherUserTodo = newTodo().build();
     
      repository.addTodo(userTodo1);
      repository.addTodo(userTodo2);
      repository.addTodo(otherUserTodo);

      List<Todo> todos = repository.getAllTodosForCurrentUser();
      
      assertThat(todos).hasSize(2);
      assertThat(extractProperty("username").from(todos)).containsOnly("jdoe", "jdoe");
   }
   
   @Test
   @Transactional
   public void updating_todo(){
      when(subject.getPrincipal()).thenReturn("jdoe");
      
      Todo todo = newTodo().withText("before").thatIsNotDone().build();
      repository.addTodo(todo);
      
      todo.setText("after");
      todo.setDone(true);
      
      repository.updateTodo(todo.getId(), todo);

      Todo databaseTodo = repository.getTodo(todo.getId());
      
      assertThat(databaseTodo.getText()).isEqualTo("after");
      assertThat(databaseTodo.isDone()).isTrue();
   }
   
   @Test
   @Transactional
   public void deleting_completed_todos(){
      when(subject.getPrincipal()).thenReturn("jdoe");

      Todo notDone = newTodo().withText("undone").thatIsNotDone().build();
      
      Todo done1 = newTodo().withText("done1").thatIsDone().build();
      Todo done2 = newTodo().withText("done2").thatIsDone().build();
      repository.addTodo(notDone);
      repository.addTodo(done1);
      repository.addTodo(done2);
      
      repository.removeCompleted();

      List<Todo> todos = repository.getAllTodosForCurrentUser();

      assertThat(extractProperty("text").from(todos)).containsOnly("undone");
   }

}
