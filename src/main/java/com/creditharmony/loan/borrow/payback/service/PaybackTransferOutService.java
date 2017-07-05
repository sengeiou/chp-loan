package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.payback.dao.PaybackTransferOutDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.service.HistoryService;

/**
 * 查账账款列表业务处理service
 * @Class Name PaybackTransferOutService
 * @author zhangfeng
 * @Create In 2015年11月24日
 */
@Service("PaybackTransferOutService")
public class PaybackTransferOutService extends CoreManager<PaybackTransferOutDao, PaybackTransferOut> {
	
	@Autowired
	private HistoryService historyService;
	
	/**
	 * 跳转查账账款页面查询列表 
	 * 2016年1月5日 By 
	 * zhangfeng
	 * @param page
	 * @param payBackTransferOut
	 * @return page
	 */
	public Page<PaybackTransferOut> queryList(
			Page<PaybackTransferOut> page, PaybackTransferOut payBackTransferOut) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<PaybackTransferOut> pageList = (PageList<PaybackTransferOut>) dao
				.findList(payBackTransferOut, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 更新汇款流水表状态(applyId) 
	 * 2015年12月25日 
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOutStatuByApplyId(PaybackTransferOut paybackTransferOut) {
		dao.updateOutStatuByApplyId(paybackTransferOut);
	}
	
	/**
	 * 更新汇款流水表状态(id)
	 * 2015年12月25日 
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateOutStatuById(PaybackTransferOut paybackTransferOut) {
		dao.updateOutStatuById(paybackTransferOut);
	}
	
	/**
	 * 查询账款信息 
	 * 2016年1月5日 
	 * By zhangfeng
	 * @param payBackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> findList(PaybackTransferOut payBackTransferOut) {
		return dao.findList(payBackTransferOut);
	}

	/**
	 * 导入银行账款数据,导入历史
	 * 2016年1月27日
	 * By zhangfeng
	 * @param lst
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void batchInsertAudited(List<PaybackTransferOut> outList) {
		
		// 批量插入
		dao.insert(outList);

		// 循环记录导入银行流水历史
		for(PaybackTransferOut po :outList){
			PaybackOpeEx paybackOpes = new PaybackOpeEx(po.getId(), null,
					RepaymentProcess.DATA_IMPORT, TargetWay.REPAYMENT,
					PaybackOperate.IMPORT_SUCCESS.getCode(), "导入Excel查账数据！"); 
	    	historyService.insertPaybackOpe(paybackOpes);
		}
	}

	/**
	 * 查询账款信息 (未匹配)
	 * 2016年3月23日
	 * By zhangfeng
	 * @param paybackTransferOut
	 * @return list
	 */
	public List<PaybackTransferOut> getNoAuditedList(
			PaybackTransferOut paybackTransferOut) {
		return dao.getNoAuditedList(paybackTransferOut);
	}

	/**
	 * 导入数据转换
	 * 2016年5月17日
	 * By zhangfeng
	 * @param out
	 * @return PaybackTransferOut
	 */
	public PaybackTransferOut importDate(PaybackTransferOut out) {
		SimpleDateFormat df = new SimpleDateFormat( "yyyyMMdd");
		if(out.getOutAuditStatus()==null || out.getOutAuditStatus().equals(""))
			out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		out.setOutReallyAmount(new BigDecimal(out.getOutReallyAmountStr().trim()));
		out.setOrderNumber(new BigDecimal(out.getOrderNumber()).toPlainString().trim());
		out.setOutEnterBankAccount(out.getOutEnterBankAccount().trim());
		out.setOutDepositName(out.getOutDepositName().trim());
		try {
			String outDepositTime = out.getOutDepositTimeStr().toString().trim();
			if (StringUtils.isNotEmpty(outDepositTime)) {
				out.setOutDepositTime(df.parse(outDepositTime));
			}
			String outTimeCheckAccountStr = out.getOutTimeCheckAccountStr().toString().trim();
			if (StringUtils.isNotEmpty(outTimeCheckAccountStr)) {
				out.setOutTimeCheckAccount(df.parse(outTimeCheckAccountStr));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		out.setIsNewRecord(false);
		out.preInsert();
		return out;
	}
	

	/**
	 * 存款日期、入账账号、金额、存款人、查账状态 导入检验
	 * @param pbtf
	 * @return String
	 */
	public String checkNullForAuditedExl(PaybackTransferOut out){
		String valiDateStr = "";
		if (StringUtils.isEmpty(out.getOrderNumber()) && StringUtils.isEmpty(out.getOutDepositTimeStr())
				&& StringUtils.isEmpty(out.getOutReallyAmountStr())
				&& StringUtils.isEmpty(out.getOutDepositName())) {
			return "continue";
		}
		if(StringUtils.isEmpty(out.getOrderNumber())){
			valiDateStr += "序号、";
		}
		if(StringUtils.isEmpty(out.getOutDepositTimeStr())){
			valiDateStr += "存款日期、";
		}
		if(StringUtils.isEmpty(out.getOutEnterBankAccount())){
			valiDateStr += "入账账号、";
		}
		if(StringUtils.isEmpty(out.getOutReallyAmountStr())){
			valiDateStr += "金额、";
		}
		if(StringUtils.isEmpty(out.getOutDepositName())){
			valiDateStr += "存款人、";
		}
		if(StringUtils.isNotEmpty(valiDateStr)){
			valiDateStr = valiDateStr.substring(0, valiDateStr.length()-1) +"不能为空!";
		}else{
			String orderNumber = out.getOrderNumber().trim();
			String outReallyAmount = out.getOutReallyAmountStr().trim();
			String outReallyAmountBd = new BigDecimal(outReallyAmount).toPlainString();
			String orderNumberBd = null; 
			try{
				orderNumberBd = new BigDecimal(orderNumber).toPlainString();
			}catch(Exception e){
				valiDateStr = "序号格式为全数字，请修改模板数据！";
				return valiDateStr;
			}
			if(!orderNumberBd.matches("^[0-9]*$")){
				valiDateStr = "序号格式为全数字，请修改模板数据！";
				return valiDateStr;
			}
			if(orderNumberBd.length() != 12){
				valiDateStr = "序号标准位数为12，请修改模板数据！";
				return valiDateStr;
			}
			if(!out.getOutDepositTimeStr().trim().matches("^[0-9]*$")){
				valiDateStr = "日期格式为全数字，请修改模板数据！";
				return valiDateStr;
			}
			if(out.getOutDepositTimeStr().trim().length() != 8){
				valiDateStr = "日期标准位数为8，请修改模板数据！";
				return valiDateStr;
			}
			if(!outReallyAmountBd.matches("[0-9]+\\.?[0-9]*")){
				valiDateStr = "金额为全数字，请修改模板数据！";
				return valiDateStr;
			}
			if(!out.getOutEnterBankAccount().trim().matches("^[0-9]*$")){
				valiDateStr = "存入银行为全数字，请修改模板数据！";
				return valiDateStr;
			}
		}
		return valiDateStr;
	}

	/**
	 * 查询导入流水表是否存在查账成功的流水
	 * 2016.5.25 by zhangfeng
	 * @param out
	 * @return list
	 */
	public List<PaybackTransferOut> findAuditedList(PaybackTransferOut out) {
		return dao.findAuditedList(out);
	}
}
