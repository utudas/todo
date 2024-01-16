package ss.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ss.todo.dao.ITodoDao;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;
import ss.todo.util.TodoConstant;
import ss.todo.util.TodoUtil;
import ss.todo.util.WrongParameterException;
import ss.todo.util.TodoTestHelper;


public class TodoServiceTest {

    @InjectMocks private TodoService todoService;
    @Mock private TodoUtil todoUtil;
    @Mock private ITodoDao todoDao;
    @Mock private TodoRequestDto todoRequestDto;

    @BeforeEach
    void setUp() {
      MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTodo() {
      TodoResponseDto todoResponseDto;
      
      when(todoUtil.buildTodoRequest(any(TodoRequestDto.class), eq(null))).thenReturn(TodoTestHelper.getTodoRequestDto());
      when(todoUtil.buildTodoResponse(any(TodoResponseDto.class))).thenReturn(TodoTestHelper.getTodoResponseDto());
      when(todoDao.createTodo(any(TodoRequestDto.class))).thenReturn(TodoTestHelper.getTodoResponseDto());
      
      todoResponseDto = todoService.createTodo(TodoTestHelper.getTodoRequestDto());

      assertNotNull(todoResponseDto);
      assertEquals(todoResponseDto.getDescription(), TodoConstant.TEST_DESCRIPTION);
    }

    @Test
    public void testGetTodoById() {
      TodoResponseDto todoResponseDto;
      
      when(todoDao.getTodoById(anyLong())).thenReturn(TodoTestHelper.getTodoResponseDto());
      
      todoResponseDto = todoService.getTodoById(TodoConstant.TEST_ID);

      assertNotNull(todoResponseDto);
      assertEquals(todoResponseDto.getId(), TodoConstant.TEST_ID);
    }

    @Test
    public void testGetConditionalTodoWhenTodoFetchConditionNotSet() {
      List<TodoResponseDto> todoResponseDtoList;
      when(todoUtil.buildTodoRequest(any(TodoRequestDto.class), eq(null))).thenReturn(TodoTestHelper.getTodoRequestDto());
      when(todoDao.getAllTodo()).thenReturn(TodoTestHelper.getAllTodoList());
      when(todoDao.getAllNotDoneTodo()).thenReturn(TodoTestHelper.getNotDoneTodoList());
      when(todoRequestDto.isTodoFetchCondition()).thenReturn(false);
      
      todoResponseDtoList = todoService.getConditionalTodo(null);

      assertNotNull(todoResponseDtoList);
      assertEquals(todoResponseDtoList.get(0).getId(), TodoConstant.TEST_ID);
    }

    @Test
    public void testGetConditionalTodoWhenTodoFetchConditionSet() {
      List<TodoResponseDto> todoResponseDtoList;
      when(todoUtil.buildTodoRequest(any(TodoRequestDto.class), anyString())).thenReturn(TodoTestHelper.getTodoRequestDto());
      when(todoDao.getAllTodo()).thenReturn(TodoTestHelper.getAllTodoList());
      when(todoDao.getAllNotDoneTodo()).thenReturn(TodoTestHelper.getNotDoneTodoList());
      when(todoRequestDto.isTodoFetchCondition()).thenReturn(true);
      
      todoResponseDtoList = todoService.getConditionalTodo("/status/all");

      assertNotNull(todoResponseDtoList);
      assertEquals(todoResponseDtoList.get(0).getId(), TodoConstant.TEST_ID);
    }

    @Test
    public void testUpdateTodoWhenIDParamIsNotPassed() {
      when(todoUtil.isNull(any())).thenReturn(true);

       assertThrows(WrongParameterException.class, () -> todoService.updateTodo(TodoTestHelper.getTodoRequestDto()));
    }
}