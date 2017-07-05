package com.creditharmony.loan.doc.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.filenet.service.datatype.Pagination;
import com.creditharmony.dm.filenet.service.datatype.RetrievalResult;
import com.creditharmony.dm.service.DmService;

/**
 * 长春市弘亚小额贷款有限公司文档上传和下载功能控制器
 * 
 * @author chenwd
 *
 */
@Controller
@RequestMapping("${adminPath}/documentManage")
public class DocumentManageController extends BaseController implements HandlerExceptionResolver{

	@RequestMapping(value = "/upload")
	public String upload(@RequestParam("file") MultipartFile multipartFile)
			throws Exception {
		User userInfo = (User) UserUtils.getSession().getAttribute(
				SystemConfigConstant.SESSION_USER_INFO);
		DmService dmService = DmService.getInstance();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		dmService.createDocument(multipartFile.getOriginalFilename(),
				multipartFile.getInputStream(),
				DmService.BUSI_TYPE_MICRO_CREDIT,
				String.valueOf(calendar.get(Calendar.YEAR)),
				String.valueOf(calendar.get(Calendar.MONTH) + 1),
				userInfo.getName());
		return "redirect:" + adminPath + "/documentManage/queryDocs";
	}
	

	@RequestMapping("/queryDocs")
	public String queryDocs(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Page<DocumentBean> page = queryDocList(request, response);
		model.addAttribute("page", page);
		return "doc/docList";
	}

	private Page<DocumentBean> queryDocList(HttpServletRequest request,
			HttpServletResponse response) {
		DmService dmService = DmService.getInstance();
		Page<DocumentBean> page = new Page<DocumentBean>(request, response);
		RetrievalResult result = dmService.searchDocs(page.getPageNo(),
				page.getPageSize(), DmService.BUSI_TYPE_MICRO_CREDIT);
		 
		Pagination pagination = result.getPagination();
		page.setCount(pagination.getTotalObjects());
		page.setList((List<DocumentBean>) result.getObjectList());
		return page;
	}

	@RequestMapping("/download/{id}")
	public void download(HttpServletResponse response,
			@PathVariable("id") String id) throws IOException {
		if (StringUtils.isEmpty(id)) {
			throw new RuntimeException("帮助文件无效，请联系管理员！DocId:" + id);
		}
		DmService dmService = DmService.getInstance();
		String docId = "{" + id + "}";
		DocumentBean document = dmService.getDocument(docId);
		response.addHeader("Content-disposition", "attachment;filename="
				+ new String(document.getDocTitle().getBytes("GBK"),
						"ISO8859-1"));
		response.setCharacterEncoding("GBK");
		dmService.download(response.getOutputStream(), docId);
	}

	/**
	 * 超过文档大小限制时页面依据maxUploadSize是否为yes进行提示
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if(ex instanceof MaxUploadSizeExceededException){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("maxUploadSize", "yes");
			Page<DocumentBean> page = queryDocList(request, response);
			model.put("page", page);
			return new ModelAndView("doc/docList", model);
		}else{
			return null;
		}
	}

}
