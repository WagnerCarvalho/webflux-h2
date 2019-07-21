package com.webflux.app.search.handler;

import com.webflux.app.business.exception.BadRequestException;
import com.webflux.app.business.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.fromCallable;

@Component
public class SearchHandler {

//  public Mono<ServerResponse> searchAll() {
//    return fromCallable()
//            .publishOn(Schedulers.elastic())
//            .flatMap(person -> ok().body(fromObject(person)))
//            .onErrorMap(ValidationException.class, BadRequestException::new);
//  }

}
