package com.u3b.server.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.u3b.client.domain.User;

public class UserDao extends ObjectifyDao<User> {

	

	public UserDao() {
		super(User.class);
	}
	
	public User findByGoogleMail(String userMail){
		Objectify ofy = ObjectifyService.begin();

		User user = ofy.query(User.class).filter("email", userMail).get();
		
		return user;
	}

}
