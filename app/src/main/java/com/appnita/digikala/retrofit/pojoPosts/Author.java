package com.appnita.digikala.retrofit.pojoPosts;

import com.google.gson.annotations.SerializedName;

public class Author{

	@SerializedName("name")
	private String name;

	@SerializedName("nickname")
	private String nickname;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("slug")
	private String slug;

	@SerializedName("url")
	private String url;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return nickname;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

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

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"Author{" + 
			"name = '" + name + '\'' + 
			",nickname = '" + nickname + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",slug = '" + slug + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}