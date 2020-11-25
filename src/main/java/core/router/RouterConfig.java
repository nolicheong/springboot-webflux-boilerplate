package core.router;


import core.handler.PersonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class RouterConfig {

    private static Logger log = LoggerFactory.getLogger(PersonHandler.class);

//    private final String HEADER = "Authorization";
//    private final String PREFIX = "Bearer ";
//    private final String SECRET = "*S3cret*";

    @Autowired
    private Environment env;

    @Bean
    public RouterFunction<ServerResponse> personRouterFunction(PersonHandler personHandler) {
        return route()
                .path("/clients", router -> router
                        .nest(accept(MediaType.APPLICATION_JSON), r -> r
                                //.GET("/{username}", personHandler::findOne)
                                .GET("", personHandler::findAll)
                        .POST("", personHandler::create)))
//                        .PUT("/{username}", clientHandler::update)
//                        .DELETE("/{username}", clientHandler::delete))

                .filter((req, next) -> {
                    // log.info("toy en el request: "+ req.headers());
                    ServerRequest newRequest = req;
//                    if (handleRequest(newRequest) == false)
//                        return ServerResponse.status(HttpStatus.valueOf(401)).build();
                    return next.handle(req);
                    // if (hasToken(req.headers())) {
                    //         return next.handle(req);
                    // }
                    // else {
                    //         return ServerResponse.status(HttpStatus.FORBIDDEN).build();
                    // }
                })
                .after((ServerRequest request, ServerResponse response) -> {
                    log.info("RESPONSE");
                    log.info("status: " + response.statusCode());
                    log.info("headers: " + response.headers());
                   // log.info("ID aws: " + GlobalConst.awsID);
                    return response;
                })
                .build();
    }
}