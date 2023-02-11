package dev.hmmr.spring.batch.playground.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
  String firstName;
  String lastName;

  //  @Override
  //  public String toString() {
  //    return "firstName: " + firstName + ", lastName: " + lastName;
  //  }
}
