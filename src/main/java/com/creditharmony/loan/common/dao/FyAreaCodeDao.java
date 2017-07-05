/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.daoFyAreaCode.java
 * @Create By 张灏
 * @Create In 2016年3月8日 下午5:03:10
 */
package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.FyAreaCode;

/**
 * 富友地区编号
 * @Class Name FyAreaCode
 * @author 张灏
 * @Create In 2016年3月8日
 */
@LoanBatisDao
public interface FyAreaCodeDao extends CrudDao<FyAreaCode>{
   
    /**
     *通过指定的参数查询地区信息 
     *@author zhanghao 
     *@Create In 2016年03月08日
     *@param param key parentId 父类ID areaType 地区类型
     *@return List<FyAreaCode> 
     */
    public List<FyAreaCode> queryACByParam(Map<String,Object> param);
}
