package com.creditharmony.loan.borrow.applyinfo.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ModifyInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.ModifyInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ModifyType;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;

/**
 * 修改信息详细记录表
 * @Class Name ModifyInfoService
 * @author lirui
 * @Create In 2016年2月18日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class ModifyInfoService extends CoreManager<ModifyInfoDao, ModifyInfo> {
	
	/**
	 * 添加修改记录
	 * 2016年2月18日
	 * By lirui
	 * @param modifyInfo 修改记录信息
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void addModifyInfo(LaunchView launchView){
		for (int i = 0; i < launchView.getLoanCoborrower().size(); i++) {
			ModifyInfo modifyInfo = launchView.getLoanCoborrower().get(i).getModifyInfo();
			StringBuffer primitiveColumn = new StringBuffer(); 
			StringBuffer primitiveValue = new StringBuffer();
			StringBuffer modifyColumn = new StringBuffer();
			StringBuffer modifyValue = new StringBuffer();			
			LoanCoborrower cobo = launchView.getLoanCoborrower().get(i);
			if (!ObjectHelper.isEmpty(cobo.getModifyInfoEx())) {
				// 如果共借人姓名改变,插入修改记录
				if (!cobo.getModifyInfoEx().getCoboName().equals(cobo.getCoboName())) {
					// 原始字段
					primitiveColumn.append("姓名");								
					// 原始值
					primitiveValue.append(cobo.getModifyInfoEx().getCoboName());
					// 修改后字段
					modifyColumn.append("姓名");
					// 修改后值
					modifyValue.append(cobo.getCoboName());
				}
				// 如果共借人身份证号改变,插入记录
				if (!cobo.getModifyInfoEx().getCoboCertNum().equals(cobo.getCoboCertNum())) {
					if (!cobo.getModifyInfoEx().getCoboName().equals(cobo.getCoboName())) {
						primitiveColumn.append(",");
						primitiveValue.append(",");
						modifyColumn.append(",");
						modifyValue.append(",");
					}
					// 原始字段
					primitiveColumn.append("身份证号");
					// 原始值
					primitiveValue.append(cobo.getModifyInfoEx().getCoboCertNum());
					// 修改后字段
					modifyColumn.append("身份证号");
					// 修改后值
					modifyValue.append(cobo.getCoboCertNum());
				}
				if (cobo.getModifyInfoEx().getCoboName().equals(cobo.getCoboName()) && cobo.getModifyInfoEx().getCoboCertNum().equals(cobo.getCoboCertNum())) {
					primitiveColumn.append("");
					primitiveValue.append("");
					modifyColumn.append("");
					modifyValue.append("");
				}				
				modifyInfo.setPrimitiveColumn(primitiveColumn.toString());
				modifyInfo.setPrimitiveValue(primitiveValue.toString());
				modifyInfo.setModifyColumn(modifyColumn.toString());
				modifyInfo.setModifyValue(modifyValue.toString());
				// 借款状态
				modifyInfo.setModifyType(ModifyType.LOAN_COBO.getCode());
				modifyInfo.preInsert();
				dao.insert(modifyInfo);
			}
		}
		
	}
}
