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
//					load.setLoad(new Random().nextInt(10));
//					loadRepository.save(load);
//				});

//		loadRepository.deleteAll();
//
//		LocalDate start = LocalDate.of(2025, 1, 1);
//
//		LocalDate end = LocalDate.now();
//
//		while(true) {
//			if (start.equals(end)) {
//				return;
//			}
//
//			for (int i = 8; i <= 18; i++) {
//				LocalTime time = LocalTime.of(i, 0, 0);
//
//				Random random = new Random();
//
//				Load load102 = Load.builder()
//						.date(start)
//						.time(time)
//						.branchId(102L)
//						.load(random.nextInt(10))
//						.build();
//
//				Load load104 = Load.builder()
//						.date(start)
//						.time(time)
//						.branchId(104L)
//						.load(random.nextInt(10))
//						.build();
//
//				Load load105 = Load.builder()
//						.date(start)
//						.time(time)
//						.branchId(105L)
//						.load(random.nextInt(10))
//						.build();
//
//				loadRepository.saveAll(List.of(load102, load104, load105));
//			}
//
//			start = start.plusDays(1);
//		}

	}
}
