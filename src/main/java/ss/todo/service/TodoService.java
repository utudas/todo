package ss.todo.service;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ss.todo.dao.ITodoDao;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;
import ss.todo.util.TodoUtil;


@Service
@AllArgsConstructor
public class TodoService implements ITodoService {

    private final TodoUtil todoUtil;
    private final ITodoDao todoDao;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {

      todoRequestDto = todoUtil.buildTodoRequest(todoRequestDto);
      TodoResponseDto todoResponseDto = todoDao.createTodo(todoRequestDto);
      todoResponseDto = todoUtil.buildTodoResponse(todoResponseDto);

      return todoResponseDto;
    }


    @Override
    public TodoResponseDto getTodoById(Long todoId) {
        TodoResponseDto todoResponseDto = todoDao.getTodoById(todoId);

        return todoResponseDto;
    }


    

}