/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.car.carTotalRate.service
 * @Create By 张进
 * @Create In 016年2月3号
 */
package com.creditharmony.loan.car.carTotalRate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.carTotalRate.ex.CarGrossSpreadEx;
import com.creditharmony.loan.car.carTotalRate.ex.CarSpreadProvinceCityRelationEx;
import com.creditharmony.loan.car.common.consts.CarSystemConfigConstant;
import com.creditharmony.loan.car.common.dao.CarCustomerConsultationDao;
import com.creditharmony.loan.car.common.dao.CarGrossSpreadDao;
import com.creditharmony.loan.car.common.dao.CarSpreadProvinceCityRelationDao;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarGrossSpread;
import com.creditharmony.loan.car.common.entity.CarSpreadProvinceCityRelation;
/**
 * 车借总费率service
 * @Class Name CarGrossSpreadService
 * @author 任亮杰
 * @Create In 2016年1月22日
 */
@Service("carGrossSpreadService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarGrossSpreadService extends CoreManager<CarGrossSpreadDao, CarGrossSpread> {

	@Autowired
	private CarGrossSpreadDao carGrossSpreadDao;
	
	@Autowired
	private CarSpreadProvinceCityRelationDao carSpreadProvinceCityRelationDao;
	
	/**
	 * 		新增一条车借总费率
	 * @param carGrossSpread
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertCarGrossSpread(CarGrossSpread carGrossSpread){
		carGrossSpread.preInsert();
		carGrossSpread.setRateId(IdGen.uuid());
		carGrossSpreadDao.insertCarGrossSpread(carGrossSpread);
	}
	
	/**
	 * 		查询车借总费率列表
	 * @param carGrossSpread
	 * @return
	 */
	public Page<CarGrossSpread> findCarGrossSpreadList(Page<CarGrossSpread> page,
			CarGrossSpread carGrossSpread) {
		carGrossSpread.setPage(page);
		page.setList(carGrossSpreadDao.selectCarGrossSpreadList(carGrossSpread));
		return page;
	}
	
	/**
	 * 根据借款期限、产品类型获取总费率
	 * 2016年2月25日
	 * By 陈伟东
	 * @param deadlineCode
	 * @param productTypeCode
	 * @return
	 */
	public Double getCarGrossSpread(String deadlineCode,String productTypeCode,String loanCode) {
		CarGrossSpread carGrossSpread = new CarGrossSpread();
		carGrossSpread.setDictDeadline(deadlineCode);
		carGrossSpread.setDictProductType(productTypeCode);
		//根据loanCode来确定使用哪个类型的费率
		String contractVer = "1.4";
		CarCustomerConsultationDao carCustomerConsultationDao = SpringContextHolder.getBean(CarCustomerConsultationDao.class);
		CarCustomerConsultation carCustomerConsultation = carCustomerConsultationDao.selectByLoanCode(loanCode);
		if(carCustomerConsultation!=null){
			Date consultTime = carCustomerConsultation.getCreateTime();
	  	    Date onLineDate = DateUtils.convertStringToDate(CarSystemConfigConstant.CAR_LOAN_ONLINE_DATE);
			Date onLineDate16 = DateUtils.convertStringToDate(CarSystemConfigConstant.CAR_LOAN_ONLINE_DATE_16);
	  	    List<Dict> dlist = DictCache.getInstance().getListByType("jk_car_contract_version");
	  	    if(null!=dlist&&dlist.size()>0){
				if(DateUtils.dateAfter(consultTime, onLineDate16)){//1.6合同版本
					contractVer = dlist.get(dlist.size() - 1).getLabel();//1.6
				}else {
					if (DateUtils.dateAfter(consultTime, onLineDate)) {//根据咨询时间来判断 合同版本号
						contractVer = dlist.get(dlist.size() - 2).getLabel();//1.5
					} else {//老版本号1.4
						contractVer = dlist.get(dlist.size() - 3).getLabel();//1.4
					}
				}
	  		}
		}
  	    //end
		carGrossSpread.setRateType(contractVer);
		List<CarGrossSpread> selectCarGross = carGrossSpreadDao.selectCarGross(carGrossSpread);
		if(selectCarGross != null && selectCarGross.size() > 0){
			return selectCarGross.get(0).getGrossRate().doubleValue();
		}
		return null;
	}
	
	/**
	 * 		查询车借总费率数量
	 * @param carGrossSpread
	 * @return 车借总费率数量
	 */
	public int findCarGrossSpreadCount(CarGrossSpread carGrossSpread) {
		return carGrossSpreadDao.findCarGrossSpreadCount(carGrossSpread);
	}
	
	/**
	 * 		根据rateId查询一条车借总费率
	 * @param rateId
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarGrossSpread findByRateId(String rateId){
		return carGrossSpreadDao.findByRateId(rateId);
	}
	
	/**
	 * 总费率关联城市信息
	 * @param rateId 总费率
	 * @param citys 关联城市信息
	 * @return 成功条数
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int grossSpreadAddCitys(String rateId,String citys){
		int count = 0;
		try{
			CarSpreadProvinceCityRelation temp=new  CarSpreadProvinceCityRelation();
			for(String city:citys.split(",")){
				temp = new CarSpreadProvinceCityRelation();
				temp.setProvinceCityId(city);//区域编码
				temp.setRateId(rateId);//  费率ID
				
				//检查是否存在
				List<CarSpreadProvinceCityRelation> ls= carSpreadProvinceCityRelationDao.selectCarSpreadProvinceCityRelation(temp);
				if(ls == null  ||  ls.size() == 0){
					temp.preInsert();
					carSpreadProvinceCityRelationDao.insert(temp);
					count++;
				}
			}
		}catch(Exception e){
			logger.error("总费率关联城市信息出错",e);
		}
		
		return count;
	}
	
	
	/**
	 * 总费率关联城市信息
	 * @param rateId 总费率
	 * @param citys 关联城市信息
	 * @return 成功条数
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int batchDeleteCitys(String linkIds){
		int count = 0;
		try{
			for(String id : linkIds.split(",")){
				carSpreadProvinceCityRelationDao.deleteById(id);
				count++;
			}
		}catch(Exception e){
			logger.error("删除关联城市信息出错",e);
		}
		
		return count;
	}
	
	
	/**
	 * 		修改一条车借总费率
	 * @param carGrossSpread
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateCarGrossSpread(CarGrossSpread carGrossSpread){
		carGrossSpread.preInsert();
		carGrossSpreadDao.updateCarGrossSpread(carGrossSpread);
	}

	/**
	 * 		启用、停用 一条或者多条车借总费率状态
	 * @param rateId
	 * @param mark:标识
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateDictInitiate(String rateId, String mark){
		if(rateId != null && !"".equals(rateId)){
			if(mark.equals("1")){
				String[] temp = rateId.split(",");
				for (String id : temp) {
					carGrossSpreadDao.updateDictInitiate0(id);
				}
			}else{
				String[] temp2 = rateId.split(",");
				for (String id : temp2) {
					carGrossSpreadDao.updateDictInitiate1(id);
				}
			}
		}
	}
	
	/**
	 * 		根据rateId进入 城市分配页面，带回数据
	 * @param rateId
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarGrossSpread showSpreadProvinceCity(String rateId){
		return carGrossSpreadDao.showSpreadProvinceCity(rateId);
	}
	
	/**
	 * 	根据rateId查找关联城市信息
	 * @param rateId 总费率ID
	 * @return 关联城市信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarSpreadProvinceCityRelationEx> getCarSpreadProvinceCityRelationByRateId(String rateId){
		return carSpreadProvinceCityRelationDao.getCarSpreadProvinceCityRelationByRateId(rateId);
	}
	
	/**
	 * 查找关联城市信息总费率信息
	 * @param grossSpreadEx 总费率扩展信息  <br>
	 * 必须包含： <br>
	 * 区域编码： provinceCityId <br>
	 * 产品类型：dictProductType <br> 
	 * 总费率期限：dictDeadline
	 * @return 关联城市信息总费率信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarGrossSpread getCarGrossSpreadByCarGrossSpreadEx(CarGrossSpreadEx grossSpreadEx){
		CarGrossSpread carGrossSpread = null;
		logger.info("===================查询产品总费率===================");
		logger.info("-------------------区域编码: "+grossSpreadEx.getProvinceCityId() );
		logger.info("-------------------产品类型: "+grossSpreadEx.getDictProductType() );
		logger.info("-------------------产品期限: "+grossSpreadEx.getDictDeadline());
		try{
			carGrossSpread = carGrossSpreadDao.getCarGrossSpreadByCarGrossSpreadEx(grossSpreadEx);
		}catch(Exception e){
			logger.error("查询产品总费率异常",e);
		}
		return carGrossSpread;
	}
	
	
	
	/**
	 * 		用指定分隔符分隔字符串
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List<Integer> segmentationStr2Integer(String str,String separator){
		List<Integer> result = new ArrayList<Integer>();
		if(CarGrossSpreadService.isEmpty(str))
			return result;
		StringTokenizer token = new StringTokenizer(str, separator);
		while (token.hasMoreElements()) {
			result.add(Integer.parseInt(token.nextToken()));
		}
		return result;
	}
	
	/**
	 * 		判断字符串是否为空(包含null与"")
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str))
			return true;
		return false;
	}
	
}
