package com.creditharmony.loan.borrow.creditor.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.claim.dto.SyncClaim;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.LoanType;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.core.sync.data.util.SyncDataTypeUtil;
import com.creditharmony.loan.borrow.creditor.dao.CreditorDao;
import com.creditharmony.loan.borrow.creditor.view.CreditorModel;
import com.creditharmony.loan.borrow.creditor.view.CreditorSearch;
import com.creditharmony.loan.sync.data.fortune.ForuneSyncCreditorService;

/**
 * 中金划扣
 * @Class Name CreditorService
 * @author WJJ
 * @Create In 2016年3月11日
 */
@LoanBatisDao
public class CreditorsService {
	protected Logger logger = LoggerFactory.getLogger(CreditorsService.class);
	@Autowired
	private CreditorDao creditorDao;
	
	@Autowired
	private ForuneSyncCreditorService foruneSyncCreditorService;
	/**
	 * 债权录入分页数据
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CreditorModel> getListByParam(Page<CreditorModel> page,CreditorSearch params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CreditorModel> pageList = (PageList<CreditorModel>) creditorDao.getListByParam(pageBounds,params);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	/**
	 * 债权录入保存
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void save(CreditorModel params) {
		creditorDao.save(params);
		this.propelling(params.getId());
	}
	/**
	 * 获取债权录入信息，并同步到财富端
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void propelling(String id) {
		logger.info("房借债权录入调用，同步到财富端数据-->开始");
		SyncClaim syncClaim = new SyncClaim();
		List<SyncClaim> list = creditorDao.getCreditor(id);
		if(!list.isEmpty()){
			syncClaim = list.get(0);
		}		
		
		syncClaim.setSyncTableName("borrow");//表名
		syncClaim.setSyncType(SyncDataTypeUtil.VALUE_ADD);//同步数据类型
		syncClaim.setDictLoanType(LoanType.HOUSE_LOAN.getCode());//借款类型
		syncClaim.setDictLoanFreeFlag("1");//是否可用
		syncClaim.setLoanTrusteeFlag("1");//资金拖管标识
		syncClaim.setLoanMonthgainFlag("0");//满盈标识
		syncClaim.setLoanPledge("抵押房");//
		syncClaim.setLoanAvailabeValue(syncClaim.getLoanQuota());
		syncClaim.setLoanCreditValue(syncClaim.getLoanQuota());
		
		
		

		syncClaim.setLoanValueYear("0");//借款年利率
		syncClaim.setLoanMiddleMan("");//中间人

		logger.info("打印要进行同步的数据------>开始");
		printSync(syncClaim);
		logger.info("打印要进行同步的数据------>结束");
		logger.info("调用财富同步接口-->开始");
		Boolean flag = foruneSyncCreditorService.executeSyncLoan(syncClaim);
		logger.info("调用财富同步接口-->结束");
		logger.info("调用财富同步接口返回结果（true:成功，false:失败）：" + flag);

		logger.info("房借债权录入调用，同步到财富端数据-->结束");
	}
	/**
	 * 获取客户名称
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String,Object>> getName(String cerNum){
		return creditorDao.getName(cerNum);
	}
	/**
	 * 获取分类
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String,Object>> getType(){
		return creditorDao.getType();
	}
	/**
	 * 获取职业信息
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String,Object>> getOccupation(String type, String value){
		return creditorDao.getOccupation(type, value);
	}
	
	private void printSync(SyncClaim syncClaim){
		System.out.println("?"+syncClaim.getCreditValueId());
		System.out.println("借款CODE"+syncClaim.getLoanCode());
		System.out.println("借款人ID"+syncClaim.getLoanId());
		System.out.println("表名"+syncClaim.getSyncTableName());
		System.out.println("同步数据类型"+syncClaim.getSyncType());
		System.out.println("借款人姓名"+syncClaim.getLoanName());
		System.out.println("身份证号："+syncClaim.getLoanIdcard());
		System.out.println("职业："+syncClaim.getLoanJob());
		System.out.println("借款类型："+syncClaim.getDictLoanType());
		System.out.println("借款产品："+syncClaim.getLoanProduct());
		System.out.println("借款用途："+syncClaim.getLoanPurpose());
		System.out.println("放款日："+syncClaim.getLoanOutmoneyDay());
		System.out.println("首次还款日："+syncClaim.getLoanBackmoneyFirday());
		System.out.println("还款日："+syncClaim.getLoanBakcmoneyDay());
		System.out.println("最后一期还款日："+syncClaim.getLoanBackmoneyLastday());
		System.out.println("借款期数："+syncClaim.getLoanMonths());
		System.out.println("借款利率："+syncClaim.getLoanMonthRate());
		System.out.println("借款年利率："+syncClaim.getLoanValueYear());
		System.out.println("是否可用："+syncClaim.getDictLoanFreeFlag());
		System.out.println("中间人："+syncClaim.getLoanMiddleMan());
		System.out.println("原始债权价值:"+syncClaim.getLoanQuota());
		System.out.println("资金托管标识："+syncClaim.getLoanTrusteeFlag());
		System.out.println("月满盈标识："+syncClaim.getLoanMonthgainFlag());
		System.out.println("还款日："+syncClaim.getLoanBakcmoneyDay());
		System.out.println("剩余借款期数："+syncClaim.getLoanMonthsSurplus());
		System.out.println("月利率："+syncClaim.getLoanMonthRate());
		System.out.println("年预计债权收益："+syncClaim.getLoanValueYear());
		System.out.println("最后编辑时间："+syncClaim.getLoanModifiedDay());
		System.out.println("抵押："+syncClaim.getLoanPledge());
		System.out.println("借款剩余天数："+syncClaim.getLoanDaySurplus());
		System.out.println("冻结时间："+syncClaim.getLoanFreezeDay());
		System.out.println("创建人："+syncClaim.getCreateBy());
		System.out.println("创建日期："+syncClaim.getCreateTime());
		System.out.println("修改人："+syncClaim.getModifyBy());
		System.out.println("修改日期："+syncClaim.getModifyTime());
	}
}
