package ss.todo.util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;

public class TodoTestHelper {

    public static TodoRequestDto getTodoRequestDto() {
      TodoRequestDto todoRequestDto = new TodoRequestDto();
      todoRequestDto.setDescription(TodoConstant.TEST_DESCRIPTION);
      LocalDate tomorrow = LocalDate.now().plusDays(1);
      todoRequestDto.setDueDate(Date.valueOf(tomorrow));
      return todoRequestDto;
    }

    public static TodoResponseDto getTodoResponseDto() {
      TodoResponseDto todoResponseDto = new TodoResponseDto();
      todoResponseDto.setDescription(TodoConstant.TEST_DESCRIPTION);
      todoResponseDto.setId(TodoConstant.TEST_ID);
      return todoResponseDto;
    }

    public static List<TodoResponseDto> getNotDoneTodoList() {
      return List.of(getTodoResponseDto());
    }

    public static List<TodoResponseDto> getAllTodoList() {
      return List.of(getTodoResponseDto());
    }

    public static TodoRequestDto getTodoRequestDtoWithId() {
      TodoRequestDto todoRequestDto = new TodoRequestDto();
      todoRequestDto.setDescription(TodoConstant.TEST_DESCRIPTION);
      todoRequestDto.setId(1L);
      LocalDate tomorrow = LocalDate.now().plusDays(1);
      todoRequestDto.setDueDate(Date.valueOf(tomorrow));
      return todoRequestDto;
    }

}
