package com.creditharmony.loan.borrow.consult.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.consult.entity.ValidHistory;
import com.creditharmony.loan.borrow.consult.service.ValidStatusService;
import com.creditharmony.loan.borrow.consult.view.ConsultSearchView;

/**
 * 身份验证controller
 * 
 * @Class Name ValidStatusController
 * @author 宋锋
 * @Create In 2016年10月20日
 */
@Controller
@RequestMapping(value = "${adminPath}/valid/validStatus")
public class ValidStatusController extends BaseController {

    @Autowired
    private ValidStatusService validStatusService;
    
    
    /**
     * 修改身份验证信息
     * @param validHistory
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "toUpdateStatus")
    public String updateStatus(ValidHistory validHistory){
    	AjaxNotify notify=new AjaxNotify();
    	String succ=validStatusService.updateStatus(validHistory);
    	if("success".equals(succ)){
    		notify.setSuccess(BooleanType.TRUE);
			notify.setMessage("修改身份验证信息成功");
    	}else if("noinfo".equals(succ) ||"noContract".equals(succ)){
    		notify.setSuccess(BooleanType.FALSE);
			notify.setMessage("查询不到借款人信息，请检查");
    	}else{
    		notify.setSuccess(BooleanType.FALSE);
			notify.setMessage("修改身份验证信息失败");
    	}
    	return jsonMapper.toJson(notify);
    	
    }
    
    /**
     * 查询身份验证修改历史列表
     * @param validHistory
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "findValidHisList")
    public String findValidHisList(ValidHistory validHistory,Model model, HttpServletRequest request,
            HttpServletResponse response){
    	 Page<ValidHistory> page = validStatusService.findPage(
                 new Page<ValidHistory>(request, response),
                 validHistory);
    	 model.addAttribute("page", page);
    	 model.addAttribute("validHistory", validHistory);
    	return "/apply/consult/validStatusPage";
    	
    }
   
}
