/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.serviceFyAreaCodeService.java
 * @Create By 张灏
 * @Create In 2016年3月8日 下午5:20:11
 */
package com.creditharmony.loan.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.FyAreaCodeDao;
import com.creditharmony.loan.common.entity.FyAreaCode;

/**
 * 富友地区信息Service
 * @Class Name FyAreaCodeService
 * @author 张灏
 * @Create In 2016年3月8日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class FyAreaCodeService extends CoreManager<FyAreaCodeDao, FyAreaCode> {
    
    /**
     *通过指定的参数查询地区信息 
     *@author zhanghao 
     *@Create In 2016年03月08日
     *@param param key parentId 父类ID areaType 地区类型
     *@return List<FyAreaCode> 
     */
    @Transactional(readOnly = true, value = "loanTransactionManager")
    public List<FyAreaCode> queryACByParam(Map<String,Object> param){
        return dao.queryACByParam(param);
    }
}
