package com.java.entity;

/**
 * 商品 条目类
 * @author Administrator
 *
 */
public class ShoppingCartItem {

	private int id;
	private Product product;
	private int count;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
