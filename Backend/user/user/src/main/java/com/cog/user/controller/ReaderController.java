package com.cog.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cog.user.model.Book;
import com.cog.user.serviceimpl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reader")
@Slf4j
@CrossOrigin("http://localhost:4200")
public class ReaderController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private AuthenticationManager authenticationManager;

	//@PreAuthorize("hasRole('READER')")
	@GetMapping("/home")
	public String home() {
		return "Home of Author";
	}

	//@PreAuthorize("hasRole('READER')")
	@GetMapping({ "/{emailId}/books" })
	public ResponseEntity<?> allSubscribedBook(@PathVariable String emailId) {
		log.info("**********Reader Controller  Fetch All Subscribed books**********");
		return this.userServiceImpl.allSubscribedBook(emailId);
	}

	//@PreAuthorize("hasRole('READER')")
	@GetMapping({ "/{emailId}/books/{subscriptionId}" })
	public ResponseEntity<?> subscribedBook(@PathVariable String emailId, @PathVariable String subscriptionId) {
		log.info("**********Reader Controller  Fetch Subscribed book*********");
		return this.userServiceImpl.subscribedBook(emailId, subscriptionId);
	}

	//@PreAuthorize("hasRole('READER')")
	@GetMapping({ "/{emailId}/books/{subscriptionId}/read" })
	public ResponseEntity<?> readSubscribedBook(@PathVariable String emailId, @PathVariable String subscriptionId) {
		log.info("*********Reader Controller  readSubscribedBook*********");
		return this.userServiceImpl.readSubscribedBook(emailId, subscriptionId);
	}

	//@PreAuthorize("hasRole('READER')")
	@PostMapping({ "/{emailId}/books/{subscriptionId}/cancel-subscription" })
	public ResponseEntity<?> cancelSubscribedBook(@PathVariable String emailId, @PathVariable String subscriptionId) {
		log.info("*********Reader Controller  cancelSubscribedBook*********");
		return this.userServiceImpl.cancelSubscribedBook(emailId, subscriptionId);
	}
}
