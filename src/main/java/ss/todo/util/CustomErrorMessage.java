package ss.todo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class CustomErrorMessage {
    private int errorCode;
    private String errorMessage;
}
