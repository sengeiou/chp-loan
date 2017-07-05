package com.creditharmony.loan.car.common.service;

import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.filenet.FileNetContextHelper;
import com.creditharmony.bpm.filenet.FileNetRequestContext;
import com.creditharmony.bpm.filenet.utils.FileNetContextHolder;
import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.core.common.service.SystemManager;
import com.creditharmony.core.users.entity.User;
import com.query.ProcessQueryBuilder;

/**
 * 固定账号提交流程（用于车借线上放款收到回盘结果为成功时提交流程到放款确认节点）
 * @Class Name BPMConfigUtils
 * @author 陈伟东
 * @Create In 2016年2月24日
 */
@Service
public class CarBPMLoginService {
	
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
	public void bpmLoginService(String applyId){
		
		logger.info("开启固定账号提交流程，applyId:" + applyId );
		ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
		queryParam.put("applyId", applyId);
		
		
		try {
			if(FileNetContextHolder.getContext().getVwSession() == null){
				logger.info("固定账号提交流程开始认证，applyId:" + applyId );
				FileNetRequestContext fileNetContext = FileNetContextHelper.login(val("p8username"), val("p8password"));
				// 目前现在取user有问题，所以为了暂时可以使用，使用了固定账号。
				User user = sysManager.getUserByLoginName(val("p8username"));
				fileNetContext.setUserInfo(user);
				FileNetContextHolder.setContext(fileNetContext);
				logger.info("固定账号提交流程结束，applyId:" + applyId );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String val(String key) {
		return CONFIG_BUNDLE.getString(key);
	}
}
