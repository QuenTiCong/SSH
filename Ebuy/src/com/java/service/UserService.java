package com.java.service;

import java.util.List;

import com.java.entity.PageBean;
import com.java.entity.User;

public interface UserService {

	public void saveUser(User user); // 添加用户
	
	public boolean existUserWithUserName(String userName); // 判断用户名是否存在
	
	public User login(User user); // 登录验证
	
	public List<User> findUserList(User s_user,PageBean pageBean); // 分页查询用户列表
	
	public Long getUserCount(User s_user); // 获取用户总记录数
	
	public void delete(User user);
	
	public User getUserById(int id);
}
