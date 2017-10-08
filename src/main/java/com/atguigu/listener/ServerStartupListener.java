package com.atguigu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerStartupListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		  ServletContext application = sce.getServletContext();
		  String contextPath = application.getContextPath();
		  application.setAttribute("APP_PATH", contextPath);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
