package core.handler;

import core.model.User;
import core.repository.ReactivePersonRepository;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Component
public class PersonHandler {

    private static  Logger log = LoggerFactory.getLogger(PersonHandler.class);

    private final ReactivePersonRepository personRepository;

    public PersonHandler(ReactivePersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // /**
    //  * GET ALL
    //  */
    public Mono<ServerResponse> findAll(ServerRequest request) {

        Flux<User> list = personRepository.findAll();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(list, User.class);
    }


    // /**
    //  * GET ITEM
    //  */
    public Mono<ServerResponse> findOne(ServerRequest request) {

        String name = request.pathVariable("name");
        Mono<User> cricketerMono = personRepository.findByUsername(name);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return cricketerMono.flatMap(cricketer ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(cricketer)))
                .switchIfEmpty(notFound);
    }

    // /**
    //  * POST
    //  */
    public Mono<ServerResponse> create(ServerRequest request) {

        Mono<User> bodyData = request.bodyToMono(User.class);
        return bodyData.flatMap(
                body ->
                        ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                                .body(personRepository.save(body), User.class)
        );
    }


}

