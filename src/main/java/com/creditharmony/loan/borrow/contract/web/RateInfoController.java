/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.webRateInfoController.java
 * @Create By 张灏
 * @Create In 2016年4月13日 下午1:38:44
 */
package com.creditharmony.loan.borrow.contract.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.entity.RateInfo;
import com.creditharmony.loan.borrow.contract.service.RateInfoService;

import filenet.vw.base.StringUtils;

/**
 * 利率信息Controller
 * @Class Name RateInfoController
 * @author 张灏
 * @Create In 2016年4月13日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/rateInfo")
public class RateInfoController extends BaseController {

    @Autowired
    private RateInfoService rateInfoService;
    
    /**
     * 获取利率信息
     * 2016年5月19日
     * By 王彬彬
     * @param model
     * @return
     */
    @RequestMapping(value="getRateInfoList")
    public String getRateInfoList(Model model){
         RateInfo queryParam = new RateInfo();
         List<RateInfo> rateInfoList = rateInfoService.findList(queryParam);
         model.addAttribute("rateInfoList", rateInfoList);
         return "/borrow/contract/rateList";
    }
    /**
     * 按时间获取利率
     * 2016年5月19日
     * By 王彬彬
     * @param model
     * @return
     */
    @RequestMapping(value="getRateQueryList")
    public String getRateQueryList(Model model){
         RateInfo queryParam = new RateInfo();
         queryParam.setEffectiveFlag(YESNO.YES.getCode());
         queryParam.setCurDate(DateUtils.formatDate(new Date(), "HH:mm:ss"));
         List<RateInfo> rateInfoList = rateInfoService.findList(queryParam);
         model.addAttribute("rateInfoList", rateInfoList);
         return "/borrow/contract/rateQueryList";
    }
    
    /**
     * 更新利率是否有效
     * 2016年5月19日
     * By 王彬彬
     * @param rateInfo
     * @return
     */
    @RequestMapping(value="updRateEffectiveDate")
    @ResponseBody
    public String updRateEffectiveDate(RateInfo rateInfo){
      //  List<RateInfo>  rates = (List<RateInfo>) jsonMapper.fromJsonString(rateInfoList, RateInfo.class);
        List<RateInfo> rateInfoList = rateInfo.getRateInfoList();
        for(RateInfo cur:rateInfoList){
            if(StringUtils.isEmpty(cur.getEffectiveFlag())){
                cur.setEffectiveFlag(YESNO.NO.getCode());
            }
            rateInfoService.updEffctiveDateById(cur);
        }
        return "true";
    }
}
