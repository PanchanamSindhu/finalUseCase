package com.cog.user.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.cog.user.model.Book;
import com.cog.user.model.ERole;
import com.cog.user.model.User;

public interface UserInterface {
	
	User getUser(int userId, ERole roleUser);

	List<Book> searchBooks(String title, String category, String author);

	Book saveBook(@Valid Book book, int authorId);

	Book bookUpdate(Book book, int authorId);

	ResponseEntity<?> bookBlocking(int bookId, Integer authorId, String status);

	ResponseEntity<?> bookUnblocking(int bookId, Integer authorId, String status);

	String subscribeBook(String bookId);

	ResponseEntity<?> allSubscribedBook(String emailId);

	ResponseEntity<?> subscribedBook(String emailId, String subscriptionId);

	ResponseEntity<?> readSubscribedBook(String emailId, String subscriptionId);

	ResponseEntity<?> cancelSubscribedBook(String emailId, String subscriptionId);

	List<Book> getAllAuthorBooks(int authorId);

}
