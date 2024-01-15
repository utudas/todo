FROM openjdk:17-alpine
EXPOSE 8084
ADD target/todo-1.0.jar todo.jar
ENTRYPOINT ["java","-jar","/todo.jar"]