package com.creditharmony.loan.app.web;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunyard.client.SunEcmClientApi;
import com.sunyard.client.bean.ClientBatchBean;
import com.sunyard.client.bean.ClientBatchFileBean;
import com.sunyard.client.bean.ClientBatchIndexBean;
import com.sunyard.client.bean.ClientFileBean;
import com.sunyard.client.bean.ClientHeightQuery;
import com.sunyard.client.impl.SunEcmClientSocketApiImpl;
import com.sunyard.util.OptionKey;

/**
 * 客户端使用示例
 * 
 */
public class ClientQuery {
	private static final Logger log = LoggerFactory.getLogger(ClientQuery.class);
	/**
	 * 查询接口调用示例 -------------------------------------------------------
	 * @return 
	 * @throws Exception 
	 */
//	public String queryExample(String ip,int sorket,String groupName,String objName,String startdate,String busiserialno,String contentId,String maxVersion,String userName,String passWord) throws Exception {
	public String queryExample(String ip,int sorket,String groupName,String objName,String startdate,String busiserialno,String contentId,String userName,String passWord) throws Exception {

	SunEcmClientApi clientApi = new SunEcmClientSocketApiImpl(ip, sorket);
		ClientBatchBean clientBatchBean = new ClientBatchBean();
		clientBatchBean.setUser(userName);
		clientBatchBean.setPassWord(passWord);
		clientBatchBean.setDownLoad(false);
	//	clientBatchBean.getIndex_Object().setVersion(maxVersion);
		clientBatchBean.getIndex_Object().setContentID(contentId);
		clientBatchBean.setModelCode(objName);
		clientBatchBean.getIndex_Object().addCustomMap("BUSI_START_DATE", startdate);
		clientBatchBean.getIndex_Object().addCustomMap("BUSI_SERIAL_NO", busiserialno);
		
		String	 resultMsg = clientApi.queryBatch(clientBatchBean, groupName);
		log.debug("#######查询批次返回的信息[" + resultMsg + "]#######");
		return resultMsg;
	}

	/**
	 * 高级搜索调用示例 -------------------------------------------------------
	 * 最后结果为组上最大版本的批次号
	 */
	public String heightQueryExample(String ip,int sorket,String groupName,String objName,String startdate,String busiserialno,String userName,String passWord) {
		SunEcmClientApi clientApi = new SunEcmClientSocketApiImpl(ip, sorket);
		ClientHeightQuery heightQuery = new ClientHeightQuery();
		heightQuery.setUserName(userName);
		heightQuery.setPassWord(passWord);
		heightQuery.setLimit(999);
		heightQuery.setPage(1);
		heightQuery.setModelCode(objName);
		heightQuery.addCustomAtt("BUSI_START_DATE", startdate);
		heightQuery.addCustomAtt("BUSI_SERIAL_NO",  busiserialno);
		String resultMsg = "";
		try {
			// 获取contentId,用来查询影像()
			resultMsg = clientApi.heightQuery(heightQuery, groupName);
			log.debug("#######调用高级搜索返回的信息[" + resultMsg + "]#######");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMsg;
	}

	/*public String uploadExam(String ip,int sorket,String groupName,String busi_serial_no,String startDate,String modelCode ,String objNamePart,
			String userName,String password, String fileType,
			String scanner,String combineImg,String imgSize,String imgType ) throws Exception {*/
		public String uploadExam(String ip,int sorket,String groupName,String busi_serial_no,String startDate,String modelCode ,String objNamePart,
				String userName,String password, String fileType,
				String scanner,ArrayList<String> combineImgs,ArrayList<String> imgSizes,ArrayList<String> imgTypes ) throws Exception {
		/**
		 * userName,passWrod,modelCode,filePartName,BUSI_FILE_TYPE 不需修改
		 * 查询时自定义的属性BUSI_SERIAL_NO和BUSI_START_DATE的值必须与上传时对应
		 */
		SunEcmClientApi clientInter = new SunEcmClientSocketApiImpl(ip, sorket);
		
		ClientBatchBean clientBatchBean = new ClientBatchBean();
		
		clientBatchBean.setModelCode(modelCode);
	//	String filePartName = modelCode+"_P";
		clientBatchBean.setUser(userName);
		clientBatchBean.setPassWord(password);
	//	clientBatchBean.setBreakPoint(false); // 是否作为断点续传上传
	//	clientBatchBean.setOwnMD5(false); // 是否为批次下的文件添加MD5码

		// =========================设置索引对象信息开始=========================
		ClientBatchIndexBean clientBatchIndexBean = new ClientBatchIndexBean();
	//	clientBatchIndexBean.setAmount("1"); // 上传文件的数量
		// 获取当前系统日期
	//	String amount = "1";
		String amount =String.valueOf(combineImgs.size());
		// 索引自定义属性
		 clientBatchIndexBean.addCustomMap("BUSI_SERIAL_NO", busi_serial_no);
		clientBatchIndexBean.addCustomMap("BUSI_START_DATE", startDate); // 实际数据移植时，改成当前系统时间
		clientBatchIndexBean.addCustomMap("AMOUNT", String.valueOf(amount)); // 获取真实影像数量

		// =========================设置索引对象信息结束=========================
		clientBatchBean.setIndex_Object(clientBatchIndexBean);
		
		for (int i=0 ; i<combineImgs.size();i++) {
			ClientFileBean fileBean1 = new ClientFileBean();
			String combineImg = combineImgs.get(i);
			String imgSize = imgSizes.get(i);
			String imgType = imgTypes.get(i);
//			String fileName = files.get(i).get(0);
//			String fileFormat = fileName.substring(fileName.lastIndexOf(".")+1);
			fileBean1.setFileName(combineImg);
			fileBean1.setFilesize(imgSize);
			fileBean1.setFileFormat(imgType); // 上传文件后缀名
			fileBean1.addOtherAtt("BUSI_FILE_TYPE", fileType); // 文件类型(不需修改)
			fileBean1.addOtherAtt("BUSI_FILE_SCANUSER", scanner);
			fileBean1.addOtherAtt("BUSI_FILE_PAGENUM", String.valueOf(i+1));
			
			// =========================设置文档部件信息开始=========================
			ClientBatchFileBean clientBatchFileBeanA = new ClientBatchFileBean();
			clientBatchFileBeanA.setFilePartName(objNamePart);
			clientBatchFileBeanA.addFile(fileBean1);
			clientBatchBean.addDocument_Object(clientBatchFileBeanA);
		}
			
			String resultMsg = clientInter.upload(clientBatchBean, groupName);
			log.debug("#######上传批次返回的信息[" + resultMsg + "]#######");
			return resultMsg;
	}

//	public String updateExample(String ip,int sorket,String groupName,String userName,String passWord,String modelCode, String objNamePart,String contentID,
//			String startDate, String busiSerialNo, String amount,
//			 String combineImg,String imgSize,String imgType, int pageNum,String scanner,String fileType) throws Exception {
		
	public String updateExample(String ip,int sorket,String groupName,String userName,String passWord,String modelCode, String objNamePart,String contentID,
	  String startDate, String busiSerialNo, String amount,
      ArrayList<String> combineImgs,ArrayList<String> imgSizes,ArrayList<String> imgTypes, int pageNum,String scanner,String fileType) throws Exception {

		SunEcmClientApi clientInter = new SunEcmClientSocketApiImpl(ip, sorket);
		ClientBatchBean clientBatchBean = new ClientBatchBean();
		clientBatchBean.setModelCode(modelCode);
		clientBatchBean.setUser(userName);
		clientBatchBean.setPassWord(passWord);
		clientBatchBean.getIndex_Object().setContentID(contentID);
		clientBatchBean.getIndex_Object().addCustomMap("BUSI_START_DATE",
				startDate);
		clientBatchBean.getIndex_Object().addCustomMap("BUSI_SERIAL_NO",
				busiSerialNo);
	/*	clientBatchBean.getIndex_Object().addCustomMap("AMOUNT",
				String.valueOf(Integer.valueOf(amount) + 1));*/
		clientBatchBean.getIndex_Object().addCustomMap("AMOUNT",
				String.valueOf(Integer.valueOf(amount) + combineImgs.size()));
		
		for (int i=0 ; i<combineImgs.size();i++) {
			pageNum = pageNum+1;
			ClientFileBean fileBean1 = new ClientFileBean();
			String combineImg = combineImgs.get(i);
			String imgSize = imgSizes.get(i);
			String imgType = imgTypes.get(i);
//			String fileName = fileInfo.get(i).get(0);
//			String fileFormat = fileName.substring(fileName.lastIndexOf(".")+1);
			fileBean1.setOptionType(OptionKey.U_ADD);
			fileBean1.setFileName(combineImg);
			fileBean1.setFilesize(imgSize);
			fileBean1.setFileFormat(imgType); // 上传文件后缀名
			fileBean1.addOtherAtt("BUSI_FILE_TYPE", fileType); // 文件类型(不需修改)
			fileBean1.addOtherAtt("BUSI_FILE_SCANUSER", scanner);
//			fileBean1.addOtherAtt("TUPIAN_NAME", fileName);
			fileBean1.addOtherAtt("BUSI_FILE_PAGENUM", String.valueOf(pageNum));
	       System.out.println(pageNum);
			// =========================设置文档部件信息开始=========================
			ClientBatchFileBean clientBatchFileBeanA = new ClientBatchFileBean();
			clientBatchFileBeanA.setFilePartName(objNamePart);
			clientBatchFileBeanA.addFile(fileBean1);
			clientBatchBean.addDocument_Object(clientBatchFileBeanA);
		}
		// 为了将此上传时间打印到配置文件中而写。以方便查询。
			String resultMsg = "";

			resultMsg = clientInter.update(clientBatchBean, groupName, true);
			System.out.println("resultMsg : " + resultMsg);
			log.debug("#######更新批次返回的信息[" + resultMsg + "]#######");

		return resultMsg;
	}

}
