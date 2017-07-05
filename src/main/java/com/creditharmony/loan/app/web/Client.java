package com.creditharmony.loan.app.web;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class Client {
	ClientQuery c = new ClientQuery();
	public String query(String ip,int sorket,String groupName,String userName,String passWord,String objName ,String busiserialno,String startdate) throws Exception {
	
		ClientQuery clientQuery = new ClientQuery();
		String resultMsg = clientQuery.heightQueryExample(ip,sorket,groupName,objName, startdate, busiserialno, userName, passWord);
    	// 查询需要contentID,从报文中截取
		resultMsg = resultMsg.replace("0001<<::>>", "");
		if(resultMsg.indexOf("CONTENT_ID") != -1){
			// 当前批次已经存在，执行补扫操作
			 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
				DocumentBuilder db = dbf.newDocumentBuilder();
				 StringReader reader = new StringReader(resultMsg);
				 Document document = db.parse(new InputSource(reader));
				 NodeList fileList = document.getElementsByTagName("BatchIndexBean");
				 // 获取文件名称和路径
				 String contentId = "",maxVersion = "",amount = "";
				 for (int i = 0; i < fileList.getLength(); i++) {
					 Element ele = (Element)fileList.item(i);  
					 contentId = ele.getAttribute("CONTENT_ID");
					 maxVersion = ele.getAttribute("MAX_VERSION");
				 }
				NodeList list = document.getElementsByTagName("AMOUNT");
				for (int i = 0; i < list.getLength(); i++) {
					Element element = (Element) list.item(i);
					amount = element.getElementsByTagName("string").item(0)
							.getFirstChild().getNodeValue();
				}
				resultMsg = clientQuery.queryExample(ip,sorket,groupName,objName, startdate, busiserialno,contentId,userName, passWord);
				resultMsg = resultMsg.replace("0001<<::>>", "");
				
		}
		return resultMsg;
	}

//	public String upload(String ip,int sorket,String groupName,String userName,String passWord,String objName ,String objNamePart,String busiserialno,String startdate,String fileType,String scanUser,String fromPath) throws Exception {
	public String upload(String ip,int sorket,String groupName,String userName,String passWord,String objName ,String objNamePart,String busiserialno,String startdate,String fileType,String scanUser,ArrayList<String> file) throws Exception {

	ClientQuery clientQuery = new ClientQuery();
		
		int SmallWidth=220;
		int SmallHeight=165;
		
	//	ArrayList<String> smallImgs = new ArrayList<String>();
		ArrayList<String> combineImgs = new ArrayList<String>();
		ArrayList<String> bigLens = new ArrayList<String>();
		ArrayList<String> imgTypes = new ArrayList<String>();
		
		for(int i = 0;i<file.size();i++){	
			
	/*	String fileP = fromPath.substring(0,fromPath.lastIndexOf("."));
		String imgType = fromPath.substring(fromPath.lastIndexOf(".")+1);
		String smallImg = fileP+"_small."+imgType;
		String combineImg = fileP+"_combine."+imgType;
		//	 根据实际图片生成压缩图
		SamllImage.saveImageAsJpg(fromPath, smallImg, SmallWidth, SmallHeight);
		//		 将实际图片和压缩图合并
		String bigLen = SamllImage.joinImages(fromPath, smallImg, imgType, combineImg);*/
			
			String fromPath = file.get(i);
			String fileP = fromPath.substring(0,fromPath.lastIndexOf("."));
			String imgType = fromPath.substring(fromPath.lastIndexOf(".")+1);
			String smallImg = fileP+"_small."+imgType;
			String combineImg = fileP+"_combine."+imgType;
			//	 根据实际图片生成压缩图
			SamllImage.saveImageAsJpg(fromPath, smallImg, SmallWidth, SmallHeight);
			//		 将实际图片和压缩图合并
			String bigLen = SamllImage.joinImages(fromPath, smallImg, imgType, combineImg);
			imgTypes.add(imgType);
	//		smallImgs.add(smallImg);
			combineImgs.add(combineImg);
			bigLens.add(bigLen);
		}
		System.out.println("============"+bigLens.size());
		String resultMsg = clientQuery.heightQueryExample(ip,sorket,groupName,objName, startdate, busiserialno, userName, passWord);
	
		// 查询需要contentID,从报文中截取
		String reValue = "";
		if(resultMsg.indexOf("CONTENT_ID") != -1){
			resultMsg = resultMsg.replace("0001<<::>>", "");
			// 当前批次已经存在，执行补扫操作
			 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
				DocumentBuilder db = dbf.newDocumentBuilder();
				 StringReader reader = new StringReader(resultMsg);
				 Document document = db.parse(new InputSource(reader));
				 NodeList fileList = document.getElementsByTagName("BatchIndexBean");
				 // 获取文件名称和路径
				 String contentId = "",amount = "";
			//	 String maxVersion = "";
				 for (int i = 0; i < fileList.getLength(); i++) {
					 Element ele = (Element)fileList.item(i);  
					 contentId = ele.getAttribute("CONTENT_ID");
			//		 maxVersion = ele.getAttribute("MAX_VERSION");
				 }
				NodeList list = document.getElementsByTagName("AMOUNT");
				for (int i = 0; i < list.getLength(); i++) {
					Element element = (Element) list.item(i);
					amount = element.getElementsByTagName("string").item(0)
							.getFirstChild().getNodeValue();
				}
	//			String queryMsg = clientQuery.queryExample(ip,sorket,groupName,objName, startdate, busiserialno,contentId,maxVersion,userName, passWord);
				
				String queryMsg = clientQuery.queryExample(ip,sorket,groupName,objName, startdate, busiserialno,contentId,userName, passWord);
			
				queryMsg = queryMsg.replace("0001<<::>>", "");
				 StringReader reader2 = new StringReader(queryMsg);
				 Document document2 = db.parse(new InputSource(reader2));
				 NodeList nodeList = document2.getElementsByTagName("BUSI_FILE_PAGENUM");
				
				String content = "";
				ArrayList<String> pageNumList = new ArrayList<String>();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Element element = (Element) nodeList.item(i);
					content = element.getElementsByTagName("string").item(0)
							.getFirstChild().getNodeValue();
					if (pageNumList == null) {
						pageNumList.add(content);
					} else {
						if (!pageNumList.contains(content)) {
							pageNumList.add(content);
						}
					}
				}
				// 获取最大的 page_num
				int pageN = 0;
				for (int i = 0; i < pageNumList.size(); i++) {
					int pageNu = Integer.valueOf(pageNumList.get(i));
					if (pageNu > pageN) {
						pageN = pageNu;
					}
				}
	//		reValue = clientQuery.updateExample(ip, sorket,groupName, userName, passWord, objName, objNamePart,contentId, startdate, busiserialno, amount, combineImg, bigLen, imgType , pageN,scanUser,fileType);	
			
				reValue = clientQuery.updateExample(ip, sorket,groupName, userName, passWord, objName, objNamePart,contentId, startdate, busiserialno, amount, combineImgs, bigLens, imgTypes , pageN,scanUser,fileType);	
	
		}else {
			reValue = clientQuery.uploadExam(ip,sorket,groupName,busiserialno, startdate, objName,objNamePart,userName,passWord, fileType, scanUser, combineImgs, bigLens, imgTypes);
		}
		return reValue;
	}
	

}
