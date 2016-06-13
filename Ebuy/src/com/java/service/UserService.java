package com.java.service;

import java.util.List;

import com.java.entity.PageBean;
import com.java.entity.User;

public interface UserService {

	public void saveUser(User user); // ����û�
	
	public boolean existUserWithUserName(String userName); // �ж��û����Ƿ����
	
	public User login(User user); // ��¼��֤
	
	public List<User> findUserList(User s_user,PageBean pageBean); // ��ҳ��ѯ�û��б�
	
	public Long getUserCount(User s_user); // ��ȡ�û��ܼ�¼��
	
	public void delete(User user);
	
	public User getUserById(int id);
}
