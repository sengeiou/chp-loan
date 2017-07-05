package com.creditharmony.loan.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.loan.common.dao.LoanPrdDao;
import com.creditharmony.loan.common.entity.LoanPrdEntity;

/**
 * 汇金产品管理-Service
 * 
 * @Class Name LoanPrdService
 * @author 李静辉
 * @Create In 2015年12月9日
 */
@Service
@Transactional(value = "loanTransactionManager", readOnly = true)
public class LoanPrdService {

	private static LoanPrdDao dao = SpringContextHolder.getBean(LoanPrdDao.class);
	
	/**
	 * 检索产品的键值对
	 * 
	 * @return List<LoanPrdEntity>
	 */
	public static List<LoanPrdEntity> selPrdKV(String productType) {
		// 检索产品
		LoanPrdEntity key = new LoanPrdEntity();
		key.setProductStatus(productType);
		List<LoanPrdEntity> ls = dao.selPrdKV(productType);
		
		return ls;
	}
	
	/**
	 * 检索产品的键值对
	 * 
	 * @return Map<String, String>
	 */
	public static Map<String, String> getPrdMap(boolean emptyRowFlag,String productType) {
		// 检索产品
		List<LoanPrdEntity> ls = selPrdKV(productType);
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if (emptyRowFlag) {
			map.put("", "请选择");
		}
		for (int i = 0; i < ls.size(); i++) {
			map.put(ls.get(i).getProductCode(), ls.get(i).getProductName());
		}
		
		return map;
	}
	
	/**
	 * 根据产品ID检索产品名称
	 * 
	 * @return String
	 */
	public static String getPrdNamebyId(String id) {
		String prdName = "";
		
		// 检索产品
		if (StringUtils.isNotBlank(id)) {
			prdName = dao.selPrdNamebyId(id);
		}
		
		return prdName;
	}
	
	/**
	 * 
	 * 2016年2月3日
	 * By 李静辉
	 * @param productType   产品类型productType
	 * @return
	 */
	public static List<LoanPrdEntity> getPrd(String productType){
		return selPrdKV(productType);
	}
	
	
	/**
	 * 
	 * 2016年2月3日
	 * By 李静辉
	 * @param productType   产品类型productType
	 * @return
	 */
	public static LoanPrdEntity getPrdByTypeAndCode(String productType,String productCode){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("productType", productType);
		map.put("productCode", productCode);
		return dao.getPrdByTypeAndCode(map);
	}
	
	/**
	 * 
	 * 2016年2月3日
	 * By 李静辉
	 * @param productType   产品类型productType
	 * @return
	 */
	public static String getPrdLabelByTypeAndCode(String productType,String productCode){
		if(StringUtils.isEmpty(productCode) || StringUtils.isEmpty(productType)) return "";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("productType", productType);
		map.put("productCode", productCode);
		LoanPrdEntity l = dao.getPrdByTypeAndCode(map);
		return null == l ?"" :l.getProductName();
	}
	
	/**
	 * 根据 产品类型 获取 全部产品期限(按大小升序排序)
	 * 2016年3月24日
	 * By 申诗阔
	 * @param productType
	 * @return
	 */
	public static Map<String, String> getPrdMonthsByType(String productType) {
		// 检索产品
		List<LoanPrdEntity> ls = selPrdKV(productType);
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		Set<Integer> set = new HashSet<Integer>();
		for (LoanPrdEntity loanPrdEntity : ls) {
			String[] itemArray = loanPrdEntity.getProductMonths().split(",");
			for (String itemVal : itemArray) {
				set.add(Integer.parseInt(itemVal));
			}
		}
		List<Integer> mapList = new ArrayList<Integer>();  
		mapList.addAll(set);
		Collections.sort(mapList);
		
		for (Integer item : mapList) {
			map.put(item + "", item + "");
		}
		
		return map;
	}
}
