/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.serviceImageService.java
 * @Create By 张灏
 * @Create In 2016年3月15日 下午8:25:26
 */
package com.creditharmony.loan.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.PropertyUtil;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.core.loan.type.SystemFromFlag;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.common.consts.FileDiskFlag;
import com.creditharmony.loan.common.dao.FileDiskInfoDao;
import com.creditharmony.loan.common.entity.FileDiskInfo;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.entity.ex.ImageParam;
import com.creditharmony.loan.common.utils.HJImagePlatformUtil;

/**
 * 影像插件Service
 * 
 * @Class Name ImageService
 * @author 张灏
 * @Create In 2016年3月15日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ImageService {

	private final static String UID = PropertyUtil.getStrValue(
			"application.properties", "loan.image.UID", "admin");
	private final static String PWD = PropertyUtil.getStrValue(
			"application.properties", "loan.image.PWD", "111");
	private final static String APPID = PropertyUtil.getStrValue(
			"application.properties", "loan.image.APPID", "AB");

	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	@Autowired
	private FileDiskInfoDao fileDiskInfoDao;

	/**
	 * 标签方法，获取图片的路径
	 *
	 * @author zhanghao
	 * @Create In 2016年03月15日
	 * @param stepName
	 * @param loanCode
	 * @return String
	 *
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getImageUrl(String stepName, String loanCode) {
		String imageUrlModel = PropertyUtil.getStrValue(
				"application.properties", "loan.image.url", "");//影像地址
		String imageParam = PropertyUtil.getStrValue("application.properties",
				"loan.image.param", "");//影像参数
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanCode", loanCode);
		LoanInfo loanInfo = loanInfoDao.selectByLoanCode(map);
		//申请时间
		String applyTime = DateUtils.formatDate(loanInfo.getLoanApplyTime(),
				"yyyyMMdd");
		// 进件时间
		String queryTime = DateUtils.formatDate(loanInfo.getCustomerIntoTime(),
				"yyyyMMdd");
		
		// 迁移数据使用申请时间作为影像查询时间
		if (!SystemFromFlag.THREE.getCode()
				.equals(loanInfo.getDictSourceType())) {
			queryTime = applyTime;
		} 
		
		Map<String, Object> queryParam = new HashMap<String, Object>();
		if (StringHelper.isNotEmpty(queryTime)) {
			queryParam.put("queryTime", queryTime);
		} else {
			queryTime =DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			queryParam.put("queryTime",
					DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		}
		if (SystemFromFlag.THREE.getCode().equals(loanInfo.getDictSourceType())) {
			queryParam.put("sysFlag", FileDiskFlag.CHP_THREE);
		} else {
			queryParam.put("sysFlag", FileDiskFlag.CHP_TWO);
		}
		
		FileDiskInfo fileDeiskInfo = fileDiskInfoDao.getByParam(queryParam);
		//影像磁盘信息不存在的情况下使用当前时间
		if (ObjectHelper.isEmpty(fileDeiskInfo)) {
			queryParam.put("queryTime",
					DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
			fileDeiskInfo = fileDiskInfoDao.getByParam(queryParam);
		}
		User user = UserUtils.getUser();
		ImageParam param = new ImageParam();
		param.setImageParam(imageParam);
		param.setUid(fileDeiskInfo.getuId() == null ? UID : fileDeiskInfo
				.getuId());
		param.setPassword(fileDeiskInfo.getSysPwd() == null ? PWD
				: fileDeiskInfo.getSysPwd());
		param.setAppID(fileDeiskInfo.getAppId() == null ? APPID : fileDeiskInfo
				.getAppId());
		param.setUrlMode(imageUrlModel);
		param.setQueryTime(queryTime);
		param.setStepName(stepName);
		
		param.setUserID(user.getId());
		param.setUserName(user.getName());
		param.setBusiSerialNO(loanCode);
		param.setOrgID(fileDeiskInfo.getOrgId());
		param.setOrgName(fileDeiskInfo.getOrgName());
		param.setObjectName(fileDeiskInfo.getFlagHj());
		param.setFlagHc(fileDeiskInfo.getFlagHc());
		param.setMy(fileDeiskInfo.getSecretKey());
		
		FlowStep step = FlowStep.parseByName(stepName);
		String stepCode = step.getCode();
		Map map1 = new HashMap();
		if(FlowStep.LAGE_AMOUNT_VIEW.getCode().equals(stepCode))
		{
			map1.put("indexFlag", fileDeiskInfo.getFlagHc());
		}
		else
		{
			map1.put("indexFlag", fileDeiskInfo.getFlagHj());
		}
		map1.put("stepName", stepCode);
		List<Map<String,String>> list = fileDiskInfoDao.getLevel(map1);
		if(!list.isEmpty()){
			param.setFileLevel(list.get(0).get("level"));
			param.setRight(list.get(0).get("file_right"));
			String url = HJImagePlatformUtil.appendUrlParm(param);
			return url;
		}
		return "";
	}
}
