package com.creditharmony.loan.common.ws;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.approvewechat.ApproveWeChatCustomerPhoneBaseService;
import com.creditharmony.adapter.service.approvewechat.bean.ApproveWeChatCustomerPhoneInParam;
import com.creditharmony.adapter.service.approvewechat.bean.ApproveWeChatCustomerPhoneOutParam;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCustomerService;
import com.creditharmony.loan.common.entity.LoanCustomer;

@Service
public class ApproveWeChatCustomerPhoneService extends ApproveWeChatCustomerPhoneBaseService{
	
	private final static  String DBNAME ="loanxj";
	private final static  String SYS ="srdbloan";
	private final static  String OBJSIG ="qweasdzx1234";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LoanCustomerService loanCustomerService;

	@Override
	public ApproveWeChatCustomerPhoneOutParam doExec(ApproveWeChatCustomerPhoneInParam paramBean) {
		ApproveWeChatCustomerPhoneOutParam outParam = new ApproveWeChatCustomerPhoneOutParam();
		
		try {
			LoanCustomer loanCustomer = loanCustomerService.getByCertNum(paramBean.getIdNum());
			if(loanCustomer != null){
				//设置手机号加密串
				outParam.setPhone(getEncryptStr(loanCustomer.getCustomerPhoneFirst()));
				outParam.setRetCode(ReturnConstant.SUCCESS);
				outParam.setRetMsg("汇诚信用-根据客户证件号获取手机号成功！");
			}else{
				outParam.setRetCode(ReturnConstant.FAIL);
				outParam.setRetMsg("汇诚信用-根据客户证件号获取手机号失败：客户不存在！");
			}
		} catch (Exception e) {
			logger.error("汇诚信用-根据客户证件号获取手机号业务异常！", e);
			outParam.setRetCode(ReturnConstant.ERROR);
			outParam.setRetMsg(e.getMessage());
		}
		return outParam;
	}
	
	/**
	 * 得到手机号加密串
	 * @param customerPhoneFirst
	 * @return
	 */
	private String getEncryptStr(String customerPhoneFirst) {
		HashMap<String, String> map = new HashMap<String, String>(); 
		map.put("flg", ""); //暂空 
		map.put("SYS", SYS);//请求加密的系统 
		map.put("IDS", "ID");//用户ID 
		map.put("USERCODE", "USERCODE");//用户标记、用户编码 
		map.put("CREATEUSER", "CREATEUSER"); //创建人 
		map.put("CREATTIME", "CREATTIME");//创建时间 必须long类型 
		map.put("OBJSIG", OBJSIG);//用户附加字段 
		map.put("MPHONES", customerPhoneFirst + ",");//手机号 密文
		map.put("PSLCODE", "PSLCODE");//证件号 
		map.put("TPHONES", "TPHONES");//固定电话号 
		map.put("DBNAME", DBNAME); //所属库 
		map.put("TABNAME","T_JK_LOAN_CUSTOMER");//所属表 
		map.put("ENCCOLS", "customer_phone_first");//加密字段
		String str = JSON.toJSONString(map); 
		return str;
	}

}
