package com.creditharmony.loan.borrow.applyinfo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.service.LoanService;

/**
 * 借款申请控制类
 * @author zhangerwei
 * @Create In 2015年12月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/apply")
public class LoanApplyController  extends BaseController {
	
	@Autowired
	private LoanService loanService;
	
	/**
	 * 借款信息上传
	 * 2015年12月28日
	 * By zhangerwei
	 * @param model
	 * @param loanCode
	 * @return
	 */
    @RequestMapping(value = "loadLoanInfoToUpload")
	@ResponseBody
	public String loadLoanInfoToUpload(Model model, String loanCode) {
    	List<LoanCoborrower> loanCoborrower = loanService.selectByLoanCode(loanCode);
    	model.addAttribute("loanCoborrower", loanCoborrower);
    	return "borrow/borrowlist/uploadInformation";
	}
}
