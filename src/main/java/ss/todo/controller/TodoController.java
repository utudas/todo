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

    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody TodoRequestDto todoRequestDto) throws HttpMessageNotReadableException {
      return new ResponseEntity(todoService.createTodo(todoRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Object> getTodo(@PathVariable("todoId") Long todoId) {
      return new ResponseEntity(todoService.getTodoById(todoId), HttpStatus.OK);
   }

   @GetMapping(value = {"", "/status/{allTodoFetchCondition}"})
    public ResponseEntity<Object> getConditionalTodo(@PathVariable(value = "allTodoFetchCondition", required = false) String allTodoFetchCondition) {
      return new ResponseEntity(todoService.getConditionalTodo(allTodoFetchCondition), HttpStatus.OK);
   }

   @PutMapping
    public ResponseEntity<Object> updateTodo(@RequestBody TodoRequestDto todoRequestDto) {
      return new ResponseEntity(todoService.updateTodo(todoRequestDto), HttpStatus.OK);
   }

}