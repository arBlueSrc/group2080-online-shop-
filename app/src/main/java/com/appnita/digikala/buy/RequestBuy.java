package com.appnita.digikala.buy;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RequestBuy{

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("customer_ip_address")
	private String customerIpAddress;

	@SerializedName("payment_method_title")
	private String paymentMethodTitle;

	@SerializedName("line_items")
	private List<LineItemsItem> lineItems;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("payment_method")
	private String paymentMethod;

	@SerializedName("billing")
	private Billing billing;

	@SerializedName("status")
	private String status;

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setCustomerIpAddress(String customerIpAddress){
		this.customerIpAddress = customerIpAddress;
	}

	public String getCustomerIpAddress(){
		return customerIpAddress;
	}

	public void setPaymentMethodTitle(String paymentMethodTitle){
		this.paymentMethodTitle = paymentMethodTitle;
	}

	public String getPaymentMethodTitle(){
		return paymentMethodTitle;
	}

	public void setLineItems(List<LineItemsItem> lineItems){
		this.lineItems = lineItems;
	}

	public List<LineItemsItem> getLineItems(){
		return lineItems;
	}

	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}

	public int getCustomerId(){
		return customerId;
	}

	public void setPaymentMethod(String paymentMethod){
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentMethod(){
		return paymentMethod;
	}

	public void setBilling(Billing billing){
		this.billing = billing;
	}

	public Billing getBilling(){
		return billing;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return
			"RequestBuy{" +
			"transaction_id = '" + transactionId + '\'' +
			",customer_ip_address = '" + customerIpAddress + '\'' +
			",payment_method_title = '" + paymentMethodTitle + '\'' +
			",line_items = '" + lineItems + '\'' +
			",customer_id = '" + customerId + '\'' +
			",payment_method = '" + paymentMethod + '\'' +
			",billing = '" + billing + '\'' +
			",status = '" + status + '\'' +
			"}";
		}
}