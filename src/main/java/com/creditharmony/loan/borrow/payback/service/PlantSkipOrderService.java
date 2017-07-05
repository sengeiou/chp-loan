package com.creditharmony.loan.borrow.payback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.UseFlag;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.type.DeductType;
import com.creditharmony.core.fortune.type.OpenBank;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.payback.dao.PlantSkipOrderDao;
import com.creditharmony.loan.borrow.payback.entity.PlantSkipOrder;
import com.creditharmony.loan.common.entity.CodeName;

@Service
@Transactional(value = "loanTransactionManager", readOnly = true)
public class PlantSkipOrderService extends CoreManager<PlantSkipOrderDao,PlantSkipOrder>{
	
	/**
	 * 增加平台跳转顺序  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void insert(PlantSkipOrder record) {
		if (record.getIsNewRecord()){
			record.preInsert();
			installRule(record);
			dao.insert(record);
		}else{
			record.preUpdate();
			dao.updateByPrimaryKey(record);
		}
		
	}
	
	/**
	 * 修改平台跳转顺序  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void update(PlantSkipOrder record) {
		installRule(record);
		dao.updateByPrimaryKeySelective(record);
		
	}
	
	/**
	 * 修改平台跳转顺序  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void delete(PlantSkipOrder record) {
		dao.deleteByPrimaryKey(record);
		
	}
	
	/**
	 * 修改平台跳转顺序  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public PlantSkipOrder selectByPrimaryKey(PlantSkipOrder record) {
		return dao.selectByPrimaryKey(record.getId());
	}
	
	/**
	 * 查询平台跳转顺序  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return list
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public PageList<PlantSkipOrder> findSkipList(PlantSkipOrder record) {
		return dao.findSkipList(record);
	}
	
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public Page<PlantSkipOrder> queryPage(Page<PlantSkipOrder> page,
			PlantSkipOrder record) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PlantSkipOrder> pageList = (PageList<PlantSkipOrder>)dao.queryPage(pageBounds,record);
		for(PlantSkipOrder order: pageList){
			order.setBankCode(OpenBank.getOpenBank(order.getBankCode()));
		    order.setIsConcentrate(YESNO.parseByCode(order.getIsConcentrate()).getName());
		    if(UseFlag.QY.value.equals(order.getStatus())){
			    order.setStatus("启用");
		    }else{
		        order.setStatus("停用");
		    }
		}
		PageUtil.convertPage(pageList, page);
		return page;	}
	
	
	/**
	 * 组装规则 讲前台传过来的规则拼成 
	 *               1:0,2:1 类似的格式
	 *               富友（实时）-> 好易联（批量）
	 * @param platId
	 * @param typeId
	 * @return
	 */
	public void installRule(PlantSkipOrder record){
		   List<String> platId = record.getPlatIdList();
		   List<String> typeId = record.getDeductTypeList();
		    StringBuilder str = new StringBuilder();
		    StringBuilder strName = new StringBuilder();
		    
		    for(int i = 0; i < platId.size(); i++){
		    	String pid = platId.get(i);
		    	String tid = "";
		    	if(typeId != null && typeId.size() != 0){
			    	   tid = typeId.get(i);
		    	}
		    	if(!ObjectHelper.isEmpty(pid)){
		    		str.append(pid);
			    	str.append(":");
			    	str.append(tid);
					DeductPlat plant =  DeductPlat.parseByCode(platId.get(i));
					if(!DeductPlat.TONGLIAN.getCode().equals(platId.get(i))){
						DeductType type = DeductType.parseByCode(typeId.get(i));
						strName.append(plant.getName()+"(");
						strName.append(type.getName()+")");
					}else{
						strName.append(plant.getName());
					}
					if(i <  platId.size()-1 ){
						str.append(",");
			    		strName.append("->");
			    	}
		    	}
		    }
		    record.setPlatformRule(str.toString());
		    record.setPlatformRuleName(strName.toString());
	 }

	public PlantSkipOrder getById(PlantSkipOrder record) {
		record = dao.selectByPrimaryKey(record.getId());
		splitRule(record);
		return record;
	}
	
	/**
	 * 拆分规则
	 */
	public void splitRule(PlantSkipOrder record){
		// name 代表平台， code代表划扣方式
		if(!ObjectHelper.isEmpty(record)){
			List<CodeName> platTypes = new  ArrayList<CodeName>();
			String  rule = record.getPlatformRule();
			if(!ObjectHelper.isEmpty(rule)){
				String[] rules = rule.split(",");
				for(String platType : rules){
					if(!ObjectHelper.isEmpty(platType)){
						 String[] str = platType.split(":");
						 CodeName ptype;
						 if(DeductPlat.TONGLIAN.getCode().equals(str[0])){
							  ptype = new CodeName("",str[0]);
						 }else{
							  ptype = new CodeName(str[1],str[0]);
						 }
						 platTypes.add(ptype);
					}
				}
				record.setPlatDeductTypeList(platTypes);
			}
		}
	}
	
	/**
	 * 根据银行code 和是否集中查询数据
	 * @param record
	 * @return
	 */

	public PlantSkipOrder findSkip(PlantSkipOrder record) { 
		List<PlantSkipOrder>   list = dao.findSkipList(record);
		if(ObjectHelper.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}
}
