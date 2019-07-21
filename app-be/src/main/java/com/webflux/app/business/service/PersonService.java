package com.webflux.app.business.service;

import com.sun.el.parser.AstFalse;
import com.webflux.app.business.domain.Person;
import com.webflux.app.business.exception.ValidationException;
import com.webflux.app.business.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.transaction.Transactional;
import java.util.Optional;
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
            .flatMap(Mono::justOrEmpty)
            .doOnError(e -> log.error("user already registered", e));
  }

  public Mono<Person> delete(final Long id) {
    return fromCallable(() -> deletePerson(id))
            .publishOn(Schedulers.elastic())
            .flatMap(Mono::justOrEmpty)
            .doOnError(e -> log.error("user not registered", e));
  }

  private Optional<Person> deletePerson(Long id) {
    final Optional<Person> deletePersonEntity = personRepository.findById(id);
    if (deletePersonEntity.isPresent()) {
      personRepository.deleteById(id);
    }else{
      throw new ValidationException("user not registered");
    }
    return deletePersonEntity;
  }

  @Transactional
  public Mono<Person> update(final Long id, final Person person) {
    return fromCallable(() -> updatePerson(id, person))
            .publishOn(Schedulers.elastic())
            .flatMap(Mono::justOrEmpty)
            .doOnError(e -> log.error("user already registered", e));
  }

  private Optional<Person> updatePerson(Long id, Person updatePerson) {
    final Optional<Person> updatePersonEntity = personRepository.findById(id);
    if (updatePersonEntity.isPresent()) {
      personRepository.updatePerson(id, updatePerson.getNickname(), updatePerson.getEmail());
    }else{
      throw new ValidationException("user not registered");
    }
    return updatePersonEntity;
  }





}
