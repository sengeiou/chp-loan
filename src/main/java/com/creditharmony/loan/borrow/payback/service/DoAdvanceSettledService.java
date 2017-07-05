package com.creditharmony.loan.borrow.payback.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.AgainstStatus;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayStatus;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.payback.dao.DoAdvanceSettledDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackCharge;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.utils.CeUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;


/**
 * 提前结清待办业务处理service
 * 
 * @Class Name DoAdvanceSettledService
 * @author zhangfeng
 * @Create In 2015年11月24日
 */
@Service("DoAdvanceSettledService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class DoAdvanceSettledService extends CoreManager<DoAdvanceSettledDao, PaybackCharge> {

	@Autowired
	private DoAdvanceSettledDao doAdvanceSettledDao;
	@Autowired
	private HistoryService historyService;
	/**
	 * 门店提前结清待办获取任务
	 * 2016年3月10日
	 * By zhangfeng
	 * @param page
	 * @param paybackCharge
	 * @return page
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<PaybackCharge> findPaybackCharge(Page<PaybackCharge> page, PaybackCharge paybackCharge) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PaybackCharge> pageList = doAdvanceSettledDao.findPaybackCharge(paybackCharge, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public PaybackCharge findPaybackCharge(PaybackCharge paybackCharge){
		return doAdvanceSettledDao.findPaybackCharge(paybackCharge);
	}
	/**
	 * 
	 * 2016年3月1日
	 * By zhaojinping
	 * @param files
	 * @param paybackCharge
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackCharge(MultipartFile[] files,
			PaybackCharge paybackCharge) {
		if(files[0].getSize()>0){
			DocumentBean db = CeUtils.uploadFile(files[0],
					paybackCharge.getContractCode(),CeFolderType.LOAN_CHANGE);
			//DocumentBean db = this.uploadFile(files[0], paybackCharge.getUploadPath());
			if (!ObjectHelper.isEmpty(db)) {
				paybackCharge.setModifyBy(UserUtils.getUser().getId());
				paybackCharge.setModifyTime(new Date());
				paybackCharge.setUploadPath(db.getDocId());
				paybackCharge.setUploadFilename(db.getDocTitle());
				paybackCharge.setUploadName(UserUtils.getUser().getId());
				paybackCharge.setUploadDate(new Date());
				paybackCharge.preUpdate();
				doAdvanceSettledDao.updatePaybackCharge(paybackCharge);
			}
		}
		
	}
	
	
	/**
	 * 上传提前金额请申请资料
	 * 2016年2月24日
	 * By zhaojinping
	 * @param files
	 * @param oldFilePath
	 * @return bean
	 */
	public DocumentBean uploadFile(MultipartFile files, String oldFilePath) {
		DmService dmService = DmService.getInstance();
		FileInputStream is = null;
		DocumentBean doc = null;
		//dmService.deleteDocument(oldFilePath);
		CeUtils.deleteFile(oldFilePath);
		try {
			File f = LoanFileUtils.multipartFile2File(files);
			is = new FileInputStream(f);
			doc = dmService.createDocument(f.getName(), is, DmService.BUSI_TYPE_LOAN, "batchNo002", "subType002",
					UserUtils.getUser().getId());
		} catch (FileNotFoundException e) {
			logger.debug("invoke DoAdvanceSettledService method: uploadFile, 提前结清门店待办发起提前结清,上传CE影像有误!");
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 更新还款主表中的还款状态
	 * 2016年3月2日
	 * By zhaojinping
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updatePaybackStatus(Payback payback){
		doAdvanceSettledDao.updatePaybackStatus(payback);
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public boolean  giveUpStatus(String contractCode,String chargeId,String paybackId){
		try {
			// 更新还款状态为 ‘还款失败’
			Payback payback = new Payback();
			payback.setContractCode(contractCode);
			payback.setDictPayStatus(RepayStatus.PEND_REPAYMENT.getCode());
			// 更新冲抵申请表的冲抵状态为 ‘冲抵失败’
			PaybackCharge paybackCharge = new PaybackCharge();
			paybackCharge.setId(chargeId);
			paybackCharge.setChargeStatus(AgainstStatus.AGAINST_FAILED.getCode());
			// 记录提前结清门店放弃历史
			PaybackOpeEx paybackOpeEx = new PaybackOpeEx(chargeId, paybackId, RepaymentProcess.GIVE_UP, TargetWay.REPAYMENT,
					PaybackOperate.GIVE_UP_SUCCESS.getCode(), "提前结清还款放弃，合同编号:"
					+ contractCode);
			doAdvanceSettledDao.updatePaybackStatus(payback);
			doAdvanceSettledDao.giveUpStatus(paybackCharge);
			historyService.insertPaybackOpe(paybackOpeEx);
	
		} catch (Exception e) {
		  throw	new ServiceException("更新失败"); 
		}
		return true;
		
	}
}
