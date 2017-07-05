package com.creditharmony.loan.utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
 

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.encdecShell.clinet.TcpClient;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer; 
 

@Service
public class PhoneSecretSerivice{
	
	@Resource
	private LoanCustomerDao customerDao;
	
	@Resource
	private LoanCoborrowerDao coborrowerDao;
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(PhoneSecretSerivice.class);
	private static Logger loggerSt = LoggerFactory.getLogger(PhoneSecretSerivice.class);
	private final static  String DBNAME ="loanxj";
	private final static  String SYS ="srdbloan";
	private final static  String OBJSIG ="qweasdzx1234";
 
	/**
	 * 客户表手机号加密
	 *  @param loanCode loan_code
		 * @param customerCode t_jk_loan_customer(id)
		 * @param createBy 创建人t_jk_loan_customer(create_By)
		 * @param createTime 创建时间t_jk_loan_customer(create_Time.getTime()) 
		 * @param mobileNums 手机号   格式"num,num,......"
		 * @param 身份证号
		 * @param tphoneNum 固定电话  t_jk_loan_customer(customer_tel)
		 * @param tableName 数据库表名 
		 * @param col 字段名称  格式"col,col......."
		 * 
		 * 
		 * 
		 *  * 自然人手机号加密
	 *  @param loanCode loan_code
		 * @param customerCode t_jk_loan_coborrower(id)
		 * @param createBy 创建人t_jk_loan_coborrower(create_By)
		 * @param createTime 创建时间t_jk_loan_coborrower(create_Time.getTime()) 
		 * @param mobileNums 手机号   格式"num,num,......"
		 * @param 身份证号 t_jk_loan_coborrower( cobo_cert_num
		 * @param tableName 数据库表名 
		 * @param col 字段名称  格式"col,col......."
		 * 
		 * 
		 * 依次类推
		 * 
	 * @author zhangman
	 * 
	 * @return 加密后手机号
	 *
	 */
	public String disEncrypt(InnerBean innerBean){
		
		String strRe = null;
		String resultNum="";
		try { 
			HashMap<String, String> map = new HashMap<String, String>(); 
			map.put("flg", ""); //暂空 
			map.put("SYS", SYS);//请求加密的系统 
			map.put("IDS", StringUtils.isNotEmpty(innerBean.getLoanCode()) ?innerBean.getLoanCode():"ID");//用户ID 
			map.put("USERCODE", StringUtils.isNotEmpty(innerBean.getCustomerCode())?innerBean.getCustomerCode():"USERCODE");//用户标记、用户编码 
			map.put("CREATEUSER", StringUtils.isNotEmpty(innerBean.getCreateBy())?innerBean.getCreateBy():"CREATEUSER"); //创建人 
			map.put("CREATTIME", StringUtils.isNotEmpty(innerBean.getCreateTime())?innerBean.getCreateTime():"CREATTIME");//创建时间 必须long类型 
			map.put("OBJSIG", StringUtils.isNotEmpty(innerBean.getObjsig())?innerBean.getObjsig():OBJSIG);//用户附加字段 
			map.put("MPHONES", innerBean.getMobileNums()+",");//手机号 原文 13716396384,13716396385, 
			map.put("PSLCODE", StringUtils.isNotEmpty(innerBean.getCertNum())?innerBean.getCertNum():"PSLCODE");//证件号 
			map.put("TPHONES", StringUtils.isNotEmpty(innerBean.getTphoneNum())?innerBean.getTphoneNum():"TPHONES");//固定电话号 
			map.put("DBNAME", DBNAME); //所属库 
			map.put("TABNAME",innerBean.getTableName());//所属表 
			map.put("ENCCOLS", innerBean.getCol());//加密字段 （单表多字段必须使用半角逗号分隔） 
			String str = JSON.toJSONString(map); 
			logger.info("手机号加密入参："+str);
			//加密对象 
			TcpClient objTcpClient = new TcpClient(); 
			//加密结果 
			strRe = objTcpClient.disEncrypt(str); 
			logger.info("手机号加密出参："+str);
			Map<String, String> mapRes = (Map)JSONObject.parse(strRe);
			resultNum = mapRes.get("MPHONES");
			if(resultNum !=null){
				resultNum = resultNum.substring(0, resultNum.length()-1);
			}
			} catch (Exception ex) {}
			return resultNum;
	 
		
	}
	/**
	 * 解密
	 * 设置loancode,mobilenums(加密后手机号),tablename,createBy 创建人对应表的创建人, createTime 创建时间对应表的创建时间, 身份证号 有值传值，没有穿""
	 
	 * @return 解密后手机号
	 */
	public String disDecrypt(InnerBean innerBean){
		String strRe = null;
		String resultNum="";
//		LoanCustomer customer  = this.selectCustomerByloancode(innerBean.getLoanCode());
		try { 
		HashMap<String, String> map = new HashMap<String, String>(); 
		map.put("flg", ""); //暂空 
		map.put("SYS", SYS); //请求解密的系统 
		map.put("IDS", StringUtils.isNotEmpty(innerBean.getLoanCode()) ?innerBean.getLoanCode():"ID");//用户ID 
		map.put("USERCODE",  StringUtils.isNotEmpty(innerBean.getCustomerCode())?innerBean.getCustomerCode():"USERCODE");//用户标记、用户编码 
		map.put("CREATEUSER", StringUtils.isNotEmpty(innerBean.getCreateBy())?innerBean.getCreateBy():"CREATEUSER"); //创建人 
		map.put("CREATTIME", StringUtils.isNotEmpty(innerBean.getCreateTime())?innerBean.getCreateTime():"CREATTIME");//创建时间 必须long类型 
		map.put("OBJSIG", StringUtils.isNotEmpty(innerBean.getObjsig())?innerBean.getObjsig():OBJSIG);//用户附加字段  从数据库获取
		map.put("MPHONES",innerBean.getMobileNums()+",");//手机号 密文 BKHAAHBT^BdoBnTBxEB63B&fCGTCP%CZuCjdCtDADA,BKIAAIgqWfLpAFA, 
		map.put("PSLCODE", StringUtils.isNotEmpty(innerBean.getCertNum())?innerBean.getCertNum():"PSLCODE");//证件号 
		map.put("TPHONES", StringUtils.isNotEmpty(innerBean.getTphoneNum())?innerBean.getTphoneNum():"TPHONES");//固定电话号 
		map.put("DBNAME", DBNAME); //所属库 
		map.put("TABNAME",innerBean.getTableName()); //所属表 
		map.put("ENCCOLS", innerBean.getCol()); //加密字段 （单表多字段必须使用半角逗号分隔） 
		String str = JSON.toJSONString(map); 
		logger.info("手机号解密入参："+str);
		//解密对象 
		TcpClient objTcpClient = new TcpClient(); 
		//解密结果 
		strRe = objTcpClient.disDecrypt(str);
		logger.info("手机号解密入参："+str);
		Map<String, String> mapRes = (Map)JSONObject.parse(strRe);
		resultNum = mapRes.get("MPHONES");
		if(resultNum !=null){
			resultNum = resultNum.substring(0, resultNum.length()-1);
		}
		} catch (Exception ex) {}
		return resultNum;
	}
 
	
	 
	
	
	
	/**
	 * 
	 * 静态方法
	 * 
	 * 
	 * 客户表手机号加密
	 *  @param loanCode loan_code
		 * @param customerCode t_jk_loan_customer(id)
		 * @param createBy 创建人t_jk_loan_customer(create_By)
		 * @param createTime 创建时间t_jk_loan_customer(create_Time.getTime()) 
		 * @param mobileNums 手机号   格式"num,num,......"
		 * @param certNum 身份证号
		 * @param tphoneNum 固定电话  t_jk_loan_customer(customer_tel)
		 * @param tableName 数据库表名 
		 * @param col 字段名称  格式"col,col......."
		 * 
		 * 
		 * 
		 *  * 自然人手机号加密
	 *  @param loanCode loan_code
		 * @param customerCode t_jk_loan_coborrower(id)
		 * @param createBy 创建人t_jk_loan_coborrower(create_By)
		 * @param createTime 创建时间t_jk_loan_coborrower(create_Time.getTime()) 
		 * @param mobileNums 手机号   格式"num,num,......"
		 * @param certNum 身份证号 t_jk_loan_coborrower( cobo_cert_num
		 * @param tableName 数据库表名 
		 * @param col 字段名称  格式"col,col......."
		 * 
		 * 
		 * 依次类推
		 * 
	 * @author zhangman
	 * 
	 * @return 加密后手机号
	 *
	 */
	public static String disEncryptStatic(InnerBean innerBean){
		
		String strRe = null;
		String resultNum="";
		try { 
			HashMap<String, String> map = new HashMap<String, String>(); 
			map.put("flg", ""); //暂空 
			map.put("SYS", SYS);//请求加密的系统 
			map.put("IDS", StringUtils.isNotEmpty(innerBean.getLoanCode())?innerBean.getLoanCode():"ID");//用户ID 
			map.put("USERCODE", StringUtils.isNotEmpty(innerBean.getCustomerCode())?innerBean.getCustomerCode():"USERCODE");//用户标记、用户编码 
			map.put("CREATEUSER", StringUtils.isNotEmpty(innerBean.getCreateBy())?innerBean.getCreateBy():"CREATEUSER"); //创建人 
			map.put("CREATTIME", StringUtils.isNotEmpty(innerBean.getCreateTime())?innerBean.getCreateTime():"CREATTIME");//创建时间 必须long类型 
			map.put("OBJSIG", StringUtils.isNotEmpty(innerBean.getObjsig())?innerBean.getObjsig():OBJSIG);//用户附加字段 
			map.put("MPHONES", innerBean.getMobileNums()+",");//手机号 原文 13716396384,13716396385, 
			map.put("PSLCODE", StringUtils.isNotEmpty(innerBean.getCertNum())?innerBean.getCertNum():"PSLCODE");//证件号 
			map.put("TPHONES", StringUtils.isNotEmpty(innerBean.getTphoneNum())?innerBean.getTphoneNum():"TPHONES");//固定电话号 
			map.put("DBNAME", DBNAME); //所属库 
			map.put("TABNAME",innerBean.getTableName());//所属表 
			map.put("ENCCOLS", innerBean.getCol());//加密字段 （单表多字段必须使用半角逗号分隔） 
			String str = JSON.toJSONString(map); 
			loggerSt.info("静态方法手机号加密入参："+str);
			//加密对象 
			TcpClient objTcpClient = new TcpClient(); 
			//加密结果 
			strRe = objTcpClient.disEncrypt(str); 
			loggerSt.info("静态方法手机号加密出参："+strRe);
			Map<String, String> mapRes = (Map)JSONObject.parse(strRe);
			resultNum = mapRes.get("MPHONES");
			if(resultNum !=null){
				resultNum = resultNum.substring(0, resultNum.length()-1);
			}
			} catch (Exception ex) {}
			return resultNum;
	 
		
	}
	/**
	 * 解密
	 * 设置loancode,mobilenums(加密后手机号),tablename,createBy 创建人对应表的创建人, createTime 创建时间对应表的创建时间, 身份证号 有值传值，没有穿""
	 
	 * @return 解密后手机号
	 */
	public static String disDecryptStatic(InnerBean innerBean){
		String strRe = null;
		String resultNum="";
//		LoanCustomer customer  = this.selectCustomerByloancode(innerBean.getLoanCode());
		try { 
		HashMap<String, String> map = new HashMap<String, String>(); 
		map.put("flg", ""); //暂空 
		map.put("SYS", SYS); //请求解密的系统 
		map.put("IDS", StringUtils.isNotEmpty(innerBean.getLoanCode())?innerBean.getLoanCode():"ID");//用户ID 
		map.put("USERCODE",  StringUtils.isNotEmpty(innerBean.getCustomerCode())?innerBean.getCustomerCode():"USERCODE");//用户标记、用户编码 
		map.put("CREATEUSER", StringUtils.isNotEmpty(innerBean.getCreateBy())?innerBean.getCreateBy():"CREATEUSER"); //创建人 
		map.put("CREATTIME", StringUtils.isNotEmpty(innerBean.getCreateTime())?innerBean.getCreateTime():"CREATTIME");//创建时间 必须long类型 
		map.put("OBJSIG", StringUtils.isNotEmpty(innerBean.getObjsig())?innerBean.getObjsig():OBJSIG);//用户附加字段  从数据库获取
		map.put("MPHONES",innerBean.getMobileNums()+",");//手机号 密文 BKHAAHBT^BdoBnTBxEB63B&fCGTCP%CZuCjdCtDADA,BKIAAIgqWfLpAFA, 
		map.put("PSLCODE", StringUtils.isNotEmpty(innerBean.getCertNum())?innerBean.getCertNum():"PSLCODE");//证件号 
		map.put("TPHONES", StringUtils.isNotEmpty(innerBean.getTphoneNum())?innerBean.getTphoneNum():"TPHONES");//固定电话号 
		map.put("DBNAME", DBNAME); //所属库 
		map.put("TABNAME",innerBean.getTableName()); //所属表 
		map.put("ENCCOLS", innerBean.getCol()); //加密字段 （单表多字段必须使用半角逗号分隔） 
		String str = JSON.toJSONString(map); 
		loggerSt.info("静态方法手机号解密入参："+str);
		//解密对象 
		TcpClient objTcpClient = new TcpClient(); 
		//解密结果 
		strRe = objTcpClient.disDecrypt(str);
		loggerSt.info("静态方法手机号解密出参："+strRe);
		Map<String, String> mapRes = (Map)JSONObject.parse(strRe);
		resultNum = mapRes.get("MPHONES");
		if(resultNum !=null){
			resultNum = resultNum.substring(0, resultNum.length()-1);
		}
		} catch (Exception ex) {}
		return resultNum;
	}
	 
	
	
	public LoanCustomer selectCustomerByloancode(String loanCode){
		LoanCustomer customer =new LoanCustomer();
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("loanCode", loanCode);
		customer =customerDao.selectByLoanCode(param);
		return customer;
	}
	
	
	public LoanCoborrower selectCoborrowerByloancode(LoanCoborrower customer){
		customer =coborrowerDao.get(customer);
		return customer;
	}
	
}
