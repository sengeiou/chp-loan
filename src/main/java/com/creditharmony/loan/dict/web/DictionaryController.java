/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.dict.webDictController.java
 * @Create By 张灏
 * @Create In 2016年4月28日 上午9:37:15
 */
package com.creditharmony.loan.dict.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.dict.entity.DictInfo;
import com.creditharmony.loan.dict.service.DictInfoManager;

/**
 * @Class Name DictController
 * @author 张灏
 * @Create In 2016年4月28日
 */
@Controller
@RequestMapping(value = "${adminPath}/common/dict")
public class DictionaryController extends BaseController {
    @Autowired
    private MiddlePersonService middlePersonService;
    @Autowired
    private DictInfoManager dictInfoManager;

	/**
	 * 还款查询存入账户
	 * 2015年12月11日
	 * @param id 中间人主键
	 * @return list
	 */
    @RequestMapping(value="list")
    public String findList(Model model,String typeName){
        MiddlePerson middlePerson = new MiddlePerson();
        List<MiddlePerson> dicts = middlePersonService.findPaybackAccount(middlePerson);
        model.addAttribute("dicts", dicts);
        model.addAttribute("typeName", typeName);
        return "comm/dictItems";
    }
    
    /**
     * 渠道标识的多选查询
     * 2016年9月29日
     * By 朱静越
     * @param model
     * @param typeName
     * @return
     */
    @RequestMapping(value="getChannelList")
    public String getChannelList(Model model,String typeName){
    	DictInfo dictInfo = new DictInfo();
    	dictInfo.setType(typeName);
    	List<DictInfo> dictList = dictInfoManager.getByType(dictInfo);
        model.addAttribute("dicts", dictList);
        return "comm/channelFlagItems";
    }
    
    
    
}
