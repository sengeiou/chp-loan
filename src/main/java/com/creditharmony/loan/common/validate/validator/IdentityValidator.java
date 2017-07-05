package com.creditharmony.loan.common.validate.validator;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.loan.common.validate.annotation.Identity;
import com.creditharmony.loan.common.validate.regexp.Regexp;

/**
 * 身份信息校验
 * @author 任志远
 * @date 2017年1月9日
 */
public class IdentityValidator implements ConstraintValidator<Identity, String> {

	/**
	 * 要验证的类型，主要用途区分错误信息
	 */
	private CertificateType validCertificateType;
	
	@Override
	public void initialize(Identity identity) {
		validCertificateType = identity.certificateType();
	}

	@Override
	public boolean isValid(String certNum, ConstraintValidatorContext context) {

		//证件号码
		if(StringUtils.isEmpty(certNum)){
			return false;
		}
		
		//身份证 或者 临时身份证
		if(CertificateType.SFZ.getCode().equals(validCertificateType.getCode())){
			//转成大写
			certNum = certNum.toUpperCase();
			//整体正则校验
			//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
			Pattern pattern = Pattern.compile(Regexp.ID_CARD);
			Matcher matcher = pattern.matcher(certNum);
			if(!matcher.matches()){
				return false;
			}
			//获取身份证长度
			int certNumLength = certNum.length();
			//15位身份证
			if (certNumLength == 15) {
			
				int year = Integer.parseInt(certNum.substring(6, 8));
				int month = Integer.parseInt(certNum.substring(8, 10));
				int day = Integer.parseInt(certNum.substring(10, 12));
				Calendar c = Calendar.getInstance();
				c.set(year, month, day);
				Calendar calendar = Calendar.getInstance();
				calendar.set(1900+year, month-1, day);
				boolean isGoodDay = ((c.get(Calendar.YEAR) - 1900) == year) && ((c.get(Calendar.MONTH)+1) == month) && ((c.get(Calendar.DATE)) == day);
				if(!isGoodDay){
					return false;
				}else{
					return true;
 				}
			}
			//18位身份证校验
			if(certNumLength == 18){
				
				int year = Integer.parseInt(certNum.substring(6, 10));
				int month = Integer.parseInt(certNum.substring(10, 12));
				int day = Integer.parseInt(certNum.substring(12, 14));
				Calendar c = Calendar.getInstance();
				c.set(year, month-1, day);
				boolean isGoodDay = (c.get(Calendar.YEAR) == year) && ((c.get(Calendar.MONTH)+1) == month) && ((c.get(Calendar.DATE)) == day);
				if(!isGoodDay){
					return false;
				}else{
					Integer[] arrInt = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
					String[] arrCh = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
					int nTemp = 0;
					for (int i = 0; i < 17; i++) {
						nTemp += Integer.parseInt(certNum.substring(i, i+1)) * arrInt[i];
					}
					String lastOne= arrCh[nTemp % 11];
					if (!lastOne.equals(certNum.substring(17))) {
						return false;
					}
					return true;
				}
			}
		}else if(CertificateType.HKB.getCode().equals(validCertificateType.getCode())){//户口本
			return true;
		}else if(CertificateType.HZ.getCode().equals(validCertificateType.getCode())){//护照
			return true;
		}else if(CertificateType.JGZ.getCode().equals(validCertificateType.getCode())){//军官证
			return true;
		}else if(CertificateType.SBZ.getCode().equals(validCertificateType.getCode())){//士兵证
			return true;
		}else if(CertificateType.GAJMLWNDTXZ.getCode().equals(validCertificateType.getCode())){//港澳居民来往内地通行证
			return true;
		}else if(CertificateType.TWTBLWNDTXZ.getCode().equals(validCertificateType.getCode())){//台湾同胞来往内地通行证
			return true;
		}else if(CertificateType.WGRJLZ.getCode().equals(validCertificateType.getCode())){//外国人居留证
			return true;
		}else if(CertificateType.JCZ.getCode().equals(validCertificateType.getCode())){//警官证
			return true;
		}else if(CertificateType.OTHER.getCode().equals(validCertificateType.getCode())){
			return true;
		}
		
		return false;
	}

}
