package com.creditharmony.loan.borrow.applyinfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.RemarkType;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanRemarkDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanRemark;
/**
 * 借款备注信息
 * @Class Name LoanRemarkService
 * @author lirui
 * @Create In 2016年2月25日
 */
@Service
@Transactional(readOnly=true,value = "loanTransactionManager")
public class LoanRemarkService extends CoreManager<LoanRemarkDao, LoanRemark>{
	/**
     * 新增备注信息 
     * @author lirui 
     * @Create In 2016年2月17日
     * @param record
     * @return none 
     *
     */
	@Transactional(readOnly=false,value = "loanTransactionManager")
    public void insertRemark(LoanRemark record) {
		record.setDictRemarkType(RemarkType.LoanFlag.getCode());
    	record.preInsert();
		dao.insertRemark(record);
    }
    
    /**
     * 根据ID选择更新备注信息 
     * @author lirui
     * @Create In 2016年2月17日 
     * @param record 
     * @return  none  
     */
	@Transactional(readOnly=false,value = "loanTransactionManager")
    public void updateByIdSelective(LoanRemark record) {
		record.setDictRemarkType(RemarkType.LoanFlag.getCode());
		record.preUpdate();
		if (StringUtils.isNotEmpty(record.getId())) {
			dao.updateByIdSelective(record);			
		}
    }
    
    /**
     * 跟据借款编号查询备注信息 
     * @author lirui
     * @Create In 2016年2月17日 
     * @param param
     * @return  List<LoanRemark> 
     */
	@Transactional(readOnly=true,value = "loanTransactionManager")
    public List<LoanRemark> findByLoanCode(Map<String,Object> param) {
		param.put("dictRemarkType", RemarkType.LoanFlag.getCode());
    	return dao.findByLoanCode(param);
    }
}
