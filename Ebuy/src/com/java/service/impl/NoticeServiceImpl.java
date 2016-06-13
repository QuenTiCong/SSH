package com.java.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java.dao.BaseDAO;
import com.java.entity.Notice;
import com.java.entity.PageBean;
import com.java.service.NoticeService;
import com.java.util.StringUtil;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private BaseDAO<Notice> baseDAO;
	
	@Override
	public List<Notice> findNoticeList(Notice s_notice, PageBean pageBean) {
		List<Object> param = new LinkedList<Object>(); // LinkedList类是双向列表,列表中的每个节点都包含了对前一个和后一个元素的引用.
		StringBuffer hql = new StringBuffer("from Notice");
		if(s_notice!=null) {
			if(StringUtil.isNotEmpty(s_notice.getTitle())){
				hql.append(" and title like ?");
				param.add("%"+s_notice.getTitle()+"%");
			}
		}
		if(pageBean!=null) {
			return baseDAO.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		} else {
			return null;
		}
	}

	@Override
	public Notice getNoticeById(int noticeId) {
		return baseDAO.get(Notice.class, noticeId);
	}

	@Override
	public Long getNoticeCount(Notice s_notice) {
		List<Object> param = new LinkedList<Object>();
		StringBuffer hql = new StringBuffer("select count(*) from Notice");
		if(s_notice!=null) {
			if(StringUtil.isNotEmpty(s_notice.getTitle())) {
				hql.append(" and title like ?");
				param.add("%"+s_notice.getTitle()+"%");
			}
		}
		return baseDAO.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void saveNotice(Notice notice) {
		baseDAO.merge(notice);
	}

	@Override
	public void deleteNotice(Notice notice) {
	    baseDAO.delete(notice);
	}

}
