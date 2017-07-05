package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewExNew;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 操作借款申请信息表的Dao 
 * @Class Name ApplyLoanInfoDao
 * @author 张灏
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface ApplyLoanInfoDao  extends CrudDao<LoanInfo>{

	/**
	 * 新增贷款信息 
	 * 2015年12月19日
	 * By 张灏
	 * @param loanInfo
	 * @return none
	 */
	public void insertLoanInfo(LoanInfo loanInfo);
	
	/**
	 * 更新贷款信息 
	 * 2015年12月19日
	 * By 张灏
	 * @param loanInfo
	 * @return none 
	 */
	public void updateLoanInfo(LoanInfo loanInfo);
	
	/**
	 * 通过ApplyId更新借款信息 
	 * 2015年12月19日
	 * By 张灏
	 * @param loanInfo
	 * @return none 
	 */
	public void updateLoanInfoByApplyId(LoanInfo loanInfo);
	
	/**
	 * 提交时更新申请信息的流程Id 
	 * 2015年12月19日
	 * By 张灏
	 * @param param applyId生成的流程Id、loanCode申请Id
	 * @return none
	 */
	public void updateApplyId(Map<String,Object> param);
	
	/**
	 *复议流程发起时将信借流程ID更改为复议流程ID 
	 *@Create In 2016年02月22日
	 *@author zhanghao
	 *@param  param
	 *@return none 
	 * 
	 */
	public void updateApplyIdByOldApplyId(Map<String,String> param);
	/**
	 * 提交时更新申请信息的流程Id 
	 * 2015年12月19日
	 * By 张灏
	 * @param param applyId生成的流程Id、loanCode申请Id、dictLoanStatus借款状态
	 * @return none
	 */
	public void updateApplyIdAndStatus(Map<String,Object> param);
	
	/**
	 * 通过 loanCode查询申请信息
	 * 2015年12月19日
	 * By 张灏
	 * @param param loanCode 
	 * @return LoanInfo
	 */
	public LoanInfo selectByLoanCode(Map<String,Object> param);
	
	/**
	 * 通过 loanCode查询申请信息 
	 * 2015年12月19日
	 * By 张灏
	 * @param param applyId
	 * @return LoanInfo
	 */
	public LoanInfo selectByApplyId(Map<String,Object> param);
	
	/**
	 * 更改借款状态 
	 * 2015年12月19日
	 * By 张灏
	 * @param param dictLoanStatus、applyId or loanCode 
	 * @return none 
	 */
	public void updateLoanStatus(Map<String,Object> param);
	
	/**
	 *通过客户编号查询客户最近的借款状态
	 *@author zhanghao 
	 *@Create In 2016年2月1日 
	 *@param customerCode 
	 *@return LoanInfo 
	 */
	public List<LoanInfo> selectByCustomerCode(String customerCode);
	
	/**
	 *通过咨询ID查询借款信息 
	 *@author zhanghao
	 *@Create In 2016年2月19日
	 *@param param
	 *@return 
	 * 
	 */
    public LoanInfo	selectByConsultId(Map<String,String> param);
    
    /**
     *通过合同表关联借款申请表 
     *@author zhanghao
     *@Create In 2016年03月14日 
     *@param param applyId 流程ID 
     *@return LoanInfo 
     *  
     */
    public LoanInfo findLoanLinkedContract(Map<String,String> param);
    /**
     *通过合同表关联借款申请表 
     *@author zhanghao
     *@Create In 2016年03月14日 
     *@param param mateCertNum 证件编号  certType 证件类型 
     *@return LoanInfo 
     *  selectByIDNum
     */
    public LoanInfo selectByIDNum(Map<String,Object> param);
    
    /**
	 * 根据借款编码获得借款状态
	 * 2016年3月18日
	 * By shangjunwei
	 * @param loanCode 借款编码
	 * @return 借款状态
	 */
	
	public LoanInfo findStatusByLoanCode(String loanCode);
	
	/**
	 * 门店复核更新出汇金时间
	 * 2016年4月7日
	 * By lirui
	 * @param param 更新参数
	 */
	public void updateOuttoLoanTime(Map<String,Object> param);
	
	/**
	 * 根据借款编号获取数据
	 * 2016年5月19日
	 * By 李文勇
	 * @param loanCode
	 * @return
	 */
	public LoanInfo getByLoanCode(String loanCode);

	/**
	 * 根据借款编号获取进件时间
	 * 2016年5月27日
	 * By 王浩
	 * @param loanCode
	 * @return 
	 */
	public String getCustomerIntoTimeByLoanCode(String loanCode);
	/**
     *使用身份证查询数据 
     *@author zhanghao
     *@Create In 2016年03月14日 
     *@param param mateCertNum 证件编号  certType 证件类型 
     *@return LoanInfo 
     *  selectByIDNum
     */
	public List<LoanInfo> findByIdentityCode(Map<String,Object> param);
	/**
     *通过loanCode更新排序字段
     *@author zhanghao
     *@Create In 2016年07月26日 
     *@param param loanCode 借款编号  orderField 排序字段 
     *@return none 
     * 
     */
	public void updOrderField(Map<String,Object> param);
	
	/**查询借款状态*/
	public List<Map<String,String>> getStatus(String applyId);
	
	/** 修改大金融推介日期与批次号 */
	public void updateRecommend(Map map);
	/**
	 * 如果先保存的其他页签然后才保存的主借人信息，，则同步主借人的id到联系人表、公司表、房产表中的关联id（根据loan_code和关联类型同步）
	 */
	public void syncRid(HashMap<String, Object> params);
	/**
	 * 查询尚未结清的借款数量
	 * role 1 尚未结清的主借人
	 * role 2 尚未结清的主借人配偶
	 * role 3 尚未结清的共借人（旧版申请表）
	 * role 4 尚未结清的最优自然人保证人（新版申请表）
	 * By 任志远 2017年1月4日
	 *
	 * @param certNum	身份证号
	 * @return	List<Map<String, Integer>>
	 */
	public List<Map<String, Integer>> selectUnSettleData(Map<String, Object> param);
	/**
	 * 新信借待办page
	 * @param loanFlowQueryParam
	 * @param pageBounds
	 * @return
	 * @author FuLiXin
	 * @date 2017年2月20日 下午5:02:48
	 */
	public PageList<LoanFlowWorkItemView> findBorrow(
			 PageBounds pageBounds,LoanFlowQueryParam loanFlowQueryParam);

	public void addFileNet(WorkItemView workItem);

	public void deleteFileNet(String applyId);

	
	/**
	 * 根据状态查询信息
	 */
	public List<LoanInfo> findStatus(LoanInfo loaninfo);
	
	/**
	 * 查询一定条件下的门店申请冻结的数据个数
	 * 2017年4月24日
	 * By 朱静越
	 * @param loanInfo
	 * @return
	 */
	public int findFrozenInt(LoanInfo loanInfo);
	
	/**
	 * 
	·* 2017年3月7日
	·* by Huowenlong
	 * @param loanCode
	 */
	public void updateOldLoanCodeByLoanCode(String loanCode);
	
	/**
	 * zmq
	* @Title: updateLoanInfoDelivery
	* @Description: TODO(单条交割更新loanInfo)
	* @param @param deliveryViewExNew
	* @param @return    设定文件
	* @return int    返回类型
	 */
	public int updateLoanInfoDelivery(DeliveryViewExNew deliveryViewExNew);
	
	/**
	 * 根据合同编号修改借款状态
	 * @author 于飞
	 * @Create 2017年4月26日
	 * @param loanInfo
	 */
	public void updateLoanInfoStatusByContractCode(LoanInfo loanInfo);
	
}
