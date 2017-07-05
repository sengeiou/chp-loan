package com.creditharmony.loan.borrow.grant.web;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.OperateType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeStatisticsView;
import com.creditharmony.loan.borrow.grant.service.UrgeCheckAuditedService;
import com.creditharmony.loan.borrow.grant.service.UrgeStatisticsViewService;
import com.creditharmony.loan.borrow.grant.util.ExportUrgeStatisticsHelper;
import com.creditharmony.loan.borrow.grant.util.UrgeStatisticsUtil;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.common.consts.SortConst;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ListSortUtil;
import com.google.common.collect.Maps;

/**
 * 催收服务费统计表
 * @Class Name UrgeCheckAuditedController
 * @author 朱静越
 * @Create In 2016年3月1日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/urgeCheckAudited")
public class UrgeCheckAuditedController extends BaseController {
	
	@Autowired
	private UrgeCheckAuditedService urgeCheckAuditedService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private UrgeStatisticsViewService urgeStatisticsViewService;
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	
	/**
	 * 催收服务费跳转列表
	 * 2016年3月2日
	 * By zhangfeng
	 * @return page
	 */
	@RequestMapping(value = "list")
	public String getUrgeAuditedList(HttpServletRequest request,HttpServletResponse response, Model model,
			PaybackTransferOut payBackTransferOut) {
		// 查询催收服务费查账数据
		payBackTransferOut.setRelationType(OperateType.SERVICE_FEE.getCode());
		Page<PaybackTransferOut> urgeAuditedPage = urgeCheckAuditedService
				.getUrgeAuditedList(new Page<PaybackTransferOut>(request,
						response), payBackTransferOut);
		Page<MiddlePerson> middlePersonPage = middlePersonService
				.selectAllMiddle(new Page<MiddlePerson>(request, response),
						null);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();
		for (PaybackTransferOut pt : urgeAuditedPage.getList()) {
			pt.setOutAuditStatusLabel(DictUtils.getLabel(dictMap,
					LoanDictType.BANKSERIAL_CHECK, pt.getOutAuditStatus()));
		}
		model.addAttribute("middlePersonList", middlePersonPage.getList());
		model.addAttribute("paybackTransferOutList", urgeAuditedPage);
		return "borrow/grant/urgeAudited";
	}
	
	/**
	 * 催收服务费统计列表
	 * 2016年4月21日
	 * By shangjunwei
	 * @return page
	 */
	@RequestMapping(value = "urgeStatisticsList")
	public String statisticsList(HttpServletRequest request,HttpServletResponse response, Model model,
			UrgeStatisticsView urgeStatisticsView) {
		//催收服务费统计列表 如果客服登陆不用到到导出按钮权限
		//----------------------------------
	     boolean isCanSe=false;
		 User user = UserUtils.getUser();
   	  List<Role> roleList = user.getRoleList();
 	    if(!ObjectHelper.isEmpty(roleList)){
           for(Role role:roleList){
	    	/*    CITY_MANAGER("6230000001","汇金门店-客服"), */
	    	     if(LoanRole.CUSTOMER_SERVICE.id.equals(role.getId())){
	    	    	 isCanSe = true;
	                 break;
	             }  
	    
           }
        }
 	   model.addAttribute("isCanSe", isCanSe); 
		//----------------------------------
 	   
 	   
 	//数据权限
	   String queryRight = DataScopeUtil.getDataScope("d", SystemFlag.LOAN.value);
	   urgeStatisticsView.setQueryRight(queryRight);
		Page<UrgeStatisticsView> page = urgeStatisticsViewService
				.findUrgeStatisticsView(urgeStatisticsView,
						new Page<UrgeStatisticsView>(request, response));
		Map<String,Dict> dictMap = null;
		if(ArrayHelper.isNotEmpty(page.getList())){
			dictMap = DictCache.getInstance().getMap();
		}
		UrgeStatisticsUtil.wrapperUrgeStatisticsView(page.getList(), dictMap);
		// 汇金产品获取
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("page",page);
		model.addAttribute("param", urgeStatisticsView);
		model.addAttribute("productList", productList);
		return "borrow/grant/urgeStatisticsList";
	}
	
	/**
	 * 催收服务费统计列表导出excel
	 * 2016年4月22日
	 * By shangjunwei
	 * @return 
	 */
	@RequestMapping(value = "excelStatisticsList")
	public void excelStatisticsList(HttpServletRequest request,
			HttpServletResponse response, Model model, String cid,
			UrgeStatisticsView params) {
		Map<String ,Object> queryMap = Maps.newHashMap();
		params.setContractCodes(cid);
		String [] contractCodes={};
		
		if (StringUtils.isNotEmpty(cid)) {
			contractCodes = cid.split(",");
		}
		queryMap.put("params", params);
		queryMap.put("contractCodes", contractCodes);
		ExportUrgeStatisticsHelper.exportData(queryMap, response);
	}

	
	/**
	 * 催收服务费导入银行流水
	 * 2016年1月6日
	 * By zhangfeng
	 * @param file
	 * @param redirectAttributes
	 * @return redirect list
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "importAuditedExl", method = RequestMethod.POST)
	public String importAuditedExl(MultipartFile file, RedirectAttributes redirectAttributes) {
		ExcelUtils excelutil = new ExcelUtils();
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		List<PaybackTransferOut> insertList = new ArrayList<PaybackTransferOut>();
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
		String msg = null;
		String dates=null;
		outList = (List<PaybackTransferOut>) excelutil.importExcel(file, 0, 0, PaybackTransferOut.class);
		if (ArrayHelper.isNotEmpty(outList)) {
			ListSortUtil<PaybackTransferOut> sortList = new ListSortUtil<PaybackTransferOut>();
			sortList.sort(outList, SortConst.ASC, new String[] { "outDepositTime"});
			for (int i = 0; i < outList.size(); i++) {
				if(i + 1 < outList.size()){
					if (StringUtils.equals(String.valueOf(outList.get(i).getOutDepositTime()), String.valueOf(outList.get(i + 1).getOutDepositTime()))) {
						outList.get(i).setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
						outList.get(i).setRelationType(OperateType.SERVICE_FEE.getCode());
						outList.get(i).setOutReallyAmount(new BigDecimal(outList.get(i).getOutReallyAmountStr()));
						outList.get(i).setIsNewRecord(false);
						outList.get(i).preInsert();
						insertList.add(outList.get(i));
						msg = "导入成功!";
					} else {
						if(dates != null){
							dates += ","+formatter.format(outList.get(i+1).getOutDepositTime());
						}else{
							dates =   formatter.format(outList.get(i).getOutDepositTime()) + "," +  formatter.format(outList.get(i+1).getOutDepositTime());
						}
					}
				}else{
					outList.get(i).setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
					outList.get(i).setRelationType(OperateType.SERVICE_FEE.getCode());
					outList.get(i).setOutReallyAmount(new BigDecimal(outList.get(i).getOutReallyAmountStr()));
					outList.get(i).setIsNewRecord(false);
					outList.get(i).preInsert();
					insertList.add(outList.get(i));
					msg = "导入成功!";
				}
			}
			if (ArrayHelper.isNotEmpty(insertList) && dates ==null) {
				urgeCheckAuditedService.insert(insertList);
			}else{
				msg = "导入的到账日期不是同一天的数据,导入日期为:"+dates+"!";
			}
			// 删除临时文件
			File f = new File(file.getOriginalFilename());
			f.delete();
		} else {
			msg = "文档没有数据！";
		}
		addMessage(redirectAttributes, msg);
		return "redirect:" + adminPath + "/borrow/grant/urgeCheckAudited/list";
	}
}
