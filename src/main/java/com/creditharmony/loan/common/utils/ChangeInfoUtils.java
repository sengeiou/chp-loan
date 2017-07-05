/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsChangeInfoUtils.java
 * @Create By 张灏
 * @Create In 2016年6月21日 下午2:34:13
 */
package com.creditharmony.loan.common.utils;

import org.apache.commons.lang.StringUtils;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.ReturnChangeResult;
import com.creditharmony.loan.borrow.applyinfo.dao.ChangerInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompManage;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanMate;
import com.creditharmony.loan.borrow.applyinfo.view.ChangerInfo;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 退回门店，信息修改记录
 * 
 * @Class Name ChangeInfoUtils
 * @author 张灏
 * @Create In 2016年6月21日
 */
public class ChangeInfoUtils {
	// 插入共借人信息变更记录
	public static void insertCoborrower(LoanCoborrower currCobo, LoanCoborrower savedCobo, String applyId, ChangerInfoDao changerInfoDao) {
		// 新建变更记录
		ChangerInfo info = new ChangerInfo();
		info.setCobId(savedCobo.getId());
		info.setApplyId(applyId);
		info.setChangeCode(savedCobo.getLoanCode());
		info.setChangeType(LoanManFlag.COBORROWE_LOAN.getCode());
		info.setUpdateId(savedCobo.getId());
		if (ObjectHelper.isNotEmpty(currCobo)) {
			info.setDealFlag("0"); // 修改
		} else {
			info.setDealFlag("1"); // 删除
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.DEL_COBORROWER.getCode());
			changerInfoDao.insertChangerInfo(info);
			return;
		}
		if (!savedCobo.getCoboName().equals(currCobo.getCoboName())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.NAME.getCode());
			info.setChangeBegin(savedCobo.getCoboName() == null ? "" : savedCobo.getCoboName());
			info.setChangeAfter(currCobo.getCoboName() == null ? "" : currCobo.getCoboName());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCobo.getCoboCertNum().equals(currCobo.getCoboCertNum())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.IC_CARD.getCode());
			info.setChangeBegin(savedCobo.getCoboCertNum() == null ? "" : savedCobo.getCoboCertNum());
			info.setChangeAfter(currCobo.getCoboCertNum() == null ? "" : currCobo.getCoboCertNum());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCobo.getCoboMobile().equals(currCobo.getCoboMobile())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MOBILE.getCode());
			info.setChangeBegin(savedCobo.getCoboMobile() == null ? "" : savedCobo.getCoboMobile());
			info.setChangeAfter(currCobo.getCoboMobile() == null ? "" : currCobo.getCoboMobile());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedCobo.getCoboMobile2()) && !savedCobo.getCoboMobile2().equals(currCobo.getCoboMobile2())) 
				|| (StringUtils.isNotEmpty(currCobo.getCoboMobile2()) && !currCobo.getCoboMobile2().equals(savedCobo.getCoboMobile2()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MOBILE.getCode());
			info.setChangeBegin(savedCobo.getCoboMobile2() == null ? "" : savedCobo.getCoboMobile2());
			info.setChangeAfter(currCobo.getCoboMobile2() == null ? "" : currCobo.getCoboMobile2());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedCobo.getDictEducation()) && !savedCobo.getDictEducation().equals(currCobo.getDictEducation())) 
				|| (StringUtils.isNotEmpty(currCobo.getDictEducation()) && !currCobo.getDictEducation().equals(savedCobo.getDictEducation()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.DEGREE.getCode());
			info.setChangeBegin(savedCobo.getDictEducation() == null ? "" : savedCobo.getDictEducation());
			info.setChangeAfter(currCobo.getDictEducation() == null ? "" : currCobo.getDictEducation());
			changerInfoDao.insertChangerInfo(info);
		}
		if (StringUtils.isNotEmpty(savedCobo.getDictMarry()) && !savedCobo.getDictMarry().equals(currCobo.getDictMarry())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MARRIED.getCode());
			info.setChangeBegin(savedCobo.getDictMarry() == null ? "" : savedCobo.getDictMarry());
			info.setChangeAfter(currCobo.getDictMarry() == null ? "" : currCobo.getDictMarry());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCobo.getCoboEmail().equals(currCobo.getCoboEmail())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.EMAIL.getCode());
			info.setChangeBegin(savedCobo.getCoboEmail() == null ? "" : savedCobo.getCoboEmail());
			info.setChangeAfter(currCobo.getCoboEmail() == null ? "" : currCobo.getCoboEmail());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedCobo.getCoboNowTel()) && !savedCobo.getCoboNowTel().equals(currCobo.getCoboNowTel())) 
				|| (StringUtils.isNotEmpty(currCobo.getCoboNowTel()) && !currCobo.getCoboNowTel().equals(savedCobo.getCoboNowTel()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.TELPHONE.getCode());
			info.setChangeBegin(savedCobo.getCoboNowTel() == null ? "" : savedCobo.getCoboNowTel());
			info.setChangeAfter(currCobo.getCoboNowTel() == null ? "" : currCobo.getCoboNowTel());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedCobo.getCustomerHouseHoldProperty()) && !savedCobo.getCustomerHouseHoldProperty().equals(currCobo.getCustomerHouseHoldProperty())) 
				|| (StringUtils.isNotEmpty(currCobo.getCustomerHouseHoldProperty()) && !currCobo.getCustomerHouseHoldProperty().equals(savedCobo.getCustomerHouseHoldProperty()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.HOUSE_PORI.getCode());
			info.setChangeBegin(savedCobo.getCustomerHouseHoldProperty() == null ? "" : savedCobo.getCustomerHouseHoldProperty());
			info.setChangeAfter(currCobo.getCustomerHouseHoldProperty() == null ? "" : currCobo.getCustomerHouseHoldProperty());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedCobo.getCoboLiveingProvince()) && !savedCobo.getCoboLiveingProvince().equals(currCobo.getCoboLiveingProvince())) 
				|| (StringUtils.isNotEmpty(currCobo.getCoboLiveingProvince()) && !currCobo.getCoboLiveingProvince().equals(savedCobo.getCoboLiveingProvince())) || (StringUtils.isNotEmpty(savedCobo.getCoboLiveingCity()) && !savedCobo.getCoboLiveingCity().equals(currCobo.getCoboLiveingCity())) || (StringUtils.isNotEmpty(currCobo.getCoboLiveingCity()) && !currCobo.getCoboLiveingCity().equals(savedCobo.getCoboLiveingCity())) || (StringUtils.isNotEmpty(savedCobo.getCoboLiveingArea()) && !savedCobo.getCoboLiveingArea().equals(currCobo.getCoboLiveingArea())) || (StringUtils.isNotEmpty(currCobo.getCoboLiveingArea()) && !currCobo.getCoboLiveingArea().equals(savedCobo.getCoboLiveingArea())) || (StringUtils.isNotEmpty(savedCobo.getCoboNowAddress()) && !savedCobo.getCoboNowAddress().equals(currCobo.getCoboNowAddress())) || (StringUtils.isNotEmpty(currCobo.getCoboNowAddress()) && !currCobo.getCoboNowAddress().equals(savedCobo.getCoboNowAddress()))) {
			StringBuffer s1 = new StringBuffer();
			StringBuffer s2 = new StringBuffer();
			s1.append(savedCobo.getCoboLiveingProvince() == null ? "" : savedCobo.getCoboLiveingProvince()).append(",").append(savedCobo.getCoboLiveingCity() == null ? "" : savedCobo.getCoboLiveingCity()).append(",").append(savedCobo.getCoboLiveingArea() == null ? "" : savedCobo.getCoboLiveingArea()).append(",").append(savedCobo.getCoboNowAddress() == null ? "" : savedCobo.getCoboNowAddress());
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.HOME_ADDRESS.getCode());
			info.setChangeBegin(s1.toString());
			if ("0".equals(info.getDealFlag())) {
				s2.append(currCobo.getCoboLiveingProvince() == null ? "" : currCobo.getCoboLiveingProvince()).append(",").append(currCobo.getCoboLiveingCity() == null ? "" : currCobo.getCoboLiveingCity()).append(",").append(currCobo.getCoboLiveingArea() == null ? "" : currCobo.getCoboLiveingArea()).append(",").append(currCobo.getCoboNowAddress() == null ? "" : currCobo.getCoboNowAddress());
				info.setChangeAfter(s2.toString());
			} else {
				info.setChangeAfter("");
			}
			changerInfoDao.insertChangerInfo(info);
		}

	}

	// 插入公司变更信息
	public static void insertCompany(LoanCompany currComp, LoanCompany savedComp, String applyId, ChangerInfoDao changerInfoDao) {
		// 新建变更记录
		ChangerInfo info = new ChangerInfo();
		info.setCobId(savedComp.getRid());
		info.setApplyId(applyId);
		info.setChangeCode(savedComp.getLoanCode());
		info.setChangeType(savedComp.getDictrCustomterType());
		info.setUpdateId(savedComp.getId());
		if (ObjectHelper.isNotEmpty(currComp)) {
			info.setDealFlag("0"); // 修改
		} else {
			info.setDealFlag("1"); // 删除
			currComp = new LoanCompany();
		}
		if ((StringUtils.isNotEmpty(savedComp.getCompName()) && !savedComp.getCompName().equals(currComp.getCompName())) 
				|| (StringUtils.isNotEmpty(currComp.getCompName()) && !currComp.getCompName().equals(savedComp.getCompName()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.WORK_NAME.getCode());
			info.setChangeBegin(savedComp.getCompName() == null ? "" : savedComp.getCompName());
			info.setChangeAfter(currComp.getCompName() == null ? "" : currComp.getCompName());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedComp.getCompProvince()) && !savedComp.getCompProvince().equals(currComp.getCompProvince())) 
				|| (StringUtils.isNotEmpty(currComp.getCompProvince()) && !currComp.getCompProvince().equals(savedComp.getCompProvince())) || (StringUtils.isNotEmpty(savedComp.getCompCity()) && !savedComp.getCompCity().equals(currComp.getCompCity())) || (StringUtils.isNotEmpty(currComp.getCompCity()) && !currComp.getCompCity().equals(savedComp.getCompCity())) || (StringUtils.isNotEmpty(savedComp.getCompAddress()) && !savedComp.getCompAddress().equals(currComp.getCompAddress())) || (StringUtils.isNotEmpty(currComp.getCompAddress()) && !currComp.getCompAddress().equals(savedComp.getCompAddress())) || (StringUtils.isNotEmpty(savedComp.getCompArer()) && !savedComp.getCompArer().equals(currComp.getCompArer())) || (StringUtils.isNotEmpty(currComp.getCompArer()) && !currComp.getCompArer().equals(savedComp.getCompArer()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.WORK_ADDRESS.getCode());
			StringBuffer s1 = new StringBuffer();
			StringBuffer s2 = new StringBuffer();
			s1.append(savedComp.getCompProvince() == null ? "" : savedComp.getCompProvince()).append(",").append(savedComp.getCompCity() == null ? "" : savedComp.getCompCity()).append(",").append(savedComp.getCompArer() == null ? "" : savedComp.getCompArer()).append(",").append(savedComp.getCompAddress() == null ? "" : savedComp.getCompAddress());
			info.setChangeBegin(s1.toString());
			if ("0".equals(info.getDealFlag())) {
				s2.append(currComp.getCompProvince() == null ? "" : currComp.getCompProvince()).append(",").append(currComp.getCompCity() == null ? "" : currComp.getCompCity()).append(",").append(currComp.getCompArer() == null ? "" : currComp.getCompArer()).append(",").append(currComp.getCompAddress() == null ? "" : currComp.getCompAddress());
				info.setChangeAfter(s2.toString());
			} else {
				info.setChangeAfter("");
			}
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedComp.getCompTel()) && !savedComp.getCompTel().equals(currComp.getCompTel())) 
				|| (StringUtils.isNotEmpty(currComp.getCompTel()) && !currComp.getCompTel().equals(savedComp.getCompTel()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.WORK_TEL.getCode());
			info.setChangeBegin(savedComp.getCompTel() == null ? "" : savedComp.getCompTel());
			info.setChangeAfter(currComp.getCompTel() == null ? "" : currComp.getCompTel());
			changerInfoDao.insertChangerInfo(info);
		}
	}

	// 插入主借人信息变更记录
	public static void insertCustomer(LoanCustomer currCust, LoanCustomer savedCust, String applyId, ChangerInfoDao changerInfoDao) {
		// 新建变更记录
		ChangerInfo info = new ChangerInfo();
		info.setCobId(savedCust.getId());
		info.setApplyId(applyId);
		info.setChangeCode(savedCust.getLoanCode());
		info.setChangeType(LoanManFlag.MAIN_LOAN.getCode());
		info.setUpdateId(savedCust.getId());
		if (ObjectHelper.isNotEmpty(currCust)) {
			info.setDealFlag("0"); // 修改
		} else {
			info.setDealFlag("1"); // 删除
			currCust = new LoanCustomer();
		}
		if (!savedCust.getCustomerName().equals(currCust.getCustomerName())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.NAME.getCode());
			info.setChangeBegin(savedCust.getCustomerName() == null ? "" : savedCust.getCustomerName());
			info.setChangeAfter(currCust.getCustomerName() == null ? "" : currCust.getCustomerName());

			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCust.getCustomerCertNum().equals(currCust.getCustomerCertNum())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.IC_CARD.getCode());
			info.setChangeBegin(savedCust.getCustomerCertNum() == null ? "" : savedCust.getCustomerCertNum());
			info.setChangeAfter(currCust.getCustomerCertNum() == null ? "" : currCust.getCustomerCertNum());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCust.getCustomerPhoneFirst().equals(currCust.getCustomerPhoneFirst())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MOBILE.getCode());
			info.setChangeBegin(savedCust.getCustomerPhoneFirst() == null ? "" : savedCust.getCustomerPhoneFirst());
			info.setChangeAfter(currCust.getCustomerPhoneFirst() == null ? "" : currCust.getCustomerPhoneFirst());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedCust.getCustomerPhoneSecond()) && !savedCust.getCustomerPhoneSecond().equals(currCust.getCustomerPhoneSecond())) || (StringUtils.isNotEmpty(currCust.getCustomerPhoneSecond()) && !currCust.getCustomerPhoneSecond().equals(savedCust.getCustomerPhoneSecond()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MOBILE.getCode());
			info.setChangeBegin(savedCust.getCustomerPhoneSecond() == null ? "" : savedCust.getCustomerPhoneSecond());
			info.setChangeAfter(currCust.getCustomerPhoneSecond() == null ? "" : currCust.getCustomerPhoneSecond());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCust.getDictEducation().equals(currCust.getDictEducation())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.DEGREE.getCode());
			info.setChangeBegin(savedCust.getDictEducation() == null ? "" : savedCust.getDictEducation());
			info.setChangeAfter(currCust.getDictEducation() == null ? "" : currCust.getDictEducation());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCust.getDictMarry().equals(currCust.getDictMarry())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MARRIED.getCode());
			info.setChangeBegin(savedCust.getDictMarry() == null ? "" : savedCust.getDictMarry());
			info.setChangeAfter(currCust.getDictMarry() == null ? "" : currCust.getDictMarry());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCust.getCustomerEmail().equals(currCust.getCustomerEmail())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.EMAIL.getCode());
			info.setChangeBegin(savedCust.getCustomerEmail() == null ? "" : savedCust.getCustomerEmail());
			info.setChangeAfter(currCust.getCustomerEmail() == null ? "" : currCust.getCustomerEmail());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedCust.getCustomerTel()) && !savedCust.getCustomerTel().equals(currCust.getCustomerTel())) || (StringUtils.isNotEmpty(currCust.getCustomerTel()) && !currCust.getCustomerTel().equals(savedCust.getCustomerTel()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.TELPHONE.getCode());
			info.setChangeBegin(savedCust.getCustomerTel() == null ? "" : savedCust.getCustomerTel());
			info.setChangeAfter(currCust.getCustomerTel() == null ? "" : currCust.getCustomerTel());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedCust.getCustomerLiveProvince().equals(currCust.getCustomerLiveProvince()) || !savedCust.getCustomerLiveCity().equals(currCust.getCustomerLiveCity()) || !savedCust.getCustomerLiveArea().equals(currCust.getCustomerLiveArea()) || !savedCust.getCustomerAddress().equals(currCust.getCustomerAddress())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.HOME_ADDRESS.getCode());
			info.setChangeBegin(savedCust.getCustomerLiveProvince() + "," + savedCust.getCustomerLiveCity() + "," + savedCust.getCustomerLiveArea() + "," + savedCust.getCustomerAddress());
			if ("0".equals(info.getDealFlag())) {
				info.setChangeAfter(currCust.getCustomerLiveProvince() + "," + currCust.getCustomerLiveCity() + "," + currCust.getCustomerLiveArea() + "," + currCust.getCustomerAddress());
			} else {
				info.setChangeAfter("");
			}
			changerInfoDao.insertChangerInfo(info);
		}
	}

	// 插入联系人变更记录
	public static void insertContact(Contact currContact, Contact savedContact, String applyId, ChangerInfoDao changerInfoDao) {
		// 新建变更记录
		ChangerInfo info = new ChangerInfo();
		info.setCobId(savedContact.getRcustomerCoborrowerId());
		info.setApplyId(applyId);
		info.setChangeCode(savedContact.getLoanCode());
		info.setChangeType(savedContact.getLoanCustomterType());
		info.setUpdateId(savedContact.getId());
		if (ObjectHelper.isNotEmpty(currContact)) {
			info.setDealFlag("0"); // 修改
		} else {
			info.setDealFlag("1"); // 删除
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.DEL_CONTACT.getCode());
			changerInfoDao.insertChangerInfo(info);
			return;
		}
		if (!savedContact.getContactName().equals(currContact.getContactName())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.CONTACT_NAME.getCode());
			info.setChangeBegin(savedContact.getContactName() == null ? "" : savedContact.getContactName());
			info.setChangeAfter(currContact.getContactName() == null ? "" : currContact.getContactName());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedContact.getContactMobile()) && !savedContact.getContactMobile().equals(currContact.getContactMobile())) 
				|| (StringUtils.isNotEmpty(currContact.getContactMobile()) && !currContact.getContactMobile().equals(savedContact.getContactMobile()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.CONTACT_PHONE.getCode());
			info.setChangeBegin(savedContact.getContactMobile() == null ? "" : savedContact.getContactMobile());
			info.setChangeAfter(currContact.getContactMobile() == null ? "" : currContact.getContactMobile());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedContact.getRelationType().equals(currContact.getRelationType())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.CONTACT_TYPE_MAIN.getCode());
			info.setChangeBegin(savedContact.getRelationType() == null ? "" : savedContact.getRelationType());
			info.setChangeAfter(currContact.getRelationType() == null ? "" : currContact.getRelationType());
			changerInfoDao.insertChangerInfo(info);
		}
		if (!savedContact.getContactRelation().equals(currContact.getContactRelation())) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.CONTACT_TRELATION_MAIN.getCode());
			info.setChangeBegin(savedContact.getContactRelation() == null ? "" : savedContact.getContactRelation());
			info.setChangeAfter(currContact.getContactRelation() == null ? "" : currContact.getContactRelation());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedContact.getCertNum()) && !savedContact.getCertNum().equals(currContact.getCertNum()))
				|| (StringUtils.isNotEmpty(currContact.getCertNum()) && !currContact.getCertNum().equals(savedContact.getCertNum()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.CONTACT_CERT.getCode());
			info.setChangeBegin(savedContact.getCertNum() == null ? "" : savedContact.getCertNum());
			info.setChangeAfter(currContact.getCertNum() == null ? "" : currContact.getCertNum());
			changerInfoDao.insertChangerInfo(info);
		}
	}

	// 插入配偶信息变更记录
	public static void insertMate(LoanMate currMate, LoanMate savedMate, String applyId, ChangerInfoDao changerInfoDao) {
		// 新建变更记录
		ChangerInfo info = new ChangerInfo();
		info.setCobId(savedMate.getRcustomerCoborrowerId());
		info.setApplyId(applyId);
		info.setChangeCode(savedMate.getLoanCode());
		info.setChangeType(savedMate.getLoanCustomterType());
		info.setUpdateId(savedMate.getId());
		if (ObjectHelper.isNotEmpty(currMate)) {
			info.setDealFlag("0"); // 修改
		} else {
			info.setDealFlag("1"); // 删除
			currMate = new LoanMate();
		}
		if ((StringUtils.isNotEmpty(savedMate.getMateName()) && !savedMate.getMateName().equals(currMate.getMateName())) 
				|| (StringUtils.isNotEmpty(currMate.getMateName()) && !currMate.getMateName().equals(savedMate.getMateName()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MATE_NAME.getCode());
			info.setChangeBegin(savedMate.getMateName() == null ? "" : savedMate.getMateName());
			info.setChangeAfter(currMate.getMateName() == null ? "" : currMate.getMateName());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedMate.getMateTel()) && !savedMate.getMateTel().equals(currMate.getMateTel())) 
				|| (StringUtils.isNotEmpty(currMate.getMateTel()) && !currMate.getMateTel().equals(savedMate.getMateTel()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MATE_MOBILE.getCode());
			info.setChangeBegin(savedMate.getMateTel() == null ? "" : savedMate.getMateTel());
			info.setChangeAfter(currMate.getMateTel() == null ? "" : currMate.getMateTel());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedMate.getMateCertNum()) && !savedMate.getMateCertNum().equals(currMate.getMateCertNum())) 
				|| (StringUtils.isNotEmpty(currMate.getMateCertNum()) && !currMate.getMateCertNum().equals(savedMate.getMateCertNum()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.MATE_IC_CARD.getCode());
			info.setChangeBegin(savedMate.getMateCertNum() == null ? "" : savedMate.getMateCertNum());
			info.setChangeAfter(currMate.getMateCertNum() == null ? "" : currMate.getMateCertNum());
			changerInfoDao.insertChangerInfo(info);
		}
	}

	public static void insertLoanCompManage(LoanCompManage loanCompManage, LoanCompManage savedLoanCompManage, String applyId, ChangerInfoDao changerInfoDao) {

		// 新建变更记录
		ChangerInfo info = new ChangerInfo();
		info.setCobId(savedLoanCompManage.getId());
		info.setApplyId(applyId);
		info.setChangeCode(savedLoanCompManage.getLoanCode());
		info.setChangeType(LoanManFlag.MAIN_LOAN.getCode());
		info.setUpdateId(savedLoanCompManage.getId());
		if (ObjectHelper.isNotEmpty(loanCompManage)) {
			info.setDealFlag("0"); // 修改
		} else {
			info.setDealFlag("1"); // 删除
			loanCompManage = new LoanCompManage();
		}
		if ((StringUtils.isNotEmpty(savedLoanCompManage.getCertNum()) 
				&& !savedLoanCompManage.getCertNum().equals(loanCompManage.getCertNum()))
				|| (StringUtils.isNotEmpty(loanCompManage.getCertNum()) 
				&& !loanCompManage.getCertNum().equals(savedLoanCompManage.getCertNum()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.LEGAL_PERSON_CERT.getCode());
			info.setChangeBegin(savedLoanCompManage.getCertNum() == null ? "" : savedLoanCompManage.getCertNum());
			info.setChangeAfter(loanCompManage.getCertNum() == null ? "" : loanCompManage.getCertNum());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedLoanCompManage.getCorporateRepresentMobile()) 
				&& !savedLoanCompManage.getCorporateRepresentMobile().equals(loanCompManage.getCorporateRepresentMobile()))
				|| (StringUtils.isNotEmpty(loanCompManage.getCorporateRepresentMobile()) 
				&& !loanCompManage.getCorporateRepresentMobile().equals(savedLoanCompManage.getCorporateRepresentMobile()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.LEGAL_PERSON_PHONE.getCode());
			info.setChangeBegin(savedLoanCompManage.getCorporateRepresentMobile() == null ? "" : savedLoanCompManage.getCorporateRepresentMobile());
			info.setChangeAfter(loanCompManage.getCorporateRepresentMobile() == null ? "" : loanCompManage.getCorporateRepresentMobile());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedLoanCompManage.getCompEmail()) 
				&& !savedLoanCompManage.getCompEmail().equals(loanCompManage.getCompEmail()))
			    || (StringUtils.isNotEmpty(loanCompManage.getCompEmail()) 
				&& !loanCompManage.getCompEmail().equals(savedLoanCompManage.getCompEmail()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.LEGAL_PERSON_EMAIL.getCode());
			info.setChangeBegin(savedLoanCompManage.getCompEmail() == null ? "" : savedLoanCompManage.getCompEmail());
			info.setChangeAfter(loanCompManage.getCompEmail() == null ? "" : loanCompManage.getCompEmail());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedLoanCompManage.getCorporateRepresent()) 
				&& !savedLoanCompManage.getCorporateRepresent().equals(loanCompManage.getCorporateRepresent()))
				|| (StringUtils.isNotEmpty(loanCompManage.getCorporateRepresent()) 
				&& !loanCompManage.getCorporateRepresent().equals(savedLoanCompManage.getCorporateRepresent()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.LEGAL_PERSON_NAME.getCode());
			info.setChangeBegin(savedLoanCompManage.getCorporateRepresent() == null ? "" : savedLoanCompManage.getCorporateRepresent());
			info.setChangeAfter(loanCompManage.getCorporateRepresent() == null ? "" : loanCompManage.getCorporateRepresent());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedLoanCompManage.getCreditCode()) 
				&& !savedLoanCompManage.getCreditCode().equals(loanCompManage.getCreditCode()))
			    || (StringUtils.isNotEmpty(loanCompManage.getCreditCode()) 
				&& !loanCompManage.getCreditCode().equals(savedLoanCompManage.getCreditCode()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.LEGAL_CREDIT_CODE.getCode());
			info.setChangeBegin(savedLoanCompManage.getCreditCode() == null ? "" : savedLoanCompManage.getCreditCode());
			info.setChangeAfter(loanCompManage.getCreditCode() == null ? "" : loanCompManage.getCreditCode());
			changerInfoDao.insertChangerInfo(info);
		}
		if ((StringUtils.isNotEmpty(savedLoanCompManage.getOrgCode()) 
				&& !savedLoanCompManage.getOrgCode().equals(loanCompManage.getOrgCode()))
				|| (StringUtils.isNotEmpty(loanCompManage.getOrgCode()) 
				&& !loanCompManage.getOrgCode().equals(savedLoanCompManage.getOrgCode()))) {
			info.preInsert();
			info.setChangeResult(ReturnChangeResult.LEGAL_ORG_CODE.getCode());
			info.setChangeBegin(savedLoanCompManage.getOrgCode() == null ? "" : savedLoanCompManage.getOrgCode());
			info.setChangeAfter(loanCompManage.getOrgCode() == null ? "" : loanCompManage.getOrgCode());
			changerInfoDao.insertChangerInfo(info);
		}
	}
}
