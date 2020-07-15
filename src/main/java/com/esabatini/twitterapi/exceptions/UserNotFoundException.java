package com.esabatini.twitterapi.exceptions;
/**
 * @author es
 *
 */
public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserNotFoundException(String name) {
	    super("Spring Advise: Could not find username \'" + name + "\'.");
	}
}
