package com.creditharmony.loan.car.common.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.excel.export.ExcelUtil;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.common.entity.ex.CarLoanGrantHaveEx;
import com.creditharmony.loan.car.common.entity.ex.CarLoanStatusHisEx;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.users.service.UserInfoService;
import com.google.common.collect.Lists;

/**
 * 放款已办
 * @Class Name GrantDoneController
 */
@Controller
@RequestMapping(value = "${adminPath}/car/common/grantDone")
public class CarGrantDoneExController extends BaseController {
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private CarHistoryService carHistoryService;
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 导出已放款列表
	 * @param request
	 * @param response
	 * @param idVal 根据合同编号
	 */
	@RequestMapping(value = "grantDoneHaveEx")
	public void grantDoneExl(HttpServletRequest request,CarLoanStatusHisEx carLoanStatusHisEx,
			HttpServletResponse response, String idVal) {
		ExcelUtils excelutil = new ExcelUtils();
		String isQueryAll = YESNO.NO.getCode();
		String isIn = YESNO.YES.getCode();
		String nodeValListStr = CarLoanSteps.GRANT_AUDIT.getCode();
		//设置为导出excel表
		carLoanStatusHisEx.setIsAllData("1");
		if(StringUtils.isNotEmpty(idVal)){
			//导出选中的已划扣数据
			 String[] id = idVal.split(",");
			List<String> applyIdList = Lists.newArrayList();
			for (int i = 0; i < id.length; i++){
				applyIdList.add(id[i]);
			}
			carLoanStatusHisEx.setApplyIdList(applyIdList);
		}
		List<CarLoanGrantHaveEx> list = carHistoryService.findDoneListForXls(new Page<CarLoanGrantHaveEx>(request,response), carLoanStatusHisEx, isIn, isQueryAll, nodeValListStr, null);
		List<Dict> dictList = DictCache.getInstance().getList();
		for (CarLoanGrantHaveEx carLoanGrantHaveExN : list) {
			if(null !=carLoanGrantHaveExN.getProductTypeContract() && null !=CarLoanProductType.parseByCode(carLoanGrantHaveExN.getProductTypeContract())){
				carLoanGrantHaveExN.setProductTypeContract(CarLoanProductType.parseByCode(carLoanGrantHaveExN.getProductTypeContract()).getName());
			}
			for(Dict dict:dictList){
				if(null != dict.getValue() && "jk_telemarketing".equals(dict.getType()) && dict.getValue().equals(carLoanGrantHaveExN.getTelesalesFlag())){
					carLoanGrantHaveExN.setTelesalesFlag(dict.getLabel());
				}
				if(null != dict.getValue() && "jk_car_throuth_flag".equals(dict.getType()) && dict.getValue().equals(carLoanGrantHaveExN.getLoanFlag())){
					carLoanGrantHaveExN.setLoanFlag(dict.getLabel());
				}
			}
		}
//		String title = FileExtension.GRANT_DONE+DateUtils.getDate("yyyyMMddHHmmss");
//		try {
//			ExcelUtil.getInstance().exportObj2ExcelByTemplate(FileExtension.GRANT_DONE,title+FileExtension.XLSX, response, list, CarLoanGrantHaveEx.class, true);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		excelutil.exportExcel(list, FileExtension.GRANT_DONE,CarLoanGrantHaveEx.class, FileExtension.XLSX,
		FileExtension.OUT_TYPE_TEMPLATE, response, null);
	}
}
