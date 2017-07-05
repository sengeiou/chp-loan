package com.creditharmony.loan.dict.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.dict.dao.DictInfoDao;
import com.creditharmony.loan.dict.entity.DictInfo;

/**
 * 字典服务
 * @Class Name DictInfoManager
 * @author 陈伟东
 * @Create In 2015年12月28日
 */
@Component
@Service
@Transactional(value = "loanTransactionManager",readOnly = false)
public class DictInfoManager extends CoreManager<DictInfoDao, DictInfo> {
	
	/**
	 * 根据ID获取字典值 
	 * @param id
	 * @return
	 */
	public DictInfo getDictInfo(String id){
		return dao.get(id);
	}
	
	/**
	 * 根据type获得字典对象
	 * 2016年9月29日
	 * By 朱静越
	 * @param dictInfo
	 * @return
	 */
	public List<DictInfo> getByType (DictInfo dictInfo){
		return dao.getByType(dictInfo);
	}
	
	/**
	 * 保存字典值 
	 * @param id
	 * @return
	 */
	public void saveDictInfo(DictInfo dictInfo){
		dao.insert(dictInfo);
	}

	/**
	 * 修改字典值 
	 * @param id
	 * @return
	 */
	public void update(DictInfo dictInfo){
		dao.update(dictInfo);
	}
}
