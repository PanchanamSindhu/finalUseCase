package com.cog.user.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cog.user.model.Book;
import com.cog.user.model.ERole;
import com.cog.user.model.JwtResponse;
import com.cog.user.model.LogInRequest;
import com.cog.user.model.MessageRespone;
import com.cog.user.model.Role;
import com.cog.user.model.SignUpRequest;
import com.cog.user.model.User;
import com.cog.user.repository.RoleRepository;
import com.cog.user.repository.UserRepository;
import com.cog.user.security.JwtUtils;
import com.cog.user.serviceimpl.UserPrinciple;
import com.cog.user.serviceimpl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:4200")
@RestController
@Slf4j
@RequestMapping("/reg")
public class RegistrationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	//@PreAuthorize("hasRole('READER')")
	@GetMapping("/home")
	public String getData() {
		return "home of Reader";
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody LogInRequest authRequest) throws Exception {
		log.info("Authentication User!!");
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		} catch (Exception ex) {
			return "Invalid username/password";
		}
		return jwtUtils.generateToken(authRequest.getUserName());
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LogInRequest loginUser) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));

		String jwt = jwtUtils.generateToken(loginUser.getUserName());

		log.info("user data: " + loginUser.getPassword());
		log.info("------UserController Login------" + loginUser.toString() + ":" + loginUser.getUserName() + ":"
				+ userRepository.findByUserName(loginUser.getUserName()));
		User uData = userRepository.findByUserName(loginUser.getUserName()).get();

		log.info("udata:" + uData.getUserName() + ":" + loginUser.getUserName());

		if (uData.getUserName().equals(loginUser.getUserName())) {
			log.info("u data" + loginUser.toString());
			UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(),
					userDetails.getUsername(), userDetails.getEmailId(), roles));

		} else {
			log.info("u error####");
			return (ResponseEntity<?>) ResponseEntity.internalServerError();
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUserName(signUpRequest.getUserName()))) {
			return ResponseEntity.badRequest().body(new MessageRespone("Error: Username is already taken!"));
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmailId(signUpRequest.getEmailId()))) {
			return ResponseEntity.badRequest().body(new MessageRespone("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUserName(), signUpRequest.getEmailId(),
				passwordEncoder.encode(signUpRequest.getPassword()));

		// System.out.println("signup reg "+signUpRequest.toString());
		Set<Role> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_READER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				if (role.getName().equals(ERole.ROLE_AUTHOR)) {
					Role authorRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(authorRole);
				} else {
					Role readerRole = roleRepository.findByName(ERole.ROLE_READER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(readerRole);
				}
			});
		}

		user.setUserRole(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageRespone("User registered successfully!"));
	}

	// @PreAuthorize("hasRole('READER')")
	@GetMapping("/search")
	public ResponseEntity<List<Book>> searchBooks(@RequestParam("title") String bookTitle,
			@RequestParam("category") String bookCategory,
			@RequestParam(value = "authorName", required = false) String bookAuthor) {
		ResponseEntity<List<Book>> response;
		log.info("title is :" + bookTitle);
		List<Book> listOfBooks = userServiceImpl.searchBooks(bookTitle, bookCategory, bookAuthor);
		response = new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		return response;
	}

	@PostMapping("/author/{authorId}/books")
	// @PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Integer> saveBook(@PathVariable("authorId") int authorId, @Valid @RequestBody Book book) {
		log.info("book data :" + book);
		User user = userServiceImpl.getUser(authorId, ERole.ROLE_AUTHOR);
		book.setAuthorName(user.getName());
		book.setAuthorUserName(user.getUserName());
		log.info("book is in reg conteoller :" + book.toString());
		Book book1 = userServiceImpl.saveBook(book, authorId);
		int bookId = book1.getId();
		return new ResponseEntity<>(bookId, HttpStatus.CREATED);
	}

	// @PreAuthorize("hasRole('AUTHOR')")
	@PutMapping({ "/author/{authorId}/books" })
	public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable int authorId) {
		ResponseEntity<Book> response;
		log.info("###UserController -A- updateBook####");
		User user = userServiceImpl.getUser(authorId, ERole.ROLE_AUTHOR);
		book.setAuthorName(user.getName());
		book.setAuthorUserName(user.getUserName());
		Book book1 = this.userServiceImpl.bookUpdate(book, authorId);
		response = new ResponseEntity<>(book1, HttpStatus.OK);
		return response;
	}

	@PreAuthorize("hasRole('AUTHOR')")
	@PostMapping({ "/author/{authorId}/book/{bookId}" })
	@ResponseBody
	public ResponseEntity<?> blockBook(@PathVariable int authorId, @PathVariable int bookId,
			@RequestParam("active") String status) {
		if (status.equals("yes")) {
			log.info("!!!!UserController Blocking Book!!!!!!");
			return this.userServiceImpl.bookBlocking(bookId, authorId, status);
		} else {
			log.info("!!!!UserController UnBlocking Book!!!!!!");
			return this.userServiceImpl.bookUnblocking(bookId, authorId, status);
		}
	}

	@GetMapping("/author/{authorId}/allbooks")
	// @PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<List<Book>> getAllAuthorBooks(@PathVariable("authorId") int authorId) {
		ResponseEntity<List<Book>> response;
		log.info("user controller#####" + authorId);
		List<Book> listOfBooks = userServiceImpl.getAllAuthorBooks(authorId);
		response = new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		return response;
	}

	// @PreAuthorize("hasRole('READER')")
	@GetMapping("/book/{bookId}/subscribe")
	public String subscribeBook(@PathVariable String bookId) {
		ResponseEntity<?> response;
		log.info("**********Reader Controller Subscribe book**********");
		String s= this.userServiceImpl.subscribeBook(bookId);
		log.info("sunscripbrtion id "+s);
		response= new  ResponseEntity<>(s, HttpStatus.OK);
		return s;
	
	}
}
