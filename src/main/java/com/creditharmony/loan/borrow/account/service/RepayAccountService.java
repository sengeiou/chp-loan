package com.creditharmony.loan.borrow.account.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ListUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.MaintainStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.account.dao.LoanBankEditDao;
import com.creditharmony.loan.borrow.account.dao.RepayAccountDao;
import com.creditharmony.loan.borrow.account.entity.KingAccountChangeExport;
import com.creditharmony.loan.borrow.account.entity.LoanBankEditEntity;
import com.creditharmony.loan.borrow.account.view.RepayAccountApplyView;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.utils.EncryptTableCol;
import com.creditharmony.loan.utils.InnerBean;
import com.creditharmony.loan.utils.PhoneSecretSerivice;

/**
 * 还款账号
 * 
 * @Class Name RepayAccountService
 * @author 王俊杰
 * @Create In 2016年2月22日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class RepayAccountService {

	@Autowired
	RepayAccountDao repayAccountDao;
	
	@Autowired
	LoanBankEditDao loanBankEditDao;
	
	@Autowired
	private PhoneSecretSerivice phoneSecretSerivice;
	
	/**
	 * 检索版本号
	 * 2016年5月19日
	 * By 王彬彬
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<String> selectVersionList(){
		return repayAccountDao.selectVersionList();
	}
	
	/**
	 * 检索账户类型
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<RepayAccountApplyView> selectAccountList(RepayAccountApplyView repayAccountApplyView){
		return repayAccountDao.selectAccountList(repayAccountApplyView);
	}
	
	/**
	 * 检索账户列表
	 * 2016年5月19日
	 * By 王彬彬
	 * @param page
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<RepayAccountApplyView> selectAccountList(Page<RepayAccountApplyView> page, RepayAccountApplyView repayAccountApplyView){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<RepayAccountApplyView> pageList = (PageList<RepayAccountApplyView>) repayAccountDao
				.selectAccountList(pageBounds, repayAccountApplyView);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据合同号检索变更信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param contractCode
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public RepayAccountApplyView selectLoanStatusAndFlag(String contractCode){
		return repayAccountDao.selectLoanStatusAndFlag(contractCode);
	}
	
	/**
	 * 检索维护状态
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	public List<String> selectMaintainStatus(RepayAccountApplyView repayAccountApplyView){
		return repayAccountDao.selectMaintainStatus(repayAccountApplyView);
	}
	
	/**
	 * 检索账户变更信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public RepayAccountApplyView selectAddAccountMassage(RepayAccountApplyView repayAccountApplyView){
		RepayAccountApplyView rav = repayAccountDao.selectAddAccountMassage(repayAccountApplyView);
		String mobile = mobileDisDecrypt(rav.getLoanCode());
		rav.setCustomerPhone(mobile);
		return rav;
	}
	
	/**
	 * 检索账户编辑信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public RepayAccountApplyView selectEditAccountMassage(RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView = selectAccountInfoById(repayAccountApplyView);
		DictCache dict = DictCache.getInstance();
		repayAccountApplyView.setOpenBankName(dict.getDictLabel("jk_open_bank", repayAccountApplyView.getBankName()));
		repayAccountApplyView.setDeductPlatName(dict.getDictLabel("jk_deduct_plat", repayAccountApplyView.getDeductPlat()));
		String oldData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setOldData(oldData);
		return repayAccountApplyView;
	}
	
	/**
	 * 插入账户信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void insertAccount(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.insertAccount(repayAccountApplyView);
		repayAccountDao.insertAccountChangeLog(repayAccountApplyView);
		repayAccountApplyView.setProvinceName(repayAccountDao.getAreaName(repayAccountApplyView.getProvinceId()));
		repayAccountApplyView.setCityName(repayAccountDao.getAreaName(repayAccountApplyView.getCityId()));
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 更新账户信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateAccount(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.insertAccount(repayAccountApplyView);
		repayAccountDao.insertAccountChangeLog(repayAccountApplyView);
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 更新邮箱
	 * 2016年5月19日
	 * By huowenlong
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateEmail(RepayAccountApplyView repayAccountApplyView,LoanBankEditEntity loanBankEditEntity){
		loanBankEditDao.insert(loanBankEditEntity);
		repayAccountDao.insertAccountChangeLog(repayAccountApplyView);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	
	/**
	 * 更新手机号码
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateMobilePhone(RepayAccountApplyView repayAccountApplyView,LoanBankEditEntity loanBankEditEntity){
		loanBankEditDao.insert(loanBankEditEntity);
		repayAccountDao.updateCustomerMobilePhone(repayAccountApplyView);
		repayAccountDao.insertAccountChangeLog(repayAccountApplyView);
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 获取账户变更信息详细
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	private String getDataToString(RepayAccountApplyView repayAccountApplyView){
		DictCache dict = DictCache.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append("<p>客户名:").append(repayAccountApplyView.getCustomerName()==null?"":repayAccountApplyView.getCustomerName()).append("</p>");
		sb.append("<p>手机号:").append(repayAccountApplyView.getCustomerPhone()==null?"":repayAccountApplyView.getCustomerPhone()).append("</p>");
		sb.append("<p>账号姓名:").append(repayAccountApplyView.getAccountName()==null?"":repayAccountApplyView.getAccountName()).append("</p>");
		sb.append("<p>省份:").append(repayAccountApplyView.getProvinceName()==null?"":repayAccountApplyView.getProvinceName()).append("</p>");
		sb.append("<p>城市:").append(repayAccountApplyView.getCityName()==null?"":repayAccountApplyView.getCityName()).append("</p>");
		sb.append("<p>划扣账号:").append(repayAccountApplyView.getAccount()==null?"":repayAccountApplyView.getAccount()).append("</p>");
		sb.append("<p>开户行名称:").append(dict.getDictLabel("jk_open_bank", repayAccountApplyView.getBankName())).append("</p>");
		sb.append("<p>开户行支行:").append(repayAccountApplyView.getBankBranch()==null?"":repayAccountApplyView.getBankBranch()).append("</p>");
		sb.append("<p>开户支行code:").append(repayAccountApplyView.getHdloanBankbrId()==null?"":repayAccountApplyView.getHdloanBankbrId()).append("</p>");
		sb.append("<p>划扣平台:").append(dict.getDictLabel("jk_deduct_plat", repayAccountApplyView.getDeductPlat())).append("</p>");
		sb.append("<p>维护状态:").append(dict.getDictLabel("jk_maintain_status", repayAccountApplyView.getMaintainStatus())).append("</p>");
		return sb.toString();
	}
	
	/**
	 * 检索账户维护信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public RepayAccountApplyView selectExamineMessage(RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView = selectAccountInfoById(repayAccountApplyView);
		DictCache dict = DictCache.getInstance();
		repayAccountApplyView.setOpenBankName(dict.getDictLabel("jk_open_bank", repayAccountApplyView.getBankName()));
		repayAccountApplyView.setDeductPlatName(dict.getDictLabel("jk_deduct_plat", repayAccountApplyView.getDeductPlat()));
		repayAccountApplyView.setMaintainType(dict.getDictLabel("jk_maintain_type", repayAccountApplyView.getMaintainType()));
		repayAccountApplyView.setOldData(getAccountOldData(repayAccountApplyView));
		return repayAccountApplyView;
	}
	
	/**
	 * 获取账户变更历史
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<RepayAccountApplyView> getHistoryList(RepayAccountApplyView repayAccountApplyView){
		return repayAccountDao.getHistoryList(repayAccountApplyView);
	}
	
	/**
	 * 拒绝手机号修改
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void refusePhone(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.refusePhone(repayAccountApplyView);
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 同意修改手机号
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void throughPhone(RepayAccountApplyView repayAccountApplyView){
		String mobile = repayAccountApplyView.getCustomerPhone();
		String moblieEncrypt = mobileDisEncrypt(repayAccountApplyView.getLoanCode(),repayAccountApplyView.getCustomerPhone());
		repayAccountApplyView.setCustomerPhone(moblieEncrypt);
		repayAccountDao.throughPhone(repayAccountApplyView);
		if(repayAccountApplyView.getMaintainStatus().equals(MaintainStatus.TO_MAINTAIN.getCode()))
		{
			//repayAccountApplyView.setId(repayAccountApplyView.getOldAccountId());
			repayAccountDao.updateAccountTop(repayAccountApplyView);
		}
		repayAccountApplyView.setCustomerPhone(mobile);
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 拒绝修改账户信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void refuseAccount(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.refuseAccount(repayAccountApplyView);
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 通过账户变更
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void throughEditAccount(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.throughEditAccount(repayAccountApplyView);
		if(repayAccountApplyView.getMaintainStatus().equals(MaintainStatus.TO_MAINTAIN.getCode()))
		{
			//repayAccountApplyView.setId(repayAccountApplyView.getOldAccountId());
			repayAccountDao.updateAccountTop(repayAccountApplyView);
		}
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 金账户终审
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void editBankAccount(RepayAccountApplyView repayAccountApplyView){
		//修改待审核状态记录相关信息
		repayAccountDao.editBankAccount(repayAccountApplyView);
		if(repayAccountApplyView.getMaintainStatus().equals(MaintainStatus.TO_MAINTAIN.getCode()))
		{
			//将审核状态为已维护的记录设为置顶
			//repayAccountApplyView.setId(repayAccountApplyView.getOldAccountId());
			repayAccountDao.updateAccountTop(repayAccountApplyView);
		}
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 金账户初审
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void saveGoldFirstCheck(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.saveGoldFirstCheck(repayAccountApplyView);
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 同意增加账户信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void throughAddAccount(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.throughAddAccount(repayAccountApplyView);
		if(repayAccountApplyView.getMaintainStatus().equals(MaintainStatus.TO_MAINTAIN.getCode()))
		{
			repayAccountDao.updateAccountTop(repayAccountApplyView);
		}
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 账户置顶
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateAccountTop(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.updateAccountTop(repayAccountApplyView);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 搜索金账户信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public RepayAccountApplyView selectGoldAccountMassage(RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView = selectAccountInfoById(repayAccountApplyView);
		DictCache dict = DictCache.getInstance();
		repayAccountApplyView.setOpenBankName(dict.getDictLabel("jk_open_bank", repayAccountApplyView.getBankName()));
		repayAccountApplyView.setDeductPlatName(dict.getDictLabel("jk_deduct_plat", repayAccountApplyView.getDeductPlat()));
		String oldData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setOldData(oldData);
		return repayAccountApplyView;
	}
	
	/**
	 * 编辑更新金账户信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void editGoldAccount(RepayAccountApplyView repayAccountApplyView){
		repayAccountDao.editGoldAccount(repayAccountApplyView);
		repayAccountDao.insertAccountChangeLog(repayAccountApplyView);
		repayAccountApplyView.setProvinceName(repayAccountDao.getAreaName(repayAccountApplyView.getProvinceId()));
		repayAccountApplyView.setCityName(repayAccountDao.getAreaName(repayAccountApplyView.getCityId()));
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 编辑更新金账户手机信息
	 * 2016年11月24日
	 * By huownelong
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateGoldPhone(RepayAccountApplyView repayAccountApplyView,LoanBankEditEntity loanBankEditEntity){
		loanBankEditDao.insert(loanBankEditEntity);
		repayAccountDao.updateCustomerMobilePhone(repayAccountApplyView);
		repayAccountDao.insertAccountChangeLog(repayAccountApplyView);
		repayAccountApplyView.setProvinceName(repayAccountDao.getAreaName(repayAccountApplyView.getProvinceId()));
		repayAccountApplyView.setCityName(repayAccountDao.getAreaName(repayAccountApplyView.getCityId()));
		String newData = getDataToString(repayAccountApplyView);
		repayAccountApplyView.setNewData(newData);
		repayAccountDao.insertChangeHistory(repayAccountApplyView);
	}
	
	/**
	 * 检索金账户变更
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public RepayAccountApplyView selectGoldExamine(RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView = selectAccountInfoById(repayAccountApplyView);
		DictCache dict = DictCache.getInstance();
		repayAccountApplyView.setOpenBankName(dict.getDictLabel("jk_open_bank", repayAccountApplyView.getBankName()));
		repayAccountApplyView.setDeductPlatName(dict.getDictLabel("jk_deduct_plat", repayAccountApplyView.getDeductPlat()));
		repayAccountApplyView.setMaintainType(dict.getDictLabel("jk_maintain_type", repayAccountApplyView.getMaintainType()));
		repayAccountApplyView.setOldData(getAccountOldData(repayAccountApplyView));
		return repayAccountApplyView;
	}
	
	/**
	 * 检索金账户信息
	 * 2016年5月19日
	 * By 王彬彬
	 * @param repayAccountApplyView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public RepayAccountApplyView selectGoldShowMessage(RepayAccountApplyView repayAccountApplyView){
		repayAccountApplyView = selectAccountInfoById(repayAccountApplyView);
		return repayAccountApplyView;
	}
	
	/**
	 * 获取账单日
	 * 2016年5月19日
	 * By 王彬彬
	 * @return
	 */
	public List<Map<String,Object>> getBillDay(){
		return repayAccountDao.getBillDay();
	}

	/**
	 * 确认成功，根据条件查询出数据，并对每一条数据进行处理
	 * 2016年5月19日
	 * By 王彬彬
	 * @param page
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void editConfirmSuccess(Page<RepayAccountApplyView> page, RepayAccountApplyView repayAccountApplyView){
		//查询出所有数据，不需要分页，所以设置大点的分页数用于查询出所有数据
		String idArrayStr = repayAccountApplyView.getBankIds();
		if(StringUtils.isNotEmpty(idArrayStr)){
			repayAccountApplyView.setBankIdarray(idArrayStr.split(","));
		}
		PageBounds pageBounds = new PageBounds(page.getPageNo(),999999999);
		PageList<RepayAccountApplyView> pageList = (PageList<RepayAccountApplyView>) repayAccountDao
				.selectAccountList(pageBounds, repayAccountApplyView);
		if(ListUtils.isNotEmptyList(pageList))
		{
			for(RepayAccountApplyView apply : pageList){
				RepayAccountApplyView applyView = selectAccountInfoById(apply);
				repayAccountApplyView.setContractCode(applyView.getContractCode());
				if(null != applyView){
					applyView.setMaintainStatus(MaintainStatus.TO_MAINTAIN.getCode());
					applyView.setMaintainTime(new Date());
					repayAccountApplyView.setOldData(getAccountOldData(applyView));
					repayAccountApplyView.setOperateStep("审核");
					repayAccountApplyView.preUpdate();
					applyView.preUpdate();
					//判断修改手机号还是账户(1:账户 0手机号)
					if("1".equals(applyView.getUptedaType())){
						
						//更新账号审核通过
						if (StringUtils.isNotEmpty(applyView.getOldAccountId())){
							repayAccountDao.editBankAccount(applyView);
							String newData = getDataToString(applyView);
							repayAccountApplyView.setNewData(newData);
							repayAccountDao.insertChangeHistory(repayAccountApplyView);
						} else {
							/*repayAccountDao.throughAddAccount(applyView);
							repayAccountDao.insertChangeHistory(repayAccountApplyView);*/
						}
					} else if("0".equals(applyView.getUptedaType())){
						//更新手机号审核通过
						String mobile = repayAccountApplyView.getCustomerPhone();
						String moblieEncrypt = mobileDisEncrypt(applyView.getLoanCode(),applyView.getNewCustomerPhone());
						applyView.setCustomerPhone(moblieEncrypt);
						repayAccountDao.throughPhone(applyView);
						applyView.setCustomerPhone(mobile);
						String newData = getDataToString(applyView);
						repayAccountApplyView.setNewData(newData);
						repayAccountDao.insertChangeHistory(repayAccountApplyView);
					}
				}
			}
		}
	}

	/**
	 * 失败/退回，根据条件查询出数据，并对每一条数据进行处理
	 * 2016年5月19日
	 * By 王彬彬
	 * @param page
	 * @param repayAccountApplyView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void editFailReturn(Page<RepayAccountApplyView> page, RepayAccountApplyView repayAccountApplyView){
		//查询出所有数据，不需要分页，所以设置大点的分页数用于查询出所有数据
		String idArrayStr = repayAccountApplyView.getBankIds();
		if(StringUtils.isNotEmpty(idArrayStr)){
			repayAccountApplyView.setBankIdarray(idArrayStr.split(","));
		}
		PageBounds pageBounds = new PageBounds(page.getPageNo(),999999999);
		PageList<RepayAccountApplyView> pageList = (PageList<RepayAccountApplyView>) repayAccountDao
				.selectAccountList(pageBounds, repayAccountApplyView);
		if(ListUtils.isNotEmptyList(pageList))
		{
			for(RepayAccountApplyView apply : pageList){
				RepayAccountApplyView applyView = selectAccountInfoById(apply);
				if(null != applyView){
					applyView.setRemarks(repayAccountApplyView.getMsg());
					applyView.setMaintainStatus(MaintainStatus.TO_REFUSE.getCode());
					applyView.setMaintainTime(new Date());
					applyView.setOperateStep("退回");
					applyView.setCreateTime(new Date());
					applyView.setModifyTime(new Date());
					applyView.preUpdate();
					applyView.setOldData(getAccountOldData(applyView));
					if("1".equals(applyView.getUptedaType())){
						String newData = getDataToString(applyView);
						applyView.setNewData(newData);
						//更新卡号审核拒绝
						repayAccountDao.refuseAccount(applyView);
				
						repayAccountDao.insertChangeHistory(applyView);
					}
					if("0".equals(applyView.getUptedaType())){
						//更新手机号审核拒绝
						applyView.setCustomerPhone(applyView.getNewCustomerPhone());
						String newData = getDataToString(applyView);
						applyView.setNewData(newData);
						repayAccountDao.refusePhone(applyView);
						applyView.setCustomerPhone(applyView.getNewCustomerPhone());
						applyView.setAccount(applyView.getOldAccount());
						repayAccountDao.insertChangeHistory(applyView);
					}
				}
			}
		}
	}
	/**
     * 金账户审批信息下载
     * 2016年06月11日
     * by zhanghao
     * @param map
     * @return List<KingAccountChangeExport>
     * 
     */
   @Transactional(readOnly = true,value="loanTransactionManager")
   public List<KingAccountChangeExport> getTGdownload(Map<String,Object> map){
      
        return repayAccountDao.getTGdownload(map);
    }

   /**
    * 查询金账户待审核的数量
    * @param repayAccountApplyView
    * @return
    */
	public String selectAccountCount(RepayAccountApplyView repayAccountApplyView) {
		
		return repayAccountDao.selectAccountCount(repayAccountApplyView);
	}

	/**
	 * 查询当前登录人是否有金账户专员，金账户管理员 权限
	 * @param userId
	 * @return
	 */
     public int selectRoleCount(String userId) {
	// TODO Auto-generated method stub
	 return repayAccountDao.selectRoleCount(userId);
     }
     
     /**
      * 查询账户信息
     ·* 2016年11月21日
     ·* by Huowenlong
      * @param id
      * @return
      */
     public LoanBankEditEntity selectLoanBankByPrimaryKey(String id){
    	 return loanBankEditDao.selectByPrimaryKey(id);
     }
     
     
     /**
 	 * 拒绝Email修改
 	 * 2016年11月21日
 	 * By 王彬彬
 	 * @param repayAccountApplyView
 	 */
 	@Transactional(readOnly = false,value="loanTransactionManager")
 	public void refuseEmail(RepayAccountApplyView repayAccountApplyView){
 		repayAccountDao.refuseEmail(repayAccountApplyView);
 		repayAccountDao.insertChangeHistory(repayAccountApplyView);
 	}
 	
 	/**
 	 * 同意修Email
 	 * 2016年11月21日
 	 * By 王彬彬
 	 * @param repayAccountApplyView
 	 */
 	@Transactional(readOnly = false,value="loanTransactionManager")
 	public void throughEmail(RepayAccountApplyView repayAccountApplyView){
 		repayAccountDao.throughEmail(repayAccountApplyView);
 		repayAccountDao.insertChangeHistory(repayAccountApplyView);
 	}
 	
 	/**
 	 * 查询账户信息
 	·* 2016年11月21日
 	·* by Huowenlong
 	 * @param repayAccountApplyView
 	 * @return
 	 */
 	@Transactional(readOnly = true,value="loanTransactionManager")
 	public RepayAccountApplyView  selectAccountInfoById(RepayAccountApplyView repayAccountApplyView){
 		RepayAccountApplyView rav = repayAccountDao.selectAccountInfoById(repayAccountApplyView);
		String mobile = mobileDisDecrypt(rav.getLoanCode());
		rav.setCustomerPhone(mobile);
 		return rav;
 	}
 	
 	/**
 	 * 获取变更信息的oldData
 	·* 2016年12月2日
 	·* by Huowenlong
 	 * @param repayAccountApplyView
 	 * @return
 	 */
 	@Transactional(readOnly = true,value="loanTransactionManager")
 	public String  getAccountOldData(RepayAccountApplyView repayAccountApplyView){
		if (StringUtils.isNotEmpty(repayAccountApplyView.getOldAccountId())) {
			RepayAccountApplyView raav = new RepayAccountApplyView();
			raav.setId(repayAccountApplyView.getOldAccountId());
			raav = selectAccountInfoById(repayAccountApplyView);
			return getDataToString(raav);
		}
		return "";
 	}
 	
 	 /**
     * 手机号解密 
    ·* 2016年12月9日
    ·* by Huowenlong
     * @param loanCode
     * @return
     */
    public String mobileDisDecrypt(String loanCode){
  	  LoanCustomer loanCustomer =  phoneSecretSerivice.selectCustomerByloancode(loanCode);
  	  if(loanCustomer != null){
  		InnerBean innerBean = new InnerBean();
    	  innerBean.setCreateBy(loanCustomer.getCreateBy());
    	  innerBean.setCreateTime(loanCustomer.getCreateTime());
    	  innerBean.setMobileNums(loanCustomer.getCustomerPhoneFirst());
    	  innerBean.setTableName(EncryptTableCol.LOAN_CUSTOMER_MOBILE_1.getTable());
    	  innerBean.setCol(EncryptTableCol.LOAN_CUSTOMER_MOBILE_1.getCol());
    	  String mobile = phoneSecretSerivice.disDecrypt(innerBean);
    	  if(!"".equals(mobile)&&!"null".equals(mobile)&&null != mobile){
    		return mobile;
    	  }
  	  }
  	  return "";
    }
    
    /**
     * 手机号加密 
    ·* 2016年12月9日
    ·* by Huowenlong
     * @param loanCode
     * @return
     */
    public String mobileDisEncrypt(String loanCode,String mobile){
  	  LoanCustomer loanCustomer =  phoneSecretSerivice.selectCustomerByloancode(loanCode);
  	  InnerBean innerBean = new InnerBean();
  	  innerBean.setCreateBy(loanCustomer.getCreateBy());
  	  innerBean.setCreateTime(loanCustomer.getCreateTime());
  	  innerBean.setMobileNums(mobile);
  	  innerBean.setTableName(EncryptTableCol.LOAN_CUSTOMER_MOBILE_1.getTable());
  	  innerBean.setCol(EncryptTableCol.LOAN_CUSTOMER_MOBILE_1.getCol());
  	  String mobileStr = phoneSecretSerivice.disEncrypt(innerBean);
  	  return mobileStr;
    }
}
