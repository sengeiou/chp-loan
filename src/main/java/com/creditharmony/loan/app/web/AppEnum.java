package com.creditharmony.loan.app.web;

public class AppEnum {

	public static String STATUS_KEY = "status";
	public static String MSG_KEY = "msg";
	public static String DATA_KEY = "data";

	// 状态
	public static String STATUS_SUCCESS = "200"; // 成功
	public static String STATUS_ERROR = "201"; // 失败
	
	// 登陆
	public static String LOGIN_STATUS_LEAVE = "203";// 离职
	public static String LOGIN_STATUS_FREEZE = "204";// 冻结
	public static String LOGIN_STATUS_NO = "205";// 不允许登陆

	// 提示信息
	public static String LOGIN_MSG_LEAVE = "该用户已离职,不允许登录!";
	public static String LOGIN_MSG_FREEZE = "该帐号被冻结，请与系统管理员联系!";
	public static String LOGIN_MSG_NO = "该用户不允许登录，请与系统管理员联系!";
	public static String LOGIN_MSG_SUCCESS = "成功登陆";
	public static String LOGIN_MSG_ERROR = "用户名或密码错误";
	public static String LOGINOUT_MSG_SUCCESS = "成功登出";
	public static String LOGINOUT_MSG_ERROR = "登出异常";
	public static String TASK_MSG_SUCCESS  = "数据成功上传";
	public static String PNG_MSG_SUCCESS  = "图片成功上传";
	public static String PNG_MSG_ERROR  = "图片上传失败";
	public static String PNGSERV_MSG_ERROR  = "信雅达图片上传失败";
	public static String TASK_MSG_ERROR  = "该任务已完成";
	
	// 完成任务标识
	public static String TASK_STATUS_NO = "300";// 该任务已完成标识

	// session 标识
	public static String LOGINFLAG = "appLogin-";
	
	public static String CUSTOMER = "1";
	
	public static String COB = "2";
}
