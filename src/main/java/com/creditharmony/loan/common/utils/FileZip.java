package com.creditharmony.loan.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;




import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.creditharmony.dm.file.exception.DmIOException;

/**
 * 压缩工具类
 * @Class Name FileZip
 * @author 韩龙
 * @Create In 2016年2月20日
 */
public class FileZip {
	
	private static final int BUFFER = 2048;

	/**
	 * 压缩文件
	 * 2016年2月20日
	 * By 韩龙
	 * @param srcfile 文件名数组
	 * @param zipfile 压缩后文件
	 */
	public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {
		byte[] buf = new byte[BUFFER];
		try {
			java.util.zip.ZipOutputStream out = new java.util.zip.ZipOutputStream(new FileOutputStream(zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				FileInputStream in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new java.util.zip.ZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
				srcfile[i].delete();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * 打包文件集
     * 2015年12月28日
     * By 韩龙
     * @param os zip文件流
     * @param map InputStream的集合
     */
    public static void zipFiles(OutputStream os, Map<String, InputStream> map) {
        BufferedOutputStream bufferedOutputStream = null;
        ZipOutputStream out = null;
        try {
            BufferedInputStream origin = null;
            bufferedOutputStream = new BufferedOutputStream(os);
            out = new ZipOutputStream(bufferedOutputStream);
            out.setEncoding("gbk");
            byte data[] = new byte[BUFFER];

            for (String fileName : map.keySet()) {
                origin = new BufferedInputStream(map.get(fileName), BUFFER);
                ZipEntry entry = new ZipEntry(fileName);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                if (map.get(fileName) != null)
                    map.get(fileName).close();
                origin.close();
            }
            os.flush();
            out.close();
            bufferedOutputStream.close();
            os.close();
        } catch (Exception e) {
            try {
                if (bufferedOutputStream != null)
                    bufferedOutputStream.close();
                if (bufferedOutputStream != null)
                    bufferedOutputStream.close();
                if (out != null)
                    out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new DmIOException("下载zip文件异常", e);
        }
    }
}
