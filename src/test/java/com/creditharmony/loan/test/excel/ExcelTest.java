/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.StoreStoreTest.java
 * @Create By 王彬彬
 * @Create In 2015年12月7日 下午6:16:52
 */
package com.creditharmony.loan.test.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import com.creditharmony.core.excel.util.ImportExcel;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * @Class Name StoreTest
 * @author 王彬彬
 * @Create In 2015年12月7日
 */
public class ExcelTest extends AbstractTestCase {

	@Autowired
	private ContractDao contractDao;
	@Autowired
	ThreePartFileName threePartFileName;
	@Test
	public void getss()
	{
		String s = threePartFileName.getHylDsExportFileName();
		System.out.println(s);
	}
	/**
	 * 导出EXCEL（list-〉excel，无格式） 2015年12月19日 By 王彬彬
	 */
	
	public void exportExcel() {
		// MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		ExcelUtils excelutil = new ExcelUtils();

		System.out.println("Test Start");

		Contract contract = contractDao.findByContractCode("HTBH1001");
		List<Contract> datalist = new ArrayList<Contract>();
		datalist.add(contract);

		excelutil.exportExcel(datalist, "", Contract.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_DATA, response, null);

		System.out.println("");
	}

	/**
	 * 导出EXCEL有格式 2015年12月19日 By 王彬彬
	 */
	@SuppressWarnings("unchecked")
	public void exportExcelTemplate() {
		MockHttpServletResponse response = new MockHttpServletResponse();

		ExcelUtils excelutil = new ExcelUtils();

		List<UserData> lst = new ArrayList<UserData>();
		System.out.println("Test Start");

		List<?> datalist = excelutil.importExcel(
				"D:/CHP资料/需求规则管理/上传下载文件/上传CS.xls", 0, 0, UserData.class);

		lst = (List<UserData>) datalist;

		excelutil.exportExcel(lst, "测试", UserData.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, null);

	}

	/**
	 * 导入list (Excel文件导出 转换成对应list) 2015年12月19日 By 王彬彬
	 */
	@SuppressWarnings("unchecked")
	public void importExcel() {
		try {
			ExcelUtils excelutil = new ExcelUtils();
			List<UserData> lst = new ArrayList<UserData>();
			System.out.println("Test Start");

			List<?> datalist = excelutil.importExcel(
					"D:/CHP资料/需求规则管理/上传下载文件/上传CS.xls", 0, 0, UserData.class);

			lst = (List<UserData>) datalist;

		} catch (Exception e) {
		}

	}

	/**
	 * 逐行解析 2015年12月19日 By 王彬彬
	 * 
	 * @throws InvalidFormatException
	 * @throws IOException
	 */

	public void importExcelss() throws InvalidFormatException, IOException {
		ImportExcel ei = new ImportExcel("D:/CHP资料/需求规则管理/上传下载文件/上传CS.xls", 0);

		Integer count = 0;
		for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {
			count++;
			Row row = ei.getRow(i);
			for (int j = 0; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				System.out.print(val + ", ");
			}
			System.out.print("\n");
		}
		System.out.print(count);
	}
}
