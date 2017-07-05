/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsHJImagePlatformUtil.java
 * @Create By 张灏
 * @Create In 2016年3月15日 下午5:58:33
 */
package com.creditharmony.loan.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.dao.RoleDao;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.entity.ex.ImageParam;

/**
 * 影像插件权限配置
 * 
 * @Class Name HJImagePlatformUtil
 * @author 张灏
 * @Create In 2016年3月15日
 */
public class HJImagePlatformUtil {

    /**
	 * 生成影像插件请求地址
	 *
	 * @author zhanghao
	 * @Create In 2016年03月21日
	 * @param param
	 *            Right 新增权限(1111110操作权限，0100000查看权限)、查看权限、删除权限、修改权限、打印权限、批注权限、管理员权限。0无权,1有权
	 * 
	 */
	public static String appendUrlParm(ImageParam param) {

		StringBuffer url = new StringBuffer();
		url.append(param.getUrlMode());
		String stepName = param.getStepName();
		FlowStep step = FlowStep.parseByName(stepName);
		String stepCode = step.getCode();
		String flag = null;

		if (FlowStep.LANUCH_RE.getCode().equals(stepCode)) {
			//param.setFileLevel("1");
			//param.setRight("1111110");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		} else if (FlowStep.FILE_UPLOAD.getCode().equals(stepCode)) { // 资料上传
			//param.setFileLevel("1");
			//param.setRight("1111110");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		} else if (FlowStep.STORE_RECHECK.getCode().equals(stepCode)) { // 门店复核
			//param.setFileLevel("1");
			//param.setRight("1111110");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		} else if (FlowStep.RECONSIDER_BACK.getCode().equals(stepCode)) { // 复议退回
			//param.setFileLevel("1");
			//param.setRight("1111110");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.AUDIT_RATE.getCode().equals(stepCode)) { // 利率审核
			//param.setFileLevel("1,2,3,4");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.CONFIRM_SIGN.getCode().equals(stepCode)) { // 确认签署
			//param.setFileLevel("1,2,3,4");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.CONTRACT_CREATE.getCode().equals(stepCode)) { // 合同制作
			//param.setFileLevel("1,2,3,4");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.CONTRACT_SIGN.getCode().equals(stepCode)) { // 合同签订
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("1100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.CONTRACT_AUDIT_CONTRACT.getCode().equals(stepCode)) { // 合同审核_合同查看
			//param.setFileLevel("5,6,7,8");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.LAGE_AMOUNT_VIEW.getCode().equals(stepCode)) { // 合同审核_大额审批
            //param.setFileLevel("8");
           // param.setRight("0100000");
            flag = param.getFlagHc();
            url.append(replaceURLParam(param,flag));

        }else if(FlowStep.PROTOCOL_VIEW.getCode().equals(stepCode)){       // 协议查看
            //param.setFileLevel("5,6,7,8");
            //param.setRight("0100000");
            flag = param.getObjectName();
            url.append(replaceURLParam(param,flag)); 
        } else if (FlowStep.LOAN_CONFIRM.getCode().equals(stepCode)) { // 放款明细确认
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.DISCARD.getCode().equals(stepCode)) { // 分配卡号
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.LOAN.getCode().equals(stepCode)) { // 放款
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.LOAN_AUDIT.getCode().equals(stepCode)) { // 放款确认
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));

		} else if (FlowStep.LOAN_PROCESSED.getCode().equals(stepCode)) { // 放款已办
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		} else if (FlowStep.TRANSLATE_APPLY.getCode().equals(stepCode)) {  // 交割申请
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("1111110");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		} else if (FlowStep.TRANSLATE_WORKITEM.getCode().equals(stepCode)) {  // 交割待办
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("1111110");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		} else if (FlowStep.SECRET_SHOOTING.getCode().equals(stepCode)) {   // 暗访
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("1111110");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		}
		else if (FlowStep.OUT_VISIT.getCode().equals(stepCode)) {   // 外访
			//param.setFileLevel("2,3");
			//param.setRight("1111110");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		}else if(FlowStep.GOLDCREDIT_RETURN.getCode().equals(stepCode))
		{
			//param.setFileLevel("1,2,3,4,5,6,7,8");
			//param.setRight("0100000");
			flag = param.getObjectName();
			url.append(replaceURLParam(param,flag));
		}else if(FlowStep.IMAGE_VIEW.getCode().equals(stepCode)){        // 影像查看
		    //param.setFileLevel("1");
           // param.setRight("0100000");
            flag = param.getObjectName();
            url.append(replaceURLParam(param,flag)); 
		}else if(FlowStep.TEAM_MANAGER_IMAGE_VIEW.getCode().equals(stepCode)){
		
		    flag = param.getObjectName();
		    url.append(replaceURLParam(param,flag));
		}
		return url.toString();
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
		String my = param.getMy();
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
		String urlParam = appendURLParam(param);
		url += urlParam; //追加url
		url += my;

		return url;
	}
	/**
	 * stepName是资料上传、门店复核、复议退回、合同签订时追加url
	 * 		如果当前登录用户所属角色不包含门店副理并且stepName是合同签订时：
	 * 				则FILELEVEL:1,4,7,8;只有查看权限---其他权限请查询数据库
	 * 		如果当前登录用户所属角色包含门店副理并且stepName是合同签订时：
	 * 				则FILELEVEL:1,2,3,4,7,8;只有查看权限---其他权限请查询数据库
	 * 		如果当前登录用户所属角色包含门店副理并且stepName是是资料上传、门店复核、复议退回时：
	 * 				则FILELEVEL:2,3;只有查看权限(外访资料文件夹:一次外访和多次外访文件夹)---其他权限请查询数据库
	 * @param param
	 * @return
	 */
	private static String appendURLParam(ImageParam param) {
		String url="";
		String stepName = param.getStepName();
		FlowStep step = FlowStep.parseByName(stepName);
		String stepCode = step.getCode();
		if(stepCode.equals("1") || stepCode.equals("2") || stepCode.equals("3") || stepCode.equals("7")){
			String userID = param.getUserID();
			RoleDao roleDao=SpringContextHolder.getBean(RoleDao.class);
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("userId", userID);
			params.put("deleteFlag", "0");
			List<Role> roleList=roleDao.findByUserId(params);
			if(roleList!=null && roleList.size()>0){
				for (Role role : roleList) {
					if(role.getId().equals(LoanRole.STORE_ASSISTANT.id)){
						String info2="&info2=BUSI_SERIAL_NO:"+param.getBusiSerialNO()+";"
								+ "OBJECT_NAME:"+param.getObjectName()+";"
								+ "QUERY_TIME:"+param.getQueryTime()+";"
								+ "FILELEVEL:2,3;"
								+ "RIGHT:0100000";
						if(stepCode.equals("7")){ //对于门店副理角色在待上传合同节点...文件夹只有查看权限
							info2="&info2=BUSI_SERIAL_NO:"+param.getBusiSerialNO()+";"
								+ "OBJECT_NAME:"+param.getObjectName()+";"
								+ "QUERY_TIME:"+param.getQueryTime()+";"
								+ "FILELEVEL:1,2,3,4,7,8;"
								+ "RIGHT:0100000";
						}
						url=info2;
						break;
					}
				}
				//对于除门店副理以外的其他角色 在待上传合同节点...文件夹只有查看权限
				if(StringUtils.isEmpty(url)){
					if(stepCode.equals("7")){
						String info2="&info2=BUSI_SERIAL_NO:"+param.getBusiSerialNO()+";"
								+ "OBJECT_NAME:"+param.getObjectName()+";"
								+ "QUERY_TIME:"+param.getQueryTime()+";"
								+ "FILELEVEL:1,4,7,8;"
								+ "RIGHT:0100000";
						url=info2;
					}
				}
			}
			
		}
		return url;
	}
}
