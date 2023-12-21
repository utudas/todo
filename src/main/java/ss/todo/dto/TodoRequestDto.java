package ss.todo.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import ss.todo.util.Status;


@Getter
@Setter
public class TodoRequestDto {
    private Long id;
    private String description;
    private Status status;
    private Date creationDate;
    private Date dueDate;
    private Date doneDate;
    private String version;
    private boolean todoFetchCondition;
}
