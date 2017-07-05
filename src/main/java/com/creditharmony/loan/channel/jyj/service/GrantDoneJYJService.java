package com.creditharmony.loan.channel.jyj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.trusteeship.dao.LoanExcelDao;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel5;
import com.creditharmony.loan.channel.goldcredit.view.GCGrantDoneView;
import com.creditharmony.loan.channel.jyj.dao.LoanGrantJYJDao;
import com.creditharmony.loan.channel.jyj.entity.LoanGrantDone;
import com.google.common.collect.Maps;
/**
 *  简易借 放款已办
 */
@Service("grantDoneJYJService")
public class GrantDoneJYJService extends CoreManager<LoanGrantJYJDao, LoanGrantDone>{
	
    @Autowired
    private LoanGrantJYJDao loanGrantDao;
    @Autowired
    private UserManager userManager;
    @Autowired
    private LoanExcelDao loanExcelDao;
	 
	/**
	 * 获取金信已放款列表中的所有的数据信息(含有查询条件和导出数据信息)
	 * @param page 分页信息
	 * @param loanGrantEx  查询条件
	 * @author 张建雄
	 * @return 
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<GCGrantDoneView> findGrantDoneList(Page<GCGrantDoneView> page,GCGrantDoneView doneView){
		Map<String ,Object> params = Maps.newHashMap();
		if (StringUtils.isNotBlank(doneView.getStoreCode())) {
			String []storeCodes = doneView.getStoreCode().split(",");
			params.put("storeCodes", storeCodes);
		}
		params.put("param", doneView);	
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<GCGrantDoneView> pageList = (PageList<GCGrantDoneView>)loanGrantDao.findGrantDoneList(pageBounds, params);
		for (GCGrantDoneView gcGrantDoneView : pageList) {
			queryName(gcGrantDoneView);
			gcGrantDoneView.setLoanFlag(DictCache.getInstance().getDictLabel("jk_channel_flag",gcGrantDoneView.getLoanFlag()));
			gcGrantDoneView.setUrgentFlag(DictCache.getInstance().getDictLabel("jk_urgent_flag",gcGrantDoneView.getUrgentFlag()));
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}
	/**
	 * (金信)查询放款已办导出，默认为全部
	 * 2016年3月23日
	 * By 张建雄
	 * @param doneView 查询条件
	 * @return 要导出的list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<GCGrantDoneView> exportGrantDoneNo(GCGrantDoneView doneView,String[]loanCodes){
		Map<String,Object> param = Maps.newHashMap();
		param.put("param", doneView);
		if(loanCodes.length > 0){
			param.put("loanCodes", loanCodes);
		}
		List<GCGrantDoneView> viewList = dao.findGrantDoneList(param);
		for (GCGrantDoneView gcGrantDoneView : viewList) {
			queryName(gcGrantDoneView);
		}
		return viewList;
	}
	
	
	/**
	 * 根据合同编号查询applyId
	 * 2016年1月19日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return applyId
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public String selApplyId(String contractCode){
		return dao.selApplyId(contractCode);
	}
	/**
	 * 查询一系列的操作人员
	 * @param gcGrantDoneView 参数信息
	 */
	private void queryName(GCGrantDoneView gcGrantDoneView){
		//团队经理
		User comUser = userManager.get(gcGrantDoneView.getLoanTeamManagerCode());
		if(!ObjectHelper.isEmpty(comUser)){
			gcGrantDoneView.setLoanTeamManagerName(comUser.getName());
		}else {
			gcGrantDoneView.setLoanTeamManagerName("");
		}
		//客户经理
		User managerUser = userManager.get(gcGrantDoneView.getLoanManagerCode());
		if(!ObjectHelper.isEmpty(managerUser)){
			gcGrantDoneView.setLoanManagerName(managerUser.getName());
		}else {
			gcGrantDoneView.setLoanManagerName("");
		}
		//客服人员
		User teamUser = userManager.get(gcGrantDoneView.getLoanCustomerServiceName());
		if(!ObjectHelper.isEmpty(teamUser)){
			gcGrantDoneView.setLoanCustomerServiceName(teamUser.getName());
		}else {
			gcGrantDoneView.setLoanCustomerServiceName("");
		}
		//放款人员 
		User serviceUser = userManager.get(gcGrantDoneView.getLoanOfficerName());
		if(!ObjectHelper.isEmpty(serviceUser)){
			gcGrantDoneView.setLoanOfficerName(serviceUser.getName());
		}else {
			gcGrantDoneView.setLoanOfficerName("");
		}
		//审核专员
		User wUser = userManager.get(gcGrantDoneView.getCheckEmpName());
		if(!ObjectHelper.isEmpty(wUser)){
			gcGrantDoneView.setCheckEmpName(wUser.getName());
		}else {
			gcGrantDoneView.setCheckEmpName("");
		}
	}
	
	//委托划扣列表数据
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<LoanGrantEx> wthkList(Page<LoanGrantEx> page,LoanGrantEx loanGrantEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<LoanGrantEx> pageList = (PageList<LoanGrantEx>)loanGrantDao.wthkList(pageBounds, loanGrantEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	//获取委托划扣详细数据
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String, String>> getLoanCode(LoanGrantEx loanGrantEx){
		return loanGrantDao.getLoanCode(loanGrantEx);
	}
	
	//获取划扣号
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String, String>> findStartDate(String[] loanCode) {
	    return this.loanGrantDao.findStartDate(loanCode);
	}
	
	/**
	 * 已放款 线下委托提现导入
	 * 2016年6月4日
	 * By 朱杰
	 * @param dataList
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void importExcel1(GrantExcel5 item) {
		String mark = item.getMark();
		String infoId = mark.substring(mark.lastIndexOf("_") + 1);
		HashMap<String, Object> resultMap = loanExcelDao.getLoanInfo(infoId);
		String loanCode = (String) resultMap.get("loan_code");

		if ("0000".equals(item.getReturnCode().trim())) {
			loanExcelDao.updateTrustCash(loanCode, item.getMoney());
		}

	}
	public Page<LoanGrantDone> findGrantDoneJYJ(Page<LoanGrantDone> page,
			LoanGrantDone loanGrantEx) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<LoanGrantDone> pageList = (PageList<LoanGrantDone>)loanGrantDao.findGrantDoneJYJ(pageBounds, loanGrantEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	public List<LoanGrantDone> findGrantDoneExcel(LoanGrantDone loanGrantEx) {
		List<LoanGrantDone> dones = loanGrantDao.findGrantDoneJYJExcel( loanGrantEx);
		return dones;
	}
}
