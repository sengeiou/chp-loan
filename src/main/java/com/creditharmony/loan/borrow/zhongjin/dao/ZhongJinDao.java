package com.creditharmony.loan.borrow.zhongjin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.zhongjin.entity.PaybackCpcnOut;
import com.creditharmony.loan.borrow.zhongjin.view.History;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackCpcn;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackCpcnModel;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackOrder;

/**
 * 中金划扣
 * @Class Name ZhongJinDao
 * @author WJJ
 * @Create In 2016年3月3日
 */
@LoanBatisDao
public interface ZhongJinDao {
	
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 分页中金列表
	 */
	List<PaybackCpcn> selectByParam(PageBounds pageBounds,PaybackCpcnModel params);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 不分页中金列表
	 */
	List<PaybackCpcn> findByParams(PaybackCpcnModel params);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 中金划扣导出Excel数据
	 */
	List<PaybackCpcnOut> exportList(PaybackCpcnModel params);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 添加中金数据
	 */
	void insert(PaybackCpcn params);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 修改中金数据
	 */
	void update(PaybackCpcn params);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 按序号查询个数
	 */
	long getCount(@Param(value = "serialNum") String serialNum);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取省编号
	 */
	List<String> getProvinceName(@Param(value = "provinceName") String provinceName);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取市级编号
	 */
	List<String> getCityName(@Param(value = "provinceName") String provinceName,@Param(value = "cityName") String cityName);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取银行编码
	 */
	String getBankValue(@Param(value = "bankName") String bankName);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取证件类型编码
	 */
	String getCerTypeValue(@Param(value = "cerName") String cerName);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取列表总条数和总金额
	 */
	List<Map<String,Object>> getSelectCount(PaybackCpcnModel params);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 添加中金划扣时间
	 */
	void insertPaybackOrder(PaybackOrder paybackOrder);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 添加中金历史
	 */
	void addHistory(History params);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取中金历史
	 */
	List<History> getHistory(@Param(value = "cpcnId") String cpcnId);
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 删除中金划扣时间
	 */
	void delOrder(@Param(value = "cpcnId") String cpcnId);
	
}
