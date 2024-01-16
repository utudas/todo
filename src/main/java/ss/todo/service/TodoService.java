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

    /**
   * Creates todo for valid request
   * @author Uttam Das
   * @param TodoRequestDto 
   * @exception HttpMessageNotReadableException. This exception is raised when API request doesn't conform TodoRequestDto.
   * @throws WrongParameterException. This exception is raised when ID param is supplied in TodoRequestDto.
   * @throws NullPointerException. This exception is raised when todo is not saved in db for technical failure.
   * @return TodoResponseDto
   */

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

    /**
   * Retrieves existing todo by using todoId
   * @author Uttam Das
   * @param todoId
   * @throws ResourceNotFoundException. This exception is raised when todo with supplied id doesn't exist in db.
   * @return TodoResponseDto
   */

    @Override
    public TodoResponseDto getTodoById(Long todoId) {
        TodoResponseDto todoResponseDto = todoDao.getTodoById(todoId);

        if(Objects.isNull(todoResponseDto))
          throw new ResourceNotFoundException(TodoConstant.TODO_NOT_FOUND + todoId);

        return todoResponseDto;
    }

     /**
   * Retrieves todos based on fetching condition set or not
   * @author Uttam Das
   * @param allTodoFetchCondition
   * @throws ResourceNotFoundException. This exception is raised when no todo exists in db.
   * @return List<TodoResponseDto>
   */

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

    /**
   * Updates todo
   * @author Uttam Das
   * @param TodoRequestDto
   * @throws WrongParameterException. This exception is raised when ID param is not supplied in TodoRequestDto.
   * @throws ResourceNotFoundException. This exception is raised when no todo exists in db for the supplied todo id in TodoRequestDto.
   * @return TodoResponseDto
   */

    @Override
    public TodoResponseDto updateTodo(TodoRequestDto todoRequestDto) {
      if(todoUtil.isNull(todoRequestDto.getId()))
        throw new WrongParameterException(TodoConstant.ID_PARAM_MISSING_IN_UPDATE_TODO);

      todoRequestDto = todoUtil.buildTodoRequest(todoRequestDto, null);
      TodoResponseDto todoResponseDto = todoDao.updateTodo(todoRequestDto);

      if(todoUtil.isNull(todoResponseDto))
        throw new ResourceNotFoundException(TodoConstant.TODO_NOT_FOUND + todoRequestDto.getId());

      todoResponseDto = todoUtil.buildTodoResponse(todoResponseDto);

      return todoResponseDto;
    }

    
    

}