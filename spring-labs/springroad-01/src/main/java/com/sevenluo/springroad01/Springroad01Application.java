package com.sevenluo.springroad01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;

import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author sevenluo
 */
@SpringBootApplication
public class Springroad01Application {

    @Bean
    MapReactiveUserDetailsService users() {
        return new MapReactiveUserDetailsService(User.withUsername("admin").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin")).roles("USER").build());
    }

    @Bean
    HealthIndicator healthIndicator() {
        return () -> Health.up().withDetail("app", "i am so good").
                withDetail("error","开什么玩笑，老夫怎么可能有错！").build();
    }

    @Bean
    RouterFunction<ServerResponse> routes (UsersDao usersDao) {
        return RouterFunctions.route(GET("/users"),serverRequest -> ok().body(usersDao.findAll(),Users.class));

    }

    public static void main(String[] args) {
        SpringApplication.run(Springroad01Application.class, args);
    }

}

@Component
class DataWriter implements ApplicationRunner {

    private Logger log = LoggerFactory.getLogger(DataWriter.class);

    private DatabaseClient client;

    private UsersDao usersDao;

    public DataWriter(DatabaseClient client, UsersDao usersDao) {
        this.client = client;
        this.usersDao = usersDao;
    }

    @Override
    public void run(ApplicationArguments args) {

        List<String> statements = Arrays.asList(
                "DROP TABLE IF EXISTS USERS;",
                "CREATE TABLE IF NOT EXISTS USERS ( id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL);");

        statements.forEach(sql -> client.sql(sql).fetch().rowsUpdated()
                .doOnSuccess(count -> log.info("Schema created, rows updated: {}", count))
                .doOnError(error -> log.error("got error : {}",error.getMessage(),error))
                .subscribe()
        );

        Flux.just("sevenluo","tonyzhu","jameschen").flatMap(name -> usersDao.save(new Users(null,name))).subscribe(user -> log.info("User saved: {}",user));


    }
}

interface UsersDao extends ReactiveCrudRepository<Users, String> {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Users {
    @Id
    private Integer id;
    private String name;

}
