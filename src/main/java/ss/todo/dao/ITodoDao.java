package ss.todo.dao;

import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;

public interface ITodoDao {
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto);
}
