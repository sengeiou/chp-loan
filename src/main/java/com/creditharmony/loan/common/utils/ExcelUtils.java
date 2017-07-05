/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsExcelUtils.java
 * @Create By 王彬彬
 * @Create In 2015年12月18日 下午8:22:43
 */
package com.creditharmony.loan.common.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.excel.util.ExportExcel;
import com.creditharmony.core.excel.util.ImportExcel;
import com.creditharmony.core.excel.util.MultiExportExcel;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.loan.common.exception.LoanImportXlsException;
import com.google.common.collect.Lists;

/**
 * （Excel操作导入导出）
 * 
 * @Class Name ExcelUtils
 * @author 王彬彬
 * @Create In 2015年12月18日
 */
public class ExcelUtils {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 导出Excel文件(导入到模板页) 2015年12月19日 By 王彬彬
	 * 
	 * @param dataList
	 *            导出的数据集
	 * @param cls
	 *            导出数据集对象Class
	 * @param title
	 *            表格标题
	 * @param fileType
	 *            文件扩展名
	 * @param templateType
	 *            导出类型（1:导出数据；2：导出模板）
	 * @param response
	 * @param groups
	 *            导入分组
	 */
	public void exportExcel(List<?> dataList, String title, Class<?> cls,
			String fileType, int templateType, HttpServletResponse response,
			int... groups) {
		try {
			ExportExcel exportExcel = new ExportExcel(title, cls, templateType,
					groups);

			// 设置导出数据源
			exportExcel.setDataList(dataList);

			exportExcel.write(response, title + fileType).dispose();
		} catch (IOException e) {
			logger.error("文件输出异常（IO）" + e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("文件输出失败" + e.getLocalizedMessage(), e);
		}

	}

	/**
	 * 导出Excel文件(导入到模板页) 2015年12月19日 By 王彬彬
	 * 
	 * @param dataList
	 *            导出的数据集
	 * @param cls
	 *            导出数据集对象Class
	 * @param fileName
	 *            文件名称
	 * @param header
	 *            表格标题（为空的场合无header）
	 * @param fileType
	 *            文件扩展名
	 * @param templateType
	 *            导出类型（1:导出数据；2：导出模板）
	 * @param response
	 * @param groups
	 *            导入分组
	 */
	public void exportExcel(List<?> dataList, String fileName, String header,
			Class<?> cls, String fileType, int templateType,
			HttpServletResponse response, int... groups) {
		try {
			ExportExcel exportExcel = new ExportExcel(header, cls,
					templateType, groups);

			// 设置导出数据源
			exportExcel.setDataList(dataList);

			exportExcel.write(response, fileName + fileType).dispose();
		} catch (IOException e) {
			logger.error("文件输出异常（IO）" + e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("文件输出失败" + e.getLocalizedMessage(), e);
		}

	}

	/**
	 * 导出EXCEL，表格标题通过自定义数据填写，文件名根据参数传入 2016年3月8日 By 王彬彬
	 * 
	 * @param dataList要导出的数据
	 * @param title
	 *            自定义表头（使用数组格式传入）
	 * @param cls
	 *            导出对象类型
	 * @param fileType
	 *            导出文件类型，默认为xlsx
	 * @param templateType
	 *            导出方式
	 * @param fileName
	 *            导出后的文件名
	 * @param response
	 *            返回请求
	 * @param groups
	 *            导出对象组别
	 */
	public void exportExcel(List<?> dataList, String[] title, Class<?> cls,
			String fileType, int templateType, String fileName,
			HttpServletResponse response, int... groups) {
		try {
			MultiExportExcel exportExcel = new MultiExportExcel(title, cls,
					templateType, groups);

			// 设置导出数据源
			exportExcel.setDataList(dataList);

			exportExcel.write(response, fileName + fileType).dispose();
		} catch (IOException e) {
			logger.error("文件输出异常（IO）" + e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("文件输出失败" + e.getLocalizedMessage(), e);
		}

	}

	/**
	 * 导出EXCEL，表格标题通过自定义数据填写，文件名根据参数传入 2016年3月8日 By 王彬彬
	 * 
	 * @param dataList要导出的数据
	 * @param title
	 *            自定义表头（使用数组格式传入）
	 * @param cls
	 *            导出对象类型
	 * @param fileType
	 *            导出文件类型，默认为xlsx
	 * @param templateType
	 *            导出方式
	 * @param fileName
	 *            导出后的文件名
	 * @param response
	 *            返回请求
	 * @param groups
	 *            导出对象组别
	 */
	public void exportExcel(List<?> dataList, String[][] title, Class<?> cls,
			String fileType, int templateType, String fileName,
			HttpServletResponse response, int... groups) {
		try {
			MultiExportExcel exportExcel = new MultiExportExcel(title, cls,
					templateType, groups);

			// 设置导出数据源
			exportExcel.setDataList(dataList);

			exportExcel.write(response, fileName + fileType).dispose();
		} catch (IOException e) {
			logger.error("文件输出异常（IO）" + e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("文件输出失败" + e.getLocalizedMessage(), e);
		}

	}

	/**
	 * 导出带header和footer的数据 2016年3月8日 By 王彬彬
	 * 
	 * @param dataList
	 *            导出对象数据
	 * @param title
	 *            文件标题
	 * @param footer
	 *            文件页脚
	 * @param cls
	 *            对象类型
	 * @param fileType
	 *            文件类型，默认xlsx
	 * @param templateType
	 *            导出对象
	 * @param fileName
	 *            文件名
	 * @param response
	 *            返回相应
	 * @param groups
	 *            导出对象分组
	 */
	public void exportExcel(List<?> dataList, String[] title, String[] footer,
			Class<?> cls, String fileType, int templateType, String fileName,
			HttpServletResponse response, int... groups) {
		try {
			MultiExportExcel exportExcel = new MultiExportExcel(title, cls,
					templateType, groups);

			// 设置导出数据源
			exportExcel.setDataList(dataList);

			// 设置页脚
			if (ArrayHelper.isNotNull(title)) {
				exportExcel.setFooter(footer);
			}

			exportExcel.write(response, fileName + fileType).dispose();
		} catch (IOException e) {
			logger.error("文件输出异常（IO）" + e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("文件输出失败" + e.getLocalizedMessage(), e);
		}

	}

	/**
	 * Excel 导入(获取导入数据列表) 2015年12月19日 By 王彬彬
	 * 
	 * @param path
	 *            导入文件路径
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @param sheetIndex
	 *            工作表编号
	 * @param cls
	 *            输出对象Class
	 * @param groups
	 */
	public List<?> importExcel(String path, Integer headerNum,
			Integer sheetIndex, Class<?> cls, int... groups) {
		try {
			ImportExcel importExcel = new ImportExcel(path, headerNum,
					sheetIndex);

			List<?> dataList = importExcel.getDataList(cls, groups);

			return dataList;
		} catch (InvalidFormatException e) {
			logger.error("文件格式异常" + e.getLocalizedMessage(), e);
			return null;
		} catch (IOException e) {
			logger.error("文件导入失败" + e.getLocalizedMessage(), e);
			return null;
		} catch (InstantiationException e) {
			logger.error("文件初始化异常" + e.getLocalizedMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			logger.error("文件访问异常" + e.getLocalizedMessage(), e);
			return null;
		}

	}

	/**
	 * Excel 导入(获取导入数据列表) 2015年12月19日 By 王彬彬
	 * 
	 * @param path
	 *            导入文件
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @param sheetIndex
	 *            工作表编号
	 * @param cls
	 *            输出对象Class
	 * @param groups
	 */
	public List<?> importExcel(File path, Integer headerNum,
			Integer sheetIndex, Class<?> cls, int... groups) {
		try {
			ImportExcel importExcel = new ImportExcel(path, headerNum,
					sheetIndex);

			List<?> dataList = importExcel.getDataList(cls, groups);

			return dataList;
		} catch (InvalidFormatException e) {
			logger.error("文件格式异常" + e.getLocalizedMessage(), e);
			return null;
		} catch (IOException e) {
			logger.error("文件导入失败" + e.getLocalizedMessage(), e);
			return null;
		} catch (InstantiationException e) {
			logger.error("文件初始化异常" + e.getLocalizedMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			logger.error("文件访问异常" + e.getLocalizedMessage(), e);
			return null;
		} catch(Exception e){
			try {
				ImportExcel importExcel = new ImportExcel(path.getName()+"x", new FileInputStream(path), headerNum, sheetIndex);
				List<?> dataList = importExcel.getDataList(cls, groups);
				return dataList;
			} catch (Exception e2) {
				logger.error("文件导入失败" + e.getLocalizedMessage(), e);
				return null;
			}
		}

	}
	
	public <E> String validateField(Class<E> cls, E obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, LoanImportXlsException {
		StringBuilder failureMsg = new StringBuilder();
		Field[] fs = cls.getDeclaredFields();
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==2)){
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(),cls);  
	            Method getMethod = pd.getReadMethod();  
	            Object o = getMethod.invoke(obj);
	            if(ef.hasNull()==1 && ( null==o ||"".equals((o+"").trim()))){
	            	failureMsg.append(ef.title()+"不能为空,");
	            }
	            if(StringUtils.isNotEmpty(ef.dictType())){
	            	List<String> dictNames = Lists.newArrayList();
	            	List<String> dictValues = Lists.newArrayList();
	        		if(null != dictMap){
	        			for (Dict dict : dictMap.values()) {
	        				if (null != dict.getType() && dict.getType().equals(ef.dictType())) {
	        					dictValues.add(dict.getValue());
	        					dictNames.add(dict.getLabel());
	        				}
	        		    }
	        		}
	            	if(!dictValues.contains((o+"").trim())){
	            		failureMsg.append(ef.title()+"信息不符（"+StringUtils.join(dictNames.toArray(),",")+")，");
	            	}
	            }
			}
		}
		if(!"".equals(failureMsg.toString())){
			throw new LoanImportXlsException(failureMsg.toString());
		}
		return failureMsg.toString();
	}

	/**
	 * Excel 导入(获取导入数据列表) 2015年12月19日 By 王彬彬
	 * 
	 * @param path
	 *            导入文件
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @param sheetIndex
	 *            工作表编号
	 * @param cls
	 *            输出对象Class
	 * @param groups
	 */
	public List<?> importExcel(MultipartFile path, Integer headerNum,
			Integer sheetIndex, Class<?> cls, int... groups) {
		try {
			ImportExcel importExcel = new ImportExcel(path, headerNum,
					sheetIndex);

			List<?> dataList = importExcel.getDataList(cls, groups);

			return dataList;
		} catch (InvalidFormatException e) {
			logger.error("文件格式异常" + e.getLocalizedMessage(), e);
			return null;
		} catch (IOException e) {
			logger.error("文件导入失败" + e.getLocalizedMessage(), e);
			return null;
		} catch (InstantiationException e) {
			logger.error("文件初始化异常" + e.getLocalizedMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			logger.error("文件访问异常" + e.getLocalizedMessage(), e);
			return null;
		}
	}

	/********************* 新EXCEL导出 ****************************/
	public void exportData(Map<String, Object> queryMap, Class<?> cls,
			ResultSet resultSet, int row, String fileName, String fileType,
			List<String> header, Integer headerIndex, List<String> footer,
			Integer footerStartCell, HttpServletResponse response,List<String> colNameList) {
		try {
			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet dataSheet = wb.createSheet("ExportList");

			if (headerIndex == null) {
				headerIndex = 0;
			}

			// excel标题
			wrapperHeaderOrFooter(dataSheet, header, headerIndex, 0);

			// 写入数据
			row = assembleExcelCell(resultSet, dataSheet, row,colNameList);

			// excel写入footer
			if (ArrayHelper.isNotEmpty(footer)) {
				wrapperHeaderOrFooter(dataSheet, footer, row, footerStartCell);
			}
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode(fileName + fileType)
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode(fileName + fileType));
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出数据出现异常," + e.getMessage());
		}
	}

	/**
	 * 数据最后行统计 2016年4月15日 By 王彬彬
	 * 
	 * @param dataSheet
	 *            要写入的excel
	 * @param rowStartIndex
	 *            开始行（excel索引从0开始）
	 * @param cellStartIndex
	 *            开始单元格（索引0开始）
	 * @param headerOrFooter
	 *            要写入的头部或尾部信息
	 */
	public static void wrapperHeaderOrFooter(Sheet dataSheet,
			List<String> headerOrFooter, int rowStartIndex, int cellStartIndex) {
		Row headerRow = dataSheet.createRow(rowStartIndex);

		for (int i = 0; i < headerOrFooter.size(); i++) {
			Cell cellHeader = headerRow.createCell(cellStartIndex + i);
			cellHeader.setCellValue(headerOrFooter.get(i));
		}
	}

	public static int assembleExcelCell(ResultSet resultSet, Sheet dataSheet, int row,List<String> lst)
			throws SQLException {
		Row dataRow;
		Cell cell;
		while (resultSet.next()) {
			dataRow = dataSheet.createRow(row);
			for(int i=0 ; i < lst.size() ; i++){
				String colName = lst.get(i);
				cell = dataRow.createCell(i);
				System.out.println(resultSet.getString(colName));
				cell.setCellValue(resultSet.getString(colName));
			}
			row = row + 1;
		}
		return row;
	}

	/**
	 * 查询数据 2016年4月15日 By 王彬彬
	 * 
	 * @param queryMap
	 * @return
	 */
	public ResultSet getData(Object queryMap, Class<?> daoClass,
			String methodName) {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Connection connection = sqlSession.getConnection();

		MyBatisSql batisSql;
		try {
			batisSql = MyBatisSqlUtil.getMyBatisSql(daoClass.getName() + "."
					+ methodName, queryMap, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql
					.toString());
			ResultSet resultSet = ps.executeQuery();

			return resultSet;
		} catch (SQLException e) {
			logger.error("查询数据失败");
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("数据连接close异常");
			}
		}
		return null;
	}
	
	/**
	 * Excel 导入(获取导入数据列表) 2017年04月11日 By zmq
	 * 主要用于交割数据读取，标题行有多行
	 * @param path
	 *            导入文件
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @param sheetIndex
	 *            工作表编号
	 * @param cls
	 *            输出对象Class
	 * @param groups
	 */
	public List<?> importExcel(String type,File path, Integer headerNum,
			Integer sheetIndex, Class<?> cls, int... groups) {
		try {
			ImportExcel importExcel = new ImportExcel(path, headerNum,
					sheetIndex);
			List<?> dataList =importExcel.getDataListByJiaoGe(cls, groups);
			return dataList;
		} catch (InvalidFormatException e) {
			logger.error("文件格式异常" + e.getLocalizedMessage(), e);
			return null;
		} catch (IOException e) {
			logger.error("文件导入失败" + e.getLocalizedMessage(), e);
			return null;
		} catch (InstantiationException e) {
			logger.error("文件初始化异常" + e.getLocalizedMessage(), e);
			return null;
		} catch (IllegalAccessException e) {
			logger.error("文件访问异常" + e.getLocalizedMessage(), e);
			return null;
		} catch(Exception e){
			try {
				ImportExcel importExcel = new ImportExcel(path.getName()+"x", new FileInputStream(path), headerNum, sheetIndex);
				List<?> dataList = importExcel.getDataList(cls, groups);
				return dataList;
			} catch (Exception e2) {
				logger.error("文件导入失败" + e.getLocalizedMessage(), e);
				return null;
			}
		}

	}
	
}
