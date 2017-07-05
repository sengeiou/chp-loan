package com.creditharmony.loan.aops.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.core.propertiess.GetProperties;
import com.creditharmony.core.thd.AbsThrdSendMsg;

public class ThrdSendMsg extends AbsThrdSendMsg {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String strCname = this.getClass().getName();
	
	public void run() {
		String strFname = " run : ";
		String strFlg = strCname + strFname + Thread.currentThread().getId() + ":" + System.currentTimeMillis();
		try{
			if(PropetiesManage.pptMq==null){
				GetProperties obj = new GetProperties();
//				System.out.println("ThrdSendMsg1 Run----" + this.getClass().getResource("").getFile());
//				System.out.println("ThrdSendMsg2 Run----" + this.getClass().getResource("").getPath());
				String strPath = this.getClass().getResource("").getPath();
				if(strPath!=null && strPath.trim().length()>0
						&& strPath.indexOf("WEB-INF")>0){
					int intOdx = strPath.indexOf("WEB-INF");
					strPath = strPath.substring(0, intOdx) + "WEB-INF/classes/activeMq.properties";
					PropetiesManage.pptMq = obj.disGet(strPath);
				}
			}
			super.sendMsg(strFlg, PropetiesManage.pptMq);
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

}
