package com.creditharmony.loan.car.common.dao;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceFyEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServiceHylEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServiceZJEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesMoneyEx;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;

/**
 * 催收服务费dao
 * @Class Name CarUrgeServicesMoneyDao
 * @Create In 2016年2月28日
 */
@LoanBatisDao
public interface CarUrgeServicesMoneyDao extends CrudDao<CarUrgeServicesMoneyEx>{
	
	
	/**
	 * 根据放款id获取最新的催收服务费
	 * 2016年3月4日
	 * By 陈伟东
	 * @param carUrgeServicesMoney
	 * @return
	 */
	public CarUrgeServicesMoney findCurrentCarUrgeMoneyByGrantId(CarUrgeServicesMoney carUrgeServicesMoney);
	
	/**
	 * 查询放款待划扣列表
	 * 2015年12月12日
	 * @param urgeServicesMoneyEx 划扣列表信息
	 * @return 催收集合
	 */
  public  List <CarUrgeServicesMoneyEx> selectDeductsList(PageBounds pageBounds,CarUrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 
   * 2016年3月1日
   * @param urgeServicesMoneyEx
   * @return
   */
  public  List <CarUrgeServicesMoneyEx> selectDeductsList(CarUrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 放款划扣已办查询
   * 2016年2月18日
   * @param pageBounds
   * @param urgeServicesMoneyEx
   * @return 放款划扣已办
   */
  public List<UrgeServicesMoneyEx> selectDeductsSuccess(PageBounds pageBounds,UrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 根据查询条件查询的单子进行导出
   * 2016年2月23日
   * @param urgeServicesMoneyEx 查询条件
   * @return 符合条件的单子
   */
  public List<UrgeServicesMoneyEx> selectDeductsSuccess(UrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 根据拆分表id查询划扣平台
   * 2016年1月12日
   * @param id 拆分表id
   * @return 拆分实体
   */
  public UrgeServicesMoneyEx getDealType(@Param("id")String id);
  
  /**
   * 根据催收主表id，查询划扣回盘结果
   * 2016年3月5日
   * @param id
   * @return
   */
  public CarUrgeServicesMoney find(@Param("id")String id);
  
  /**
   * 根据合同编号查询要进行更新的催收主表信息
   * 2016年2月19日
   * @param remark 合同编号
   * @return
   */
  public List<CarUrgeServicesMoney> selSuccess(@Param("enterpriseSerialno")String enterpriseSerialno);
  
  /**
   * 根据催收主表id和回盘结果查询，回盘结果为处理中（线下）
   * 2016年2月17日
   * @param urgeServicesMoneyEx 查询条件
   * @return 集合
   */
  public List<CarUrgeServicesMoneyEx> selProcess(CarUrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 根据查询出来的list，进行拆分表的删除
   * 2016年2月17日
   * @param list 回盘结果为处理中的单子
   * @return 删除结果
   */
  public int delProcess(@Param("id")String id);
  
   /**
    * 更新拆分表，追回单子时，进行拆分表的更新，更新单子的划扣结果，回盘原因，回盘时间。
    * 2015年12月15日
    * @param urgeMoneyEx 催收实体类
    * @return 更新的结果
    */
  public int updUrgeSplit(CarUrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 更新催收主表
   * 2016年2月17日
   * @param urgeMoney 更新实体
   * @return 更新结果
   */
  public int updateUrge(CarUrgeServicesMoney urgeMoney);
  
  /**
   * 拆分时，将处理状态置为无效标识
   * 2016年1月12日
   * @param urgeMoneyEx 催收实体
   * @return 更新结果
   */
  public int updSplitStatus(CarUrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 插入催收服务费表,放款成功之后对催收服务费进行插入
   * 2016年1月8日
   * @param urgeServicesMoney 催收服务费实体
   * @return 是否插入成功
   */
  public int insertUrge(CarUrgeServicesMoney urgeServicesMoney);
  
  /**
   * 放款划扣，富友平台导出
   * 2016年1月6日
   * @param urgeMoneyEx 封装拆分表中id
   * @return 富友平台导出实体的集合
   */
  public List<UrgeServiceFyEx> getDeductsFy(UrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 放款划扣，好易联、通联导出
   * 2016年3月2日
   * @param urgeMoneyEx 封装拆分表id
   * @return 好易联、通联平台导出实体
   */
  public List<CarUrgeServiceHylEx> getDeductsHyl(CarUrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 放款划扣，中金导出
   * 2016年3月2日
   * @param urgeMoneyEx 封装拆分表id
   * @return 中金平台导出实体
   */
  public List<CarUrgeServiceZJEx> getDeductsZJ(CarUrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 将催收服务费主表拆分为拆分表
   * 2016年1月13日
   * @param id 催收服务费id
   * @return 要进行拆分的实体
   */
  public List<PaybackApply> queryUrgeList(@Param("id") String id);
  
  /**
   * 根据条件检索要进行划扣的集合
   * 2016年2月17日
   * @param urgeMoney 查询条件实体
   * @return 发送到批处理的list
   */
  public List<DeductReq> selSendList(UrgeServicesMoneyEx urgeMoney);
  
	/**
	 * 停止或开启滚动划扣方法
	 * 2016年3月2日
	 * @param sysValue 
	 * @return int
	 */
	public int changeDeductsRule(HashMap<Object, Object> hashMap);
	
	/**
	 * 停止或开启滚动划扣方法
	 * 2016年3月2日
	 * @param sysValue 
	 * @return int
	 */
	public String selectSystemSetting(HashMap<Object, Object> hashMap);
	
	/**
	 *  查询要划扣的数据  DeductReq  车借
	 * @Create In 2016年2月17日
	 * @param paramMap
	 * @return List<DeductReq> 要划扣的数据 
	 */
	public List<DeductReq> queryUrgeDeductReq(HashMap<Object, Object> hashMap);
	
	/**
	 *  查询要划扣的数据  DeductReq  车借
	 * @Create In 2016年2月17日
	 * @param paramMap
	 * @return List<DeductReq> 要划扣的数据 
	 */
	public List<DeductReq> queryAutomaticDeductReq(HashMap<Object, Object> hashMap);
	
	/**
	 * 根据合同编号查询要进行放款审核退回的单子的拆分回盘结果为处理中的单子的个数
	 * 2016年3月8日
	 * @param contractCode 合同编号
	 * @return  该单子的处理状态
	 */
	public String getDealCount(String contractCode);
	
	
}