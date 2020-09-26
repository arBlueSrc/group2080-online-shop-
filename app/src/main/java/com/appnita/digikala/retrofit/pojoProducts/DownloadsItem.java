package com.appnita.digikala.retrofit.pojoProducts;

import com.google.gson.annotations.SerializedName;

public class DownloadsItem{

	@SerializedName("file")
	private String file;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"DownloadsItem{" + 
			"file = '" + file + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}