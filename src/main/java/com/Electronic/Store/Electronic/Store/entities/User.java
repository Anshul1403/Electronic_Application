package com.Electronic.Store.Electronic.Store.entities;

import org.hibernate.annotations.GeneratorType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userId;
	
	@Column(name="user_name")
	private String name;
	
	@Column(name = "user_email",unique = true)
	private String email;
	
	@Column(name = "user_password",length = 10)
	private String password;
	
	private String gender;
	
	@Column(length = 100)
	private String about;
	
	@Column(name="user_image_name")
	private String imageName;
	
}
