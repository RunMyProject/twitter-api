/**
 * 
 */
package com.esabatini.twitterapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.esabatini.twitterapi.model.TwitterMessage;

/**
 * @author es
 *
 */
@RepositoryRestResource
public interface TwitterRepository extends CrudRepository<TwitterMessage, Long> {

	public TwitterMessage findByMessage(String messsage);
	public Iterable<TwitterMessage> findByUserId(Long userId);
}

