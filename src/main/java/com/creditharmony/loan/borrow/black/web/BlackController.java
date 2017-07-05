/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.black.webBlackController.java
 * @Create By 张灏
 * @Create In 2015年12月15日 上午10:38:52
 */
package com.creditharmony.loan.borrow.black.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.approve.type.BlackGreyList;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.black.entity.Black;
import com.creditharmony.loan.borrow.black.service.BlackService;

/**
 * 黑名单 Controller
 * @Class Name BlackController
 * @author 张灏
 * @Create In 2015年12月15日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/black")
public class BlackController extends BaseController {

    @Autowired
    private BlackService blackService;
    
    /**
     * 2015年12月15日
     * By 张灏
     * @param model
     * @param black
     * @return result
     */
    @RequestMapping(value="asynQueryBlack")
    @ResponseBody
    public Map<String,Object> asynQueryBlack(Model model,Black black){
        Map<String,Object> result = new HashMap<String,Object>();
        black.setDictBlackType(BlackGreyList.BLACK_LIST.getCode());
        List<Black> blackList = blackService.findBlackByIdentification(black);
        if(ArrayHelper.isNotEmpty(blackList)){
            result.put("isWhite", "0");
            result.put("message", "该客户是黑名单客户");
        }else{
            result.put("isWhite", "1");
            result.put("message", "");
        }
        
        return result; 
    }
}
