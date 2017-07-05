package com.creditharmony.loan.yunwei.operate.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.yunwei.operate.dao.OperateDataDao;
import com.creditharmony.loan.yunwei.operate.entity.OperateObj;
import com.github.abel533.sql.SqlMapper;
import com.sun.star.uno.Exception;

@Service("operateDataService")
public class OperateDataService extends CoreManager<OperateDataDao, OperateObj>{

	@Autowired
	private OperateDataDao operateDataDao;
	@Autowired
	private SqlMapper sqlMapper;
	
	@Transactional
	public OperateObj updateDate(OperateObj operateObj) throws Exception {
		// 查询ID
		String systemLogId = operateDataDao.selectLoanSystemLog();
		int result = 0;
		// 比对ID是否一致
		if(StringUtils.isNotEmpty(systemLogId) 
			&& systemLogId.toUpperCase().equals(operateObj.getSessionId().toUpperCase())){
			if(operateObj.getUpdateSql().startsWith("UPDATE")) {
				result = sqlMapper.update(operateObj.getUpdateSql());
			} else if(operateObj.getUpdateSql().startsWith("DELETE")){
				result = sqlMapper.delete(operateObj.getUpdateSql());
			} else if(operateObj.getUpdateSql().startsWith("INSERT")){
				result = sqlMapper.insert(operateObj.getUpdateSql());
			}
			// 
			if(result > 0){
				Map<String, Object> loanSystemLog = new HashMap<String, Object>();
				loanSystemLog.put("JK_LOAN_SYSTEM_LOG_ID", systemLogId);
				loanSystemLog.put("MESSAGE", operateObj.getUpdateSql());
				loanSystemLog.put("OPERATOR_NAME", UserUtils.getUser().getName());
				loanSystemLog.put("OPERATOR_CODE", UserUtils.getUser().getLoginName());
				operateDataDao.updateLoanSystemLog(loanSystemLog);
				String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
				operateDataDao.insertLoanSystemLog(uuid);
				operateObj.setUpdateDataResult("操作成功，更新" + result + "条数据！");
			}
		} else {
			// ID不一致，不能进行更新操作。
			throw new Exception("sessionIdError，请重新查询标识。");
		}
		return operateObj;
	}
}
