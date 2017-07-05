package com.creditharmony.loan.common.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.djrcreditor.DjrReceiveContractFileBaseService;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveContractFileInParam;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveContractFileOutParam;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;

/**
 * 1.01 接收盖章后合同文件信息接口
 * 
 * @author wenlongliu
 *
 */
@Service
public class DjrReceiveContractFileService extends DjrReceiveContractFileBaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ContractFileService contractFileService;
	
	@Override
	public DjrReceiveContractFileOutParam doExec(DjrReceiveContractFileInParam paramBean) {
		DjrReceiveContractFileOutParam param = new DjrReceiveContractFileOutParam();
		logger.info("+++++++=接口盖章的合同参数:"+JSONObject.toJSONString(paramBean) );
		try
		{
			ContractFile file = new ContractFile();
			file.setContractCode(paramBean.getContractCode());
			file.setDocId(paramBean.getSrcFileName());
			file = contractFileService.getContractFile(file);
			if (ObjectHelper.isNotEmpty(file)) {
				file.setModifyBy("0");
				file.setModifyTime(new java.util.Date());
				file.setSignDocId(paramBean.getDocId());
				contractFileService.updateCtrFile(file);
			}else {
				logger.info("大金融发送过来的合同文件在系统中没有查找到,合同编号为："+paramBean.getContractCode()
						+",合同文件为："+paramBean.getSrcFileName());
				param.setRetCode(ReturnConstant.ERROR);
				param.setRetMsg("大金融发送过来的合同文件在系统中没有查找到");
			}
		}catch(Exception ex){
			logger.error("接收大金融债权退回盖章后合同文件信息失败，发生异常",ex);
			param.setRetCode(ReturnConstant.ERROR);
			param.setRetMsg(ex.getMessage());
		}
		param.setRetCode(ReturnConstant.SUCCESS);
		param.setRetMsg("成功");
		return param;
	}

}
