package dev.hmmr.spring.batch.playground;

import static org.assertj.core.api.Assertions.assertThat;

import dev.hmmr.spring.batch.playground.model.Person;
import dev.hmmr.spring.batch.playground.repository.PersonRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class ApplicationIT {
  @Autowired PersonRepository personRepository;

  @Test
  void shouldMakeAllPersonsUppercaseAfterContextLoaded() {
    final Iterable<Person> actualPersons = personRepository.findAll();

    final List<Person> expected =
        List.of(
            new Person("JILL", "DOE"),
            new Person("JOE", "DOE"),
            new Person("JUSTIN", "DOE"),
            new Person("JANE", "DOE"),
            new Person("JOHN", "DOE"));
    assertThat(actualPersons)
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
        .containsExactlyElementsOf(expected);
  }
}
