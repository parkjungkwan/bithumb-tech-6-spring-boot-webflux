package net.zerotodev.api.quiz.repository;

import net.zerotodev.api.quiz.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Optional<User> findByAlias(String alias);
    Flux<User> findAll();
    Mono<User> findById();

    @Query("{'alias': {$regex: ?0}}")
    Flux<User> findRegexByAlias(String alias); // select * from users where alias like %alias%

    @Query("{'alias': {$regex: ?0}}")
    Flux<User> findByAliasWithPage(String alias, Pageable page); // paging
}
