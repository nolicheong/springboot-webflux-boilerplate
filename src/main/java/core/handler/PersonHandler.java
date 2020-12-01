package core.handler;

import core.model.User;
import core.repository.ReactivePersonRepository;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.http.HttpStatus;

import java.time.Instant;

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

    // /**
    //  *	PUT
    //  */
//    public Mono<ServerResponse> update(ServerRequest request) {
//        String username = String.valueOf(request.pathVariable("username"));
//
//        Client newClient = clientRepository.findByUsername(username);
//
//        //Mono<Client> bodyData = request.bodyToMono(Client.class);
//        Client bodyData = request.bodyToMono(Client.class).block();
//
//        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//
//        if (newClient == null) return notFound;
//
//        log.info("body data: " + bodyData);
//
//        if (bodyData.getFirstname() != null)
//            newClient.setFirstname(bodyData.getFirstname());
//
//        if (bodyData.getLastname() != null)
//            newClient.setLastname(bodyData.getLastname());
//
//        if (bodyData.getDni() != null)
//            newClient.setDni(bodyData.getDni());
//
//        if (bodyData.getEmail() != null)
//            newClient.setEmail(bodyData.getEmail());
//
//        if (bodyData.getPhone() != null)
//            newClient.setPhone(bodyData.getPhone());
//        newClient.setLastUpdatedDate(Instant.now().toString());
//
//
//        try {
//            Client updated = clientRepository.save(newClient);
//            return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(fromObject(updated));
//        } catch (Exception e) {
//            //TODO: handle exception
//            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    // /**
    //  *	DELETE
    //  */
//    public Mono<ServerResponse> delete(ServerRequest request) {
//        String username = String.valueOf(request.pathVariable("username"));
//
//        Client client = clientRepository.findByUsername(username);
//
//        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//
//        if (client == null) return notFound;
//
//        try {
//            clientRepository.delete(client);
//            return ServerResponse.noContent().build();
//        } catch (Exception e) {
//            //TODO: handle exception
//            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

}

