package com.appnita.digikala.retrofit.pojoPosts;

import com.google.gson.annotations.SerializedName;

public class TagsItem{

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("post_count")
	private int postCount;

	@SerializedName("title")
	private String title;

	@SerializedName("slug")
	private String slug;

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

	public void setPostCount(int postCount){
		this.postCount = postCount;
	}

	public int getPostCount(){
		return postCount;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"TagsItem{" + 
			"description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",post_count = '" + postCount + '\'' + 
			",title = '" + title + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}