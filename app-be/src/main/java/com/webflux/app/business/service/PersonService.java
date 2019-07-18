package com.webflux.app.business.service;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

import static reactor.core.publisher.Mono.fromCallable;

@Service
public class PersonService {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private final TransactionTemplate transactionTemplate;

  @Autowired
  private final PersonRepository personRepository;

  public PersonService(TransactionTemplate transactionTemplate, PersonRepository personRepository) {
    this.transactionTemplate = transactionTemplate;
    this.personRepository = personRepository;

  }

  public Optional<Person> findByEmail(final String email) {
    return personRepository.findByEmail(email);
  }

  private Person persistPerson(final Person person) {
    personRepository.save(person);
    personRepository.findAll().forEach(p -> log.info("person: {}", p));
    return person;
  }

  public Mono<Person> save(final Person person) {
    return fromCallable(() -> personRepository.findByEmail(person.getEmail()))
            .publishOn(Schedulers.elastic())
            .flatMap(Mono::justOrEmpty)
            .map(currentPerson -> persistPerson(person))
            .flatMap(this::save);
  }
}
