package ss.todo.schedular;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ss.todo.dao.ITodoDao;
import ss.todo.dto.TodoRequestDto;
import ss.todo.dto.TodoResponseDto;
import ss.todo.util.Status;

@Configuration
@EnableScheduling
public class TodoSchedular {

    @Autowired private ITodoDao todoDao;
    @Autowired private ModelMapper modelMapper;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Paris")
    public void scheduledPastDueTask() {
        List<TodoResponseDto> notDoneTodoList = todoDao.getAllNotDoneTodo();
        List<TodoRequestDto> todoRequestDtoList = new ArrayList<>();
        java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        notDoneTodoList.forEach(notDoneTodo -> 
        {       
            if(todayDate.after(notDoneTodo.getDueDate())) {
              TodoRequestDto todoRequestDto = modelMapper.map(notDoneTodo, TodoRequestDto.class);
              todoRequestDto.setStatus(Status.PAST_DUE);
              todoRequestDto.setPastDueUpdateFlag(true);
              todoRequestDtoList.add(todoRequestDto);
            }    
        });

        todoRequestDtoList.forEach(pastDueUpdate->
        {
            todoDao.updateTodo(pastDueUpdate);
        });
    
    }

}



