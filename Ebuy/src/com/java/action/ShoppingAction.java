package com.java.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.java.entity.Product;
import com.java.entity.ShoppingCart;
import com.java.entity.ShoppingCartItem;
import com.java.entity.User;
import com.java.service.ProductService;
import com.java.util.NavUtil;
import com.java.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Result;

import net.sf.json.JSONObject;

public class ShoppingAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	
	private String mainPage;
	private String navCode;
	
	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getNavCode() {
		return navCode;
	}

	public void setNavCode(String navCode) {
		this.navCode = navCode;
	}

	@Resource
	private ProductService productService;
	
	private int productId; // ǰ̨��������
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String addShoppingCartItem() throws Exception {
		HttpSession session = request.getSession();
		Product product = productService.getProductById(productId);
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		if(shoppingCart==null) {
			shoppingCart = new ShoppingCart();
			User currentUser = (User) session.getAttribute("currentUser");
			shoppingCart.setUserId(currentUser.getId());
		}
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart.getShoppingCartItems();
		
		boolean flag = true;
		for(ShoppingCartItem scI:shoppingCartItemList) {
			if(scI.getProduct().getId()==product.getId()) {
				scI.setCount(scI.getCount() + 1);
				flag = false;
				break;
			}
		}
		
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
		
		if(flag) {
			shoppingCartItem.setProduct(product);
			shoppingCartItem.setCount(1);
			shoppingCartItemList.add(shoppingCartItem);
		}
		
		session.setAttribute("shoppingCart", shoppingCart);
		
		/**
		 * ���ص�ҳ��
		 */
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		
		return null;
	}
	
	public String list() throws Exception {
		mainPage = "shopping/shopping.jsp";
		navCode = NavUtil.genNavCode("���ﳵ");
		return SUCCESS;
	}
	
	public String buy() throws Exception {
		HttpSession session = request.getSession();
		Product product = productService.getProductById(productId);
		
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		if(shoppingCart == null){
			shoppingCart = new ShoppingCart();
			User currentUser = (User) session.getAttribute("currentUser");
			shoppingCart.setUserId(currentUser.getId());
		}
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart.getShoppingCartItems();
		
		boolean flag = true;
		for(ShoppingCartItem scI:shoppingCartItemList){
			if(scI.getProduct().getId()==product.getId()){
				scI.setCount(scI.getCount() + 1);
				flag = false;
				break;
			}
		}
		
		ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
		
		if(flag){
			shoppingCartItem.setProduct(product);
			shoppingCartItem.setCount(1);
			shoppingCartItemList.add(shoppingCartItem);
		}
		
		session.setAttribute("shoppingCart", shoppingCart);
		
		mainPage="shopping/shopping.jsp";
		navCode=NavUtil.genNavCode("���ﳵ");
		return SUCCESS;
	}
	
	/**
	 * ���¹��ﳵ��Ŀ
	 * @return
	 * @throws Exception
	 */
	public String updateShoppingCartItem() throws Exception {
		HttpSession session = request.getSession();
		Product product = productService.getProductById(productId); // ��ȡ��Ʒ��Ϣ
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart"); // ��ȡ��ǰ�Ĺ��ﳵ
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart.getShoppingCartItems(); // ��ȡ��ǰ���ﳵ�����Ŀ
		for(ShoppingCartItem scI:shoppingCartItemList) {
			if(scI.getProduct().getId()==product.getId()) {
				scI.setCount(count);
				break;
			}
		}
		
		session.setAttribute("shoppingCart", shoppingCart);
		
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		
		return null;
	}
	
	public String removeShoppingCartItem() throws Exception {
		HttpSession session = request.getSession();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		List<ShoppingCartItem> shoppingCartItemList = shoppingCart.getShoppingCartItems();
		for(int i=0;i<shoppingCartItemList.size();i++) {
			if(productId==shoppingCartItemList.get(i).getProduct().getId()) {
				shoppingCartItemList.remove(i);
				break;
			}
		}
		shoppingCart.setShoppingCartItems(shoppingCartItemList);
		session.setAttribute("shoppingCart", shoppingCart);
		return "list";
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
