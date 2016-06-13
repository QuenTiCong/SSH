package com.java.service;

import java.util.List;

import com.java.entity.PageBean;
import com.java.entity.Product;

public interface ProductService {

	public List<Product> findProductList(Product s_product,PageBean pageBean); // 分页显示商品
	
	public Long getProductCount(Product s_product); // 获取商品的总记录数
	
	public Product getProductById(int productId); // 商品详情页
	
	public void saveProduct(Product product);
	
	public void deleteProduct(Product product);
	
	public void setProductWithHot(int productId);
	
	public void setProductWithSpecialPrice(int productId);

	public boolean existProductWithSmallTypeId(int smallTypeId);
}
