package com.digitalbook.entity;

import org.springframework.stereotype.Component;

/**
* 
* @author sindhu
* BookAuthor entity is used for sending book details.
*
*/
@Component
public class BookAuthor {
	
	private Book book;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

}
