package core.handler;

import core.model.Person;
import core.repository.ReactivePersonRepository;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
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

        Flux<Person> list = personRepository.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(list, Person.class);
    }


    // /**
    //  * GET ITEM
    //  */
//    public Mono<ServerResponse> findOne(ServerRequest request) {
//
//        String username = String.valueOf(request.pathVariable("username"));
//
//        Flux<Person> clientMono = personRepository.findByFirstname(username);
//
//        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//
//        if (clientMono != null) return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(clientMono));
//        return notFound;
//    }

    // /**
    //  * POST
    //  */
    public Mono<ServerResponse> create(ServerRequest request) {
        Person bodyData = request.bodyToMono(Person.class).block();

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String newPassword = passwordEncoder.encode(bodyData.getPassword());
//        bodyData.setPassword(newPassword);

        try {
            Mono<Person> newItem = personRepository.save(bodyData);

            return ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(fromObject(newItem));
        } catch (Exception e) {
            //TODO: handle exception
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


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

