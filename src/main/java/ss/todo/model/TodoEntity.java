package ss.todo.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import ss.todo.util.Status;


@Entity
@Table(name="todo")
@Getter @Setter
public class TodoEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name="description", length=100, nullable=false, unique=false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="creation_date", nullable=false, unique=false)
    private Date creationDate;

    @Column(name="due_date", nullable=false, unique=false)
    private Date dueDate;

    @Column(name="done_date", nullable=true, unique=false)
    private Date doneDate;
}