package com.creditharmony.loan.app.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.creditharmony.loan.app.web.Client;

public class AttachUploadServlet extends HttpServlet {

	/**
	 * 文件上传
	 */
	private static final long serialVersionUID = 1L;
	private static int sum = 0;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public String getValue(FileItem fileItem)
			throws UnsupportedEncodingException {
		return new String(fileItem.getString("utf-8"));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String ip = "123.124.20.50";// 信雅达应用服务的ip地址

		// 不变量
		String userName = "admin";// 信雅达数据(不用变)
		String passWord = "111";// 信雅达数据(不用变)
		String groupName = "group"; // 信雅达数据(不用变)
		int sorket = 8023;// 信雅达数据(不用变)

		// 变量
		String objName = "HUIJINSY1";// 信雅达各阶段的业务数据模型 (变量) url中的OBJECT_NAME --贷前
		String objNamePart = "HUIJINBJ1"; // 信雅达各阶段的业务数据模型(变量) --贷前 CRE_PART
		Client client = new Client();
		String busiserialno = "400000317152215"; // 生成字符串从此序列中取url中QUERY_TIME
		String startdate = "20150611";// 业务发生日期(变量) url中QUERY_TIME
		String fileType = "0102";// 文件类型条码(变量) 约定数据
		String scanUser = "zhangsan";
		PrintWriter out = null;
		JSONObject jsonObject = new JSONObject();  
        JSONArray jsonArray = new JSONArray();  
		try {
			// step1,创建一个DiskFileItemFactory对象
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			// step2,创建一个解析器
			ServletFileUpload sfu = new ServletFileUpload(dfif);
			List<FileItem> items = sfu.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				String filePath = null;
				// 要判断是一个普通的表单域还是上传文件域
				if (item.isFormField()) {
					// 是一个普通的表单域
					String name = item.getFieldName();
					filePath  = name + System.currentTimeMillis() + ".jpg";
					File file = new File(filePath);
					System.out.println(filePath);
					item.write(file);
				} else {
					// 获得上传文件的名称
					filePath = item.getName();
					System.out.println(filePath);
					File file = new File(filePath);
					System.out.println(filePath);
					item.write(file);
				}
				// 提交影像
				ArrayList<String> file = new ArrayList<String>();
				file.add(filePath);
				String resMsg = null;
				try {
					resMsg = client.upload(ip, sorket, groupName, userName,
							passWord, objName, objNamePart, busiserialno,
							startdate, fileType, scanUser, file);
					resMsg = "上传UE成功!";
					sum = sum+1;
			        jsonObject.put("msg", resMsg);  
				} catch (Exception e) {
					resMsg = "上传UE失败!";
			        jsonObject.put("msg", resMsg);  
				}
				System.out.println(sum); 
		        jsonArray.add(jsonObject);  
				out = response.getWriter();
			    out.write(jsonArray.toString());
			    out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			 if (out != null) {
			        out.close();
			    }
		}
	}
}
