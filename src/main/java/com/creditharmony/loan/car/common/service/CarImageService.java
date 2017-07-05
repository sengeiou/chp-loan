/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.serviceImageService.java
 * @Create By 张灏
 * @Create In 2016年3月15日 下午8:25:26
 */
package com.creditharmony.loan.car.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.PropertyUtil;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.CarExtendLoanSteps;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.users.dao.OrgDao;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.common.dao.FileDiskInfoDao;
import com.creditharmony.loan.common.entity.FileDiskInfo;
import com.creditharmony.loan.common.entity.ex.ImageParam;

/**
 * 影像插件Service
 * 
 * @Class Name CarImageService
 * @author 葛志超
 * @Create In 2016年5月25日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarImageService {

	private final static String UID = PropertyUtil.getStrValue(
			"application.properties", "loan.image.UID", "admin");
	private final static String PWD = PropertyUtil.getStrValue(
			"application.properties", "loan.image.PWD", "111");
	private final static String APPID = PropertyUtil.getStrValue(
			"application.properties", "loan.image.APPID", "AB");

	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	@Autowired
	private FileDiskInfoDao fileDiskInfoDao;
	
	@Autowired
	private CarContractDao carContractDao;
	
	private static OrgDao orgDao = SpringContextHolder.getBean(OrgDao.class);

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
	public String getImageUrl(String stepName, String loanCode) {
		String imageUrlModel = PropertyUtil.getStrValue(
				"application.properties", "car.image.url", "");
		String imageParam = PropertyUtil.getStrValue("application.properties",
				"car.image.param", "");
		User user = UserUtils.getUser();
		CarLoanSteps step = CarLoanSteps.parseByName(stepName);
		String stepCode = step.getCode();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("queryTime",
				DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		
		CarLoanInfo loanInfo = carLoanInfoDao.selectByLoanCode(loanCode);
		if(null!=loanInfo.getDictsourcetype()&&loanInfo.getDictsourcetype().equals("2")){
			queryParam.put("sysFlag", "3");
		}else if (null!=loanInfo.getDictsourcetype()&&loanInfo.getDictsourcetype().equals("3")){
			queryParam.put("sysFlag", "6");
		}else{
			queryParam.put("sysFlag", "6");
		}
		FileDiskInfo fileDeiskInfo = fileDiskInfoDao.getByParam(queryParam);
		
		String queryTime = DateUtils.formatDate(loanInfo.getCustomerIntoTime(),
				"yyyyMMdd");
		ImageParam param = new ImageParam();
		param.setImageParam(imageParam);
		param.setUid(UID);
		param.setPassword(PWD);
		param.setAppID(APPID);
		param.setUrlMode(imageUrlModel);
		param.setQueryTime(queryTime);
		param.setStepName(stepName);
		
		param.setUserID(user.getId());
		param.setUserName(user.getName());
		param.setBusiSerialNO(loanCode);
		param.setOrgID("1");
		param.setOrgName("fenbu");
		param.setObjectName(fileDeiskInfo.getFlagHj());
		param.setFlagHc(fileDeiskInfo.getFlagHc());
		if("2".equals(loanInfo.getConditionalThroughFlag())){
			return appendUrlParm("2",stepCode,param); //附条件 检索
		}else{
			return appendUrlParm("1",stepCode,param);
		}
		
	}
	
	
	public String getExendImageUrl(String stepName, String loanCode) {
		String imageUrlModel = PropertyUtil.getStrValue(
				"application.properties", "car.image.url", "");
		String imageParam = PropertyUtil.getStrValue("application.properties",
				"car.image.param", "");
		User user = UserUtils.getUser();
		
		CarExtendLoanSteps step = CarExtendLoanSteps.parseByName(stepName);
		String stepcode ="";
		if(null!=step){
			 stepcode = step.getCode();
		}else{
			 stepcode = "20";
		}
		
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("queryTime",
				DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		CarLoanInfo loanInfo = carLoanInfoDao.selectByLoanCode(loanCode);
		int CountCarExtend = carContractDao.getExtendCountByLoanCodeSign(loanCode);
		if(null!=loanInfo.getDictsourcetype()&&loanInfo.getDictsourcetype().equals("2")){
			queryParam.put("sysFlag", "3");
		}else if (null!=loanInfo.getDictsourcetype()&&loanInfo.getDictsourcetype().equals("3")){
			queryParam.put("sysFlag", "6");
		}else{
			queryParam.put("sysFlag", "6");
		}
		FileDiskInfo fileDeiskInfo = fileDiskInfoDao.getByParam(queryParam);
		
		String queryTime = DateUtils.formatDate(loanInfo.getCustomerIntoTime(),
				"yyyyMMdd");
		if(queryTime==null){
			 queryTime = DateUtils.formatDate(new Date(),
						"yyyyMMdd");
		}
		ImageParam param = new ImageParam();
		param.setImageParam(imageParam);
		param.setUid(UID);
		param.setPassword(PWD);
		param.setAppID(APPID);
		param.setUrlMode(imageUrlModel);
		param.setQueryTime(queryTime);
		param.setStepName(stepName);
		
		param.setUserID(user.getId());
		param.setUserName(user.getName());
		param.setBusiSerialNO(loanCode);
		param.setOrgID("1");
		param.setOrgName("fenbu");
		param.setObjectName(fileDeiskInfo.getFlagHj());
		param.setFlagHc(fileDeiskInfo.getFlagHc());
		return appendUrlParm(CountCarExtend+"",stepcode,param);
	}
	
	/**
	 * 生成影像插件请求地址
	 *
	 * @author zhanghao
	 * @Create In 2016年03月21日
	 * @param param
	 *            Right 新增权限(1111110操作权限，0100000查看权限)、查看权限、删除权限、修改权限、打印权限、批注权限、管理员权限。0无权,1有权
	 * 
	 */
	public static String appendUrlParm(String conditionalThroughFlag,String stepCode ,ImageParam param) {

		//CarLoanInfo loanInfo = carLoanInfoDao.selectByLoanCode(param.getBusiSerialNO());
		StringBuffer url = new StringBuffer();
		url.append(param.getUrlMode());
		String flag = null;
		String [] level1={"1","4","5","6","7","8"};
		String [] level2={"2","3"};
		String [] level3={"9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		String [] level4={"30","31","32"};
		if(isHave(level1,stepCode)){
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			flag = param.getObjectName();
			if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
				param.setFileLevel("1");
				param.setRight("1111110");
			}else{
				param.setFileLevel("1");
				param.setRight("0100000");
			}
			url.append(replaceURLParam(param,flag));
			return url.toString();
		}else if(isHave(level2,stepCode)){
			flag = param.getObjectName();
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
				param.setFileLevel("2");
				param.setRight("1111110");
			}else if("6100000001".equals(orgType)){
				param.setFileLevel("2");
				param.setRight("0100000");
			}
			url.append(replaceURLParam(param,flag));
			/*url.append("&info2=BUSI_SERIAL_NO:");
			url.append(param.getBusiSerialNO());
			url.append(";OBJECT_NAME:");
			url.append(param.getFlagHc());
			url.append(";QUERY_TIME:");
			url.append(param.getQueryTime());
			url.append(";FILELEVEL:1");
			url.append(";RIGHT:0100000");*/
			return url.toString();
			
		}else if(isHave(level3,stepCode)){
			flag = param.getObjectName();
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			if("11".equals(stepCode)&&"2".equals(conditionalThroughFlag)){
				if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
					param.setFileLevel("3,4");
					param.setRight("1111110");
				}else{
					param.setFileLevel("3,4");
					param.setRight("0100000");
				}
			}else{
				if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
					param.setFileLevel("4");
					param.setRight("1111110");
				}else{
					param.setFileLevel("4");
					param.setRight("0100000");
				}
			}
			url.append(replaceURLParam(param,flag));
			/*url.append(replaceURLParam(param,flag));
			url.append("&info2=BUSI_SERIAL_NO:");
			url.append(param.getBusiSerialNO());
			url.append(";OBJECT_NAME:");
			url.append(param.getFlagHc());
			url.append(";QUERY_TIME:");
			url.append(param.getQueryTime());
			url.append(";FILELEVEL:1");
			url.append(";RIGHT:0100000");*/
			return url.toString();
			
		}else if(isHave(level4,stepCode)){
			flag = param.getObjectName();
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
				if("1".equals(conditionalThroughFlag)){
					param.setFileLevel("5");
				}else if("2".equals(conditionalThroughFlag)){
					param.setFileLevel("5,6");
				}else if("3".equals(conditionalThroughFlag)){
					param.setFileLevel("5,6,7");
				}else if("4".equals(conditionalThroughFlag)){
					param.setFileLevel("5,6,7,8");
				}else if("5".equals(conditionalThroughFlag)){
					param.setFileLevel("5,6,7,8,9");
				}else {
					param.setFileLevel("1");
				}
				param.setRight("1111110");
			}else{
				if("1".equals(conditionalThroughFlag)){
					param.setFileLevel("5");
				}else if("2".equals(conditionalThroughFlag)){
					param.setFileLevel("5,6");
				}else if("3".equals(conditionalThroughFlag)){
					param.setFileLevel("5,6,7");
				}else if("4".equals(conditionalThroughFlag)){
					param.setFileLevel("5,6,7,8");
				}else if("5".equals(conditionalThroughFlag)){
					param.setFileLevel("5,6,7,8,9");
				}
				else {
					param.setFileLevel("1");
				}
				param.setRight("0100000");
			}
			url.append(replaceURLParam(param,flag));
			return url.toString();
			
		}else{
			flag = param.getObjectName();
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			String orgId = currentUser.getDepartment().getId();
			Org org = orgDao.get(orgId);
			String orgType = org != null ? org.getType() : "";
			if(LoanOrgType.STORE.key.equals(orgType)||LoanOrgType.TEAM.key.equals(orgType)){
				param.setFileLevel("1,2,3");
				param.setRight("1111110");
			}else if("6100000001".equals(orgType)||"6100000003".equals(orgType)){
				param.setFileLevel("1,2,3");
				param.setRight("0100000");
			}
			param.setFileLevel("1");
			param.setRight("0100000");
			url.append(replaceURLParam(param,flag));
			/*url.append(replaceURLParam(param,flag));
			url.append("&info2=BUSI_SERIAL_NO:");
			url.append(param.getBusiSerialNO());
			url.append(";OBJECT_NAME:");
			url.append(param.getFlagHc());
			url.append(";QUERY_TIME:");
			url.append(param.getQueryTime());
			url.append(";FILELEVEL:1");
			url.append(";RIGHT:0100000");*/
			return url.toString();
		}

	}

	public static String replaceURLParam(ImageParam param,String flag) {
		String UID = param.getUid();
		String PWD = param.getPassword();
		String appID = param.getAppID();
		String userID = param.getUserID();
		String userName = param.getUserName();
		String busiSerialNo = param.getBusiSerialNO();
		String queryTime = param.getQueryTime();
		String orgName = param.getOrgName();
		String orgID = param.getOrgID();
		String objectName = flag;
		String filelevel = param.getFileLevel();
		String right = param.getRight();
		String url = param.getImageParam();
		url = url.replace("uid", UID);
		url = url.replace("pwd", PWD);
		url = url.replace("appID", appID);
		url = url.replace("userID", userID);
		url = url.replace("userName", userName);
		url = url.replace("orgID", orgID);
		url = url.replace("orgName", orgName);
		url = url.replace("busiSerialNo", busiSerialNo);
		url = url.replace("objectName", objectName);
		url = url.replace("queryTime", queryTime);
		url = url.replace("filelevel", filelevel);
		url = url.replace("right", right);

		return url;
	}
	
	public static boolean isHave(String [] slist,String code){
		for (String string : slist) {
			if(string.equals(code)){
				return true;
			}
		}
		return false;
	}


	public String getLargeAmountImageUrl(String string, String loancode) {
		String imageUrlModel = PropertyUtil.getStrValue(
				"application.properties", "car.image.url", "");
		String imageParam = PropertyUtil.getStrValue("application.properties",
				"car.image.param", "");
		User user = UserUtils.getUser();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("queryTime",
				DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		queryParam.put("sysFlag", "3");
		FileDiskInfo fileDeiskInfo = fileDiskInfoDao.getByParam(queryParam);
		
		CarLoanInfo loanInfo = carLoanInfoDao.selectByLoanCode(loancode);
		String queryTime = DateUtils.formatDate(loanInfo.getCustomerIntoTime(),
				"yyyyMMdd");
		ImageParam param = new ImageParam();
		param.setImageParam(imageParam);
		param.setUid(UID);
		param.setPassword(PWD);
		param.setAppID(APPID);
		param.setUrlMode(imageUrlModel);
		param.setQueryTime(queryTime);
		param.setUserID(user.getId());
		param.setUserName(user.getName());
		param.setBusiSerialNO(loancode);
		param.setOrgID("1");
		param.setOrgName("fenbu");
		param.setObjectName(fileDeiskInfo.getFlagHj());
		param.setFlagHc(fileDeiskInfo.getFlagHc());
		param.setFileLevel("1");
		param.setRight("0100000");
		StringBuffer url = new StringBuffer();
		url.append(param.getUrlMode());
		url.append(replaceURLParam(param,param.getFlagHc()));
		return url.toString();
	}
}
