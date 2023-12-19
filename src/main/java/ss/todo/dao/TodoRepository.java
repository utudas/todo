package ss.todo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import ss.todo.model.TodoEntity;

@Repository
public interface TodoRepository extends CrudRepository<TodoEntity, Long> {

}
