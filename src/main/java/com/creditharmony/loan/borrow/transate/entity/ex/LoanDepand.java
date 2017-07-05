package com.creditharmony.loan.borrow.transate.entity.ex;

import java.util.List;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Role;
/**
 * 数据范围比较方法
 * 2015年12月3日 管洪昌
 * @param isManager 检索参数
 * @return 信借数据列表页面
 */
public class LoanDepand {
	
	/**
	 * 汇金门店
	 * 2015年12月3日
	 * @param isManager 检索参数
	 * @return 信借数据列表页面
	 */
	public static boolean store(boolean isManager, List<Role> roleList) {
	    if(!ObjectHelper.isEmpty(roleList)){
            for(Role role:roleList){
	    	/*    CITY_MANAGER("6230000001","汇金门店-城市经理"), */
	    	     if(LoanRole.CITY_MANAGER.id.equals(role.getId())){
	                 isManager = true;
	                 break;
	             }  
	    	/*    STORE_MANAGER("6230000002","汇金门店-门店经理"), */
	    	     if(LoanRole.STORE_MANAGER.id.equals(role.getId())){
	                 isManager = true;
	                 break;
	             }  
	    		/*STORE_ASSISTANT("6230000003","汇金门店-门店副理"), */
	    	     if(LoanRole.STORE_ASSISTANT.id.equals(role.getId())){
	                 isManager = true;
	                 break;
	             }  
	    	/*	CUSTOMER_SERVICE("6230000004","汇金门店-客服"), */
	    	     if(LoanRole.CUSTOMER_SERVICE.id.equals(role.getId())){
	                 isManager = true;
	                 break;
	             }  
	    /*		VISIT_PERSON("6230000005","汇金门店-外访"), */
	    	     if(LoanRole.VISIT_PERSON.id.equals(role.getId())){
	                 isManager = true;
	                 break;
	             }  
	    	/*    TEAM_MANAGER("6240000001","汇金门店-团队经理"), */
	    	     if(LoanRole.TEAM_MANAGER.id.equals(role.getId())){
	                 isManager = true;
	                 break;
	             }  
	    	/*	FINANCING_MANAGER("6240000002","汇金门店-客户经理"),*/
	    	     if(LoanRole.FINANCING_MANAGER.id.equals(role.getId())){
	                 isManager = true;
	                 break;
	             }  
	    	/*	STORE_HR_DIRECTOR("6240000003","汇金门店-人事行政专员"),*/
	    	     if(LoanRole.STORE_HR_DIRECTOR.id.equals(role.getId())){
	                 isManager = true;
	                 break;
	             }  
	    	/*	STORE_SERVICE_MANAGER("6240000004","汇金门店-服务经理"),*/
	            if(LoanRole.STORE_SERVICE_MANAGER.id.equals(role.getId())){
	                isManager = true;
	                break;
	            }  
            }
         }
		return isManager;
	}

	/**
	 * 汇金业务部
	 * 2015年12月3日
	 * @param isManager 检索参数
	 * @return 信借数据列表页面
	 */
	public static boolean hjBusiness(boolean isManager, List<Role> roleList) {
		  if(!ObjectHelper.isEmpty(roleList)){
	            for(Role role:roleList){
	            /*	DEPT_MANAGER("6210000001", "汇金业务部-部门经理"), */
	    	    	  if(LoanRole.DEPT_MANAGER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	       /*     	BIZ_ASSISTANT("6210000002","汇金业务部-业务助理"), */
	    	    	  if(LoanRole.BIZ_ASSISTANT.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	PROVINCE_BIZ_MANAGER("6220000001","汇金省分公司-省分公司业管"), */
	    	    	  if(LoanRole.PROVINCE_BIZ_MANAGER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	             /*   SUB_CITY_BIZ_MANAGER("6220000002","汇金省分公司-城市支公司业管"), */
	    	    	  if(LoanRole.SUB_CITY_BIZ_MANAGER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	          /*  	SUB_CITY_MANAGER("6220000003","汇金省分公司-城市经理"), */
	    	    	  if(LoanRole.SUB_CITY_MANAGER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	PROVINCE_GENERAL_MANAGER("6220000004","汇金省分公司-省级分公司总经理"),*/
	    	    	  if(LoanRole.PROVINCE_GENERAL_MANAGER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	PRODUCT_EXECUTIVE_DIRECTOR("6220000005","汇金省分公司-产品推动主管"),*/
	    	    	  if(LoanRole.PRODUCT_EXECUTIVE_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	BIZ_EXECUTIVE_DIRECTOR("6220000006","汇金省分公司-业务推动主管"),*/
	    	    	  if(LoanRole.BIZ_EXECUTIVE_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	CAR_LOAN_DIRECTOR("6220000007","汇金省分公司-车借主管"),*/
	    	    	  if(LoanRole.CAR_LOAN_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            	/*MARKET_DIRECTOR("6220000008","汇金省分公司-市场主管"),*/
	    	    	  if(LoanRole.MARKET_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	OPERATE_CHECK_DIRECTOR("6220000009","汇金省分公司-运营稽核主管"),*/
	    	    	  if(LoanRole.OPERATE_CHECK_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	PERSONNEL_MANAGER("6220000010","汇金省分公司-人事行政经理"),*/
	    	    	  if(LoanRole.PERSONNEL_MANAGER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	FINANCE_MANAGER("6220000011","汇金省分公司-财务经理"),*/
	    	    	  if(LoanRole.FINANCE_MANAGER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	TRAIN_DIRECTOR("6220000012","汇金省分公司-培训主管"),*/
	    	    	  if(LoanRole.TRAIN_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            	/*HR_DIRECTOR("6220000013","汇金省分公司-招聘主管"),*/
	    	    	  if(LoanRole.HR_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            	/*CHARGE_COMMISSIONER("6220000014","汇金省分公司-催收专员"),*/
	    	    	  if(LoanRole.CHARGE_COMMISSIONER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	CHARGE_DIRECTOR("6220000015","汇金省分公司-催收主管"),*/
	    	    	  if(LoanRole.CHARGE_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            	/*PERSONNEL_DIRECTOR("6220000016","城市支公司-人事行政主管"),*/
	    	    	  if(LoanRole.PERSONNEL_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            	/*CITY_FINANCE_DIRECTOR("6220000017","城市支公司-财务主管"),*/
	    	    	  if(LoanRole.CITY_FINANCE_DIRECTOR.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	CITY_TRAIN_TEACHER("6220000018","城市支公司-培训讲师"),*/
	    	    	  if(LoanRole.CITY_TRAIN_TEACHER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	          /*  	CITY_HR_COMMISSIONER("6220000019","城市支公司-招聘专员"),*/
	    	    	  if(LoanRole.CITY_HR_COMMISSIONER.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            /*	CITY_MECHANICS("6600000008","城市支公司-机械师"),*/
	    	    	  if(LoanRole.CITY_MECHANICS.id.equals(role.getId())){
	                      isManager = true;
	                      break;
	                  }  
	            }
	         }
		return isManager;
	}

	/**
	 * 数据管理部
	 * 2015年12月3日
	 * @param isManager 检索参数
	 * @return 信借数据列表页面
	 */
	public static boolean managementData(boolean isManager, List<Role> roleList) {
	    if(!ObjectHelper.isEmpty(roleList)){
            for(Role role:roleList){
    	    /*	DATA_MASTER("6130000001","惠民-数据管理部-数据管理部负责人"),*/
    	    	  if(BaseRole.DATA_MASTER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
    	    /*	FORTUNE_DATA_PERSON("6130000002","惠民-数据管理部-财富数据专员"),*/
    	    	  if(BaseRole.FORTUNE_DATA_PERSON.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
    	   /* 	LOAN_DATA_PERSON("6130000003","惠民-数据管理部-汇金数据专员"),*/
    	    	  if(BaseRole.LOAN_DATA_PERSON.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
    	    /*	DATA_MANAGER("6130000004","惠民-数据管理部-数据经理/主管"),*/
    	    	  if(BaseRole.DATA_MANAGER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
    	  /*  	DEDUCT_PERSON("6130000005","惠民-数据管理部-划扣专员"),*/
    	    	  if(BaseRole.DEDUCT_PERSON.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
    	   /* 	DEDUCT_MASTER("6130000006","惠民-数据管理部-划扣经理/主管"),*/
    	    	  if(BaseRole.DEDUCT_MASTER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
    	    /*	DELIVERY_PERSON("6130000007","惠民-数据管理部-放款结算专员"),*/
    	    	  if(BaseRole.DELIVERY_PERSON.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
            }
         }
		return isManager;
	}

	/**
	 * 借款人服务部人员比对
	 * 2015年12月3日
	 * @param isManager 检索参数
	 * @return 信借数据列表页面
	 */
	public static boolean borrower(boolean isManager,List<Role> roleList) {
	        if(!ObjectHelper.isEmpty(roleList)){
	            for(Role role:roleList){
	          /*  	LOANER_DEPT_MANAGER("6110000001","惠民-借款人服务部-部门经理"),*/
	            	  if(BaseRole.LOANER_DEPT_MANAGER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	            /*	LOANER_DATA_ANALYSIS("6110000002","惠民-借款人服务部-数据分析专员"),*/
	            	  if(BaseRole.LOANER_DATA_ANALYSIS.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	            	/*DATA_ANALYSIS_LEADER("6110000003","惠民-借款人服务部-数据分析组长"),*/
	            	  if(BaseRole.DATA_ANALYSIS_LEADER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	         /*   	REPAYMENT_COMMISSIONER("6110000004","惠民-借款人服务部-还款管理专员"),*/
	            	  if(BaseRole.REPAYMENT_COMMISSIONER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	           /* 	REPAYMENT_LEADER("6110000005","惠民-借款人服务部-还款管理组长"),*/
	            	  if(BaseRole.REPAYMENT_LEADER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	            	/*CAR_COMMISSIONER("6110000006","惠民-借款人服务部-车借专员"),*/
	            	  if(BaseRole.CAR_COMMISSIONER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	            /*	CAR_LEADER("6110000007","惠民-借款人服务部-车借组长"),*/
	            	  if(BaseRole.CAR_LEADER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	        /*    	MONEY_STATISTICIAN("6110000008","惠民-借款人服务部-款项统计专员"),*/
	            	  if(BaseRole.MONEY_STATISTICIAN.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	        /*    	MONEY_LEADER("6110000009","惠民-借款人服务部-款项统计组长"),*/
	            	  if(BaseRole.MONEY_LEADER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	      /*      	LOANER_CONTRACT_APPROVER("6110000010","惠民-借款人服务部-合同审核专员"),*/
	            	  if(BaseRole.LOANER_CONTRACT_APPROVER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	            /*	CONTRACT_APPROVE_LEADER("6110000011","惠民-借款人服务部-合同审核组长"),*/
	            	  if(BaseRole.CONTRACT_APPROVE_LEADER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	          /*  	LOANER_CONTRACT_MAKER("6110000012","惠民-借款人服务部-合同制作专员"),*/
	            	  if(BaseRole.LOANER_CONTRACT_MAKER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	          /*  	CONTRACT_MAKE_LEADER("6110000013","惠民-借款人服务部-合同制作组长"),*/
	            	  if(BaseRole.CONTRACT_MAKE_LEADER.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	            /*	LOANER_DEPT_ADMIN("6110000014","惠民-借款人服务部-部门管理员"),*/
	            	  if(BaseRole.LOANER_DEPT_ADMIN.id.equals(role.getId())){
		                    isManager = true;
		                    break;
		                }  
	            }
	         }
		return  isManager;
	}
	
	
	/**
	 * 
	 * 2015年12月3日
	 * @param isManager 检索参数
	 * @return 信借数据列表页面  电销售总监 ，电销现场经理
	 */
	public static boolean electricData(boolean isManager, List<Role> roleList) {
	    if(!ObjectHelper.isEmpty(roleList)){
            for(Role role:roleList){
    	    /*	MOBILE_SALE_MASTER("6250000001","电销总监"),*/
    	    	  if(LoanRole.MOBILE_SALE_MASTER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
    	    /*	MOBILE_SALE_MANAGER("6250000002","电销售现场经理"),*/
    	    	  if(LoanRole.MOBILE_SALE_MANAGER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  } 
    	   /*	MOBILE_SALE_DATA_COMMISSIONER("6250000006","电销数据专员"),*/
    	    	  if(LoanRole.MOBILE_SALE_DATA_COMMISSIONER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }
    	  
            }
         }
		return isManager;
	}
	
	
	/**
	 * 
	 * 2015年12月3日
	 * @param isManager 检索参数
	 * @return 信借数据列表页面  电销现场主管
	 */
	public static boolean electricDataMobile(boolean isManager, List<Role> roleList) {
	    if(!ObjectHelper.isEmpty(roleList)){
            for(Role role:roleList){
    	    /* LoanRole.MOBILE_SALE_TEAM_MANAGER.id,   */
    	    	  if( LoanRole.MOBILE_SALE_TEAM_MANAGER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
            }
         }
		return isManager;
	}
	
	
	/**
	 * 
	 * 2015年12月3日
	 * @param isManager 检索参数
	 * @return 信借数据列表页面  电销录单专员
	 */
	public static boolean electDataMobile(boolean isManager, List<Role> roleList) {
	    if(!ObjectHelper.isEmpty(roleList)){
            for(Role role:roleList){
    	    /* LoanRole.MOBILE_SALE_TEAM_MANAGER.id,   */
    	    	  if( LoanRole.MOBILE_SALE_RECORDER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
    	  	    /* LoanRole.MOBILE_SALE_TEAM_MANAGER.id,   */
    	    	  if( LoanRole.MOBILE_SALE_COMMISSIONER.id.equals(role.getId())){
                      isManager = true;
                      break;
                  }  
            }
         }
		return isManager;
	}


}
