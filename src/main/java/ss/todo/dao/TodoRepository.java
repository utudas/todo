package ss.todo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ss.todo.model.TodoEntity;
import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<TodoEntity, Long> {

    @Query("SELECT t FROM TodoEntity t WHERE t.status=NOT_DONE")
    List<TodoEntity> findAllNotDoneTodo();

}
