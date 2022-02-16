package com.example.akselibookstore;

import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.akselibookstore.domain.Book;
import com.example.akselibookstore.domain.BookRepository;

@SpringBootApplication
public class AkselibookstoreApplication {
	
	private static final Logger log = LoggerFactory.getLogger(AkselibookstoreApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AkselibookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner bookDemo(BookRepository repository) {
		return (args) -> {
			log.info("save a couple of books");
			repository.save(new Book(null, "TestiKirja1", "Akseli", 2022, "1234567890", 10));
			repository.save(new Book(null, "TestiKirja2", "Akseli", 2022, "0987654321", 10));
			
			log.info("fetch all books");
			for (Book book: repository.findAll()) {
				log.info(book.toString());
			}
		};
	}
}