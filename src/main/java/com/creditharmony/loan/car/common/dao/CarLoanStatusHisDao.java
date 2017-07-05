package com.creditharmony.loan.car.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carBankInfo.view.CarCustomerBankInfoView;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.ex.CarLoanGrantHaveEx;
import com.creditharmony.loan.car.common.entity.ex.CarLoanStatusHisEx;
@LoanBatisDao
public interface CarLoanStatusHisDao extends CrudDao<CarLoanStatusHis> {

    CarLoanStatusHis selectByLoanCode(String loanCode);
    
    /**
     * 分页查询操作历史数据列表
     * @param loanStatusHis 查询条件，applyId及分页信息
     * @return
     */
    PageList<CarLoanStatusHis> findPage(CarLoanStatusHis loanStatusHis);
    
    /**
     * 分页查询2.0操作历史数据列表
     * @param loanStatusHis 查询条件，applyId及分页信息
     * @return
     */
    PageList<CarLoanStatusHis>  findOldPage(CarLoanStatusHis loanStatusHis);
   
    
    /**
     * 获取车借已办列表
     * 2016年2月26日
     * By 陈伟东
     * @param carLoanStatusHisEx 检索条件
     * @param pageBounds
     * @return
     */
    public List<CarLoanStatusHisEx> findDoneList(Map<String,Object> params,PageBounds pageBounds);
    
    public List<CarLoanGrantHaveEx> findDoneListForXls(Map<String,Object> params);
    /**
	 * 放款已办导出时使用
	 * 2016年3月2日
	 * By	安子帅
	 * @param loanGrantEx
	 * @return 放款已办导出
	 */
	public List<CarLoanGrantHaveEx> findCarGrantDone(CarLoanGrantHaveEx carLoanGrantHaveEx);
	
	/**
	 * 导出放款已办excel
	 * 2016年3月2日
	 * By 安子帅
	 * @param loanGrantEx 查询实体
	 * @return 放款已办实体
	 */
	public CarLoanGrantHaveEx daoCarGrantDone(CarLoanGrantHaveEx carLoanGrantHaveEx);
	
	/**
     * 获取车借展期已办列表
     * 2016年3月11日
     * By 申诗阔
     * @param carExtendLoanStatusHisEx 检索条件
     * @param pageBounds
     * @return
     */
    public List<CarLoanStatusHisEx> findExtendDoneList(Map<String,Object> params,PageBounds pageBounds);
    /**
     * 获取车借展期合同已办列表
     * 2016年3月11日
     * By 申诗阔
     * @param carExtendLoanStatusHisEx 检索条件
     * @param pageBounds
     * @return
     */
    public List<CarLoanStatusHisEx> findExtendContractDoneList(Map<String,Object> params,PageBounds pageBounds);
    
    
    public List<CarLoanStatusHisEx> findDoneList(Map<String,Object> params);
    
    /**
     * 查询车借数据列表-包含2.0迁移数据
     * 2016年4月22日
     * By JiangLi
     * @param carLoanStatusHisEx 检索条件
     * @param pageBounds
     * @return
     */
    public List<CarLoanStatusHisEx> findLoanDataList(Map<String,Object> params,PageBounds pageBounds);
    
    /**
     * 通过loanCode校验待确认签署状态下，这条记录是否为合同审核退回，若是，则返回这条记录，否则，返回null
     * 2016年5月6日
     * By 申诗阔
     * @param loanCode
     * @return
     */
    public CarLoanStatusHis checkIsContractCheckBackSign(String loanCode);
    /**
     * 2016年5月12
     * By 陈伟丽
     * @param filter
     * @param pageBounds
     * @return
     */
	PageList<CarLoanStatusHisEx> findCheckDoneList(Map<String, Object> filter,
			PageBounds pageBounds);
	/**
	 * 
	 * 车借申请列表
	 * 申请电子协议
	 * 显示客户基本信息
	 */
	public List<CarLoanStatusHisEx> getCustomerMsg(CarLoanStatusHisEx ex);
	/**
	 * 车借申请列表
	 * 申请电子协议
	 * 修改协议状态
	 */
	public void updateContractArgType(CarLoanStatusHisEx ex);
	
	/** 
	 * 电子协议申请列表
	 * 发送/拒绝
	 * @param ex
	 */
	public void updateSendOrReturn(CarLoanStatusHisEx ex);
	
	/** 
	 * 电子协议申请列表
	 * @param pageBounds
	 * @param info
	 * @return
	 */
	public List<CarLoanStatusHisEx> getCustomerMsg(PageBounds pageBounds,CarLoanStatusHisEx info);

	/** 
	 * 插入车借电子协议历史数据
	 * @param ex
	 */
	public void insertAgrLog(CarLoanStatusHisEx ex);
	
	/** 
	 * 查询车借电子协议历史数据
	 */
	public List<CarLoanStatusHisEx> selectActAgrLogList(String contractCode);
	
	/** 
	 * 获取合同文件
	 * @param ex
	 * @return
	 */
	public List<CarLoanStatusHisEx> getFileNameList(CarLoanStatusHisEx ex);
	
	/** 
	 * 获取邮件发送模板
	 * @return
	 */
	public Map<String, String> getEmailTemplate(String templateType);
	
	/**
	 * 复审拒绝、终审拒绝    	  90天内不能再申请
	 * @param loanCode
	 * @return
	 */
	public List<CarLoanStatusHis> getCarLoanStatusHis90(String loanCode);
	
	/**
	 * 初审拒绝、签约上传门店拒绝     7天内不能申请
	 * @param loanCode
	 * @return
	 */
	public List<CarLoanStatusHis> getCarLoanStatusHis7(String loanCode);
	/**
	 * 判断是否可以进行90之内的申请
	 * @param loanCode
	 * @return
	 */
	public int checkCarLoanStatusHis90(String id);
	/**
	 * 判断是否可以进行7之内的申请
	 * @param loanCode
	 * @return
	 */
	public int checkCarLoanStatusHis7(String id);

	/**
	 * 车借划扣已办
	 * @param params
	 * @param pageBounds
	 * @return
	 */
	PageList<CarLoanStatusHisEx> findDrawDoneList(Map<String,Object> params,PageBounds pageBounds);
	
	
	   
}