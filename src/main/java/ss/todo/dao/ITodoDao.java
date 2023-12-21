package ss.todo.dao;

import java.util.List;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;

public interface ITodoDao {
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto);
    public TodoResponseDto getTodoById(Long todoId);
    public List<TodoResponseDto> getAllTodo();
    public List<TodoResponseDto> getAllNotDoneTodo();
}