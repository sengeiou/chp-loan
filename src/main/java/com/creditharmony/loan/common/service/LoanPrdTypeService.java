package com.creditharmony.loan.common.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.loan.common.dao.LoanPrdTypeDao;
import com.creditharmony.loan.common.entity.LoanPrdTypeEntity;

/**
 * 汇金产品类型管理-Service
 * 
 * @Class Name LoanPrdTypeService
 * @author 李静辉
 * @Create In 2015年12月9日
 */
@Service
@Transactional(value = "loanTransactionManager", readOnly = true)
public class LoanPrdTypeService {

	private static LoanPrdTypeDao dao = SpringContextHolder.getBean(LoanPrdTypeDao.class);

	/**
	 * 检索产品类型的键值对
	 * 
	 * @return List<LoanPrdTypeEntity>
	 */
	public static List<LoanPrdTypeEntity> selPrdTypeKV(String productTypeStatus) {
		// 检索产品类型
		List<LoanPrdTypeEntity> ls = dao.selPrdTypeKV(productTypeStatus);
		
		return ls;
	}
	
	/**
	 * 检索产品类型的键值对
	 * 
	 * @return Map<String, String>
	 */
	public static Map<String, String> getPrdTypeMap(boolean emptyRowFlag, String productTypeStatus) {
		// 检索产品类型
		List<LoanPrdTypeEntity> ls = selPrdTypeKV(productTypeStatus);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String> ();
		if (emptyRowFlag) {
			map.put("", "请选择");
		}
		for (int i = 0; i < ls.size(); i++) {
			map.put(ls.get(i).getProductTypeCode(), ls.get(i).getProductTypeName());
		}
		
		return map;
	}
	
	/**
	 * 根据产品类型ID检索产品类型名称
	 * 
	 * @return String
	 */
	public static String getPrdTypeNamebyId(String productTypeCode) {
		String prdTypeName = "";
		
		// 检索产品类型
		if (StringUtils.isNotBlank(productTypeCode)) {
			prdTypeName = dao.selPrdTypeNamebyId(productTypeCode);
		}
		
		return prdTypeName;
	}
}
