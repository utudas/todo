package ss.todo.service;

import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;

public interface ITodoService {
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto);
    public TodoResponseDto getTodoById(Long todoId);
}
