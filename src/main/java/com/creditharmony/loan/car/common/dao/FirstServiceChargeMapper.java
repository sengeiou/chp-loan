package com.creditharmony.loan.car.common.dao;

import java.util.List;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.FirstServiceCharge;
/**
 * 首期服务费率
 * @author 张庆安
 *
 */
@LoanBatisDao
public interface FirstServiceChargeMapper {
    int deleteByPrimaryKey(String id);

    int insert(FirstServiceCharge record);

    int insertSelective(FirstServiceCharge record);

    FirstServiceCharge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FirstServiceCharge record);

    int updateByPrimaryKey(FirstServiceCharge record);
    
    /**
     * 查找所有费率信息
     * @return
     */
    List<FirstServiceCharge> findFirstServiceChargeList(FirstServiceCharge charge);
    
}