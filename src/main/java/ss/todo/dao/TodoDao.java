package ss.todo.dao;

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

      if(todoRepository.existsById(todoRequestDto.getId())) {
        TodoEntity todoEntity = todoRepository.findById(todoRequestDto.getId()).get();
        todoEntity.setDescription(todoRequestDto.getDescription());
        todoEntity = todoRepository.save(todoEntity);
        return modelMapper.map(todoEntity, TodoResponseDto.class);
      }
 
      return null;
    }

}