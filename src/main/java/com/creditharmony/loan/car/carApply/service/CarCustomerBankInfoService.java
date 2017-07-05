package com.creditharmony.loan.car.carApply.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ListUtils;
import com.creditharmony.common.util.ObjectUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.MaintainStatus;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.account.view.RepayAccountApplyView;
import com.creditharmony.loan.car.carBankInfo.view.CarCustomerBankInfoView;
import com.creditharmony.loan.car.common.dao.CarCustomerBankInfoDao;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;

/**
 * 银行卡信息
 * @Class Name CarCustomerBankInfoService
 * @author 安子帅
 * @Create In 2016年2月16日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarCustomerBankInfoService extends CoreManager<CarCustomerBankInfoDao, CarCustomerBankInfo> {
	@Autowired
	private CarCustomerBankInfoDao carCustomerBankInfoDao;

	/**
	 * 保存银行卡信息 2016年2月16日 
	 * By 安子帅
	 * @param carCustomerBankInfo
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveCarCustomerBankInfo(CarCustomerBankInfo carCustomerBankInfo) {
		carCustomerBankInfoDao.insert(carCustomerBankInfo);
	}
	/**
	 * 查询银行卡信息 2016年2月16日 
	 * By 安子帅
	 * @param String
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public CarCustomerBankInfo selectCarCustomerBankInfo(String loanCode) {
		return carCustomerBankInfoDao.selectCarCustomerBankInfo(loanCode);
	}
	/**
	 * 修改
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void upadteCarCustomerBankInfo(CarCustomerBankInfo carCustomerBankInfo) {
		carCustomerBankInfoDao.update(carCustomerBankInfo);
	}
	
	/**
	 * 根据ID修改
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void upadteCarCustomerBankInfoById(CarCustomerBankInfo carCustomerBankInfo) {
		carCustomerBankInfoDao.upadteCarCustomerBankInfoById(carCustomerBankInfo);
	}

//-----------------------------------车借还款账号--------------------------------------\\
	/**
	 * 车借
	 * 查询还款账号维护申请列表
	 * @param String
	 */
	public Page<CarCustomerBankInfoView> getCarCustomerBankInfoList(Page<CarCustomerBankInfoView> page,CarCustomerBankInfoView info) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CarCustomerBankInfoView> pageList = (PageList<CarCustomerBankInfoView>) carCustomerBankInfoDao
				.getCarCustomerBankInfoList(pageBounds, info);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 车借
	 * 查询还款账号维护申请列表
	 * 去掉查询条件
	 * @param String
	 */
	public List<CarCustomerBankInfoView> getCarCustomerBankInfoList(CarCustomerBankInfoView info) {
		List<CarCustomerBankInfoView> s = carCustomerBankInfoDao
				.getCarCustomerBankInfoList(info);
		return s;
	}

	/**
	 * 车借
	 * 还款账户新增页面显示数据
	 * @param String
	 */
	public CarCustomerBankInfoView getCustomerMsg(String loanCode) {
		CarCustomerBankInfoView customerMsg = carCustomerBankInfoDao
				.getCustomerMsg(loanCode);
		return customerMsg;
	}
	
	/**
	 * 新建车借还款账号
	 * @param info
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void insertBankInfo(CarCustomerBankInfoView info) {
		info.preInsert();
		info.setModifyBy(null);
		info.setModifyTime(null);
		carCustomerBankInfoDao.insertBankInfoAdd(info);
		//添加历史
		info.setOperateStep("新增");
		info.setNewData(getDataToString(info));
		carCustomerBankInfoDao.insertCjBankInfoLog(info);
	}

	/**
	 * 获取车借还款账号基本数据信息，包括用户名，合同编号 
	 * @param info
	 */
	public CarCustomerBankInfoView getBankInfoMsg(String id) {
		return carCustomerBankInfoDao.getBankInfoMsg(id);
	}
	
	/**
	 * 根据借款编号
	 * 获取车借共借人 
	 * @param loanCode
	 */
	public List<CarCustomerBankInfoView> getCoborrowerList(String loanCode) {
		return carCustomerBankInfoDao.getCoborrowerList(loanCode);
	}
	
	/**
	 * 车借
	 * 通过ID查询还款账号信息，用于插入数据到修改手机号
	 * @param info
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveNewCjBankInfoPhone(CarCustomerBankInfoView info){
		CarCustomerBankInfoView i = carCustomerBankInfoDao.getCjBankInfoById(info.getOldBankAccountId());
		i.setFileId(info.getFileId());
		i.setFileName(info.getFileName());
		//设置置顶值为0，不置顶
		i.setTop(MaintainType.ADD.getCode());
		//设置维护类型为 新建
		i.setDictMaintainType(MaintainType.CHANGE.getCode());
		//设置维护状态为"待审核"
		i.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
		i.setNewCustomerPhone(info.getNewCustomerPhone());
		i.setUpdateType(info.getUpdateType());
		i.setOldBankAccountId(info.getOldBankAccountId());
		i.setCoboId(info.getCoboId());
		i.setCoboName(info.getCoboName());
		i.setCoboCertNum(info.getCoboCertNum());
		i.setCoboMobile(info.getCoboMobile());
		i.preInsert();
		i.setModifyBy(null);
		i.setModifyTime(null);
		i.setBankCheckResult(null);
		i.setBankCheckDesc(null);
		carCustomerBankInfoDao.insertBankInfoAdd(i);
		//添加历史
		i.setOperateStep("修改手机号");
		StringBuffer newData = new StringBuffer();
		StringBuffer oldData = new StringBuffer();
		newData.append("<p>客户新手机号:###"+ i.getNewCustomerPhoneEnc()+"###</p>");
		oldData.append("<p>客户原手机号:###"+ info.getCustomerPhoneFirstEnc()+"###</p>");
		if(StringUtils.isNotEmpty(i.getCoboId())){
			CarCustomerBankInfoView coboMsg = carCustomerBankInfoDao.getCoboMsg(i);
			if(null != coboMsg){
				newData.append("<p>共借人姓名:"+ i.getCoboName()+"</p>");
				newData.append("<p>共借人身份证号:"+ i.getCoboCertNum()+"</p>");
				newData.append("<p>共借人手机号:###"+ i.getCoboMobileEnc()+"###</p>");
				
				oldData.append("<p>共借人姓名:"+ coboMsg.getCoboName()+"</p>");
				oldData.append("<p>共借人身份证号:"+ coboMsg.getCoboCertNum()+"</p>");
				oldData.append("<p>共借人手机号:###"+ coboMsg.getCoboMobileEnc()+"###</p>");
			}
		}
		i.setNewData(newData.toString());
		i.setOldData(oldData.toString());
		carCustomerBankInfoDao.insertCjBankInfoLog(i);
	}

	/**
	 * 车借
	 * 通过ID查询还款账号信息，用于插入数据到修改银行卡号
	 * @param info
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveNewCjBankInfoAccount(CarCustomerBankInfoView info){
		CarCustomerBankInfoView i = carCustomerBankInfoDao.getCjBankInfoById(info.getOldBankAccountId());
		String bankSigningPlatform = i.getBankSigningPlatform();
		String bankCardNo = i.getBankCardNo();
		String applyBankName = i.getApplyBankName();
		i.setFileId(info.getFileId());
		i.setFileName(info.getFileName());
		//设置置顶值为0，不置顶
		i.setTop(MaintainType.ADD.getCode());
		//设置维护类型为 新建
		i.setDictMaintainType(MaintainType.CHANGE.getCode());
		//设置维护状态为"待审核"
		i.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
		i.setApplyBankName(info.getApplyBankName());
		i.setBankSigningPlatform(info.getBankSigningPlatform());
		i.setBankCardNo(info.getBankCardNo());
		i.setUpdateType(info.getUpdateType());
		i.setOldBankAccountId(info.getOldBankAccountId());
		i.setCoboId(info.getCoboId());
		i.setCoboName(info.getCoboName());
		i.setCoboCertNum(info.getCoboCertNum());
		i.preInsert();
		i.setModifyBy(null);
		i.setModifyTime(null);
		i.setBankCheckResult(null);
		i.setBankCheckDesc(null);
		carCustomerBankInfoDao.insertBankInfoAdd(i);
		//添加历史
		i.setOperateStep("修改还款账号");
		i.setApplyBankName(applyBankName);
		i.setBankSigningPlatform(bankSigningPlatform);
		i.setBankCardNo(bankCardNo);
		i.setNewData(getDataToString(info));
		i.setOldData(getDataToString(i));
		carCustomerBankInfoDao.insertCjBankInfoLog(i);
	}

	/**
	 * 车借
	 * 通过ID查询还款账号信息，用于插入数据到修改邮箱
	 * @param info
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveNewCjBankInfoEmail(CarCustomerBankInfoView info){
		CarCustomerBankInfoView i = carCustomerBankInfoDao.getCjBankInfoById(info.getOldBankAccountId());
		i.setFileId(info.getFileId());
		i.setFileName(info.getFileName());
		//设置置顶值为0，不置顶
		i.setTop(MaintainType.ADD.getCode());
		i.setDictMaintainType(MaintainStatus.TO_REFUSE.getCode());
		//设置维护状态为"待审核"
		i.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
		i.setNewEmail(info.getNewEmail());
		i.setUpdateType(info.getUpdateType());
		i.setOldBankAccountId(info.getOldBankAccountId());
		i.setCoboId(info.getCoboId());
		i.setCoboName(info.getCoboName());
		i.setCoboCertNum(info.getCoboCertNum());
		i.setCoboEmail(info.getCoboEmail());
		i.preInsert();
		i.setModifyBy(null);
		i.setModifyTime(null);
		i.setBankCheckResult(null);
		i.setBankCheckDesc(null);
		carCustomerBankInfoDao.insertBankInfoAdd(i);
		//添加历史
		i.setOperateStep("修改邮箱地址");

		StringBuffer newData = new StringBuffer();
		StringBuffer oldData = new StringBuffer();
		newData.append("<p>客户新邮箱地址:"+ i.getNewEmail()+"</p>");
		oldData.append("<p>客户原邮箱地址:"+ info.getCustomerEmail()+"</p>");
		if(StringUtils.isNotEmpty(i.getCoboId())){
			CarCustomerBankInfoView coboMsg = carCustomerBankInfoDao.getCoboMsg(i);
			if(null != coboMsg){
				newData.append("<p>共借人姓名:"+ i.getCoboName()+"</p>");
				newData.append("<p>共借人身份证号:"+ i.getCoboCertNum()+"</p>");
				newData.append("<p>共借人邮箱地址:"+ i.getCoboEmail()+"</p>");
				
				oldData.append("<p>共借人姓名:"+ coboMsg.getCoboName()+"</p>");
				oldData.append("<p>共借人身份证号:"+ coboMsg.getCoboCertNum()+"</p>");
				oldData.append("<p>共借人邮箱地址:"+ coboMsg.getCoboEmail()+"</p>");
			}
		}
		i.setNewData(newData.toString());
		i.setOldData(oldData.toString());
		carCustomerBankInfoDao.insertCjBankInfoLog(i);
	}

	/**
	 * 车借还款账户
	 * 置顶
	 * @param CarCustomerBankInfoView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateAccountTop(CarCustomerBankInfoView info){
		info.preUpdate();
		PageBounds pageBounds = new PageBounds(Integer.parseInt(MaintainStatus.TO_CHECK.getCode()),Integer.parseInt(MaintainStatus.TO_REFUSE.getCode()));
		CarCustomerBankInfoView view =  carCustomerBankInfoDao
				.getBankInfoList(pageBounds, info).get(0);
		
		List<CarCustomerBankInfoView> infos = carCustomerBankInfoDao.getBankInfoByLoanCode(info.getLoanCode());
		if(ListUtils.isNotEmptyList(infos)){
			for(CarCustomerBankInfoView infoView : infos){
				infoView.preUpdate();
				carCustomerBankInfoDao.deleteBankInfo(infoView);
				carCustomerBankInfoDao.insertBankInfoAdd(infoView);
			}
		}
		carCustomerBankInfoDao.deleteBankInfoAdd(view);
		carCustomerBankInfoDao.insertBankInfo(view);
		carCustomerBankInfoDao.updateAccountTop(view);
		//添加历史
		info.setLoanCode(info.getLoanCode());
		info.setOperateStep("置顶");
		carCustomerBankInfoDao.insertCjBankInfoLog(info);
	}
	
//--------------------------------------车借还款账号(已办列表/审核列表)-------------------------------------\\
	/**
	 * 车借
	 * 查询还款账号
	 * 已办列表 
	 * 审核列表
	 * 通过维护状态来区别
	 * @param String
	 */
	public Page<CarCustomerBankInfoView> getBankInfoList(Page<CarCustomerBankInfoView> page,CarCustomerBankInfoView info) {
		if(StringUtils.isNotEmpty(info.getOrgCode())){
			String [] s = info.getOrgCode().split(",");
			info.setConditions(Arrays.asList(s));
		}
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CarCustomerBankInfoView> pageList = (PageList<CarCustomerBankInfoView>) carCustomerBankInfoDao
				.getBankInfoList(pageBounds, info);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	public CarCustomerBankInfoView getOldMsg(String id) {
		return carCustomerBankInfoDao.getOldMsg(id);
	}
	
	public CarCustomerBankInfoView getOldCoborrowerMsg(String id) {
		return carCustomerBankInfoDao.getOldCoborrowerMsg(id);
	}

	/**
	 * 车借还款账户
	 * 审核确认
	 * @param CarCustomerBankInfoView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateBankInfoCheckMsg(CarCustomerBankInfoView info){
		info.preUpdate();
		//如果确认为拒绝，保存拒绝数据
		if(info.getBankCheckResult().equals(MaintainStatus.TO_REFUSE.getCode())){
			info.setDictMaintainStatus(MaintainStatus.TO_REFUSE.getCode());
			carCustomerBankInfoDao.checkBankInfoRefuse(info);
			//添加历史
			info.setLoanCode(info.getLoanCode());
			info.setOperateStep("审核拒绝");
			carCustomerBankInfoDao.insertCjBankInfoLog(info);
		}
		//如果确认为通过，保存通过数据
		else if(info.getBankCheckResult().equals(MaintainStatus.TO_MAINTAIN.getCode())){
			PageBounds pageBounds = new PageBounds(Integer.parseInt(MaintainStatus.TO_CHECK.getCode()),Integer.parseInt(MaintainStatus.TO_REFUSE.getCode()));
			CarCustomerBankInfoView view =  carCustomerBankInfoDao
					.getBankInfoList(pageBounds, info).get(0);
			//判断是新增还是修改
			if(view.getDictMaintainType().equals(MaintainStatus.TO_REFUSE.getCode())){
				info.setDictMaintainStatus(MaintainStatus.TO_MAINTAIN.getCode());
				info.setDictMaintainType(view.getDictMaintainType());
				info.setFileId(view.getFileId());
				info.setFileName(view.getFileName());
				//共借人信息
				info.setCoboId(view.getCoboId());
				info.setCoboName(view.getCoboName());
				info.setCoboCertNum(view.getCoboCertNum());
				//如果修改类型为手机号码，则执行修改手机号码语句
				if(info.getUpdateType().equals(MaintainStatus.TO_REFUSE.getCode())){
					info.setNewEmail(null);
					info.setCoboMobile(view.getCoboMobile());
					carCustomerBankInfoDao.updatePhoneOrEmail(info);
				}
				//如果修改类型为银行卡号，则执行修改银行卡号语句
				else if(info.getUpdateType().equals(MaintainStatus.TO_MAINTAIN.getCode())){
					info.setBankSigningPlatform(view.getBankSigningPlatform());
					info.setBankCardNo(view.getBankCardNo());
					info.setApplyBankName(view.getApplyBankName());
				}
				//如果修改类型为邮箱地址，则执行修改邮箱地址语句
				else if(info.getUpdateType().equals(MaintainStatus.TO_FIRST.getCode())){
					info.setNewCustomerPhone(null);
					info.setCoboEmail(view.getCoboEmail());
					carCustomerBankInfoDao.updatePhoneOrEmail(info);
				}
				carCustomerBankInfoDao.checkTrueUpdateAndDelete(info);
			}else if(view.getDictMaintainType().equals(MaintainStatus.TO_CHECK.getCode())){
				view.setDictMaintainStatus(MaintainStatus.TO_MAINTAIN.getCode());
				view.setBankCheckResult(info.getBankCheckResult());
				view.setBankCheckDesc(info.getBankCheckDesc());
				List<CarCustomerBankInfoView> infos = carCustomerBankInfoDao.getBankInfoByLoanCode(info.getLoanCode());
				if(ListUtils.isNotEmptyList(infos)){
					for(CarCustomerBankInfoView infoView : infos){
						infoView.preUpdate();
						carCustomerBankInfoDao.deleteBankInfo(infoView);
						infoView.setBankProvinceCity(infoView.getBankCity());
						carCustomerBankInfoDao.insertBankInfoAdd(infoView);
					}
				}
				view.preUpdate();
				carCustomerBankInfoDao.deleteBankInfoAdd(view);
				carCustomerBankInfoDao.insertBankInfo(view);
			}
			carCustomerBankInfoDao.updateAccountTop(view);
			//添加历史
			info.setOperateStep("审核通过");
			carCustomerBankInfoDao.insertCjBankInfoLog(info);
		}
	}
	

	/**
	 * 获取账户变更信息详细
	 * @param CarCustomerBankInfoView
	 * @return
	 */
	private String getDataToString(CarCustomerBankInfoView info){
		DictCache dict = DictCache.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append("<p>划扣账号:").append(info.getBankCardNo()==null?"":info.getBankCardNo()).append("</p>");
		sb.append("<p>开户行支行:").append(info.getApplyBankName()==null?"":info.getApplyBankName()).append("</p>");
		sb.append("<p>划扣平台:").append(dict.getDictLabel("jk_deduct_plat", info.getBankSigningPlatform())).append("</p>");
		return sb.toString();
	}
	
	/**
	 * 获取车借还款账户历史
	 * @param CarCustomerBankInfoView
	 * @return
	 */
	public List<CarCustomerBankInfoView> getHistoryList(CarCustomerBankInfoView view){
		return carCustomerBankInfoDao.getHistoryList(view);
	}
}
