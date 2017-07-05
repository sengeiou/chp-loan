package com.creditharmony.loan.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.query.ProcessQueryBuilder;

public class ReflectHandle {
   /**
    *流程查询参数copy 
    * 
    */
	public static  void copy(Object object,ProcessQueryBuilder queryBuilder) throws Exception
    {  
		if(object!=null && queryBuilder!=null){
    		Class<?> classType = object.getClass();
		    Field[] fields = classType.getDeclaredFields();
	        for(Field field : fields)
	        {
	        	String name = field.getName();
	        	if(!"lendingMoneyStart".equals(name) && !"lendingMoneyEnd".equals(name)
	        	     && !"lendingTimeStart".equals(name) && !"lendingTimeEnd".equals(name)  
	        	     && !"submissionDateStart".equals(name) && !"submissionDateEnd".equals(name)
	        	     && !"checkTime".equals(name)){
	        	    String firstLetter = name.substring(0,1).toUpperCase();    // 将属性的首字母转换为大写            
	        	    String getMethodName = "get" + firstLetter + name.substring(1);
	        	    // 获取方法对象
	        	    Method getMethod = classType.getMethod(getMethodName, new Class[]{});
	        	    // 调用get方法获取旧的对象的值
	        	    Object value = getMethod.invoke(object, new Object[]{});
	        	    if("storeCode".equals(name) || "storeName".equals(name) || "storeOrgId".equals(name)||"cautionerDepositBank".equals(name)||"midBankName".equals(name)||"cardBank".equals(name)||"depositBank".equals(name)){
	        	        if(value!=null){
	        	            String strVal =(String)value; 
	        	            String tag[] = null;
	        	            if(strVal.trim().length()!=0){
	        	                if(strVal.indexOf(",")!=-1){
	        	                    tag = strVal.split(",");
	        	                }else{
	        	                    tag = new String[1];
	        	                    tag[0] = strVal;
	        	                }
	        	                queryBuilder.put(name,tag);
	        	            }
	        	        }
	        	    }else{
	        	        if(value!=null){
	        	            if(value instanceof Double){
	        	                queryBuilder.put(name,(Double)value);
	        	            }else if(value instanceof String){
	        	            	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	                String strVal =(String)value; 
	        	                if(StringUtils.isNotEmpty(strVal)){
	        	                   
	        	                    if("loanCode".equals(name)){
	        	                    	queryBuilder.put(name+"@like","%"+strVal+"%");
	        	                    }else if("customerName".equals(name)){
	        	                        strVal = StringEscapeUtils.unescapeHtml4(strVal);
	        	                        queryBuilder.put(name+"@like","%"+strVal+"%");
	        	                    
	        	                    }else if("visitUserName".equals(name)){
	        	                        strVal = StringEscapeUtils.unescapeHtml4(strVal);
	        	                        queryBuilder.put(name+"@like","%"+strVal+"%");
	        	                    
	        	                    }else if("identityCode".equals(name)){
	        	                        
	        	                        queryBuilder.put(name+"@like","%"+strVal+"%");
	        	                    
	        	                    }else if("contractCode".equals(name)){
                                    
	        	                        queryBuilder.put(name+"@like","%"+strVal+"%");
                                    
	        	                    }else if("certNum".equals(name)){
                                    
	        	                        queryBuilder.put(name+"@like","%"+strVal+"%");
                                    
	        	                    }else if("submissionDate".equals(name)){
	                                    
		        	                      queryBuilder.put(name+"@like","%"+strVal+"%");
	                                    
		        	                }else if("cjAuthenFailure".equals(name)){
		        	                	    strVal = StringEscapeUtils.unescapeHtml4(strVal);
		        	                	    queryBuilder.put(name+"@like","%"+strVal+"%");
		                                    
			        	            }else if("outApproveTimeStart".equals(name)){
			        	            	
			        	            	queryBuilder.put("outApproveTime@>=", DateUtils.formatDate(sdf.parse(strVal), "yyyy-MM-dd HH:mm:ss"));
			        	            	
			        	            }else if("outApproveTimeEnd".equals(name)){
			        	            	
			        	            	queryBuilder.put("outApproveTime@<=", DateUtils.formatDate(sdf.parse(strVal), "yyyy-MM-dd HH:mm:ss"));
			        	            	
			        	            }else if("lastDealTimeStart".equals(name)){
			        	            	
			        	            	queryBuilder.put("lastDealTime@>=", DateUtils.formatDate(sdf.parse(strVal), "yyyy-MM-dd HH:mm:ss"));
			        	            	
			        	            }else if("lastDealTimeEnd".equals(name)){
			        	            	
			        	            	queryBuilder.put("lastDealTime@<=", DateUtils.formatDate(sdf.parse(strVal), "yyyy-MM-dd HH:mm:ss"));
			        	            	
			        	            }else if("confirmSignDateStart".equals(name)){
			        	            	strVal=strVal+" 00:00:00";
			        	            	queryBuilder.put("confirmSignDate@>=", DateUtils.formatDate(sdf.parse(strVal), "yyyy-MM-dd"));
			        	            	
			        	            }else if("confirmSignDateEnd".equals(name)){
			        	            	strVal=strVal+" 23:59:59";
			        	            	queryBuilder.put("confirmSignDate@<=", DateUtils.formatDate(sdf.parse(strVal), "yyyy-MM-dd"));
			        	            	
			        	            }else{
	        	                       
	        	                        queryBuilder.put(name,strVal);
	        	                    }
	        	                }
	        	            }else if(value instanceof Date){
	        	               
	        	                queryBuilder.put(name,(Date)value);
	        	            }
	        	            } 
	        	        }
	        	    }
	        }
		}
  		return;
    }
	/**
	    *流程查询参数copy 
	    * 
	    */
	    public static  void copyGrantParam(LoanFlowQueryParam grtQryParam,Map<String,Object> param) throws Exception
	    {  
	        if(!ObjectHelper.isEmpty(grtQryParam) && !ObjectHelper.isEmpty(param)){
	            Class<?> classType = grtQryParam.getClass();
	            Field[] fields = classType.getDeclaredFields();
	            for(Field field : fields)
	            {
	                String name = field.getName();
	                String firstLetter = name.substring(0,1).toUpperCase();    //将属性的首字母转换为大写            
	                String getMethodName = "get" + firstLetter + name.substring(1);
	                //获取方法对象
	                Method getMethod = classType.getMethod(getMethodName, new Class[]{});
	               //调用get方法获取旧的对象的值
	                Object value = getMethod.invoke(grtQryParam, new Object[]{});
	                if("storeCode".equals(name) || "storeName".equals(name) || "storeOrgId".equals(name)){
	                  if(value!=null){
	                      String strVal =(String)value; 
	                      String tag[] = null;
	                      if(StringUtils.isNotEmpty(strVal)){
	                          if(strVal.indexOf(",")!=-1){
	                              tag = strVal.split(",");
	                          }else{
	                              tag = new String[1];
	                              tag[0] = strVal;
	                          }
	                          param.put(name,tag);
	                       }
	                   }
	                }else{
	                    if(value!=null){
	                         if(value instanceof Double){
	                             param.put(name,(Double)value);
	                          }else if(value instanceof String){
	                             String strVal =(String)value; 
	                             if(strVal.trim().length()!=0){
	                                 param.put(name,strVal);
	                             }
	                          }else if(value instanceof Date){
	                              param.put(name,(Date)value);
	                         }
	                      } 
	                }
	            }
	        }
	        return;
	    }
	/**
	 *具有相同属性的对象数据copy 
	 * 
	 * 
	 * 
	 */
	public  static void  copy(Object src,Object target){
		if(src!=null && target!=null){
		 Class<?> classType = src.getClass();
		 Class<?> tarClassType = target.getClass();
		 Field[] fields = classType.getDeclaredFields();
	        for(Field field : fields)
	        {
	        	String name = field.getName();
	        	String firstLetter = name.substring(0,1).toUpperCase();    //将属性的首字母转换为大写            
	            String getMethodName = "get" + firstLetter + name.substring(1);
	            String setMethodName = "set" + firstLetter + name.substring(1); 
	            //获取方法对象
	            Method getMethod;
                try {
                getMethod = classType.getMethod(getMethodName, new Class[]{});
 	            Method setMethod = tarClassType.getMethod(setMethodName, new Class[]{field.getType()});//注意set方法需要传入
	            //调用get方法获取旧的对象的值
	            Object value = getMethod.invoke(src, new Object[]{});
	            //调用set方法将这个值复制到新的对象中去
	            if(value!=null){
	              setMethod.invoke(target, new Object[]{value});
	            }
                } catch (Exception e) {
                   continue;
                }
	        }
	  }
	}
}
