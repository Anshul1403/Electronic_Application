package com.Electronic.Store.Electronic.Store.services;

import java.util.List;

import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.UserDto;
import com.Electronic.Store.Electronic.Store.entities.User;

public interface UserService {
	
	//create
	
	UserDto createUser(UserDto userDto);
	
	//update
	
	UserDto updateUser(UserDto userDto, String userId);
	
	//delete
	
	void deleteuser(String userId);
	
	
	//get all users
	
	PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy, String sortDir);
	
    //get single user by id
	
	UserDto getUserById(String userId);
	
	//get single user by email
	
	UserDto getUserByEmail(String email);
	
	//search user
	
	List<UserDto> searchUser(String keyword);
	
	

}
