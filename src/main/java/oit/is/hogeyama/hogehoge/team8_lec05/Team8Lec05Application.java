package oit.is.hogeyama.hogehoge.team8_lec05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Team8Lec05Application {

	public static void main(String[] args) {
		SpringApplication.run(Team8Lec05Application.class, args);
	}

}
