package com.esabatini.twitterapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.esabatini.twitterapi.model.User;

@SpringBootTest
class TwitterApiApplicationTests {

	@Test
	void contextLoads() {
		User user = User.builder().name("Edoardo").build();
		assertEquals(user.getName(), "Edoardo");		
	}
}
