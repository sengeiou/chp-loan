package com.creditharmony.loan.channel.jyj.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.grant.service.GrantDeductsService;
import com.creditharmony.loan.borrow.grant.util.ExportGrantDeductDoneJYJHelper;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 放款划扣已办处理类
 * @Class Name GrantDeductsDoneController
 * @author 朱静越
 * @Create In 2015年12月24日
 */
@Controller
@RequestMapping(value = "${adminPath}/grantDeductsDoneJYJ")
public class GrantDeductsDoneJYJController extends BaseController {

	@Autowired
	private GrantDeductsService grantDeductsService;
	@Autowired
	private CityInfoService cityManager;

	/**
	 * 放款划扣已办，直接从催收主表中查询，催收主表中的回盘结果; 
	 * 以及拆分表中的回盘结果为成功的,同时计算出所有的划扣金额，和总笔数 
	 * 2016年1月6日 By 朱静越
	 * @param model
	 * @param urgeMoneyEx 催收实体
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value = "deductsDoneInfo")
	public String deductsDoneInfo(Model model, UrgeServicesMoneyEx urgeMoneyEx,
			HttpServletRequest request, HttpServletResponse response) {
		GrantUtil.setStoreId(urgeMoneyEx);
		Page<UrgeServicesMoneyEx> urgePage = grantDeductsService
				.selectDeductsJYJSuccess(new Page<UrgeServicesMoneyEx>(request,
						response), urgeMoneyEx);
		List<UrgeServicesMoneyEx> urgeListItemMoneyExs = urgePage.getList();
		BigDecimal totalDeducts = new BigDecimal(0.00);
		Map<String, Dict> dictMap = null;
		LoanModel loanModel = null;
		if (ArrayHelper.isNotEmpty(urgeListItemMoneyExs)) {
			totalDeducts = urgeListItemMoneyExs.get(0).getSumDeductAmount();
			dictMap = DictCache.getInstance().getMap();
			for (UrgeServicesMoneyEx ex : urgeListItemMoneyExs) {
				// 划扣已办中划扣平台转为追回平台LoanDictType.CHANNEL_FLAG
				ex.setCustomerTelesalesFlag(DictUtils.getLabel(dictMap,
						LoanDictType.TELEMARKETING,
						ex.getCustomerTelesalesFlag()));
				loanModel = LoanModel.parseByCode(ex.getModel());
				if(!ObjectHelper.isEmpty(loanModel)){
				    ex.setModelName(loanModel.getName());
				}
			}
		}
		List<CityInfo> provinceList = cityManager.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("totalNum", urgePage.getCount());
		model.addAttribute("deductsAmount", totalDeducts);
		model.addAttribute("UrgeServicesMoneyEx", urgeMoneyEx);
		model.addAttribute("urgeList", urgePage);
		return "borrow/grant/grantDeductsDonejyjList";
	}

	/**
	 * 催收划扣已办导出 
	 * 2016年1月8日 By 朱静越
	 * @param request
	 * @param response
	 * @param cid 要进行导出的单子的催收id
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "exportDeductsDone")
	public String exportDeductsDone(HttpServletRequest request,
			UrgeServicesMoneyEx urgeServicesMoneyEx,
			HttpServletResponse response, String cid,
			RedirectAttributes redirectAttributes) {
		boolean success = true;
		try {
			GrantUtil.setStoreId(urgeServicesMoneyEx);
			// 如果有进行选择，获得选中单子的list
			if (StringUtils.isNotEmpty(cid)) {
				String[] urgeIds = cid.split(",");
				urgeServicesMoneyEx.setUrgeIds(urgeIds);
			} 
			String[] header = {"合同编号","客户姓名","借款类型","信借产品","合同金额","首次放款金额","费用总金额","前期综合服务费","外访费","加急费","催收服务费","未划金额","划扣金额","划扣平台","批借期限","开户行","银行卡号","放款日期","最新划扣日期","回盘结果","标识","是否电销","门店名称"};
			ExportGrantDeductDoneJYJHelper.exportData(urgeServicesMoneyEx, header, response);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			logger.error("催收划扣已办导出发生异常", e);
			addMessage(redirectAttributes, "催收划扣已办导出发生异常");
		} finally{
			if(success){
				return null;
			}else{
				return "redirect:"
		                + adminPath
		                + "/borrow/grant/grantDeductsDone/deductsDoneInfo";
			}
		}
	}

}
