package com.creditharmony.loan.common.dao;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;

/**
 * 
 * 
 * @Class Name ChangeDao
 * @author WJJ
 * @Create In 2016年3月17日
 */
@LoanBatisDao
public interface ChangeDao {

	public int updateAccountChange(@Param(value = "docId") String docId,@Param(value = "status") String status);
	public int updateArchivesStatus(@Param(value = "loanCode") String loanCode,@Param(value = "fileType") String fileType);
}
