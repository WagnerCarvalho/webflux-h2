package com.webflux.app.business.handler;

import com.webflux.app.business.domain.Person;
import com.webflux.app.business.service.PersonService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class PersonHandler {

  private final PersonService personService;

  public PersonHandler(PersonService personService) {
    this.personService = personService;
  }

  public Mono<ServerResponse> create(final ServerRequest serverRequest) {
    return serverRequest.bodyToMono(Person.class)
            .flatMap(personService::save)
            .map(person -> UriComponentsBuilder.fromUriString("/person/{id}").buildAndExpand(person.getId()).toUri())
            .flatMap(uri -> ServerResponse.created(uri).build() );
  }


}
