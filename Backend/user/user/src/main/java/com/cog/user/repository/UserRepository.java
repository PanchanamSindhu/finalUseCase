package com.cog.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cog.user.model.User;

/**
 * 
 * @author sindhu This is UserRepository which is used for saving user details
 *         and fetching user details from db
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "select * from users where Email_id=?1", nativeQuery = true)
	User findEmailId(String emailid);

	Optional<User> findByUserName(String username);

	Optional<User> findByEmailId(String emailId);

	Boolean existsByUserName(String username);

	Boolean existsByEmailId(String email);

	@Query(value = "select id from users where Email_id=?1", nativeQuery = true)
	Integer findEmailIdByUserId(String emailid);

}
