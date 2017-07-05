package com.creditharmony.loan.car.carRefund.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carRefund.ex.RefundAuditEx;
import com.creditharmony.loan.car.carRefund.service.CarRefundAuditService;
import com.creditharmony.loan.car.common.consts.CarRefundStatus;
import com.creditharmony.loan.car.common.entity.CarRefundInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;


/**
 * 放款审核进行处理
 * 
 * @Class Name CarRefundAuditController
 * @author 蒋力
 * @Create In 2016年2月29日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/refund/refundAudit")
public class CarRefundAuditController extends BaseController {
	@Autowired
	private CarRefundAuditService carRefundAuditService;
	@Autowired
	private CarHistoryService carHistoryService;
	
	/**
	 * 退款审核待办
	 * 
	 * @param model
	 *            中存放要在页面中显示的list
	 * @param checkVal
	 *            applyId，根据applyId进行处理
	 * @return 要进行跳转的页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "refundAuditJump")
	public String refundAuditJump(Model model,CarRefundInfo carRefundInfo,String result,
				HttpServletRequest request,HttpServletResponse response,String returnUrl) {
			
		Page<CarRefundInfo> refundPage = carRefundAuditService.selectCarRefundAuditList(new Page<CarRefundInfo>(request, response), carRefundInfo);
		BigDecimal totalDeducts=new BigDecimal(0.00);
		totalDeducts = carRefundAuditService.CarRefundAuditCountSum(carRefundInfo).getUrgeMoeny();
		
		for(CarRefundInfo view:refundPage.getList()){
			view.setCardBank(DictCache.getInstance().getDictLabel("jk_open_bank", view.getCardBank()));
			view.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", view.getDictLoanStatus()));
			view.setAuditStatus(DictCache.getInstance().getDictLabel("car_refund_status", view.getAuditStatus()));
			view.setProductType(CarLoanProductType.parseByCode(view.getProductType()).getName());
			view.setCustomerTelesalesFlag(DictCache.getInstance().getDictLabel("jk_telemarketing", view.getCustomerTelesalesFlag()));
		}
		model.addAttribute("deductsAmount", totalDeducts);
		model.addAttribute("refundList", refundPage);	
		return "car/carRefund/carRefundAuditList";
	}
	

	/**
	 * 退款审核跳转
	 * 
	 * @param model
	 *            中存放要在页面中显示的list
	 * @param checkVal
	 *            applyId，根据applyId进行处理
	 * @return 要进行跳转的页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "refundAuditDo")
	public String refundAuditDo(Model model, String checkVal, String cardNo) {
		model.addAttribute("id", checkVal);
		model.addAttribute("cardNo", cardNo);
		return "car/carRefund/carRefundAuditDo";
	}
	
	/**
	 * 退款审核通过，更新审核结果和拒绝原因
	 * 
	 * @param param
	 *            属性
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "refundAuditSubmit")
	public String refundAuditSubmit(String id,String result,String reason) {
		CarRefundInfo carRefundInfo = new CarRefundInfo();
		carRefundInfo.setId(id);
		if((CarRefundStatus.CAR_AUDIT_STATUS_Y.getCode()).equals(result))
		{
			carRefundInfo.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_Y.getCode());
		}else{
			carRefundInfo.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_N.getCode());
		}
		carRefundInfo.setAuditRefuseReason(reason);
		try {
			carRefundAuditService.updateCarRefundAudit(carRefundInfo);
			String loanCode = carRefundAuditService.getLoanCodeByRefundId(id);
			carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.REFUND_AUDIT.getCode(), CarLoanOperateResult.SUCCESS.getCode(), "退款审核","");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BooleanType.TRUE;
	}
	
	/**
	 * 退款审核待办
	 * 
	 * @param model
	 *            中存放要在页面中显示的list
	 * @param checkVal
	 *            applyId，根据applyId进行处理
	 * @return 要进行跳转的页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "refundBacklogJump")
	public String refundBacklogJump(Model model,CarRefundInfo carRefundInfo,String result,
				HttpServletRequest request,HttpServletResponse response,String returnUrl) {
			
		Page<CarRefundInfo> refundPage = carRefundAuditService.selectCarRefundList(new Page<CarRefundInfo>(request, response), carRefundInfo);
		BigDecimal totalDeducts=new BigDecimal(0.00);
		totalDeducts = carRefundAuditService.CarRefundAuditCountSum(carRefundInfo).getUrgeMoeny();

		for(CarRefundInfo view:refundPage.getList()){
			view.setCardBank(DictCache.getInstance().getDictLabel("jk_open_bank", view.getCardBank()));
			view.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", view.getDictLoanStatus()));
			view.setAuditStatus(DictCache.getInstance().getDictLabel("car_refund_status", view.getAuditStatus()));
			view.setCustomerTelesalesFlag(DictCache.getInstance().getDictLabel("jk_telemarketing", view.getCustomerTelesalesFlag()));
		}
		carRefundInfo.setStaticValue(DictCache.getInstance().getDictLabel("car_refund_status", "3"));
		model.addAttribute("deductsAmount", totalDeducts);
		model.addAttribute("refundList", refundPage);	
		return "car/carRefund/carRefundList";
	}
	
	/**
	 * 导出退款信息列表 
	 * 2016年3月1日
	 * By 蒋力
	 * @param request
	 * @param response
	 * @param idVal
	 */
	@RequestMapping(value = "refundExl")
	public void refundExl(RefundAuditEx refundAuditEx,
			HttpServletRequest request,HttpServletResponse response, String idVal) {
		ExcelUtils excelutil = new ExcelUtils();
		String[] id=null;
		List<RefundAuditEx> auditList=new ArrayList<RefundAuditEx>();
		if (StringUtils.isEmpty(idVal)) {
			// 没有选中的，默认为全部,通过
			auditList = carRefundAuditService.exportCarRefundList(refundAuditEx);
			carRefundAuditService.editRefundReturnById(idVal);
		}else {
			// 如果有选中的单子,将选中的单子导出
			id=idVal.split(",");
			for (int i = 0; i < id.length; i++) {
				RefundAuditEx gae=new RefundAuditEx();
				gae.setId(id[i]);
				List<RefundAuditEx> tempList=carRefundAuditService.exportCarRefundList(gae);
				carRefundAuditService.editRefundReturnById(id[i]);
				if(tempList!=null&&tempList.size()>0)
				{
					auditList.add(tempList.get(0));
				}
			}
		}
		for(RefundAuditEx view:auditList){
			view.setCardBank(DictCache.getInstance().getDictLabel("jk_open_bank", view.getCardBank()));
			view.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", view.getDictLoanStatus()));
			view.setAuditStatus(DictCache.getInstance().getDictLabel("car_refund_status", view.getAuditStatus()));
			view.setCustomerTelesalesFlag(DictCache.getInstance().getDictLabel("jk_telemarketing", view.getCustomerTelesalesFlag()));
		}
		excelutil.exportExcel(auditList, FileExtension.GRANT_AUDIT, null,
				RefundAuditEx.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_DATA, response, null);
			for(int i=0;i<auditList.size();i++)
			{
				RefundAuditEx ex = new RefundAuditEx();
				ex.setId(auditList.get(i).getId());
				ex.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_Z.getCode());
				carRefundAuditService.updateRefundReturnStatus(ex);
			}
	}
	
	/**
	 * 线下退款处理，上传回执结果,同时更新车借_催收服务费退回表，更新退款退回状态 
	 * 2016年3月2日 
	 * By 蒋力
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @return 要进行跳转的页面
	 */
	@ResponseBody
	@RequestMapping(value = "importResult")
	public String importResult(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile file) {
			ExcelUtils excelutil = new ExcelUtils();
			List<RefundAuditEx> lst = new ArrayList<RefundAuditEx>();
			List<?> datalist = excelutil
					.importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
							RefundAuditEx.class);
			lst = (List<RefundAuditEx>) datalist;
			String isEffectFile = BooleanType.TRUE;
			if(ArrayHelper.isNotEmpty(lst))
			{
				for (int i = 0; i < lst.size(); i++) {
					//效验上传文件是否有效
					String checkLoanCode = carRefundAuditService.getLoanCodeByRefundId(lst.get(i).getId());
					if(checkLoanCode==null||"".equals(checkLoanCode))
					{
						isEffectFile = BooleanType.FALSE;
					}
				}
			}
			if(BooleanType.TRUE.equals(isEffectFile))
			{
				for (int i = 0; i < lst.size(); i++) {
					RefundAuditEx ex = new RefundAuditEx();
					ex.setId(lst.get(i).getId());
					ex.setContractCode(lst.get(i).getContractCode());
					ex.setReturnStatus(CarRefundStatus.CAR_RETURN_STATUS_Y.getCode());
					ex.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_P.getCode());
					ex.setRequireStatus(CarRefundStatus.CAR_AUDIT_STATUS_Z.getCode());//只修改现状态为处理中的
					try {
						carRefundAuditService.updateRefundReturnStatus(ex);
						String loanCode = carRefundAuditService.getLoanCodeByContractCode(ex.getContractCode());
						carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.REFUND.getCode(), CarLoanOperateResult.SUCCESS.getCode(), "线下退款退回处理","");
						
					} catch (Exception e) {
						e.printStackTrace();
						isEffectFile=BooleanType.FALSE;
					}
				}
			}
			return isEffectFile;
	}
	
	/**
	 * 线上退款处理-提交请求
	 * 2016年3月2日
	 * By 蒋力
	 * @param checkVal 要进行处理的单子的id
	 * @param grantWay 线上退款方式
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "onlineRefundDeal")
	@ResponseBody
	public String onlineRefundDeal(String checkVal,String grantWay) throws Exception{

		DeductReq deductReq;
		String[] id = null;
		String rule = "";
		int flag = 0;
		List<DeductReq> deductReqList = new ArrayList<DeductReq>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		
		hashMap.put("dictLoanStatus", CarLoanStatus.LENDING_FAILURE.getCode());
		if (PaymentWay.ZHONGJIN.getCode().equals(grantWay)) {
			rule = PaymentWay.ZHONGJIN.getCode() +":"+DeductTime.BATCH.getCode() ;
		}else if (PaymentWay.TONG_LIAN.getCode().equals(grantWay)) {
			rule = PaymentWay.TONG_LIAN.getCode() +":"+DeductTime.BATCH.getCode();
		}
		if (StringUtils.isEmpty(checkVal)) {
		}else {			
			id = checkVal.split(";");
			for (int i = 0; i < id.length; i++) {
				hashMap.put("id", id[i]);
				deductReq =  carRefundAuditService.getDeductReq(hashMap,rule);
				if (deductReq != null) {
					deductReqList.add(deductReq);
				}
				
			}
		}
		for(DeductReq req:deductReqList)
		{
			flag++;
			DeResult t = null;
			try{
				t = TaskService.addTask(req);
				if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
					RefundAuditEx ex = new RefundAuditEx();
					ex.setId(req.getBusinessId());
					ex.setContractCode(req.getBusinessId());
					ex.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_Z.getCode());
					carRefundAuditService.updateRefundReturnStatus(ex);
					String loanCode = carRefundAuditService.getLoanCodeByContractCode(ex.getId());
					carHistoryService.saveCarLoanStatusHis(loanCode, CarLoanSteps.REFUND.getCode(), CarLoanOperateResult.SUCCESS.getCode(), "线上退款退回处理","");
					
					TaskService.commit(t.getDeductReq());
				} else {
					TaskService.rollBack(t.getDeductReq());
				}
				
			} catch (Exception e) {
				if(t != null){
					TaskService.rollBack(t.getDeductReq());
				}
				e.printStackTrace();
				System.out.println(e.getMessage());
				flag = 0;
				e.printStackTrace();
				throw new RuntimeException();  
			}
		}
		if (flag>0) {
			return BooleanType.TRUE;
		}
		return BooleanType.FALSE;
	}
	
}
