package com.creditharmony.loan.test.excel.ex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class TemplateExports {
	public static void main(String[] args) {
        String templateFileName= "D:/templateFileName.xls";
        String destFileName="D:/destFileName.xls";
        List<Employees> staff = new ArrayList<Employees>();
        staff.add(new Employees("Derek", 35, 3000, 0.30));
        staff.add(new Employees("Elsa", 28, 1500, 0.15));
        staff.add(new Employees("Oleg", 32, 2300, 0.25));
        Map<String,Object> beans = new HashMap<String,Object>();
        beans.put("employees", staff);
        XLSTransformer transformer = new XLSTransformer();
        
            try {
				transformer.transformXLS(templateFileName, beans, destFileName);
			} catch (ParsePropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        

    }
}
