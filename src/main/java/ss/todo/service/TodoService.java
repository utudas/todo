package ss.todo.service;

import java.util.List;
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

      todoRequestDto = todoUtil.buildTodoRequest(todoRequestDto, null);
      TodoResponseDto todoResponseDto = todoDao.createTodo(todoRequestDto);
      todoResponseDto = todoUtil.buildTodoResponse(todoResponseDto);

      return todoResponseDto;
    }

    @Override
    public TodoResponseDto getTodoById(Long todoId) {
        TodoResponseDto todoResponseDto = todoDao.getTodoById(todoId);

        return todoResponseDto;
    }

    @Override
    public List<TodoResponseDto> getConditionalTodo(String allTodoFetchCondition) {

      TodoRequestDto todoRequestDto = new TodoRequestDto();
      todoRequestDto = todoUtil.buildTodoRequest(todoRequestDto, allTodoFetchCondition);

      if(todoRequestDto.isTodoFetchCondition()) {
        return todoDao.getAllTodo();
      }
      
      return todoDao.getAllNotDoneTodo();
    }

    @Override
    public TodoResponseDto updateTodo(TodoRequestDto todoRequestDto) {
      todoRequestDto = todoUtil.buildTodoRequest(todoRequestDto, null);
      TodoResponseDto todoResponseDto = todoDao.updateTodo(todoRequestDto);
      todoResponseDto = todoUtil.buildTodoResponse(todoResponseDto);

      return todoResponseDto;
    }

    
    

}