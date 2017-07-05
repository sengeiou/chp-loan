package com.creditharmony.loan.borrow.serve.ws;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.jinxin.JxUploadFilesBaseService;
import com.creditharmony.adapter.service.jinxin.bean.JxUploadFilesInParam;
import com.creditharmony.adapter.service.jinxin.bean.JxUploadFilesOutParam;
import com.creditharmony.loan.borrow.serve.dao.CustomerServeDao;
import com.creditharmony.loan.borrow.serve.view.ContractFileSendEmailView;

/**
 * 金信借款协议回调类
 * 
 * @Class Name JxUploadFilesService
 * @author WJJ
 * @Create In 2017年1月12日
 */
@Service
public class JxUploadFilesService extends JxUploadFilesBaseService {

	private Logger logger = LoggerFactory.getLogger(JxUploadFilesService.class);
	
	@Autowired
	private CustomerServeDao customerServeDao;
	
	@Override
	public JxUploadFilesOutParam doExec(JxUploadFilesInParam jxUploadFilesInParam) {
		JxUploadFilesOutParam jxUploadFilesOutParam = new JxUploadFilesOutParam();
		try{
			jxUploadFilesInParam.getContractCode();
			jxUploadFilesInParam.getDocid();
			logger.error("JxUploadFilesService类接收金信借款协议合同号："+jxUploadFilesInParam.getContractCode()+",DOCID:"+jxUploadFilesInParam.getDocid());
			//修改金信电子借款协议DOCID和状态
			if("jxJKXY".equals(jxUploadFilesInParam.getType())){
				ContractFileSendEmailView contractFileSendEmailView = new ContractFileSendEmailView();
				contractFileSendEmailView.setPdfId(jxUploadFilesInParam.getDocid());
				contractFileSendEmailView.setContractCode(jxUploadFilesInParam.getContractCode());
				contractFileSendEmailView.setDictFileType("0");
				contractFileSendEmailView.setModifyTime(new Date());
				customerServeDao.updateDocId(contractFileSendEmailView);
				jxUploadFilesOutParam.setRetCode(ReturnConstant.SUCCESS);
				jxUploadFilesOutParam.setRetMsg("执行成功");
				logger.error("JxUploadFilesService类接收金信借款协议成功");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("JxUploadFilesService类执行失败");
			jxUploadFilesOutParam.setRetCode(ReturnConstant.ERROR);
			jxUploadFilesOutParam.setRetMsg("执行失败，失败原因："+e.getMessage());
		}
		return jxUploadFilesOutParam;
	}

}
