package com.digitalbook.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Book;

/**
 * 
 * @author sindhu This is BookRepository which is used for saving book details
 *         and fetching book details from db
 *
 */

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {

	Optional<Book> findByTitle(String title);

	@Modifying
	@Query(value = "UPDATE book_data SET active=?3 WHERE id=?1 and book_author_id=?2", nativeQuery = true)
	Integer bookStatusUpdate(Integer bookid, Integer authorid, boolean status);

	@Query(value = "select active from book_data where id=?1 and book_author_id=?2", nativeQuery = true)
	String bookStatus(Integer bookid, Integer authorid);

	@Modifying
	@Query(value = "UPDATE book_data SET book_subscribe=?4,book_subscribe_id=?2,book_subscribe_date=?3 WHERE id=?1", nativeQuery = true)
	void bookSubscribe(Integer bookId, String formatted, LocalDate localDate, String status);

	@Query(value = "select count(*) from book_data where book_author_id=?1", nativeQuery = true)
	Integer bookFetchCheck(Integer Id);

	@Query(value = "select * from book_data where book_author_id=?1", nativeQuery = true)
	List<Book> bookFetch(Integer Id);

	@Query(value = "select * from book_data where category=?1 and title=?2 and author_name=?3", nativeQuery = true)
	Book bookSearch(String category, String title, String author);

	@Query(value = "select count(*) from book_data where category=?1 and title=?2 and author_name=?3", nativeQuery = true)
	Integer bookSearchCount(String category, String title, String author);
}
