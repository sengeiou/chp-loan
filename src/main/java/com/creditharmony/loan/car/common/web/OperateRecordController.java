package com.creditharmony.loan.car.common.web;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.car.common.consts.CarLoanOperateType;
import com.creditharmony.loan.car.common.entity.OperateRecord;
import com.creditharmony.loan.car.common.service.OperateRecordService;


/**
 * 
 * @Class Name OperateRecordController
 * @author zhangqinagn	
 * @Create In 2016年9月28日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/operateRecord")
public class OperateRecordController {

	@Autowired
    private OperateRecordService operateRecordService;

	
	@ResponseBody
	@RequestMapping(value = "saveOperateRecord")
	public String saveOperateRecord(HttpServletRequest request) throws ParseException{
		String loanCode = request.getParameter("loanCode");
		String operateStep = request.getParameter("operateStep");
		String reason = request.getParameter("reason");
		String flag = "true";
		User user = UserUtils.getUser();
		OperateRecord record = new OperateRecord();
		record.setLoanCode(loanCode);
		record.setOperateStep(operateStep);
		record.setOperateType(CarLoanOperateType.DELETE);
		record.setReason(reason);
		record.setUserId(user.getId());
		record.setUserName(user.getName());
		UUID uuid = UUID.randomUUID();
		String id=uuid.toString().replace("-", ""); 
		record.setId(id);
		record.setOperateTime(new Date());
		operateRecordService.insertRecord(record);
		return flag;
				
	}
}
