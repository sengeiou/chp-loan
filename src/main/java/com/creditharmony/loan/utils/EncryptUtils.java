package com.creditharmony.loan.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 加密解密
 * @author 任志远
 * @date 2016年12月10日
 */
public class EncryptUtils {
	
	private final static String stringClassName = "String";
	
	private final static String nullString = "null";
	
	private enum DoType {ENCRYPT, DECRYPT}

	/**
	 * 加密
	 * By 任志远 2016年12月10日
	 *
	 * @param o 要加密的对象
	 * @param encryptTableColArray	如果传进来的对象是String， 则需要一个EncryptTableCol类型参数, 否则不要传
	 */
	public static Object encrypt(Object o, EncryptTableCol...encryptTableColArray){
		
		return encryptOrDecrypt(o, DoType.ENCRYPT, encryptTableColArray);
	}
	
	/**
	 * 组团加密
	 * By 任志远 2016年12月13日
	 *
	 * @param list	需要加密的对象集合
	 * @return
	 */
	public static List<?> encryptMulti(List<?> list){
		
		return encryptOrDecryptMulti(list, DoType.ENCRYPT);
	}
	
	/**
	 * 解密
	 * By 任志远 2016年12月10日
	 *
	 * @param o	要解密的对象
	 * @param encryptTableColArray 如果传进来的对象是String， 则需要一个EncryptTableCol类型参数, 否则不要传
	 */
	public static Object decrypt(Object o, EncryptTableCol...encryptTableColArray) {
		
		return encryptOrDecrypt(o, DoType.DECRYPT, encryptTableColArray);
	}
	
	/**
	 * 组团解密
	 * By 任志远 2016年12月13日
	 *
	 * @param list 对象集合
	 * @return
	 */
	public static List<?> decryptMulti(List<?> list) {
		
		return encryptOrDecryptMulti(list, DoType.DECRYPT);
	}
	
	/**
	 * 加密解密服务
	 * By 任志远 2016年12月12日
	 *
	 * @param o	要加密解密的对象
	 * @param doType	动作（加密，解密）
	 * @param encryptTableColArray
	 * @return
	 */
	private static Object encryptOrDecrypt(Object o, DoType doType, EncryptTableCol...encryptTableColArray){
		
		if (o == null) {
			return o;
		}

		Class<?> clazz = o.getClass();

		if (encryptTableColArray != null && encryptTableColArray.length > 0 && o.getClass().getSimpleName().equals(stringClassName) && o.toString().length() > 0) {
			EncryptTableCol etc = encryptTableColArray[0];
			if (EncryptColType.PHONE.equals(etc.getEncryptColType())) {
				InnerBean innerBean = new InnerBean(o.toString(), etc.getTable(), etc.getCol());
				String ret = DoType.ENCRYPT.equals(doType) ? PhoneSecretSerivice.disEncryptStatic(innerBean) : PhoneSecretSerivice.disDecryptStatic(innerBean);
				//加密服务器处理超时，会返回Exception
				if(ret.indexOf("socket") > 0){
					throw new RuntimeException("phone number encrypt or decrypt error. result string = " + ret);
				}
				if(StringUtils.isNotEmpty(ret) && !nullString.equals(ret)){
					o = ret;
				}else{//如果手机加密结果等于"" 或者 等于 "null", 则抛加密异常错误
					throw new RuntimeException("phone number encrypt or decrypt error");
				}
			}
		} else {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (EncryptTableCol.getFieldMap().containsKey(field.getName())) {
					EncryptTableCol etc = EncryptTableCol.getFieldMap().get(field.getName());
					if (etc.getClassName().equals(clazz.getSimpleName())) {
						field.setAccessible(true);
						try {
							Object col = field.get(o);
							if (col != null && col.toString().length() > 0 && EncryptColType.PHONE.equals(etc.getEncryptColType())) {
								InnerBean innerBean = new InnerBean(col.toString(), etc.getTable(), etc.getCol());
								String ret = DoType.ENCRYPT.equals(doType) ? PhoneSecretSerivice.disEncryptStatic(innerBean) : PhoneSecretSerivice.disDecryptStatic(innerBean);;
								//加密服务器处理超时，会返回Exception
								if(ret.indexOf("socket") > 0){
									throw new RuntimeException("phone number encrypt or decrypt error. result string = " + ret);
								}
								if(StringUtils.isNotEmpty(ret) && !nullString.equals(ret)){
									field.set(o, ret);
								}else{//如果手机加密结果等于"" 或者 等于 "null", 则抛加密异常错误
									throw new RuntimeException("phone number encrypt or decrypt error");
								}
							}
						} catch (IllegalArgumentException|IllegalAccessException e) {
							e.printStackTrace();
							return o;
						}
					}
				}
			}
		}
		
		return o;
	}
	
	/**
	 * 加密
	 * By 任志远 2016年12月10日
	 *
	 * @param o 要加密的对象
	 * @param encryptTableColArray	如果传进来的对象是String， 则需要一个EncryptTableCol类型参数, 否则不要传
	 */
	private static List<?> encryptOrDecryptMulti(List<?> list, DoType doType){
		
		if(list == null || list.isEmpty()){
			return list;
		}
		
		Map<Integer, Field> fieldMap = new HashMap<Integer, Field>();
		Map<Integer, Object> objectMap = new HashMap<Integer, Object>();
		StringBuilder mobiles = new StringBuilder();
		StringBuilder cols = new StringBuilder();
		String table = null;
		int i = 0;
		for(Object o : list){
			Class<?> clazz = o.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields){
				if (EncryptTableCol.getFieldMap().containsKey(field.getName())) {
					EncryptTableCol etc = EncryptTableCol.getFieldMap().get(field.getName());
					if (etc.getClassName().equals(clazz.getSimpleName())) {
						try {
							field.setAccessible(true);
							Object col = field.get(o);
							if (col != null && col.toString().length() > 0  && EncryptColType.PHONE.equals(etc.getEncryptColType())) {
								mobiles.append(col.toString()).append(",");
								cols.append(etc.getCol()).append(",");
								if(table == null || !etc.getTable().equals(table)){
									table = etc.getTable();
								}
								fieldMap.put(i, field);
								objectMap.put(i, o);
								i++;
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		if(!fieldMap.isEmpty()){
			InnerBean innerBean = new InnerBean(mobiles.substring(0, mobiles.lastIndexOf(",")), table, cols.toString());
			String ret = DoType.ENCRYPT.equals(doType) ? PhoneSecretSerivice.disEncryptStatic(innerBean) : PhoneSecretSerivice.disDecryptStatic(innerBean);
			//加密服务器处理超时，会返回Exception
			if(ret.indexOf("socket") > 0){
				throw new RuntimeException("phone number encrypt or decrypt error. result string = " + ret);
			}
			String[] mobileArray = ret.split(",");
			if(mobileArray.length == fieldMap.size()){
				for(Map.Entry<Integer, Field> entry: fieldMap.entrySet()){
					Integer index = entry.getKey();
					Field field = entry.getValue();
					try {
						if(StringUtils.isNotEmpty(mobileArray[index]) && !nullString.equals(mobileArray[index])){
							field.set(objectMap.get(index), mobileArray[index]);
						}else{//如果手机加密结果等于"" 或者 等于 "null", 则抛加密异常错误
							throw new RuntimeException("phone number encrypt or decrypt error");
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}else{//返回结果集与机密数量不符
				throw new RuntimeException("phone number encrypt or decrypt error. result string = " + ret);
			}
		}
		
		return list;
	}
	
//	public static void main(String[] args) {
//		
//		long time0 = System.currentTimeMillis();
//		System.out.println("开始时间-----》"+time0);
//		
//		LoanCustomer lc = new LoanCustomer();
//		lc.setCustomerPhoneFirst("");
////		lc.setCustomerPhoneSecond("13822273727");
////		lc.setCustomerPhoneSecond("dc7069591fcbdedf8540af1f0a23cdba");
////		String phone = "13822273727";
////		String phone = "dc7069591fcbdedf8540af1f0a23cdba";
////		System.out.println(phone.getClass().getDeclaredFields()[0]);
//		decrypt(lc);
////		phone = (String) encrypt(phone, EncryptTableCol.LOAN_CUSTOMER_MOBILE_1);
////		System.out.println(System.currentTimeMillis());
////		for(int i =0 ; i< 2; i++){
//			encrypt(lc);
////		}
////		phone = (String) decrypt(phone, EncryptTableCol.LOAN_CUSTOMER_MOBILE_1);
//		System.out.println(lc.getCustomerPhoneFirst());
////		System.out.println(lc.getCustomerPhoneSecond());
////		System.out.println(System.currentTimeMillis());
////		System.out.println(phone);
//		
////		List<LoanCustomer> list = new ArrayList<LoanCustomer>();
////		
////		LoanCustomer lc1 = new LoanCustomer();
////		lc1.setCustomerPhoneFirst("13822273727");
////		list.add(lc1);
////		LoanCustomer lc2 = new LoanCustomer();
////		lc2.setCustomerPhoneFirst("13822273727");
////		list.add(lc2);
////		LoanCustomer lc3 = new LoanCustomer();
////		lc3.setCustomerPhoneFirst("13822273727");
////		list.add(lc3);
////		LoanCustomer lc4 = new LoanCustomer();
////		lc4.setCustomerPhoneFirst("13822273727");
////		list.add(lc4);
////		LoanCustomer lc5 = new LoanCustomer();
////		lc5.setCustomerPhoneFirst("13822273727");
////		list.add(lc5);
////		LoanCustomer lc6 = new LoanCustomer();
////		lc6.setCustomerPhoneFirst("13822273727");
////		list.add(lc6);
////		
//////		decryptMulti(list);
////		encryptMulti(list);
////		
////		for(LoanCustomer lc7 : list){
////			System.out.println(lc7.getCustomerPhoneFirst());
////		}
////		
////		long time6 = System.currentTimeMillis();
////		System.out.println("6使用时间>>>>>>>>>>>>>>>>>"+time6+"------"+(time6-time0));
//	}
}
