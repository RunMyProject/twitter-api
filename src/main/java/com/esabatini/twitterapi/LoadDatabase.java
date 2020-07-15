/**
 * 
 */
package com.esabatini.twitterapi;

import org.springframework.context.annotation.Configuration;

import com.esabatini.twitterapi.model.User;
import com.esabatini.twitterapi.repository.UserRepository;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
/**
 * @author es
 *
 */
@Configuration
public class LoadDatabase {
	
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
	    return args -> {
	      log.info("Preloading " + repository.save(User.builder().name("Edoardo").followers(new HashSet<Long>()).build()));
	      log.info("Preloading " + repository.save(User.builder().name("Sabatini").followers(new HashSet<Long>()).build()));
	    };
    }
}