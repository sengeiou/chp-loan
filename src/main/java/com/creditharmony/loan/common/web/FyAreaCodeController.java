/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.webFyAreaCodeController.java
 * @Create By 张灏
 * @Create In 2016年3月8日 下午6:37:06
 */
package com.creditharmony.loan.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.entity.FyAreaCode;
import com.creditharmony.loan.common.service.FyAreaCodeService;

/**
 * @Class Name FyAreaCodeController
 * @author 张灏
 * @Create In 2016年3月8日
 */
@Controller
@RequestMapping(value = "${adminPath}/common/fyAreaCode")
public class FyAreaCodeController extends BaseController{

    @Autowired
    private FyAreaCodeService fyAreaCodeService;
    
    @ResponseBody
    @RequestMapping("asynGetFyAreaCode")
    public String asynGetFyAreaCode(String parentId,String areaType){
        Map<String,Object> param = new HashMap<String,Object>();
        List<FyAreaCode> FyAreaList = null;
        param.put("parentId", parentId);
        param.put("areaType", areaType);
        FyAreaList = fyAreaCodeService.queryACByParam(param);
        return jsonMapper.toJson(FyAreaList);
    }
}
