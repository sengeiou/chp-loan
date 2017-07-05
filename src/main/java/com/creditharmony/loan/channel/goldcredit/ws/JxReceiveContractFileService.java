package com.creditharmony.loan.channel.goldcredit.ws;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.jxcredit.JxReceiveContractFileBaseService;
import com.creditharmony.adapter.service.jxcredit.bean.JxReceiveContractFileInBean;
import com.creditharmony.adapter.service.jxcredit.bean.JxReceiveContractFileOutBean;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;

import filenet.vw.base.logging.Logger;
@Service
public class JxReceiveContractFileService extends
		JxReceiveContractFileBaseService {
	private Logger logger = Logger.getLogger(JxReceiveContractFileService.class);
	@Autowired
	private ContractFileService contractFileService;
	@Override
	public JxReceiveContractFileOutBean doExec(JxReceiveContractFileInBean inBean) {
 		JxReceiveContractFileOutBean outBean = new JxReceiveContractFileOutBean();
		logger.info("【----开始执行合同文件信息----】");
		try{
			if(inBean!=null){
				String fileType=contractFileService.getFileType(inBean.getDocIdOld());
				inBean.setFileType(fileType);
				ContractFile contractFile = makeContractFileInfo(inBean);
				List<ContractFile> findList = contractFileService.findList(contractFile);
				//新申请表的合同文件名称
				ContractFile contractFileNew = makeContractFileInfoNew(inBean);
				List<ContractFile> findListNew = contractFileService.findList(contractFileNew);
				// 用来判断是不是 文件表是不是有数据
				ContractFile contractFileUpdate = null;
				if(findList!=null&&findList.size()>0){
					contractFileUpdate = findList.get(0);
				}
				
				if(findListNew!=null&&findListNew.size()>0){
					contractFileUpdate = findListNew.get(0);
				}
				if(contractFileUpdate  !=null ){ 
					contractFileUpdate.setSignDocId(inBean.getDocId());
					contractFileUpdate.setModifyTime(new Date());
					try {
						User user = (User) UserUtils.getSession().getAttribute(
								SystemConfigConstant.SESSION_USER_INFO);
						if (user != null) {
							contractFileUpdate.setModifyBy(user.getName());
						}
					} catch (Exception e) {
						contractFileUpdate.setModifyBy("金信推送");
					}
					contractFileService.updateCtrFile(contractFileUpdate);
				}else{
					contractFileService.save(contractFile);
				}
			}
			
			outBean.setRetCode(ReturnConstant.SUCCESS);
			outBean.setRetMsg("【调用成功!】");
			logger.info("【调用成功!】");
		}catch(Exception e){
			outBean.setRetCode(ReturnConstant.FAIL);
			outBean.setRetMsg("【调用失败"+e.getMessage()+"】");
			e.printStackTrace();
			logger.error("出现异常:"+e.getMessage());
		}
		return outBean;
	}

	/*
	 * 封装数据到contractFile
	 */
	private ContractFile makeContractFileInfo(JxReceiveContractFileInBean inBean) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = format.format(new Date());
		String contractNumber = inBean.getContractNumber();//合同编号
		String docId = inBean.getDocId();
		
		logger.info("【合同编号:"+contractNumber+",文档id:"+docId+",时间:"+dateString+"】");
		
		StringBuilder builder = new StringBuilder();
		
		ContractFile contractFile = new ContractFile();
		//文件名
		String name = null;
		//文件编码
		String code = null;
		//文件排序标识
		String flag = null;
		if("1".equals(inBean.getFileType())){
			name = ContractType.CONTRACT_MANAGE_JX.getName();
			code = ContractType.CONTRACT_MANAGE_JX.getCode();
			flag = ContractType.CONTRACT_MANAGE_JX.getFlag();
		}else{
			name = ContractType.CONTRACT_RETURN_MANAGE_JX.getName();
			code = ContractType.CONTRACT_RETURN_MANAGE_JX.getCode();
			flag = ContractType.CONTRACT_RETURN_MANAGE_JX.getFlag();
		}

		contractFile.setFileShowOrder(flag);
		
		contractFile.setSendFlag("0");//推送标识(默认值：0，0：未推送，1：已推送，3：已确认)
		contractFile.setDownloadFlag("0");//下载标识(0：不可下载；1可下载，默认值为0)
		contractFile.setContractCode(contractNumber);//合同编号
		
		builder.append(name).append("_"+dateString+".pdf");
		
		contractFile.setFileName(builder.toString());//文件名
		
		contractFile.setSignDocId(docId);//签约文件id
		
		contractFile.setCreateTime(new Date());//创建时间
		
		
		contractFile.setFileShowOrder(code);
		
		contractFile.setContractFileName(name);//合同文件名  
		try {
			User user = (User) UserUtils.getSession().getAttribute(
					SystemConfigConstant.SESSION_USER_INFO);
			if (user != null) {
				contractFile.setCreateBy(user.getName());// 操作人记录当前登陆系统用户名称
			}
		} catch (Exception e) {
			contractFile.setCreateBy("金信推送");
		}
		return contractFile;
	}
	
	
	
	
	/*
	 * 封装数据到contractFile
	 */
	private ContractFile makeContractFileInfoNew(JxReceiveContractFileInBean inBean) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = format.format(new Date());
		String contractNumber = inBean.getContractNumber();//合同编号
		String docId = inBean.getDocId();
		
		logger.info("【合同编号:"+contractNumber+",文档id:"+docId+",时间:"+dateString+"】");
		
		StringBuilder builder = new StringBuilder();
		
		ContractFile contractFile = new ContractFile();
		//文件名
		String name = null;
		//文件编码
		String code = null;
		//文件排序标识
		String flag = null;
		if("1".equals(inBean.getFileType())){
			name = ContractType.CONTRACT_MANAGE_JX2_ASSURER.getName();
			code = ContractType.CONTRACT_MANAGE_JX2_ASSURER.getCode();
			flag = ContractType.CONTRACT_MANAGE_JX2_ASSURER.getFlag();
		}else{
			name = ContractType.CONTRACT_JX2_RETURN_MANAGE.getName();
			code = ContractType.CONTRACT_JX2_RETURN_MANAGE.getCode();
			flag = ContractType.CONTRACT_JX2_RETURN_MANAGE.getFlag();
		}

		contractFile.setFileShowOrder(flag);
		
		contractFile.setSendFlag("0");//推送标识(默认值：0，0：未推送，1：已推送，3：已确认)
		contractFile.setDownloadFlag("0");//下载标识(0：不可下载；1可下载，默认值为0)
		contractFile.setContractCode(contractNumber);//合同编号
		
		builder.append(name).append("_"+dateString+".pdf");
		
		contractFile.setFileName(builder.toString());//文件名
		
		contractFile.setSignDocId(docId);//签约文件id
		
		contractFile.setCreateTime(new Date());//创建时间
		
		
		contractFile.setFileShowOrder(code);
		
		contractFile.setContractFileName(name);//合同文件名  
		try {
			User user = (User) UserUtils.getSession().getAttribute(
					SystemConfigConstant.SESSION_USER_INFO);
			if (user != null) {
				contractFile.setCreateBy(user.getName());// 操作人记录当前登陆系统用户名称
			}
		} catch (Exception e) {
			contractFile.setCreateBy("金信推送");
		}
		return contractFile;
	}
}
