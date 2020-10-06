package com.appnita.digikala.buy;

import com.google.gson.annotations.SerializedName;

public class Billing{

	public Billing(String phone, String lastName, String firstName, String email) {
		this.phone = phone;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
	}

	@SerializedName("phone")
	private String phone;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Billing{" + 
			"phone = '" + phone + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}