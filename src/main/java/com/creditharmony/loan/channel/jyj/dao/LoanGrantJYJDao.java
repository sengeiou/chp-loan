/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.daoLoanGrantDao.java
 * @Create By 朱静越
 * @Create In 2015年11月28日 下午6:19:34
 */
/**
 * @Class Name LoanGrantDao
 * @author 朱静越
 * @Create In 2015年11月28日
 */
package com.creditharmony.loan.channel.jyj.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.channel.goldcredit.view.GCGrantDoneView;
import com.creditharmony.loan.channel.jyj.entity.LoanGrantDone;
@LoanBatisDao
public interface LoanGrantJYJDao extends CrudDao<LoanGrantDone>{
	
	/**
     * 查询办理页面中要显示的字段,根据传送过来的ApplyID查询，业务表中的数据，返回值为实体
     * 2015年12月11日
     * By 张灏
     * @param applyId 流程Id 
     * @return
     */
    public LoanGrantEx queryReconsiderGrantDeal(String applyId);
	
	/**
	 * 根据参数查询要发送到批处理的list
	 * 2016年2月24日
	 * By 朱静越
	 * @param loanGrantEx 
	 * @return 要发送到批处理的list
	 */
	public List<DeductReq> selSendList(LoanGrantEx loanGrantEx);
	
	/**
	 * 根据applyId查询loanCode,用来插入历史的时候传送loanCode
	 * 2016年2月23日
	 * By 朱静越
	 * @param applyId 
	 * @return loanCode
	 */
	public String selLoanCode(String applyId);
	
	/**
	 * 根据借款编号查询标识
	 * 2016年5月18日
	 * By 朱静越
	 * @param loanCode
	 * @return
	 */
	public String selLoanFlag(String loanCode);
	
	/**
	 * 
	 * 2015年12月31日
	 * By 朱静越
	 * @param applyId
	 * @return
	 */
	public LoanGrantEx queryDisCardDeal(String loanCode);
	
	/**
	 * 根据传送过来的id更改业务表中标识字段
	 * 2015年12月11日
	 * By 朱静越
	 * @param LoanInfo 借款主表
	 * @return
	 */
	public int updateFlag(LoanInfo loanInfo);
	
	/**
	 * 根据传送过来的id更改业务表中的借款状态
	 * 2015年12月11日
	 * By 朱静越
	 * @param LoanInfo 借款主表
	 * @return
	 */
	public int updateStatus(LoanInfo loanInfo);
	
	/**
	 * 根据传送过来的借款信息更改业务表中的借款状态
	 * 2015年02月22日
	 * By 王彬彬
	 * @param LoanInfo 借款主表
	 * @return 更新值
	 */
	public int updateStatusByLoanCode(LoanInfo loanInfo);
	
	/**
	 * 根据合同编号更新还款主表中的有效标识
	 * 2016年1月18日
	 * By 朱静越
	 * @param loanGrant 放款记录标识
	 * @return  更新结果
	 */
	public int updFlag(LoanGrant loanGrant);
	
	/**
	 * 更新放款记录表
	 * 2015年12月11日
	 * By 朱静越
	 * @param loanGrant 放款实体
	 * @return
	 */
	public int updateLoanGrant(LoanGrant loanGrant);
	
	/**
	 * 查询已办
	 * 2015年12月11日
	 * By 朱静越
	 * @param loanGrantEx 封装查询检索条件的实体类
	 * @return
	 */
	public List<LoanGrantEx> findGrantDone(PageBounds pageBounds,Map<String, Object> map);
	
	/**
	 * 金信
	 * 2016年4月28日
	 * By 朱静越
	 * @param pageBounds
	 * @param params
	 * @return
	 */
	public List<GCGrantDoneView> findGrantDoneList(PageBounds pageBounds,Map<String ,Object> params);
	
	/**
	 * 放款已办导出时使用
	 * 2016年2月23日
	 * By 朱静越
	 * @param loanGrantEx
	 * @return 放款已办导出
	 */
	public List<LoanGrantEx> findGrantDone(LoanGrantEx loanGrantEx);
	
	/**
	 * 金信
	 * 2016年4月28日
	 * By 朱静越
	 * @param params
	 * @return
	 */
	public List<GCGrantDoneView> findGrantDoneList(Map<String ,Object> params);
	
	/**
	 * 根据合同编号查询applyId，用于放款已办查看历史
	 * 2016年1月19日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return applyId
	 */
	public String selApplyId(String contractCode);
	
	/**
	 * 对放款表进行插入
	 * 2015年12月30日
	 * By 朱静越
	 * @param loanGrant
	 * @return
	 */
	public int insertGrant(LoanGrant loanGrant);
	
	/**
	 *删除放款信息 
	 *2015年12月31日
	 *By zhanghao  
	 *@param loanGrant
	 *@return 
	 */
	public void deleteGrant(LoanGrant loanGrant);
	
	/**
	 * 查询LoanGrant
	 * 2016年1月18日
	 * By 朱静越
	 * @param loanGrant
	 * @return
	 */
	public LoanGrantEx findGrant(LoanGrant loanGrant);
	
	/**
	 * 根据applyId查询初始签约平台
	 * 2016年1月18日
	 * By 朱静越
	 * @param loanCode 借款编号
	 * @return 签约平台
	 */
	public String selPlat(String loanCode);
	
	/**
	 * 查找所有的产品名称
	 * 2016年1月25日
	 * By 朱静越
	 * @return
	 */
	public List<LoanGrantEx> findProduct();
	
	/**
	 * 查询所有的放款批次
	 * 2016年3月22日
	 * By 朱静越
	 * @return
	 */
	public List<LoanGrant> selGrantPch();
	
	// 以下是    车借新增
	
	/**
	 *  查询要放款的数据  DeductReq  车借
	 * @author 张振强
	 * @Create In 2016年2月17日
	 * @param paramMap
	 * @return DeductReq 要放款的数据 
	 */
	public DeductReq queryDeductReq(HashMap<Object, Object> hashMap);
	
	/**
	 * 分配卡号页面信息查询  车借
	 * 2016年2月17日
	 * By 张振强
	 * @param applyId
	 * @return
	 */
	public LoanGrantEx queryDisDeal(String applyId);
	/**
	 * 更新放款记录表  车借
	 * 2016年2月17日
	 * By 张振强
	 * @param loanGrant 放款实体
	 * @return
	 */
	public int updateCarStatus(CarLoanInfo loanInfo);
	
	/**
	 * 根据合同编号查询applyId  车借
	 * 2016年2月16日
	 * By 张振强
	 * @param contractCode 合同编号
	 * @return applyId
	 */
	public String selectCarApplyId(String contractCode);
	
	/**
	 * 根据用户code查询用户id
	 * 2016年2月24日
	 * By 朱静越
	 * @param userCode 用户code
	 * @return 用户id
	 */
	public String selUserName(String userCode);
	
	/**
	 *查找最大的批次号  金信
	 *2016年2月29日
	 *By zhanghao
	 *@param param letter 信借 urgentFlag 加急
	 *@return List<LoanGrantEx> 
	 * 
	 */
	public List<LoanGrantEx> findMaxJINXINGrantPch(Map<String,String> param);
	
	/**
	 * 根据applyId查询冻结的单子的合同编号
	 * 2016年3月24日
	 * By 朱静越
	 * @param applyId
	 * @return
	 */
	public String selFrozenContract(String contractCode);
	
	//委托划扣列表数据
	public List<LoanGrantEx> wthkList(PageBounds pageBounds,LoanGrantEx loanGrantEx);
	
	//获取委托划扣详细数据
	public List<Map<String, String>> getLoanCode(LoanGrantEx loanGrantEx);
	
	//获取划扣号
	public List<Map<String,String>> findStartDate(@Param(value = "loanCode")String[] loanCode);

	public PageList<LoanGrantDone> findGrantDoneJYJ(PageBounds pageBounds,
			LoanGrantDone loanGrantEx);

	public List<LoanGrantDone> findGrantDoneJYJExcel(LoanGrantDone loanGrantEx);
	
}