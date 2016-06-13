package com.java.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.java.entity.PageBean;
import com.java.entity.User;
import com.java.service.UserService;
import com.java.util.NavUtil;
import com.java.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class UserAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private UserService userService;

	private HttpServletRequest request;
	
	private String userName;
	private User user;
	private String error;
	
	private String imageCode;
	
	private String navCode;
	private String mainPage;
	
	private User s_user;
	
	private String page; // ��ǰҳ
	private String rows; // ÿҳ�ļ�¼��
	
	private String ids;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public User getS_user() {
		return s_user;
	}
	public void setS_user(User s_user) {
		this.s_user = s_user;
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
	
	public String getNavCode() {
		return navCode;
	}
	public void setNavCode(String navCode) {
		this.navCode = navCode;
	}
	public String getMainPage() {
		return mainPage;
	}
	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getImageCode() {
		return imageCode;
	}
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	public String existUserWithUserName() throws Exception {
		/**
		 * �û�����뿪�ı���ʱ����ajax�ж�
		 */
		boolean exist = userService.existUserWithUserName(userName);
		JSONObject result = new JSONObject();
		if(exist) {
			result.put("exist", true);
		} else {
			result.put("exist", false);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String login() throws Exception {
		HttpSession session = request.getSession();
		User currentUser = userService.login(user);
		if(!imageCode.equals(session.getAttribute("sRand"))) {
			error = "��֤�����";
			return ERROR;
		} else if(currentUser==null) {
			error = "�û������������";
			return ERROR;
		} else {
			session.setAttribute("currentUser", currentUser);
		}
		if(user.getStatus()==2) {
			return "adminLogin";
		} else {
			return "login";
		}
	}
	
	
	public String register() throws Exception {
		userService.saveUser(user);
		return "register_success";
	}
	
	public String logout() throws Exception {
		request.getSession().invalidate();
		return "logout";
	}
	
	public String logout2() throws Exception {
		request.getSession().invalidate();
		return "logout2";
	}
	
	public String userCenter() throws Exception {
		navCode = NavUtil.genNavCode("��������");
		mainPage = "userCenter/ucDefault.jsp";
		return "userCenter";
	}
	
	public String getUserInfo() throws Exception {
		navCode = NavUtil.genNavCode("��������");
		mainPage = "userCenter/userInfo.jsp";
		return "userCenter";
	}
	
	public String preSave() throws Exception {
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("currentUser");
		navCode = NavUtil.genNavCode("��������");
		mainPage = "userCenter/userSave.jsp";
		return "userCenter";
	}
	
	public String save() throws Exception {
		HttpSession session = request.getSession();
		userService.saveUser(user);
		session.setAttribute("currentUser", user);
		navCode = NavUtil.genNavCode("��������");
		mainPage = "userCenter/userInfo.jsp";
		return "userCenter";
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String list() throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		List<User> userList = userService.findUserList(s_user, pageBean);
		long total = userService.getUserCount(s_user);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"orderList"});
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(userList, jsonConfig);
		JSONObject result = new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String deleteUser() throws Exception {
		JSONObject result = new JSONObject();
		String []idsStr = ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			User u = userService.getUserById(Integer.parseInt(idsStr[i]));
			userService.delete(u);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String saveUser() throws Exception {
		userService.saveUser(user);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String modifyPassword() throws Exception {
		User u = userService.getUserById(user.getId());
		u.setPassword(user.getPassword());
		userService.saveUser(u);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
}
