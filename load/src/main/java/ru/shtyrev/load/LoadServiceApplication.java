package ru.shtyrev.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import ru.shtyrev.load.dto.LoadPredictionResponseDto;
import ru.shtyrev.load.entity.Load;
import ru.shtyrev.load.repository.LoadRepository;
import ru.shtyrev.load.service.LoadService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class LoadServiceApplication implements CommandLineRunner {
	@Autowired
	LoadRepository loadRepository;

	public static void main(String[] args) {
		SpringApplication.run(LoadServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		loadRepository.findAll().forEach(load -> {
//					load.setTime(load.getTime().minusHours(5));
//					loadRepository.save(load);
//				});

	}
}
