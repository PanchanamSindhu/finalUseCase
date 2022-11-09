package com.digitalbook.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.digitalbook.entity.Book;
import com.digitalbook.repository.BookRepository;
import com.digitalbook.service.BookService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author sindhu This is BookServiceImpl which is used for running methods from
 *         controller
 */
@Service
@Slf4j
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Override
	public Book saveBook(Book book, Integer authorId) {
		// TODO Auto-generated method stub
		log.info("###BookServiceImplementation - BookCreation###");
		book.setBookAuthorId(authorId);
		return bookRepository.save(book);
	}

	@Override
	public Book getBook(String title) {
		Book book = new Book();
		Optional<Book> bookOptional = bookRepository.findByTitle(title);
		if (bookOptional.isPresent()) {
			book = bookOptional.get();
		}
		return book;
	}

	@Override
	public List<Book> searchBooks(String title, String category, String author) {
		log.info("###BookServiceImplementation - SearchBook###");
		List<Book> listOfBooks = new ArrayList<>();
		List<Book> bookList = bookRepository.findAll();
		log.info("book list count "+bookList.size());
		for(Book b:bookList) {
			if(b.getTitle().equalsIgnoreCase(title) && b.getCategory().toString().equalsIgnoreCase(category)) {
				listOfBooks.add(b);
			}
		}
		return listOfBooks;
	}

	@Override
	public Book bookUpdate(Book book, Integer authorId) {
		log.info("***BookServiceImplementation - BookUpdate**");
		book.setBookAuthorId(authorId);
		final Book updatedEmployee = bookRepository.save(book);
		return updatedEmployee;
		
	}

	@Override
	public ResponseEntity<?> bookBlocking(Integer bookId, Integer authorId) {
		log.info("****BookServiceImplementation - Blocking****");
		this.bookRepository.bookStatusUpdate(bookId, authorId, false);
		return this.bookRepository.bookStatus(bookId, authorId).equals("false")
				? new ResponseEntity<String>("Updated Sucessfully!!", HttpStatus.OK)
				: new ResponseEntity<String>("Failed to update!!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> bookUnblocking(Integer bookId, Integer authorId) {
		Integer i = this.bookRepository.bookStatusUpdate(bookId, authorId, true);
		log.info("***BookServiceImplementation - Unblocking**" + i + " test : "
				+ this.bookRepository.bookStatus(bookId, authorId));
		return this.bookRepository.bookStatus(bookId, authorId).equals("true")
				? new ResponseEntity<String>("Updated Sucessfully!!", HttpStatus.OK)
				: new ResponseEntity<String>("Failed to update!!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public String bookSubscribing(Integer bookId) {
		log.info("***BookServiceImplementation - bookSubscribing****");
		Random random = new Random();
		int num = random.nextInt(100000);
		String formatted = String.format("%05d", num);
		this.bookRepository.bookSubscribe(bookId, formatted, LocalDate.now(), "yes");
		return formatted;
	}

	@Override
	public ResponseEntity<?> fetchAllSubscribedBooks(Integer authorId) {
		log.info("###BookServiceImplementation - FetchAllSubscribeBook###");
		if (this.bookRepository.bookFetchCheck(authorId) != 0) {

			return new ResponseEntity(this.bookRepository.bookFetch(authorId).toString(), HttpStatus.OK);
		} else {
			return new ResponseEntity("No Book match found with the provided details", HttpStatus.OK);
		}
	}

	@Override
	public List<Book> getAllAuthorBooks(int authorId) {
		// TODO Auto-generated method stub
		log.info("author id is :"+authorId);
		List<Book> listOfBooks = new ArrayList<>();
		log.info("inside book controller ");
		List<Book> bookList = bookRepository.findAll();
		log.info("book list length "+bookList.size());
		for(Book b:bookList) {
			if(b.getBookAuthorId().equals(authorId)) {
				log.info("inside for loop :"+b.getBookAuthorId() +":"+ authorId);
				listOfBooks.add(b);
			}
		}
		return listOfBooks;
	}

	

}
