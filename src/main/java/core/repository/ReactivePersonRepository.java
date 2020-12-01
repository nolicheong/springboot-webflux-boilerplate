package core.repository;
import core.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface ReactivePersonRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByUsername(String username);

    Flux<User> findByLastname(Mono<String> lastname);



}