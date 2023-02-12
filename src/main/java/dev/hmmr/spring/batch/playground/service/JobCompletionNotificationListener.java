package dev.hmmr.spring.batch.playground.service;

import dev.hmmr.spring.batch.playground.repository.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JobCompletionNotificationListener implements JobExecutionListener {
  PersonRepository personRepository;

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.debug("!!! JOB FINISHED! Time to verify the results");
      personRepository
          .findAll()
          .forEach(person -> log.debug("Found <{{}}> in the database.", person));
    }
  }
}
