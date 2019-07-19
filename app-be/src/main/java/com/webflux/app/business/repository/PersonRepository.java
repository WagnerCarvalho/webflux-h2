package com.webflux.app.business.repository;

import com.webflux.app.business.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
  Person findByEmail(String email);
  Person findFirstByEmail(String email);
}
