package com.creditharmony.loan.borrow.secretvisit.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.secretvisit.entity.ex.SecretInfoEx;
import com.creditharmony.loan.borrow.secretvisit.entity.ex.SecretVisitEx;

/**
 * 暗访信息用Dao
 * @Class Name SecretVisitDao
 * @author 王彬彬
 * @Create In 2015年12月26日
 */
@LoanBatisDao
public interface SecretVisitDao extends CrudDao<SecretVisitEx> {

	/**
	 * 查询暗访信息表
	 * 2015年12月26日
	 * By 王彬彬
	 * @param params 查询条件
	 * @param pageBounds 分页信息
	 * @return 暗访信息集合
	 */
	public List<SecretVisitEx> findSecretList(Map<String, Object> params,
			PageBounds pageBounds);
	
	/**
	 * 更新暗访信息
	 * 2015年12月26日
	 * By 王彬彬
	 * @param secretinfo
	 * @return 受影响行数
	 */
	public int updateSecretInfo(SecretVisitEx secretinfo);
	
	/**
	 * 根据合同编号查询期供列表
	 * 2015年12月30日
	 * By lirui
	 * @param pageBounds 分页信息
	 * @param contractCode 合同编号
	 * @return 期供列表
	 */
	public List<SecretInfoEx> supplyInfo(PageBounds pageBounds,String contractCode);
	
	/**
	 * 根据合同编号查询暗访
	 * 2015年12月30日
	 * By lirui
	 * @param contractCode 合同编号
	 * @return 暗访列表(对象)
	 */
	public List<SecretInfoEx> secretInfo(String contractCode);
	 
	/**
	 * 根据借款编码改变暗访标识
	 * 2015年12月31日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return none
	 */
	public void updateSecret(String loanCode);
}
