package com.creditharmony.loan.common.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.ExportHistory;

/**
 * 导出功能历史记录
 * @author huowenlong
 *
 */
@LoanBatisDao
public interface ExportHistoryDao extends CrudDao<ExportHistory>{

}
