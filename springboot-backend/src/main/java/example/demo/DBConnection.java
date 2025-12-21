package example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBConnection {

    @Value("${usernames}")
    String userNames;

    @Value("${passwords}")
    String passwords;

   @PostConstruct
    public void init(){
       System.out.println("database connection");
       System.out.println("username : " +userNames + " | password : " +passwords);
   }
}
