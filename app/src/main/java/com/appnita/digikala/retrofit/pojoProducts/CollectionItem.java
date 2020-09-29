package com.appnita.digikala.retrofit.pojoProducts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CollectionItem implements Serializable {

	@SerializedName("href")
	private String href;

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}

	@Override
 	public String toString(){
		return 
			"CollectionItem{" + 
			"href = '" + href + '\'' + 
			"}";
		}
}