package com.creditharmony.loan.borrow.secretvisit.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.secretvisit.entity.ex.SecretInfoEx;
import com.creditharmony.loan.borrow.secretvisit.entity.ex.SecretVisitEx;
import com.creditharmony.loan.borrow.secretvisit.service.SecretVisitService;
import com.creditharmony.loan.borrow.secretvisit.view.SecretView;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.ImageService;

/**
 * 暗访信息
 * @Class Name SecretVisitController
 * @author 王彬彬
 * @Create In 2015年12月26日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/secret")
public class SecretVisitController extends BaseController {

	@Autowired
	private SecretVisitService secretVisitService;

	@Autowired
    private ImageService imageService;

	/**
	 * 门店查询列表 2015年12月28日 By 王彬彬
	 * @param request
	 * @param response
	 * @param model
	 * @param secret 
	 * @return 查询信息
	 */
	@RequestMapping(value = "secretlist")
	public String stroesAllotList(HttpServletRequest request,
			HttpServletResponse response, Model model, SecretView secret) {

		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("certiNO", secret.getCertiNo());
		filter.put("customerName", secret.getCustomerName());
		filter.put("managerCode", secret.getManagerCode());
		filter.put("productName", secret.getProductName());
		filter.put("store", secret.getStore());
		filter.put("teamManager", secret.getTeamManager());
		//数据权限控制
		 String queryRight = DataScopeUtil.getDataScope("b", SystemFlag.LOAN.value);
		 filter.put("queryRight", queryRight);
		Page<SecretVisitEx> secretPage = secretVisitService.findloaninfo(
				new Page<SecretVisitEx>(request, response), filter);
		// 缓存获取码值
		List<SecretVisitEx> list = secretPage.getList();
		for (SecretVisitEx secretVisitEx : list) {
			secretVisitEx.setLoanStatus(DictCache.getInstance().getDictLabel("jk_loan_apply_status", secretVisitEx.getLoanStatus()));
			secretVisitEx.setLoanUrgentFlag(DictCache.getInstance().getDictLabel("jk_urgent_flag", secretVisitEx.getLoanUrgentFlag()));
			secretVisitEx.setConsTelesalesFlag(DictCache.getInstance().getDictLabel("jk_telemarketing", secretVisitEx.getConsTelesalesFlag()));
			secretVisitEx.setVisitFlagLabel(DictCache.getInstance().getDictLabel("jk_visit_flag", secretVisitEx.getVisitFlag()));
			secretVisitEx.setLoanFlag(DictCache.getInstance().getDictLabel("jk_channel_flag", secretVisitEx.getLoanFlag()));			
		}		
		model.addAttribute("secretPage", secretPage);
		model.addAttribute("secret", secret);
		return "borrow/secretvisit/secretlist";
	}
	
	/**
	 * 暗访挖成,改变暗访标识,并返回暗访列表页面
	 * 2015年12月31日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 暗访列表
	 */
	@RequestMapping(value = "updateSecret")
	public String updateSecret(String loanCode) {
		secretVisitService.updateSecret(loanCode);
		return "redirect:" + adminPath + "/borrow/secret/secretlist";
	}
	
	/**
	 * 根据合同编号查询暗访信息
	 * 2015年12月30日
	 * By lirui
	 * @param request 获得request 
	 * @param response 获得response
	 * @param m Model模型
	 * @param contractCode 合同编号
	 * @return 暗访信息页面
	 */
	@RequestMapping(value = "getInfoByCode")
	public String getInfoByCode(HttpServletRequest request,HttpServletResponse response,Model m,String contractCode) {
		SecretInfoEx secretInfo = secretVisitService.secretInfo(contractCode);
        String imageUrl = imageService.getImageUrl(FlowStep.SECRET_SHOOTING.getName(),secretInfo.getLoanCode());
        secretInfo.setImageUrl(imageUrl);
		Page<SecretInfoEx> supplyPage = secretVisitService.supplyInfo(new Page<SecretInfoEx>(request, response), contractCode);
		List<SecretInfoEx> list = supplyPage.getList();
		for (SecretInfoEx secretInfoEx : list) {
			secretInfoEx.setIsOverdue(DictCache.getInstance().getDictLabel("jk_overdue_flag", secretInfoEx.getIsOverdue()));
		}
		m.addAttribute("secretInfo", secretInfo);
		m.addAttribute("supplyPage", supplyPage);
		return "borrow/secretvisit/secretInfo";
	}
}
