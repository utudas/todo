package ss.todo.service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ss.todo.dao.ITodoDao;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;
import ss.todo.util.PastDueDateException;
import ss.todo.util.ResourceNotFoundException;
import ss.todo.util.TodoConstant;
import ss.todo.util.TodoUtil;
import ss.todo.util.WrongParameterException;


@Service
@AllArgsConstructor
public class TodoService implements ITodoService {

    private final TodoUtil todoUtil;
    private final ITodoDao todoDao;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {

      if(todoUtil.nonNull(todoRequestDto.getId()))
        throw new WrongParameterException(TodoConstant.ID_PARAM_IN_CREATE_TODO);

      if(!todoRequestDto.getDueDate().after(new java.sql.Date(Calendar.getInstance().getTime().getTime()))) {
        throw new PastDueDateException(TodoConstant.PAST_DUE_DATE);
      }

      todoRequestDto = todoUtil.buildTodoRequest(todoRequestDto, null);
      TodoResponseDto todoResponseDto = todoDao.createTodo(todoRequestDto);

      if(todoUtil.isNull(todoResponseDto))
        throw new NullPointerException(TodoConstant.TODO_NOT_SAVED);

      todoResponseDto = todoUtil.buildTodoResponse(todoResponseDto);

      return todoResponseDto;
    }

    @Override
    public TodoResponseDto getTodoById(Long todoId) {
        TodoResponseDto todoResponseDto = todoDao.getTodoById(todoId);

        if(Objects.isNull(todoResponseDto))
          throw new ResourceNotFoundException(TodoConstant.TODO_NOT_FOUND + todoId);

        return todoResponseDto;
    }

    @Override
    public List<TodoResponseDto> getConditionalTodo(String allTodoFetchCondition) {

      TodoRequestDto todoRequestDto = new TodoRequestDto();
      todoRequestDto = todoUtil.buildTodoRequest(todoRequestDto, allTodoFetchCondition);

      List<TodoResponseDto> todoResponse;

      if(todoRequestDto.isTodoFetchCondition()) {
        todoResponse = todoDao.getAllTodo();
      } else {
        todoResponse = todoDao.getAllNotDoneTodo();
      }

      if(todoResponse.isEmpty())
        throw new ResourceNotFoundException(TodoConstant.EMPTY_TODO);
      
      return todoResponse;
    }

    @Override
    public TodoResponseDto updateTodo(TodoRequestDto todoRequestDto) {
      if(Objects.isNull(todoRequestDto.getId()))
        throw new WrongParameterException(TodoConstant.ID_PARAM_MISSING_IN_UPDATE_TODO);

      todoRequestDto = todoUtil.buildTodoRequest(todoRequestDto, null);
      TodoResponseDto todoResponseDto = todoDao.updateTodo(todoRequestDto);

      if(Objects.isNull(todoResponseDto))
        throw new ResourceNotFoundException(TodoConstant.TODO_NOT_FOUND + todoRequestDto.getId());

      todoResponseDto = todoUtil.buildTodoResponse(todoResponseDto);

      return todoResponseDto;
    }

    
    

}