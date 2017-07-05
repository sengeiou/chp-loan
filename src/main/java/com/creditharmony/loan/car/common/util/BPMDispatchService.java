package com.creditharmony.loan.car.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.filenet.FileNetContextHelper;
import com.creditharmony.bpm.filenet.FileNetRequestContext;
import com.creditharmony.bpm.filenet.utils.FileNetContextHolder;
import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.common.service.SystemManager;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.loan.car.carGrant.view.CarGrantDealView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.query.ProcessQueryBuilder;

/**
 * 固定账号提交流程（用于车借线上放款收到回盘结果为成功时提交流程到放款确认节点）
 * @Class Name BPMConfigUtils
 * @author 陈伟东
 * @Create In 2016年2月24日
 */
@Service
public class BPMDispatchService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	public final static String NON = "non";
	
	@Resource(name="appFrame_flowServiceImpl")
	FlowService flowService;
	@Autowired
	SystemManager sysManager;
	
	private static final ResourceBundle CONFIG_BUNDLE = ResourceBundle.getBundle("p8");
	
	/**
	 * 
	 * 2016年2月25日
	 * By 陈伟东
	 * @param applyId 用以获取待处理的流程任务
	 * @param token   用以验证当前处理节点的合法性。non:不进行验证
	 * @param CarGrantDealView 包含：借款状态code、回盘结果code、失败原因
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void dispatch(String applyId,String token,CarGrantDealView bv){
		
		logger.info("开启固定账号提交流程，applyId:" + applyId + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult());
		ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
		queryParam.put("applyId", applyId);
		try {
			
			
		
//		try {
//			if(FileNetContextHolder.getContext().getVwSession() == null){
//				logger.error("固定账号提交流程开始认证，applyId:" + applyId + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult());
//				FileNetRequestContext fileNetContext = FileNetContextHelper.login(val("p8username"), val("p8password"));
//				User user = sysManager.getUserByLoginName(val("p8username"));
//				if(user == null){
//					user = new User();
//					user.setLoginName(val("p8username"));
//					user.setId(val("p8username"));
//				}
//				fileNetContext.setUserInfo(user);
//				FileNetContextHolder.setContext(fileNetContext);
//			}
//			User userInfo = FileNetContextHolder.getContext().getUserInfo();
//			if(userInfo == null){
//				userInfo = new User();
//				userInfo.setLoginName(val("p8username"));
//				userInfo.setId(val("p8username"));
//				FileNetContextHolder.getContext().setUserInfo(userInfo);
//			}
//		} catch (Exception e1) {
//			logger.debug(val("p8username"));
//			logger.debug(val("p8password"));
//			logger.debug(val("ce.connectionURL"));
//			logger.debug(val("RemoteServerRmi"));
//			logger.debug(val("ce.domainName"));
//			logger.debug(val("pe.defaultRosterName"));
//			logger.error("异常",e1);
//			e1.printStackTrace();
//		}
//		List<BaseTaskItemView> baseViewList = (List<BaseTaskItemView>) flowService
//				.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_DEDUCTION_COMMISSIONER
//						.getWorkQueue(), queryParam,
//						BaseTaskItemView.class).getItemList();
		logger.debug("什么问题呀！！！");
		WorkItemView workItem = flowService.loadWorkItemViewForAdmin(applyId);
		if (workItem != null) {
//			BaseTaskItemView baseView = baseViewList.get(0);
			WorkItemView view = new WorkItemView();
			try {
				BeanUtils.copyProperties(view, workItem);
				workItem.setResponse(CarLoanResponses.TO_GRANT_AUDIT.getCode());
				workItem.setBv(bv);
			} catch (IllegalAccessException e) {
				logger.error("开启固定账号提交流程异常，applyId:" + applyId + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult() ,e);
			} catch (InvocationTargetException e) {
				logger.error("开启固定账号提交流程异常，applyId:" + applyId + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult() ,e);
			}
			if(NON.equals(token)){
				if(LoansendResult.LOAN_SENDED_SUCCEED.getCode().equals(bv.getGrantRecepicResult())){
					flowService.dispatch(workItem);
					logger.info("完成固定账号提交流程，applyId:" + applyId + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult());
				}else{
					flowService.saveData(workItem);
					logger.info("完成固定账号保存流程属性，applyId:" + applyId + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult());
				}
			}else{
				if(LoansendResult.LOAN_SENDED_SUCCEED.getCode().equals(bv.getGrantRecepicResult())){
					if(workItem.getToken().equals(token)){
						flowService.dispatch(workItem);
						logger.info("完成固定账号提交流程，applyId:" + applyId + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult());
					}else{
						logger.info("完成固定账号提交流程不能进行，因为token不相同，applyId:" + applyId + ";待办token=" + workItem.getToken() + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult());
					}
				}else{
					flowService.saveData(workItem);
					logger.info("完成固定账号保存流程属性，applyId:" + applyId + ";token=" + token + ";回盘结果：" + bv.getGrantRecepicResult());
				}
			}
		}else{
			logger.warn("固定账号提交流程时无法获取待办任务，不能提交此流程流程，applyId:" + applyId + ";token=" + token  + ";回盘结果：" + bv.getGrantRecepicResult());
		}
		} catch (Exception e) {
			logger.error("异常高欢",e);
		}
	}

	private static String val(String key) {
		return CONFIG_BUNDLE.getString(key);
	}
}
