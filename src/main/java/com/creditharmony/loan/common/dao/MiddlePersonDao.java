package com.creditharmony.loan.common.dao;
import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.MiddlePerson;
/**
 * 
 * @Class Name MiddlePersonDao
 * @author 朱静越
 * @Create In 2015年12月3日
 * 中间人Dao类
 */
@LoanBatisDao
public interface MiddlePersonDao extends CrudDao<MiddlePerson>{
    /**
     * 查找全部中间人信息
     * 2015年12月24日
     * By 朱静越
     * @param pageBounds 分页参数
     * @param midPerson 中间人实体
     * @return
     */
    public List<MiddlePerson> selectMiddleByName(PageBounds pageBounds,MiddlePerson midPerson);
    
    /**
     * 查找全部中间人信息
     * 2016年1月29日
     * By 张振强
     * @param midPerson 中间人实体
     * @return
     */
    public List<MiddlePerson> selectMiddlePerson(MiddlePerson midPerson);
    
    /**
     * 根据中间人id查询
     * 2015年12月24日
     * By 朱静越
     * @param id 中间人id
     * @return
     */
    public MiddlePerson selectById(String id);

    /**
     * 还款查询存入账户
     * 2016年3月30日
     * By zhangfeng
     * @param midPerson
     * @return list
     */
	public List<MiddlePerson> findPaybackAccount(MiddlePerson midPerson);
    
}