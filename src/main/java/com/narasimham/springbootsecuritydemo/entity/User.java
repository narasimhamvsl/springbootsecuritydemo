package com.narasimham.springbootsecuritydemo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Name cannot be empty.")
	@Size(min = 3, max = 20, message = "Minimum 3 and maximum 20 characters are allowed !! ")
	private String name;

	@Column(unique = true)
	@NotBlank(message = "email cannot be empty.")
	private String email;

	@NotBlank(message = "Password cannot be empty.")
	private String password;

	private String imageURL;

	@Column(length = 500)
	private String about;

	private String role;;

	private boolean enabled;

	@Column(unique = true)
	@NotBlank(message = "email cannot be empty.")
	@Size(min = 10, max = 10, message = "Enter a valid 10 digit mobile number !! ")
	private String mobile;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	//	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	//	private List<Contact> contactsList = new ArrayList<Contact>();

	public User() {
		super();
	}

	public User(int id,
			@NotBlank(message = "Name cannot be empty.") @Size(min = 3, max = 20, message = "Minimum 3 and maximum 20 characters are allowed !! ") String name,
			@NotBlank(message = "email cannot be empty.") String email,
			@NotBlank(message = "Password cannot be empty.") String password, String imageURL, String about, String role,
			boolean enabled,
			@NotBlank(message = "email cannot be empty.") @Size(min = 10, max = 10, message = "Enter a valid 10 digit mobile number !! ") String mobile,
			LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.imageURL = imageURL;
		this.about = about;
		this.role = role;
		this.enabled = enabled;
		this.mobile = mobile;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", imageURL="
				+ imageURL + ", about=" + about + ", role=" + role + ", enabled=" + enabled + ", mobile=" + mobile
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + "]";
	}
}
