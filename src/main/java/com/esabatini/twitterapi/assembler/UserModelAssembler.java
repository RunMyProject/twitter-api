package com.esabatini.twitterapi.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.esabatini.twitterapi.controller.UserController;
import com.esabatini.twitterapi.model.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

	@Override
	public EntityModel<User> toModel(User user) {
	    return EntityModel.of(user,
	        linkTo(methodOn(UserController.class).one(user.getName())).withSelfRel(),
	        linkTo(methodOn(UserController.class).all()).withSelfRel()
	     );
	}	
}
