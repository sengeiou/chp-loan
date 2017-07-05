package com.creditharmony.loan.sync.data.proxy;

import java.math.BigDecimal;
import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.creditharmony.core.common.entity.AuditResult;
import com.creditharmony.loan.sync.data.remote.MonthRateDataService;

public class HessianTestClient {
	public static void main(String[] args) {
		// 在服务器端的web.xml文件中配置的HessianServlet映射的访问URL地址
		String url = "http://127.0.0.1:8080/loan/MonthRateData.do";
		HessianProxyFactory factory = new HessianProxyFactory();
		MonthRateDataService service = null;
		try {
			service = (MonthRateDataService) factory.create(MonthRateDataService.class, url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AuditResult a = new AuditResult();
		a.setRiskLevel("A1");
		a.setLoanCode("JK2017033000000015");
		a.setAuditAmount("20000");
		a.setProductCode("A020");
		a.setMonths(6);
		// 创建IService接口的实例对象
		AuditResult helloWorld = service.getProductRateList(a);
		// 调用Hessian服务器端的ServiceImpl类中的getUser方法来获取一个User对象
		System.out.println(helloWorld.getProductRateList().get(0).getPeriodAmount());
	}
}
