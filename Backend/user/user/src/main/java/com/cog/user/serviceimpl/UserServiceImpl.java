package com.cog.user.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cog.user.model.Book;
import com.cog.user.model.ERole;
import com.cog.user.model.Role;
import com.cog.user.model.User;
import com.cog.user.repository.RoleRepository;
import com.cog.user.repository.UserRepository;
import com.cog.user.service.UserInterface;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserInterface {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public User getUser(int userId, ERole roleUser) {
		User user = null;
		Role userRole = roleRepository.findByName(roleUser)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isPresent()
				&& userOptional.get().getUserRole().stream().anyMatch(r -> r.getName().equals(userRole.getName()))) {
			user = userOptional.get();
		}
		return user;
	}

	@Override
	public List<Book> searchBooks(String title, String category, String author) {
		//http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/

		@SuppressWarnings("unchecked")
		List<Book> response = restTemplate.getForObject("http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/search?title="
				+ title + "&category=" + category + "&author=" + author, List.class);
		return response;

	}

	@Override
	public Book saveBook(@Valid Book book, int authorId) {
		Book book1 = null;
		User user = this.getUser(authorId, ERole.ROLE_AUTHOR);
		log.info("usre is :" + user);
		if (user != null) {
			book1 = restTemplate.postForObject(
					"http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/author/" + user.getId() + "/books", book, Book.class);
		}
		book1.setAuthorId(user.getId());
		book1.setAuthorName(user.getUserName());

		return book1;
	}

	@Override
	public Book bookUpdate(Book book, int authorId) {

		log.info("!!!!UserService bookUpdate####");

		Book bData = restTemplate.postForObject(
				"http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/api/v1/digitalbooks/author/" + authorId + "/book", book, Book.class);

		return bData;
	}

	@Override
	public ResponseEntity<?> bookBlocking(int bookId, Integer authorId, String status) {
		log.info("*****UserService bookblocking*****");

		String s = restTemplate.postForObject("http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/api/v1/digitalbooks/author/" + authorId + "/book/"
				+ bookId + "?active=" + status, "", String.class);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> bookUnblocking(int bookId, Integer authorId, String status) {
		log.info("!!!!UserService bookUnblocking!!!!");
		String s = restTemplate.postForObject("http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/api/v1/digitalbooks/author/" + authorId + "/book/"
				+ bookId + "?active=" + status, "", String.class);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}

	@Override
	public String subscribeBook(String bookId) {
		log.info("!!!UserService subscribeBook!!!!");
		String s = restTemplate.postForObject("http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/api/v1/digitalbooks/" + bookId + "/subscribe", "",
				String.class);
		return s;
	}

	@Override
	public ResponseEntity<?> allSubscribedBook(String emailId) {
		// TODO Auto-generated method stub
		log.info("###UserService allSubscribedBook###");
		log.info("Email Id : " + emailId + " Author Id : " + userRepository.findEmailIdByUserId(emailId));
		Integer authorId = userRepository.findEmailIdByUserId(emailId);
		if (authorId != null) {
			String s = restTemplate.getForObject(
					"http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/api/v1/digitalbooks/readers/" + authorId + "/books", String.class);
			return new ResponseEntity<>(s, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Not able find the user with the provided email", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> subscribedBook(String emailId, String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> readSubscribedBook(String emailId, String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> cancelSubscribedBook(String emailId, String subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getAllAuthorBooks(int authorId) {
		log.info("!!!UserService allAuthorBooks!!!!");
		User user = getUser(authorId, ERole.ROLE_AUTHOR);
		log.info("user data is :"+user.toString());
		@SuppressWarnings("unchecked")
		List<Book> response = restTemplate.getForObject(
				"http://digitalbook-env.eba-xrp9mdyd.ap-northeast-1.elasticbeanstalk.com/api/v1/digitalbooks/api/v1/digitalbooks/author/" + user.getId() + "/allbooks", List.class);
		
		return response;
	}

}
