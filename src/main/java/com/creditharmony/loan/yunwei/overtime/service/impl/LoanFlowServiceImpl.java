package com.creditharmony.loan.yunwei.overtime.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.creditharmony.bpm.filenet.utils.ConfigUtils;
import com.creditharmony.bpm.filenet.utils.FileNetContextHolder;
import com.creditharmony.bpm.frame.config.FlowInfoDefinitionConfig;
import com.creditharmony.bpm.frame.exception.WorkflowException;
import com.creditharmony.bpm.frame.service.impl.FlowServiceImpl;
import com.creditharmony.bpm.frame.utils.Constant;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.loan.yunwei.overtime.entity.LoanWorkItemView;
import com.strongit.filenet.pe.core.VWRosterHelper;

import filenet.vw.api.VWException;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWWorkObject;

@Service("LoanFlowServiceImpl")
public class LoanFlowServiceImpl extends  FlowServiceImpl{
	private void fillWorkItem(LoanWorkItemView item, VWWorkObject vwWorkObject){
		item.setFlowName(vwWorkObject.getWorkClassName());
		item.setStepName(vwWorkObject.getStepName());
		item.setWobNum(vwWorkObject.getWorkObjectNumber());
		
		if (vwWorkObject.hasFieldName(Constant.FLOW_FRAME_FIELD_TOKEN)){
			item.setToken((String)vwWorkObject.getFieldValue(Constant.FLOW_FRAME_FIELD_TOKEN));
		}
		if (vwWorkObject.hasFieldName(Constant.FLOW_FRAME_FIELD_EX_SUB_DATA)){
			item.setExSubData((String)vwWorkObject.getFieldValue(Constant.FLOW_FRAME_FIELD_EX_SUB_DATA));
		}
		if (vwWorkObject.hasFieldName(Constant.FLOW_FRAME_FIELD_TRACK_STATE)){
			item.setTrackState((String)vwWorkObject.getFieldValue(Constant.FLOW_FRAME_FIELD_TRACK_STATE));
		}
		if (vwWorkObject.hasFieldName(Constant.FLOW_FRAME_FIELD_MESSAGE)){
			item.setMessage((String)vwWorkObject.getFieldValue(Constant.FLOW_FRAME_FIELD_MESSAGE));
		}
		if (vwWorkObject.hasFieldName("timeOutPoint")){
			item.setTimeOutPoint((Date)vwWorkObject.getFieldValue("timeOutPoint"));
		}
	}
	
 
	@Override
	public WorkItemView loadWorkItemView(String applyId) {

		VWSession vwSession = FileNetContextHolder.getContext().getVwSession();
		try {
			LoanWorkItemView item = new LoanWorkItemView();
			String configName = null;
			if (applyId != null && !"".equals(applyId)){
//				List<VWWorkObject> list = VWRosterHelper.fetchTaskObjects(vwSession,ConfigUtils.getRosterName(), "applyId = '" + applyId + "'", null, 3);
				List<VWWorkObject> list = VWRosterHelper.fetchTaskObjects(vwSession,ConfigUtils.getRosterName(), "applyId = :applyIdp", new String[]{applyId}, 3);
				if (list == null || list.size() == 0){
					throw new WorkflowException("没有找到该任务applyId对应的任务,请检查：" + applyId);
				}
				
				if (list.size() > 1){
					throw new WorkflowException("该applyId对应多个任务,请检查：" + applyId);
				}
				VWWorkObject vwWorkObject = list.get(0);
				fillWorkItem(item, vwWorkObject);
				
				configName = FlowInfoDefinitionConfig.getInstance().getFlowConfigNameByFlowName(item.getFlowName());
			} else {
				throw new WorkflowException("applyId参数值为空."); 
			}
			
			return item;
		} catch (VWException e) {
			throw new WorkflowException(e.getMessage(),e);
		}
	
	}
 
	 
}
