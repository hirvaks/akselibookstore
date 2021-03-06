package com.example.akselibookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.akselibookstore.domain.Book;
import com.example.akselibookstore.domain.BookRepository;
import com.example.akselibookstore.domain.CategoryRepository;

@Controller
public class BookController {
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CategoryRepository crepository; 
	/*
	 "/edit/X" endpointteja ei enää tarvitse,
	 editbook.html ohjaus korjattu
	 th:action="@{save}" -> th:action="@{../save}"
	 */
	
	@RequestMapping(value="/login")
    public String login() {	
        return "login";
    }
	
    @RequestMapping(value= {"/", "/booklist", "/edit/booklist"})
    public String bookList(Model model) {
    	System.out.println("### GET BOOKLIST");
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }
    
    // RESTful service to get all books
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public @ResponseBody List<Book> bookListRest() {	
        return (List<Book>) repository.findAll();
    }    

	// RESTful service to get a book by id
    @RequestMapping(value="/book/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId) {	
    	return repository.findById(bookId);
    }  
     
    @RequestMapping(value = "/add")
    public String addBook(Model model){
    	System.out.println("### ADDING A NEW BOOK");
    	model.addAttribute("book", new Book());
    	model.addAttribute("categories", crepository.findAll());
        return "addbook";
    }     
    
    @RequestMapping(value = {"/save", "/edit/save"}, method = RequestMethod.POST)
    public String save(Book book){
    	System.out.println("### SAVING BOOK");
        repository.save(book);
        System.out.println("### SAVED BOOK: " + book);
        return "redirect:booklist";
    }    

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
    	System.out.println("### DELETING: " + repository.findById(bookId));
    	repository.deleteById(bookId);
    	if (repository.findById(bookId) != null) {
    		System.out.println("### BOOK DELETED");
    	}
        return "redirect:../booklist";
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") Long bookId, Model model){
    	System.out.println("### EDITING BOOK: " + repository.findById(bookId));
    	Book bookEdit = repository.findById(bookId).get();
    	// model.addAttribute("ebook", bookEdit);
    	model.addAttribute("book", bookEdit);
    	model.addAttribute("categories", crepository.findAll());
    	return "editbook";
    }
}