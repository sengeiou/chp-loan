package com.creditharmony.loan.common.vo;

import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * 
 * @author zjb Feb 18, 2016  6:31:50 PM 
 *
 */
public class Errors {

	public static String errorMsg(org.springframework.validation.Errors errors){
		if (errors.hasErrors() || errors.hasGlobalErrors()) {
			StringBuffer errInfo = new StringBuffer();
			List<FieldError> ferrors = errors.getFieldErrors();
			if (ferrors.size()>0){
				for (FieldError error : ferrors) {
					errInfo.append(error.getDefaultMessage()+"！\r\n");
				}
			}
			List<ObjectError> gerrors = errors.getGlobalErrors();
			if (gerrors.size()>0){
				for (ObjectError error : gerrors) {
					errInfo.append(error.getDefaultMessage()+"！\r\n");
				}
			}
			errors.reject(null, errInfo.toString());
			return errInfo.toString();
		} 
		return "";
	}
}
