package com.creditharmony.loan.app.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.creditharmony.bpm.filenet.FileNetContextHelper;
import com.creditharmony.bpm.filenet.FileNetRequestContext;
import com.creditharmony.bpm.filenet.utils.FileNetContextHolder;
import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.cache.redis.util.RedisUtils;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.service.SystemManager;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.app.service.AppService;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskInfo;
import com.creditharmony.loan.borrow.outvisit.service.OutVisitService;
import com.creditharmony.loan.common.entity.OutsideCheckInfo;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.query.ProcessQueryBuilder;

/**
 * APP后台接口
 * 
 * @Class Name AppController
 * @author zhangfeng
 * @Create In 2016年1月18日
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping(value = "${adminPath}/app")
public class AppController extends BaseController {

	@Autowired
	private SystemManager systemManager;
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private OutVisitService outVisitService;
	@Autowired
	private AppService appService;

	/**
	 * 终端app登陆接口 2016年1月19日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = request.getParameter("empId");
		String password = request.getParameter("pass");
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(password)) {
			User user = systemManager.getUserByLoginName(userId);
			if (!ObjectHelper.isEmpty(user)) {
				if (Global.NO.equals(user.getStatus())) {
					map.put(AppEnum.STATUS_KEY, AppEnum.LOGIN_STATUS_LEAVE);
					map.put(AppEnum.MSG_KEY, AppEnum.LOGIN_MSG_LEAVE);
				} else if (Global.IS_FROZEN.equals(user.getStatus())) {
					map.put(AppEnum.STATUS_KEY, AppEnum.LOGIN_STATUS_FREEZE);
					map.put(AppEnum.MSG_KEY, AppEnum.LOGIN_MSG_FREEZE);
				} else if (Global.NO.equalsIgnoreCase(user.getHasLogin())) {
					map.put(AppEnum.STATUS_KEY, AppEnum.LOGIN_STATUS_NO);
					map.put(AppEnum.MSG_KEY, AppEnum.LOGIN_MSG_NO);
				} else if (Global.YES.equals(user.getStatus())) {
					try {
						Object userSession = RedisUtils.getObject(AppEnum.LOGINFLAG + userId);
						if (ObjectHelper.isEmpty(userSession)) {
							FileNetRequestContext context = FileNetContextHelper.login(userId, password);
							context.setUserInfo(user);
							RedisUtils.saveObject(AppEnum.LOGINFLAG + userId, context);
						} else {
							FileNetRequestContext context = (FileNetRequestContext) RedisUtils.getObject(AppEnum.LOGINFLAG + userId);
							FileNetContextHelper.login(userId, password);
							context.setUserInfo(user);
						}
						map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_SUCCESS);
						map.put(AppEnum.MSG_KEY, AppEnum.LOGIN_MSG_SUCCESS);
						map.put(AppEnum.DATA_KEY, user);
					} catch (Exception e) {
						map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
						map.put(AppEnum.MSG_KEY, AppEnum.LOGIN_MSG_ERROR);
					}
				}
			} else {
				map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
				map.put(AppEnum.MSG_KEY, AppEnum.LOGIN_MSG_ERROR);
			}
		} else {
			map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
			map.put(AppEnum.MSG_KEY, AppEnum.LOGIN_MSG_ERROR);
		}
		return JsonMapper.nonDefaultMapper().toJson(map);
	}

	/**
	 * 终端app登出接口 2016年1月19日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = request.getParameter("empId");
		if (StringUtils.isNotEmpty(userId)) {
			try {
				FileNetRequestContext context = (FileNetRequestContext) RedisUtils.getObject(AppEnum.LOGINFLAG + userId);
				FileNetContextHelper.logoff(context);
				map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_SUCCESS);
				map.put(AppEnum.MSG_KEY, AppEnum.LOGINOUT_MSG_SUCCESS);
				RedisUtils.remove(AppEnum.LOGINFLAG + userId);
			} catch (Exception e) {
				map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
				map.put(AppEnum.MSG_KEY, AppEnum.LOGINOUT_MSG_ERROR);
			}
		} else {
			map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
			map.put(AppEnum.MSG_KEY, AppEnum.LOGINOUT_MSG_ERROR);
		}
		return JsonMapper.nonDefaultMapper().toJson(map);
	}

	/**
	 * app待办任务 (获取) 2016年1月19日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @return json
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "getTaskList", method = RequestMethod.POST)
	public String getCustList(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> task = new ArrayList<Map<String, Object>>();
		ProcessQueryBuilder qryParam = new ProcessQueryBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String userId = request.getParameter("empId");
		
		String createTime = request.getParameter("createTime");
		String queue = "HJ_VISIT_COMMISSIONER";

		qryParam.put("visitUserId", userId);

		if (StringUtils.isNotEmpty(createTime)) {
			try {
				Date date = sdf.parse(createTime);
				long unixTimestamp = date.getTime()/1000;
				qryParam.put("F_StartTime@>=", unixTimestamp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotEmpty(userId)) {
			FileNetRequestContext context = (FileNetRequestContext) RedisUtils.getObject(AppEnum.LOGINFLAG + userId);
			FileNetContextHolder.setContext(context);

			TaskBean taskList = flowService.fetchTaskItems(queue, qryParam, LoanFlowWorkItemView.class);
			List<LoanFlowWorkItemView> lst = (List<LoanFlowWorkItemView>) taskList.getItemList();

			if (ArrayHelper.isNotEmpty(lst)) {
				for (LoanFlowWorkItemView lwv : lst) {
					List<Map<String, String>> newCustomerList = new ArrayList<Map<String, String>>();
					List<Map<String, String>> newCobList = new ArrayList<Map<String, String>>();
					Map<String, Object> map = new HashMap<String, Object>();
					Map<String, String> mapCob = new HashMap<String, String>();
					Map<String, String> mapCustomer = new HashMap<String, String>();
					
					LoanOutsideTaskInfo outInfo = new LoanOutsideTaskInfo();
					// 借款基本信息
					String loanCode = lwv.getLoanCode();
					mapCustomer.put("loanCode", loanCode);

					// 外访任务清单
					List<OutsideCheckInfo> outTaskList = appService.getOutsideTaskList(mapCustomer);
					if (ArrayHelper.isNotEmpty(outTaskList)) {
						for (OutsideCheckInfo outside : outTaskList) {
							map.put("applyId", lwv.getApplyId());
							map.put("wobNum", lwv.getWobNum());
							map.put("token", lwv.getToken());
							map.put("taskId", outside.getId());
							map.put("loanCode", loanCode);
							map.put("empId", userId);
							map.put("startTime", outside.getSurveyStartTime());
							map.put("state", outside.getDictSurveyStatus());
							map.put("type", outside.getDictCheckType());
							map.put("createTime", sdf.format(outside.getCreateTime()));

							// 主借人信息
							outInfo.setTaskId(outTaskList.get(0).getId());
							outInfo.setDictCustomerType(YESNO.NO.getCode());
							List<LoanOutsideTaskInfo> customerList = appService.getOutsideTaskListInfo(outInfo);
							if (ArrayHelper.isNotEmpty(customerList)) {
								for(LoanOutsideTaskInfo customerInfo : customerList){
									mapCustomer.put("customer_name", customerInfo.getCustomerName());
									mapCustomer.put("customType", customerInfo.getDictCustomerType());
									mapCustomer.put("telephone", customerInfo.getTelephone());
	
									mapCustomer.put("liveProvince", customerInfo.getLiveProvince());
									mapCustomer.put("liveCity", customerInfo.getLiveCity());
									mapCustomer.put("liveArea", customerInfo.getLiveArea());
									mapCustomer.put("liveAddress", customerInfo.getLiveAddress());
	
									mapCustomer.put("houseProvince", customerInfo.getHouseProvince());
									mapCustomer.put("houseCity", customerInfo.getHouseCity());
									mapCustomer.put("houseArea", customerInfo.getHouseArea());
									mapCustomer.put("houseAddress", customerInfo.getHouseAddress());
	
									mapCustomer.put("workInitProvince", customerInfo.getWorkInitProvince());
									mapCustomer.put("workUnitCity", customerInfo.getWorkUnitCity());
									mapCustomer.put("workUnitArea", customerInfo.getWorkUnitArea());
									mapCustomer.put("workUnitAddress", customerInfo.getWorkUnitAddress());
	
									// 获取主借人拍摄要求
									if (StringUtils.isNotEmpty(customerInfo.getCheckJson())) {
										mapCustomer.put("checkJson", appService.getOutCheck(customerInfo.getCheckJson()));
									}
									newCustomerList.add(mapCustomer);
									map.put("customerList", newCustomerList);
								}
							}
	
							// 共借人信息
							outInfo.setTaskId(outTaskList.get(0).getId());
							outInfo.setDictCustomerType(YESNO.YES.getCode());
							List<LoanOutsideTaskInfo> cobList = appService.getOutsideTaskListInfo(outInfo);
							if (ArrayHelper.isNotEmpty(cobList)) {
								for (LoanOutsideTaskInfo cobInfo : cobList) {
									mapCob.put("customer_name", cobInfo.getCustomerName());
									mapCob.put("customType", cobInfo.getDictCustomerType());
									mapCob.put("telephone", cobInfo.getTelephone());
	
									mapCob.put("liveProvince", cobInfo.getLiveProvince());
									mapCob.put("liveCity", cobInfo.getLiveCity());
									mapCob.put("liveArea", cobInfo.getLiveArea());
									mapCob.put("liveAddress", cobInfo.getLiveAddress());
	
									mapCob.put("houseProvince", cobInfo.getHouseProvince());
									mapCob.put("houseCity", cobInfo.getHouseCity());
									mapCob.put("houseArea", cobInfo.getHouseArea());
									mapCob.put("houseAddress", cobInfo.getHouseAddress());
	
									mapCob.put("workInitProvince", cobInfo.getWorkInitProvince());
									mapCob.put("workUnitCity", cobInfo.getWorkUnitCity());
									mapCob.put("workUnitArea", cobInfo.getWorkUnitArea());
									mapCob.put("workUnitAddress", cobInfo.getWorkUnitAddress());
	
									// 获取主借人拍摄要求
									if (StringUtils.isNotEmpty(cobInfo.getCheckJson())) {
										mapCob.put("checkJson", appService.getOutCheck(cobInfo.getCheckJson()));
									}
									newCobList.add(mapCob);
									map.put("cobList", newCobList);
								}
							}
						}
						task.add(map);
					}
				}
			}
		}
		return JsonMapper.nonDefaultMapper().toJson(task);
	}

	/**
	 * app待办任务(完成) 2016年1月19日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "finishTask", method = RequestMethod.POST)
	public String uploadCustList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String userId = request.getParameter("empId");
		String distance = request.getParameter("distance");
		
		// 工作流参数
		String applyId = request.getParameter("applyId");
		String taskId = request.getParameter("taskId");
		String loanCode = request.getParameter("loanCode");
		String wobNum = request.getParameter("wobNum");
		String token = request.getParameter("token");

		User user = systemManager.getUserByLoginName(userId);
		
		// 判断任务是否完成
		Map<String, String> mapCustomer = new HashMap<String, String>();
		mapCustomer.put("loanCode", loanCode);
		List<LoanOutsideTaskInfo> list = appService.getOutListForDistance(mapCustomer);
		if (ArrayHelper.isNotEmpty(list)) {
			map.put(AppEnum.STATUS_KEY, AppEnum.TASK_STATUS_NO);
			map.put(AppEnum.MSG_KEY, AppEnum.TASK_MSG_ERROR);
		} else {
			// 上传文件
			String resStatus = uploadPngs(userId, taskId, loanCode);
			if(StringUtils.equals(AppEnum.STATUS_SUCCESS, resStatus)){
				

				FileNetRequestContext context = (FileNetRequestContext) RedisUtils.getObject(AppEnum.LOGINFLAG + userId);
				FileNetContextHolder.setContext(context);
				// 流转工作流流程
				WorkItemView workItem = new WorkItemView();
				LaunchView bv = new LaunchView();
				LoanInfo info = new LoanInfo();

				workItem.setWobNum(wobNum);
				workItem.setToken(token);
				info.setDictLoanStatus(LoanApplyStatus.VISIT_FINISH_CHECK.getCode());
				workItem.setResponse(LoanFlowRoute.COMPLETEOUTVISIT);

				bv.setApplyId(applyId);
				bv.setDictLoanStatus(LoanApplyStatus.VISIT_FINISH_CHECK.getName());
				bv.setDictLoanStatusCode(LoanApplyStatus.VISIT_FINISH_CHECK.getCode());
				bv.setVisitFinishTime(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				bv.setOutSideUserCode(user.getUserCode());
				bv.setOutSideUserName(user.getName());
				bv.setItemDistance(new BigDecimal(distance));

				info.setLoanCode(loanCode);

				bv.setLoanInfo(info);
				workItem.setBv(bv);

				// 调用流程方法
				flowService.dispatch(workItem);
				
				map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_SUCCESS);
				map.put(AppEnum.MSG_KEY, AppEnum.TASK_MSG_SUCCESS);
			}else{
				map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
				map.put(AppEnum.MSG_KEY, AppEnum.PNGSERV_MSG_ERROR);
			}
		}
		return JsonMapper.nonDefaultMapper().toJson(map);
	}

	/**
	 * 文件上传信雅达 2016年1月19日 By zhangfeng
	 * 
	 * @param userId
	 * @param taskId
	 * @return json
	 */
	public String uploadPngs(String userId, String taskId, String loanCode) {
		String ip = "123.124.20.50";// 信雅达应用服务的ip地址
		// 不变量
		String userName = "admin";// 信雅达数据(不用变)
		String passWord = "111";// 信雅达数据(不用变)
		String groupName = "group"; // 信雅达数据(不用变)
		int sorket = 8023;// 信雅达数据(不用变)

		// 变量
		String objName = "HUIJINSY1";// 信雅达各阶段的业务数据模型 (变量) url中的OBJECT_NAME
		String objNamePart = "HUIJINBJ1"; // 信雅达各阶段的业务数据模型(变量)
		String startdate = "20150611";// 业务发生日期(变量) url中QUERY_TIME
		String scanUser = "zhangsan"; // UserId
		
		String busiserialno = loanCode; // 批次

		String fileType = null;// 文件类型条码(变量) 约定数据
		String resStatus = null;
		String resMsg = null;
		Client client = new Client();
		
		if (StringUtils.isNotEmpty(userId)) {
			User user = systemManager.getUserByLoginName(userId);
			if (!ObjectHelper.isEmpty(user) && StringUtils.isNotEmpty(user.getUserCode())) {
				String filepath = "c:/test/" + user.getUserCode() + "/" + taskId;
				File file = new File(filepath);
				 if (file.isDirectory()) {
					 // 共借人和主借人循环
					 String[] customerLists = file.list();
					 if(null != customerLists){
                     for (int i = 0; i < customerLists.length; i++) {
                    	 
						File customerFiles = new File(filepath + "\\" + customerLists[i]);
                    	 
                    	 // 采集类型循环
                    	 String[] fileTypeLists = customerFiles.list();
                    	 if(null != fileTypeLists){
	                    	 for(int j = 0; j < fileTypeLists.length; j++){
	                    		 File files = new File(customerFiles + "\\" + fileTypeLists[j]);
	    						int fileTypeCode = Integer.valueOf(fileTypeLists[j]);
	                    		//上传文件类型
	 							if (StringUtils.equals(customerLists[i], AppEnum.CUSTOMER)) {
	 								// 主借人
									switch (fileTypeCode) {
									case 1:
										fileType = "1009201";
										break;
									case 2:
										fileType = "1009202";
										break;
									case 3:
										fileType = "1009203";
										break;
									case 4:
										fileType = "1009204";
										break;
									default:
										break;
									}
	 							}else{
	 								// 共借人
	 								switch (fileTypeCode) {
									case 1:
										fileType = "1009301";
										break;
									case 2:
										fileType = "1009302";
										break;
									case 3:
										fileType = "1009303";
										break;
									case 4:
										fileType = "1009304";
										break;
									default:
										break;
									}
	 							}
	 							
	                        	 // 文件循环
	     						 ArrayList<String> fileArray = new ArrayList<String>();
	                        	 String[] filelists = files.list();
	                        	 if(null != filelists){
		                        	 for(int k = 0; k < filelists.length; k++){
		                        		 File f = new File(files + "\\" + filelists[k]);
		                        		 if (!f.isDirectory()) {
		                        			 fileArray.add(f.getPath());
		                                 }
		                        	 }
	                        	 }
	                             try {
	                      			// 提交影像
	                            	 resMsg = client.upload(ip, sorket, groupName, userName, passWord,
	                      					objName, objNamePart, busiserialno, startdate, fileType,
	                      					scanUser, fileArray);
	                            	 //删除本地文件
	        						appService.deleteFile(customerFiles + "\\" + fileTypeLists[j]);
	                      			resStatus = AppEnum.STATUS_SUCCESS;
	                      			logger.debug("invoke AppController method: uploadPngs, " + resMsg);
	                      		} catch (IOException e) {
	                      			resStatus = AppEnum.STATUS_ERROR;
	                      			logger.debug("invoke AppController method: uploadPngs, uploadPng is error!");
	                      		} catch (Exception e) {
	                      			resStatus = AppEnum.STATUS_ERROR;
	                      			logger.debug("invoke AppController method: uploadPngs, uploadPng is error!");
	                      		}
	                    	 }
                    	 }
                     }
					 }
				 }
					appService.deleteFile(filepath);
			}
		}
		return resStatus;
	}
	
	/**
	 * 文件上传 2016年1月19日 By zhangfeng
	 * 
	 * @param request
	 * @param response
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public String uploadPngToServlet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");

		Map<String, Object> map = new HashMap<String, Object>();
		BASE64Decoder decoder = new sun.misc.BASE64Decoder();

		OutputStream out = null;
		
		String file =  request.getParameter("file");
		String userId = request.getParameter("empId");
		String taskId = request.getParameter("taskId");
		String fileName = request.getParameter("fileName");
		

		String loanCode = request.getParameter("loanCode");
		String customerType = request.getParameter("customerType");
		String fileType = request.getParameter("fileType");
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(file)) {

			// 判断任务是否完成
			Map<String, String> mapCustomer = new HashMap<String, String>();
			mapCustomer.put("loanCode", loanCode);
			List<LoanOutsideTaskInfo> list = appService.getOutListForDistance(mapCustomer);
			if (ArrayHelper.isNotEmpty(list)) {
				map.put(AppEnum.STATUS_KEY, AppEnum.TASK_STATUS_NO);
				map.put(AppEnum.MSG_KEY, AppEnum.TASK_MSG_ERROR);
			} else {
				User user = systemManager.getUserByLoginName(userId);
				if (!ObjectHelper.isEmpty(user) && StringUtils.isNotEmpty(user.getUserCode())) {
					try {
						String filePath = "C:/test/" + user.getUserCode() + "/" + taskId + "/" + customerType + "/" + fileType + "/";
						File fileP = new File(filePath);
						if(!fileP.isDirectory()){
							fileP.mkdirs();
						}
						out = new FileOutputStream(filePath + fileName);
						byte[] bytes = decoder.decodeBuffer(file);
						out.write(bytes);
						map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_SUCCESS);
						map.put(AppEnum.MSG_KEY, AppEnum.PNG_MSG_SUCCESS);
						System.out.println(fileName);
					} catch (IOException e) {
						map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
						map.put(AppEnum.MSG_KEY, AppEnum.PNG_MSG_ERROR);
						e.printStackTrace();
						System.out.println("文件上传异常！：");
					} catch (Exception e) {
						map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
						map.put(AppEnum.MSG_KEY, AppEnum.PNG_MSG_ERROR);
						e.printStackTrace();
						System.out.println("文件上传异常！");
					}finally{
						try {
							if(out != null){
								out.flush();
								out.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}else{
					map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
					map.put(AppEnum.MSG_KEY, AppEnum.PNG_MSG_ERROR);
				}
			}
		}else{
			map.put(AppEnum.STATUS_KEY, AppEnum.STATUS_ERROR);
			map.put(AppEnum.MSG_KEY, AppEnum.PNG_MSG_ERROR);
		}
		return JsonMapper.nonDefaultMapper().toJson(map);
	}
}
