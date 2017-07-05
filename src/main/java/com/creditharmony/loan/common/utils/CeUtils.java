/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsCeUtils.java
 * @Create By 王彬彬
 * @Create In 2016年4月28日 下午9:25:03
 */
package com.creditharmony.loan.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.service.DmService;

/**
 * @Class Name CeUtils
 * @author 王彬彬
 * @Create In 2016年4月28日
 */
public class CeUtils {

	/**
	 * 上传提前金额请申请资料 
	 * 2016年4月29日
	 * By 王彬彬
	 * @param files 上传的文件
	 * @param flag 文件夹标识
	 * @param ceFolderType 文件夹类型
	 * @return 上传返回信息
	 */
	public static DocumentBean uploadFile(MultipartFile files, String flag,
			CeFolderType ceFolderType) {
		DmService dmService = DmService.getInstance();
		FileInputStream is = null;
		DocumentBean doc = null;
		try {
			File f = LoanFileUtils.multipartFile2File(files);
			is = new FileInputStream(f);
			doc = dmService.createDocument(f.getName(), is,
					DmService.BUSI_TYPE_LOAN, ceFolderType.getName(), flag
							+ "_" + DateUtils.getDate("yyyyMMdd"), UserUtils
							.getUser().getId());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return doc;
	}
	

	/**
	 * 还款申请上传凭证 
	 * 2016年1月25日 
	 * By wbb
	 * @param files 上传的文件
	 * @param oldFilePath 需要删除的凭条
	 * @reutrn object 上传返回信息
	 */
	public static DocumentBean uploadFile(MultipartFile files,
			String oldFilePath, String flag, CeFolderType ceFolderType) {
		DmService dmService = DmService.getInstance();
		FileInputStream is = null;
		DocumentBean doc = null;
		try {
			File f = LoanFileUtils.multipartFile2File(files);
			is = new FileInputStream(f);
			doc = dmService.createDocument(f.getName(), is,
					DmService.BUSI_TYPE_LOAN, ceFolderType.getName() ,flag
					+ "_" + DateUtils.getDate("yyyyMMdd"), UserUtils
					.getUser().getId());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return doc;
	}
	
	/**
	 * 删除CE文件
	 * 2016年4月29日
	 * By 王彬彬
	 * @param docId 文档ID
	 */
	public static void deleteFile(String... docId) {
		DmService dmService = DmService.getInstance();
		try{
		if (!ObjectHelper.isEmpty(docId)) {
			for (String doc : docId) {
			   	dmService.deleteDocument(doc);
			}
		}
		}catch(Exception e){
		    e.printStackTrace();
		}
	}
	
	/**
	 * 根据文档业务类型下载文件包
	 * 2016年6月13日
	 * By 王彬彬
	 * @param businessType 业务类型（loan）
	 * @param batchNo 批次号
	 * @param subType 文件主题
	 * @param docCreator 创建人
	 * @param response 响应
	 * @throws IOException 
	 */
	public static List<DocumentBean> downFileBySubType(String batchNo,
			String subType, String docCreator, HttpServletResponse response) throws IOException {
		DmService dmService = DmService.getInstance();
		List<DocumentBean>  lstDocument = dmService.queryDocumentsWithContent(DmService.BUSI_TYPE_LOAN, batchNo, subType,
				docCreator);
		return lstDocument;
	}
}
