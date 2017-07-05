/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.servicePaperlessPhotoService.java
 * @Create By 张灏
 * @Create In 2016年4月22日 下午5:54:36
 */
package com.creditharmony.loan.borrow.contract.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.PaperlessPhotoDao;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;

/**
 * 无纸化照片Service
 * @Class Name PaperlessPhotoService
 * @author 张灏
 * @Create In 2016年4月22日
 */
@Service
public class PaperlessPhotoService extends CoreManager<PaperlessPhotoDao, PaperlessPhoto>{
   
    /**
     *保存无纸化图片 
     *@author zhanghao 
     *@Create In 2016年04月22日
     *@param photo
     *@return none 
     */
    @Transactional(readOnly=false,value = "loanTransactionManager")
    public void savePaperlessPhoto(PaperlessPhoto photo){
		Map<String, String> param = new HashMap<String, String>();
		param.put("relationId", photo.getRelationId());
		PaperlessPhoto getPhoto = dao.getByRelationId(param);
		if (ObjectHelper.isNotEmpty(getPhoto)) {
			dao.updateByRelationId(photo);
		}else {
			dao.insertPaperlessPhoto(photo); 
		}
    }
    
    /**
     *更新图片 
     *@author zhanghao 
     *@Create In 2016年04月22日
     *@param photo
     *@return none 
     */
    @Transactional(readOnly=false,value = "loanTransactionManager")
    public void updatePaperlessPhoto(PaperlessPhoto photo){
       dao.updateByRelationId(photo); 
    }
    
    /**
     *更新图片 
     *@author zhanghao 
     *@Create In 2016年04月22日
     *@param photo
     *@return none 
     */
    @Transactional(readOnly=true,value = "loanTransactionManager")
    public PaperlessPhoto findPaperlessPhoto(Map<String,String> param){
       
        return dao.getByRelationId(param);
    }
    
    /**
     *通过loanCode获取所有的图片缓存信息 
     *@author zhanghao
     *@Create In 2016年04月23日
     *@param loanCode
     *@return List<PaperlessPhoto>
     * 
     */
    public List<PaperlessPhoto> getByLoanCode(String loanCode){
       
        return dao.getByLoanCode(loanCode); 
    }
}
