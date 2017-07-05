package com.creditharmony.loan.aops.aspects;

import java.util.Properties;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.creditharmony.core.logs.LogsSysFlg;
import com.creditharmony.core.propertiess.GetProperties;
import com.creditharmony.core.proxys.CtlrAspectProcess;
import com.creditharmony.core.proxys.ThreadLocalManage;
import com.creditharmony.core.thd.pools.ThreadPool01;
import com.creditharmony.core.thd.runs.ThreadRun01;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;

@Component
@Aspect
public class CtlrAspect {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String strCname = this.getClass().getName();
	private static final String strSys = LogsSysFlg.strFlg_MqReceiveQueue_Chploan;
	private static final String strAopFlg = LogsSysFlg.strFlg_AopFlg_Ctrl;
	private static Properties pptSysTarget = null; 
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Order(1)
	@Before("" 
			//信借 
			+ "execution(* com.creditharmony.loan.telesales.web.TelesalesCustomerManagementController.findTelesaleReconsiderList(..))" 
			+ "|| execution(* com.creditharmony.loan.telesales.web.TelesalesCustomerManagementController.findTelesaleCustomerHisList(..))" 
			+ "|| execution(* com.creditharmony.loan.telesales.web.TelesalesConsultController.openTelesalesCustomerForm(..))" 
			+ "|| execution(* com.creditharmony.loan.borrow.account.web.RepayAccountController.accountManageList(..))" 
			+ "|| execution(* com.creditharmony.loan.channel.goldcredit.web.GCCeilingController.init(..))" 
			+ "|| execution(* com.creditharmony.loan.borrow.trusteeship.web.KingOpenAccountController.getTaskItems(..))" 
			+ "|| execution(* com.creditharmony.loan.borrow.grant.web.GrantSureController.grantSureFetchTaskItems(..))" 
			+ "|| execution(* com.creditharmony.loan.borrow.serve.web.CustomerServeController.waitSendList(..))" 
			+ "|| execution(* com.creditharmony.loan.borrow.serve.web.CustomerServeController.alreadyHandleList(..))" 
			+ "|| execution(* com.creditharmony.loan.borrow.payback.web.RemindRepaymentAgencyController.remindRepayByStore(..))" 
			+ "|| execution(* com.creditharmony.loan.borrow.payback.web.RemindRepaymentAgencyController.remindRepaymentAgencyList(..))" 
			+ "|| execution(* com.creditharmony.loan.borrow.borrowlist.web.BorrowListController.fetchTaskItems(..))" 
			//车借 
			+ "|| execution(* com.creditharmony.loan.car.common.web.CarLoanWorkItemsController.fetchTaskItems(..))" 
			+ "|| execution(* com.creditharmony.loan.car.common.web.CarHistoryController.doneList(..))" 
			+ "|| execution(* com.creditharmony.loan.car.carConsultation.web.CarConsultationController.toAddCarConsultation(..))" 
			+ "|| execution(* com.creditharmony.loan.car.carApply.web.CarApplyTaskController.fetchTaskItems(..))" 
			+ "|| execution(* com.creditharmony.loan.car.carConsultation.web.CarLoanAdvisoryBacklogController.CarLoanAdvisoryBacklog(..))" 
			+ "|| execution(* com.creditharmony.loan.car.carContract.web.CarFirstDeferController.selectDeferList(..))" 
			+ "|| execution(* com.creditharmony.loan.car.carExtend.web.CarExtendWorkItemsController.fetchTaskItems(..))" 
			+ "")
	public void beforeMethod(JoinPoint joinpoint) {
		String strFname = " beforeMethod : ";
		try {
			if(PropetiesManage.booAopFlgGlobal){
				String strSystarget = null;
				if(pptSysTarget==null){
					disGetppt();
				} 
				if(pptSysTarget.containsKey("serverTarget")){
					strSystarget = pptSysTarget.get("serverTarget")==null? null:pptSysTarget.get("serverTarget").toString();
					strSystarget = "_"+strSystarget;
				}
				
				
				disCreateManage();
				User userObj = UserUtils.getUser();
				CtlrAspectProcess objCap = new CtlrAspectProcess(strFname);
				objCap.disBefore(strFname
						, strSys+strSystarget
						, strAopFlg
						, joinpoint
						, userObj
						, ThreadLocalManage.tdl
						, ThreadPoolManage.thdpMang_MsgSend
						, ThreadRunManage.thdrMang_MsgSend);
				objCap = null;
			}
		} catch(Exception ex) {
			long lonFlg = System.currentTimeMillis();
			logger.error(strCname + strFname + ex + "||" + lonFlg);
			StackTraceElement[] subSte = ex.getStackTrace();
			for(int i=0; i<subSte.length; i++){
				logger.error(
						subSte[i].getClassName() 
						+ subSte[i].getMethodName() 
						+ ":" + subSte[i].getLineNumber() 
						+ "||" + lonFlg );
			}
		}
    }
    
	private void disGetppt(){
		String strPath = this.getClass().getResource("").getPath();
		if(strPath!=null && strPath.trim().length()>0
				&& strPath.indexOf("WEB-INF")>0){
			int intOdx = strPath.indexOf("WEB-INF");
			strPath = strPath.substring(0, intOdx) + "WEB-INF/classes/SysTarget.properties";
			GetProperties objPro = new GetProperties();
			pptSysTarget = objPro.disGet(strPath);
		}
	}
//	@SuppressWarnings({"unchecked","rawtypes"})
//	@Order(3)
//	@After("execution(* "
//			+ "com.creditharmony.sms.users.web.UserController.save(..))"
//			+ "|| execution(* com.creditharmony.sms.users.web.UserController.form(..))"
//			+ "|| execution(* com.creditharmony.sms.users.web.UserController.list(..))"
//			+ "")
//	public void afterMethod(JoinPoint joinpoint) {
//		String strFname = " afterMethod : ";
//		try{
//			CtlrAspectProcess objCap = new CtlrAspectProcess(strFname);
//			objCap.disAfter(strFname
//					, strSys
//					, strAopFlg
//					, joinpoint
//					, ThreadLocalManage.tdl
//					, ThreadPoolManage.thdpMang_MsgSend
//					, ThreadRunManage.thdrMang_MsgSend);
//			objCap = null;
//		} catch(Exception ex) {
//			long lonFlg = System.currentTimeMillis();
//			logger.error(strCname + strFname + ex + "||" + lonFlg);
//			StackTraceElement[] subSte = ex.getStackTrace();
//			for(int i=0; i<subSte.length; i++){
//				logger.error(
//						subSte[i].getClassName() 
//						+ subSte[i].getMethodName() 
//						+ ":" + subSte[i].getLineNumber() 
//						+ "||" + lonFlg );
//			}
//		}
//    }
//	
//	@SuppressWarnings({"unchecked","rawtypes"})
//	@Order(5)
//	@AfterThrowing (throwing="thrEx", pointcut="execution(* "
//			+ "com.creditharmony.sms.users.web.UserController.save(..))"
//			+ "|| execution(* com.creditharmony.sms.users.web.UserController.form(..))"
//			+ "|| execution(* com.creditharmony.sms.users.web.UserController.list(..))"
//			+ "")
//	public void AfterThrowingMethod(JoinPoint joinpoint, Throwable thrEx) {
//		String strFname = " AfterThrowing : ";
//		try{
//			User userObj = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
//			CtlrAspectProcess objCap = new CtlrAspectProcess(strFname);
//			objCap.disAfterThrowing(strFname
//					, strSys
//					, strAopFlg
//					, joinpoint
//					, userObj
//					, thrEx
//					, ThreadLocalManage.tdl
//					, ThreadPoolManage.thdpMang_MsgSend
//					, ThreadRunManage.thdrMang_MsgSend);
//			objCap = null;
//		} catch(Exception ex) {
//			long lonFlg = System.currentTimeMillis();
//			logger.error(strCname + strFname + ex + "||" + lonFlg);
//			StackTraceElement[] subSte = ex.getStackTrace();
//			for(int i=0; i<subSte.length; i++){
//				logger.error(
//						subSte[i].getClassName() 
//						+ subSte[i].getMethodName() 
//						+ ":" + subSte[i].getLineNumber() 
//						+ "||" + lonFlg );
//			}
//		}
//    }
	
	private void disCreateManage(){
		if(ThreadPoolManage.thdpMang_MsgSend==null){
			ThreadPoolManage.thdpMang_MsgSend = new ThreadPool01(500, ThrdSendMsg.class);
		}
		if(ThreadRunManage.thdrMang_MsgSend==null){
			ThreadRunManage.thdrMang_MsgSend = new ThreadRun01(5);
		}
	}
	
//	@Pointcut("execution(* com.creditharmony.sms.users.web.*.*(..))")
//	public void declareJoinPointExpression() {}
}
