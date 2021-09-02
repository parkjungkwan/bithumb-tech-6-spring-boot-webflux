package net.zerotodev.api.quiz.router;

import lombok.RequiredArgsConstructor;
import net.zerotodev.api.quiz.domain.User;
import net.zerotodev.api.quiz.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.reactive.function.server.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Configuration
public class MongoRouter {
    private final UserRepository db;

    @Bean
    public RouterFunction<ServerResponse> findAll(){
        final RequestPredicate predicate = RequestPredicates
                .GET("/find")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));
        RouterFunction<ServerResponse> response = RouterFunctions.route(predicate, request -> {
            Flux<User> mapper = db.findAll();
            db.findRegexByAlias("happy-john").collectList().subscribe(System.out::println);
            Pageable page = PageRequest.of(0,5);
            db.findByAliasWithPage("john", page).collectList().subscribe(System.out::println);
            User john = new User("john","happy-john","john","john@test.com");
            db.insert(john).subscribe(System.out::println);
            User tom = new User("tom","angry-tom","tom","tom@test.com");
            db.insert(tom).subscribe(System.out::println);
            Mono<ServerResponse> res = ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromProducer(mapper, User.class));
            return res;
        });
        return response;

    }
}
