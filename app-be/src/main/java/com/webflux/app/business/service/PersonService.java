package com.webflux.app.business.service;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.exception.ValidationException;
import com.webflux.app.business.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static reactor.core.publisher.Mono.fromCallable;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;
  private Logger log = LoggerFactory.getLogger(this.getClass());

  private Person createPerson(Person person) {
    final Person persistedPersonEntity = personRepository.findFirstByEmail(person.getEmail());
    if (persistedPersonEntity == null) {
      return personRepository.save(person);
    } else {
      throw new ValidationException("user already registered");
    }
  }

  public Mono<Person> save(final Person person) {
    return fromCallable(() -> createPerson(person))
            .publishOn(Schedulers.elastic())
            .doOnError(e -> log.error("user already registered", e))
            .flatMap(Mono::justOrEmpty);
  }
}
