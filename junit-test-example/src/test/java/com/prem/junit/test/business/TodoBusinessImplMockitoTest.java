package com.prem.junit.test.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.prem.junit.test.service.TodoService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TodoBusinessImplMockitoTest {
  @Test
  public void usingMockito() {
    TodoService todoService = mock(TodoService.class);
    List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
    when(todoService.retrieveTodos("Prem")).thenReturn(allTodos);
    TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
    List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("Prem");
    assertEquals(2, todos.size());
  }
}
