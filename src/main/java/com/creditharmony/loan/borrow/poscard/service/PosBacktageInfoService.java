package com.creditharmony.loan.borrow.poscard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.poscard.dao.PosBacktageDao;
import com.creditharmony.loan.borrow.poscard.entity.PosBacktage;

/**
 * pos后台数据操作
 * @Class Name PosBacktageInfoService
 * @author 管洪昌
 * @Create In 2016年1月20日
 */
@Service("posBacktageInfoService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class PosBacktageInfoService extends CoreManager<PosBacktageDao, PosBacktage>{

	@Autowired
	private PosBacktageDao posBacktageDao;
	
	/**
	 * pos后台数据列表
	 * 2016年1月20日
	 * By 管洪昌
	 * @param page
	 * @param PosBacktage
	 * @return 分页对象
	 */
	public Page<PosBacktage> selectPosBacktageList(Page<PosBacktage> page,PosBacktage posBacktage) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PosBacktage> pageList = (PageList<PosBacktage>)posBacktageDao.selectPosBacktageList(pageBounds, posBacktage);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
     * 修改pos单条数据 2015年1月25日 
     * By 管洪昌
     * @param posBacktage
     * @return
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updatePosBacktage(PosBacktage posBacktage) {
		posBacktage.preUpdate();
		dao.update(posBacktage);
	}
	
	
	/**
     * 修改已匹配POS列表数据
     * By 管洪昌
     * @param posBacktage
     * @return
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updatePosMaching(PosBacktage posBacktage) {
		posBacktage.preUpdate();
		dao.updatePosMaching(posBacktage);
	}
	
	/**
     * 查询POS登录密码是否正确2015年1月25日 
     * By 管洪昌
     * @param id
     * @param posOldPassword
     * @return
     */
	public List<PosBacktage> findPosPwd(String id) {
		PosBacktage  PosBacktage=new PosBacktage();
		PosBacktage.setId(id);
		return dao.findList(PosBacktage);
	}

	/**
     * 修改POS登录密码2015年1月25日 
     * By 管洪昌
     * @param id
     * @param posOldPassword
     * @return
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updatePosPasswordById(String id, String loginName,String posNewPassword) {
		PosBacktage  PosBacktage=new PosBacktage();
		PosBacktage.setPosNewPassword(posNewPassword);
		PosBacktage.setLoginName(loginName);
		PosBacktage.setId(id);
		posBacktageDao.updatePosPwd(PosBacktage);
	}

	/**
     * POS已匹配数据列表
     * By 管洪昌
     * @param id
     * @param posBacktage page
     * @return page
     */
	public Page<PosBacktage> selectPosAlreadyMatchInfoList(
			Page<PosBacktage> page, PosBacktage posBacktage) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PosBacktage> pageList = (PageList<PosBacktage>)posBacktageDao.selectPosAlreadyMatchInfoList(pageBounds, posBacktage);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	
	/**
     * 填写的POS刷卡查账数据是否已经有未匹配项
     * By 管洪昌
     * @param id
     * @param posBacktage page
     * @return page
     */
	public List<PosBacktage> checkRefPosStr(PosBacktage posBacktage) {
		// TODO Auto-generated method stub
		return dao.checkRefPosStr(posBacktage);
	}

	
	/**
     * 填写的POS刷卡查账数据是否已经有未匹配项
     * By 管洪昌
     * @param id
     * @param posBacktage page
     * @param posBacktage posBacktage
     * @return page
     */
	public Page<PosBacktage> findApplyPosCard(Page<PosBacktage> page,
			PosBacktage posBacktage) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PosBacktage> pageList =posBacktageDao.selectPosBacktagePosCard(pageBounds, posBacktage);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	
	/**
     * 查看单条的数POS已匹配界面
     * By 管洪昌
     * @param id
     * @param posBacktage page
     * @param posBacktage posBacktage
     * @return page
     */
	public PosBacktage seeStoresAlreadyDos(PosBacktage posBacktage) {
		return dao.seePosOne(posBacktage);
	}
	
	

}
