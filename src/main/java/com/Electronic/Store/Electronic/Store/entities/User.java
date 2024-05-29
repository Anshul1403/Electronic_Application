package com.Electronic.Store.Electronic.Store.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GeneratorType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Order> orders = new ArrayList<>();
	
}
