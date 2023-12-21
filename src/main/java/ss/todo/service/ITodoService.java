package ss.todo.service;

import java.util.List;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;

public interface ITodoService {
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto);
    public TodoResponseDto getTodoById(Long todoId);
    public List<TodoResponseDto> getConditionalTodo(String allTodoFetchCondition);
}
