package com.creditharmony.loan.webservice.ocr.dao;

import java.util.List;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.webservice.ocr.bean.DictionaryInfo;


/**
 * OCR信借操作数据库
 * @author 王俊杰
 * @Create In 2016年3月31日
 */
@LoanBatisDao
public interface OcrDao {
	
	/**
	 * 根据类型编码查询字典表
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param type
	 * @return
	 */
	List<DictionaryInfo> getDictList(String typeCode);
}
