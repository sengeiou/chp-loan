package com.creditharmony.loan.channel.goldcredit.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.trusteeship.dao.GoldAccountCeilingDao;
import com.creditharmony.loan.channel.goldcredit.dao.GCCeilingDao;
import com.creditharmony.loan.channel.goldcredit.view.GCCeiling;
import com.creditharmony.loan.channel.goldcredit.view.SettingCellingNumEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.star.lang.NullPointerException;
@Service
public class GCCeilingService extends CoreManager<GCCeilingDao, GCCeiling>{
	@Autowired
	private GCCeilingDao dao;
	@Autowired
	private GoldAccountCeilingDao goldAccountCeilingDao;
	/**
	 * 获取金信上限数据列表 2016年2月19日 By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 金信上限数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<Object> findGCCeilingList(Page<GCCeiling> page, GCCeiling params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		String [] storeOrgIds = {};
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			storeOrgIds = params.getStoreOrgId().split(",");
		}
		Map<String ,Object> conditions = Maps.newHashMap();
		conditions.put("params", params);
		conditions.put("storeOrgIds", storeOrgIds);
		PageList<GCCeiling> pageList = (PageList<GCCeiling>) dao.getCeilingList(pageBounds, conditions);
		Map<String,String> ceilingSum = dao.getCeilingSum(conditions);
		List<Object> lists = Lists.newArrayList();
		
		PageUtil.convertPage(pageList, page);
		lists.add(page);
		lists.add(ceilingSum);
		return lists;
	}
	/**
	 * 导出
	 * 2016年2月24日 
	 * By 张建雄
	 * 
	 * @return 债权提前结清的数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<GCCeiling> exportCeiling(String cid,GCCeiling params){
		Map<String ,Object> conditions = Maps.newHashMap();
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			String []storeOrgIds = params.getStoreOrgId().split(",");
			conditions.put("storeOrgIds", storeOrgIds);
		}
		if (StringUtils.isNotEmpty(cid)) {
			String [] loanCodes = cid.split(",");
			params.setLoanCode(cid);
			conditions.put("loanCodes", loanCodes);
		}
		conditions.put("params", params);
		List<GCCeiling> pageList = dao.getCeilingList(conditions);
		return pageList;
	}
	/**
	 * 获取用户申请信贷信息
	 *  2016年2月24日 
	 *  By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 金信上限数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Map<String,Object> findUserInfo(String loanCode) {
		if (StringUtils.isEmpty(loanCode)){
			return null;
		}
		Map<String,Object> maps = Maps.newHashMap();
		Map<String,String> pageList =  dao.findUserInfo(loanCode);
		List<Map<String,String>> creditList = dao.findCreditList(loanCode);
		List<Map<String,String>> contactList = dao.findContactList(loanCode);
		pageList.put("dict_customer_source", DictCache.getInstance().getDictLabel("jk_cm_src", pageList.get("dict_customer_source")));
		pageList.put("dict_loan_use", DictCache.getInstance().getDictLabel("jk_loan_use", pageList.get("dict_loan_use")));
		/*pageList.put("dict_comp_type", DictCache.getInstance().getDictLabel("jk_industry_type", pageList.get("dict_comp_type")));*/
		
		for (Map<String, String> map : creditList) {
			map.put("dict_mortgage_type", DictCache.getInstance().getDictLabel("jk_pledge_flag", map.get("dict_mortgage_type")));
		}
		for (Map<String, String> map : contactList) {
			map.put("contact_relation", DictCache.getInstance().getDictLabel("jk_workemate_relation", map.get("contact_relation")));
			
		}
		maps.put("info", pageList);
		maps.put("creditList", creditList);
		maps.put("contactList", contactList);
		return maps;
	}
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public SettingCellingNumEntity selectCeilingMoney (){
		return dao.selectCeilingMoney();
	}
	/**
	 *  设置金信上限次数
	 * @param ceilingMoney 上限额度
	 * @return 
	 * @throws NullPointerException 
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public SettingCellingNumEntity setCeilingMoney(SettingCellingNumEntity cellingNum,String userName) throws NullPointerException {
		if (ObjectHelper.isEmpty(cellingNum)) {
			throw new NullPointerException();
		}
		cellingNum.setUserName(userName);
		Integer cHPResidual = cellingNum.getCHP1() + cellingNum.getCHP2() + cellingNum.getCHP3() + cellingNum.getCHP4() + cellingNum.getCHP5();
		cellingNum.setCHPResidual(cHPResidual);
		Integer goldCreditResidual = cellingNum.getGoldCredit1() + cellingNum.getGoldCredit2() + cellingNum.getGoldCredit3() + cellingNum.getGoldCredit4() + cellingNum.getGoldCredit5();
		cellingNum.setGoldCreditResidual(goldCreditResidual);
		Integer zcjResidual = cellingNum.getZcj1() + cellingNum.getZcj2() + cellingNum.getZcj3() + cellingNum.getZcj4() + cellingNum.getZcj5();
		cellingNum.setZcjResidual(zcjResidual);
		Integer p2pResidual = cellingNum.getP2p1() + cellingNum.getP2p2() + cellingNum.getP2p3() + cellingNum.getP2p4() + cellingNum.getP2p5();
		cellingNum.setP2pResidual(p2pResidual);
		cellingNum.setVersion(0);
		dao.updateJINXIN();
		dao.setCeilingNum(cellingNum);
		dao.setCeilingNumCopy(cellingNum);
		return cellingNum;
	}
	/**
	 *  金信数据归档
	 * @return 
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCeiling() {
		dao.updateData();
		dao.updateJINXIN();
		dao.updateJINXINCopy();
	}
	/**
	 * 向金信库中插入数据信息
	 */
	public void insertJINXINData(String loanCode){
		if (StringUtils.isEmpty(dao.selectLoanCode(loanCode))){
			dao.insertJINXINData(loanCode);
		}
	}
	/**
	 * 合同审核中的金信上限设置
	 * @return
	 */
	public Map<String ,Object> getJXCeillingData(){
		 Map<String ,Object> maps = Maps.newHashMap();
		 maps = dao.getJXCeillingData();
		 return maps;
	}
	/**
	 * 设置合同审核中的金信上限
	 * @return
	 */
	public void setJXCeiling(BigDecimal celling){
		 Map<String ,Object> maps = Maps.newHashMap();
		 User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		 maps.put("version", 1);
		 maps.put("userName", user.getUserCode());
		 maps.put("quotaLimit", celling);
		 dao.insertJXCeilling(maps);
	}
	/**
	 * 归档合同审核中的金信上限数据信息
	 */
	public void clearCeilling(){
		dao.updateJXCeilling();
	}
	/**
	 * TG数据归档
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCeilingForTG() {
		goldAccountCeilingDao.updateData();
		goldAccountCeilingDao.updateGoldAccount();
		dao.updateTGData();
	}
	/**
	 * 查询指定的归档总笔数和批借金额
	 * @param cellingNum
	 * @return
	 */
	public List<Map<String, String>> queryCellingNum(GCCeiling cellingNum) {
		Map<String ,Object> conditions = Maps.newHashMap();
		conditions.put("params", cellingNum);
		List< Map<String, String>> lists=null;
		if("0".equals(cellingNum.getModel())){
			SettingCellingNumEntity entity= dao.selectCeilingMoney();
			String channelFlag="";
			if(entity!=null){
				if(entity.getCHP1()!=0 || entity.getCHP2()!=0 || entity.getCHP3()!=0 || entity.getCHP4()!=0 || entity.getCHP5()!=0){
					channelFlag="'2'";
				}
				if(entity.getGoldCredit1()!=0 || entity.getGoldCredit2()!=0 || entity.getGoldCredit3()!=0 || entity.getGoldCredit4()!=0 || entity.getGoldCredit5()!=0){
					if(channelFlag==""){
						channelFlag="'0'";
					}else{
						channelFlag=channelFlag+",'0'";
					}
				}
				if(entity.getZcj1()!=0 || entity.getZcj2()!=0 || entity.getZcj3()!=0 || entity.getZcj4()!=0 || entity.getZcj5()!=0){
					if(channelFlag==""){
						channelFlag="'5'";
					}else{
						channelFlag=channelFlag+",'5'";
					}
				}
				if(entity.getP2p1()!=0 || entity.getP2p2()!=0 || entity.getP2p3()!=0 || entity.getP2p4()!=0 || entity.getP2p5()!=0){
					if(channelFlag==""){
						channelFlag="'1'";
					}else{
						channelFlag=channelFlag+",'1'";
					}
				}
			}
			conditions.put("channelFlag", channelFlag);
			lists = dao.getCeilingSumForArchiveChannel(conditions);
		}else{
			Map<String,String> map = dao.getCeilingSumForArchive(conditions);
			lists = Lists.newArrayList();
			lists.add(map);
		}
		return lists;
	}
	/**
	 * 查询房产信息
	 * @param loanCode
	 * @return
	 */
	public List<Map<String, String>> findHouseList(String loanCode) {
		
		return dao.findHouseList(loanCode);
	}
}
