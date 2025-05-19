package ru.shtyrev.branch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BranchServiceApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(BranchServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}
}
