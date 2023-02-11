package dev.hmmr.spring.batch.playground.config;

import dev.hmmr.spring.batch.playground.model.Person;
import dev.hmmr.spring.batch.playground.service.JobCompletionNotificationListener;
import dev.hmmr.spring.batch.playground.service.PersonItemProcessor;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
  @Bean
  public FlatFileItemReader<Person> reader() {
    final BeanWrapperFieldSetMapper<Person> mapper = new BeanWrapperFieldSetMapper<>();
    mapper.setTargetType(Person.class);

    return new FlatFileItemReaderBuilder<Person>()
        .name("personItemReader")
        .resource(new ClassPathResource("sample.csv"))
        .delimited()
        .names("firstName", "lastName")
        .fieldSetMapper(mapper)
        .build();
  }

  @Bean
  public PersonItemProcessor processor() {
    return new PersonItemProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Person>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
        .dataSource(dataSource)
        .build();
  }

  @Bean
  public Job importUserJob(
      JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
    return new JobBuilder("importUserJob", jobRepository)
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
  }

  @Bean
  public Step step1(
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      JdbcBatchItemWriter<Person> writer) {
    return new StepBuilder("step1", jobRepository)
        .<Person, Person>chunk(10, transactionManager)
        .reader(reader())
        .processor(processor())
        .writer(writer)
        .build();
  }
}
