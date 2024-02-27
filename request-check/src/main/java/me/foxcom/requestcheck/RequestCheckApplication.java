package me.foxcom.requestcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"me.foxcom.requestcheck", "me.foxcom.requestresource"})
public class RequestCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestCheckApplication.class, args);
    }

}
