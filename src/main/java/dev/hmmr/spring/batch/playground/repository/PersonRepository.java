package dev.hmmr.spring.batch.playground.repository;

import dev.hmmr.spring.batch.playground.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {}
