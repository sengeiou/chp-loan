package com.creditharmony.loan.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.MiddlePersonDao;
import com.creditharmony.loan.common.entity.MiddlePerson;
/**
 * 中间人Service,用来定义中间人表的各种操作
 * @Class Name MiddlePersonService
 * @author 朱静越
 * @Create In 2015年12月3日
 */
@Service("middlePersonService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class MiddlePersonService extends CoreManager<MiddlePersonDao, MiddlePerson>{
	
	/**
	 * 查找所有的中间人信息
	 * 2016年2月22日
	 * By 朱静越
	 * @param page page对象
	 * @param midPerson 中间人查询条件
	 * @return 分页的中间人信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<MiddlePerson> selectAllMiddle(Page<MiddlePerson> page,MiddlePerson midPerson){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<MiddlePerson> pageList = (PageList<MiddlePerson>)dao.selectMiddleByName(pageBounds, midPerson);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查找所有的中间人
	 * 2016年2月22日
	 * By 朱静越
	 * @param midPerson 查询条件
	 * @return 中间人集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<MiddlePerson> selectMiddlePerson(MiddlePerson midPerson){
		
		return dao.selectMiddlePerson(midPerson);
	}
	
	/**
	 * 根据中间人主键进行检索
	 * 2015年12月11日
	 * By 朱静越
	 * @param id 中间人主键
	 * @return 中间人
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public MiddlePerson selectById(String id){
		return dao.selectById(id);
	}
	
	/**
	 * 还款查询存入账户
	 * 2015年12月11日
	 * By zhangfeng
	 * @param id 中间人主键
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<MiddlePerson>  findPaybackAccount(MiddlePerson midPerson){
		return dao.findPaybackAccount(midPerson);
	}
}
