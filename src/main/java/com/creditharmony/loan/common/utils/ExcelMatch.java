package com.creditharmony.loan.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.loan.car.carGrant.ex.SortableField;

/**
 * excel导入时，校验字段是否匹配
 * @Class Name ExcelMatch
 * @author 申诗阔
 * @Create In 2016年5月16日
 */
public class ExcelMatch {

	private ExcelMatch() {
	}

	/**
	 * 校验导入excel格式是否正确
	 * 2016年5月16日
	 * By 申诗阔
	 * @param file 导入的文件
	 * @param cls 解析的class类
	 * @return true 表示匹配成功，false 表示导入excel有误（缺少字段，或字段名有误）
	 * @throws IOException
	 */
	public static boolean matchResult(MultipartFile file, Class<?> cls)
			throws IOException {
		// 获取导入文件第一行的每列的小标题
		InputStream is = file.getInputStream();
		Workbook wb = new XSSFWorkbook(is);
		Sheet sheet = wb.getSheetAt(0);
		Row title = sheet.getRow(0);
		// 标题数组
		String[] titles = new String[title.getPhysicalNumberOfCells()];
		for (int i = 0; i < title.getPhysicalNumberOfCells(); i++) {
			titles[i] = title.getCell(i).getStringCellValue();
		}
		List<String> excelFieldList = Arrays.asList(titles);

		// 获取要导入的实体类CarGrantEx的含有@ExcelField(title 的小标题
		// 用于和导入文件的小标题对比，若有一个不匹配，表示导入文件有错
		Field[] fields = cls.getDeclaredFields();
		boolean isExist = true;
		for (Field f : fields) {
			// 获取字段中包含ExcelField的注解
			ExcelField meta = f.getAnnotation(ExcelField.class);
			if (meta != null) {
				SortableField sf = new SortableField(meta, f);
				if (!excelFieldList.contains(sf.geteF().title())) {
					isExist = false;
					break;
				}
			}
		}
		return isExist;
	}
}