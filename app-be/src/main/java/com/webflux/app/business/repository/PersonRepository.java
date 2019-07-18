package com.webflux.app.business.repository;

import com.webflux.app.business.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

  Person save(Person person);
  List<Person> findAll();

  Optional<Person> findByEmail(String email);
}