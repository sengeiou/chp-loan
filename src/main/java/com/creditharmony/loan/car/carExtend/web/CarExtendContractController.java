package com.creditharmony.loan.car.carExtend.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.CarExtendType;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;
import com.creditharmony.loan.borrow.contract.service.PaperLessService;
import com.creditharmony.loan.borrow.contract.service.PaperlessPhotoService;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carApply.service.CarLoanCoborrowerService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.carContract.service.CarAuditResultService;
import com.creditharmony.loan.car.carContract.service.CarCheckRateService;
import com.creditharmony.loan.car.carContract.service.CarContractService;
import com.creditharmony.loan.car.carContract.utils.carContractUtils;
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.carExtend.consts.CarExtendWorkQueues;
import com.creditharmony.loan.car.carExtend.ex.CarExportCustomerExtendsDataEx;
import com.creditharmony.loan.car.carGrant.service.CarGrantCAService;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarCustomerBankInfoDao;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarContractFile;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarContractFileService;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.service.ContractCommonService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ImageUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.remote.service.OrgProxyService;
import com.query.ProcessQueryBuilder;

/**
 * 合同、利率审核
 * @Class Name CarExtendContractController
 * @author 申诗阔
 * @Create In 2016年3月9日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carExtendContract")
public class CarExtendContractController extends BaseController {

	//FlowService 查询流程待办列表、提交流程
	@Resource(name="appFrame_flowServiceImpl")
	protected FlowService flowService;
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	@Autowired
	private CarAuditResultService carAuditResultService;
	@Autowired
	private ContractCommonService contractCommonService;
	@Autowired
	private CarContractService carContractService;
	@Autowired
	private OrgProxyService orgProxyService;
	@Autowired
	private CarCustomerService carCustomerService;
	@Autowired
	private CarLoanCoborrowerService carLoanCoborrowerService;
	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;
	@Autowired
	private CarCheckRateService carCheckRateService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired 
	private CarContractFileService carContractFileService;
	@Autowired
	private CarContractDao carContractDao;	
	@Autowired
	private CarGrantCAService carGrantCAService;
	@Autowired
	private PaperLessService paperLessService;
	@Autowired
    private PaperlessPhotoService paperlessPhotoService;
	@Autowired
	private CarCustomerBankInfoDao carCustomerBankInfoDao;
	
	private static final Logger log = LoggerFactory.getLogger(CarExtendContractController.class);
	/**
	 * 利率审核、合同制作、签订上传合同、合同审核
	 * 2016年3月9日
	 * By 申诗阔
	 * @param workItem
	 * @param view
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "carContractHandle")
	public String carContractHandle(WorkItemView workItem, CarCheckRateBusinessView view,HttpServletRequest request)  throws IOException{
		String result = view.getCheckResult(); // 审批结果
		if (StringUtils.isNotBlank(result)) { // 若审批结果不为空
			//获取标红置顶属性内容
			WorkItemView workItemView = flowService.loadWorkItemView(view.getApplyId(), workItem.getWobNum(), workItem.getToken());
			workItem.setFlowProperties(workItemView.getFlowProperties());
			
			if (CarLoanResponses.TO_SIGN.getCode().equals(result)) { // 审核费率 通过
				workItem.setResponse(CarLoanResponses.TO_SIGN.getCode());
			} else if (CarLoanResponses.BACK_END_AUDIT.getCode().equals(result)) { // 审核费率 退回
				workItem.setResponse(CarLoanResponses.BACK_END_AUDIT.getCode());
			} else if (CarLoanResponses.TO_UPLOAD_CONTRACT.getCode().equals(result)) { // 合同制作 通过
				String centerUser = view.getCenterUser();
				if(StringUtils.isNotEmpty(centerUser)){
					CarContract carContract = new CarContract();
					carContract.setLoanCode(view.getLoanCode());
					carContract.setMidId(centerUser);
					carContractService.updateByLoanCode(carContract);
				}
				String loanCode = view.getLoanCode();
				Map<String, Object> map = createContract(loanCode);
				if (BooleanType.FALSE.equals(map.get("flag"))) { // 若制作合同失败
					view.setDictLoanStatus(CarLoanStatus.PRODUCTION_CONTRACT_FAILURE.getCode());
					workItem.setBv(view);
					carContractService.waitHandle(workItem);
					flowService.saveData(workItem);
					return BooleanType.FALSE;
				} else { // 若制作合同成功
					workItem.setResponse(CarLoanResponses.TO_UPLOAD_CONTRACT.getCode());
					workItem.setBv(view);
					flowService.dispatch(workItem);
					return BooleanType.TRUE;
				}
			} else if (CarLoanStatus.AUDIT_INTEREST_RATE.getCode().equals(result)) { // 合同制作 退回到利率审核
				workItem.setResponse(CarLoanResponses.BACK_AUDIT_RATE.getCode());
			} else if (CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode().equals(result)) { // 合同制作 退回到签署
				workItem.setResponse(CarLoanResponses.BACK_SIGN.getCode());
			} else if (CarLoanResponses.TO_AUDIT_CONTRACT.getCode().equals(result)) { // 合同签约上传 通过
				// 合同签订阶段,图片合成
				//根据loanCode得到产品类型
				CarContract carContract = carContractService.getByLoanCode(view.getLoanCode());
				//保存银行卡信息
				CarCustomerBankInfo carCustomerBankInfo = new CarCustomerBankInfo();
				carCustomerBankInfo.setLoanCode(view.getLoanCode());
				carCustomerBankInfo.setBankSigningPlatform(view.getSigningPlatformName());
				carCustomerBankInfoDao.update(carCustomerBankInfo);
		        if( YESNO.YES.getCode().equals(carContract.getPaperLessFlag())){
		            String webPath = request.getSession().getServletContext().getRealPath("/");
		            logger.info("dispatchFlow--->图片合成路径: "  + webPath);
		            List<PaperlessPhoto> PaperlessPhotoList =  paperlessPhotoService.getByLoanCode(view.getLoanCode()); 
		            DmService dmService = DmService.getInstance();
		            InputStream input1 = null;
		            InputStream input2 =null;
		            OutputStream output1 = null;
		            OutputStream output2 = null;
		            File file1 = null;
		            File file2 = null;
		            File file3 = null;
		            InputStream ceInput = null;
		            for(PaperlessPhoto photo:PaperlessPhotoList){
		            	logger.info("dispatchFlow ->图片合成 file1 path = " + webPath+photo.getIdPhotoId()+".jpg");
		                file1 = new File(webPath+photo.getIdPhotoId()+".jpg");
		                file2 = new File(webPath+photo.getSpotPhotoId()+".jpg");
		                file3 = new File(webPath+"COMPOSE_"+System.currentTimeMillis()+".jpg");
		                logger.info("dispatchFlow ->图片合成 file1 path = " + webPath+photo.getIdPhotoId()+".jpg");
		                logger.info("dispatchFlow ->图片合成 file1 path = " + webPath+photo.getIdPhotoId()+".jpg");
		                logger.info("dispatchFlow ->图片合成 file1 path = " + webPath+"COMPOSE_"+System.currentTimeMillis()+".jpg");
		                if(!file1.exists()){
		                    file1.createNewFile();
		                }
		                if(!file2.exists()){
		                    file2.createNewFile();
		                }
		                output1 = new FileOutputStream(file1);
		                output2 = new FileOutputStream(file2);
		                logger.info("photo1 id is " + photo.getIdPhotoId());
		                input1 = dmService.downloadDocument(photo.getIdPhotoId());
		                logger.info("photo2 id is " + photo.getSpotPhotoId());
		                input2 = dmService.downloadDocument(photo.getSpotPhotoId());
		                int c;  
		                while ((c = input1.read()) != -1) {  
		                    output1.write(c);  
		                }  
		                while ((c = input2.read()) != -1) {  
		                    output2.write(c);  
		                } 
		                logger.info(file1.getAbsolutePath());
		                ImageUtils.xPic(file2.getAbsolutePath(),file1.getAbsolutePath(),  file3.getAbsolutePath());
		                ceInput = new FileInputStream(file3);
		                DocumentBean doc = dmService.createDocument("COMPOSE_"+System.currentTimeMillis()+".jpg",
		                        ceInput, DmService.BUSI_TYPE_LOAN,CeFolderType.SIGN_UPLOAD.getName(),
		                        view.getContractCode(), UserUtils.getUser().getId());
		                paperLessService.updateCarComposeDocId(photo.getRelationId(),doc.getDocId(),photo.getCustomerType());
		                input1.close();
		                input2.close();
		                output1.close();
		                output2.close();
		                ceInput.close();
		                file1.deleteOnExit();
		                file2.deleteOnExit();
		                file3.deleteOnExit();
		            }
		        }
				workItem.setResponse(CarLoanResponses.TO_AUDIT_CONTRACT.getCode());
			} else if (CarLoanResponses.TO_GRANT_CONFIRM.getCode().equals(result)) { // 合同审核 通过
				// TODO 执行ca签章操作
			   /*CarContract contract = carContractDao.selectByLoanCode(view.getLoanCode());
			   String channelFlag = contract.getChannelFlag();
//			   boolean result1 = false;
//			   if(ChannelFlag.CHP.getCode().equals(channelFlag) || 
//			        ChannelFlag.P2P.getCode().equals(channelFlag)){
//				   result1 = carGrantCAService.signUpCA(view.getLoanCode());    
//			   }
				   //boolean result = true;
				*//**
				 *CA签章成功则到下一个节点 
				 *CA签章失败则更新签章失败属性 
				 *//*
			   workItem.setResponse(CarLoanResponses.TO_END.getCode());
				if(result1){
					workItem.setResponse(CarLoanResponses.TO_END.getCode());
				}else{
					
				}*/
				workItem.setResponse(CarLoanResponses.TO_END.getCode());
				
			} else if ("CONTRACT_CHECK_BACK".equals(result)) { // 合同审核 退回
				String backNode = view.getBackNode();
				if (CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode().equals(backNode)) { // 退回到 待确认签署
					workItem.setResponse(CarLoanResponses.BACK_SIGN.getCode());
				} else if (CarLoanStatus.PENDING_SIGNED_CONTRACT.getCode().equals(backNode)) { //退回到 待签订合同
					workItem.setResponse(CarLoanResponses.BACK_UPLOAD_CONTRACT.getCode());
				}
			}
			workItem.setBv(view);
			
			flowService.dispatch(workItem);
		} else {
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 通过applyId生成合同编号
	 * 2016年3月9日
	 * By 申诗阔
	 * @param applyId
	 * @return
	 */
	@SuppressWarnings("finally")
	@ResponseBody
	@RequestMapping(value = "generalExtendContractCode")
	public String generalExtendContractCode(String applyId) {
		String str = BooleanType.TRUE;
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId); // 通过aplyId获取借款信息
		try {
			// 生成合同编号
			carContractService.getExtendContractNo(carLoanInfo.getLoanRawcode(), carLoanInfo.getLoanCode(), YESNO.YES.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			str = BooleanType.FALSE;
		} finally {
			return str;
		}
	}
	/**
	 * 
	 * 导出客户展期信息
	 * 2016-3-14
	 * By 高远
	 * @return
	 */
		@RequestMapping(value = "exportData")
		public String exportWatch(HttpServletResponse response,HttpServletRequest request, String idVal,
				CarLoanFlowQueryView carLoanFlowQueryView) {
			FlowPage page = new FlowPage();
			String pageSize = request.getParameter("pageSize");
	        String pageNo = request.getParameter("pageNo");
	        Integer ps = 30;
	        Integer pn = 1;
	        if(!ObjectHelper.isEmpty(pageSize)){
	            ps = Integer.valueOf(pageSize);
	        }
	        if(!ObjectHelper.isEmpty(pageNo)){
	            pn = Integer.valueOf(pageNo); 
	        }
	        page.setPageSize(ps);
	        page.setPageNo(pn);
	        
			ExcelUtils excelutil = new ExcelUtils();
			List<CarExportCustomerExtendsDataEx> lst = new ArrayList<CarExportCustomerExtendsDataEx>();
			ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
			List<CarLoanFlowWorkItemView> workItems = new ArrayList<CarLoanFlowWorkItemView>();
			try {
				ReflectHandle.copy(carLoanFlowQueryView, queryParam);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!"".equals(idVal)) {
//				String[] id = null;
//				id = idVal.split(",");
//				List<String> list = new ArrayList<String>();
//				for (int i = 0; i < id.length; i++) {
//					list.add(id[i]);
//				}
				queryParam.put("applyId", idVal.split(","));
			}
			flowService.fetchTaskItems(CarExtendWorkQueues.HJ_CAR_EXTEND_CONTRACT_CHECK.getWorkQueue(), queryParam, page, null, CarLoanFlowWorkItemView.class);
			List<BaseTaskItemView> sourceWorkItems = page.getList();
			workItems = this.convertList(sourceWorkItems);
			if (ArrayHelper.isNotEmpty(workItems)) {
				for (int i = 0; i < workItems.size(); i++) {
					CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(workItems.get(i).getApplyId());
					String loanCode = carLoanInfo.getLoanCode();
					CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
					CarContract carContract = carContractService.getByLoanCode(loanCode);
					CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
					CarExportCustomerExtendsDataEx carExportMoneyEx = new CarExportCustomerExtendsDataEx();
					ReflectHandle.copy(workItems.get(i), carExportMoneyEx);
					carExportMoneyEx.setNumber(i + 1);
					carExportMoneyEx.setContractCode(workItems.get(i).getContractCode());
					carExportMoneyEx.setCustomerName(workItems.get(i).getCustomerName());
					carExportMoneyEx.setCoborrowerName(workItems.get(i).getCoborrowerName());
					carExportMoneyEx.setStoreName(workItems.get(i).getStoreName());
					carExportMoneyEx.setContractAmount(workItems.get(i).getContractAmount());
					carExportMoneyEx.setDerate(carContract.getDerate().doubleValue());
					carExportMoneyEx.setCertNum(workItems.get(i).getCertNum());
					carExportMoneyEx.setCardBank(carCustomerBankInfo.getCardBank());
					carExportMoneyEx.setBankCardNo(carCustomerBankInfo.getBankCardNo());
					carExportMoneyEx.setAuditLoanMonths(workItems.get(i).getAuditLoanMonths());
					carExportMoneyEx.setAuditBorrowProductName(workItems.get(i).getAuditBorrowProductName());
					carExportMoneyEx.setIsextension(DictCache.getInstance().getDictLabel("jk_extend_loan_flag", workItems.get(i).getExtensionFlag()));
					carExportMoneyEx.setPlateNumbers(workItems.get(i).getPlateNumbers());
					carExportMoneyEx.setApplyStatusCode(DictCache.getInstance().getDictLabel("jk_car_loan_status", workItems.get(i).getApplyStatusCode()));
					carExportMoneyEx.setContractVersion(workItems.get(i).getContractVersion());
					carExportMoneyEx.setFeePaymentAmount(carCheckRate.getFeePaymentAmount()==null?null:carCheckRate.getFeePaymentAmount().doubleValue());
					carExportMoneyEx.setMonthRepayAmount(carCheckRate.getMonthRepayAmount()==null?null:carCheckRate.getMonthRepayAmount().doubleValue());
					carExportMoneyEx.setServiceFee(carCheckRate.getComprehensiveServiceFee()==null?null:carCheckRate.getComprehensiveServiceFee().doubleValue());
					carExportMoneyEx.setParkFee(carLoanInfo.getParkingFee()==null?null:carLoanInfo.getParkingFee().doubleValue());
					carExportMoneyEx.setFacilityFee(carLoanInfo.getFacilityCharge()==null?null:carLoanInfo.getFacilityCharge().doubleValue());
					carExportMoneyEx.setDeviceUsedFee(carLoanInfo.getDeviceUsedFee()==null?null:carLoanInfo.getDeviceUsedFee().doubleValue());
					carExportMoneyEx.setFlowFee(carLoanInfo.getFlowFee()==null?null:carLoanInfo.getFlowFee().doubleValue());
					carExportMoneyEx.setGrossRate(carContract.getGrossRate()==null?null:carContract.getGrossRate().doubleValue());
					carExportMoneyEx.setInterestRate(carCheckRate.getInterestRate()==null?null:carCheckRate.getInterestRate().doubleValue());
					carExportMoneyEx.setContractFactDay(carContract.getContractFactDay());
					carExportMoneyEx.setContractReplayDay(carContract.getContractReplayDay());
					carExportMoneyEx.setContractEndDay(carContract.getContractEndDay());
					carExportMoneyEx.setReplayDay(carContract.getContractReplayDay());
					carExportMoneyEx.setExtendNumber(workItems.get(i).getExtendNumber()+"");
					carExportMoneyEx.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag",workItems.get(i).getLoanFlag()));
					carExportMoneyEx.setExtensionFee(carContract.getExtensionFee()==null?null:carContract.getExtensionFee().doubleValue());
					carExportMoneyEx.setExtendAmount(workItems.get(i).getContractAmount());
					lst.add(carExportMoneyEx);
				}
			}
			excelutil.exportExcel(lst, FileExtension.GRANT_CUSTOMER,
					CarExportCustomerExtendsDataEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_DATA, response, null);
			return null;
		}
		
		/**
		 * 签订上传合同操作-展期
		 * 2016年3月15日
		 * By 蒋力
		 * @param workItem
		 * @param view
		 * @return
		 */
		@RequestMapping(value = "extendUploadContractHandle")
		public String extendUploadContractHandle(WorkItemView workItemView,CarCheckRateBusinessView view) {
			//获取标红置顶属性内容
			WorkItemView workItem = flowService.loadWorkItemView(view.getApplyId(), workItemView.getWobNum(), workItemView.getToken());
			workItemView.setFlowProperties(workItem.getFlowProperties());
			
			if(CarLoanResponses.EXTEND_GIVE_UP.getCode().equals(workItemView.getResponse()))
			{
				//走客户放弃
				workItemView.setResponse(CarLoanResponses.EXTEND_GIVE_UP.getCode());
				workItemView.setBv(view);
				flowService.dispatch(workItemView);
			}
			else if(CarLoanResponses.BACK_SIGN_CONTRACT.getCode().equals(workItemView.getResponse()))
			{
				//走退回
				workItemView.setResponse(CarLoanResponses.BACK_SIGN_CONTRACT.getCode());
				workItemView.setBv(view);
				flowService.dispatch(workItemView);
				//保存退回原因
				CarLoanInfo carLoanInfo = new CarLoanInfo();
				carLoanInfo.setLoanCode(view.getLoanCode());
				carLoanInfo.setRemark(view.getBackReason());
				carLoanInfoService.updateCarLoanInfo(carLoanInfo);
				//保存退回原因
			}
			return "redirect:" + this.adminPath + "/car/carExtendWorkItems/fetchTaskItems/extendReceived";
		}
		
		/**
		 * 将流程中查询出来的数据类型进行转封装
		 * 2016年4月21日
		 * By 申诗阔
		 * @param sourceWorkItems
		 * @return
		 */
	    private List<CarLoanFlowWorkItemView> convertList(
	            List<BaseTaskItemView> sourceWorkItems) {
	        List<CarLoanFlowWorkItemView> targetList = new ArrayList<CarLoanFlowWorkItemView>();
	        if (!ObjectHelper.isEmpty(sourceWorkItems)) {
	            for (BaseTaskItemView currItem : sourceWorkItems)
	                targetList.add((CarLoanFlowWorkItemView) currItem);
	        }
	        return targetList;
	    }
	    /**
		 * 合同生成
		 * 2016年4月1日
		 * By 甘泉
		 * @param loanCode
		 * @return 
		 */
		public Map<String, Object> createContract(String loanCode){
			//开始制作合同
			log.debug("汇金车借合同制作处理开始，当前时间是：" + new Date());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			CarContract carContract = carContractService.getByLoanCode(loanCode);
			String productType = carContract.getProductType();
			String contractVersion = carContract.getContractVersion();
			resultMap.put("flag", BooleanType.TRUE);
			CarContract carContractNew = new CarContract();
			try{
				// 获取dmService
				DmService dmService = DmService.getInstance();
					//................................................................................
					log.debug("合同制作所需求数据检索完成，共检索合同制作数据6条！");
				if(CarLoanProductType.GPS.getCode().equals(productType)||CarLoanProductType.TRANSFER.getCode().equals(productType)){
					//1.车借展期协议
					String wordUrl1 = carContractUtils.getLoanCarAllURL(carContractUtils.CJZQXY_GPSYJ,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl1 = new URL(wordUrl1);
					URLConnection wordUrlcon1 = wUrl1.openConnection();
					// 获取连接
					wordUrlcon1.connect();
					// 获取流
					InputStream word1 = wordUrlcon1.getInputStream();
					String fileName1 = "车借展期协议_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc1 = dmService.createDocument(fileName1, 
									word1, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					//2.还款管理服务说明书
					String wordUrl2 = carContractUtils.getLoanCarAllURL(carContractUtils.HKGLFWSMS_GPSYJ,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl2 = new URL(wordUrl2);
					URLConnection wordUrlcon2 = wUrl2.openConnection();
					// 获取连接
					wordUrlcon2.connect();
					// 获取流
					InputStream word2 = wordUrlcon2.getInputStream();
					String fileName2 = "还款管理服务说明书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc2 = dmService.createDocument(fileName2, 
									word2, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					//3.借款人委托扣款授权书
					String wordUrl3 = carContractUtils.getLoanCarAllURL(carContractUtils.JKRWTKKSQS_GPSYJ,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl3 = new URL(wordUrl3);
					URLConnection wordUrlcon3 = wUrl3.openConnection();
					// 获取连接
					wordUrlcon3.connect();
					// 获取流
					InputStream word3 = wordUrlcon3.getInputStream();
					String fileName3 = "借款人委托扣款授权书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc3 = dmService.createDocument(fileName3, 
									word3, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					//4.车借展期协议
					String wordUrl4 = carContractUtils.getLoanCarAllURL(carContractUtils.CJZQXY_GPSYJ,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl4 = new URL(wordUrl4);
					URLConnection wordUrlcon4 = wUrl4.openConnection();
					// 获取连接
					wordUrlcon4.connect();
					// 获取流
					InputStream word4 = wordUrlcon4.getInputStream();
					String fileName4 = "车借展期协议_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc4 = dmService.createDocument(fileName4, 
									word4, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					if (doc4.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName4, doc4.getDocId(),
								CarExtendType.CAREXTENDTYPE_PROTOCOL_GPS_YJ);
					}
					//5.还款管理服务说明书
					String wordUrl5 = carContractUtils.getLoanCarAllURL(carContractUtils.HKGLFWSMS_GPSYJ,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl5 = new URL(wordUrl5);
					URLConnection wordUrlcon5 = wUrl5.openConnection();
					// 获取连接
					wordUrlcon5.connect();
					// 获取流
					InputStream word5 = wordUrlcon5.getInputStream();
					String fileName5 = "还款管理服务说明书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc5 = dmService.createDocument(fileName5, 
									word5, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					if (doc5.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName5, doc5.getDocId(),
								CarExtendType.CAREXTENDTYPE_MANAGE_GPS_YJ);
					}
					//6.借款人委托扣款授权书
					String wordUrl6 = carContractUtils.getLoanCarAllURL(carContractUtils.JKRWTKKSQS_GPSYJ,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl6 = new URL(wordUrl6);
					URLConnection wordUrlcon6 = wUrl6.openConnection();
					// 获取连接
					wordUrlcon6.connect();
					// 获取流
					InputStream word6 = wordUrlcon6.getInputStream();
					String fileName6 = "借款人委托扣款授权书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc6 = dmService.createDocument(fileName6, 
									word6, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					if (doc6.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName6, doc6.getDocId(),
								CarExtendType.CAREXTENDTYPE_RETURN_MANAGE_GPS_YJ);
					}
					
					//7.免责声明
					String wordUrl7 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_ZQ,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl7 = new URL(wordUrl7);
					URLConnection wordUrlcon7 = wUrl7.openConnection();
					// 获取连接
					wordUrlcon7.connect();
					// 获取流
					InputStream word7 = wordUrlcon7.getInputStream();
					String fileName7 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc7 = dmService.createDocument(fileName7, 
									word7, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					//8.免责声明
					String wordUrl8 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_ZQ,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl8 = new URL(wordUrl8);
					URLConnection wordUrlcon8 = wUrl8.openConnection();
					// 获取连接
					wordUrlcon8.connect();
					// 获取流
					InputStream word8 = wordUrlcon8.getInputStream();
					String fileName8 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc8 = dmService.createDocument(fileName8, 
									word8, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					if (doc8.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName8, doc8.getDocId(),
								CarExtendType.CAREXTENDTYPE_RETURN_MFSM_GPS_YJ);
					}
					//9.特别说明
					String wordUrl9 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM_ZQ,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl9 = new URL(wordUrl9);
					URLConnection wordUrlcon9 = wUrl9.openConnection();
					// 获取连接
					wordUrlcon9.connect();
					// 获取流
					InputStream word9 = wordUrlcon9.getInputStream();
					String fileName9 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc9 = dmService.createDocument(fileName9, 
							word9, 
							DmService.BUSI_TYPE_LOAN, 
							CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
							carContract.getContractCode(), 
							UserUtils.getUser().getId()
							);
					//9.特别说明
					String wordUrl10 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM_ZQ,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl10 = new URL(wordUrl10);
					URLConnection wordUrlcon10 = wUrl10.openConnection();
					// 获取连接
					wordUrlcon10.connect();
					// 获取流
					InputStream word10 = wordUrlcon10.getInputStream();
					String fileName10 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc10 = dmService.createDocument(fileName10, 
							word10, 
							DmService.BUSI_TYPE_LOAN, 
							CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
							carContract.getContractCode(), 
							UserUtils.getUser().getId()
							);
					if (doc10.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName10, doc10.getDocId(),
								CarExtendType.CAREXTENDTYPE_TBSM);
					}
					
					//修改合同表的状态添加docId
					carContractNew.setDocId(doc1.getDocId()
							+ "," + doc2.getDocId()
							+ "," + doc3.getDocId()
							+ "," + doc4.getDocId()
							+ "," + doc5.getDocId()
							+ "," + doc6.getDocId()
							+ "," + doc7.getDocId()
							+ "," + doc8.getDocId()
							+ "," + doc9.getDocId()
							+ "," + doc10.getDocId()
							);
				}else{
					//1.车借展期协议
					String wordUrl1 = carContractUtils.getLoanCarAllURL(carContractUtils.CJZQXY_ZY,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl1 = new URL(wordUrl1);
					URLConnection wordUrlcon1 = wUrl1.openConnection();
					// 获取连接
					wordUrlcon1.connect();
					// 获取流
					InputStream word1 = wordUrlcon1.getInputStream();
					String fileName1 = "车借展期协议_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc1 = dmService.createDocument(fileName1, 
									word1, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					//2.还款管理服务说明书
					String wordUrl2 = carContractUtils.getLoanCarAllURL(carContractUtils.ZQHKGLFWSMS_ZY,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl2 = new URL(wordUrl2);
					URLConnection wordUrlcon2 = wUrl2.openConnection();
					// 获取连接
					wordUrlcon2.connect();
					// 获取流
					InputStream word2 = wordUrlcon2.getInputStream();
					String fileName2 = "还款管理服务说明书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc2 = dmService.createDocument(fileName2, 
									word2, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					//3.借款人委托扣款授权书
					String wordUrl3 = carContractUtils.getLoanCarAllURL(carContractUtils.ZQJKRWTKKSQS_ZY,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl3 = new URL(wordUrl3);
					URLConnection wordUrlcon3 = wUrl3.openConnection();
					// 获取连接
					wordUrlcon3.connect();
					// 获取流
					InputStream word3 = wordUrlcon3.getInputStream();
					String fileName3 = "借款人委托扣款授权书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc3 = dmService.createDocument(fileName3, 
									word3, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					//4.车借展期协议
					String wordUrl4 = carContractUtils.getLoanCarAllURL(carContractUtils.CJZQXY_ZY,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl4 = new URL(wordUrl4);
					URLConnection wordUrlcon4 = wUrl4.openConnection();
					// 获取连接
					wordUrlcon4.connect();
					// 获取流
					InputStream word4 = wordUrlcon4.getInputStream();
					String fileName4 = "车借展期协议_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc4 = dmService.createDocument(fileName4, 
									word4, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					if (doc4.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName4, doc4.getDocId(),
								CarExtendType.CAREXTENDTYPE_PROTOCOL_ZY);
					}
					//5.还款管理服务说明书
					String wordUrl5 = carContractUtils.getLoanCarAllURL(carContractUtils.ZQHKGLFWSMS_ZY,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl5 = new URL(wordUrl5);
					URLConnection wordUrlcon5 = wUrl5.openConnection();
					// 获取连接
					wordUrlcon5.connect();
					// 获取流
					InputStream word5 = wordUrlcon5.getInputStream();
					String fileName5 = "还款管理服务说明书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc5 = dmService.createDocument(fileName5, 
									word5, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					if (doc5.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName5, doc5.getDocId(),
								CarExtendType.CAREXTENDTYPE_MANAGE_ZY);
					}
					//6.借款人委托扣款授权书
					String wordUrl6 = carContractUtils.getLoanCarAllURL(carContractUtils.ZQJKRWTKKSQS_ZY,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl6 = new URL(wordUrl6);
					URLConnection wordUrlcon6 = wUrl6.openConnection();
					// 获取连接
					wordUrlcon6.connect();
					// 获取流
					InputStream word6 = wordUrlcon6.getInputStream();
					String fileName6 = "借款人委托扣款授权书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc6 = dmService.createDocument(fileName6, 
									word6, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					if (doc6.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName6, doc6.getDocId(),
								CarExtendType.CAREXTENDTYPE_RETURN_MANAGE_ZY);
					}
					
					//7.免责声明
					String wordUrl7 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_ZQ,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl7 = new URL(wordUrl7);
					URLConnection wordUrlcon7 = wUrl7.openConnection();
					// 获取连接
					wordUrlcon7.connect();
					// 获取流
					InputStream word7 = wordUrlcon7.getInputStream();
					String fileName7 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc7 = dmService.createDocument(fileName7, 
									word7, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					//8.免责声明
					String wordUrl8 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_ZQ,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl8 = new URL(wordUrl8);
					URLConnection wordUrlcon8 = wUrl8.openConnection();
					// 获取连接
					wordUrlcon8.connect();
					// 获取流
					InputStream word8 = wordUrlcon8.getInputStream();
					String fileName8 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc8 = dmService.createDocument(fileName8, 
									word8, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
									carContract.getContractCode(), 
									UserUtils.getUser().getId()
							);
					if (doc8.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName8, doc8.getDocId(),
								CarExtendType.CAREXTENDTYPE_RETURN_MFSM_GPS_YJ);
					}
					
					//9.特别说明
					String wordUrl9 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM_ZQ,loanCode,carContractUtils.PDF,contractVersion);
					// 拼接URL
					URL wUrl9 = new URL(wordUrl9);
					URLConnection wordUrlcon9 = wUrl9.openConnection();
					// 获取连接
					wordUrlcon9.connect();
					// 获取流
					InputStream word9 = wordUrlcon9.getInputStream();
					String fileName9 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
					DocumentBean doc9 = dmService.createDocument(fileName9, 
							word9, 
							DmService.BUSI_TYPE_LOAN, 
							CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
							carContract.getContractCode(), 
							UserUtils.getUser().getId()
							);
					if (doc9.getDocId() != null) {
						crateContractFile(
								carContract.getContractCode(), fileName9, doc9.getDocId(),
								CarExtendType.CAREXTENDTYPE_TBSM);
					}
					//9.特别说明
					String wordUrl10 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM_ZQ,loanCode,carContractUtils.WORD,contractVersion);
					// 拼接URL
					URL wUrl10 = new URL(wordUrl10);
					URLConnection wordUrlcon10 = wUrl10.openConnection();
					// 获取连接
					wordUrlcon10.connect();
					// 获取流
					InputStream word10 = wordUrlcon10.getInputStream();
					String fileName10 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
					DocumentBean doc10 = dmService.createDocument(fileName10, 
							word10, 
							DmService.BUSI_TYPE_LOAN, 
							CeFolderType.CAR_EXTEND_SIGN_UPLOAD.getName(), 
							carContract.getContractCode(), 
							UserUtils.getUser().getId()
							);
					
					//修改合同表的状态添加docId
					carContractNew.setDocId(doc1.getDocId()
							+ "," + doc2.getDocId()
							+ "," + doc3.getDocId()
							+ "," + doc4.getDocId()
							+ "," + doc5.getDocId()
							+ "," + doc6.getDocId()
							+ "," + doc7.getDocId()
							+ "," + doc8.getDocId()
							+ "," + doc9.getDocId()
							+ "," + doc10.getDocId()
							);
				}
				//上传成功是修改合同表的状态
				carContractNew.preUpdate();
				carContractNew.setLoanCode(loanCode);
				carContractService.updateByLoanCode(carContractNew);
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("链接失败！！！");
				resultMap.put("flag", BooleanType.FALSE);
			}
			log.debug("汇金车借合同制作处理结束，当前时间是：" + new Date());
			return resultMap;
		}
		/**
		 * 向合同文件表插入数据
		 * 
		 * @param contractCode
		 * @param fileName
		 * @param docId
		 */
		@Transactional(value = "loanTransactionManager", readOnly = false)
		public void crateContractFile(String contractCode, String fileName,
				String docId, CarExtendType carExtendType) {
			User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			CarContractFile contractFile = new CarContractFile();
			if(StringUtils.isNotBlank(currentUser.getId())){
				contractFile.setModifyBy(currentUser.getName());
				contractFile.setCreateBy(currentUser.getName());
			}
			contractFile.setContractCode(contractCode);
			contractFile.setFileName(fileName);
			contractFile.setSendFlag("0");
			// 由于 隐私保护声明 和 富友-信和财富专用账户协议 尚未确定，所以两处穿null， 此处需要做校验，确定后，可去掉
			if (carExtendType != null) {
				contractFile.setDownloadFlag(carExtendType.getFlag());
				contractFile.setContractFileName(carExtendType.getName());
				contractFile.setFileShowOrder(carExtendType.getCode());
			}
			List<CarContractFile> findList = carContractFileService.findList(contractFile);
			if(null!=findList&&findList.size()>0){
				contractFile = findList.get(0);
				contractFile.setModifyTime(new Date());
				contractFile.setDocId(docId);
				contractFile.setSignDocId("");
				carContractFileService.updateCtrFile(contractFile);
			}else{
				contractFile.setRemarks("");
				contractFile.setCreateTime(new Date());
				contractFile.setId(IdGen.uuid());
				contractFile.setDocId(docId);
				carContractFileService.insertCtrFile(contractFile);
			}
		}
	
	/**
	 * 展期合同加盖CA签章
	 * gezhichao
	 * @param loancode 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "contractsignUpCA")
	public String contractsignUpCA(String contractCode) {
		CarContract contract = carContractDao.selectByContractCode(contractCode);
	    //String channelFlag = contract.getChannelFlag();
	    boolean result = false;
	   // if(CarLoanThroughFlag.HARMONY.getCode().equals(channelFlag) || 
		//	   CarLoanThroughFlag.P2P.getCode().equals(channelFlag)){
		   result = carGrantCAService.signUpCA(contract.getLoanCode(), contract.getContractVersion());
	  //  }
		String str =  BooleanType.FALSE;
		if(result){
			str = BooleanType.TRUE;
		}
		return str;
	}
}
