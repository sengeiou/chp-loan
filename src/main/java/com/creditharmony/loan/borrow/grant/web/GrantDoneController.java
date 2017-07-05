package com.creditharmony.loan.borrow.grant.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.creditharmony.loan.util.sunscan.ClientScan;
import cn.creditharmony.loan.util.sunscan.PropertiesScan;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.Encodes;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.excel.util.ImportExcel;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.service.GrantAuditService;
import com.creditharmony.loan.borrow.grant.service.GrantDoneService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.ExportEntrustReflectHelper;
import com.creditharmony.loan.borrow.grant.util.ExportGrantDoneHelper;
import com.creditharmony.loan.borrow.grant.util.ExportGrantDoneTGHelper;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.trusteeship.dao.LoanExcelDao;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel5;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ZipUtils;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;
import com.google.common.collect.Lists;

/**
 * 放款确认列表处理事件
 * @Class Name GrantDoneController
 * @author 朱静越
 * @Create In 2015年12月16日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/grantDone")
public class GrantDoneController extends BaseController {

	@Autowired
	private GrantDoneService grantDoneService;
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private GrantAuditService grantAuditService;
	@Autowired
	private LoanExcelDao loanExcelDao;
	@Autowired
    private UserManager userManager;
	@Autowired
    private LoanPrdMngService loanPrdMngService;
	// 数据列表缓存
	private List<LoanGrantEx> backList = Lists.newArrayList();
	
	public static String PRODUCT_NYJ = "农信贷";

	/**
	 * 查询放款已办页面 
	 * 2015年12月15日 By 朱静越
	 * @param model
	 * @param loanGrantEx 放款实体
	 * @return
	 */
	@RequestMapping(value = "grantDone")
	public String grantDone(Model model, LoanGrantEx loanGrantEx,
			HttpServletRequest request, HttpServletResponse response,
			String listFlag) {
		// 查询金账户列表
		if (LoanModel.TG.getName().equals(listFlag)) {
			loanGrantEx.setModel(LoanModel.TG.getCode());
			loanGrantEx.settGFlag(YESNO.YES.getCode());
			loanGrantEx.setDictLoanWay(PaymentWay.KING_ACCOUNT.getCode());
		}
		GrantUtil.setStoreOrgId(loanGrantEx);
		Page<LoanGrantEx> grantDonePage = grantDoneService.findGrantDone(
				new Page<LoanGrantEx>(request, response), loanGrantEx);
		if(null != grantDonePage && null != grantDonePage.getList()){
			backList.clear();
			// 保存数据列表信息
			backList = grantDonePage.getList();
		}
		BigDecimal totalGrantMoney = new BigDecimal(0.00);
		long totalCount = 0;
		List<LoanGrantEx> grantDoneList = grantDonePage.getList();
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(grantDoneList)) {
			totalCount = grantDonePage.getCount();
			totalGrantMoney = grantDoneList.get(0).getTotalGrantMoney();
			dictMap = DictCache.getInstance().getMap();
			for (LoanGrantEx loanGrant:grantDoneList) {
				// 从缓存中取值
				loanGrant.setDictIsAdditional(DictUtils.getLabel(dictMap,LoanDictType.ADD_FLAG, loanGrant.getDictIsAdditional()));
				loanGrant.setDictLoanWay(DictUtils.getLabel(dictMap,LoanDictType.PAYMENT_WAY, loanGrant.getDictLoanWay()));
				loanGrant.setTrustCash(DictUtils.getLabel(dictMap,LoanDictType.TRUST_STATUS, loanGrant.getTrustCash()));
			}
		}
		// 查询所有的放款人员
		Map<String, String> mapUser = new HashMap<String, String>();
		mapUser.put("roleId", BaseRole.DELIVERY_PERSON.id);
		mapUser.put("departmentId", UserUtils.getUser().getDepartment().getId());
		List<UserInfo> user = userInfoService.getRoleUser(mapUser);
		// 放款人员下拉框
		List<LoanGrant> grantPchList = loanGrantService.selGrantPch();
		// 获得汇金产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		model.addAttribute("user", user);
		model.addAttribute("totalNum", totalCount);
		model.addAttribute("totalGrantMoney", totalGrantMoney);
		model.addAttribute("loanGrantEx", loanGrantEx);
		model.addAttribute("grantDoneList", grantDonePage);
		model.addAttribute("grantPchList", grantPchList);
		model.addAttribute("productList", productList);
		model.addAttribute("listFlag", listFlag);
		return "borrow/grant/grantDoneList";
	}

	/**
	 * 导出资金托管
	 * 2016年4月25日
	 * By 朱静越
	 * @param codes
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportEntrustReflect")
	public void exportEntrustReflect(String[] codes,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != codes && codes.length > 0) {
			map.put("contractCodes", codes);
		} else {
			if (backList.size() > 0) {
				List<String> codeList = new ArrayList<String>();
				for (LoanGrantEx item : backList) {
					codeList.add(item.getContractCode());
				}
				String[] codeArray = (String[]) codeList
						.toArray(new String[codeList.size()]);
				map.put("contractCodes", codeArray);
			}
		}
		ExportEntrustReflectHelper.exportData(map, response);
	}
	
	/**
	 * 导入已放款委托提现
	 * 2017年1月19日
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@RequestMapping("importExcel")
	@ResponseBody
	public String importExcel(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam MultipartFile file){
		String result="";
		try {
			if(null != file){
				ImportExcel excel = new ImportExcel(file, 0, 0);
				List<GrantExcel5> list = excel.getDataList(GrantExcel5.class, 1);
				if(ArrayHelper.isNotEmpty(list)){
					for (int i=0; i<list.size();i++) {
						try{
							grantDoneService.importExcel1(list.get(i));
						}catch(Exception e){
							logger.error("已放款委托提现导入失败",e);
							result += "第" + (i+1) + "行数据导入失败;";
						}
					}
					
					
				}
			}
		} catch (Exception e) {
			logger.error("方法：importExcel发生异常，导出excel出现错误",e);
			result = "fail";
		}
		return result;
	}

	/**
	 * 导出已放款列表 
	 * 2015年12月26日 By 朱静越
	 * @param request
	 * @param response
	 * @param idVal
	 *            根据合同编号
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "grantDoneExl")
	public String grantDoneExl(HttpServletRequest request,RedirectAttributes redirectAttributes,
			LoanGrantEx loanGrantEx, HttpServletResponse response, String idVal,String listFlag) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] contractCodes = null;
		boolean success = true;
		GrantUtil.setStoreOrgId(loanGrantEx);
		try {
			if (LoanModel.TG.getName().equals(listFlag)) {
				loanGrantEx.setModel(LoanModel.TG.getCode());
				loanGrantEx.settGFlag(YESNO.YES.getCode());
				loanGrantEx.setDictLoanWay(PaymentWay.KING_ACCOUNT.getCode());
			}
			// 如果有进行选择，根据合同编号进行单个查询，
			if (StringUtils.isNotEmpty(idVal)) {
				contractCodes = idVal.split(",");
				map.put("contractCodes", contractCodes);
			}
			map.put("loanGrant", loanGrantEx);
			if (LoanModel.TG.getName().equals(listFlag)) {
				String[] header={"客户姓名","证件号码","团队经理","团队经理编号","客户经理","客户经理编号","借款类型","合同编号","合同金额","放款金额","批借金额","信访费","催收服务费","产品类型","批借期限","账号","机构","放款时间","放款审核人员","标识","是否电销","是否加急","放款批次","提交批次","提交时间"};
				ExportGrantDoneTGHelper.exportData(map,header,response);
			}else {
				String[] header={"客户姓名","证件号码","团队经理","团队经理编号","客户经理","客户经理编号","借款类型","合同编号","合同金额","放款金额","批借金额","信访费","催收服务费","产品类型","批借期限","放款账户","开户行","账号","机构","放款时间","放款审核人员","标识","是否电销","是否加急","放款批次","提交批次","提交时间","征信费","费用总计"};
				ExportGrantDoneHelper.exportData(map,header,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			logger.error("方法：grantDoneExl发生异常，导出excel出现错误");
			addMessage(redirectAttributes, "放款已办列表导出出错！");
		}finally{
			if (success) {
				return null;
			}else{
				return "redirect:"
		                + adminPath
		                + "/borrow/grant/grantDone/grantDone";
			}
		}
	}
	
	/**
	 * 委托划扣页面
	 * 2016年11月8日 By WJJ
	 * @param model
	 * @param loanGrantEx 放款实体
	 * @return
	 */
	@RequestMapping(value = "wthkList")
	public String wthkList(Model model, LoanGrantEx loanGrantEx,
			HttpServletRequest request, HttpServletResponse response) {
		GrantUtil.setStoreOrgId(loanGrantEx);
		if(loanGrantEx.getBankId()!=null&&!"".equals(loanGrantEx.getBankId())){
			loanGrantEx.setBankIds(loanGrantEx.getBankId().split(","));
		}
		Page<LoanGrantEx> grantDonePage = grantDoneService.wthkList(
				new Page<LoanGrantEx>(request, response), loanGrantEx);
		List<LoanGrantEx> grantDoneList = grantDonePage.getList();
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(grantDoneList)) {
			dictMap = DictCache.getInstance().getMap();
			for (LoanGrantEx loanGrant:grantDoneList) {
				// 从缓存中取值
				loanGrant.setDictLoanStatus(DictUtils.getLabel(dictMap,LoanDictType.LOAN_STATUS, loanGrant.getDictLoanStatus()));
			}
		}
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(new LoanPrdMngEntity());
		model.addAttribute("productList", productList);
		model.addAttribute("loanGrantEx", loanGrantEx);
		model.addAttribute("grantDoneList", grantDonePage);
		return "borrow/grant/wthkList";
	}
	
	/**
	 * 从影像平台下载文件
	 * 2015年12月9日 By WJJ
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getFile")
	public void getFile(Model model,
			HttpServletRequest request, HttpServletResponse response,String loanCode) {
		
		String ip = PropertiesScan.Val("ip");
		String userName = PropertiesScan.Val("userName");
		String passWord = PropertiesScan.Val("passWord");
		String objName = PropertiesScan.Val("objName");
		String groupName = PropertiesScan.Val("groupName"); // 内容存储服务器组名
		int sorket = Integer.parseInt(PropertiesScan.Val("sorket"));//工程端口，不是网络访问端口，不可修改
		List<Map<String,String>> list = grantDoneService.findStartDate(new String[]{loanCode});
		File[] tempList = null;
		if(!list.isEmpty()){
			ClientScan client = new ClientScan();
			String contractCode = list.get(0).get("contract_code");
			String busiserialno = loanCode;//"JK2016092100000025";
			String startdate = list.get(0).get("customer_into_time");//"20160921";
			String jx = "";
			if(ChannelFlag.JINXIN.getCode().equals(list.get(0).get("loan_flag"))){
				jx = contractCode + ChannelFlag.JINXIN.getName() + "/";
			}
			String fileType = PropertiesScan.Val("fileType");
			//String scanUser = UserUtils.getUser().getName();
		    SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");  
		    String ymd = sdf.format(new Date());  
			String downFilePath = PropertiesScan.Val("downFilePath")+ymd+"/" + jx;//"F:\\wthk\\";//目录文件，注意区分是WIN还是linux
			File delFile = new File(PropertiesScan.Val("downFilePath")+ymd+"/");
			File pathFile = new File(downFilePath);
			pathFile.mkdirs();
			String imgName = "";//暂时未用
			String resMsg="";//返回结果信息，如果返回FAIL:下载失败，XML对像：下载成功
			try {
				//PrintWriter out = response.getWriter();
				logger.error("开始下载影像平台文件到服务器,合同编号:"+contractCode+",借款编号"+busiserialno);
				resMsg = client.download(ip, sorket, groupName,userName, passWord, objName,  busiserialno, startdate, downFilePath,fileType, imgName);
				if("FAIL".equals(resMsg)){
					pathFile.delete();
					logger.error("下载影像平台文件到服务器失败,合同编号:"+contractCode+",借款编号:"+busiserialno);
					//out.print("{\"msg\":\"false\",\"loanCode\":\"\",\"path\":\"\"}");	
				}else if("".equals(resMsg)||"NOTHING".equals(resMsg)){
					
					logger.error("此合同没有文件可下载,合同编号:"+contractCode+",借款编号:"+busiserialno);
					//out.print("{\"msg\":\"0\",\"loanCode\":\""+loanCode+"\",\"path\":\""+ymd+"\"}");	
					response.setContentType("multipart/form-data");
					response.setCharacterEncoding("UTF-8");
					response.setHeader(
							"Content-Disposition",
							"attachment; filename="
									+ Encodes.urlEncode(contractCode+".zip")
									+ ";filename*=UTF-8''"
									+ Encodes.urlEncode(contractCode+".zip"));
					File ZipFile = new File(downFilePath+contractCode+".zip");
					ZipUtils.zipFiles(tempList, ZipFile);
					this.down(response,ZipFile);
					pathFile.delete();
				}else{
					logger.error("下载影像平台文件到服务器成功,合同编号:"+contractCode+",借款编号:"+busiserialno);
					//out.print("{\"msg\":\"true\",\"loanCode\":\""+loanCode+"\",\"path\":\""+ymd+"\"}");	
					tempList = pathFile.listFiles();
					if(tempList.length>1){
						for(int fi=0;fi<tempList.length;fi++){
							int num = fi + 1;
							String fn = tempList[fi].getName();
							File nFile = new File(downFilePath + contractCode + "-" + num + "." + fn.substring(fn.lastIndexOf(".")+1));
							tempList[fi].renameTo(nFile);
							tempList[fi]=nFile;
						}
						response.setContentType("multipart/form-data");
						response.setCharacterEncoding("UTF-8");
						response.setHeader(
								"Content-Disposition",
								"attachment; filename="
										+ Encodes.urlEncode(contractCode+".zip")
										+ ";filename*=UTF-8''"
										+ Encodes.urlEncode(contractCode+".zip"));
						File ZipFile = new File(delFile+contractCode+".zip");
						//ZipUtils.zipFiles(delFile.listFiles(), ZipFile);
						ZipUtils.ZipFiles(ZipFile,"",delFile.listFiles()); 
						this.down(response,ZipFile);
					}else if(tempList.length==1){
						if (tempList[0].isFile()) {
							response.setContentType("multipart/form-data");
							response.setCharacterEncoding("UTF-8");
							String prefix = tempList[0].getName().substring(tempList[0].getName().lastIndexOf(".")+1);
							response.setHeader(
									"Content-Disposition",
									"attachment; filename="
											+ Encodes.urlEncode(contractCode+"."+prefix)
											+ ";filename*=UTF-8''"
											+ Encodes.urlEncode(contractCode+"."+prefix));
						    this.down(response,tempList[0]);
						}
					}
				}
			} catch (Exception e) {
				pathFile.delete();
				e.printStackTrace();
				logger.error("下载影像平台文件到服务器出错,合同编号:"+contractCode+",借款编号"+busiserialno);
			}finally{
				ZipUtils.delFiles(tempList);
				pathFile.delete();
				delFile.delete();
			}
		}
	}
	
	/**
	 * 从影像平台下载文件
	 * 2015年12月9日 By WJJ
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getFiles")
	public void getFiles(Model model, LoanGrantEx loanGrantEx,
			HttpServletRequest request, HttpServletResponse response) {
		/*PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		boolean msg = true;
		String ip = PropertiesScan.Val("ip");
		String userName = PropertiesScan.Val("userName");
		String passWord = PropertiesScan.Val("passWord");
		String objName = PropertiesScan.Val("objName");
		String groupName = PropertiesScan.Val("groupName"); // 内容存储服务器组名
		int sorket = Integer.parseInt(PropertiesScan.Val("sorket"));//工程端口，不是网络访问端口，不可修改
		
		ClientScan client = new ClientScan();
		
		
		
		String fileType = PropertiesScan.Val("fileType");
		//String scanUser = UserUtils.getUser().getName();
	    SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");  
	    String ymd = sdf.format(new Date());  
		
		
		String imgName = "";//暂时未用
		String resMsg="";//返回结果信息，如果返回FAIL:下载失败，XML对像：下载成功
		
		File downPathFile = new File(PropertiesScan.Val("downFilePath")+ymd);
		File zipFile = new File(PropertiesScan.Val("downFilePath")+"批量下载.zip");
		
		List<Map<String,String>> list = new ArrayList();
		if("".equals(loanGrantEx.getLoanCode())){
			//list = grantDoneService.getLoanCode(loanGrantEx);
		}else{
			String[] loanCodes = loanGrantEx.getLoanCode().split(",");
			list = grantDoneService.findStartDate(loanCodes);
		}
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				String contractCode = list.get(i).get("contract_code");
				String busiserialno = list.get(i).get("loan_code");//"JK2016092100000025";
				String startdate = list.get(i).get("customer_into_time");//"20160921";
				String jx = "";
				if(ChannelFlag.JINXIN.getCode().equals(list.get(i).get("loan_flag"))){
					jx = ChannelFlag.JINXIN.getName();
				}
				String downFilePath = PropertiesScan.Val("downFilePath") + ymd + "/" + list.get(i).get("contract_code") + jx + "/";//"F:\\wthk\\";//目录文件，注意区分是WIN还是linux
				File pathFile = new File(downFilePath);
				pathFile.mkdirs();
				try {
					logger.error("开始下载影像平台文件到服务器,合同编号:"+contractCode+",借款编号"+busiserialno);
					resMsg = client.download(ip, sorket, groupName,userName, passWord, objName,  busiserialno, startdate, downFilePath,fileType, imgName);
					if("FAIL".equals(resMsg)){
						msg = false;
						pathFile.delete();
						logger.error("下载影像平台文件到服务器失败,合同编号:"+contractCode+",借款编号:"+busiserialno);
					}else if("".equals(resMsg)){
						pathFile.delete();
						logger.info("此合同没有文件可下载,合同编号:"+contractCode+",借款编号:"+busiserialno);
					}else{
						File[] tempList = pathFile.listFiles();
						if(tempList!=null&&tempList.length!=1){
							for(int fi=0;fi<tempList.length;fi++){
								int num = fi + 1;
								String fn = tempList[fi].getName();
								File nFile = new File(downFilePath + contractCode + "-" + num + "." + fn.substring(fn.lastIndexOf(".")+1));
								tempList[fi].renameTo(nFile);
							}
						}else{
							String fn = tempList[0].getName();
							File nFile = new File(downFilePath + contractCode + "." + fn.substring(fn.lastIndexOf(".")+1));
							tempList[0].renameTo(nFile);
						}
						logger.info("下载影像平台文件到服务器成功,合同编号:"+contractCode+",借款编号:"+busiserialno);
					}
				} catch (Exception e) {
					msg = false;
					pathFile.delete();
					e.printStackTrace();
					logger.error("下载影像平台文件到服务器出错,合同编号:"+contractCode+",借款编号"+busiserialno);
				}
			}
			
			try{
				logger.error("开始下载影像平台文件到本地");
					response.setContentType("multipart/form-data");
					response.setCharacterEncoding("UTF-8");
					response.setHeader(
							"Content-Disposition",
							"attachment; filename="
									+ Encodes.urlEncode("批量下载.zip")
									+ ";filename*=UTF-8''"
									+ Encodes.urlEncode("批量下载.zip"));
					
			        ZipUtils.ZipFiles(zipFile,"",downPathFile.listFiles()); 
					this.down(response,zipFile);
			}catch(Exception e){
				logger.error("下载到本地出错");
			}finally{
				ZipUtils.delFiles(downPathFile.listFiles());
				downPathFile.delete();
			}
		}else{
			try{
				logger.error("开始下载影像平台文件到本地");
					response.setContentType("multipart/form-data");
					response.setCharacterEncoding("UTF-8");
					response.setHeader(
							"Content-Disposition",
							"attachment; filename="
									+ Encodes.urlEncode("批量下载.zip")
									+ ";filename*=UTF-8''"
									+ Encodes.urlEncode("批量下载.zip"));
					
			        ZipUtils.ZipFiles(zipFile,"",downPathFile.listFiles()); 
					this.down(response,zipFile);
			}catch(Exception e){
				logger.error("下载到本地出错");
			}
		}
		//out.print("{\"msg\":\""+msg+"\",\"path\":\""+ymd+"\"}");	
	}
	
	/**
	 * 下载文件
	 * 2015年12月9日 By WJJ
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "downFile")
	public void downFile(Model model,
			HttpServletRequest request, HttpServletResponse response,String loanCode,String path) {
		
		List<Map<String,String>> list = grantDoneService.findStartDate(new String[]{loanCode});
		String contractCode = list.get(0).get("contract_code");
		String busiserialno = loanCode;//"JK2016092100000025";
		String downFilePath = PropertiesScan.Val("downFilePath")+path+"/";//"F:\\wthk\\";//目录文件，注意区分是WIN还是linux
		logger.error(downFilePath);
		File pathFile = new File(downFilePath);
		File[] tempList = pathFile.listFiles();
		//下载文件
		try{
			logger.error("开始下载影像平台文件到本地,合同编号:"+contractCode+",借款编号"+busiserialno);
			if(tempList.length>1){
				response.setContentType("multipart/form-data");
				response.setCharacterEncoding("UTF-8");
				response.setHeader(
						"Content-Disposition",
						"attachment; filename="
								+ Encodes.urlEncode(contractCode+".zip")
								+ ";filename*=UTF-8''"
								+ Encodes.urlEncode(contractCode+".zip"));
				File ZipFile = new File(downFilePath+contractCode+".zip");
				ZipUtils.zipFiles(tempList, ZipFile);
				this.down(response,ZipFile);
			}else if(tempList.length==1){
				if (tempList[0].isFile()) {
					response.setContentType("multipart/form-data");
					response.setCharacterEncoding("UTF-8");
					String prefix = tempList[0].getName().substring(tempList[0].getName().lastIndexOf(".")+1);
					response.setHeader(
							"Content-Disposition",
							"attachment; filename="
									+ Encodes.urlEncode(contractCode+"."+prefix)
									+ ";filename*=UTF-8''"
									+ Encodes.urlEncode(contractCode+"."+prefix));
				    this.down(response,tempList[0]);
				}
			}else{
				PrintWriter out = response.getWriter();
				out.print("ERROR");
			}
		}catch(Exception e){
			logger.error("下载到本地出错,合同编号:"+contractCode+",借款编号:"+busiserialno);
		}finally{
			ZipUtils.delFiles(tempList);
			pathFile.delete();
		}
	}
	
	/**
	 * 下载文件
	 * 2015年12月9日 By WJJ
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "downFiles")
	public void downFiles(Model model,
			HttpServletRequest request, HttpServletResponse response,String path) {
		String downFilePath = PropertiesScan.Val("downFilePath")+"／";//"F:\\wthk\\";//目录文件，注意区分是WIN还是linux
		logger.error(downFilePath);
		File pathFile = new File(downFilePath+path);
		File zipFile = new File(downFilePath+"批量下载.zip");
		//下载文件
		try{
			logger.error("开始下载影像平台文件到本地");
				response.setContentType("multipart/form-data");
				response.setCharacterEncoding("UTF-8");
				response.setHeader(
						"Content-Disposition",
						"attachment; filename="
								+ Encodes.urlEncode("批量下载.zip")
								+ ";filename*=UTF-8''"
								+ Encodes.urlEncode("批量下载.zip"));
				
		        ZipUtils.ZipFiles(zipFile,"",pathFile.listFiles()); 
				this.down(response,zipFile);
		}catch(Exception e){
			logger.error("下载到本地出错");
		}finally{
			ZipUtils.delFiles(pathFile.listFiles());
			pathFile.delete();
		}
	}
	
	public void down(HttpServletResponse response,File file) {
		InputStream inputStream = null;
		OutputStream os = null;
		try{
			inputStream = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}			
		}catch(Exception e){
			logger.error("下载到本地出错,文件名:"+file.getName());
		}finally{
			try{
				if(inputStream!=null){
					inputStream.close();
				}
				if(os!=null){
					os.flush();
					os.close();
				}
			}catch(Exception e){
				e.addSuppressed(e);
			}
			file.delete();
		}
	}
	
}
