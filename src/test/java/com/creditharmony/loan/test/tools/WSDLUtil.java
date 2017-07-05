package com.creditharmony.loan.test.tools;

import org.apache.cxf.tools.java2ws.JavaToWS;

import com.creditharmony.loan.common.ws.LoanWebService;

public class WSDLUtil {

	public static void main(String[] args) {
		JavaToWS.main(new String[]{"-o","LoanExecute.wsdl","-wsdl",LoanWebService.class.getName()});
		System.out.println("OK");

	}

}
