package com.creditharmony.loan.test.tools;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * jetty服务器启动工具类
 * @Class Name JettyUtils
 * @author 张永生
 * @Create In 2015年11月11日
 */
public class JettyUtils {

	/**
	 * @param port
	 * @param context
	 * @return
	 */
	public static Server buildDebugServer(int port, String context) {
		Server server = new Server(port);
		WebAppContext webContext = new WebAppContext("src/main/webapp", context);
		webContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		server.setHandler(webContext);
		server.setStopAtShutdown(true);
		return server;
	}

	/**
	 * 创建用于Functional TestJetty Server, 以src/main/webapp为Web应用目录.
	 * 以test/resources/web.xml指向applicationContext-test.xml创建测试环境.
	 */
	public static Server buildTestServer(int port, String contextPath) {
		Server server = new Server(port);
		WebAppContext webContext = new WebAppContext("src/main/webapp", contextPath);
		webContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		webContext.setDescriptor("src/test/resources/web.xml");
		server.setHandler(webContext);
		server.setStopAtShutdown(true);
		return server;
	}
	
}
