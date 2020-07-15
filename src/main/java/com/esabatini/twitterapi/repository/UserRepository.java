/**
 * 
 */
package com.esabatini.twitterapi.repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.esabatini.twitterapi.model.User;

/**
 * @author es
 *
 */
@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findByName(String name);
	
	default Map<Long, User> findAllMap() {
		return StreamSupport.stream(findAll().spliterator(), false).collect(Collectors.toMap(User::getId, v -> v));
    }

}

