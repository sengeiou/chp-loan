/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.daoFileDiskInfoDao.java
 * @Create By 张灏
 * @Create In 2016年3月24日 上午11:29:45
 */
package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.FileDiskInfo;

/**
 * 磁盘信息Dao层
 * @Class Name FileDiskInfoDao
 * @author 张灏
 * @Create In 2016年3月24日
 */
@LoanBatisDao
public interface FileDiskInfoDao extends CrudDao<FileDiskInfo>{

    /**
     * 通过指定参数查找磁盘信息
     * @author zhanghao
     * @CREAT IN 2016年03月24日
     * @param param  queryTime
     * @return FileDiskInfo
     * 
     */
    public FileDiskInfo getByParam(Map<String,Object> param);
    
    public List<Map<String,String>> getLevel(Map<String,Object> param);
    
	/**
	 * 查询汇金索引以及部件名
	 * @param queryTime 当前时间
	 * @param sysFlag 系统标识，默认为0
	 * @return
	 */
	public Map<String,String> getIndexComponentByQueryTime(String queryTime, String sysFlag);
}
