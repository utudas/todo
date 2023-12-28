package ss.todo.dao;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;
import ss.todo.model.TodoEntity;
import ss.todo.util.PastDueItemUpdateException;
import ss.todo.util.Status;
import ss.todo.util.TodoConstant;

@Repository
@AllArgsConstructor
public class TodoDao implements ITodoDao {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {
        TodoEntity todoEntity = modelMapper.map(todoRequestDto, TodoEntity.class);
        if(Objects.isNull(todoEntity))
          throw new NullPointerException(TodoConstant.NULL_TODO_OBJECT);
        
          todoEntity = todoRepository.save(todoEntity);

        return modelMapper.map(todoEntity, TodoResponseDto.class);
    }

    @Override
    public TodoResponseDto getTodoById(Long todoId) {
      
      Optional<TodoEntity> todoEntity = todoRepository.findById(todoId);

      Optional<TodoResponseDto> todoResponseDto = todoEntity.map(todoEntityObject -> {
        return modelMapper.map(todoEntityObject, TodoResponseDto.class);
      });
      
      if(!todoResponseDto.isEmpty())
        return todoResponseDto.get();

      return null;
    }

    @Override
    public List<TodoResponseDto> getAllTodo() {
      Iterable<TodoEntity> allTodoList = todoRepository.findAll();
      return Streamable.of(allTodoList).toList().stream().map(todoEntity -> modelMapper.map(todoEntity, TodoResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TodoResponseDto> getAllNotDoneTodo() {
      List<TodoEntity> notDonetodoList = todoRepository.findAllNotDoneTodo();
      return Streamable.of(notDonetodoList).toList().stream().map(todoEntity -> modelMapper.map(todoEntity, TodoResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoResponseDto updateTodo(TodoRequestDto todoRequestDto) {

      boolean dbUpdate = false;

      if(todoRepository.existsById(todoRequestDto.getId())) {

        TodoEntity todoEntity = todoRepository.findById(todoRequestDto.getId()).get();

        if(todoEntity.getStatus() == Status.PAST_DUE) {
          throw new PastDueItemUpdateException(TodoConstant.PAST_DUE_ITEM_UPDATE);
        }

        if(!todoRequestDto.getDescription().equals(todoEntity.getDescription())) {
          todoEntity.setDescription(todoRequestDto.getDescription());
          dbUpdate = true;
        }

        if((todoRequestDto.getStatus() == Status.DONE) && !(todoEntity.getStatus() == Status.DONE)) {
          todoEntity.setStatus(Status.DONE);
          todoEntity.setDoneDate(new Date(System.currentTimeMillis()));
          dbUpdate = true;
        }

        if((todoRequestDto.getStatus() == Status.NOT_DONE) && !(todoEntity.getStatus() == Status.NOT_DONE)) {
          todoEntity.setStatus(Status.NOT_DONE);
          todoEntity.setDoneDate(null);
          dbUpdate = true;
        }

        if(dbUpdate) {
          return modelMapper.map(saveEntity(todoEntity), TodoResponseDto.class);
        }

        if(todoRequestDto.isPastDueUpdateFlag()) {
          todoEntity.setStatus(todoRequestDto.getStatus());
          saveEntity(todoEntity);
        }
        
      }
 
      return null;
    }


    private TodoEntity saveEntity(TodoEntity todoEntity) {
      todoEntity = todoRepository.save(todoEntity);
      return todoEntity;
    }

}