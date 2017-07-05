package com.creditharmony.loan.borrow.trusteeship.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Global;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.excel.util.ExportExcel;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.TrustGrantOutputStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.service.GrantService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel1;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel2;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel3;
import com.creditharmony.loan.borrow.trusteeship.service.TrusteeshipService;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 资金托管待放款
 * 
 * @Class Name TrusteeshipGrantController
 * @author 孙凯文
 * @Create In 2016年3月1日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/trustee/grant")
public class TrusteeshipGrantController extends BaseController {
	@Autowired
	private TrusteeshipService trusteeshipService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantService grantService;

	/**
	 * 导出页面条件的数据
	 * 2016年3月16日
	 * By 孙凯文
	 * @param model
	 * @param page
	 * @param grtQryParam
	 * @param excelName
	 * @param listFlag
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "exportExcel")
	public String exportExcel(Model model,
			LoanFlowQueryParam grtQryParam, String excelName, String listFlag,
			HttpServletResponse response) {

		try {
			if (LoanModel.TG.getName().equals(listFlag)) {
				grtQryParam.setModel(LoanModel.TG.getCode());
				grtQryParam.setTgFlag(YESNO.YES.getCode());
			}
			GrantUtil.setStoreOrgIdQuery(grtQryParam);
			List<LoanFlowWorkItemView> workItems = grantService.getGrantLists(grtQryParam);
			List<String> list = new ArrayList<String>();
			if (ArrayHelper.isNotEmpty(workItems)) {
				for (LoanFlowWorkItemView item : workItems) {
					list.add(item.getContractCode());
				}
			}
			if (ArrayHelper.isNotEmpty(list)) {
				if ("1".equals(excelName)) {
					//导出Excel1
					String[] codes = (String[]) list.toArray(new String[list.size()]);
					exportExcel1(codes, null, response);
				} else if ("2".equals(excelName)) {
					//导出Excel2
					String[] codes = (String[]) list.toArray(new String[list.size()]);
					exportExcel2(codes, null, response);
				} else if ("3".equals(excelName)) {
					//导出解冻1
					String[] codes = (String[]) list.toArray(new String[list.size()]);
					exportExcel3(codes, null, response);
				} else if ("4".equals(excelName)) {
					//导出解冻2
					String[] codes = (String[]) list.toArray(new String[list.size()]);
					exportExcel4(codes, null, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：exportExcel导出excel出现异常");
		}
		return null;
	}

	/**
	 * 导出Excel1
	 * 2016年3月16日
	 * By 孙凯文
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportExcel1")
	public void exportExcel1(String[] code, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> param = new HashMap<String, Object>();
		if (null != code && code.length > 0) {
			param.put("contractCodes", code);
		}
		List<GrantExcel1> dataList = trusteeshipService.getExcel1(param);
		if (ArrayHelper.isNotEmpty(dataList)) {
			String payAccountName = Global.getConfig("jzh_fk_account");
			String payChinaName = Global.getConfig("jzh_fk_name");

			for (int i = 0; i < dataList.size(); i++) {
				dataList.get(i).setIndex((i + 1) + "");
				dataList.get(i).setPayLoginName(payAccountName);
				dataList.get(i).setPayChinaName(payChinaName);
				dataList.get(i).setPayFundFreeze("否");
				dataList.get(i).setReceiveFundFreeze("是");
				
				String contract = dataList.get(i).getTradeMoney();
				BigDecimal tradeMoney = (null != contract && !"".equals(contract)) ? 
						new BigDecimal(contract): new BigDecimal("0");
				dataList.get(i).setMark(dataList.get(i).getMark()+"借款");
				String contract1 = dataList.get(i).getUrgedServiceFee();
				BigDecimal urgedServiceFee = (null != contract1 && !"".equals(contract1)) ? 
								new BigDecimal(contract1): new BigDecimal("0");
				dataList.get(i).setTradeMoney(tradeMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				dataList.get(i).setUrgedServiceFee(urgedServiceFee.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				String model=DictCache.getInstance().getDictLabel("jk_contract_ver",dataList.get(i).getContractVersion());
				dataList.get(i).setContractVersion(model);
			}
			ExportExcel exportExcel = new ExportExcel(null,GrantExcel1.class);
			// 设置导出数据源
			exportExcel.setDataList(dataList);
			try {
				//更新工作流中的导出状态
				for (GrantExcel1 excel1 : dataList) {
					this.updateOutputStatus(excel1.getContractCode(), TrustGrantOutputStatus.EXCEL1.getCode());
				}
				// 写出文件到客户端
				exportExcel.write(response,
						"PW03_" + DateUtils.getDate("yyyyMMdd")+ "_0000.xlsx");
				exportExcel.dispose();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("列表导出失败", e);
			}
		}
	}

	/**
	 * 导出Excel2
	 * 服务费+催收服务费
	 * 2016年3月16日
	 * By 孙凯文
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportExcel2")
	public void exportExcel2(String[] code, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> param = new HashMap<String, Object>();
		if (null != code && code.length > 0) {
			param.put("contractCodes", code);
		}
		//ExcelExportExcel2Helper.exportData(param, response);
		List<GrantExcel2> dataList = trusteeshipService.getExcel2(param);
		if (ArrayHelper.isNotEmpty(dataList)) {

			List<GrantExcel2> dataListOut = new ArrayList<GrantExcel2>();
			String loginName = Global.getConfig("jzh_sk_account");
			String chinaName = Global.getConfig("jzh_sk_name");

			GrantExcel2 excel1 = null;
			GrantExcel2 excel2 = null;
			int index=1;
			for (GrantExcel2 temp : dataList) {
				excel1 = new GrantExcel2();
				excel2 = new GrantExcel2();

				BeanUtils.copyProperties(temp, excel1);
				BeanUtils.copyProperties(temp, excel2);

				String contract = temp.getTradeMoney();
				BigDecimal contractMoney = (null != contract && !"".equals(contract)) ? 
						new BigDecimal(contract): new BigDecimal("0");
				String grant = temp.getGrantMoney();
				BigDecimal grantMoney = (null != grant && !"".equals(grant)) ? 
						new BigDecimal(grant) : new BigDecimal("0");
				String urgedServiceFee = temp.getUrgedServiceFee();
				BigDecimal urgedServiceFeeMoney = (null != urgedServiceFee && !"".equals(urgedServiceFee)) ? 
						new BigDecimal(urgedServiceFee) : new BigDecimal("0");

				/*excel2.setTradeMoney(contractMoney.subtract(grantMoney)
						.add(urgedServiceFeeMoney).toString());*/
				

				
				excel1.setUrgedServiceFee(urgedServiceFeeMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				excel2.setUrgedServiceFee(urgedServiceFeeMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				excel1.setIndex((index++) + "");
				excel2.setIndex((index++) + "");
				excel1.setReceiveFundFreeze(YESNO.NO.getName());
				excel2.setReceiveFundFreeze(YESNO.NO.getName());
				excel1.setPayFundFreeze(YESNO.NO.getName());
				excel2.setPayFundFreeze(YESNO.NO.getName());
				excel1.setReceiveLoginName(loginName);
				excel1.setReceiveChinaName(chinaName);
				excel2.setReceiveLoginName(loginName);
				excel2.setReceiveChinaName(chinaName);
				// 催收服务费
				excel1.setTradeMoney(urgedServiceFeeMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				// 服务费=合同金额-放款金额
				excel2.setTradeMoney(contractMoney.subtract(grantMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				excel1.setMark(excel1.getMark() + "催收服务费");				
				excel2.setMark(excel2.getMark() + "服务费");
				
				dataListOut.add(excel1);
				dataListOut.add(excel2);

			}
			ExportExcel exportExcel = new ExportExcel(null,GrantExcel2.class);
			// 设置导出数据源
			exportExcel.setDataList(dataListOut);
			try {
				//更新工作流中的导出状态
				for (GrantExcel2 elem : dataList) {
					this.updateOutputStatus(elem.getContractCode(), TrustGrantOutputStatus.EXCEL2.getCode());
				}
				// 写出文件到客户端
				exportExcel.write(response,
						"PWCV_" + DateUtils.getDate("yyyyMMdd")+ "_0000.xlsx");
				exportExcel.dispose();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("列表导出失败", e);
			}
		}
		 
	}

	/**
	 * 导出解冻1
	 * 2016年3月16日
	 * By 孙凯文
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportExcel3")
	public void exportExcel3(String[] code, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> param = new HashMap<String, Object>();
		if (null != code && code.length > 0) {
			param.put("contractCodes", code);
		}
		//ExcelExportExcel3Helper.exportData(param, response);
		List<GrantExcel1> dataList = trusteeshipService.getExcel1(param);
		if (ArrayHelper.isNotEmpty(dataList)) {

			List<GrantExcel3> dataList1 = new ArrayList<GrantExcel3>();			
			GrantExcel3 excel3 = null;
			int index=1;
			for (GrantExcel1 excel1 : dataList) {
				excel3 = new GrantExcel3();
				BeanUtils.copyProperties(excel1, excel3);

				String contract = excel1.getTradeMoney();
				BigDecimal contractMoney = (null != contract && !"".equals(contract)) ? 
						new BigDecimal(contract) : new BigDecimal("0");
				String grant = excel1.getGrantMoney();
				BigDecimal grantMoney = (null != grant && !"".equals(grant)) ?
						new BigDecimal(grant) : new BigDecimal("0");
				String urgedServiceFee = excel1.getUrgedServiceFee();
				BigDecimal urgedServiceFeeMoney = (null != urgedServiceFee && !"".equals(urgedServiceFee)) ? 
						new BigDecimal(urgedServiceFee) : new BigDecimal("0");

				excel3.setTradeMoney(contractMoney.subtract(grantMoney)
						.add(urgedServiceFeeMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				excel3.setMark(excel3.getMark()+excel3.getTradeMoney());
				excel3.setIndex((index++)+"");
				dataList1.add(excel3);

			}
			ExportExcel exportExcel = new ExportExcel(null,GrantExcel3.class);
			// 设置导出数据源
			exportExcel.setDataList(dataList1);
			try {
				//更新工作流中的导出状态
				for (GrantExcel1 elem : dataList) {
					this.updateOutputStatus(elem.getContractCode(), TrustGrantOutputStatus.THAW1.getCode());
				}
				// 写出文件到客户端
				exportExcel.write(response,
								"PWJD_" + DateUtils.getDate("yyyyMMdd")+ "_0001.xlsx");
				exportExcel.dispose();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("列表导出失败", e);
			}
		}
		
		
	}

	/**
	 * 导出解冻2
	 * 2016年3月16日
	 * By 孙凯文
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportExcel4")
	public void exportExcel4(String[] code, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> param = new HashMap<String, Object>();
		if (null != code && code.length > 0) {
			param.put("contractCodes", code);
		}
		//ExcelExportExcel4Helper.exportData(param, response);
		List<GrantExcel1> dataList = trusteeshipService.getExcel1(param);
		if (ArrayHelper.isNotEmpty(dataList)) {

			List<GrantExcel3> dataList1 = new ArrayList<GrantExcel3>();
			GrantExcel3 excel3 = null;
			int index=1;
			for (GrantExcel1 excel1 : dataList) {
				excel3 = new GrantExcel3();

				BeanUtils.copyProperties(excel1, excel3);

				String grant = excel1.getGrantMoney();
				BigDecimal grantMoney = (null != grant && !"".equals(grant)) ? 
						new BigDecimal(grant) : new BigDecimal("0");
				String urgedServiceFee = excel1.getUrgedServiceFee();
				BigDecimal urgedServiceFeeMoney = (null != urgedServiceFee && !"".equals(urgedServiceFee)) ? 
						new BigDecimal(urgedServiceFee) : new BigDecimal("0");
				excel3.setTradeMoney(grantMoney.subtract(urgedServiceFeeMoney).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				excel3.setMark(excel3.getMark()+excel3.getTradeMoney());
				excel3.setIndex((index++) + "");
				dataList1.add(excel3);
			}
			ExportExcel exportExcel = new ExportExcel(null,GrantExcel3.class);
			// 设置导出数据源
			exportExcel.setDataList(dataList1);
			try {
				//更新工作流中的导出状态
				for (GrantExcel1 excel1 : dataList) {
					this.updateOutputStatus(excel1.getContractCode(), TrustGrantOutputStatus.THAW2.getCode());
				}
				
				// 写出文件到客户端
				exportExcel.write(response,
								"PWJD_" + DateUtils.getDate("yyyyMMdd")+ "_0002.xlsx");
				exportExcel.dispose();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("列表导出失败", e);
			}
		}
		
		
	}

	/**
	 * 更新导出文件状态 
	 * 2016年3月16日
	 * By 朱杰
	 * @param applyId
	 * @param status 1：Excel1 2:Excel2 3:解冻1 4:解冻2
	 */
	private void updateOutputStatus(String contractCode,String status){
		LoanGrant loanGrant = new LoanGrant();
		loanGrant.setContractCode(contractCode);
		loanGrant.setTrustGrantOutputStatus(status);
		loanGrantService.updateLoanGrant(loanGrant);
	}
	
	/**
	 * 批量放款完成，将资金托管文件导出的状态为解冻2的数据放款成功到放款审核
	 * 2016年3月16日
	 * By 朱杰
	 * @param model
	 * @param grtQryParam 查询条件
	 * @param listFlag TG
	 * @param code 合同编号
	 * @param request
	 */
	@RequestMapping("grantToNext")
	@ResponseBody
	public void grantToNext(Model model,
			LoanFlowQueryParam grtQryParam, String listFlag,String[] code,
 HttpServletRequest request) {
		try {
			// 勾选的数据
			if (code != null) {
				grtQryParam.setContractCodes(code);
			} 
			grtQryParam.setModel(LoanModel.TG.getCode());
			grtQryParam.setTgFlag(YESNO.YES.getCode());
			grtQryParam
					.setTrustGrantOutputStatus(TrustGrantOutputStatus.THAW2
							.getCode()); // 状态是4[解冻2]
			GrantUtil.setStoreOrgIdQuery(grtQryParam);
			List<LoanFlowWorkItemView> workItems = grantService
					.getGrantLists(grtQryParam);
			if (ArrayHelper.isNotEmpty(workItems)) {
				for (LoanFlowWorkItemView item : workItems) {
					trusteeshipService.trueeshipGrant(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：grantToNext，资金托管批量放款完成出现异常：", e);
		}
	}
}
