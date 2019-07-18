package com.webflux.app.business.service;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import static reactor.core.publisher.Mono.fromCallable;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  private Person persistPerson(final Person person) {
    personRepository.save(person);
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
