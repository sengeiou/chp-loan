package com.creditharmony.loan.channel.goldcredit.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.jxcredit.JxSendContractFileBaseService;
import com.creditharmony.adapter.service.jxcredit.bean.JxSendContractFileInBean;
import com.creditharmony.adapter.service.jxcredit.bean.JxSendContractFileOutBean;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;

import filenet.vw.base.logging.Logger;
/**
 * 根据接口组提供的合同编号,查询未盖章的合同文件信息
 * @author zhouhongliang
 *
 */
@Service
public class JxSendContractFileService extends JxSendContractFileBaseService{
	private Logger logger = Logger.getLogger(JxReceiveContractFileService.class);
	@Autowired
	private ContractFileService contractFileService;
	@Override
	public JxSendContractFileOutBean doExec(JxSendContractFileInBean paramBean) {
		logger.info("【开始执行查询操作】");
		JxSendContractFileOutBean outbean = new JxSendContractFileOutBean();
		if(paramBean==null){
			outbean.setRetCode(ReturnConstant.FAIL);
			outbean.setRetMsg("【参数为null】");
		}
		String contractCode = null;
		ContractFile contractFileQuery = new ContractFile();
		//金信的合同名--信用咨询及管理服务协议-金信
		contractFileQuery.setContractFileName(ContractType.CONTRACT_MANAGE_JX.getName());
		contractFileQuery.setContractCode(contractCode);
		List<ContractFile> contractFileList = contractFileService.findList(contractFileQuery);
		
		//金信的合同名--信用咨询及管理服务协议(保证人)-金信    
		contractFileQuery.setContractFileName(ContractType.CONTRACT_MANAGE_JX2_ASSURER.getName());
		List<ContractFile> contractFileListBaozhen = contractFileService.findList(contractFileQuery);
		ContractFile contractFile = null;
		if(contractFileList!=null&&contractFileList.size()>0){
			 contractFile = contractFileList.get(0);
		}
		if(contractFileListBaozhen!=null&&contractFileListBaozhen.size()>0){
			 contractFile = contractFileListBaozhen.get(0);
		}
		if(contractFile!=null){
			String docId = contractFile.getDocId();
			logger.info("【根据合同编号"+contractCode+",查询出的docId为"+docId+"】");
			outbean.setDocId(docId);
			outbean.setRetCode(ReturnConstant.SUCCESS);
			outbean.setRetMsg("【查询成功!】");
		}else{
			logger.info("【根据合同编号"+contractCode+",查询出的docId为null】");
			outbean.setRetCode(ReturnConstant.FAIL);
			outbean.setRetMsg("【没有查询出数据】");
		}
		return outbean;
	}

}
