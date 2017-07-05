package com.creditharmony.loan.car.carContract.web;

																																																										import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.FlowPage;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.CarContractType;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanResult;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.Org;
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
import com.creditharmony.loan.car.carContract.ex.CarLoanPaybackBrief;
import com.creditharmony.loan.car.carContract.service.CarAuditResultService;
import com.creditharmony.loan.car.carContract.service.CarCheckRateService;
import com.creditharmony.loan.car.carContract.service.CarContractService;
import com.creditharmony.loan.car.carContract.utils.CarLoanPayBackDetailUtils;
import com.creditharmony.loan.car.carContract.utils.carContractUtils;
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.carContract.view.CarSigningCheckBusinessView;
import com.creditharmony.loan.car.carExtend.ex.CarExportCustomerDataExColumn;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.dao.CarCustomerBankInfoDao;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarContractFile;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanFlowQueryParam;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.service.CarContractFileService;
import com.creditharmony.loan.car.common.service.CarImageService;
import com.creditharmony.loan.car.common.service.CarLoanPaybackBriefSerive;
import com.creditharmony.loan.car.common.util.Arith;
import com.creditharmony.loan.car.common.util.CarCommonUtil;
import com.creditharmony.loan.car.common.view.CarLoanFlowWorkItemView;
import com.creditharmony.loan.car.common.view.FlowProperties;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.ContractCommonService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ImageUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.remote.service.OrgProxyService;
import com.creditharmony.loan.users.entity.OrgInfo;
import com.creditharmony.loan.users.service.OrgInfoService;
import com.query.ProcessQueryBuilder;

/**
 * 合同、利率审核
 * @Class Name CarContractController
 * @author 李静辉
 * @Create In 2016年2月20日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carContract/checkRate")
public class CarContractController extends BaseController {

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
	private CarCustomerBankInfoDao carCustomerBankInfoDao;
	@Autowired
	private CityInfoService cityInfoService;
	
	@Autowired
    private  CarImageService  carImageService;
	@Autowired
    private PaperlessPhotoService paperlessPhotoService;
	
	@Autowired
	private PaperLessService paperLessService;
	@Autowired
    private  CarLoanPaybackBriefSerive  carLoanPaybackBriefSerive;//合同明细Serive
	
	@Autowired
	private OrgInfoService orgInfoService;
	
	private static final Logger log = LoggerFactory.getLogger(CarContractController.class);
	/**
	 * 利率审核、合同制作、签订上传合同、合同审核
	 * 2016年2月20日
	 * By 申诗阔
	 * @param workItem
	 * @param view
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "carContractHandle")
	public String carContractHandle(WorkItemView workItem, CarCheckRateBusinessView view,FlowProperties flowProperties,HttpServletRequest request) throws IOException {
		//标红置顶业务参数
		workItem.setFlowProperties(flowProperties);
		
		String result = view.getCheckResult(); // 审批结果
		if (StringUtils.isNotBlank(result)) { // 若审批结果不为空
			if (CarLoanResponses.TO_SIGN.getCode().equals(result)) { // 审核费率 通过
				workItem.setResponse(CarLoanResponses.TO_SIGN.getCode());
				view.setComprehensiveServiceFee(view.getCarCheckRate().getComprehensiveServiceFee().doubleValue());
				workItem.setBv(view);
				flowService.dispatch(workItem);
			} else if (CarLoanResponses.BACK_END_AUDIT.getCode().equals(result)) { // 审核费率 退回
				workItem.setResponse(CarLoanResponses.BACK_END_AUDIT.getCode());
				workItem.setBv(view);
				flowService.dispatch(workItem);
			} else if (CarLoanResponses.TO_UPLOAD_CONTRACT.getCode().equals(result)) { // 合同制作 通过
				workItem.setResponse(CarLoanResponses.TO_UPLOAD_CONTRACT.getCode());
				String centerUser = view.getCenterUser();
				if(StringUtils.isNotEmpty(centerUser)){
					CarContract carContract = new CarContract();
					carContract.setLoanCode(view.getLoanCode());
					carContract.setMidId(centerUser);
					carContractService.updateByLoanCode(carContract);
				}
				
				String loanCode = view.getLoanCode();
				CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
				CarContract carContract = carContractService.getByLoanCode(loanCode);
				CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
				Map payBackMap = CarLoanPayBackDetailUtils.getPayBack(carLoanInfo, carContract, carCheckRate);
				List<CarLoanPaybackBrief> payBackList = (List<CarLoanPaybackBrief>) payBackMap.get("list");
				//根据LoanCode删除还款明细
				carLoanPaybackBriefSerive.deletePaybackBriefByLoanCode(carContract.getLoanCode());
				//向还款明细中插入值
				for(int i = 0;i<payBackList.size();i++ ){
					CarLoanPaybackBrief carLoanPaybackDetail =  payBackList.get(i); 
					carLoanPaybackDetail.setLoanPaybackBriefId(IdGen.uuid());
					carLoanPaybackDetail.setLoanPaybackId(carContract.getLoanCode());
					carLoanPaybackDetail.setCurrentLimitTimme(i+1);
					carLoanPaybackBriefSerive.insertPaybackBrief(carLoanPaybackDetail);
				}
				
				
				Map<String, Object> map = createContract(loanCode);
				if (BooleanType.FALSE.equals(map.get("flag"))) { // 若制作合同失败
					view.setDictLoanStatus(CarLoanStatus.PRODUCTION_CONTRACT_FAILURE.getName());
					workItem.setBv(view);
					carContractService.waitHandle(workItem);
					flowService.saveData(workItem);
					return BooleanType.FALSE;
				} else { // 若制作合同成功
					
					workItem.setResponse(CarLoanResponses.TO_UPLOAD_CONTRACT.getCode());
					workItem.setBv(view);
					flowService.dispatch(workItem);
				}
			} else if (CarLoanStatus.AUDIT_INTEREST_RATE.getCode().equals(result)) { // 合同制作 退回到利率审核
				workItem.setResponse(CarLoanResponses.BACK_AUDIT_RATE.getCode());
				workItem.setBv(view);
				flowService.dispatch(workItem);
			} else if (CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode().equals(result)) { // 合同制作 退回到签署
				workItem.setResponse(CarLoanResponses.BACK_SIGN.getCode());
				workItem.setBv(view);
				flowService.dispatch(workItem);
			} else if (CarLoanResponses.TO_AUDIT_CONTRACT.getCode().equals(result)) { // 合同签约上传 通过
				String loanCode = view.getLoanCode();
				Date d = carCheckRateService.selectCarCheckRateByLoanCode(loanCode).getCreateTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				Date cuD = DateUtils.addDays(d, -1);
				try {
					cuD = sdf.parse(sdf.format(d));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(cuD);
				calendar.add(Calendar.DAY_OF_MONTH, 8);
				if (!DateUtils.between(new Date(), d, calendar.getTime())) {
					return "noSubmit";
				} else {
					CarLoanInfo carLoanInfo = carLoanInfoService.selectByLoanCode(loanCode);
					//保存银行卡信息
					CarCustomerBankInfo carCustomerBankInfo = new CarCustomerBankInfo();
					carCustomerBankInfo.setLoanCode(view.getLoanCode());
					carCustomerBankInfo.setBankSigningPlatform(view.getSigningPlatformName());
					carCustomerBankInfoDao.update(carCustomerBankInfo);
					// 若为附条件或者借款状态为复审退回待签订合同，则走向补传复审
					if (CarLoanResult.CONDITIONAL_THROUGH.getName().equals(view.getConditionThroughFlag()) || CarLoanStatus.SUPPLY_BACK_PENDING_SIGNED_CONTRACT.getCode().equals(carLoanInfo.getDictLoanStatus())) {
						workItem.setResponse(CarLoanResponses.SUPPLY_SEC_AUDIT.getCode());
					} else {
						workItem.setResponse(CarLoanResponses.TO_AUDIT_CONTRACT.getCode());
					}
					
					workItem.setBv(view);
					
					// 合同签订阶段,图片合成
					//根据loanCode得到产品类型
					CarContract carContract = carContractService.getByLoanCode(loanCode);
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
			                ImageUtils.xPic(file2.getAbsolutePath(),file1.getAbsolutePath(), file3.getAbsolutePath());
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
					flowService.dispatch(workItem);
					
				}
			} else if (CarLoanResponses.TO_GRANT_CONFIRM.getCode().equals(result)) { // 合同审核 通过
				workItem.setResponse(CarLoanResponses.TO_GRANT_CONFIRM.getCode());
				//view.setContractVersion(CarCommonUtil.getNewContractVer());
				workItem.setBv(view);
				flowService.dispatch(workItem);
			} else if ("CONTRACT_CHECK_BACK".equals(result)) { // 合同审核 退回
				String backNode = view.getBackNode();
				if (CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode().equals(backNode)) { // 退回到 待确认签署
					workItem.setResponse(CarLoanResponses.BACK_SIGN.getCode());
				} else if (CarLoanStatus.PENDING_SIGNED_CONTRACT.getCode().equals(backNode)) { //退回到 待签订合同
					workItem.setResponse(CarLoanResponses.BACK_UPLOAD_CONTRACT.getCode());
				}
				workItem.setBv(view);
				flowService.dispatch(workItem);
			}
		} else {
			return BooleanType.FALSE;
		}
		return BooleanType.TRUE;
	}
	/**
	 * 合同审核列表直接退回
	 * 2016年3月31日
	 * By 甘泉
	 * @param applyId
	 * @param backNode
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "contractSendBack")
	public String contractSendBack(String applyId, String backNode){
		String result = "从待放款确认退回的单子，直接退回";
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		String loanCode = carLoanInfo.getLoanCode();
		CarCheckRateBusinessView view = new CarCheckRateBusinessView();
		view.setCheckResult(result);
		view.setApplyId(applyId);
		view.setLoanCode(loanCode);
		view.setDictLoanStatus(CarLoanStatus.PENDING_CONTRACT_REVIEW_BACK.getCode());
		// 获取workItem中的数据
		ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
		queryParam.put("applyId", applyId);
		TaskBean taskBean = flowService
				.fetchTaskItems(
						CarLoanWorkQueues.HJ_CAR_CONTRACT_CHECK
								.getWorkQueue(), queryParam,
						BaseTaskItemView.class);
		List<BaseTaskItemView> workItems = (List<BaseTaskItemView>) taskBean
				.getItemList();
		BaseTaskItemView workItem = workItems.get(0);
		WorkItemView wi = new WorkItemView();
		ReflectHandle.copy(workItem, wi);
		wi.setBv(view);
		if(CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode().equals(backNode)){
			//退回到待确认签署
			wi.setResponse(CarLoanResponses.BACK_SIGN.getCode());
		}else if(CarLoanStatus.PENDING_SIGNED_CONTRACT.getCode().equals(backNode)){
			//退回到待签订合同
			wi.setResponse(CarLoanResponses.BACK_UPLOAD_CONTRACT.getCode());
		}
		flowService.dispatch(wi);
		return "redirect:" + adminPath
				+ "/car/carLoanWorkItems/fetchTaskItems/contractCheck";
	}
	/**
	 * 合同生成
	 * 2016年4月1日
	 * By 甘泉
	 * @param loanCode
	 * @return 
	 */
	public Map<String, Object> createContract(String loanCode){
		//根据loanCode得到产品类型
		CarContract carContract = carContractService.getByLoanCode(loanCode);
		String productType = carContract.getProductType();
		String contractVersion = carContract.getContractVersion();
		//开始制作合同
		log.debug("汇金车借合同制作处理开始，当前时间是：" + new Date());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("flag", BooleanType.TRUE);
		try{
			// 获取dmService
			DmService dmService = DmService.getInstance();
			if(CarLoanProductType.GPS.getCode().equals(productType)){//产品类型为GPS
				//................................................................................
				log.debug("合同制作所需求数据检索完成，共检索合同制作数据6条！");
				//1.车辆交易授权委托书
				String wordUrl1 = carContractUtils.getLoanCarAllURL(carContractUtils.CLJYSQWTS_GPS,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl1 = new URL(wordUrl1);
				URLConnection wordUrlcon1 = wUrl1.openConnection();
				// 获取连接
				wordUrlcon1.connect();
				// 获取流
				InputStream word1 = wordUrlcon1.getInputStream();
				String fileName1 = "车辆交易授权委托书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc1 = dmService.createDocument(fileName1, 
								word1, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				/*if (doc1.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName1, doc1.getDocId(),
							CarContractType.CARCONTRACT_TRANSACTION_GPS_YJ);
					// System.out.println("Doc1 =" + doc1.getDocId());
				}*/
				//2.还款管理服务说明书
				String wordUrl2 = carContractUtils.getLoanCarAllURL(carContractUtils.HKGLFWSMS_GPS,loanCode,carContractUtils.WORD, contractVersion);
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
								"", 
								"", 
								"Car"
						);
				/*if (doc2.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName1, doc1.getDocId(),
							CarContractType.CARCONTRACT_RETURN_MANAGE_GPS_YJ);
				}*/
				//3.借款人委托扣款授权书
				String wordUrl3 = carContractUtils.getLoanCarAllURL(carContractUtils.JKRWTKKSQS_GPS,loanCode,carContractUtils.WORD, contractVersion);
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
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				/*if (doc3.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName1, doc1.getDocId(),
							CarContractType.CARCONTRACT_DELEGATE_GPS_YJ);
				}*/
				//4.借款协议(GPS类)
				String wordUrl4 = carContractUtils.getLoanCarAllURL(carContractUtils.JKXY_GPS,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl4 = new URL(wordUrl4);
				URLConnection wordUrlcon4 = wUrl4.openConnection();
				// 获取连接
				wordUrlcon4.connect();
				// 获取流
				InputStream word4 = wordUrlcon4.getInputStream();
				String fileName4 = "借款协议(GPS类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc4 = dmService.createDocument(fileName4, 
								word4, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				/*if (doc4.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName1, doc1.getDocId(),
							CarContractType.CARCONTRACT_PROTOCOL_GPS_YJ);
				}*/
				//5.借款收条
				String wordUrl5 = carContractUtils.getLoanCarAllURL(carContractUtils.ST_GPS,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl5 = new URL(wordUrl5);
				URLConnection wordUrlcon5 = wUrl5.openConnection();
				// 获取连接
				wordUrlcon5.connect();
				// 获取流
				InputStream word5 = wordUrlcon5.getInputStream();
				String fileName5 = "借款收条_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc5 = dmService.createDocument(fileName5, 
								word5, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				/*if (doc5.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName1, doc1.getDocId(),
							CarContractType.CARCONTRACT_RECEIPT_GPS_YJ);
				}*/
				//6.信用咨询及管理服务协议(GPS类)
				String wordUrl6 = carContractUtils.getLoanCarAllURL(carContractUtils.XYZXJGLFWXY_GPS,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl6 = new URL(wordUrl6);
				URLConnection wordUrlcon6 = wUrl6.openConnection();
				// 获取连接
				wordUrlcon6.connect();
				// 获取流
				InputStream word6 = wordUrlcon6.getInputStream();
				String fileName6 = "信用咨询及管理服务协议(GPS类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc6 = dmService.createDocument(fileName6, 
								word6, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				/*if (doc6.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName1, doc1.getDocId(),
							CarContractType.CAECONTRACT_MANAGE_GPS_YJ);
				}*/
				//7.车辆交易授权委托书
				String wordUrl7 = carContractUtils.getLoanCarAllURL(carContractUtils.CLJYSQWTS_GPS,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl7 = new URL(wordUrl7);
				URLConnection wordUrlcon7 = wUrl7.openConnection();
				// 获取连接
				wordUrlcon7.connect();
				// 获取流
				InputStream word7 = wordUrlcon7.getInputStream();
				String fileName7 = "车辆交易授权委托书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc7 = dmService.createDocument(fileName7, 
								word7, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc7.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName7, doc7.getDocId(),
							CarContractType.CARCONTRACT_TRANSACTION_GPS_YJ);
				}
				//8.还款管理服务说明书
				String wordUrl8 = carContractUtils.getLoanCarAllURL(carContractUtils.HKGLFWSMS_GPS,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl8 = new URL(wordUrl8);
				URLConnection wordUrlcon8 = wUrl8.openConnection();
				// 获取连接
				wordUrlcon8.connect();
				// 获取流
				InputStream word8 = wordUrlcon8.getInputStream();
				String fileName8 = "还款管理服务说明书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc8 = dmService.createDocument(fileName8, 
								word8, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc8.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName8, doc8.getDocId(),
							CarContractType.CARCONTRACT_RETURN_MANAGE_GPS_YJ);
				}
				//9.借款人委托扣款授权书
				String wordUrl9 = carContractUtils.getLoanCarAllURL(carContractUtils.JKRWTKKSQS_GPS,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl9 = new URL(wordUrl9);
				URLConnection wordUrlcon9 = wUrl9.openConnection();
				// 获取连接
				wordUrlcon9.connect();
				// 获取流
				InputStream word9 = wordUrlcon9.getInputStream();
				String fileName9 = "借款人委托扣款授权书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc9 = dmService.createDocument(fileName9, 
								word9, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc9.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName9, doc9.getDocId(),
							CarContractType.CARCONTRACT_DELEGATE_GPS_YJ);
				}
				//10.借款协议(GPS类)
				String wordUrl10 = carContractUtils.getLoanCarAllURL(carContractUtils.JKXY_GPS,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl10 = new URL(wordUrl10);
				URLConnection wordUrlcon10 = wUrl10.openConnection();
				// 获取连接
				wordUrlcon10.connect();
				// 获取流
				InputStream word10 = wordUrlcon10.getInputStream();
				String fileName10 = "借款协议(GPS类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc10 = dmService.createDocument(fileName10, 
								word10, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc10.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName10, doc10.getDocId(),
							CarContractType.CARCONTRACT_PROTOCOL_GPS_YJ);
				}
				//11.借款收条
				String wordUrl11 = carContractUtils.getLoanCarAllURL(carContractUtils.ST_GPS,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl11 = new URL(wordUrl11);
				URLConnection wordUrlcon11 = wUrl11.openConnection();
				// 获取连接
				wordUrlcon11.connect();
				// 获取流
				InputStream word11 = wordUrlcon11.getInputStream();
				String fileName11 = "借款收条_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc11 = dmService.createDocument(fileName11, 
								word11, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc11.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName11, doc11.getDocId(),
							CarContractType.CARCONTRACT_RECEIPT_GPS_YJ);
				}
				//12.信用咨询及管理服务协议(GPS类)
				String wordUrl12 = carContractUtils.getLoanCarAllURL(carContractUtils.XYZXJGLFWXY_GPS,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl12 = new URL(wordUrl12);
				URLConnection wordUrlcon12 = wUrl12.openConnection();
				// 获取连接
				wordUrlcon12.connect();
				// 获取流
				InputStream word12 = wordUrlcon12.getInputStream();
				String fileName12 = "信用咨询及管理服务协议(GPS类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc12 = dmService.createDocument(fileName12, 
								word12, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc12.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName12, doc12.getDocId(),
							CarContractType.CAECONTRACT_MANAGE_GPS_YJ);
				}
				
				//13.免责声明合同
				String wordUrl13 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_CJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl13 = new URL(wordUrl13);
				URLConnection wordUrlcon13 = wUrl13.openConnection();
				// 获取连接
				wordUrlcon13.connect();
				// 获取流
				InputStream word13 = wordUrlcon13.getInputStream();
				String fileName13 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc13 = dmService.createDocument(fileName13, 
								word13, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc13.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName13, doc13.getDocId(),
							CarContractType.CARCONTRACT_MFSM);
				}
				
				//14.免责声明合同
				String wordUrl14 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_CJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl14 = new URL(wordUrl14);
				URLConnection wordUrlcon14 = wUrl14.openConnection();
				// 获取连接
				wordUrlcon14.connect();
				// 获取流
				InputStream word14 = wordUrlcon14.getInputStream();
				String fileName14 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc14 = dmService.createDocument(fileName14, 
								word14, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//15.抵押物清单(GPS类)
				String wordUrl15 = carContractUtils.getLoanCarAllURL(carContractUtils.DYWQD_GPS_YJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl15 = new URL(wordUrl15);
				URLConnection wordUrlcon15 = wUrl15.openConnection();
				// 获取连接
				wordUrlcon15.connect();
				// 获取流
				InputStream word15 = wordUrlcon15.getInputStream();
				String fileName15 = "抵押物清单_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc15 = dmService.createDocument(fileName15, 
						word15, 
						DmService.BUSI_TYPE_LOAN, 
						CeFolderType.CAR_CONTRACT_MAKE.getName(), 
						carContract.getContractCode(), 
						UserUtils.getUser().getId()
				);
				//16.抵押物清单(GPS类)
				String wordUrl16 = carContractUtils.getLoanCarAllURL(carContractUtils.DYWQD_GPS_YJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl16 = new URL(wordUrl16);
				URLConnection wordUrlcon16 = wUrl16.openConnection();
				// 获取连接
				wordUrlcon16.connect();
				// 获取流
				InputStream word16 = wordUrlcon16.getInputStream();
				String fileName16 = "抵押物清单_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc16 = dmService.createDocument(fileName16, 
								word16, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc16.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName16, doc16.getDocId(),
							CarContractType.CARCONTRACT_BILL_GPS_YJ);
				}
				
				//17.特别说明
				String wordUrl17 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl17 = new URL(wordUrl17);
				URLConnection wordUrlcon17 = wUrl17.openConnection();
				// 获取连接
				wordUrlcon17.connect();
				// 获取流
				InputStream word17 = wordUrlcon17.getInputStream();
				String fileName17 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc17 = dmService.createDocument(fileName17, 
						word17, 
						DmService.BUSI_TYPE_LOAN, 
						CeFolderType.CAR_CONTRACT_MAKE.getName(), 
						carContract.getContractCode(), 
						UserUtils.getUser().getId()
						);
				if (doc17.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName17, doc17.getDocId(),
							CarContractType.CARCONTRACT_TBSM);
				}
				
				//17.特别说明
				String wordUrl18 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl18 = new URL(wordUrl18);
				URLConnection wordUrlcon18 = wUrl18.openConnection();
				// 获取连接
				wordUrlcon18.connect();
				// 获取流
				InputStream word18 = wordUrlcon18.getInputStream();
				String fileName18 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc18 = dmService.createDocument(fileName18, 
						word18, 
						DmService.BUSI_TYPE_LOAN, 
						CeFolderType.CAR_CONTRACT_MAKE.getName(), 
						carContract.getContractCode(), 
						UserUtils.getUser().getId()
						);
				if (doc17.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName18, doc18.getDocId(),
							CarContractType.CARCONTRACT_TBSM);
				}
				
				//修改合同表的状态添加docId  排序
				carContract.setDocId(doc4.getDocId()
							+ "," + doc6.getDocId()
							+ "," + doc2.getDocId()
							+ "," + doc3.getDocId()
							+ "," + doc5.getDocId()
							+ "," + doc1.getDocId()
							+ "," + doc10.getDocId()
							+ "," + doc12.getDocId()
							+ "," + doc8.getDocId()
							+ "," + doc9.getDocId()
							+ "," + doc11.getDocId()
							+ "," + doc7.getDocId()
							+ "," + doc13.getDocId()
							+ "," + doc14.getDocId()
							+ "," + doc17.getDocId()
							+ "," + doc18.getDocId()
							+ "," + doc15.getDocId()
							+ "," + doc16.getDocId()
							);
			}else if(CarLoanProductType.TRANSFER.getCode().equals(productType)){//产品类型为移交
				//................................................................................
				log.debug("合同制作所需求数据检索完成，共检索合同制作数据6条！");
				//1.车辆交易授权委托书
				String wordUrl1 = carContractUtils.getLoanCarAllURL(carContractUtils.CLJYSQWTS_YJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl1 = new URL(wordUrl1);
				URLConnection wordUrlcon1 = wUrl1.openConnection();
				// 获取连接
				wordUrlcon1.connect();
				// 获取流
				InputStream word1 = wordUrlcon1.getInputStream();
				String fileName1 = "车辆交易授权委托书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc1 = dmService.createDocument(fileName1, 
								word1, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//2.还款管理服务说明书
				String wordUrl2 = carContractUtils.getLoanCarAllURL(carContractUtils.HKGLFWSMS_YJ,loanCode,carContractUtils.WORD, contractVersion);
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
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//3.借款人委托扣款授权书
				String wordUrl3 = carContractUtils.getLoanCarAllURL(carContractUtils.JKRWTKKSQS_YJ,loanCode,carContractUtils.WORD, contractVersion);
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
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//4.借款协议(移交类)
				String wordUrl4 = carContractUtils.getLoanCarAllURL(carContractUtils.JKXY_YJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl4 = new URL(wordUrl4);
				URLConnection wordUrlcon4 = wUrl4.openConnection();
				// 获取连接
				wordUrlcon4.connect();
				// 获取流
				InputStream word4 = wordUrlcon4.getInputStream();
				String fileName4 = "借款协议(移交类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc4 = dmService.createDocument(fileName4, 
								word4, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//5.借款收条
				String wordUrl5 = carContractUtils.getLoanCarAllURL(carContractUtils.ST_YJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl5 = new URL(wordUrl5);
				URLConnection wordUrlcon5 = wUrl5.openConnection();
				// 获取连接
				wordUrlcon5.connect();
				// 获取流
				InputStream word5 = wordUrlcon5.getInputStream();
				String fileName5 = "借款收条_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc5 = dmService.createDocument(fileName5, 
								word5, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//6.信用咨询及管理服务协议(移交类)
				String wordUrl6 = carContractUtils.getLoanCarAllURL(carContractUtils.XYZXJGLFWXY_YJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl6 = new URL(wordUrl6);
				URLConnection wordUrlcon6 = wUrl6.openConnection();
				// 获取连接
				wordUrlcon6.connect();
				// 获取流
				InputStream word6 = wordUrlcon6.getInputStream();
				String fileName6 = "信用咨询及管理服务协议(移交类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc6 = dmService.createDocument(fileName6, 
								word6, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//7.车辆交易授权委托书
				String wordUrl7 = carContractUtils.getLoanCarAllURL(carContractUtils.CLJYSQWTS_YJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl7 = new URL(wordUrl7);
				URLConnection wordUrlcon7 = wUrl7.openConnection();
				// 获取连接
				wordUrlcon7.connect();
				// 获取流
				InputStream word7 = wordUrlcon7.getInputStream();
				String fileName7 = "车辆交易授权委托书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc7 = dmService.createDocument(fileName7, 
								word7, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc7.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName7, doc7.getDocId(),
							CarContractType.CARCONTRACT_TRANSACTION_GPS_YJ);
				}
				//8.还款管理服务说明书
				String wordUrl8 = carContractUtils.getLoanCarAllURL(carContractUtils.HKGLFWSMS_YJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl8 = new URL(wordUrl8);
				URLConnection wordUrlcon8 = wUrl8.openConnection();
				// 获取连接
				wordUrlcon8.connect();
				// 获取流
				InputStream word8 = wordUrlcon8.getInputStream();
				String fileName8 = "还款管理服务说明书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc8 = dmService.createDocument(fileName8, 
								word8, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc8.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName8, doc8.getDocId(),
							CarContractType.CARCONTRACT_RETURN_MANAGE_GPS_YJ);
				}
				//9.借款人委托扣款授权书
				String wordUrl9 = carContractUtils.getLoanCarAllURL(carContractUtils.JKRWTKKSQS_YJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl9 = new URL(wordUrl9);
				URLConnection wordUrlcon9 = wUrl9.openConnection();
				// 获取连接
				wordUrlcon9.connect();
				// 获取流
				InputStream word9 = wordUrlcon9.getInputStream();
				String fileName9 = "借款人委托扣款授权书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc9 = dmService.createDocument(fileName9, 
								word9, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc9.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName9, doc9.getDocId(),
							CarContractType.CARCONTRACT_DELEGATE_GPS_YJ);
				}
				//10.借款协议(移交类)
				String wordUrl10 = carContractUtils.getLoanCarAllURL(carContractUtils.JKXY_YJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl10 = new URL(wordUrl10);
				URLConnection wordUrlcon10 = wUrl10.openConnection();
				// 获取连接
				wordUrlcon10.connect();
				// 获取流
				InputStream word10 = wordUrlcon10.getInputStream();
				String fileName10 = "借款协议(移交类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc10 = dmService.createDocument(fileName10, 
								word10, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc10.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName10, doc10.getDocId(),
							CarContractType.CARCONTRACT_PROTOCOL_GPS_YJ);
				}
				//11.借款收条
				String wordUrl11 = carContractUtils.getLoanCarAllURL(carContractUtils.ST_YJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl11 = new URL(wordUrl11);
				URLConnection wordUrlcon11 = wUrl11.openConnection();
				// 获取连接
				wordUrlcon11.connect();
				// 获取流
				InputStream word11 = wordUrlcon11.getInputStream();
				String fileName11 = "借款收条_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc11 = dmService.createDocument(fileName11, 
								word11, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc11.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName11, doc11.getDocId(),
							CarContractType.CARCONTRACT_RECEIPT_GPS_YJ);
				}
				//12.信用咨询及管理服务协议(移交类)
				String wordUrl12 = carContractUtils.getLoanCarAllURL(carContractUtils.XYZXJGLFWXY_YJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl12 = new URL(wordUrl12);
				URLConnection wordUrlcon12 = wUrl12.openConnection();
				// 获取连接
				wordUrlcon12.connect();
				// 获取流
				InputStream word12 = wordUrlcon12.getInputStream();
				String fileName12 = "信用咨询及管理服务协议(移交类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc12 = dmService.createDocument(fileName12, 
								word12, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc12.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName12, doc12.getDocId(),
							CarContractType.CAECONTRACT_MANAGE_GPS_YJ);
				}

				//13.免责声明合同
				String wordUrl13 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_CJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl13 = new URL(wordUrl13);
				URLConnection wordUrlcon13 = wUrl13.openConnection();
				// 获取连接
				wordUrlcon13.connect();
				// 获取流
				InputStream word13 = wordUrlcon13.getInputStream();
				String fileName13 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc13 = dmService.createDocument(fileName13, 
								word13, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc13.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName13, doc13.getDocId(),
							CarContractType.CARCONTRACT_MFSM);
				}
				
				//14.免责声明合同
				String wordUrl14 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_CJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl14 = new URL(wordUrl14);
				URLConnection wordUrlcon14 = wUrl14.openConnection();
				// 获取连接
				wordUrlcon14.connect();
				// 获取流
				InputStream word14 = wordUrlcon14.getInputStream();
				String fileName14 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc14 = dmService.createDocument(fileName14, 
								word14, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				
				//15.抵押物清单
				String wordUrl15 = carContractUtils.getLoanCarAllURL(carContractUtils.DYWQD_GPS_YJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl15 = new URL(wordUrl15);
				URLConnection wordUrlcon15 = wUrl15.openConnection();
				// 获取连接
				wordUrlcon15.connect();
				// 获取流
				InputStream word15 = wordUrlcon15.getInputStream();
				String fileName15 = "抵押物清单_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc15 = dmService.createDocument(fileName15, 
						word15, 
						DmService.BUSI_TYPE_LOAN, 
						CeFolderType.CAR_CONTRACT_MAKE.getName(), 
						carContract.getContractCode(), 
						UserUtils.getUser().getId()
				);
				//16.抵押物清单
				String wordUrl16 = carContractUtils.getLoanCarAllURL(carContractUtils.DYWQD_GPS_YJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl16 = new URL(wordUrl16);
				URLConnection wordUrlcon16 = wUrl16.openConnection();
				// 获取连接
				wordUrlcon16.connect();
				// 获取流
				InputStream word16 = wordUrlcon16.getInputStream();
				String fileName16 = "抵押物清单_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc16 = dmService.createDocument(fileName16, 
								word16, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc16.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName16, doc16.getDocId(),
							CarContractType.CARCONTRACT_BILL_GPS_YJ);
				}
				
				//17.特别说明
				String wordUrl17 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl17 = new URL(wordUrl17);
				URLConnection wordUrlcon17 = wUrl17.openConnection();
				// 获取连接
				wordUrlcon17.connect();
				// 获取流
				InputStream word17 = wordUrlcon17.getInputStream();
				String fileName17 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc17 = dmService.createDocument(fileName17, 
						word17, 
						DmService.BUSI_TYPE_LOAN, 
						CeFolderType.CAR_CONTRACT_MAKE.getName(), 
						carContract.getContractCode(), 
						UserUtils.getUser().getId()
						);
//				if (doc17.getDocId() != null) {
//					crateContractFile(
//							carContract.getContractCode(), fileName17, doc17.getDocId(),
//							CarContractType.CARCONTRACT_TBSM);
//				}
				//17.特别说明
				String wordUrl18 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl18 = new URL(wordUrl18);
				URLConnection wordUrlcon18 = wUrl18.openConnection();
				// 获取连接
				wordUrlcon18.connect();
				// 获取流
				InputStream word18 = wordUrlcon18.getInputStream();
				String fileName18 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc18 = dmService.createDocument(fileName18, 
						word18, 
						DmService.BUSI_TYPE_LOAN, 
						CeFolderType.CAR_CONTRACT_MAKE.getName(), 
						carContract.getContractCode(), 
						UserUtils.getUser().getId()
						);
				if (doc18.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName18, doc18.getDocId(),
							CarContractType.CARCONTRACT_TBSM);
				}
				
				//修改合同表的状态添加docId
				carContract.setDocId(doc4.getDocId()
						+ "," + doc6.getDocId()
						+ "," + doc2.getDocId()
						+ "," + doc3.getDocId()
						+ "," + doc5.getDocId()
						+ "," + doc1.getDocId()
						+ "," + doc10.getDocId()
						+ "," + doc12.getDocId()
						+ "," + doc8.getDocId()
						+ "," + doc9.getDocId()
						+ "," + doc11.getDocId()
						+ "," + doc7.getDocId()
						+ "," + doc13.getDocId()
						+ "," + doc14.getDocId()
						+ "," + doc17.getDocId()
						+ "," + doc18.getDocId()
						+ "," + doc15.getDocId()
						+ "," + doc16.getDocId()
						);
			}else if(CarLoanProductType.PLEDGE.getCode().equals(productType)){//产品类型为质押
				log.debug("合同制作所需求数据检索完成，共检索合同制作数据8条！");
				//................................................................................
				//1.车辆交易授权委托书
				String wordUrl1 = carContractUtils.getLoanCarAllURL(carContractUtils.CLJYSQWTS_ZY,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl1 = new URL(wordUrl1);
				URLConnection wordUrlcon1 = wUrl1.openConnection();
				// 获取连接
				wordUrlcon1.connect();
				// 获取流
				InputStream word1 = wordUrlcon1.getInputStream();
				String fileName1 = "车辆交易授权委托书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc1 = dmService.createDocument(fileName1, 
								word1, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//2.还款管理服务说明书
				String wordUrl2 = carContractUtils.getLoanCarAllURL(carContractUtils.HKGLFWSMS_ZY,loanCode,carContractUtils.WORD, contractVersion);
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
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//3.借款人委托扣款授权书
				String wordUrl3 = carContractUtils.getLoanCarAllURL(carContractUtils.JKRWTKKSQS_ZY,loanCode,carContractUtils.WORD, contractVersion);
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
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//4.借款协议(质押类)
				String wordUrl4 = carContractUtils.getLoanCarAllURL(carContractUtils.JKXY_ZY,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl4 = new URL(wordUrl4);
				URLConnection wordUrlcon4 = wUrl4.openConnection();
				// 获取连接
				wordUrlcon4.connect();
				// 获取流
				InputStream word4 = wordUrlcon4.getInputStream();
				String fileName4 = "借款协议(质押类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc4 = dmService.createDocument(fileName4, 
								word4, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//5.借款收条
				String wordUrl5 = carContractUtils.getLoanCarAllURL(carContractUtils.ST_ZY,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl5 = new URL(wordUrl5);
				URLConnection wordUrlcon5 = wUrl5.openConnection();
				// 获取连接
				wordUrlcon5.connect();
				// 获取流
				InputStream word5 = wordUrlcon5.getInputStream();
				String fileName5 = "借款收条_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc5 = dmService.createDocument(fileName5, 
								word5, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//6.信用咨询及管理服务协议(质押类)
				String wordUrl6 = carContractUtils.getLoanCarAllURL(carContractUtils.XYZXJGLFWXY_ZY,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl6 = new URL(wordUrl6);
				URLConnection wordUrlcon6 = wUrl6.openConnection();
				// 获取连接
				wordUrlcon6.connect();
				// 获取流
				InputStream word6 = wordUrlcon6.getInputStream();
				String fileName6 = "信用咨询及管理服务协议(质押类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc6 = dmService.createDocument(fileName6, 
								word6, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//7.二手车买卖合同
				String wordUrl7 = carContractUtils.getLoanCarAllURL(carContractUtils.ESCMMHT_ZY,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl7 = new URL(wordUrl7);
				URLConnection wordUrlcon7 = wUrl7.openConnection();
				// 获取连接
				wordUrlcon7.connect();
				// 获取流
				InputStream word7 = wordUrlcon7.getInputStream();
				String fileName7 = "二手车买卖合同_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc7 = dmService.createDocument(fileName7, 
								word7, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//8.汽车质押合同
				String wordUrl8 = carContractUtils.getLoanCarAllURL(carContractUtils.JDCZYHT_ZY,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl8 = new URL(wordUrl8);
				URLConnection wordUrlcon8 = wUrl8.openConnection();
				// 获取连接
				wordUrlcon8.connect();
				// 获取流
				InputStream word8 = wordUrlcon8.getInputStream();
				String fileName8 = "汽车质押合同_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc8 = dmService.createDocument(fileName8, 
								word8, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//9.车辆交易授权委托书
				String wordUrl9 = carContractUtils.getLoanCarAllURL(carContractUtils.CLJYSQWTS_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl9 = new URL(wordUrl9);
				URLConnection wordUrlcon9 = wUrl9.openConnection();
				// 获取连接
				wordUrlcon9.connect();
				// 获取流
				InputStream word9 = wordUrlcon9.getInputStream();
				String fileName9 = "车辆交易授权委托书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc9 = dmService.createDocument(fileName9, 
								word9, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc9.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName9, doc9.getDocId(),
							CarContractType.CARCONTRACT_TRANSACTION_ZY);
				}
				//10.还款管理服务说明书
				String wordUrl10 = carContractUtils.getLoanCarAllURL(carContractUtils.HKGLFWSMS_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl10 = new URL(wordUrl10);
				URLConnection wordUrlcon10 = wUrl10.openConnection();
				// 获取连接
				wordUrlcon10.connect();
				// 获取流
				InputStream word10 = wordUrlcon10.getInputStream();
				String fileName10 = "还款管理服务说明书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc10 = dmService.createDocument(fileName10, 
								word10, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc10.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName10, doc10.getDocId(),
							CarContractType.CARCONTRACT_RETURN_MANAGE_ZY);
				}
				//11.借款人委托扣款授权书
				String wordUrl11 = carContractUtils.getLoanCarAllURL(carContractUtils.JKRWTKKSQS_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl11 = new URL(wordUrl11);
				URLConnection wordUrlcon11 = wUrl11.openConnection();
				// 获取连接
				wordUrlcon11.connect();
				// 获取流
				InputStream word11 = wordUrlcon11.getInputStream();
				String fileName11 = "借款人委托扣款授权书_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc11 = dmService.createDocument(fileName11, 
								word11, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc11.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName11, doc11.getDocId(),
							CarContractType.CARCONTRACT_DELEGATE_ZY);
				}
				//12.借款协议(质押类)
				String wordUrl12 = carContractUtils.getLoanCarAllURL(carContractUtils.JKXY_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl12 = new URL(wordUrl12);
				URLConnection wordUrlcon12 = wUrl12.openConnection();
				// 获取连接
				wordUrlcon12.connect();
				// 获取流
				InputStream word12 = wordUrlcon12.getInputStream();
				String fileName12 = "借款协议(质押类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc12 = dmService.createDocument(fileName12, 
								word12, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc12.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName12, doc12.getDocId(),
							CarContractType.CARCONTRACT_PROTOCOL_ZY);
				}
				//13.借款收条
				String wordUrl13 = carContractUtils.getLoanCarAllURL(carContractUtils.ST_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl13 = new URL(wordUrl13);
				URLConnection wordUrlcon13 = wUrl13.openConnection();
				// 获取连接
				wordUrlcon13.connect();
				// 获取流
				InputStream word13 = wordUrlcon13.getInputStream();
				String fileName13 = "借款收条_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc13 = dmService.createDocument(fileName13, 
								word13, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc13.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName13, doc13.getDocId(),
							CarContractType.CARCONTRACT_RECEIPT_ZY);
				}
				//14.信用咨询及管理服务协议(质押类)
				String wordUrl14 = carContractUtils.getLoanCarAllURL(carContractUtils.XYZXJGLFWXY_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl14 = new URL(wordUrl14);
				URLConnection wordUrlcon14 = wUrl14.openConnection();
				// 获取连接
				wordUrlcon14.connect();
				// 获取流
				InputStream word14 = wordUrlcon14.getInputStream();
				String fileName14 = "信用咨询及管理服务协议(质押类)_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc14 = dmService.createDocument(fileName14, 
								word14, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc14.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName14, doc14.getDocId(),
							CarContractType.CAECONTRACT_MANAGE_ZY);
				}
				//15.二手车买卖合同
				String wordUrl15 = carContractUtils.getLoanCarAllURL(carContractUtils.ESCMMHT_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl15 = new URL(wordUrl15);
				URLConnection wordUrlcon15 = wUrl15.openConnection();
				// 获取连接
				wordUrlcon15.connect();
				// 获取流
				InputStream word15 = wordUrlcon15.getInputStream();
				String fileName15 = "二手车买卖合同_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc15 = dmService.createDocument(fileName15, 
								word15, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc15.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName15, doc15.getDocId(),
							CarContractType.CARCONTRACT_BILL_ZY);
				}
				//16.汽车质押合同
				String wordUrl16 = carContractUtils.getLoanCarAllURL(carContractUtils.JDCZYHT_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl16 = new URL(wordUrl16);
				URLConnection wordUrlcon16 = wUrl16.openConnection();
				// 获取连接
				wordUrlcon16.connect();
				// 获取流
				InputStream word16 = wordUrlcon16.getInputStream();
				String fileName16 = "汽车质押合同_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc16 = dmService.createDocument(fileName16, 
								word16, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc16.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName16, doc16.getDocId(),
							CarContractType.CARCONTRACT_CAR_ZY);
				}
				//13.免责声明合同
				String wordUrl17 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_CJ,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl17 = new URL(wordUrl17);
				URLConnection wordUrlcon17 = wUrl17.openConnection();
				// 获取连接
				wordUrlcon17.connect();
				// 获取流
				InputStream word17 = wordUrlcon17.getInputStream();
				String fileName17 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc17 = dmService.createDocument(fileName17, 
								word17, 
								DmService.BUSI_TYPE_LOAN, 
 								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc17.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName17, doc17.getDocId(),
							CarContractType.CARCONTRACT_MFSM);
				}
				
				//18.免责声明合同
				String wordUrl18 = carContractUtils.getLoanCarAllURL(carContractUtils.MFSM_CJ,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl18 = new URL(wordUrl18);
				URLConnection wordUrlcon18 = wUrl18.openConnection();
				// 获取连接
				wordUrlcon18.connect();
				// 获取流
				InputStream word18 = wordUrlcon18.getInputStream();
				String fileName18 = "免责声明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc18 = dmService.createDocument(fileName18, 
								word18, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				
				//19.质押物清单
				String wordUrl19 = carContractUtils.getLoanCarAllURL(carContractUtils.ZYWQD_ZY,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl19 = new URL(wordUrl19);
				URLConnection wordUrlcon19 = wUrl19.openConnection();
				// 获取连接
				wordUrlcon19.connect();
				// 获取流
				InputStream word19 = wordUrlcon19.getInputStream();
				String fileName19 = "质押物清单_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc19 = dmService.createDocument(fileName19, 
								word19, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				//19.质押物清单
				String wordUrl20 = carContractUtils.getLoanCarAllURL(carContractUtils.ZYWQD_ZY,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl20 = new URL(wordUrl20);
				URLConnection wordUrlcon20 = wUrl20.openConnection();
				// 获取连接
				wordUrlcon20.connect();
				// 获取流
				InputStream word20 = wordUrlcon20.getInputStream();
				String fileName20 = "质押物清单_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc20 = dmService.createDocument(fileName20, 
								word20, 
								DmService.BUSI_TYPE_LOAN, 
								CeFolderType.CAR_CONTRACT_MAKE.getName(), 
								carContract.getContractCode(), 
								UserUtils.getUser().getId()
						);
				if (doc20.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName20, doc20.getDocId(),
							CarContractType.CONTRACT_BILL_ZY);
				}
				
				
				//17.特别说明
				String wordUrl21 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM,loanCode,carContractUtils.WORD, contractVersion);
				// 拼接URL
				URL wUrl21 = new URL(wordUrl21);
				URLConnection wordUrlcon21 = wUrl21.openConnection();
				// 获取连接
				wordUrlcon21.connect();
				// 获取流
				InputStream word21 = wordUrlcon21.getInputStream();
				String fileName21 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".doc";
				DocumentBean doc21 = dmService.createDocument(fileName21, 
						word21, 
						DmService.BUSI_TYPE_LOAN, 
						CeFolderType.CAR_CONTRACT_MAKE.getName(), 
						carContract.getContractCode(), 
						UserUtils.getUser().getId()
						);
				if (doc21.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName21, doc21.getDocId(),
							CarContractType.CARCONTRACT_TBSM);
				}
				//17.特别说明
				String wordUrl22 = carContractUtils.getLoanCarAllURL(carContractUtils.TBSM,loanCode,carContractUtils.PDF, contractVersion);
				// 拼接URL
				URL wUrl22 = new URL(wordUrl22);
				URLConnection wordUrlcon22 = wUrl22.openConnection();
				// 获取连接
				wordUrlcon22.connect();
				// 获取流
				InputStream word22 = wordUrlcon22.getInputStream();
				String fileName22 = "特别说明_" + carContractUtils.dateToString(new Date(), "yyyyMMddhhmmss") + ".pdf";
				DocumentBean doc22 = dmService.createDocument(fileName22, 
						word22, 
						DmService.BUSI_TYPE_LOAN, 
						CeFolderType.CAR_CONTRACT_MAKE.getName(), 
						carContract.getContractCode(), 
						UserUtils.getUser().getId()
						);
				if (doc22.getDocId() != null) {
					crateContractFile(
							carContract.getContractCode(), fileName22, doc22.getDocId(),
							CarContractType.CARCONTRACT_TBSM);
				}
				
				//修改合同表的状态添加docId
				carContract.setDocId(doc4.getDocId()
						+ "," + doc6.getDocId()
						+ "," + doc2.getDocId()
						+ "," + doc3.getDocId()
						+ "," + doc5.getDocId()
						+ "," + doc1.getDocId()
						+ "," + doc7.getDocId()
						+ "," + doc8.getDocId()
						+ "," + doc12.getDocId()
						+ "," + doc14.getDocId()
						+ "," + doc10.getDocId()
						+ "," + doc11.getDocId()
						+ "," + doc13.getDocId()
						+ "," + doc9.getDocId()
						+ "," + doc15.getDocId()
						+ "," + doc16.getDocId()
						+ "," + doc17.getDocId()
						+ "," + doc18.getDocId()
						+ "," + doc21.getDocId()
						+ "," + doc22.getDocId()
						+ "," + doc19.getDocId()
						+ "," + doc20.getDocId()
						);
			}
			//上传成功是修改合同表的状态
			CarContract carContractNew = new CarContract();
			carContractNew.preUpdate();
			carContractNew.setLoanCode(carContract.getLoanCode());
			carContractNew.setDocId(carContract.getDocId());
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
	 * 到合同协议查看/下载页面
	 * 2016年4月7日
	 * By 甘泉
	 * @param loanCode
	 * @param flag
	 * @return 
	 */
	@RequestMapping(value = "contractDownload")
	public String contractDownload(String loanCode,String flag,Model model){
		//1.得到docId
		CarContract carContract = carContractService.getByLoanCode(loanCode);
		return contractDownloadCommon(carContract.getDocId(), flag, model);
	}
	
	/**
	 * 到合同协议CA签章查看/下载页面
	 * 2016年6月3日
	 * By 葛志超
	 * @param loanCode
	 * @param flag
	 * @return 
	 */
	@RequestMapping("initPreviewSignContract")
	public String initPreviewSignContract(Model model,String contractCode){
	    List<CarContractFile> files = carContractFileService.findContractFileByContractCode(contractCode);
        model.addAttribute("files", files);
        model.addAttribute("signed", "1");
        return "car/done/contractFilePreview"; 
	}
	
	@RequestMapping(value="xieyiList")
	public String findXieYi(String loanCode, String contractCode,Model model){
		List<CarContractFile> files = new ArrayList<CarContractFile>();
		CarContractFile cafile=null;
		CarContract carContract = carContractService.getByLoanCode(loanCode);
        String docIds = carContract.getDocId();
        String[] docId = null;
        if(!StringUtils.isEmpty(docIds)){
            if(docIds.indexOf(",")!=-1){
                docId = docIds.split(",");
            } else{
                docId = new String[1];
                docId[0] = docIds;
            }		
            if(docId != null && docId.length > 0){
				for (int i = 0; i < docId.length; i++) {
					cafile =carContractFileService.findContractFileByDocId(docId[i]);
					if (null!=cafile&&StringUtils.isNotEmpty(cafile.getFileName())){
						files.add(cafile);
					}
				}
			}
        }
        model.addAttribute("files", files);
        model.addAttribute("docId",docId);
		return "car/contract/xieYiList1";
	}
	/**
	 * CreatBy 陈伟丽 2016-5-26
	 * @param loanCode
	 * @param contractCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="xieyiLookList")
	public String findXieYiLook(String applyId, String contractCode,Model model){
		List<CarContractFile> files = new ArrayList<CarContractFile>();
		CarContractFile cafile=null;
		CarLoanInfo car = carLoanInfoService.selectByApplyId(applyId);
		CarContract carContract = carContractService.getByLoanCode(car.getLoanCode());
		
        String docIds = carContract.getDocId();
        String[] docId = null;
        if(!StringUtils.isEmpty(docIds)){
            if(docIds.indexOf(",")!=-1){
                docId = docIds.split(",");
            } else{
                docId = new String[1];
                docId[0] = docIds;
            }		
            if(docId != null && docId.length > 0){
				for (int i = 0; i < docId.length; i++) {
					cafile =carContractFileService.findContractFileByDocId(docId[i]);
					if (null!=cafile&&StringUtils.isNotEmpty(cafile.getFileName())){
						files.add(cafile);
					}
				}
			}
        }
        model.addAttribute("files", files);
        model.addAttribute("docId",docId);
		return "car/contract/xieYiList1";
	}
	
	/**
	 * 单个的合同协议下载
	 * 2016年4月7日
	 * By 甘泉
	 * @param docId
	 * @return 
	 */
	@RequestMapping(value = "contractDownloadOne")
	public void contractDownloadOne(String docId,HttpServletResponse response){
		try {
			DmService dmService = DmService.getInstance();
			DocumentBean doc = dmService.getDocument(docId);
			if(StringUtils.endsWith(doc.getDocTitle(), ".doc")){
				response.setContentType("application/msword");
				response.addHeader("Content-disposition", "attachment;filename=" + new String((doc.getDocTitle()+".doc").getBytes("gbk"), "ISO-8859-1"));
			}else if(StringUtils.endsWith(doc.getDocTitle(), ".pdf")){
				response.setContentType("application/pdf");
				response.addHeader("Content-disposition", "attachment;filename=" + new String((doc.getDocTitle()+".pdf").getBytes("gbk"), "ISO-8859-1"));
			}
			// 获取dmService
			dmService.download(response.getOutputStream() , docId);
		} catch (Exception e) {
			System.err.println("下载失败");
		}
	}
	
	/**
	 * 单个的合同协议预览
	 * 2016年4月8日
	 * By 甘泉
	 * @param docId
	 * @return 
	 */
	@RequestMapping(value = "contractPreviewOne")
	public void contractPreviewOne(HttpServletRequest request, HttpServletResponse response,String docId,Model model){
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			DmService dmService = DmService.getInstance();
			InputStream in = dmService.downloadDocument(docId);
			com.creditharmony.dm.file.util.FileUtil.writeFile(os, in);
			os.flush();
			os.close();
			os=null;
			response.flushBuffer();
		} catch (Exception e) {
		}
	}
	@RequestMapping(value = "contractPreviewOneShow")
	public String contractPreviewOneShow(HttpServletRequest request, HttpServletResponse response,String docId,Model model){
		return "car/done/contractFilePreviewShow";
	}
	
	
	
	@RequestMapping(value = "showImg")
	public void showImg(HttpServletRequest request, HttpServletResponse response,String docId,Model model){
		try {
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/*");
			String dirPath = SpringContextHolder.getApplicationContext().getResource("/WEB-INF/template/"+docId).getFile().getAbsolutePath();
			String tifPath = dirPath.substring(0, dirPath.length()-1)+File.separator+docId;
			String pdfPath = tifPath+".tif";
            response.setContentType("image/*");
            BufferedImage output = ImageIO.read(new File(pdfPath));
            OutputStream out = response.getOutputStream();
    		ImageIO.write(output, "TIF", out);
    		out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 合同确认签署
	 * 2016年2月19日
	 * By 李静辉
	 * @param redirectAttributes 重定向
	 * @param workItemView 流程工作项
	 * @param view 业务视图
	 * @param redirectUrl 重定向URL
	 * @param flowFlag  重定向之后返回的视图名字
	 * @return
	 */
	@RequestMapping(value="carSigningCheckHandle")
	public String carSigningCheckHandle(RedirectAttributes redirectAttributes,WorkItemView workItemView,
			CarSigningCheckBusinessView view, String redirectUrl, String flowFlag,FlowProperties flowProperties){
		//标红置顶标志
		workItemView.setFlowProperties(flowProperties);
		if (!CarLoanResponses.SIGN_GIVE_UP.getCode().equals(workItemView.getResponse()) 
				&& !CarLoanResponses.TO_FRIST_AUDIT.getCode().equals(workItemView.getResponse())) {
			if(null != view && null != view.getCarCustomerBankInfo() && null != view.getCarContract() && null!=view.getCarCustomerBankInfo().getId()){
				CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.get(view.getCarCustomerBankInfo().getId());
				HashMap<String, Object> map = new HashMap<String, Object>();
				if (!carCustomerBankInfo.getBankCardNo().equals(view.getCarCustomerBankInfo().getBankCardNo())) {
					map.put("bankCardNo", view.getCarCustomerBankInfo().getBankCardNo());
				}
				if (!carCustomerBankInfo.getCardBank().equals(view.getCarCustomerBankInfo().getCardBank())) {
					map.put("cardBank", view.getCarCustomerBankInfo().getCardBank());
				}
				if (!map.isEmpty()) {
					CarLoanInfo caLI = carLoanInfoService.selectByLoanCode(view.getLoanCode());
					flowService.saveDataByApplyId(caLI.getApplyId(), map);
				}
				carCustomerBankInfo.setBankAccountName(CarCommonUtil.replaceSpot(view.getCarCustomerBankInfo().getBankAccountName()));
				carCustomerBankInfo.setIsrare(view.getCarCustomerBankInfo().getIsrare());
				carCustomerBankInfo.setBankProvince(view.getCarCustomerBankInfo().getBankProvince());
				carCustomerBankInfo.setBankCity(view.getCarCustomerBankInfo().getBankCity());
				carCustomerBankInfo.setCardBank(view.getCarCustomerBankInfo().getCardBank());
				carCustomerBankInfo.setBankCardNo(view.getCarCustomerBankInfo().getBankCardNo());
				carCustomerBankInfo.setApplyBankName(view.getCarCustomerBankInfo().getApplyBankName());
				carCustomerBankInfo.setBankSigningPlatform(view.getCarCustomerBankInfo().getBankSigningPlatform());
				CarContract carContract = new CarContract();
				carContract.setLoanCode(view.getLoanCode());
				carContract.setContractDueDay(view.getCarContract().getContractDueDay());
				carContractService.carSigningCheckHandle(carCustomerBankInfo,carContract);
			}
		}
		if (ObjectHelper.isEmpty(workItemView.getResponse())) {
			workItemView.setResponse(null);
		}
		
		//流程转向制作合同
		//workItemView.setResponse(CarLoanResponses.TO_MAKE_CONTRACT.getCode());
		workItemView.setBv(view);
		flowService.dispatch(workItemView);

		if (flowFlag != null && flowFlag.trim().length() != 0) {
			redirectAttributes.addAttribute("flowFlag", flowFlag);
		}
		return "redirect:" + adminPath + redirectUrl;
	}
	
	/**
	 * 通过applyId校验车借门店编号是否为空
	 * 2016年2月24日
	 * By 申诗阔
	 * @param applyId
	 * @return
	 */
	@SuppressWarnings("finally")
	@ResponseBody
	@RequestMapping(value = "checkStoreCodeIsEmpty")
	public String checkStoreCodeIsEmpty(String applyId) {
		String str = BooleanType.FALSE;
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId); // 通过aplyId获取借款信息
		Org org = OrgCache.getInstance().get(carLoanInfo.getStoreCode()); // 通过借款信息表中storeCode（目前存储的是门店id）,来获取门店
		if (org == null || StringUtils.isEmpty(org.getCarLoanCode())) {
			str = BooleanType.TRUE; // 机构或门店code为空
		} else if (StringUtils.isEmpty(org.getProvinceId()) || StringUtils.isEmpty(org.getCityId())) {
			str = "pCEmpty"; // 机构 省 市id为null
		} else {
			CityInfo cityInfo = cityInfoService.get(org.getCityId());
			if (cityInfo == null || StringUtils.isEmpty(cityInfo.getCityCarCode())) {
				str = "cCodeEmpty"; // 市 或市的车借城市编码 为null
			} else {
				try {
					// 生成合同编号
					carContractService.generalContractNo(applyId);
				} catch (Exception e) {
					e.printStackTrace();
					str = "geneFalse";
				} finally {
					return str;
				}
			}
		}
		return str;
	}
	
	/**
	 * 若生成合同时候，车借门店编码为null，则更新车借门店编码
	 * 2016年2月25日
	 * By 申诗阔
	 * @param applyId
	 * @param carLoanCode
	 * @return
	 */
	@SuppressWarnings("finally")
	@ResponseBody
	@RequestMapping(value = "updateOrgCarStoreCode")
	public String updateOrgCarStoreCode(String applyId, String carLoanCode) {
		String str = BooleanType.FALSE;
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId); // 通过aplyId获取借款信息
		Org org = OrgCache.getInstance().get(carLoanInfo.getStoreCode());
		// 由于机构是同步的，故这里通过  cityId  和  门店车借编码  验证是否已经  此城市门店编码  是否已存在
		OrgInfo orgInfo = orgInfoService.getByCityIdAndCarLoanCode(org.getCityId(), carLoanCode);
		if (orgInfo != null) {
			str = "carExist";
		} else {
			boolean flag = orgProxyService.updateCarLoanCode(carLoanInfo.getStoreCode(), carLoanCode);
			if (flag) {
				try {
					// 生成合同编号
					carContractService.generalContractNo(applyId);
					str = BooleanType.TRUE;
				} catch (Exception e) {
					e.printStackTrace();
					str = "geneFalse";
				} finally {
					return str;
				}
			}
		}
		return str;
	}
	
	/**
	 * 车借--通过利息率 的值计算 各项费用，供页面使用
	 * 2016年2月26日
	 * By 申诗阔
	 * @param rate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "calculateLoanEveryAmount")
	public Map<String, Double> calculateLoanEveryAmount(String loanCode, Double rate,Double outVisitFee) {
		Map<String, Double> everyAmountMap = new HashMap<String, Double>();
		Double monthRa = 0d; // 利息（月）：借款总额  * 利息率
		Double firstServerAmount = 0d; // 首期服务费
		Double auditAmount = 0d; // 审批金额
		Double autualAmount = 0d; // 实放金额
		Double grantAmount = 0d; // 放款金额
		Double handAmount = 0d; // 到手金额
		Double monthPayAmount = 0d; // 月还款额
		Double mulAmount = 0d; // 综合服务费
		Double consultAmount = 0d; // 咨询费(汇金)：综合服务费 * 40%
		Double checkAmount = 0d; // 审核费(汇诚)：综合服务费 * 2%
		Double mediacyAmount = 0d; // 居间服务费(汇民)：综合服务费 * 18%
		Double infoAmount = 0d; // 信息服务费(财富)：综合服务费 * 40%
		Double deductAmount = 0d; // 划扣金额：30天，90天：应划扣金额 = 综合服务费（包括：咨询费+审核费+居间服务费+信息服务费）+利息+停车费或设备费及平台流量费（备注：这里的停车费是划扣一个月的停车费）
				// 180天，270天，360天：应划扣金额=首期服务费+停车费或设备费及平台流量费（备注：这里的停车费是划扣3个月的停车费）
		if (rate != null) {
			CarContract carContract = carContractService.getByLoanCode(loanCode);
			int auditDay = carContract.getContractMonths(); // 审批期限（天）
			int auditMonth = auditDay / 30; // 审批期限（期）
			auditAmount = carContract.getAuditAmount().doubleValue(); // 审批金额
			String auditProduct = carContract.getProductType(); // 审批产品
			String contractVersion = carContract.getContractVersion();//合同版本
			Double grossRate = Arith.div(carContract.getGrossRate().doubleValue(), 100d); // 总费率
			rate = Arith.div(rate, 100d); // 利息率
			
			//计算开始
			Double mulRate = Arith.sub(grossRate, rate); // 综合服务费率
			monthRa = Arith.round(Arith.mul(auditAmount, rate),2);
			mulAmount = Arith.round(Arith.mul(auditAmount, mulRate),2);
			
			Double parkFree = 0d; // 停车费
			Double change = 0d; // 费设备费
			Double change2 = 0d; // 费设备使用费
			Double flowFee = 0d; // 平台及流量费
			CarLoanInfo loanInfo = carLoanInfoService.selectByLoanCode(loanCode);
			if (CarLoanProductType.GPS.getCode().equals(auditProduct)) {
				change = loanInfo.getFacilityCharge().doubleValue();// 费设备费
				change2=loanInfo.getDeviceUsedFee()==null?0:loanInfo.getDeviceUsedFee().doubleValue();//费设备使用费
				change= Arith.add(change,change2);
				flowFee = loanInfo.getFlowFee()==null?0:loanInfo.getFlowFee().doubleValue();
			} else {
				parkFree = loanInfo.getParkingFee()==null?0:loanInfo.getParkingFee().doubleValue();
			}
			// 划扣金额：30天，90天：应划扣金额 = 首期服务费+综合服务费（包括：咨询费+审核费+居间服务费+信息服务费）+利息+停车费或GPS费及平台流量费（备注：这里的停车费是划扣一个月的停车费）
			// 180天，270天，360天：应划扣金额=首期服务费+停车费或设备费及平台流量费（备注：这里的停车费是划扣3个月的停车费）
			if (auditMonth == 1 || auditMonth == 3) {
				/*if("1.3".equals(contractVersion)){
					if (mulAmount < 1000d) {//1.4版车借  取消综合服务费不满1000元按1000元收取规则，变更为按计算所得实际金额收取
						mulAmount = 1000d;
					}
				}else{
					firstServerAmount = Arith.mul(auditAmount, 0.02);
				}*/
				firstServerAmount = Arith.mul(auditAmount, loanInfo.getFirstServiceTariffingRate().doubleValue()/100);
				monthPayAmount = Arith.add(monthRa, mulAmount);
				handAmount = Arith.sub(Arith.sub(Arith.sub(auditAmount, monthRa), mulAmount),firstServerAmount);
				if(auditMonth == 3){
					deductAmount = Arith.add(firstServerAmount,  Arith.add(mulAmount, Arith.add(monthRa, Arith.add(Arith.mul(parkFree, 3),Arith.add(flowFee,change)))));
				}else{
					deductAmount = Arith.add(firstServerAmount,  Arith.add(mulAmount, Arith.add(monthRa, Arith.add(parkFree, Arith.add(flowFee,change)))));
				}
				
			} else {
				monthPayAmount = Arith.add(Arith.div(auditAmount, auditMonth), Arith.mul(auditAmount, grossRate));
				/*if("1.3".equals(contractVersion)){//1.4版车借 调整由0.02改为0.04
					firstServerAmount = Arith.mul(auditAmount, 0.02);
				}else{
					firstServerAmount = Arith.mul(auditAmount, 0.04);
				}*/
				firstServerAmount = Arith.mul(auditAmount, loanInfo.getFirstServiceTariffingRate().doubleValue()/100);
				handAmount = Arith.sub(auditAmount, firstServerAmount);
				deductAmount = Arith.add(firstServerAmount, Arith.add(Arith.add(flowFee,change), Arith.mul(parkFree, 3)));
			}
			consultAmount = Arith.round(Arith.mul(mulAmount, 0.4),2);
			checkAmount = Arith.round(Arith.mul(mulAmount, 0.02),2);
			mediacyAmount = Arith.round(Arith.mul(mulAmount, 0.18),2);
			infoAmount = Arith.round(Arith.sub(Arith.sub(Arith.sub(mulAmount, checkAmount), consultAmount), mediacyAmount), 2);
			autualAmount = grantAmount = Arith.round(handAmount,2) ;
			if (outVisitFee != null) {
			autualAmount =Arith.round(Arith.sub(autualAmount, outVisitFee),2) ;
			deductAmount=Arith.round(Arith.add(deductAmount, outVisitFee),2) ;//划扣金额减掉外访费
			}
			//计算结束
		}
		everyAmountMap.put("monthRa", monthRa);//利息（月）
		everyAmountMap.put("firstServerAmount", firstServerAmount);//首期服务费
		everyAmountMap.put("auditAmount", auditAmount);//批款金额
		everyAmountMap.put("autualAmount", autualAmount);//到手金额
		everyAmountMap.put("grantAmount", grantAmount);//
		everyAmountMap.put("deductAmount", deductAmount);
		everyAmountMap.put("monthPayAmount", Arith.round(monthPayAmount,2));
		everyAmountMap.put("mulAmount", mulAmount);
		everyAmountMap.put("checkAmount", checkAmount);
		everyAmountMap.put("consultAmount", consultAmount);
		everyAmountMap.put("mediacyAmount", mediacyAmount);
		//everyAmountMap.put("infoAmount", Arith.round(infoAmount,2));
		everyAmountMap.put("infoAmount",infoAmount);
		
		return everyAmountMap;
	}
	
	/**
	 * 展期--通过利息率 的值计算 各项费用，供页面使用
	 * 2016年2月26日
	 * By 申诗阔
	 * @param rate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "calculateExtendEveryAmount")
	public Map<String, Double> calculateExtendEveryAmount(String loanCode, Double rate) {
		Map<String, Double> everyAmountMap = new HashMap<String, Double>();
		Double monthRa = 0d; // 利息（月）：借款总额  * 利息率
		Double grantAmount = 0d; // 放款金额
		Double extendPayTotalAmount = 0d; // 展期应还总金额
		Double mulAmount = 0d; // 综合服务费
		Double extendAmount = 0d; // 展期费用
		Double consultAmount = 0d; // 咨询费(汇金)：综合服务费 * 40%
		Double checkAmount = 0d; // 审核费(汇诚)：综合服务费 * 2%
		Double mediacyAmount = 0d; // 居间服务费(汇民)：综合服务费 * 18%
		Double infoAmount = 0d; // 信息服务费(财富)：综合服务费 * 40%
		if (rate != null) {
//			Double originalGrossRate = carContractService.selectOriginalGrossRate(loanCode);
			CarContract carContract = carContractService.getByLoanCode(loanCode);
			Double grossRate = Arith.div(carContract.getGrossRate().doubleValue(), 100d); // 总费率
			String auditProduct = carContract.getProductType(); // 审批产品
			String contractVersion = carContract.getContractVersion();//合同版本
			rate = Arith.div(rate, 100d); // 利息率
//			originalGrossRate = Arith.div(originalGrossRate, 100d); // 原车借总费率
			//计算开始
			Double mulRate = Arith.sub(grossRate, rate);
			Double contractAmount = carContract.getContractAmount().doubleValue();
			monthRa = Arith.round(Arith.mul(contractAmount, rate),2);
			mulAmount = Arith.round(Arith.mul(contractAmount, mulRate),2);
			Double parkOrFlowFee = 0d; // 停车费 或 平台及流量费
			Double deviceUsedFee = 0d;//设备使用费 
			CarLoanInfo loanInfo = carLoanInfoService.selectByLoanCode(loanCode);
			if (CarLoanProductType.GPS.getCode().equals(auditProduct)) {
				parkOrFlowFee = loanInfo.getFlowFee()==  null ? 0 : loanInfo.getFlowFee().doubleValue();
				deviceUsedFee=loanInfo.getDeviceUsedFee() ==null?0:loanInfo.getDeviceUsedFee().doubleValue();
			} else {
				parkOrFlowFee = loanInfo.getParkingFee() == null ? 0 : loanInfo.getParkingFee().doubleValue();
			}
			if("1.3".equals(contractVersion)){//1.4版车借  取消综合服务费不满1000元按1000元收取规则，变更为按计算所得实际金额收取
				if (mulAmount < 1000d) {
					mulAmount = 1000d;
				}
			}
			extendAmount = Arith.round(Arith.add(mulAmount, monthRa),2);
			extendPayTotalAmount = Arith.round(Arith.add(Arith.add(extendAmount, Arith.add(carContract.getDerate().doubleValue(), parkOrFlowFee)),deviceUsedFee),2);
//			extendPayTotalAmount = Arith.round(Arith.add(Arith.add(extendAmount, Arith.add(carContract.getDerate().doubleValue(), 0.00)),0.00),2);
			consultAmount = Arith.round(Arith.mul(mulAmount, 0.4),2);
			checkAmount = Arith.round(Arith.mul(mulAmount, 0.02),2);
			mediacyAmount = Arith.round(Arith.mul(mulAmount, 0.18),2);
			infoAmount = Arith.round(Arith.sub(Arith.sub(Arith.sub(mulAmount, checkAmount), consultAmount), mediacyAmount), 2);
			
			// 计算公式中没有计算方法，这里统一为到手金额
			grantAmount = contractAmount;
			//计算结束
		}
		everyAmountMap.put("monthRa", monthRa);
		everyAmountMap.put("grantAmount", grantAmount);
		everyAmountMap.put("extendAmount", extendAmount);
		everyAmountMap.put("mulAmount", mulAmount);
		everyAmountMap.put("checkAmount", checkAmount);
		everyAmountMap.put("consultAmount", consultAmount);
		everyAmountMap.put("mediacyAmount", mediacyAmount);
		everyAmountMap.put("infoAmount", infoAmount);
		everyAmountMap.put("extendPayTotalAmount", extendPayTotalAmount);
		
		return everyAmountMap;
	}
	
	/**
	 * 通过applyId得到合同已办的相关信息
	 * 2016年2月29日
	 * By 甘泉
	 * @param applyId
	 * @return String
	 */
	@RequestMapping(value = "contractDone")
	public String contractDone(String applyId,Model model){
		//得到借款编码
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		carLoanInfo.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", carLoanInfo.getLoanFlag()));
		String loanCode = carLoanInfo.getLoanCode();
		//1.得到客户相关信息
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode);
		//2.得到共借人相关信息
		//共借人姓名
    	List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
    	//3.车辆信息
    	CarVehicleInfo carVehicleInfo =carVehicleInfoService.selectCarVehicleInfo(loanCode);
    	//4.银行卡折信息
    	CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
    	//审核结果
    	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); // 审批通过记录	
    	//5.得到合同费率信息
    	CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
		//6.得到合同相关信息
		CarContract carContract = carContractService.getByLoanCode(loanCode);
		//7.通过中间人主键得到中间人
		String  midId = carContract.getMidId();
		if(!StringUtils.isEmpty(midId)){
			String middleman = middlePersonService.selectById(midId).getMiddleName();
			model.addAttribute("middleman", middleman);
		}
		model.addAttribute("carCustomer",carCustomer );
		model.addAttribute("carLoanCoborrowers", carLoanCoborrowers );
		model.addAttribute("carVehicleInfo",carVehicleInfo );
		model.addAttribute("carCustomerBankInfo",carCustomerBankInfo );
		model.addAttribute("carCheckRate",carCheckRate );
		model.addAttribute("carAuditResult",carAuditResult);
		model.addAttribute("carLoanInfo",carLoanInfo );
		model.addAttribute("carContract",carContract );
		String imageurl = carImageService.getExendImageUrl("20", loanCode);
		model.addAttribute("imageurl",imageurl );
		return "car/done/contract_done";
	}
	/**
	 * 通过applyId得到展期合同已办的相关信息
	 * 2016年3月10日
	 * By 甘泉
	 * @param applyId
	 * @return String
	 */
	@RequestMapping(value = "extendContractDone")
	public String extendContractDone(String applyId,Model model){
		//得到借款编码
		CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
		String loanCode = carLoanInfo.getLoanCode();
		//1.得到客户相关信息
		CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode);
		//2.得到共借人相关信息
		//共借人姓名
		List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
		//3.车辆信息
    	CarVehicleInfo carVehicleInfo =carVehicleInfoService.selectCarVehicleInfo(loanCode);
    	//4.银行卡折信息
    	CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
    	//5.得到合同费率信息
    	CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
		//6.获得历史展期信息
    	List<CarContract> carContracts = carContractService.getExtendContractByLoanCode(loanCode);
    	//7.获得合同信息
    	CarContract carContract = carContractService.getByLoanCode(loanCode);
    	//8.通过中间人主键得到中间人
    			String  midId = carContract.getMidId();
    			if(!StringUtils.isEmpty(midId)){
    				String middleman = middlePersonService.selectById(midId).getMiddleName();
    				model.addAttribute("middleman", middleman);
    			}
    	model.addAttribute("carCustomer",carCustomer );
		model.addAttribute("carLoanCoborrowers", carLoanCoborrowers );
		model.addAttribute("carVehicleInfo",carVehicleInfo );
		model.addAttribute("carCustomerBankInfo",carCustomerBankInfo );
		model.addAttribute("carCheckRate",carCheckRate );
		model.addAttribute("carLoanInfo",carLoanInfo );
		model.addAttribute("carContracts",carContracts );
		carContract.setProductType(CarLoanProductType.parseByCode(carContract.getProductType()).getName());
		model.addAttribute("carContract",carContract );
		String imageurl = carImageService.getExendImageUrl("20", loanCode);
		model.addAttribute("imageurl",imageurl );
		return "car/carExtend/carExtendDone/carExtendContractDone";
	}
	
	/**
	 * 签订上传合同操作
	 * 2016年3月14日
	 * By 蒋力
	 * @param workItem
	 * @param view
	 * @return
	 */
	@RequestMapping(value = "signUploadContractHandle")
	public String signUploadContractHandle(WorkItemView workItemView,FlowProperties flowProperties,String loanCode,String remark) {
		//标红置顶业务参数
		workItemView.setFlowProperties(flowProperties);
		CarCheckRateBusinessView bv = new CarCheckRateBusinessView();
		bv.setLoanCode(loanCode);
		if(CarLoanResponses.UPLOAD_CONTRACT_ABANDON.getCode().equals(workItemView.getResponse())) {
			//走客户放弃
			workItemView.setResponse(CarLoanResponses.UPLOAD_CONTRACT_ABANDON.getCode());
			bv.setDictLoanStatus(CarLoanStatus.CUSTOMER_GIVE_UP.getCode());
			workItemView.setBv(bv);
			flowService.dispatch(workItemView);
		} else if(CarLoanResponses.BACK_SIGN_CONTRACT.getCode().equals(workItemView.getResponse())) {
			//走退回
			workItemView.setResponse(CarLoanResponses.BACK_SIGN_CONTRACT.getCode());
			bv.setDictLoanStatus(CarLoanStatus.PENDING_SIGNED_CONTRAC_BACK.getCode());
			bv.setBackReason(remark);
			workItemView.setBv(bv);
			flowService.dispatch(workItemView);
			//保存退回原因
		} else if(CarLoanResponses.UPLOAD_CONTRACT_STORE_REFUSE.getCode().equals(workItemView.getResponse())) {
			//走门店拒绝
			workItemView.setResponse(CarLoanResponses.UPLOAD_CONTRACT_STORE_REFUSE.getCode());
			bv.setDictLoanStatus(CarLoanStatus.UPLOAD_CONTRACT_STORE_REFUSE.getCode());
			workItemView.setBv(bv);
			flowService.dispatch(workItemView);
		}
		return "redirect:" + this.adminPath + "/car/carApplyTask/fetchTaskItems";
	}

	/**
	 * 导出客户信息
	 * 2016年4月1日
	 * By 申诗阔
	 * @param response
	 * @param idVal
	 * @param carLoanFlowQueryParam
	 * @return
	 */
	@RequestMapping(value = "exportData")
	public String exportWatch(HttpServletResponse response, String idVal, CarLoanFlowQueryParam carLoanFlowQueryParam) {
		ExcelUtils excelutil = new ExcelUtils();
		List<CarExportCustomerDataExColumn> lst = new ArrayList<CarExportCustomerDataExColumn>();
		ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
		List<CarLoanFlowWorkItemView> workItems = new ArrayList<CarLoanFlowWorkItemView>();
		try {
			ReflectHandle.copy(carLoanFlowQueryParam, queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!"".equals(idVal)) {
			queryParam.put("applyId", idVal.split(","));
		}
		// 用于获取总条数，以便后面用分页来实现导出数据，保证导出顺序与列表数据一致
		TaskBean taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_CONTRACT_CHECK.getWorkQueue(), queryParam, CarLoanFlowWorkItemView.class);
		
		FlowPage page = new FlowPage();
		page.setPageSize(taskBean.getItemList().size());
        page.setPageNo(1);
        
		flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_CONTRACT_CHECK.getWorkQueue(), queryParam, page, null, CarLoanFlowWorkItemView.class);
		List<BaseTaskItemView> sourceWorkItems = page.getList();
        for (BaseTaskItemView currItem : sourceWorkItems) {
        	workItems.add((CarLoanFlowWorkItemView) currItem);
        }
		if (ArrayHelper.isNotEmpty(workItems)) {
			for (int i = 0; i < workItems.size(); i++) {
				CarExportCustomerDataExColumn carExportMoneyEx = new CarExportCustomerDataExColumn();
				ReflectHandle.copy(workItems.get(i), carExportMoneyEx);
				carExportMoneyEx.setNumber(i + 1);
				carExportMoneyEx.setCardBank(DictCache.getInstance().getDictLabel("jk_open_bank", workItems.get(i).getCardBank()));
				CarExportCustomerDataExColumn carExportCustomerDataExColumn = carContractService.getContractCustomColumnByContractCode(workItems.get(i).getContractCode());
				// 还款银行帐号
				carExportMoneyEx.setBankCardNo(carExportCustomerDataExColumn.getBankCardNo());
				// 月还款额
				carExportMoneyEx.setContractMonthRepayAmount(carExportCustomerDataExColumn.getContractMonthRepayAmount());
				// 停车费
				carExportMoneyEx.setParkingFee(carExportCustomerDataExColumn.getParkingFee());
				// 设备费
				carExportMoneyEx.setFacilityCharge(carExportCustomerDataExColumn.getFacilityCharge());
				// 平台流量费
				carExportMoneyEx.setFlowFee(carExportCustomerDataExColumn.getFlowFee());
				// 综合费用
				carExportMoneyEx.setComprehensiveServiceFee(carExportCustomerDataExColumn.getComprehensiveServiceFee());
				// 首期还款日
				carExportMoneyEx.setContractReplayDay(carExportCustomerDataExColumn.getContractReplayDay());
				// 合同到期日
				carExportMoneyEx.setContractEndDay(carExportCustomerDataExColumn.getContractEndDay());
				// 合同签订日期
				carExportMoneyEx.setContractFactDay(carExportCustomerDataExColumn.getContractFactDay());
				carExportMoneyEx.setCardBank(carExportCustomerDataExColumn.getCardBank());
				//首期服务费
				carExportMoneyEx.setFirstServiceTariffing(carExportCustomerDataExColumn.getFirstServiceTariffing());
				//设备使用费
				carExportMoneyEx.setDeviceUsedFee(carExportCustomerDataExColumn.getDeviceUsedFee());
				//外访费
				carExportMoneyEx.setOutVisitFee(carExportCustomerDataExColumn.getOutVisitFee());
				lst.add(carExportMoneyEx);
			}
		}
		excelutil.exportExcel(lst, FileExtension.GRANT_CUSTOMER,
				CarExportCustomerDataExColumn.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, null);
		return null;
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
			String docId, CarContractType contractType) {
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
		if (contractType != null) {
			contractFile.setDownloadFlag(contractType.getFlag());
			contractFile.setContractFileName(contractType.getName());
			contractFile.setFileShowOrder(contractType.getCode());
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
	@RequestMapping(value = "contractDownloadByApplyId")
	public String contractDownloadByApplyId(String applyId,String flag,Model model){
		CarLoanInfo car = carLoanInfoService.selectByApplyId(applyId);
		CarContract carContract = carContractService.getByLoanCode(car.getLoanCode());
		return contractDownloadCommon(carContract.getDocId(), flag, model);
	}
	
	private String contractDownloadCommon(String docIds,String flag,Model model){
		String documentType = null;
		User user = UserUtils.getUser();
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains("6220000007")) {
			documentType = ".doc";
		}else{
			documentType = ".pdf";
		}
		//2.得到每一个合同的名称
		// 获取dmService
		DmService dmService = DmService.getInstance();
		String[] docId = null;
		List<DocumentBean> docs = new ArrayList<DocumentBean>();
		List<CarContractFile> files = new ArrayList<CarContractFile>();
		DocumentBean doc = null;
		CarContractFile cafile=null;
		if(StringUtils.isNotEmpty(docIds)){
			if(docIds.indexOf(",")!=-1){
                docId = docIds.split(",");
            } else{
                docId = new String[1];
                docId[0] = docIds;
            }
			if(docId != null && docId.length > 0){
				for (int i = 0; i < docId.length; i++) {
					doc = dmService.getDocument(docId[i]);
					cafile =carContractFileService.findContractFileByDocId(docId[i]);
					if ( StringUtils.endsWith(doc.getDocTitle(), documentType)){
						docs.add(doc);
					}
					if (null!=cafile&&StringUtils.endsWith(cafile.getFileName(), documentType)){
						files.add(cafile);
					}
				}
			}
		}
		model.addAttribute("docs", files);
		model.addAttribute("flag", flag);
		return "car/done/downloadContract";
	}
}
