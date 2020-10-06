package com.appnita.digikala.buy;

import com.google.gson.annotations.SerializedName;

public class LineItemsItem{

	public LineItemsItem(int productId) {
		this.productId = productId;
	}

	@SerializedName("product_id")
	private int productId;

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	@Override
 	public String toString(){
		return 
			"LineItemsItem{" + 
			"product_id = '" + productId + '\'' + 
			"}";
		}
}