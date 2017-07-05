package com.creditharmony.loan.borrow.creditor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.claim.dto.SyncClaim;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.creditor.view.CreditorModel;
import com.creditharmony.loan.borrow.creditor.view.CreditorSearch;

/**
 * 债权录入DAO
 * @Class Name CreditorDao
 * @author WJJ
 * @Create In 2016年3月11日
 */
@LoanBatisDao
public interface CreditorDao {
	
	/**
	 * 债权录入分页数据
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	public List<CreditorModel> getListByParam(PageBounds pageBounds,CreditorSearch params);

	/**
	 * 债权录入保存
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	public void save(CreditorModel params);
	/**
	 * 获取客户名称
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	public List<Map<String,Object>> getName(@Param(value = "cerNum") String cerNum);
	/**
	 * 获取分类
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	public List<Map<String,Object>> getType();
	/**
	 * 获取职业信息
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	public List<Map<String,Object>> getOccupation(@Param(value = "type") String type,@Param(value = "value") String value);
	/**
	 * 获取债权录入信息
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	public List<SyncClaim> getCreditor(@Param(value = "id") String id);
}
