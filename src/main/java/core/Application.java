package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SpringBootApplication
public class Application {

	private static final Log log = LogFactory.getLog(Application.class);

	public static void main(String[] args) {


		SpringApplication.run(Application.class, args);
	}

}
