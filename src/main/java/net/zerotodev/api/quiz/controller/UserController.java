package net.zerotodev.api.quiz.controller;

import lombok.RequiredArgsConstructor;
import net.zerotodev.api.quiz.domain.User;
import net.zerotodev.api.quiz.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public Flux<User> getAllPersons(){
        return userRepository.findAll();
    }
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamAllPerson(){
        return userRepository.findAll();
    }
    @PostMapping
    public Mono<ResponseEntity<User>> createPerson(@RequestBody User user){
        return userRepository.save(user).map(i -> {return ResponseEntity.ok(i);} )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updatePerson(@PathVariable(value = "id")String id,
                                                     @RequestBody User user){
        return userRepository.findById(id).flatMap(i -> {
                    i.setName(user.getName());
                    i.setEmail(user.getEmail());
                    return userRepository.save(i);
                }).map( j -> { return ResponseEntity.ok(j);})
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable(value = "id") String id){
        return userRepository.findById(id)
                .flatMap(i -> userRepository.delete(i).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
