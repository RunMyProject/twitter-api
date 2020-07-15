/**
 * 
 */
package com.esabatini.twitterapi.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.esabatini.twitterapi.repository.UserRepository;
import com.esabatini.twitterapi.model.User;

/**
 * @author es
 *
 */
@Service
public class UserService {

	@Inject
	public UserRepository userRepository;

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findByName(String name) {
    	return(userRepository.findByName(name));
    }

    public Iterable<User> findFollowersByUser(User user) {
		return userRepository.findAllById(user.getFollowers());
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
