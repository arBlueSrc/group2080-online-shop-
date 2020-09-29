package com.appnita.digikala.retrofit.pojoPosts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomFields{

	@SerializedName("enclosure")
	private List<String> enclosure;

	public void setEnclosure(List<String> enclosure){
		this.enclosure = enclosure;
	}

	public List<String> getEnclosure(){
		return enclosure;
	}

	@Override
 	public String toString(){
		return 
			"CustomFields{" + 
			"enclosure = '" + enclosure + '\'' + 
			"}";
		}
}