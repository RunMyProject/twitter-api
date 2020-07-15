/**
 * 
 */
package com.esabatini.twitterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author es
 *
 */
@SpringBootApplication
@ComponentScan(basePackages ="com.esabatini.twitterapi")
@EnableAutoConfiguration
public class TwitterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApiApplication.class, args);
	}
	
}
