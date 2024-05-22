package com.Electronic.Store.Electronic.Store.services.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import ch.qos.logback.classic.spi.LoggerContextListener;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.UserDto;
import com.Electronic.Store.Electronic.Store.entities.User;
import com.Electronic.Store.Electronic.Store.exceptions.ResourceNotFoundException;
import com.Electronic.Store.Electronic.Store.repositories.UserRepository;
import com.Electronic.Store.Electronic.Store.services.UserService;

import lombok.experimental.PackagePrivate;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Value("${user.profile.image.path}")
	private String imageUploadPath;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		//userdto --> entity
		
		User user = dtotoEntity(userDto);
       
		User savedUser = userRepository.save(user);
		
		//entity -->userdto
		
		UserDto newdto = entityToDto(savedUser);
		
		
		return newdto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		// TODO Auto-generated method stub
		
	User user =	userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
		 user.setName(userDto.getName());
		 user.setAbout(userDto.getAbout());
		 user.setGender(userDto.getGender());
		 user.setPassword(userDto.getPassword());
		 user.setImageName(userDto.getImageName());
		 
		 // save user
		User updatedUser = userRepository.save(user);
	UserDto updateDto = entityToDto(updatedUser);
		
		return updateDto;
	}

	@Transactional
	@Override
	public void deleteuser(String userId) {
		
		User user =	userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

		String fullPath = imageUploadPath + user.getImageName();

		try {
			Path path = Paths.get(fullPath);
			Files.delete(path);
		}
		catch (IOException e){
			logger.info("File not found to delete");
		}


		// delete
		userRepository.delete(user);
		
	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		
		Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<User> page = userRepository.findAll(pageable);
		List<User> users = page.getContent();
	List<UserDto> dtolist = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
	PageableResponse<UserDto> response  = new PageableResponse<>();
	response.setContent(dtolist);
	response.setPageNumber(page.getNumber());
	response.setPageSize(page.getSize());
	response.setTotalElements(page.getTotalElements());
	response.setTotalPages(page.getTotalPages());
	response.setLastpage(page.isLast());
	
	
		return response;
	}

	@Override
	public UserDto getUserById(String userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
		
		return entityToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User is not found with this email id !!!"));
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findByNameContaining(keyword);
		List<UserDto> dtolist = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		return dtolist;
	}
	
	
	public User dtotoEntity(UserDto userDto) {
		
		return mapper.map(userDto, User.class);
		
	}
	
	public UserDto entityToDto(User saveduser) {
		
		
		return mapper.map(saveduser, UserDto.class);
				}

}
