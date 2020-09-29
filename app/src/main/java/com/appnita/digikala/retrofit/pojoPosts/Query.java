package com.appnita.digikala.retrofit.pojoPosts;

import com.google.gson.annotations.SerializedName;

public class Query{

	@SerializedName("id")
	private String id;

	@SerializedName("ignore_sticky_posts")
	private boolean ignoreStickyPosts;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIgnoreStickyPosts(boolean ignoreStickyPosts){
		this.ignoreStickyPosts = ignoreStickyPosts;
	}

	public boolean isIgnoreStickyPosts(){
		return ignoreStickyPosts;
	}

	@Override
 	public String toString(){
		return 
			"Query{" + 
			"id = '" + id + '\'' + 
			",ignore_sticky_posts = '" + ignoreStickyPosts + '\'' + 
			"}";
		}
}