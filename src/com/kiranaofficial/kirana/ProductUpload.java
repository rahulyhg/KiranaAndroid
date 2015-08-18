package com.kiranaofficial.kirana;

import android.graphics.Bitmap;

public class ProductUpload {
	private int majorCode;
	private int id;
	private String product_id;
    private int quantity;
    private double price;
    private double discount;
    private double taxBracket;
    private double productSubTotal;
    private String created_at;
    private String updated_at;
    private String productImageUrl;
    private Bitmap productImage;
    
	public int getMajorCode() {
		return majorCode;
	}
	public void setMajorCode(int majorCode) {
		this.majorCode = majorCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductId() {
		return product_id;
	}
	public void setProductId(String product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getTaxBracket() {
		return taxBracket;
	}
	public void setTaxBracket(double taxBracket) {
		this.taxBracket = taxBracket;
	}
	public String getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdatedAt() {
		return updated_at;
	}
	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
	public double getProductSubTotal() {
		return productSubTotal;
	}
	public void setProductSubTotal(double productSubTotal) {
		this.productSubTotal = productSubTotal;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	public Bitmap getProductImage() {
		return productImage;
	}
	public void setProductImage(Bitmap productImage) {
		this.productImage = productImage;
	}
    
}
