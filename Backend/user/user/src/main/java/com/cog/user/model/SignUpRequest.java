package com.cog.user.model;

import java.util.Set;

import javax.validation.constraints.NotBlank;

/*simply a data transfer object to hold some data from the hibernate entity
 * 
 */
public class SignUpRequest {

	@NotBlank(message = "name cannot be blank#######")
	private String name;
	private String userName;
	private String emailId;
	private String password;
	private Set<Role> role;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "SignUpRequest [name=" + name + ", userName=" + userName + ", emailId=" + emailId + ", password="
				+ password + ", role=" + role + "]";
	}
	public SignUpRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SignUpRequest(@NotBlank(message = "name cannot be blank#######") String name, String userName,
			String emailId, String password, Set<Role> role) {
		super();
		this.name = name;
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.role = role;
	}

	
}
