package com.creditharmony.loan.borrow.restrictedInlet.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Role;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.restrictedInlet.entity.RestrictedInlet;
import com.creditharmony.loan.borrow.restrictedInlet.service.RestrictedInletService;

/**
 * 限制进件操作列表
 * @Class Name RestrictedInletController
 * @author 管洪昌
 * @Create In 2016年4月20日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/restrictedInlet")
public class RestrictedInletController extends BaseController{
  
	@Autowired
	private RestrictedInletService restrictedInletService;
	Page<RestrictedInlet> restrictedInletListpage;
	
	/**
	 * 团队经理查看页面
	 * 2016年1月20日
	 * By 管洪昌
	 * @param model
	 * @param restrictedInlet
	 * @param request
	 * @param response
	 * @return 跳转到高危线设置界面
	 */
	@RequestMapping(value="stroreStr")
    public String  stroreStr(Model model,RestrictedInlet restrictedInlet,
    		HttpServletRequest request,HttpServletResponse response){
	    //查询登陆人门店信息
    	RestrictedInlet res=new RestrictedInlet();
		//当前登陆人是不是 门店经理
	    User user = UserUtils.getUser();
        List<Role> roleList = user.getRoleList();
        boolean isManager = false;
        if(!ObjectHelper.isEmpty(roleList)){
            for(Role role:roleList){
            	//团队经理
                if(LoanRole.STORE_MANAGER.id.equals(role.getId())){
                    isManager = true;
                    break;
                }  
            }
         }
        if(isManager){
        	restrictedInlet.setOrgCode(user.getDepartment().getId());
        	res=restrictedInletService.selectStr(restrictedInlet);
        	//百分比格式化 % 当前M1逾期率
			if(res.getM1YqlCurrent()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String m1YqlCurrentLable = myformat.format(res.getM1YqlCurrent());    
				res.setM1YqlCurrentLable(m1YqlCurrentLable);
			}else {
				res.setM1YqlCurrentLable("0.00");
			}
			//百分比格式化 % M1逾期率高危线
			if(res.getStoreNum()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String storeNumLable = myformat.format(res.getStoreNum());    
				res.setStoreNumLable(storeNumLable);
			}else {
				res.setStoreNumLable("0.00");
			}
        }else{
        	restrictedInlet.setOrgCode(" ");
        }
	    // 门店高危线设置
		restrictedInletListpage = restrictedInletService.selectStroreList(new Page<RestrictedInlet>(request,response), restrictedInlet);
		//是否限制进件
		for(RestrictedInlet pb:restrictedInletListpage.getList()){
			//岗位
			if(StringUtils.isNotEmpty(pb.getZkbjType())){
				String zkbjTypeLable=DictCache.getInstance().getDictLabel("jk_person_type",pb.getZkbjType());
				pb.setZkbjTypeLable(zkbjTypeLable);
			}
			//是否被限制进谏
			if(StringUtils.isNotEmpty(pb.getSfJj())){
				String sfJjLable=DictCache.getInstance().getDictLabel("jk_input_limit",pb.getSfJj());
				pb.setSfJjLable(sfJjLable);
			}
			//百分比格式化 % 当前M1逾期率
			if(pb.getM1YqlCurrent()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String m1YqlCurrentLable = myformat.format(pb.getM1YqlCurrent());    
				pb.setM1YqlCurrentLable(m1YqlCurrentLable);
			}else {
				pb.setM1YqlCurrentLable("0.00");
			}
			//百分比格式化 % M1逾期率高危线
			if(pb.getM1Yql()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String m1YqlLable = myformat.format(pb.getM1Yql());    
				pb.setM1YqlLable(m1YqlLable);
			}else {
				pb.setM1YqlLable("0.00");
			}
		}
		model.addAttribute("res", res);
		model.addAttribute("restrictedInletListpage", restrictedInletListpage);
		model.addAttribute("RestrictedInlet", restrictedInlet);
    	return "borrow/restrictedInlet/stroreStr";
    }
	
	/**
	 * 高危线设置
	 * 2016年1月20日
	 * By 管洪昌
	 * @param model
	 * @param restrictedInlet
	 * @param request
	 * @param response
	 * @return 跳转到高危线设置界面
	 */
	@RequestMapping(value="restrictedInletList")
    public String  restrictedInletList(Model model,RestrictedInlet restrictedInlet,
    		HttpServletRequest request,HttpServletResponse response){
	    // 查询高危线列表设置
		restrictedInletListpage = restrictedInletService.selectRestrictedInletList(new Page<RestrictedInlet>(request,response), restrictedInlet);
		for(RestrictedInlet pb:restrictedInletListpage.getList()){
			//百分比格式化 %   客户经理M1
			if(pb.getCustomerNum()!=null && pb.getCustomerNum().compareTo(BigDecimal.ZERO)!=0){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String customerNumLable = myformat.format(pb.getCustomerNum());    
				pb.setCustomerNumLable(customerNumLable);
			}else {
				pb.setCustomerNumLable("0.00");
			}
			//百分比格式化 %   团队经理M1
			if(pb.getTermNum()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String termNumLable = myformat.format(pb.getTermNum());    
				pb.setTermNumLable(termNumLable);
			}else {
				pb.setTermNumLable("0.00");
			}
			//百分比格式化% 门店M1
			if(pb.getStoreNum()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String storeNumLable = myformat.format(pb.getStoreNum());    
				pb.setStoreNumLable(storeNumLable);
			}else {
				pb.setStoreNumLable("0.00");
			}
		}
		model.addAttribute("restrictedInletListpage", restrictedInletListpage);
		model.addAttribute("RestrictedInlet", restrictedInlet);
    	return "borrow/restrictedInlet/restrictedInletList";
    }
	
	/**
	 * 门店  服务器经理 逾期率以及高危线查看
	 * 2016年1月20日
	 * By 管洪昌
	 * @param model
	 * @param restrictedInlet
	 * @param request
	 * @param response
	 * @return 跳转到高危线设置界面
	 */
	@RequestMapping(value="stroreList")
    public String  stroreList(Model model,RestrictedInlet restrictedInlet,
    		HttpServletRequest request,HttpServletResponse response){
	    //查询登陆人门店信息
    	RestrictedInlet res=new RestrictedInlet();
		//当前登陆人是不是 门店经理
	    User user = UserUtils.getUser();
        List<Role> roleList = user.getRoleList();
        boolean isManager = false;
        if(!ObjectHelper.isEmpty(roleList)){
            for(Role role:roleList){
            	//团队经理
                if(LoanRole.STORE_MANAGER.id.equals(role.getId())){
                    isManager = true;
                    break;
                }  
                //服务经理
                if(LoanRole.STORE_SERVICE_MANAGER.id.equals(role.getId())){
                    isManager = true;
                    break;
                }  
            }
         }
        if(isManager){
        	restrictedInlet.setOrgCode(user.getDepartment().getId());
        	res=restrictedInletService.selectStr(restrictedInlet);
        	//百分比格式化 % 当前M1逾期率
			if(res.getM1YqlCurrent()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String m1YqlCurrentLable = myformat.format(res.getM1YqlCurrent());    
				res.setM1YqlCurrentLable(m1YqlCurrentLable);
			}else {
				res.setM1YqlCurrentLable("0.00");
			}
			//百分比格式化 % M1逾期率高危线
			if(res.getStoreNum()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String storeNumLable = myformat.format(res.getStoreNum());    
				res.setStoreNumLable(storeNumLable);
			}else {
				res.setStoreNumLable("0.00");
			}
        }else{
        	restrictedInlet.setOrgCode(" ");
        }
	    // 门店高危线设置
		restrictedInletListpage = restrictedInletService.selectStroreList(new Page<RestrictedInlet>(request,response), restrictedInlet);
		//是否限制进件
		for(RestrictedInlet pb:restrictedInletListpage.getList()){
			//岗位
			if(StringUtils.isNotEmpty(pb.getZkbjType())){
				String zkbjTypeLable=DictCache.getInstance().getDictLabel("jk_person_type",pb.getZkbjType());
				pb.setZkbjTypeLable(zkbjTypeLable);
			}
			//是否被限制进谏
			if(StringUtils.isNotEmpty(pb.getSfJj())){
				String sfJjLable=DictCache.getInstance().getDictLabel("jk_input_limit",pb.getSfJj());
				pb.setSfJjLable(sfJjLable);
			}
			//百分比格式化 % 当前M1逾期率
			if(pb.getM1YqlCurrent()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String m1YqlCurrentLable = myformat.format(pb.getM1YqlCurrent());    
				pb.setM1YqlCurrentLable(m1YqlCurrentLable);
			}else {
				pb.setM1YqlCurrentLable("0.00");
			}
			//百分比格式化 % M1逾期率高危线
			if(pb.getM1Yql()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String m1YqlLable = myformat.format(pb.getM1Yql());    
				pb.setM1YqlLable(m1YqlLable);
			}else {
				pb.setM1YqlLable("0.00");
			}
		}
		model.addAttribute("res", res);
		model.addAttribute("restrictedInletListpage", restrictedInletListpage);
		model.addAttribute("RestrictedInlet", restrictedInlet);
    	return "borrow/restrictedInlet/stroreList";
    }
	
	/**
	 * 查看门店限制进件情况
	 * 2016年1月20日
	 * By 管洪昌
	 * @param model
	 * @param restrictedInlet
	 * @param request
	 * @param response
	 * @return 跳转到门店限制进件情况
	 */
	@RequestMapping(value="storeRestrictedList")
    public String  storeRestrictedList(Model model,RestrictedInlet restrictedInlet,
    		HttpServletRequest request,HttpServletResponse response){
	    // 查询门店限制进件
		restrictedInletListpage = restrictedInletService.selectStoreRestricList(new Page<RestrictedInlet>(request,response), restrictedInlet);
		for(RestrictedInlet pb:restrictedInletListpage.getList()){
			//是否被限制进谏
			if(StringUtils.isNotEmpty(pb.getSfJj())){
				String sfJjLable=DictCache.getInstance().getDictLabel("jk_input_limit",pb.getSfJj());
				pb.setSfJjLable(sfJjLable);
			}
			//高危险标准
			String highStandardLable=DictCache.getInstance().getDictLabel("jk_denger_line",pb.getHighStandard());
			pb.setHighStandardLable(highStandardLable);
			//百分比格式化 % 当前M1逾期率
			if(pb.getM1YqlCurrent()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String m1YqlCurrentLable = myformat.format(pb.getM1YqlCurrent());    
				pb.setM1YqlCurrentLable(m1YqlCurrentLable);
			}else {
				pb.setM1YqlCurrentLable("0.00");
			}
			if(pb.getM1Yql()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String m1YqlCurrentLable = myformat.format(pb.getM1Yql());    
				pb.setM1YqlLable(m1YqlCurrentLable);
			}else {
				pb.setM1YqlLable("0.00");
			}
			//百分比格式化 % M1逾期率高危线
			if(pb.getStoreNum()!=null){
			    DecimalFormat myformat=new java.text.DecimalFormat("0.00");
				String storeNumLable = myformat.format(pb.getStoreNum());    
				pb.setStoreNumLable(storeNumLable);
			}else {
				pb.setStoreNumLable("0.00");
			}
			
		}
		model.addAttribute("restrictedInletListpage", restrictedInletListpage);
		model.addAttribute("RestrictedInlet", restrictedInlet);
    	return "borrow/restrictedInlet/storeRestrictedList";
    }
	
    /**
     * 跳转高危线设置
     * by 管洪昌
     * @param model
     * @param id
     * @return 要进行跳转的页面
     */
    @RequestMapping(value = { "restrictedInlet" })
    public String restrictedInlet(Model model,String id) {
    	if(!StringUtils.isBlank(id)){
    		id = id.substring(0,id.length()-1);
    	    model.addAttribute("id",appendString(id));
    	}
        return "borrow/restrictedInlet/restrictedInletManagementForm";
    }
    
    /**
     * 拼接字符串
     * 2016年1月8日 By wengsi
     * @param ids
     * @return idstring 拼接好的id
     */
    public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (String id : idArray) {
			if (id != null && !"".equals(id)) {
				parameter.append("'" + id + "',");
			}
		}
		String idstring = null;
		if (parameter != null) {
			idstring = parameter.toString();
			idstring = idstring.substring(0, parameter.lastIndexOf(","));
		}
		return idstring;
    }
	/**
     * 修改逾期高危险设置 
     * By 管洪昌
     * @param posBacktage
     * @param model
     * @return 修改状态
     */
    @RequestMapping(value="updateConsultRestrict")
    @ResponseBody
    public String updateConsultRestrict(RestrictedInlet restrictedInlet, Model model){
    	if(restrictedInlet.getTermNum()!=null  || restrictedInlet.getStoreNum()!=null ||restrictedInlet.getCustomerNum()!=null ){
    		restrictedInletService.updateConsultRestrict(restrictedInlet);
    	}
        return BooleanType.TRUE;
    }
	
    /**
     * 跳转到门店高危线设置
     * by 管洪昌
     * @param model
     * @param id
     * @return 要进行跳转的页面
     */
    @RequestMapping(value = { "storeInlet" })
    public String storeInlet(Model model,String id) {
    	if(!StringUtils.isBlank(id)){
    		id = id.substring(0,id.length()-1);
    	    model.addAttribute("id",appendString(id));
    	}
        return "borrow/restrictedInlet/storeInletForm";
    }
    
	/**
     * 修改门店逾期高危线设置
     * By 管洪昌
     * @param restrictedInlet
     * @param model
     * @return 修改状态
     */
    @RequestMapping(value="updateStoreRestrict")
    @ResponseBody
    public String updateStoreRestrict(RestrictedInlet restrictedInlet, Model model){
    	 //修改成省份高危线
    	if("0".equals(restrictedInlet.getHighStandard())){
    		restrictedInletService.updateStore(restrictedInlet);
    	  }    
        //自定义高危线修改
	   if("1".equals(restrictedInlet.getHighStandard())){
	    	restrictedInletService.updateStoreRestrict(restrictedInlet);
    	}    
        return BooleanType.TRUE;
    }
	
}
