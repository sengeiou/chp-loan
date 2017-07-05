package com.creditharmony.loan.channel.jyj.dao;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.grant.entity.ex.ExportTemplateEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceFyEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceHylEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceTlEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceZJEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;

/**
 * 催收服务费dao
 * @Class Name UrgeServicesMoneyDao
 * @author 朱静越
 * @Create In 2015年12月11日
 */
@LoanBatisDao
public interface JyjUrgeServicesMoneyAlreadyDao extends CrudDao<UrgeServicesMoneyEx>{
	
	/**
	 * 查询放款待划扣列表
	 * 2015年12月12日
	 * By 朱静越
	 * @param urgeServicesMoneyEx 划扣列表信息
	 * @return 催收集合
	 */
  public  List <UrgeServicesMoneyEx> selectDeductsList(PageBounds pageBounds,UrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 
   * 2016年2月20日
   * By 朱静越
   * @param urgeServicesMoneyEx
   * @return
   */
  public  List <UrgeServicesMoneyEx> selectDeductsList(UrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 放款划扣已办查询
   * 2016年2月18日
   * By 朱静越
   * @param pageBounds
   * @param urgeServicesMoneyEx
   * @return 放款划扣已办
   */
  public List<UrgeServicesMoneyEx> selectDeductsSuccess(PageBounds pageBounds,UrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 根据查询条件查询的单子进行导出
   * 2016年2月23日
   * By 朱静越
   * @param urgeServicesMoneyEx 查询条件
   * @return 符合条件的单子
   */
  public List<UrgeServicesMoneyEx> selectDeductsSuccess(UrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 根据拆分表id查询划扣平台
   * 2016年1月12日
   * By 朱静越
   * @param id 拆分表id
   * @return 拆分实体
   */
  public UrgeServicesMoneyEx getDealType(@Param("id")String id);
  
  /**
   * 根据催收主表id，查询划扣回盘结果
   * 2016年2月20日
   * By 朱静越
   * @param id
   * @return
   */
  public UrgeServicesMoney find(@Param("id")String id);
  
  /**
   * 根据合同编号查询要进行更新的催收主表信息
   * 2016年2月19日
   * By 朱静越
   * @param remark 合同编号
   * @return
   */
  public UrgeServicesMoney selSuccess(@Param("remark")String remark);
  
  /**
   * 根据催收主表id和回盘结果查询，回盘结果为处理中（线下）
   * 2016年2月17日
   * By 朱静越
   * @param urgeServicesMoneyEx 查询条件
   * @return 集合
   */
  public List<UrgeServicesMoneyEx> selProcess(UrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 查询拆分表中回盘结果不为成功的单子
   * 2016年3月15日
   * By 朱静越
   * @param urgeServicesMoneyEx
   * @return
   */
  public List<UrgeServicesMoneyEx> selToDealOnLine(UrgeServicesMoneyEx urgeServicesMoneyEx);
  
  /**
   * 根据查询出来的list，进行拆分表的删除
   * 2016年2月17日
   * By 朱静越
   * @param list 回盘结果为处理中的单子
   * @return 删除结果
   */
  public int delProcess(@Param("id")String id);
  
   /**
    * 更新拆分表，追回单子时，进行拆分表的更新，更新单子的划扣结果，回盘原因，回盘时间。
    * 2015年12月15日
    * By 朱静越
    * @param urgeMoneyEx 催收实体类
    * @return 更新的结果
    */
  public int updUrgeSplit(UrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 更新催收主表
   * 2016年2月17日
   * By 朱静越
   * @param urgeMoney 更新实体
   * @return 更新结果
   */
  public int updateUrge(UrgeServicesMoney urgeMoney);
  
  /**
   * 根据合同编号进行更新催收服务费主表
   * 2016年3月9日
   * By 朱静越
   * @param urgeServicesMoney
   * @return
   */
  public int updateUrgeByCont(UrgeServicesMoney urgeServicesMoney);
  
  /**
   * 拆分时，将处理状态置为无效标识
   * 2016年1月12日
   * By 朱静越
   * @param urgeMoneyEx 催收实体
   * @return 更新结果
   */
  public int updSplitStatus(UrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 插入催收服务费表,放款成功之后对催收服务费进行插入
   * 2016年1月8日
   * By 朱静越
   * @param urgeServicesMoney 催收服务费实体
   * @return 是否插入成功
   */
  public int insertUrge(UrgeServicesMoney urgeServicesMoney);
  
  /**
   * 放款划扣，富友平台导出
   * 2016年1月6日
   * By 朱静越
   * @param urgeMoneyEx 封装拆分表中id
   * @return 富友平台导出实体的集合
   */
  public List<UrgeServiceFyEx> getDeductsFy(UrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 放款划扣，好易联导出
   * 2016年1月6日
   * By 朱静越
   * @param urgeMoneyEx 封装拆分表id
   * @return 好易联平台导出实体
   */
  public List<UrgeServiceHylEx> getDeductsHyl(UrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 放款划扣，好易联导出
   * 2016年1月6日
   * By 朱静越
   * @param urgeMoneyEx 封装拆分表id
   * @return 好易联平台导出实体
   */
  public List<UrgeServiceTlEx> getDeductsTl(UrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 放款划扣，中金导出
   * 2016年1月6日
   * By 朱静越
   * @param urgeMoneyEx 封装拆分表id
   * @return 好易联平台导出实体
   */
  public List<UrgeServiceZJEx> getDeductsZJ(UrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 将催收服务费主表拆分为拆分表
   * 2016年1月13日
   * By 朱静越
   * @param id 催收服务费id
   * @return 要进行拆分的实体
   */
  public List<PaybackApply> queryUrgeList(@Param("id") String id);
  
  /**
   * 根据条件检索要进行划扣的集合
   * 2016年2月17日
   * By 朱静越
   * @param urgeMoney 查询条件实体
   * @return 发送到批处理的list
   */
  public List<DeductReq> selSendList(UrgeServicesMoneyEx urgeMoney);
  
  /**
   * 导出划扣数据
   * 2016年3月1日
   * By 王彬彬
   * @param urgeMoneyEx 封装拆分表id
   * @return 导出实体
   */
  public List<ExportTemplateEx> getDeductsData(UrgeServicesMoneyEx urgeMoneyEx);
  
  /**
   * 根据放款id查找放款记录表
   * 2016年3月8日
   * By 朱静越
   * @param id
   * @return
   */
  public LoanGrant selGrant(String id);
  
  /**
   * 根据催收主表中的合同编号进行查询催收主表的id
   * 2016年3月9日
   * By 朱静越
   * @param contractCode
   * @return
   */
  public String selUrgeId(String contractCode);
  
  /**
   * 根据合同编号查询蓝补金额
   * 2016年4月18日
   * By 朱静越
   * @param contractCode
   * @return
   */
  public BigDecimal selBlueAmount(String contractCode);
  
  /**
   * 更新蓝补金额
   * 2016年4月18日
   * By 朱静越
   * @param payback
   */
  public void updBlue(Payback payback);
  
  /**
   * 删除催收服务费
   * 2016年6月2日
   * By 朱静越
   * @param urge
   */
  public void deleteByContract(UrgeServicesMoney urge);
}