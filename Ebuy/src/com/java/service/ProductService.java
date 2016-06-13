package com.java.service;

import java.util.List;

import com.java.entity.PageBean;
import com.java.entity.Product;

public interface ProductService {

	public List<Product> findProductList(Product s_product,PageBean pageBean); // ��ҳ��ʾ��Ʒ
	
	public Long getProductCount(Product s_product); // ��ȡ��Ʒ���ܼ�¼��
	
	public Product getProductById(int productId); // ��Ʒ����ҳ
	
	public void saveProduct(Product product);
	
	public void deleteProduct(Product product);
	
	public void setProductWithHot(int productId);
	
	public void setProductWithSpecialPrice(int productId);

	public boolean existProductWithSmallTypeId(int smallTypeId);
}
