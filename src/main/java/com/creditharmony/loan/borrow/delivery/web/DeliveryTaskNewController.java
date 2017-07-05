package com.creditharmony.loan.borrow.delivery.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryReq;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewExNew;
import com.creditharmony.loan.borrow.delivery.service.DeliveryTaskNewService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "${adminPath}/borrow/taskDelivery")
public class DeliveryTaskNewController extends BaseController{

	@Autowired
	private DeliveryTaskNewService des;
	/**
	* @Title: queryDeliveryList
	* @Description: TODO(查询导入流转到匹配列表的数据)
	* @param @param request
	* @param @param response
	* @param @param params
	* @param @param m
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping(value = "queryDeliveryList")
	public String queryDeliveryList(HttpServletRequest request,HttpServletResponse response,DeliveryReq params,Model m) {
		String message = request.getParameter("message");
		m.addAttribute("message",message);
		String custName=request.getParameter("custName");
		String custCode=request.getParameter("custCode");
		String deliveryResult=request.getParameter("deliveryResult");
		String deliveryResultOne="1";
		String deliveryResultTwo="2";
		int countSuccess = des.queryDeliveryListCount(deliveryResultOne);
		m.addAttribute("countSuccess", countSuccess);
		int countFalse = des.queryDeliveryListCount(deliveryResultTwo);
		m.addAttribute("countFalse", countFalse);
		// 如果搜索条件不为空 ,将检索条件存到session中
		if ((custName!=null && !"".equals(custName)) || (custCode!=null && !"".equals(custCode)) || (deliveryResult!=null && !"".equals(deliveryResult))){
				m.addAttribute("params",params);
				// 获得交割已办列表
				Page<DeliveryViewExNew> taskPage = des.queryDeliveryList(new Page<DeliveryViewExNew>(request, response), params);	
				m.addAttribute("taskPage", taskPage);	
		}
		return "apply/delivery/deliveryTaskNew";
	}
	
	/**
	* @Title: exportExcel
	* @Description: TODO(导出交割数据选中或查询出的数据)
	* @param @param request
	* @param @param response
	* @param @param params
	* @param @param redirectAttributes
	* @param @return
	* @param @throws Exception    设定文件
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping(value = "exportExcel")
	public String exportExcel(HttpServletRequest request, HttpServletResponse response,DeliveryReq params,RedirectAttributes redirectAttributes) throws Exception{
		String message = null;
		try {
			User user = UserUtils.getUser();
			logger.debug("匹配列表导出开始：" + DateUtils.getDate("yyyyMMddHHmmss"));
			ExcelUtils excelutil = new ExcelUtils();
			List<DeliveryViewExNew> list = Lists.newArrayList();
			// 如果有进行选择，获得选中单子的list
			String[] checkIds = params.getCheckIds();
			if (ArrayHelper.isNotNull(checkIds)) {
				DeliveryReq deliveryReq = new DeliveryReq();
				deliveryReq.setCheckIds(checkIds);
				list = des.exportList(deliveryReq);
			} else {
				list = des.exportList(params);
			}
			final int MAXCOLUMN = 20;
			String[] header={"序号","客户名称(必填)","合同编号(必填)","原所属信息","","","","","","","现归属信息","","","","","","","交割状态","失败原因"};
			String[] headerOne={"","","","交割前所属门店( 门店名称)","交割前团队经理(交割团队经理会同时交割所属团队)","交割前团队经理员工号(交割团队经理会同时交割所属团队)","交割前客户经理","交割前客户经理员工号","交割前客服人员","交割前客服人员员工号",
					"交割后所属门店( 门店名称)","交割后团队经理(交割团队经理会同时交割所属团队)","交割后团队经理员工号(交割团队经理会同时交割所属团队)","交割后客户经理","交割后客户经理员工号","交割后客服人员","交割后客服人员员工号","",""};
			//ExportGrantDoneTGHelper.exportData(map,header,response);
			String fileName ="数据交割明细";
			//创建workbook
			SXSSFWorkbook wb = new SXSSFWorkbook();
			 //创建sheet页
			Sheet dataSheet = wb.createSheet("交割模板");
			wrapperHeader(wb,dataSheet,fileName,header,headerOne);
			
			assembleExcelCell(wb,list,dataSheet,MAXCOLUMN);
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ Encodes.urlEncode("数据交割模板--前线和后线合并"+ FileExtension.XLSX)
							+ ";filename*=UTF-8''"
							+ Encodes.urlEncode("数据交割模板--前线和后线合并"+ FileExtension.XLSX));
			wb.write(response.getOutputStream());
			wb.dispose(); 
			logger.info("匹配列表导出结束：" + DateUtils.getDate("yyyyMMddHHmmss"));
		} catch (IOException e) {
			logger.error("文件输出异常（IO）" + e.getLocalizedMessage(), e);
			message = "导出错误";
			redirectAttributes.addAttribute("message", message);
		} catch (Exception e) {
			logger.error("方法exportExcel：导出数据交割结果,导出错误"+e);
			message = "导出错误";
			redirectAttributes.addAttribute("message", message);
		}
		return null;
	}
	
	// 导出标题设置
	@SuppressWarnings("deprecation")
	private static void wrapperHeader(SXSSFWorkbook wb,Sheet dataSheet,String fileName,String[] header,String[] headerOne) {
		Row titleRow = dataSheet.createRow(0);
		titleRow.setHeight((short) (15.625*40)); 
		Cell titleCell = titleRow.createCell(0);
		dataSheet.addMergedRegion(new CellRangeAddress(0,0,0,header.length-1));
		//创建样式
		CellStyle style = wb.createCellStyle(); 
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直   
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平
		
        //创建字体
        Font font = wb.createFont();
        //字体位置  上 下 左 右
        //font.setTypeOffset((short)0);
        //字体宽度
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //字体高度
        font.setFontHeightInPoints((short)16);
        style.setFont(font);
        titleCell.setCellValue(fileName);
        titleCell.setCellStyle(style);
        
        style = wb.createCellStyle();
        style.setBorderBottom((short) 1);   
        style.setBorderTop((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);
        style.setFillPattern(CellStyle.FINE_DOTS);
        style.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());
        font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(new HSSFColor.WHITE().getIndex());
        font.setFontHeightInPoints((short)11);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直   
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        
		Row headerRow = dataSheet.createRow(1); //为标题创建一行
		/*for (int i = 0; i < header.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}*/
		for (int i = 0; i < 3; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
		for (int i = 3; i < 10; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
		for (int i = 10; i < 17; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
		for (int i = 17; i < header.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			headerCell.setCellValue(header[i]);
		}
		Row headerRowOne = dataSheet.createRow(2); //为标题创建一行
		for (int i = 0; i < headerOne.length; i++) {
			Cell headerOneCell = headerRowOne.createCell(i);
			headerOneCell.setCellStyle(style);
			headerOneCell.setCellValue(headerOne[i]);
		}
		
		dataSheet.addMergedRegion(new CellRangeAddress(1,2,0,0)); //起始行号  终止行号  起始列号  终止列号
		dataSheet.addMergedRegion(new CellRangeAddress(1,2,1,1)); //起始行号  终止行号  起始列号  终止列号
		dataSheet.addMergedRegion(new CellRangeAddress(1,2,2,2)); //起始行号  终止行号  起始列号  终止列号
		dataSheet.addMergedRegion(new CellRangeAddress(1,1,3,9)); //起始行号  终止行号  起始列号  终止列号
		dataSheet.addMergedRegion(new CellRangeAddress(1,1,10,16)); //起始行号  终止行号  起始列号  终止列号
		dataSheet.addMergedRegion(new CellRangeAddress(1,2,header.length-2,header.length-2)); //起始行号  终止行号  起始列号  终止列号
		dataSheet.addMergedRegion(new CellRangeAddress(1,2,header.length-1,header.length-1)); //起始行号  终止行号  起始列号  终止列号
		
	}
	//导出数据方法
	private static void assembleExcelCell(SXSSFWorkbook wb,List<DeliveryViewExNew> list,Sheet dataSheet,int MAXCOLUMN) throws SQLException {
		int row = 3;
		int i=1;
		CellStyle style = wb.createCellStyle();
	        style.setBorderBottom((short) 1);   
	        style.setBorderTop((short) 1);
	        style.setBorderLeft((short) 1);
	        style.setBorderRight((short) 1);
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)10);
	        style.setFont(font);
		
		Row dataRow;
		for (DeliveryViewExNew deliveryPram : list) {
			dataRow = dataSheet.createRow(row);
			
			Cell numCell = dataRow.createCell(0);
			numCell.setCellStyle(style);
			numCell.setCellValue(i);
			
			Cell loanCustomerNameCell = dataRow.createCell(1);
			loanCustomerNameCell.setCellStyle(style);
			loanCustomerNameCell.setCellValue(deliveryPram.getLoanCustomerName());
			
			Cell contractCodeCell = dataRow.createCell(2);
			contractCodeCell.setCellStyle(style);
			contractCodeCell.setCellValue(deliveryPram.getContractCode());
			
			Cell storesNameCell = dataRow.createCell(3);
			storesNameCell.setCellStyle(style);
			storesNameCell.setCellValue(deliveryPram.getStoresName());
			
			Cell teamManagerNameCell = dataRow.createCell(4);
			teamManagerNameCell.setCellStyle(style);
			teamManagerNameCell.setCellValue(deliveryPram.getTeamManagerName());
			
			Cell teamManagerCodeCell = dataRow.createCell(5);
			teamManagerCodeCell.setCellStyle(style);
			teamManagerCodeCell.setCellValue(deliveryPram.getTeamManagerCode());
			
			Cell managerNameCell = dataRow.createCell(6);
			managerNameCell.setCellStyle(style);
			managerNameCell.setCellValue(deliveryPram.getManagerName());
			
			Cell managerCodeCell = dataRow.createCell(7);
			managerCodeCell.setCellStyle(style);
			managerCodeCell.setCellValue(deliveryPram.getManagerCode());
			
			Cell customerServicesNameCell = dataRow.createCell(8);
			customerServicesNameCell.setCellStyle(style); 
			customerServicesNameCell.setCellValue(deliveryPram.getCustomerServicesName());
			
			Cell customerServicesCodeCell = dataRow.createCell(9);
			customerServicesCodeCell.setCellStyle(style);
			customerServicesCodeCell.setCellValue(deliveryPram.getCustomerServicesCode());
			
			Cell newStoresNameCell = dataRow.createCell(10);
			newStoresNameCell.setCellStyle(style);
			newStoresNameCell.setCellValue(deliveryPram.getNewStoresName());
			
			Cell newTeamManagerNameCell = dataRow.createCell(11);
			newTeamManagerNameCell.setCellStyle(style);
			newTeamManagerNameCell.setCellValue(deliveryPram.getNewTeamManagerName());
			
			Cell newTeamManagerCodeCell = dataRow.createCell(12);
			newTeamManagerCodeCell.setCellStyle(style);
			newTeamManagerCodeCell.setCellValue(deliveryPram.getNewTeamManagerCode());
			
			Cell newManagerNameCell = dataRow.createCell(13);
			newManagerNameCell.setCellStyle(style);
			newManagerNameCell.setCellValue(deliveryPram.getNewManagerName());
			
			Cell newManagerCodeCell = dataRow.createCell(14);
			newManagerCodeCell.setCellStyle(style);
			newManagerCodeCell.setCellValue(deliveryPram.getNewManagerCode());
			
			Cell newCustomerServicesNameCell = dataRow.createCell(15);
			newCustomerServicesNameCell.setCellStyle(style);
			newCustomerServicesNameCell.setCellValue(deliveryPram.getNewCustomerServicesName());
			
			Cell newCustomerServicesCodeCell = dataRow.createCell(16);
			newCustomerServicesCodeCell.setCellStyle(style);
			newCustomerServicesCodeCell.setCellValue(deliveryPram.getNewCustomerServicesCode());
			
			String deliveryResuilLable="";
			if("1".equals(deliveryPram.getDeliveryResult())){
				deliveryResuilLable="成功";
			}
			if("2".equals(deliveryPram.getDeliveryResult())){
				deliveryResuilLable="失败";
			}
			Cell deliveryResultCell = dataRow.createCell(17);
			deliveryResultCell.setCellStyle(style);
			deliveryResultCell.setCellValue(deliveryResuilLable);
			
			Cell rejectedReasonCell = dataRow.createCell(18);
			rejectedReasonCell.setCellStyle(style);
			rejectedReasonCell.setCellValue(deliveryPram.getRejectedReason());
			
			row = row + 1;
			i++;
		}
		setAutoColumn(MAXCOLUMN, dataSheet);
	}
				
	/**
	 * 设置Excel表格的自适应功能(由于poi中的自动适应除英文、数字外的其他字符不支持自动适应功能)
	 * @param maxColumn 列数
	 * @param sheet Sheet表格
	 */
	private static void setAutoColumn (int maxColumn,Sheet sheet) {
		//获取当前列的宽度，然后对比本列的长度，取最大值  
		for (int columnNum = 0; columnNum <= maxColumn; columnNum++) {  
		    int columnWidth = sheet.getColumnWidth(columnNum) / 256;  
		    for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {  
		        Row currentRow;  
		        //当前行未被使用过  
		        if (sheet.getRow(rowNum) == null) {  
		            currentRow = sheet.createRow(rowNum);  
		        } else {  
		            currentRow = sheet.getRow(rowNum);  
		        }  
		        if(currentRow.getCell(columnNum) != null) {  
		            Cell currentCell = currentRow.getCell(columnNum);  
		             if(currentCell !=null){
		            	 String cellStr = currentCell.toString();
		            	 if (StringUtils.isNotEmpty(cellStr)) {
		            		 int length = cellStr.getBytes().length; 
			                  if (columnWidth < length) {  
			                    columnWidth = length;  
			                   }  
		            	 }
		             }
		        }  
		        sheet.setColumnWidth(columnNum, columnWidth * 256);
		    }  
		}  
	}
	
	
}
