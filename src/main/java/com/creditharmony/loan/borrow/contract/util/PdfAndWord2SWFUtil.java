package com.creditharmony.loan.borrow.contract.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.ConnectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;


//word文件转化成swf文件
public class PdfAndWord2SWFUtil {
	
	@SuppressWarnings("deprecation")
	public static File src2SWF(HttpServletRequest request, HttpServletResponse response,String saveFileName){
		String webPath = request.getRealPath("/");
		String filePath = webPath + "reader\\" + saveFileName;
		String fileName = filePath.substring(0, filePath.lastIndexOf("."));
		// 创建三个文件对象
		File sourceFile = new File(filePath);
		File pdfFile = new File(fileName + ".pdf");
		File swfFile = new File(fileName + ".swf");
		//System.out.println(pdfFile);
		//System.out.println(swfFile);
		// 1、将源文件转化为pdf格式文件
		src2pdf(sourceFile,pdfFile);
		try {
			// 2、将pdf文件转化为swf文件
			File f=pdf2swf(swfFile,pdfFile);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//源文件转化为PDF文件
	public static void src2pdf(File sourceFile,File pdfFile) {
		if (sourceFile.exists()) {
			// 如果不存在，需要转份为PDF文件
			if (!pdfFile.exists()) {
				// 启用OpenOffice提供的转化服务
				OpenOfficeConnection conn = new SocketOpenOfficeConnection(8100);
				// 连接OpenOffice服务器
				try {
					conn.connect();
					// 建立文件转换器对象
					DocumentConverter converter = new OpenOfficeDocumentConverter(
							conn);
					converter.convert(sourceFile, pdfFile);
					// 断开链接
					conn.disconnect();
					//System.out.println("转换成功");
				} catch (ConnectException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("已经存在PDF文件，不需要在转换！！");
			}
		} else {
			System.out.println("文件路径不存在！！！");
		}

	}
	
	//PDF转化为SWF文件
	public static File pdf2swf(File swfFile,File pdfFile) throws Exception {
		if (!swfFile.exists()) {
			if (pdfFile.exists()) {
				String command = "C:\\swftool\\pdf2swf.exe "
						+ pdfFile.getPath() + " -o " + swfFile.getPath()
						+ " -T 9";
				//System.out.println("转换命令:" + command);
				// Java调用外部命令,执行pdf转化为swf
				Runtime r = Runtime.getRuntime();
				Process p = r.exec(command);
				Thread.sleep(1000);
				p.destroy();
				//System.out.println(loadStream(p.getInputStream()));
				System.out.println("swf文件转份成功！！！");
				//System.out.println(swfFile.getPath());
				//return swfFile;
			} else {
				System.out.println("不存在PDF文件");
				
			}
			
		}
		return swfFile;
	}
	
	@SuppressWarnings("unused")
	private static String loadStream(InputStream in) throws Exception {
		int len = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		while ((len = in.read()) != -1) {
			buffer.append((char) len);
		}
		return buffer.toString();
	}
}
