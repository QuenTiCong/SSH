package com.java.service;

import java.util.List;

import com.java.entity.Notice;
import com.java.entity.PageBean;

public interface NoticeService {

	public List<Notice> findNoticeList(Notice s_notice,PageBean pageBean);
	
	public Notice getNoticeById(int noticeId);
	
	public Long getNoticeCount(Notice s_notice);
	
	public void saveNotice(Notice notice);
	
	public void deleteNotice(Notice notice);
}
