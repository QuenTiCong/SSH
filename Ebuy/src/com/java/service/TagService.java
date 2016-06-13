package com.java.service;

import java.util.List;

import com.java.entity.PageBean;
import com.java.entity.Tag;

public interface TagService {

	public List<Tag> findTagList(Tag s_Tag,PageBean pageBean);
	
    public Long getTagCount(Tag s_tag);
	
	public void saveTag(Tag tag);
	
	public void delete(Tag tag);
	
	public Tag getTagById(int tagId);
}

