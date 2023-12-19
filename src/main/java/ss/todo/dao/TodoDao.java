package ss.todo.dao;

import java.util.Objects;

import org.modelmapper.ModelMapper;
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

}