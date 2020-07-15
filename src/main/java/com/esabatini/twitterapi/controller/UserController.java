/**
 * 
 */
package com.esabatini.twitterapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.esabatini.twitterapi.Setup;
import com.esabatini.twitterapi.assembler.UserModelAssembler;
import com.esabatini.twitterapi.exceptions.UserNotFoundException;
import com.esabatini.twitterapi.model.CheckResult;
import com.esabatini.twitterapi.model.TwitterMessage;
import com.esabatini.twitterapi.model.User;
import com.esabatini.twitterapi.repository.UserRepository;
import com.esabatini.twitterapi.service.TwitterService;
import com.esabatini.twitterapi.service.UserService;

import io.swagger.annotations.Api;

/**
 * @author es
 *
 */
@Api(tags = {"User Controller manages users."})
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	UserController(UserRepository userRepository, UserModelAssembler userModelAssembler) {
	    this.userRepository = userRepository;
	    this.userModelAssembler = userModelAssembler;
	}
	
	private UserModelAssembler userModelAssembler;
	private UserRepository userRepository;
    
	@Inject
    private UserService userService;

    @Inject
    private TwitterService twitterService;
        
	@ResponseBody
    @GetMapping(path = "/")
	ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
	}
    
    @GetMapping("/allLinked")
    public CollectionModel<EntityModel<User>> all() {
      List<EntityModel<User>> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
    		  .map(userModelAssembler::toModel)
    		  .collect(Collectors.toList());
      return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @GetMapping(path = "/getUser/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name) {
    	Optional<User> user = userService.findByName(name);
    	if(!user.isEmpty()) return ResponseEntity.ok(user.get());
    	return ResponseEntity.notFound().build();
    }

	@GetMapping("/{name}")
	public EntityModel<User> one(@PathVariable String name) {
	  return userModelAssembler.toModel(userRepository.findByName(name).orElseThrow(() -> new UserNotFoundException(name)));
	}

    @ResponseBody
    @PutMapping(value = "/save")
    public ResponseEntity<User> save(@RequestBody User user) {
		return ResponseEntity.ok(userService.save(user));
    }
    
    @ResponseBody
    @PostMapping(path = "/{name}/message")
    public ResponseEntity<CheckResult> postMessage(@PathVariable String name, @RequestBody String message) {
    	if(message.length()>Setup.MSG_MAX_CHARS) 
    		return ResponseEntity.ok(CheckResult.builder()
    				.message("the message \'" + message + "\' is too long!")
    				.code(Setup.KO)
    				.build());
    	Optional<User> user = userService.findByName(name);
    	if(user.isEmpty()) user = Optional.ofNullable(userService.save(User.builder().name(name).build()));
    	TwitterMessage twitterMessage = twitterService.save(TwitterMessage.builder()
				.userId(user.get().getId())
				.name(user.get().getName())
				.localDateTimeCreated(LocalDateTime.now())
				.message(message)
				.build());
    	if(twitterMessage!=null) return ResponseEntity.ok(CheckResult.builder()
				.message("the message \'" + message + "\' saved.")
				.code(Setup.MSG_SAVED)
				.build());
    	return ResponseEntity.badRequest().build();
    }

    @ResponseBody
    @GetMapping(path = "/wall/{name}")
    public ResponseEntity<List<TwitterMessage>> getWall(@PathVariable String name) {
    	Optional<User> user = userService.findByName(name);
    	if(user.isEmpty()) return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(twitterService.getAllMessagesByUserId(user.get().getId()));
    }
    
    @ResponseBody
    @DeleteMapping(value = "/delete/{name}")
    public ResponseEntity<String> delete(@PathVariable String name) {
    	Optional<User> user = userService.findByName(name);    	
    	if(!user.isEmpty()) {
    		userService.delete(user.get());
            return ResponseEntity.ok("the name \'" + name + "\' was deleted.");
    	}
        return ResponseEntity.ok("the name \'" + name + "\' doesn't exist!");
    }

    @ResponseBody
    @PutMapping(path = "/{name}/follow")
    public ResponseEntity<CheckResult> follow(@PathVariable String name, @RequestBody String nameToFollow) {

    	Optional<User> user = userService.findByName(name);
    	if(user.isEmpty()) return ResponseEntity.badRequest().build();
    	
    	Optional<User> userToFollow = userService.findByName(nameToFollow);
    	if(userToFollow.isEmpty()) return ResponseEntity.ok(CheckResult.builder()
    				.message("the name \'" + nameToFollow + "\' doesn't exist!")
    				.code(Setup.KO)
    				.build());

    	user.get().getFollowers().add(userToFollow.get().getId());
    	user = Optional.ofNullable(userService.save(user.get()));

    	if(!user.isEmpty()) return ResponseEntity.ok(CheckResult.builder()
				.message("the follower \'" + nameToFollow + "\' saved.")
				.code(Setup.MSG_SAVED)
				.build());
    	
    	return ResponseEntity.badRequest().build();
    }

	@GetMapping("/followers/{name}")
	public Iterable<User> followers(@PathVariable String name) {
	  EntityModel<User> user = userModelAssembler.toModel(userRepository.findByName(name).orElseThrow(() -> new UserNotFoundException(name)));
	  return userService.findFollowersByUser(user.getContent());
	}

	@GetMapping("/timeline/{name}")
	public Iterable<TwitterMessage> timeline(@PathVariable String name) {
	  EntityModel<User> user = userModelAssembler.toModel(userRepository.findByName(name).orElseThrow(() -> new UserNotFoundException(name)));
	  Iterable<User> userList = userService.findFollowersByUser(user.getContent());

	  List<Long> ids = StreamSupport
		  .stream(userList.spliterator(), false)
          .map(User::getId)
          .collect(Collectors.toList());
	  
	  return StreamSupport
		  	.stream(ids.spliterator(), false)
			.map(twitterService::getAllMessagesByUserId)
            .flatMap(Collection::stream)
	        .sorted(Comparator.comparing(TwitterMessage::getLocalDateTimeCreated).reversed())
			.collect(Collectors.toList());
	}

 }