package com.appnita.digikala.retrofit.pojoid;

import com.google.gson.annotations.SerializedName;

public class ResponseId{



	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("username")
	private String username;


	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"ResponseId{" +
			",id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}