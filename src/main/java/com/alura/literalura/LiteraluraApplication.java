package com.alura.literalura;

import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.repository.PersonRepository;
import com.alura.literalura.view.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.alura.literalura.model"})
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Menu menu = new Menu();
		menu.deployMenu(bookRepository, personRepository);
	}
}
