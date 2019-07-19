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

  public Mono<Person> save(final Person person) {
    return fromCallable(() -> personRepository.findFirstByEmail(person.getEmail()))
            .defaultIfEmpty(personRepository.save(person))
            .publishOn(Schedulers.elastic())
            .flatMap(Mono::justOrEmpty);
  }
}
