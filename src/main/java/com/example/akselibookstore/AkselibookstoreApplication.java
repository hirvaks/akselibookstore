package com.example.akselibookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.akselibookstore.domain.Book;
import com.example.akselibookstore.domain.BookRepository;
import com.example.akselibookstore.domain.Category;
import com.example.akselibookstore.domain.CategoryRepository;

@SpringBootApplication
public class AkselibookstoreApplication {
	
	private static final Logger log = LoggerFactory.getLogger(AkselibookstoreApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AkselibookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner bookDemo(BookRepository brepository, CategoryRepository crepository) {
		return (args) -> {
			log.info("### CREATING DEFAULT CATEGORIES");
			crepository.save(new Category("Fantasy"));
			crepository.save(new Category("Sci-Fi"));
			crepository.save(new Category("Mystery"));
			crepository.save(new Category("Thriller"));
			crepository.save(new Category("Romance"));
			crepository.save(new Category("Westerns"));
			crepository.save(new Category("Dystopian"));
			crepository.save(new Category("Contemporary"));
			log.info("### SAVED " + crepository.count() + " CATEGORIES");
			log.info("### FETCHING ALL AVAILABLE CATEGORIES");
			int ccount = 0;
			for (Category category: crepository.findAll()) {
				log.info(category.toString());
				ccount++;
			}
			log.info("### " + ccount + " CATEGORIES AVAILABLE");
			
			
			log.info("### ADDING TEST BOOKS");
			brepository.save(new Book( "TestiKirja1", "Akseli", 2022, "1234567890", 10, crepository.findByName("Fantasy").get(0)));
			brepository.save(new Book("TestiKirja2", "Akseli", 2022, "0987654321", 10, crepository.findByName("Sci-Fi").get(0)));
			log.info("### SAVED " + brepository.count() + " BOOKS");
			log.info("### FETCHING ALL AVAILABLE BOOKS");
			int bcount = 0;
			for (Book book: brepository.findAll()) {
				log.info(book.toString());
				bcount++;
			}
			log.info("### " + bcount + " BOOKS AVAILABLE");
		};
	}
}