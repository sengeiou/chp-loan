package com.creditharmony.loan.borrow.serve.ws;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.zcj.ZcjUploadFilesBaseService;
import com.creditharmony.adapter.service.zcj.bean.ZcjUploadFilesInParam;
import com.creditharmony.adapter.service.zcj.bean.ZcjUploadFilesOutParam;
import com.creditharmony.loan.borrow.serve.dao.CustomerServeDao;
import com.creditharmony.loan.borrow.serve.view.ContractFileSendEmailView;

/**
 * 合同盖完章后回调类
 * 
 * @Class Name ZcjUploadFilesService
 * @author WJJ
 * @Create In 2017年1月6日
 */
@Service
public class ZcjUploadFilesService extends ZcjUploadFilesBaseService{

	private Logger logger = LoggerFactory.getLogger(ZcjUploadFilesService.class);
	
	@Autowired
	private CustomerServeDao customerServeDao;
	
	@Override
	public ZcjUploadFilesOutParam doExec(ZcjUploadFilesInParam zcjUploadFilesInParam) {
		ZcjUploadFilesOutParam zcjUploadFilesOutParam = new ZcjUploadFilesOutParam();
		try{
			//大金融结清证明盖完章后业务
			if("zcjJQZM".equals(zcjUploadFilesInParam.getType())){
				//修改结清证明盖完章后的DOCID和状态
				ContractFileSendEmailView contractFileSendEmailView = new ContractFileSendEmailView();
				contractFileSendEmailView.setPdfId(zcjUploadFilesInParam.getDocid());
				contractFileSendEmailView.setContractCode(zcjUploadFilesInParam.getContractCode());
				contractFileSendEmailView.setDocId(zcjUploadFilesInParam.getOldDocId());
				contractFileSendEmailView.setSendStatus("2");
				contractFileSendEmailView.setDictFileType("1");
				contractFileSendEmailView.setModifyTime(new Date());
				customerServeDao.updatePdfId(contractFileSendEmailView);
				zcjUploadFilesOutParam.setRetCode(ReturnConstant.SUCCESS);
				zcjUploadFilesOutParam.setRetMsg("执行成功");
				logger.error("ZcjUploadFilesOutParam类接收大金融盖章结清证明成功");
			}else if("zcjJKXY".equals(zcjUploadFilesInParam.getType())){
				ContractFileSendEmailView contractFileSendEmailView = new ContractFileSendEmailView();
				contractFileSendEmailView.setPdfId(zcjUploadFilesInParam.getDocid());
				contractFileSendEmailView.setContractCode(zcjUploadFilesInParam.getContractCode());
				contractFileSendEmailView.setDictFileType("0");
				contractFileSendEmailView.setModifyTime(new Date());
				customerServeDao.updateDocId(contractFileSendEmailView);
				zcjUploadFilesOutParam.setRetCode(ReturnConstant.SUCCESS);
				zcjUploadFilesOutParam.setRetMsg("执行成功");
				logger.error("ZcjUploadFilesOutParam类接收大金融借款协议成功");
			} 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("ZcjUploadFilesService类执行失败");
			zcjUploadFilesOutParam.setRetCode(ReturnConstant.ERROR);
			zcjUploadFilesOutParam.setRetMsg("执行失败，失败原因："+e.getMessage());
		}
		return zcjUploadFilesOutParam;
	}

}
