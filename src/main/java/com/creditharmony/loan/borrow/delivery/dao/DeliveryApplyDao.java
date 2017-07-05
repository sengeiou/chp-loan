package com.creditharmony.loan.borrow.delivery.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.borrow.delivery.entity.ex.UserView;
/**
 * 交割-发起交割申请Dao
 * @Class Name DeliveryApplyDao
 * @author lirui
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface DeliveryApplyDao extends CrudDao<DeliveryViewEx> {
	/**
	 * 获得待交割申请列表
	 * 2015年12月14日
	 * By lirui
	 * @param params 搜索条件
	 * @param pageBounds 分页信息
	 * @return 带交割申请列表
	 */
	public List<DeliveryViewEx> deliveryApplyList(PageBounds pageBounds,DeliveryParamsEx params);
	
	
	/**
	 * 根据借款编码获得交割申请信息
	 * 2015年12月14日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 交割申请信息列表
	 */
	public List<DeliveryViewEx> applyInfoByloanCode(String loanCode);
	
	/**
	 * 获得所有门店信息
	 * 2015年12月9日
	 * By lirui
	 * @params orgKey 门店枚举key值
	 * @return 门店组成的集合
	 */
	public List<OrgView> orgs(String orgKey);
	
	/**
	 * 根据机构编码查询所有团队经理
	 * 2015年12月9日
	 * By lirui
	 * @param orgCode 门店ID
	 * @params teamManagerKey 团队经理key值
	 * @return 团队经理集合
	 */
	public List<UserView> teamManager(Map<String,String> map);
	
	/**
	 * 查询团队经理下所有客户经理
	 * 2015年12月9日
	 * By lirui
	 * @param userCode 团队经理工号
	 * @params managerKey 客户经理key值
	 * @return 客户经理集合
	 */
	public List<UserView> manager(Map<String,String> map);
	
	/**
	 * 查询机构下所有客服人员
	 * 2015年12月9日
	 * By lirui
	 * @param orgCode 门店ID
	 * @param serviceKey 客服key
	 * @return 客服人员集合
	 */
	public List<UserView> service(Map<String,String> map);
	
	/**
	 * 所有外访人员
	 * 2015年12月9日
	 * By lirui
	 * @param orgCode 门店ID
	 * @param outBoundKey 外访key
	 * @return 外访人员集合
	 */
	public List<UserView> outBound(Map<String,String> map);
	
	/**
	 * 办理交割申请
	 * 2015年12月14日
	 * By lirui
	 * @param dv 前台传递参数
	 */
	public void insertDelivery(DeliveryViewEx dv);
}
