/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.serviceApplyLoanInfoService.java
 * @Create By 张灏
 * @Create In 2015年12月18日 上午9:56:44
 */
package com.creditharmony.loan.borrow.applyinfo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ContractResult;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.event.ChangJieRealNameAuthentication;
import com.creditharmony.loan.borrow.contract.event.LoanFlowUpdCtrEx;
import com.creditharmony.loan.borrow.contract.event.LoanFlowUpdateCtrOperEx;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.event.CreateOrderFileIdEx;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 操作申请信息Service
 * @Class Name ApplyLoanInfoService
 * @author 张灏
 * @Create In 2015年12月18日
 */
@Service("applyLoanInfoService")
public class ApplyLoanInfoService extends
        CoreManager<ApplyLoanInfoDao, LoanInfo> {

   @Autowired
   private ApplyLoanInfoDao applyLoanInfoDao;
   @Autowired
   private LoanStatusHisDao loanStatusHisDao;
   
   @Autowired
   private LoanFlowUpdateCtrOperEx loanFlowUpdateCtrOperEx;
   @Autowired
   private LoanFlowUpdCtrEx loanFlowUpdCtrEx;
   @Autowired
   private CreateOrderFileIdEx createOrderFileIdEx;
   @Autowired
   private ChangJieRealNameAuthentication changJieRealNameAuthentication;
   
   
   
   /**
     *提交时更新申请信息的流程Id 
     *@param param key applyId生成的流程Id loanCode申请Id
     *@return none 
     */
    @Transactional(readOnly=false,value = "loanTransactionManager")
    public void updateApplyId(Map<String,Object> param){
       
       applyLoanInfoDao.updateApplyId(param);
    }
    
    /**
	 * 更新贷款信息 
	 * 2015年12月19日
	 * By 张灏
	 * @param loanInfo
	 * @return none 
	 */
    @Transactional(readOnly=false,value = "loanTransactionManager")
	public void updateLoanInfo(LoanInfo loanInfo){
    	loanInfo.preUpdate();
		applyLoanInfoDao.updateLoanInfo(loanInfo);
	}
   
   /**
    * 根据applyId查询LoanInfo
    * @author zhanghao
    * @Create In 2016年2月1日
    * @param applyId
    * @return LoanInfo
    */
   @Transactional(readOnly=true,value = "loanTransactionManager")
   public LoanInfo selectByApplyId(String applyId){
	   Map<String,Object> param = new HashMap<String, Object>();
	   param.put("applyId", applyId);
	   return  applyLoanInfoDao.selectByApplyId(param);
   }
 
   /**
    * 根据loanCode查询LoanInfo
    * @author zhanghao
    * @Create In 2016年2月1日
    * @param loanCode
    * @return LoanInfo
    */
   @Transactional(readOnly=true,value = "loanTransactionManager")
   public LoanInfo selectByLoanCode(String loanCode){
	   Map<String,Object> param = new HashMap<String, Object>();
	   param.put("loanCode", loanCode);
	   return  applyLoanInfoDao.selectByLoanCode(param);
   }  
   /**
    *通过用户编号查询借款信息
    *@author zhanghao 
    *@Create In 2016年3月2日 
    *@param customerCode
    *@return List<LoanInfo> 
    */
   @Transactional(readOnly=true,value = "loanTransactionManager")
   public List<LoanInfo> selectByCustomerCode(String customerCode){
       
       return applyLoanInfoDao.selectByCustomerCode(customerCode);
   }
   
   /**
    *更新冻结信息
    *@author zhanghao 
    *@Create In 2016年3月2日 
    *@param loanInfo
    *@return none
    */
   @Transactional(readOnly=false,value = "loanTransactionManager")
   public void updateFrozen(LoanInfo loanInfo){
       loanInfo.preUpdate();
       applyLoanInfoDao.updateLoanInfo(loanInfo);
   }
   /**
    *通过合同表关联借款申请表 
    *@author zhanghao
    *@Create In 2016年03月14日 
    *@param param idNum 证件编号  idType 证件类型 
    *@return LoanInfo 
    *  selectByIDNum
    */
   @Transactional(readOnly=true,value = "loanTransactionManager")
   public LoanInfo selectByIDNum(Map<String,Object> param){
       
       return applyLoanInfoDao.selectByIDNum(param);
   }
   
	/**
	 * 使用身份证查询数据 
	 * By 任志远 2017年4月13日
	 *
	 * @param certNum 身份证号码
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<LoanInfo> findByIdentityCode(String certNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mateCertNum", certNum);
		param.put("certType", CertificateType.SFZ.getCode());
		return applyLoanInfoDao.findByIdentityCode(param);
	}
   
	/**
	 * 查询尚未结清的借款数量
	 * role 1 尚未结清的主借人
	 * role 2 尚未结清的主借人配偶
	 * role 3 尚未结清的共借人（旧版申请表）
	 * role 4 尚未结清的最优自然人保证人（新版申请表）
	 * By 任志远 2017年1月4日
	 *
	 * @param certNum	身份证号
	 * @return	List<Map<String, Integer>>
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String, Integer>> selectUnSettleData(String certNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("certNum", certNum);
		return applyLoanInfoDao.selectUnSettleData(param);
	}
   
   /**
    * 新信借待办page
    * @param loanFlowQueryParam
    * @param page
    * @return
    * @author FuLiXin
    * @date 2017年2月20日 下午5:03:34
    */
   @Transactional(readOnly = true,value = "loanTransactionManager")
   public Page<LoanFlowWorkItemView> findBorrow(
		LoanFlowQueryParam loanFlowQueryParam, Page<LoanFlowWorkItemView> page) {
	   PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
	   pageBounds.setFilterOrderBy(BooleanType.FALSE);
	   PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)applyLoanInfoDao.findBorrow(pageBounds, loanFlowQueryParam);

       PageUtil.convertPage(pageList, page);
	return page;
   }
   /**
    * 客户放弃 、门店拒绝
    * @param loanCode
    * @param type
    * @author FuLiXin
    * @date 2017年2月22日 下午2:40:46
    */
   	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void dateEnd(String loanCode, String type,String applyId,String stepName) {
		String status;
		String statusName;
		String operResultName;
   		if("1".equals(type)){//客户放弃
			  status = LoanApplyStatus.CUSTOMER_GIVEUP.getCode();
              statusName = LoanApplyStatus.CUSTOMER_GIVEUP.getName();
              operResultName=ContractResult.CONTRACT_GIVEUP.getName();
			
		}else{//门店拒绝
			 status = LoanApplyStatus.STORE_REJECT.getCode();
             statusName = LoanApplyStatus.STORE_REJECT.getName();
             operResultName=ContractResult.CONTRACT_REJECT.getName();
		}
		User user = UserUtils.getUser();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("modifyBy", user.getId());
		param.put("modifyTime", new Date());
		Map<String, String> loanParam = new HashMap<String, String>();
        loanParam.put("applyId", applyId);
		LoanInfo loanInfo = applyLoanInfoDao.findLoanLinkedContract(loanParam);
		if (!ObjectHelper.isEmpty(loanInfo)) {
			
               param.put("applyId", applyId);
               param.put("dictLoanStatus", status);
               applyLoanInfoDao.updateLoanStatus(param);
		}
		 LoanStatusHis record = new LoanStatusHis();
         // APPLY_ID
         record.setApplyId(applyId);
         record.setLoanCode(loanCode);
         // 状态
         record.setDictLoanStatus(status);
         // 操作步骤(回退,放弃,拒绝 等)
         record.setOperateStep(stepName);
         // 系统标识
         record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
         // 设置Crud属性值
         record.preInsert();
         // 操作时间
         record.setOperateTime(record.getCreateTime());
         // 操作人记录当前登陆系统用户名称
         record.setOperator(user.getName());
         if (!ObjectHelper.isEmpty(user.getRole())) {
             // 操作人角色
             record.setOperateRoleId(user.getRole().getId());
         }
         if (!ObjectHelper.isEmpty(user.getDepartment())) {
             // 机构编码
             record.setOrgCode(user.getDepartment().getId());
         }
         record.setOperateResult(operResultName);
         loanStatusHisDao.insertSelective(record);
   	}
   	/**
   	 * 合同签订办理提交
   	 * @param workItem
   	 * @author FuLiXin
   	 * @date 2017年2月22日 下午5:12:50
   	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void contractUpload(WorkItemView workItem) {
		loanFlowUpdateCtrOperEx.invoke(workItem);
		loanFlowUpdCtrEx.invoke(workItem);
		createOrderFileIdEx.invoke(workItem);
		changJieRealNameAuthentication.invoke(workItem);
	}
}
