package com.creditharmony.loan.common.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.type.LoanProductCode;

/**
 * 汇金产品管理-Controller
 * 
 * @Class Name LoanPrdMngController
 * @author 周亮
 * @Create In 2015年11月30日
 */
@Controller
@RequestMapping(value = "${adminPath}/common/prdmng")
public class LoanPrdAndMothsController extends BaseController {

	@Autowired
	private LoanPrdMngService svc;

	/**
	 * 检索产品
	 */
	@RequestMapping(value = "asynLoadPrd", method = RequestMethod.POST)
	@ResponseBody
	public String selPrd() {
		LoanPrdMngEntity selParam = new LoanPrdMngEntity();

		List<LoanPrdMngEntity> prdList = new ArrayList<LoanPrdMngEntity>();
		prdList = svc.selPrd(selParam);

		return jsonMapper.toJson(prdList);
	}

	/**
	 * 产品期限 
	 * 2016年1月4日 By 王彬彬
	 * 
	 * @param productCode
	 * @return
	 */
	@RequestMapping(value = "asynLoadPrdMonths", method = RequestMethod.POST)
	@ResponseBody
	public String getPrd(String productCode) {
		LoanPrdMngEntity selParam = new LoanPrdMngEntity();
		selParam.setProductCode(productCode);

		List<LoanPrdMngEntity> prdList = new ArrayList<LoanPrdMngEntity>();
		prdList = svc.selPrd(selParam);

		List<String> listMonths = new ArrayList<String>();
		if (!ObjectHelper.isEmpty(prdList)) {
			//String month = prdList.get(0).getProductMonths();
			listMonths = svc.getCoeffReferMonths();
			if (!LoanProductCode.PRO_XYJ.equals(productCode)) {// 删除48天周期
				listMonths.remove("48");
			}
		}

		return jsonMapper.toJson(listMonths);
	}
	@RequestMapping(value = "asynLoadPrdMonthsRisk", method = RequestMethod.POST)
	@ResponseBody
	public String getPrdRisk(String productCode,String createTime) {
		LoanPrdMngEntity selParam = new LoanPrdMngEntity();
		selParam.setProductCode(productCode);
		
		List<LoanPrdMngEntity> prdList = new ArrayList<LoanPrdMngEntity>();
		prdList = svc.selPrd(selParam);
		
		List<String> listMonths = new ArrayList<String>();
		
		if (!ObjectHelper.isEmpty(prdList)) {
			//String month = prdList.get(0).getProductMonths();
			listMonths = svc.getCoeffReferMonths(createTime);
			if(!LoanProductCode.PRO_XYJ.equals(productCode)){//删除48天周期
				listMonths.remove("48");
			}
		}
		
		return jsonMapper.toJson(listMonths);
	}
	/**
     * 产品期限 
     * 2016年1月4日 By 王彬彬
     * 
     * @param productCode
     * @return
     */
    @RequestMapping(value = "asynLoadPrdInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> asynLoadPrdInfo(String productCode) {
        LoanPrdMngEntity selParam = new LoanPrdMngEntity();
        selParam.setProductCode(productCode);
        Map<String,Object> result = new HashMap<String,Object>();
        List<LoanPrdMngEntity> prdList = new ArrayList<LoanPrdMngEntity>();
        prdList = svc.selPrd(selParam);
        
        List<String> listMonths = svc.getCoeffReferMonths();
        if(!LoanProductCode.PRO_XYJ.equals(productCode)){//删除48天周期
			listMonths.remove("48");
		}
        LoanPrdMngEntity  prdMng = null;
        if (!ObjectHelper.isEmpty(prdList)) {
            prdMng = prdList.get(0);
            result.put("listMonths", listMonths);
            result.put("limitLower", prdMng.getLimitLower().doubleValue());
            result.put("limitUpper", prdMng.getLimitUpper().doubleValue());
        }
       
        return result;
    }
    /**
     * 产品期限 
     * 2016年1月4日 By 王彬彬
     * 
     * @param productCode
     * @return
     */
    @RequestMapping(value = "asynLoadPrdInfoRisk", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> asynLoadPrdInfoForRisk(String productCode,String createTime,String loanCustomerSourceCode) {
    	//根据产品code查产品
    	LoanPrdMngEntity selParam = new LoanPrdMngEntity();
    	selParam.setProductCode(productCode);
    	List<LoanPrdMngEntity> prdList = svc.selPrd(selParam);
    	
    	Map<String,Object> result = new HashMap<String,Object>();
    	if (!ObjectHelper.isEmpty(prdList)) {
    		LoanPrdMngEntity prdMng = prdList.get(0);
    		//如果客户来源是买金网，则产品期限只显示一期
        	if("1".equals(loanCustomerSourceCode)){
        		List<String> listMonths = new ArrayList<String>();
        		listMonths.add("1");
        		result.put("listMonths", listMonths);
        	}else{
        		result.put("listMonths", Arrays.asList(prdMng.getProductMonths().split(",")));
        	}
    		result.put("limitLower", prdMng.getLimitLower().doubleValue());
    		result.put("limitUpper", prdMng.getLimitUpper().doubleValue());
    	}
    	return result;
    }
}
