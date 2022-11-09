package com.digitalbook.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookAuthor;
import com.digitalbook.entity.ERole;
import com.digitalbook.entity.User;
import com.digitalbook.service.BookService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author sindhu This is BookController which run methods for book api
 */

@RestController
@RequestMapping("/api/v1/digitalbooks")
@Slf4j
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/home")
	public String home() {
		return "Book Home";
	}

	@GetMapping("/search")
	public List<Book> searchBooks(@RequestParam("title") String bookTitle,
			@RequestParam("category") String bookCategory, @RequestParam("author") String bookAuthor) {

		List<Book> listOfBooks = bookService.searchBooks(bookTitle, bookCategory, bookAuthor);
		// response = new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		return listOfBooks;
	}

	@PostMapping("/author/{authorId}/books")
	public Book saveBook(@PathVariable("authorId") int authorId, @Valid @RequestBody Book book) {
		log.info("###BookController - A- createBook####");
		Book book1 = bookService.saveBook(book, authorId);

		return book1;

	}

	@PostMapping({ "/author/{authorId}/book" })
	public Book upadteBook(@RequestBody Book book, @PathVariable Integer authorId) {

		log.info("###BookController - A- editBook####");
		return this.bookService.bookUpdate(book, authorId);
	}

	@PostMapping({ "/author/{authorId}/book/{bookId}" })
	@ResponseBody
	public ResponseEntity<?> blockBook(@PathVariable Integer authorId, @PathVariable Integer bookId,
			@RequestParam("active") String status) {
		if (status.equals("yes")) {
			log.info("****BookController Blocking Book **");
			return this.bookService.bookBlocking(bookId, authorId);
		} else {
			log.info("***BookController UnBlocking Book ***");
			return this.bookService.bookUnblocking(bookId, authorId);
		}
	}

	@PostMapping({ "/{bookId}/subscribe" })
	public String subscribeBook(@PathVariable Integer bookId) {
		log.info("###BookController - Subscribing ####");
		return this.bookService.bookSubscribing(bookId);
	}

	@ResponseBody
	@GetMapping({ "/readers/{authorId}/books" })
	public ResponseEntity<?> fetchSubscribeBooks(@PathVariable Integer authorId) {
		log.info("###BookController - Fetch All Subscribe Books####");
		return new ResponseEntity(this.bookService.fetchAllSubscribedBooks(authorId), HttpStatus.OK);
	}

	@GetMapping("/author/{authorId}/allbooks")
	public List<Book> getAllAuthorBooks(@PathVariable("authorId") int authorId) {
		log.info("inside Book controller all books");

		List<Book> listOfBooks = bookService.getAllAuthorBooks(authorId);

		return listOfBooks;
	}

}
