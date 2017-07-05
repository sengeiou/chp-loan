package com.creditharmony.loan.borrow.payback.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.BankFlatMaintain;
import com.creditharmony.loan.borrow.payback.service.DeductsDueMaintainService;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * 预约银行及时间维护列表业务处理Controller
 * @Class Name DeductsDueMaintainController
 * @author zhaojinping
 * @Create In 2015年12月10日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/deductsDue")
public class DeductsDueMaintainController extends BaseController  {
  
	@Autowired
	private DeductsDueMaintainService deductsDueMaintainService;
	
	/**
	 * 预约银行及平台维护
	 * 2015年12月12日
	 * By zhaojinping
	 * @param request
	 * @param response
	 * @param model
	 * @param pabackDeductsDue
	 * @return page
	 */
	@RequestMapping(value="deductsDueMain")
	public String dueMain(HttpServletRequest request,HttpServletResponse response,Model model,BankFlatMaintain pabackDeductsDue){
		String bankId = pabackDeductsDue.getBankCode();
		if (!ObjectHelper.isEmpty(bankId)) {
			pabackDeductsDue.setBankId(FilterHelper.appendIdFilter(bankId));
		}
		Page<BankFlatMaintain> waitPage = deductsDueMaintainService.getDeductsDue(new Page<BankFlatMaintain>(request, response),pabackDeductsDue);
		List<BankFlatMaintain> list = waitPage.getList();
		List<Dict> dictList = DictCache.getInstance().getList();
		for (BankFlatMaintain bankFlatMaintain : list) {
			for(Dict dict:dictList){
				if("jk_deduct_time".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(bankFlatMaintain.getDeductTime())){
					bankFlatMaintain.setDeductTimeLabel(dict.getLabel());
				}
			}
		}	
		model.addAttribute("pbean", pabackDeductsDue);
		model.addAttribute("waitPage", waitPage);
		logger.debug("invoke DeductsDueMaintainController method: findContractData, consult.id is: "+ waitPage);
		return "borrow/payback/deductsdue/deductsDueMaintain";
	}
	
	/**
	 * 修改批量或实时的状态
	 * 2015年12月12日
	 * By zhaojinping
	 * @param id
	 * @param flag
	 * @return redirect page
	 */
	@RequestMapping(value="realBatch",method=RequestMethod.POST)
	public String realBatch(@RequestParam(value="id",required=false) String id,
			                @RequestParam(value="flag",required=false) String flag){
		if (flag.equals(DeductTime.RIGHTNOW.getCode())) {
			deductsDueMaintainService.realBatch(id);
		} else {
			deductsDueMaintainService.batchReal(id);
		}
		return "redirect:" + adminPath + "/borrow/payback/deductsDue/deductsDueMain";
	}
	
	/**
	 * 批量修改批量或实时的状态
	 * 2015年12月12日
	 * By zhaojinping
	 * @param id
	 * @param flag
	 * @return redirect page
	 */
	@ResponseBody
	@RequestMapping(value="batchUpdate",method=RequestMethod.POST)
	public String batchUpdate(@RequestParam(value="id",required=false) String id,
			@RequestParam(value="flag",required=false) String flag){
		   id = FilterHelper.appendIdFilter(id);
		   BankFlatMaintain bank = new BankFlatMaintain();
		   bank.setId(id);
		   bank.setDeductTime(flag);
		   deductsDueMaintainService.batchUpdate(bank);
		return "success";
	}
}
