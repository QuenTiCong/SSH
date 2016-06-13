package com.java.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.java.entity.News;
import com.java.entity.Notice;
import com.java.entity.PageBean;
import com.java.entity.Product;
import com.java.entity.ProductBigType;
import com.java.entity.Tag;
import com.java.service.NewsService;
import com.java.service.NoticeService;
import com.java.service.ProductBigTypeService;
import com.java.service.ProductService;
import com.java.service.TagService;

@Component
public class InitAction implements ServletContextListener,ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext application = servletContextEvent.getServletContext();
		ProductBigTypeService productBigTypeService = (ProductBigTypeService)applicationContext.getBean("productBigTypeService");
		List<ProductBigType> bigTypeList = productBigTypeService.findAllBigTypeList();
		application.setAttribute("bigTypeList", bigTypeList);
		
		TagService tagService = (TagService) applicationContext.getBean("tagService");
		List<Tag> tagList = tagService.findTagList(null,null);
		application.setAttribute("tagList", tagList);
		
		NoticeService noticeService = (NoticeService) applicationContext.getBean("noticeService");
		List<Notice> noticeList = noticeService.findNoticeList(null, new PageBean(1, 7));
		application.setAttribute("noticeList", noticeList);
		
		NewsService newsService=(NewsService)applicationContext.getBean("newsService");
		List<News> newsList=newsService.findNewsList(null, new PageBean(1,7));
		application.setAttribute("newsList", newsList);
		
		ProductService productService = (ProductService) applicationContext.getBean("productService");
		List<Product> productList = productService.findProductList(null, new PageBean(1, 8));
		Product s_product = new Product();
		s_product.setSpecialPrice(1); // Ãÿº€≤È—Ø
		List<Product> specialPriceProductList = productService.findProductList(s_product, new PageBean(1, 8));
		application.setAttribute("specialPriceProductList", specialPriceProductList);
		
		s_product = new Product();
		s_product.setHot(1);
		List<Product> hotProductList = productService.findProductList(s_product, new PageBean(1,6));
		application.setAttribute("hotProductList", hotProductList);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext  =applicationContext;
	}

}
