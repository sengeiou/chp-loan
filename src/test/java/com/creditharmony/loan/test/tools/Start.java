package com.creditharmony.loan.test.tools;

import org.mortbay.jetty.Server;

/**
 * 使用Jetty运行调试Web应用
 * 在Console输入回车停止服务
 * @Class Name Start
 * @author 张永生
 * @Create In 2015年12月10日
 */
public class Start {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/loan";
	public static final String BASE_URL = "http://localhost:8080/loan";

	public static void main(String[] args) throws Exception {
		
		System.setProperty("java.awt.headless", "true");
		Server server = JettyUtils.buildDebugServer(PORT, CONTEXT);
		server.start();

		System.out.println("Hit Enter in console to stop server");
		
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
		
	}
}
