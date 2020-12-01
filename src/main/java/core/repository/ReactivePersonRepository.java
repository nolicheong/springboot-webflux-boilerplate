package core.repository;
import core.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repository interface to manage {@link User} instances.
 *
 * @author Mark Paluch
 */
public interface ReactivePersonRepository extends ReactiveCrudRepository<User, String> {

    /**
     * Derived query selecting by {@code lastname}.
     *
     * @param username
     * @return
     */
     Mono<User> findByUsername(String username);

    /**
     * String query selecting one entity.
     *
     * @param lastname
     * @return
     */
//    @Query("{ 'firstname': ?0, 'lastname': ?1}")
//    Mono<Person> findByFirstnameAndLastname(String firstname, String lastname);

    /**
     * Derived query selecting by {@code lastname}. {@code lastname} uses deferred resolution that does not require
     * blocking to obtain the parameter value.
     *
     * @param lastname
     * @return
     */
    Flux<User> findByLastname(Mono<String> lastname);

    /**
     * Derived query selecting by {@code firstname} and {@code lastname}. {@code firstname} uses deferred resolution that
     * does not require blocking to obtain the parameter value.
     *
     * @param firstname
     * @param lastname
     * @return
     */
    // Mono<Person> findByFirstnameAndLastname(Mono<String> firstname, String lastname);

    /**
     * Use a tailable cursor to emit a stream of entities as new entities are written to the capped collection.
     *
     * @return
     */
//    @Tailable
//    Flux<Person> findWithTailableCursorBy();

}