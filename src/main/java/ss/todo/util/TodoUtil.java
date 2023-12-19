package ss.todo.util;

import java.util.Objects;

import org.springframework.stereotype.Component;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;

@Component
public class TodoUtil {

    public TodoRequestDto buildTodoRequest(TodoRequestDto todoRequestDto) {

      if(Objects.isNull(todoRequestDto.getVersion()))
        todoRequestDto.setVersion(TodoConstant.DEFAULT_API_VERSION);

      return todoRequestDto;
    }

    public TodoResponseDto buildTodoResponse(TodoResponseDto todoResponseDto) {

      return todoResponseDto;
    }

    

}
