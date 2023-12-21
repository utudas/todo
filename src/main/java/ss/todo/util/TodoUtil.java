package ss.todo.util;

import java.util.Objects;
import org.springframework.stereotype.Component;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;

@Component
public class TodoUtil {

    public TodoRequestDto buildTodoRequest(TodoRequestDto todoRequestDto, String allTodoFetchCondition) {

      if(Objects.isNull(todoRequestDto.getVersion()))
        todoRequestDto.setVersion(TodoConstant.DEFAULT_API_VERSION);

      if(Objects.nonNull(allTodoFetchCondition))
        todoRequestDto.setTodoFetchCondition(true);

      return todoRequestDto;
    }

    public TodoResponseDto buildTodoResponse(TodoResponseDto todoResponseDto) {

      return todoResponseDto;
    }

    

}
