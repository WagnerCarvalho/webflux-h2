package com.webflux.app.search.config;

import com.webflux.app.search.handler.SearchHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

//  @Bean
//  public RouterFunction<ServerResponse> routes(final SearchHandler searchHandler) {
//    return nest(path("/person").and(accept(APPLICATION_JSON_UTF8)).and(contentType(APPLICATION_JSON_UTF8)),
//            //route(GET("/"), searchHandler::searchAll));
//  }
}
