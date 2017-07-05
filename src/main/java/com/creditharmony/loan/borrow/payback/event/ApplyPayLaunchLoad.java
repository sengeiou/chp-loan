package com.creditharmony.loan.borrow.payback.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.BusinessLoadCallBack;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.borrow.contract.service.ContractService;

/**
 *BusinessLoadCallBack接口用于查询、初始化从待办列表进去的业务办理页面数据
 *该接口的load方法签名有两个参数，第一个表示业务ID，第二个表示当前步骤名称
 *若使该方法生效需要在taskDispatch.xml的<dataLoadBeanName>节点进行配置 
 *一个流程仅可配置一个实现了BusinessLoadCallBack接口的dataLoadBean
 *继承BusinessLoadCallBack的同时需要继承BaseService
 *load方法返回的BaseBusinessView被放在WorkItemView的bv属性里面
 *相关JSP页面取值方式可参考/apply/bpmtest/taskHandle.jsp
 */
@Service("load_applyPay_launch")
public class ApplyPayLaunchLoad extends BaseService implements BusinessLoadCallBack {

    @Autowired
    private ContractService contractService;
	
    @Override
	public BaseBusinessView load(String applyId, String stepName) {
		return null;
		
		
	}

}
