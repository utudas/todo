package ss.todo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;
import ss.todo.service.ITodoService;
import ss.todo.util.TodoConstant;


@RestController
@RequestMapping("/todo")
@AllArgsConstructor
public class TodoController {

    private final ITodoService todoService;

    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody TodoRequestDto todoRequestDto) throws HttpMessageNotReadableException {

      TodoResponseDto todoResponseDto = todoService.createTodo(todoRequestDto);

      if(Objects.isNull(todoResponseDto))
        throw new NullPointerException(TodoConstant.TODO_NOT_SAVED);

      return new ResponseEntity(todoResponseDto, HttpStatus.CREATED);
    }


    @GetMapping("/{todoId}")
    public ResponseEntity<Object> getTodo(@PathVariable("todoId") Long todoId) {

      TodoResponseDto todoResponseDto = todoService.getTodoById(todoId);

      if(Objects.isNull(todoResponseDto))
        return new ResponseEntity("Todo with ID "+todoId+" not found", HttpStatus.NOT_FOUND);

      return new ResponseEntity(todoResponseDto, HttpStatus.FOUND);
   }


   @GetMapping(value = {"", "/status/{allTodoFetchCondition}"})
    public ResponseEntity<Object> getConditionalTodo(@PathVariable(value = "allTodoFetchCondition", required = false) String allTodoFetchCondition) {

      List<TodoResponseDto> TodoResponse = todoService.getConditionalTodo(allTodoFetchCondition);

      if(TodoResponse.isEmpty())
        return new ResponseEntity("There are no Todo", HttpStatus.NOT_FOUND);

      return new ResponseEntity(TodoResponse, HttpStatus.FOUND);
   }


}
