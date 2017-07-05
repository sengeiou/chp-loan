package com.creditharmony.loan.car.common.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.loan.car.carConsultation.ex.CarLoanAdvisoryBacklogEx;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;

@LoanBatisDao
public interface CarCustomerConsultationDao extends CrudDao<CarCustomerConsultation>{

    CarCustomerConsultation selectById(String id);
    
	 /**
     *新增沟通信息   
     *@param CarCustomerConsultation 
     *@return  
     */
    public void insertCarCustomerConsultation(CarCustomerConsultation carCustomerConsultation);
    
    /**
     *修改沟通信息   
     *@param CarCustomerConsultation 
     *@return  
     */
    public Integer updateById(CarCustomerConsultation carCustomerConsultation);
    
    /**
	 * 更新CarCustomerConsultation 
	 * 2016年1月22日
	 * By ganquan
	 * @param CarLoanAdvisoryBacklogEx
	 */
    public List<CarLoanAdvisoryBacklogEx> selectByCarLoanAdvisoryBacklog(PageBounds pageBounds,CarLoanAdvisoryBacklogEx carLoanAdvisoryBacklogEx);
    
  
    public List<CarLoanAdvisoryBacklogEx> selectOcrByCarLoanAdvisoryBacklog(PageBounds pageBounds,CarLoanAdvisoryBacklogEx carLoanAdvisoryBacklogEx);
      
    
    /**
     * 获取客户管理列表
     * 2016年2月25日
     * By 陈伟东
     * @param carLoanAdvisoryBacklogEx
     * @return
     */
    public List<CarLoanAdvisoryBacklogEx> getCustomerManagementList(PageBounds pageBounds,CarLoanAdvisoryBacklogEx carLoanAdvisoryBacklogEx);
    /**
	 * 查询CarCustomerConsultation 
	 * 2016年1月29日
	 * By ganquan
	 * @param String
	 */
    public CarCustomerConsultation selectByCustomerCode(String customerCode);
    /**
	 * 查询CarCustomerConsultation 
	 * 2016年3月11日
	 * By ganquan
	 * @param String
	 */
    public CarCustomerConsultation selectByLoanCode(String loanCode);
    
    public int selectConsul(String plateNumbers);
}