package com.creditharmony.loan.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.service.LoanDeductService;

@Controller
@RequestMapping(value = "${adminPath}/common/bank")
public class BankBranchController {
	
	@Autowired
	private LoanDeductService loanDeductService;
	@RequestMapping(value = "list")
	public String selectStorePage(LoanBank bank, HttpServletRequest request,
			HttpServletResponse response, String isSingle, Model model) {
		   String bankName = bank.getBankName();
		   String bankCode = bank.getBankCode();
		Page<LoanBank> bankList = loanDeductService.findBankPage(new Page<LoanBank>(request,
				response),bank);
		bank.setBankName(bankName);
		bank.setBankCode(bankCode);
		model.addAttribute("page", bankList);
		model.addAttribute("bank", bank);
		model.addAttribute("queryURL", "list");
		return "modules/bankbranch/bankbranch";
	}
	

}
