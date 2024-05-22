package com.Electronic.Store.Electronic.Store.dtos;

import java.security.InvalidAlgorithmParameterException;

import org.hibernate.annotations.NotFound;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


	private String userId;
	
	@Size(min = 3,max = 20,message = "Invalid Name")
	private String name;
	
	@Email(message ="invalid email")
	@NotBlank(message ="email is Required")
	private String email;
	
    @NotBlank(message ="Password is Required")
	private String password;
	
    @Size(min = 4,max = 6,message = "Invalid Gender")
	private String gender;
	
	 
	private String about;
	
	
	private String imageName;
}
