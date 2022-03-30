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

import com.example.akselibookstore.domain.User;
import com.example.akselibookstore.domain.UserRepository;

@SpringBootApplication
public class AkselibookstoreApplication {
	
	private static final Logger log = LoggerFactory.getLogger(AkselibookstoreApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AkselibookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner bookDemo(BookRepository brepository, CategoryRepository crepository, UserRepository urepository) {
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
			
			// Create users: admin/admin user/user
			log.info("### CREATING USERS: USER & ADMIN");
			urepository.save(new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER"));
			urepository.save(new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN"));
			int ucount = 0;
			for (User user: urepository.findAll()) {
				log.info(user.toString());
				ucount++;
			}
			log.info("### " + ucount + " USERS EXISTING");
		};
	}
}