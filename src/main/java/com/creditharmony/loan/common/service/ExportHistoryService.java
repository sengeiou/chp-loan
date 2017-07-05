package com.creditharmony.loan.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.loan.common.dao.ExportHistoryDao;
import com.creditharmony.loan.common.entity.ExportHistory;
/**
 * 导出功能历史记录
 * @author huowenlong
 *
 */
@Service
public class ExportHistoryService {

	@Autowired
	private ExportHistoryDao exportHisDao;
	
	public int insertExportHistory(ExportHistory his){
		return exportHisDao.insert(his);
	}
}
