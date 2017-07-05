package com.creditharmony.loan.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;
/**
 * 汇金工具类
 * @author ws
 *
 */
public class LoanFileUtils {

	 /**
	  * 将流转化为 file
	  * 2015年12月26日
	  * by wengsi
	  * @param ins
	  * @param file
	  */
	public static void inputstreamtofile(InputStream ins,File file){
		try{
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		}catch(Exception e){
			
		}
	}
	
	
	public static File multipartFile2File(MultipartFile files){
		File file = new File(files.getOriginalFilename());
		try {
			inputstreamtofile(files.getInputStream(),file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
