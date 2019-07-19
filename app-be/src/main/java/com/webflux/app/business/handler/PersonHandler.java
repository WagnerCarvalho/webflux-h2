package com.webflux.app.business.handler;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.repository.PersonRepository;
import com.webflux.app.business.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.fromCallable;

@Component
public class PersonHandler {

  private final PersonService personService;
  private final PersonRepository personRepository;

  public PersonHandler(PersonService personService, PersonRepository personRepository) {
    this.personService = personService;
    this.personRepository = personRepository;
  }

  public Mono<ServerResponse> create(final ServerRequest serverRequest) {
    return serverRequest.bodyToMono(Person.class)
            .flatMap(personService::save)
            .map(person -> UriComponentsBuilder.fromUriString("/person/{id}").buildAndExpand(person.getId()).toUri())
            .flatMap(uri -> ServerResponse.created(uri).build());
  }

  public Mono<ServerResponse> findAll() {
    return fromCallable(personRepository::findAll)
            .publishOn(Schedulers.elastic())
            .flatMap(areas -> ok().body(fromObject(areas)));
  }




}
