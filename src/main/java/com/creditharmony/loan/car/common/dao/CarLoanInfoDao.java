package com.creditharmony.loan.car.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.ex.CarLoanCheckTabEx;
@LoanBatisDao
public interface CarLoanInfoDao extends CrudDao<CarLoanInfo>{
	/**
     *新增借款信息 
     *安子帅 
     *@param CarLoanInfo 
     *@return   
     */
    public void insertCarLoanInfo(CarLoanInfo carLoanInfo);
    /**
     *查询车借借款信息
     *@param String 
     *@return   String
     */
    public CarLoanInfo selectByLoanCode(String loanCode);
    
    public CarLoanInfo selectByLoanCodeExtend(String loanCode);
    /**
     *查询车借借款信息
     *@param String 
     *@return   String
     */
    public CarLoanInfo selectByApplyId(String applyId);
    /**
     *查询一个身份证车借的次数 
     *@param String 
     *@return   Integer
     */
    public Integer vehicleCeiling(String customerCertNum);
    /**
     *通过原借款编码得到借款信息
     *@param String 
     *@return   List<CarLoanInfo>
     */
    public List<CarLoanInfo> selectByLoanRawCode(String loanRawcode);
    
    /**
     * 通过上次loancode得到本次展期是否进行完成
     * 2016年3月16日
     * By 安子帅
     * @param loanCode
     * @return
     */
    public CarLoanInfo selectLoanCodeByLoanRaw(String loanCode);
    /**
     * 根据loanCode检查车借申请办理时tab页必填信息是否填写完全
     * 2016年4月22日
     * By 高远
     * @param loanCode
     * @return
     */
	public CarLoanCheckTabEx checkAllTab(String loanCode);
	
	/**
	 * 通过原车借loancode得到已完成的借款信息
	 * 2016年5月10日
	 * By 申诗阔
	 * @param loanCode
	 * @return
	 */
    public CarLoanInfo selectNearestByLoanCode(String loanCode);
    /**
     * 查询批借金额
     * @param loanAdditionalApplyid2
     * @return
     * By 高远
     */
	public Double selectAuditAmount(String loanAdditionalApplyid2);
    /**
     * 修改车借p2p标识（事物）
     * @param 
     * @return
     * By wangj
     */
	public int updateCarP2PStatu(Map<String,Object> param);
    /**
     * 修改车借展期p2p标识（事物）
     * @param 
     * @return
     * By wangj
     */
	public int updateCarExtendP2PStatu(Map<String,Object> param);
	/**
	 * 修改初始处理人
	 * 2016年5月28日
	 * By 何军
	 * @param param
	 * @return
	 */
	public int updateCarLoanInfoDealUser(Map<String,Object> param);
	
	/**
	 * 补录展期时，新增客户，同时需要更新展期借款信息中customer_code
	 * 2016年6月20日
	 * By 申诗阔
	 * @param param
	 * @return
	 */
	public int updateCarExtendLoanInfoCusCode(Map<String,Object> param);
	
	/**
	 * 历史展期补录，删除历史展期时，删除借款信息
	 * 2016年6月22日
	 * By 申诗阔
	 * @param list
	 */
	public void deleteCarLoanInfoBatchByContractIds(List<String> list);
	
	/**
	 * 历史展期补录使用，根据车借借款编码和本次借款编码获取最近的含有各种信息的 那条 借款信息
	 * 2016年6月23日
	 * By 申诗阔
	 * @param param
	 * @return
	 */
	public CarLoanInfo getRichCarLoanInfo(Map<String, Object> param);
	
	/**
	 * 通过原车借借款编码获取展期申请（7个大页签填写到到一半的）记录
	 * 2016年6月30日
	 * By 申诗阔
	 * @param loanCode
	 * @return
	 */
	public CarLoanInfo selectExtendHistoryByLoanRaw(String loanCode);
	
	public CarLoanInfo checkExtendHistoryEntry(String contractCode);
	public int updateById(CarLoanInfo carLoanInfo);
	
    /**
     *通过借款信息ID查找展期放弃的数据,升序取最早的一条记录
     *@param String 
     *@return   List<CarLoanInfo>
     */
    public List<CarLoanInfo> selectByLoanAddtionAppid(String id);
	/**
	 * 通过身份证号、车牌号查找以往的借款信息
	 * @param certNum
	 * @param vehicleNum
	 * @return List<CarLoanInfo>
	 */
    public List<CarLoanInfo> selectByCertNumAndVehicleNum(Map<String, Object> param);
    
    
	
}