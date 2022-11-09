package com.digitalbook.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 
 * @author sindhu Book entity is used for declaring the details of book and
 *         validation of book details
 *
 */

@Component
@Entity
@Table(name = "book_data")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String logo;
	private String title;
	private Integer bookAuthorId;
	@Enumerated(EnumType.STRING)
	private BookCategory category;
	private BigDecimal price;
	private String authorUserName;
	private String authorName;
	private String publisher;
	private String content;
	private Boolean active;
	private String bookSubscribe;
	private Date bookSubscribeDate;
	private String bookSubscribeId;

	@DateTimeFormat(style = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate publishedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getBookAuthorId() {
		return bookAuthorId;
	}

	public void setBookAuthorId(Integer bookAuthorId) {
		this.bookAuthorId = bookAuthorId;
	}

	public BookCategory getCategory() {
		return category;
	}

	public void setCategory(BookCategory category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAuthorUserName() {
		return authorUserName;
	}

	public void setAuthorUserName(String authorUserName) {
		this.authorUserName = authorUserName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getBookSubscribe() {
		return bookSubscribe;
	}

	public void setBookSubscribe(String bookSubscribe) {
		this.bookSubscribe = bookSubscribe;
	}

	public Date getBookSubscribeDate() {
		return bookSubscribeDate;
	}

	public void setBookSubscribeDate(Date bookSubscribeDate) {
		this.bookSubscribeDate = bookSubscribeDate;
	}

	public String getBookSubscribeId() {
		return bookSubscribeId;
	}

	public void setBookSubscribeId(String bookSubscribeId) {
		this.bookSubscribeId = bookSubscribeId;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", logo=" + logo + ", title=" + title + ", bookAuthorId=" + bookAuthorId
				+ ", category=" + category + ", price=" + price + ", authorUserName=" + authorUserName + ", authorName="
				+ authorName + ", publisher=" + publisher + ", content=" + content + ", active=" + active
				+ ", bookSubscribe=" + bookSubscribe + ", bookSubscribeDate=" + bookSubscribeDate + ", bookSubscribeId="
				+ bookSubscribeId + ", publishedDate=" + publishedDate + "]";
	}

	

	
	

}
