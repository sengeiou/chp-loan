package com.creditharmony.loan.webservice.infodisclosure.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.webservice.infodisclosure.entity.InfoDisclosure;

/**
 * 信息披露接口
 * @Class Name InfoDisclosureDao
 * @author 高原
 * @Create In 2016年7月13日
 */
@LoanBatisDao
public interface InfoDisclosureDao extends CrudDao<InfoDisclosureDao> {

	void saveRiskLevel(Map<String, Object> map);
	
	List<InfoDisclosure> getBatchDetailInfo(@Param("contractCodeList") List<String> contractCodeList);
	
}
