package com.example.logging.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	private List<User> users = new ArrayList<>(Arrays.asList( 
			new User("u1","User1",3,"u1","u1"),
			new User("u2","User2",5,"u2","u2"),
			new User("u3","User3",4,"u3","u3"),
			new User("u4","User4",2,"u4","u4"),
			new User("u5","User5",10,"u5","u5"),
			new User("u6","User6",16,"u6","u6"),
			new User("u7","User7",21,"u7","u7"),
			new User("u8","User8",23,"u8","u8"),
			new User("u9","User9",61,"u9","u9"),
			new User("u10","User10",18,"u10","u10")
			
			));
	
	public List<User> getUsers(){
		return users;
	}
	
	public User getUser(String id) {
		User result=null;
		for (User u: users) {
			if(u.getId().equals(id)) {
				result=u; 
			}
		}
		
		return result;
	}
	
	public User getUserByEmail(String email) {
		User result=null;
		for (User u: users) {
			if(u.getEmail().equals(email)) {
				result=u; 
			}
		}
		
		return result;
	}

	public void addUser(User user) {
		users.add(user);
		
	}
	
}
