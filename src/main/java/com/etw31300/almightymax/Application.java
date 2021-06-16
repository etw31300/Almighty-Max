package com.etw31300.almightymax;

import com.etw31300.almightymax.config.ApplicationConfiguration;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Log4j2
@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class Application {

    public static void main(String[] args) {
        try {
            //Start the application
            SpringApplication.run(Application.class, args);
        }
        catch (Exception error) {
            log.error(error.getMessage());
        }
    }
}
