package com.creditharmony.loan.borrow.contract.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.Global;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractTempDao;
import com.creditharmony.loan.borrow.contract.dao.SplitDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractTemp;
import com.creditharmony.loan.borrow.contract.entity.Split;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.transate.web.LoanApplyStatus;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 合同临时Service
 * 
 * @Class Name ContractTempService
 * @create In 2017年02月22日
 * @author 申阿伟
 */
@Service("ContractTempService")
public class ContractTempService extends CoreManager<ContractTempDao, ContractTemp> {

	@Autowired
	private ContractTempDao contractTempDao;

	@Autowired
	private ContractDao contractDao;

	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;

	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;

	@Autowired
	private SplitDao splitDao;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;

	/**
	 * 占比分配-单子拆分
	 * 
	 * @throws Exception
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void ContractSplit(String zcj, String jinxin, String param) throws Exception {
		User user = UserUtils.getUser();
		param = URLDecoder.decode(param, "UTF-8");
		String[] params = param.split(";");
		String zcjversion = Global.getConfig("zcjversion");
		String jxcontractVersion = Global.getConfig("jxcontractVersion");
		BigDecimal zcj1=BigDecimal.valueOf(Double.valueOf(zcj));
		BigDecimal jinxin1=BigDecimal.valueOf(Double.valueOf(jinxin));
		if(!zcj1.equals(BigDecimal.valueOf(Double.valueOf(100))) && !jinxin1.equals(BigDecimal.valueOf(Double.valueOf(100)))){
			for (int i = 0; i < params.length; i++) {
				String[] p = params[i].split(",");
				Contract contract = contractDao.findByLoanCode(p[5]);
				if (contract.getAuditAmount().compareTo(BigDecimal.valueOf(200000)) == 1) {
					contractTempDao.deleteByContractCode(contract.getContractCode() + "-1");
					contractTempDao.deleteByContractCode(contract.getContractCode() + "-2");
					ContractTemp contractTemp = new ContractTemp();
					// 大金融
					ReflectHandle.copy(contract, contractTemp);
					contractTemp.setContractCode(contractTemp.getContractCode() + "-1");
					contractTemp.setChannelFlag(ChannelFlag.ZCJ.getCode());
					contractTemp.setContractVersion(zcjversion);
					contractTemp.setAuditAmount(contractTemp.getAuditAmount()
							.multiply(zcj1.divide(BigDecimal.valueOf(100))));
					contractTemp.preInsert();
					contractTempDao.insertSelective(contractTemp);
					// 金信
					ReflectHandle.copy(contract, contractTemp);
					contractTemp.setContractCode(contractTemp.getContractCode() + "-2");
					contractTemp.setChannelFlag(ChannelFlag.JINXIN.getCode());
					contractTemp.setContractVersion(jxcontractVersion);
					contractTemp.setAuditAmount(contractTemp.getAuditAmount()
							.multiply(jinxin1.divide(BigDecimal.valueOf(100))));
					contractTemp.preInsert();
					contractTempDao.insertSelective(contractTemp);
					// 更新loanInfo表拆分属性
					LoanInfo loaninfo = new LoanInfo();
					loaninfo.setLoanCode(contract.getLoanCode());
					loaninfo.setIssplit(ContractConstant.ISSPLIT_1);
					loaninfo.setZcj(BigDecimal.valueOf(Double.valueOf(zcj)));
					loaninfo.setJinxin(BigDecimal.valueOf(Double.valueOf(jinxin)));
					loaninfo.setLoanFlag(ChannelFlag.LIANHE.getCode());
					applyLoanInfoDao.updateLoanInfo(loaninfo);

					// 插入日志
					LoanStatusHis record = new LoanStatusHis();
					record.setApplyId(contract.getApplyId());
					record.setLoanCode(contract.getLoanCode());
					// 状态
					record.setDictLoanStatus(LoanApplyStatus.RATE_TO_VERIFY.getCode());
					// 操作步骤(回退,放弃,拒绝 等)
					record.setOperateStep("占比分配");
					record.setOperateResult("成功");
					// 备注
					record.setRemark("拆分占比分配（大金融：" + zcj + "%,金信：" + jinxin + "%）");
					// 系统标识
					record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
					// 设置Crud属性值
					record.preInsert();
					// 操作时间
					record.setOperateTime(record.getCreateTime());
					// 操作人记录当前登陆系统用户名称
					record.setOperator(user.getName());
					if (!ObjectHelper.isEmpty(user.getRole())) {
						// 操作人角色
						record.setOperateRoleId(user.getRole().getId());
					}
					if (!ObjectHelper.isEmpty(user.getDepartment())) {
						// 机构编码
						record.setOrgCode(user.getDepartment().getId());
					}
					loanStatusHisDao.insertSelective(record);

					// 更改工作流渠道
					WorkItemView workItem = flowService.loadWorkItemView(contract.getApplyId());
					ContractBusiView ctrView = new ContractBusiView();
					ctrView.setApplyId(contract.getApplyId());
					ctrView.setLoanFlag(ChannelFlag.LIANHE.getName());
					ctrView.setLoanFlagCode(ChannelFlag.LIANHE.getCode());
					ctrView.setContractVersion(jxcontractVersion);
					ctrView.setOperType(YESNO.NO.getCode());
					workItem.setBv(ctrView);
					flowService.saveData(workItem);
				}
			}
		}
		
	}

	/**
	 * 占比分配-单子拆分-自动
	 * 
	 * @throws Exception
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void ContractSplit(String zcj, String jinxin) throws Exception {
		User user = UserUtils.getUser();
		String zcjversion = Global.getConfig("zcjversion");
		String jxcontractVersion = Global.getConfig("jxcontractVersion");
		// 设置占比
		Split split = new Split();
		split.setCreateBy(user.getLoginName());
		split.setCreateTime(new Date());
		split.setZcj(BigDecimal.valueOf(Double.valueOf(zcj)));
		split.setJinxin(BigDecimal.valueOf(Double.valueOf(jinxin)));
		split.setEffective(ContractConstant.EFFECTIVE_1);
		splitDao.updateSplit();
		split.preInsert();
		splitDao.saveSpilt(split);
		if(!split.getZcj().equals(BigDecimal.valueOf(Double.valueOf(100))) && !split.getJinxin().equals(BigDecimal.valueOf(Double.valueOf(100)))){
			// 拆分
			LoanInfo ln = new LoanInfo();
			ln.setDictLoanStatus(LoanApplyStatus.RATE_TO_VERIFY.getCode());
			List<LoanInfo> applyLoanInfoList = applyLoanInfoDao.findStatus(ln);
			for (int i = 0; i < applyLoanInfoList.size(); i++) {
				LoanInfo lns = applyLoanInfoList.get(i);
				Contract contract = contractDao.findByLoanCode(lns.getLoanCode());
				if (contract != null && contract.getAuditAmount().compareTo(BigDecimal.valueOf(200000)) == 1
						&& lns.getFlFlag().equals(ContractConstant.FLFLAG_0)) {
					contractTempDao.deleteByContractCode(contract.getContractCode() + "-1");
					contractTempDao.deleteByContractCode(contract.getContractCode() + "-2");
					ContractTemp contractTemp = new ContractTemp();
					// 大金融
					ReflectHandle.copy(contract, contractTemp);
					contractTemp.setContractCode(contractTemp.getContractCode() + "-1");
					contractTemp.setChannelFlag(ChannelFlag.ZCJ.getCode());
					contractTemp.setContractVersion(zcjversion);
					contractTemp.setAuditAmount(contractTemp.getAuditAmount()
							.multiply(BigDecimal.valueOf(Double.valueOf(zcj)).divide(BigDecimal.valueOf(100))));
					contractTemp.preInsert();
					contractTempDao.insertSelective(contractTemp);
					// 金信
					ReflectHandle.copy(contract, contractTemp);
					contractTemp.setContractCode(contractTemp.getContractCode() + "-2");
					contractTemp.setChannelFlag(ChannelFlag.JINXIN.getCode());
					contractTemp.setContractVersion(jxcontractVersion);
					contractTemp.setAuditAmount(contractTemp.getAuditAmount()
							.multiply(BigDecimal.valueOf(Double.valueOf(jinxin)).divide(BigDecimal.valueOf(100))));
					contractTemp.preInsert();
					contractTempDao.insertSelective(contractTemp);
					// 更新loanInfo表拆分属性
					LoanInfo loaninfo = new LoanInfo();
					loaninfo.setLoanCode(contract.getLoanCode());
					loaninfo.setIssplit(ContractConstant.ISSPLIT_1);
					loaninfo.setZcj(BigDecimal.valueOf(Double.valueOf(zcj)));
					loaninfo.setJinxin(BigDecimal.valueOf(Double.valueOf(jinxin)));
					loaninfo.setLoanFlag(ChannelFlag.LIANHE.getCode());
					applyLoanInfoDao.updateLoanInfo(loaninfo);
					
					// 插入日志
					LoanStatusHis record = new LoanStatusHis();
					record.setApplyId(contract.getApplyId());
					record.setLoanCode(contract.getLoanCode());
					// 状态
					record.setDictLoanStatus(LoanApplyStatus.RATE_TO_VERIFY.getCode());
					// 操作步骤(回退,放弃,拒绝 等)
					record.setOperateStep("占比分配");
					record.setOperateResult("成功");
					// 备注
					record.setRemark("拆分占比分配（大金融：" + zcj + "%,金信：" + jinxin + "%）");
					// 系统标识
					record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
					// 设置Crud属性值
					record.preInsert();
					// 操作时间
					record.setOperateTime(record.getCreateTime());
					// 操作人记录当前登陆系统用户名称
					record.setOperator(user.getName());
					if (!ObjectHelper.isEmpty(user.getRole())) {
						// 操作人角色
						record.setOperateRoleId(user.getRole().getId());
					}
					if (!ObjectHelper.isEmpty(user.getDepartment())) {
						// 机构编码
						record.setOrgCode(user.getDepartment().getId());
					}
					loanStatusHisDao.insertSelective(record);
					
					// 更改工作流渠道
					WorkItemView workItem = flowService.loadWorkItemView(contract.getApplyId());
					ContractBusiView ctrView = new ContractBusiView();
					ctrView.setApplyId(contract.getApplyId());
					ctrView.setLoanFlag(ChannelFlag.LIANHE.getName());
					ctrView.setLoanFlagCode(ChannelFlag.LIANHE.getCode());
					ctrView.setOperType(YESNO.NO.getCode());
					ctrView.setContractVersion(jxcontractVersion);
					workItem.setBv(ctrView);
					flowService.saveData(workItem);
				}
			}
		}
		
	}

}
