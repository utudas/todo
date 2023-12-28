package ss.todo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PastDueDateException extends RuntimeException {
    private final String message;
}
