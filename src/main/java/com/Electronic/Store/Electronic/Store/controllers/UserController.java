package com.Electronic.Store.Electronic.Store.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Electronic.Store.Electronic.Store.dtos.ApiResponseMessage;
import com.Electronic.Store.Electronic.Store.dtos.ImageResponse;
import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.UserDto;
import com.Electronic.Store.Electronic.Store.services.FileService;
import com.Electronic.Store.Electronic.Store.services.UserService;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	//create
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto userDto1 = userService.createUser(userDto);
		
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
	}
	
	
	//update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,@Valid @RequestBody UserDto userDto){
		
		UserDto userDto2 = userService.updateUser(userDto, userId);
		
		return new ResponseEntity<>(userDto2, HttpStatus.OK);
	}
	
	 //
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId){
		
		userService.deleteuser(userId);
		ApiResponseMessage errMessage  = ApiResponseMessage.builder().message("User has been deleted").success(true).build();
		return new ResponseEntity<>(errMessage, HttpStatus.OK);
	}
	
	
	@GetMapping()
	public ResponseEntity<PageableResponse<UserDto>> getAllUser(
			@RequestParam(value ="pageNumber", defaultValue="0",required = false) int pageNumber,
			@RequestParam(value ="pageSize" , defaultValue="10",required = false) int pageSize,
			@RequestParam(value ="sortBy", defaultValue="name",required = false) String sortBy,
			@RequestParam(value ="sortDir" , defaultValue="asc",required = false) String sortDir
			){
		
		return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUseRById(@PathVariable("userId") String userId){
		
		return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email){
		
		return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
	}
	
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable("keywords") String keywords){
		
		return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
	}
	
	//upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,@PathVariable String userId) throws IOException
	{
		String imageName = fileService.uploadFile(image, imageUploadPath);
		UserDto userdto =  userService.getUserById(userId);
		userdto.setImageName(imageName);

		userService.updateUser(userdto, userId);
		
		ImageResponse imageResponse = ImageResponse.builder().message("Image is SuccessFully Uploaded!!!").imageName(imageName).success(true).status(HttpStatus.CREATED).build();
	  return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
	}
	
	
	
	
	//serve user image
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable("userId") String userId, HttpServletResponse response) throws IOException {

		UserDto userDto = userService.getUserById(userId);

		InputStream resource = fileService.getResource(imageUploadPath, userDto.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(resource,response.getOutputStream());


	}

	
}
