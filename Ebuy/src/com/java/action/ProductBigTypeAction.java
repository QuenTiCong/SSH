package com.java.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.java.entity.PageBean;
import com.java.entity.ProductBigType;
import com.java.service.ProductBigTypeService;
import com.java.service.ProductSmallTypeService;
import com.java.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;



@Controller
public class ProductBigTypeAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String page;
	private String rows;
	private ProductBigType s_productBigType;
	
	private String ids;
	
	private ProductBigType productBigType;
	
	@Resource
	private ProductBigTypeService productBigTypeService;

	@Resource
	private ProductSmallTypeService productSmallTypeService;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
	
	public ProductBigType getProductBigType() {
		return productBigType;
	}

	public void setProductBigType(ProductBigType productBigType) {
		this.productBigType = productBigType;
	}

	public String comboList() throws Exception {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", "");
		jsonObject.put("name", "��ѡ��...");
		jsonArray.add(jsonObject);
		
		List<ProductBigType> productBigTypeList = productBigTypeService.findAllBigTypeList();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"productList","smallTypeList"});
		JSONArray rows = JSONArray.fromObject(productBigTypeList, jsonConfig);
		jsonArray.addAll(rows);
		ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
		return null;
	}
	
	public String list() throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<ProductBigType> productBigTypeList=productBigTypeService.findProductBigTypeList(s_productBigType, pageBean);		
		long total = productBigTypeService.getProductBigTypeCount(s_productBigType);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"productList","smallTypeList"});
		JSONArray rows = JSONArray.fromObject(productBigTypeList, jsonConfig);
		JSONObject result = new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String save() throws Exception {
		productBigTypeService.saveProductBigType(productBigType);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String delete() throws Exception {
		JSONObject result = new JSONObject();
		String []idsStr = ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			if(productSmallTypeService.existSmallTypeWithBigTypeId(Integer.parseInt(idsStr[i]))){
				result.put("exist", "��Ʒ���������ƷС��");
			}else{
				ProductBigType productBigType = productBigTypeService.getProductBigTypeById(Integer.parseInt(idsStr[i]));
				productBigTypeService.delete(productBigType);				
			}
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	
}
