package dev.hmmr.spring.batch.playground;

import static org.springframework.boot.SpringApplication.exit;
import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    // make sure JVM exists upon job completion
    // https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.spring-application.application-exit
    System.exit(exit(run(Application.class, args)));
  }
}
