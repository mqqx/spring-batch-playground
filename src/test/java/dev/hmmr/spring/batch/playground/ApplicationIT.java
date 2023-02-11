package dev.hmmr.spring.batch.playground;

import static org.assertj.core.api.Assertions.assertThat;

import dev.hmmr.spring.batch.playground.model.Person;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class ApplicationIT {
  @Autowired JdbcTemplate jdbcTemplate;

  @Test
  void shouldMakeAllPersonsUppercaseAfterContextLoaded() {
    final List<Person> updatedPersons =
        jdbcTemplate.query(
            "SELECT first_name, last_name FROM people",
            (rs, row) -> new Person(rs.getString(1), rs.getString(2)));

    final List<Person> expected =
        List.of(
            new Person("JILL", "DOE"),
            new Person("JOE", "DOE"),
            new Person("JUSTIN", "DOE"),
            new Person("JANE", "DOE"),
            new Person("JOHN", "DOE"));
    assertThat(updatedPersons).containsExactlyInAnyOrderElementsOf(expected);
  }
}
