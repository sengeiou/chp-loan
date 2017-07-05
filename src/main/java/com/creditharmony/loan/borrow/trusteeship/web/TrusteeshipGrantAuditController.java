package com.creditharmony.loan.borrow.trusteeship.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.excel.util.ExportExcel;
import com.creditharmony.core.excel.util.ImportExcel;
import com.creditharmony.core.loan.type.TrustmentStatus;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.util.GrantExcelExportUtil;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel2;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel4;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel5;
import com.creditharmony.loan.borrow.trusteeship.service.TrusteeshipService;
import com.google.common.collect.Lists;

/**
 * 资金托管待放款审核
 * @Class Name TrusteeshipGrantAuditController
 * @author 朱静越
 * @Create In 2017年1月19日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/trustee/grantAudit")
public class TrusteeshipGrantAuditController extends BaseController {
	
	@Autowired
	private TrusteeshipService trusteeshipService;

	/**
	 * 线下委托提现导出——放款审核列表 
	 * 2017年1月19日
	 * By 朱静越
	 * @param codes
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportEntrustReflect")
	public void exportEntrustReflect(String[] codes,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GrantExcel2> dataList = Lists.newArrayList();
		if (null != codes && codes.length > 0) {
			map.put("contractCodes", codes);
			map.put("trustCash", TrustmentStatus.YWT.getCode());
			dataList = trusteeshipService.getExcel2(map);
		}
		List<GrantExcel4> excel4List = GrantExcelExportUtil.assembleExcelDataList(dataList);
		ExportExcel exportExcel = new ExportExcel("", GrantExcel4.class);
		exportExcel.setDataList(excel4List);
		try {
			exportExcel.write(response,
					"PWTX_"+DateUtils.getDate("yyyyMMdd")+"_0000" + ".xlsx");
			exportExcel.dispose();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("方法:exportEntrustReflect,列表导出失败", e);
		}
	}
	
	
	/**
	 * 线下委托提现导入 
	 * 2016年3月13日
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("importExcel")
	@ResponseBody
	public String importExcel(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file) {
		String result = "";
		try {
			if (null != file) {
				ImportExcel excel = new ImportExcel(file, 0, 0);
				List<GrantExcel5> list = excel.getDataList(GrantExcel5.class, 1);
				if (ArrayHelper.isNotEmpty(list)) {
					for (int i=0;i<list.size();i++) {
						try{
							trusteeshipService.importExcel(list.get(i));
						}
						catch (Exception e) {
							logger.error("放款审核委托提现导入失败",e);
							result += "第" + (i+1) + "行数据导入失败;";
						}
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("放款审核委托提现导入文件失败",e);
			result = "文件导入失败";
		} finally{
			return result;
		}
	}
}
