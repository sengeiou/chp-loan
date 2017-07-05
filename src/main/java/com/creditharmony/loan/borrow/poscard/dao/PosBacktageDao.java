package com.creditharmony.loan.borrow.poscard.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.poscard.entity.PosBacktage;

/**
 * pos后台数据操作
 * @Class Name PosBacktageDao
 * @author 管洪昌
 * @Create In 2016年1月20日
 */
@LoanBatisDao
public interface PosBacktageDao extends CrudDao<PosBacktage>{
	
	/**
     * pos后台数据列表
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
    public List<PosBacktage> selectPosBacktageList(PageBounds pageBounds,PosBacktage posBacktage);
    
    
	/**
     * pos后台数据列表
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
    public List<PosBacktage> selectPosList(PageBounds pageBounds,PosBacktage posBacktage);
    
	/**
     * pos后台数据列表更新
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
	public void updatePosBacktage(PosBacktage posBacktage);

	/**
     * pos登录密码修改
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
	public void updatePosPwd(PosBacktage posBacktage);

	
	/**
     * pos登录密码修改
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
	public PageList<PosBacktage> selectPosAlreadyMatchInfoList(
			PageBounds pageBounds, PosBacktage posBacktage);
	
	
	/**
     * 发起还款申请的POS刷卡查账数据是否在POS后台数据列表有未匹配项
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
	public List<PosBacktage> checkRefPosStr(PosBacktage posBacktage);

	
	/**
     * POS已匹配成功列表
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
	public PageList<PosBacktage> selectPosBacktagePosCard(
			PageBounds pageBounds, PosBacktage posBacktage);
	
	/**
     * POS已匹配成功列表详细页面
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
	public PosBacktage seePosOne(PosBacktage posBacktage);

	/**
     * 修改POS已匹配列表
     * 2016年1月20日
     * By 管哄昌
     * @param pageBounds
     * @param posBacktage
     * @return List
     */
	public void updatePosMaching(PosBacktage posBacktage);
	    
}
