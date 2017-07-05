package com.creditharmony.loan.borrow.delivery.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.borrow.delivery.service.DeliveryApplyService;
import com.creditharmony.loan.borrow.delivery.service.DeliveryWaitService;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.ImageService;
/**
 * 交割待办
 * @Class Name DeliveryWaitController
 * @author lirui
 * @Create In 2015年12月4日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/delivery")
public class DeliveryWaitController extends BaseController {

	@Autowired
	private DeliveryWaitService dws;
	
	@Autowired
	private DeliveryApplyService das;
	
	@Autowired
    private ImageService imageService;
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * 交割待办列表
	 * 2015年12月4日
	 * By lirui
	 * @param request 获得request
	 * @param response 获得response
	 * @param params 检索参数
	 * @param m Model模型
	 * @return 交割待办列表页面
	 */
	@RequestMapping(value = "deliveryWaitInfo")
	public String deliveryWaitInfo(HttpServletRequest request,HttpServletResponse response,DeliveryParamsEx params,Model m) {
		// 定义日期格式
		String fmt = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		// 如果有搜索参数,则将参数回显
		if (params != null) {
			// 如果搜索起始时间不为空,将起始时间转换成字符串另存为startTime,并封装到params中,留在搜索条件中
			if (params.getStartDate() != null) {
				params.setStartTime(sdf.format(params.getStartDate()));
			}
			// 如果搜索结束时间不为空,将结束时间转换成字符串另存为endTime,并封装到params中,留在搜索条件中
			if (params.getEndDate() != null) {
				params.setEndTime(sdf.format(params.getEndDate()));
			}
			m.addAttribute("params", params);
		}
		// 获得交割待办列表
		Page<DeliveryViewEx> waitPage = dws.deliveryWait(new Page<DeliveryViewEx>(request, response), params);
		List<DeliveryViewEx> list = waitPage.getList();
		// 缓存取码值
		for (DeliveryViewEx deliveryViewEx : list) {
			deliveryViewEx.setDictLoanStatusLabel(DictCache.getInstance().getDictLabel("jk_loan_apply_status", deliveryViewEx.getDictLoanStatus()));
		}
		for (int i = 0; i < waitPage.getList().size(); i++) {
			if (StringUtils.isNotEmpty(waitPage.getList().get(i).getEntrustMan())) {
				String entrustManName = areaService.selectNameByCode(waitPage.getList().get(i).getEntrustMan());
				waitPage.getList().get(i).setEntrustManName(entrustManName);
			}
		}
		// 获得所有门店
		List<OrgView> orgs = das.orgs();
		m.addAttribute("orgs", orgs);
		m.addAttribute("waitPage", waitPage);
		return "apply/delivery/deliveryWait";
	}
	
	/**
	 * 交割办理详情
	 * 2015年12月11日
	 * By lirui
	 * @param loanCode 借款编码
	 * @param m Model模型
	 * @return 交割办理详情页面
	 */
	@RequestMapping(value = "delivaryInfo")
	public String delivaryInfo(String loanCode,Model m) {
		// 根据借款编码获得当前交割信息
		DeliveryViewEx dv = dws.deliveryInfo(loanCode);
	    String imageUrl = imageService.getImageUrl(FlowStep.TRANSLATE_WORKITEM.getName(),loanCode);
        dv.setImageUrl(imageUrl);
		m.addAttribute("dv", dv);
		return "apply/delivery/delivaryInfo";
	}
	
	/**
	 * 交割结果提交
	 * 2015年12月15日
	 * By lirui
	 * @param loanCode 借款编码
	 * @param dv 存放交割结果的容器
	 * @return 
	 */
	@RequestMapping(value = "deliveryResult",method=RequestMethod.POST)
	public String deliveryResult(DeliveryViewEx dv,String loanCode) {
		dv.setLoanCode(loanCode);
		// 提交交割结果
		dws.deliveryResult(dv);
		return	"redirect:"+adminPath+"/borrow/delivery/deliveryWaitInfo";	
	}
	
	/**
	 * 批量通过
	 * 2015年12月16日
	 * By lirui
	 * @param loanCodes 借款编码组成的数组
	 * @return 待办页面
	 */
	@RequestMapping(value = "passAll")
	public String passAll(String[] loanCodes) {
		dws.passDelivery(loanCodes);
		return "redirect:"+adminPath+"/borrow/delivery/deliveryWaitInfo";
	}
	
	/**
	 * 批量驳回
	 * 2015年12月17日
	 * By lirui
	 * @param loanCodes 驳回项的借款编码
	 * @param reason 驳回原因
	 * @return 待办页面
	 */	
	@RequestMapping(value = "regectedAll")
	public String regectedAll(String[] loanCodes,String reason) {		
		dws.regected(loanCodes, reason);
		return "redirect:"+adminPath+"/borrow/delivery/deliveryWaitInfo";
	}
}
