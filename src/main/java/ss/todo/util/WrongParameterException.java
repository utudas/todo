package ss.todo.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WrongParameterException extends RuntimeException {
    private final String message;
}
