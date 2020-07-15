/**
 * 
 */
package com.esabatini.twitterapi.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.esabatini.twitterapi.model.TwitterMessage;
import com.esabatini.twitterapi.model.User;
import com.esabatini.twitterapi.repository.TwitterRepository;

/**
 * @author es
 *
 */
@Service
public class TwitterService {

	@Inject
	public TwitterRepository twitterRepository;

	public TwitterMessage save(TwitterMessage twitterMessage) {
		return twitterRepository.save(twitterMessage);
	}
	
	public List<TwitterMessage> getAllMessagesByUserId(Long userId) {
		return StreamSupport
				.stream(twitterRepository.findByUserId(userId).spliterator(), false)
		        .sorted(Comparator.comparing(TwitterMessage::getLocalDateTimeCreated).reversed())
		        .collect(Collectors.toList());
	}

	public List<TwitterMessage> getAllFollowersByUsername(String name) {
		return (List<TwitterMessage>) twitterRepository.findAll();
	}

}
