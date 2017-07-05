/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.serviceGrantCAService.java
 * @Create By 张灏
 * @Create In 2016年4月25日 下午3:58:05
 */
package com.creditharmony.loan.car.carGrant.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.CarContractType;
import com.creditharmony.core.loan.type.CarExtendType;
import com.creditharmony.core.loan.type.LoanCASignType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarContractFileDao;
import com.creditharmony.loan.car.common.dao.CarCustomerDao;
import com.creditharmony.loan.car.common.dao.CarLoanCoborrowerDao;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarContractFile;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.common.consts.CAKeyWord;
import com.creditharmony.loan.common.entity.CaCustomerSign;
import com.creditharmony.loan.common.utils.CaUtil;

/**
 * @Class Name CarGrantCAService
 * @author 葛志超
 * @Create In 2016年5月9日
 */
@Service
public class CarGrantCAService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CarContractFileDao carContractFileDao;

	@Autowired
	private CarLoanCoborrowerDao carLoanCoborrowerDao;

	@Autowired
	private CarCustomerDao carCustomerDao;
	@Autowired
	private CarContractDao carContractDao;



	/**
	 * CA签章
	 *
	 * @author 葛志超
	 * @create In 2016年5月9日
	 * @param
	 * @return boolean
	 *
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean signUpCA(String loanCode, String contractVersion) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loanCode", loanCode);
		CarCustomer loanCustomer = carCustomerDao.selectByloanCode(loanCode);
		List<CarLoanCoborrower> coborrowerList = carLoanCoborrowerDao
				.selectByLoanCode(loanCode);
		StringBuffer custName = new StringBuffer();
		StringBuffer cerNum = new StringBuffer();
		custName.append(loanCustomer.getCustomerName());
		cerNum.append(loanCustomer.getCustomerCertNum());
		if (!ObjectHelper.isEmpty(coborrowerList)) {
			for (CarLoanCoborrower c : coborrowerList) {
				custName.append("   ").append(c.getCoboName());
				cerNum.append(" ").append(c.getCertNum());
			}
		}
		// 查询不可下载的合同文件
		param.put("downloadFlag", YESNO.NO.getCode());
		List<CarContractFile> files = carContractFileDao
				.getContractFileByParam(param);
		boolean result = false;
		String contractCode = null;
		if (!ObjectHelper.isEmpty(files)) {
			contractCode = files.get(0).getContractCode();
		}
		LoanCASignType loanCaSignType = null;
		CaCustomerSign customerSign = new CaCustomerSign(custName.toString(),
				CAKeyWord.CUSTOMER_SIGN, contractCode, cerNum.toString(),loanCustomer.getCustomerPhoneFirst());

		int toSignFileAmout = 0; // 应签章文件个数
		int signedFileAmout = 0; // 完成签章文件个数
		for (CarContractFile file : files) {
			customerSign.setCustName(custName.toString());
			if (StringUtils.isEmpty(file.getSignDocId())) {
				toSignFileAmout++;
				for(int i = 0;i<5;i++){
					result = false;
					if (CarContractType.CARCONTRACT_PROTOCOL_GPS_YJ.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					} else if (CarContractType.CAECONTRACT_MANAGE_GPS_YJ.getName().equals(
							file.getContractFileName())) {
						if(!"1.6".equals(contractVersion)){
							loanCaSignType = LoanCASignType.ALL_SIGN;
						}
					} else if (CarContractType.CARCONTRACT_RETURN_MANAGE_GPS_YJ.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					} else if (CarContractType.CARCONTRACT_DELEGATE_GPS_YJ.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					}else if (CarContractType.CARCONTRACT_RECEIPT_GPS_YJ.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					} else if (CarContractType.CARCONTRACT_TRANSACTION_GPS_YJ.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					} else if (CarContractType.CARCONTRACT_BILL_GPS_YJ.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					}else if (CarContractType.CARCONTRACT_PROTOCOL_ZY.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					} else if (CarContractType.CAECONTRACT_MANAGE_ZY.getName().equals(
							file.getContractFileName())) {
						if(!"1.6".equals(contractVersion)){
							loanCaSignType = LoanCASignType.ALL_SIGN;
						}
					} else if (CarContractType.CARCONTRACT_RETURN_MANAGE_ZY.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					}else if (CarContractType.CARCONTRACT_DELEGATE_ZY.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					} else if (CarContractType.CARCONTRACT_RECEIPT_ZY.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					} else if (CarContractType.CARCONTRACT_TRANSACTION_ZY.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					}else if (CarContractType.CARCONTRACT_BILL_ZY.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					} else if (CarContractType.CARCONTRACT_CAR_ZY.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
						customerSign.setCustName(loanCustomer.getCustomerName());
						customerSign.setCertNum(loanCustomer.getCustomerCertNum());
					} else if (CarContractType.CONTRACT_BILL_ZY.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					}else if (CarExtendType.CAREXTENDTYPE_PROTOCOL_GPS_YJ.getName().equals(
							file.getContractFileName())) {
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
						if(!"1.6".equals(contractVersion)) {
							loanCaSignType = LoanCASignType.PER_COM_ALL_SIGN_APPROVE;
						}else{
							loanCaSignType = LoanCASignType.COMPANY_HJ;
						}
					} else if (CarExtendType.CAREXTENDTYPE_MANAGE_GPS_YJ.getName().equals(
							file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					} else if (CarExtendType.CAREXTENDTYPE_RETURN_MANAGE_GPS_YJ.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					}else if (CarExtendType.CAREXTENDTYPE_RETURN_MFSM_GPS_YJ.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					}else if (CarContractType.CARCONTRACT_MFSM.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					}else if (CarExtendType.CAREXTENDTYPE_PROTOCOL_ZY.getName()
							.equals(file.getContractFileName())) {
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
						if(!"1.6".equals(contractVersion)) {
							loanCaSignType = LoanCASignType.PER_COM_ALL_SIGN_APPROVE;
						}else{
							loanCaSignType = LoanCASignType.COMPANY_HJ;
						}
					}else if (CarExtendType.CAREXTENDTYPE_MANAGE_ZY.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.CUSTOMER;
						customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
					}else if (CarExtendType.CAREXTENDTYPE_RETURN_MANAGE_ZY.getName()
							.equals(file.getContractFileName())) {
						loanCaSignType = LoanCASignType.PER_COM;
						customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
					}

					String outInfoDocid = null;
					if((CarContractType.CAECONTRACT_MANAGE_ZY.getName().equals(file.getContractFileName())
						||CarContractType.CAECONTRACT_MANAGE_GPS_YJ.getName().equals(file.getContractFileName()))
						&& "1.6".equals(contractVersion)){//1.6合同版本信用咨询及管理服务协议只加盖两个章
						outInfoDocid = CaUtil.signHJCompanyAndCustomer(file.getDocId(), customerSign);
					}else if((CarExtendType.CAREXTENDTYPE_PROTOCOL_GPS_YJ.getName().equals(file.getContractFileName())
						 ||CarExtendType.CAREXTENDTYPE_PROTOCOL_ZY.getName().equals(file.getContractFileName()))
						 && "1.6".equals(contractVersion)){//1.6合同版本展期协议只加盖两个章
						outInfoDocid = CaUtil.signHjXy(loanCaSignType, file.getDocId(), customerSign);
					}else {
						outInfoDocid = CaUtil.caSign(loanCaSignType,
								file.getDocId(), customerSign);
					}

					if (!ObjectHelper.isEmpty(outInfoDocid)) {
						file.setSignDocId(outInfoDocid);
						try {
							carContractFileDao.updateCtrFile(file);
							result = true;
						} catch (Exception e) {
							logger.error("签章完成后保存合同文件表异常：",e);
							result = false;
						}
						break;
					}
				}
				if(result){
					signedFileAmout++;
				}
			}else{
				signedFileAmout++;
				toSignFileAmout++;
				result = true;
			}
		}
		if(toSignFileAmout != 0){
			CarContract carContract = new CarContract();
			carContract.setLoanCode(loanCode);
			if(toSignFileAmout == signedFileAmout){
				carContract.setSignFlag("1");
				carContractDao.update(carContract);
				result = true;
			}else{
				carContract.setSignFlag("0");
				carContractDao.update(carContract);
				result = false;
			}
		}
		return result;
	}
}
