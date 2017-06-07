package org.github.login.jquery.spring.controllers;

import java.io.Serializable;

import org.github.login.jquery.spring.entity.Data;
import org.github.login.jquery.spring.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vladimir_antin on 7.6.17..
 */

@RestController
public class AuthController {
	
	/**
	 * @param userLogin - here may be the User, in this case
	 * 			should be take email and password of User
	 * 			and class UserLogin not needed
	 */
	@RequestMapping(value="login", method = RequestMethod.POST)
	public User login(@RequestBody UserLogin userLogin){
		for (User user : Data.users.values()) {
			if(user.getEmail().equals(userLogin.username) && 
					user.getPassword().equals(userLogin.password)){
				return user;
			}
		}
		return null;
	}
	@RequestMapping(value = "api/me:{token}") //method get
	public ResponseEntity<User> me(@PathVariable String token){
		// user.token = "myapp_"+id
		System.out.println(token);
		String id = token.substring(6);
		if(Data.users.containsKey(id)){
			return new ResponseEntity<User>(Data.users.get(id), HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "api/check:{token}") //method get
	public ResponseEntity<String> check(@PathVariable String token){
		// user.token = "myapp_"+id
		String id = token.substring(6);
		if(Data.users.containsKey(id)){
			User user = Data.users.get(id);
			String message = "No one";
			if(user.getRole().equals("admin")){
				message="I am "+user.getRole()+"and i have privileges for all services.";
			}else if(user.getRole().equals("owner")){
				message="I am "+user.getRole()+" and i dont have privileges for some services.";
			}else if(user.getRole().equals("bidder")){
				message="I am "+user.getRole()+" and i dont have privileges for most services.";
			}
			return new ResponseEntity<String>(message, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
	}


	private static class UserLogin{
		public String username;
		public String password;
	}

	private static class UserID{
		public String token;
	}
}
