package ss.todo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class TodoExceptionHandler {

  private final TodoUtil todoUtil;

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(todoUtil.getCustomErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    return new ResponseEntity<>(todoUtil.getCustomErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = NoResourceFoundException.class)
  public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
    return new ResponseEntity<>(todoUtil.getCustomErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    return new ResponseEntity<>(todoUtil.getCustomErrorMessage(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(value = NullPointerException.class)
  public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
    return new ResponseEntity<>(todoUtil.getCustomErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = WrongParameterException.class)
  public ResponseEntity<Object> handleWrongParameterException(WrongParameterException ex) {
    //return new ResponseEntity<>(new CustomErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(todoUtil.getCustomErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = PastDueItemUpdateException.class)
  public ResponseEntity<Object> handlePastDueItemUpdateException(PastDueItemUpdateException ex) {
    return new ResponseEntity<>(todoUtil.getCustomErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = PastDueDateException.class)
  public ResponseEntity<Object> handlePastDueDateException(PastDueDateException ex) {
    return new ResponseEntity<>(todoUtil.getCustomErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

}
