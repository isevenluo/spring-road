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

@SpringBootApplication
public class SpringRoad01Application {

	@Bean
	MapReactiveUserDetailsService mapReactiveUserDetailsService () {
		return new MapReactiveUserDetailsService(
				User.withUsername("admin").
						password(PasswordEncoderFactories.createDelegatingPasswordEncoder().
								encode("admin")).roles("USER").build());

	}

	@Bean
	HealthIndicator healthIndicator() {
		return () -> Health.up().withDetail("application","i am so good").withDetail(
				"error", "not exists").build();
	}

	@Bean
	RouterFunction<ServerResponse> routes(UserDao userDao) {
		return RouterFunctions.route(GET("/users"), serverRequest -> ok().body(userDao.findAll(),Users.class));

	}



	public static void main(String[] args) {
		SpringApplication.run(SpringRoad01Application.class, args);
	}



}

@Component
class DateWriter implements ApplicationRunner {

	private final static Logger logger = LoggerFactory.getLogger(DateWriter.class);

	private UserDao userDao;

	private DatabaseClient databaseClient;

	public DateWriter(UserDao userDao, DatabaseClient databaseClient) {
		this.userDao = userDao;
		this.databaseClient = databaseClient;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 建表语句
		List<String> statements = Arrays.asList(
				"DROP TABLE IF EXISTS USERS;",
				"CREATE TABLE IF NOT EXISTS USERS ( id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL);"
		);
		statements.forEach(sql -> databaseClient.sql(sql).fetch().rowsUpdated()
				.doOnSuccess(count -> logger.info("Statement created , rows = {}",count)).doOnError(
						error -> logger.error("got error : {}",error.getMessage(),error)).subscribe());
		Flux.just("sevenluo","tonyzhu","jameschen").flatMap(
				name -> userDao.save(new Users(null,name))).subscribe(
					user -> logger.info("User saved:{}",user));

	}
}

interface UserDao extends ReactiveCrudRepository<Users,String> {

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Users {
	@Id
	private Integer id;
	private String name;
}
