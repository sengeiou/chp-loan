/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.StoreStoreTest.java
 * @Create By 王彬彬
 * @Create In 2015年12月7日 下午6:16:52
 */
package com.creditharmony.loan.test.excel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.creditharmony.core.excel.util.JxlsUtils;
import com.creditharmony.loan.test.base.AbstractTestCase;
import com.creditharmony.loan.test.excel.ex.Employees;

/**
 * @Class Name StoreTest
 * @author 王彬彬
 * @Create In 2015年12月7日
 */
public class ExcelExport  extends AbstractTestCase  {

	@SuppressWarnings("unchecked")
	@Test
	public void getss() throws FileNotFoundException {
		List<Employees> staff = new ArrayList<Employees>();
		for (int i = 0; i < 10000; i++) {
			staff.add(new Employees("呜呜" + i, 35, 3000, 0.30));
			staff.add(new Employees("发斯蒂芬" + i, 28, 1500, 0.15));
			staff.add(new Employees("撒的发" + i, 32, 2300, 0.25));
		}
        Map<String,Object> beans = new HashMap<String,Object>();
        beans.put("employees", staff);
		
		String destFileName="D:/destFileName.xls";
		String template="D:/templateFileName.xls";
		 
		@SuppressWarnings("rawtypes")
		JxlsUtils s = new JxlsUtils();
		
		long startTimne = System.currentTimeMillis();
		s.exportExcel(template, beans, destFileName);
		long endTime = System.currentTimeMillis();
		System.out.println("用时=" + ((endTime - startTimne) / 1000) + "秒");
	}

}
