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

    @Autowired
    private Environment env;

    @Bean
    public RouterFunction<ServerResponse> personRouterFunction(PersonHandler personHandler) {
        return route()
                .path("/clients", router -> router
                        .nest(accept(MediaType.APPLICATION_JSON), r -> r
                                .GET("/{name}", personHandler::findOne)
                                .GET("", personHandler::findAll)
                        .POST("", personHandler::create)))

                .filter((req, next) -> {
                    ServerRequest newRequest = req;

                    return next.handle(req);

                })
                .after((ServerRequest request, ServerResponse response) -> {
                    log.info("RESPONSE");
                    log.info("status: " + response.statusCode());
                    log.info("headers: " + response.headers());
                    return response;
                })
                .build();
    }
}