package com.prem.junit.test.business;

import com.prem.junit.test.service.TodoService;
import java.util.ArrayList;
import java.util.List;

public class TodoBusinessImpl {

  private TodoService todoService;

  TodoBusinessImpl(TodoService todoService) {
    this.todoService = todoService;
  }

  public List<String> retrieveTodosRelatedToSpring(String user) {
    List<String> filteredTodos = new ArrayList<String>();
    List<String> allTodos = todoService.retrieveTodos(user);
    for (String todo : allTodos) {
      if (todo.contains("Spring")) {
        filteredTodos.add(todo);
      }
    }
    return filteredTodos;
  }

  public void deleteTodosNotRelatedToSpring(String user) {
    List<String> allTodos = todoService.retrieveTodos(user);
    for (String todo : allTodos) {
      if (!todo.contains("Spring")) {
        todoService.deleteTodo(todo);
      }
    }
  }
}
