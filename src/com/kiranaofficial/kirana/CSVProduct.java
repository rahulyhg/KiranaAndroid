package com.kiranaofficial.kirana;

import java.io.Serializable;

public class CSVProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productName;
	private String productQty;
	private String productPrice;
	private String productDiscount;
	private String productTax;
	private String productImagePath;
	private String productExtra1;
	private String productExtra2;
	private String productExtra3;
	private String productExtra4;
	
	public CSVProduct(String productName, String productQty, String productPrice, String productDiscount, String productTax,
			String productImagePath, String productExtra1, String productExtra2, String productExtra3, String productExtra4) {
		this.productName = productName;
		this.productQty = productQty;
		this.productPrice = productPrice;
		this.productDiscount = productDiscount;
		this.productTax = productTax;
		this.productImagePath = productImagePath;
		this.productExtra1 = productExtra1;
		this.productExtra2 = productExtra2;
		this.productExtra3 = productExtra3;
		this.productExtra4 = productExtra4;
	}
	
	public CSVProduct() {
		
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductQty() {
		return productQty;
	}
	public void setProductQty(String productQty) {
		this.productQty = productQty;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductDiscount() {
		return productDiscount;
	}
	public void setProductDiscount(String productDiscount) {
		this.productDiscount = productDiscount;
	}
	public String getProductTax() {
		return productTax;
	}
	public void setProductTax(String productTax) {
		this.productTax = productTax;
	}
	public String getProductImagePath() {
		return productImagePath;
	}
	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}
	public String getProductExtra1() {
		return productExtra1;
	}
	public void setProductExtra1(String productExtra1) {
		this.productExtra1 = productExtra1;
	}
	public String getProductExtra2() {
		return productExtra2;
	}
	public void setProductExtra2(String productExtra2) {
		this.productExtra2 = productExtra2;
	}
	public String getProductExtra3() {
		return productExtra3;
	}
	public void setProductExtra3(String productExtra3) {
		this.productExtra3 = productExtra3;
	}
	public String getProductExtra4() {
		return productExtra4;
	}
	public void setProductExtra4(String productExtra4) {
		this.productExtra4 = productExtra4;
	}
}
