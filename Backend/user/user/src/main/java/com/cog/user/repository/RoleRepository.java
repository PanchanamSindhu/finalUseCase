package com.cog.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cog.user.model.ERole;
import com.cog.user.model.Role;

/**
 * 
 * @author sindhu This is RoleRepository which is used for saving role details
 *         and fetching role details from db
 *
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(ERole name);

}
