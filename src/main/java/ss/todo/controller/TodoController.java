package ss.todo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import lombok.AllArgsConstructor;
import ss.todo.dto.TodoRequestDto;
import ss.todo.service.ITodoService;


@RestController
@RequestMapping("/todo")
@AllArgsConstructor
public class TodoController {

    private final ITodoService todoService;

    /**
   * Creates todo for valid request
   * @author Uttam Das
   * @param TodoRequestDto 
   * @exception HttpMessageNotReadableException. This exception is raised when API request doesn't conform TodoRequestDto.
   * @return TodoResponseDto
   */

    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody TodoRequestDto todoRequestDto) throws HttpMessageNotReadableException {
      return new ResponseEntity(todoService.createTodo(todoRequestDto), HttpStatus.CREATED);
    }

    /**
   * Retrieves existing todo by using todoId
   * @author Uttam Das
   * @param todoId
   * @return TodoResponseDto
   */

    @GetMapping("/{todoId}")
    public ResponseEntity<Object> getTodo(@PathVariable("todoId") Long todoId) {
      return new ResponseEntity(todoService.getTodoById(todoId), HttpStatus.OK);
   }

   /**
   * Retrieves NOT_DONE todo by default. if /todo/status/{anyString} endpoint is hit, then all todos are retrieved.
   * @author Uttam Das
   * @param allTodoFetchCondition
   * @return List<TodoResponseDto>
   */

   @GetMapping(value = {"", "/status/{allTodoFetchCondition}"})
    public ResponseEntity<Object> getConditionalTodo(@PathVariable(value = "allTodoFetchCondition", required = false) String allTodoFetchCondition) {
      return new ResponseEntity(todoService.getConditionalTodo(allTodoFetchCondition), HttpStatus.OK);
   }

   /**
   * Updates todo for valid todo request
   * @author Uttam Das
   * @param TodoRequestDto
   * @return TodoResponseDto
   */

   @PutMapping
    public ResponseEntity<Object> updateTodo(@RequestBody TodoRequestDto todoRequestDto) {
      return new ResponseEntity(todoService.updateTodo(todoRequestDto), HttpStatus.OK);
   }

}