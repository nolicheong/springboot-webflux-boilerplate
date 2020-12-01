package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import core.repository.ReactivePersonRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Application {

	private static final Log log = LogFactory.getLog(Application.class);

	@Autowired
	private ReactivePersonRepository personRepository;

	public static void main(String[] args) {


		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {

	}

}
