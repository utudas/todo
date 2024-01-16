package ss.todo.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;
import ss.todo.service.ITodoService;
import ss.todo.util.ResourceNotFoundException;
import ss.todo.util.TodoExceptionHandler;
import ss.todo.util.TodoTestHelper;
import ss.todo.util.TodoUtil;
import ss.todo.util.WrongParameterException;

@ExtendWith(SpringExtension.class)
public class TodoControllerTest {

  @InjectMocks private TodoController todoController;
  @InjectMocks private ObjectMapper objectMapper;
  @Mock private TodoUtil todoUtil;
  @Mock private ITodoService todoService;

  private MockMvc mockMvc;


  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
              .standaloneSetup(todoController)
              .setControllerAdvice(new TodoExceptionHandler(todoUtil))
              .build();
  }

  @Test
  public void createTodo() throws JsonProcessingException, Exception {

    when(todoUtil.nonNull(any())).thenReturn(false);
    when(todoUtil.isNull(any(TodoResponseDto.class))).thenReturn(false);
    when(todoService.createTodo(any(TodoRequestDto.class))).thenReturn(TodoTestHelper.getTodoResponseDto());
    
    mockMvc
    .perform(
      MockMvcRequestBuilders.post("/todo")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(TodoTestHelper.getTodoRequestDto())))
    .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void createTodoWhenIdParamIsPassedInRequest() throws JsonProcessingException, Exception {

    when(todoService.createTodo(any(TodoRequestDto.class))).thenThrow(WrongParameterException.class);
    
     mockMvc
    .perform(
      MockMvcRequestBuilders.post("/todo")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(TodoTestHelper.getTodoRequestDtoWithId())))
    .andExpect(MockMvcResultMatchers.status().isBadRequest())
    .andExpect(result-> assertTrue(result.getResolvedException() instanceof WrongParameterException));
  }

  @Test
  public void createTodoWhenNullResponseReceivedFromService() throws JsonProcessingException, Exception {

    when(todoService.createTodo(any(TodoRequestDto.class))).thenThrow(NullPointerException.class);
    
     mockMvc
    .perform(
      MockMvcRequestBuilders.post("/todo")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(TodoTestHelper.getTodoRequestDto())))
    .andExpect(MockMvcResultMatchers.status().isInternalServerError())
    .andExpect(result-> assertTrue(result.getResolvedException() instanceof NullPointerException));
  }

}