package dev.hmmr.spring.batch.playground.service;

import dev.hmmr.spring.batch.playground.model.Person;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobCompletionNotificationListener implements JobExecutionListener {
  JdbcTemplate jdbcTemplate;

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      jdbcTemplate
          .query(
              "SELECT first_name, last_name FROM people",
              (rs, row) -> new Person(rs.getString(1), rs.getString(2)))
          .forEach(person -> log.info("Found <{{}}> in the database.", person));
    }
  }
}
