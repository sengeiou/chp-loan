package com.creditharmony.loan.borrow.grant.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.PayFeeResult;
import com.creditharmony.core.loan.type.UrgeRepay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.grant.constants.UrgeBackMoneyConstants;
import com.creditharmony.loan.borrow.grant.dao.UrgeBackMoneyDao;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesBackMoneyDao;
import com.creditharmony.loan.borrow.grant.dto.UrgeBackMoneyNotify;
import com.creditharmony.loan.borrow.grant.entity.UrgeHistory;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeBackMoneyEx;

/**
 * 催收服务费操作
 * @Class Name UrgeBackMoneyService
 * @author 朱静越
 * @Create In 2016年1月11日
 */
@Service("urgeBackMoneyService")
public class UrgeBackMoneyService extends CoreManager<UrgeBackMoneyDao, UrgeBackMoneyEx> {
	
	@Autowired
	private UrgeServicesBackMoneyDao urgeServicesBackMoneyDao;

	/**
	 * 催收返款列表
	 * 2016年1月11日
	 * By 朱静越
	 * @param page
	 * @param urgeBackMoneyEx
	 * @return 分页对象
	 */
	public Page<UrgeBackMoneyEx> selectBackMoneyList(Page<UrgeBackMoneyEx> page,UrgeBackMoneyEx urgeBackMoneyEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<UrgeBackMoneyEx> pageList = (PageList<UrgeBackMoneyEx>)dao.selectBackMoneyList(pageBounds, urgeBackMoneyEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据查询条件查询需要进行操作的单子，不带查询
	 * 2016年2月23日
	 * By 朱静越
	 * @param urgeBackMoneyEx 查询条件
	 * @return 结果集
	 */
	public List<UrgeBackMoneyEx> selectBackMoneyListNo(UrgeBackMoneyEx urgeBackMoneyEx){
		return dao.selectBackMoneyList(urgeBackMoneyEx);
	}
	/**
	 * 返款退回更新状态
	 * 2015年12月17日
	 * @param urgeBackMoneyEx
	 * By 张振强
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateUrgeBack(UrgeBackMoneyEx urgeBackMoneyEx) {
		urgeBackMoneyEx.preUpdate();
		dao.updateUrgeBack(urgeBackMoneyEx);
	}
	
	/**
	 * 保存催收返款
	 * 2016年4月25日
	 * By 张永生
	 * @param urgeBackExList
	 * @param page
	 * @param urgeBackMoneyEx
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public UrgeBackMoneyNotify saveUrgeBackMoney(List<UrgeBackMoneyEx> urgeBackExList,
			Page<UrgeBackMoneyEx> page, UrgeBackMoneyEx urgeBackMoneyEx) {
		UrgeBackMoneyNotify notify = new UrgeBackMoneyNotify();
		// 根据金额和合同号进行唯一性判断（查询得到的实体类集合），返款结果也为必填项的判断
		UrgeHistory urgeHistory = new UrgeHistory();
		int flag = 0;
		StringBuffer message = new StringBuffer();
		if (urgeBackExList.size()>0) {
			flag = urgeBackMoneyIsNull(urgeBackExList);
			if (flag <= 0 ) {
				for (int i = 0; i < urgeBackExList.size(); i++) {
					urgeBackMoneyEx = urgeBackExList.get(i);
					if (StringUtils.isNotEmpty(urgeBackMoneyEx.getContractCode())) {
						UrgeBackMoneyEx urgeBackMoneyExSR = new UrgeBackMoneyEx();
						urgeBackMoneyExSR.setContractCode(urgeBackMoneyEx.getContractCode());
						UrgeBackMoneyEx urgeItem = dao.getByContract(urgeBackMoneyExSR);
						if(ObjectHelper.isNotEmpty(urgeItem)){
							// 插入催收服务费关联id
							urgeHistory.setrUrgeId(urgeItem.getId());
							// 根据催收服务费返款id更新数据，成功更新返款状态和返款结果，原因和返款日期
							urgeBackMoneyExSR.setId(urgeItem.getId());
							if (UrgeBackMoneyConstants.SUCCESS.equals(urgeBackMoneyEx.getDictPayResult())) {
								urgeBackMoneyExSR.setDictPayResult(PayFeeResult.SUCCESS.getCode());
								urgeBackMoneyExSR.setDictPayStatus(UrgeRepay.REPAIED.getCode());
								urgeBackMoneyExSR.setRemark(urgeBackMoneyEx.getRemark());
								urgeBackMoneyExSR.setBackTime(new Date());
								updateUrgeBack(urgeBackMoneyExSR);
							} else {
								// 返款结果为失败，更新返款结果，返款原因
								urgeBackMoneyExSR.setDictPayResult(PayFeeResult.FAILED.getCode());
								urgeBackMoneyExSR.setRemark(urgeBackMoneyEx.getRemark());
								updateUrgeBack(urgeBackMoneyExSR);
							}
							// 插入催收服务费历史
							urgeHistory.setContractCode(urgeBackMoneyEx.getContractCode());
							urgeHistory.setDictayPayResult(UrgeBackMoneyConstants.IMPORT_SUCCESS);
							urgeHistory.setOperateStep(UrgeBackMoneyConstants.IMPORT_BACK_RESULT);
							urgeHistory.setRemarks(urgeBackMoneyEx.getDictPayResult());
							urgeHistory.setOperateTime(new Date());
							User user = UserUtils.getUser();
							if (ObjectHelper.isNotEmpty(user)) {
								urgeHistory.setOperator(user.getName());
							}
							urgeHistory.preInsert();
							urgeServicesBackMoneyDao.insertUrgeHistory(urgeHistory);
						}else{
							message.append("合同号："+ urgeBackMoneyEx.getContractCode());
						}
					
					}
				}
			}
		}
		notify.setFlag(flag);
		notify.setMessage(message.toString());
		return notify;
	}
	
	/**
	 * 催收返款非空判断
	 * 2016年7月21日
	 * By 朱静越
	 * @param urgeBackExList  导入的excel
	 * @return 
	 */
	public int urgeBackMoneyIsNull(List<UrgeBackMoneyEx> urgeBackExList){
		int flag = 0;
		for (UrgeBackMoneyEx urgeBackMoneyEx : urgeBackExList) {
			// 合同编号为空的判断，合同编号为空不必对金额等进行判断
			if (StringUtils.isEmpty(urgeBackMoneyEx.getContractCode()) && 
					StringUtils.isNotEmpty(urgeBackMoneyEx.getCustomerName())) {
				flag++;
			}else if(StringUtils.isNotEmpty(urgeBackMoneyEx.getContractCode())){
				// 合同编号存在的情况下金额和回盘结果为空的判断
				if (urgeBackMoneyEx.getPaybackBackAmount() == ""
						|| StringUtils.isEmpty(urgeBackMoneyEx
								.getDictPayResult())) {
					flag++;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 催收返款退回
	 * 2016年4月25日
	 * By 张永生
	 * @param urgeBackMoneyEx
	 * @param backReason
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveUrgeBack(UrgeBackMoneyEx urgeBackMoneyEx, String backReason) {
		UrgeBackMoneyEx urgeBack = new UrgeBackMoneyEx();
		urgeBack.setDictPayStatus(UrgeRepay.REPAY_BACK.getCode());
		urgeBack.setId(urgeBackMoneyEx.getId());
		urgeBack.setRemark(backReason);
		urgeBackMoneyEx.preUpdate();
		dao.updateUrgeBack(urgeBack);
		UrgeHistory urgeHistory = new UrgeHistory();
		urgeHistory.setContractCode(urgeBackMoneyEx.getContractCode());
		urgeHistory.setDictayPayResult(UrgeBackMoneyConstants.MONEY_BACK_SUCCESS);
		urgeHistory.setOperateStep(UrgeBackMoneyConstants.MONEY_BACK);
		urgeHistory.setRemarks(backReason);
		urgeHistory.setOperateTime(new Date());
		User user = UserUtils.getUser();
		if (ObjectHelper.isNotEmpty(user)) {
			urgeHistory.setOperator(user.getName());
		}
		// 插入催收服务费返款关联id
		urgeHistory.setrUrgeId(urgeBackMoneyEx.getId());
		urgeHistory.preInsert();
		urgeServicesBackMoneyDao.insertUrgeHistory(urgeHistory);
	}
	
}
