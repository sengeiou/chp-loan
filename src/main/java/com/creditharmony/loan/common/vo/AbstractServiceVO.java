package com.creditharmony.loan.common.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by ShenTeng on 2015/1/8.
 */
public abstract class AbstractServiceVO<T extends AbstractServiceVO> {

	protected boolean success = true;
	protected String code;
	protected String msg;

	public static DefaultServiceVO createMsg(boolean boo, String message) {
		DefaultServiceVO vo = new DefaultServiceVO();
		vo.success = boo;
		vo.msg = message;
		return vo;
	}

	public static DefaultServiceVO createErrorBykey(String errorKey) {
		DefaultServiceVO vo = new DefaultServiceVO();
		vo.success = false;
		vo.code = ErrorCode.getErrorCode(errorKey);
		vo.msg = ErrorCode.getErrorMsg(errorKey);
		return vo;
	}

	public static DefaultServiceVO createSuccessByKey(String key) {
		DefaultServiceVO vo = new DefaultServiceVO();
		vo.success = true;
		vo.code = ErrorCode.getErrorCode(key);
		vo.msg = ErrorCode.getErrorMsg(key);
		return vo;
	}

	public static DefaultServiceVO createSuccess(String message) {
		DefaultServiceVO vo = new DefaultServiceVO();
		vo.success = true;
		vo.msg = message;
		return vo;
	}

	public static DefaultServiceVO errorMsg(String errorMsg) {
		DefaultServiceVO vo = new DefaultServiceVO();
		vo.success = false;
		vo.code = "10002";
		vo.msg = errorMsg;
		return vo;
	}

	public T error(String errorKey) {
		this.success = false;
		// this.errorCode = ErrorCode.getErrorCode(errorKey);
		this.msg = ErrorCode.getErrorMsg(errorKey);
		return (T) this;
	}

	public T error(AbstractServiceVO<?> vo) {
		this.success = false;
		this.code = vo.getCode();
		this.msg = vo.getMsg();
		return (T) this;
	}

	public T error(String errorKey, String extMsg) {
		this.success = false;
		this.code = ErrorCode.getErrorCode(errorKey);
		this.msg = ErrorCode.getErrorMsg(errorKey, extMsg);
		return (T) this;
	}

	public T systemError() {
		return error("global.systemError");
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean errorKeyEquals(String code) {
		if (StringUtils.isBlank(code)) {
			return false;
		}

		String inputErrorCode = ErrorCode.getErrorCode(code);
		if (StringUtils.isBlank(inputErrorCode)) {
			return false;
		}

		return inputErrorCode.equals(code);
	}
	

}
