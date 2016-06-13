package com.java.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.java.entity.Comment;
import com.java.entity.PageBean;
import com.java.service.CommentService;
import com.java.util.PageUtil;
import com.java.util.ResponseUtil;
import com.java.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class CommentAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	@Resource
	private CommentService commentService;
	
	private List<Comment> commentList;
	
	private String page;
	private Long total;
	private String pageCode;
	
	private Comment s_Comment;
	
	private Comment comment;
	
    private int commentId;
    private String rows;
	private String ids;
	
	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public Comment getS_Comment() {
		return s_Comment;
	}

	public void setS_Comment(Comment s_Comment) {
		this.s_Comment = s_Comment;
	}
	
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public String list() throws Exception {
		if(StringUtil.isEmpty(page)) {
			page="1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		commentList = commentService.findCommentList(s_Comment, pageBean);
		total = commentService.getCommentCount(s_Comment);
		pageCode = PageUtil.genPaginationNoParam(request.getContextPath() + "/comment_list.action", total, Integer.parseInt(page), 3);
		return SUCCESS;
	}
	
	public String save() throws Exception {
		if(comment.getCreateTime()==null) {
			comment.setCreateTime(new Date());
		}
		commentService.saveComment(comment);
		return "save";
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String listComment() throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<Comment> commentList = commentService.findCommentList(s_Comment, pageBean);
		total = commentService.getCommentCount(s_Comment);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows = JSONArray.fromObject(commentList, jsonConfig);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	
	public String delete() throws Exception {
		JSONObject result = new JSONObject();
		String []idStr = ids.split(",");
		for(int i=0;i<idStr.length;i++) {
			Comment comment = commentService.getCommentById(Integer.parseInt(idStr[i]));
			commentService.delete(comment);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String loadCommentById() throws Exception{
		Comment comment = commentService.getCommentById(commentId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject result = JSONObject.fromObject(comment, jsonConfig);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String replay() throws Exception {
		comment.setReplyTime(new Date());
		commentService.saveComment(comment);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
}
