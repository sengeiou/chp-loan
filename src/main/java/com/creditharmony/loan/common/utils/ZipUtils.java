package com.creditharmony.loan.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	public static void zipFiles(File[] srcfile, File zipFile) {
		byte[] buf = new byte[1024];
		FileOutputStream fos = null;
		ZipOutputStream out = null;
		FileInputStream in = null;
		try {
			fos = new FileOutputStream(zipFile);
			out = new ZipOutputStream(fos);
			if(srcfile!=null){
				for (int i = 0; i < srcfile.length; i++) {
					in = new FileInputStream(srcfile[i]);
					out.putNextEntry(new ZipEntry(srcfile[i].getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					if (out != null) {
						out.closeEntry();
					}
					if (in != null) {
						in.close();
					}
				}
			}
		} catch (Exception e) {
			zipFile.delete();
			logger.error("压缩出错,合同号:" + zipFile.getName());
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (fos != null) {
					fos.close();
				}
				delFiles(srcfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 
  
  /** 
   * 压缩文件-由于out要在递归调用外,所以封装一个方法用来 
   * 调用ZipFiles(ZipOutputStream out,String path,File... srcFiles) 
   * @param zip 
   * @param path 
   * @param srcFiles 
   * @throws IOException 
   * @author isea533 
   */  
  public static void ZipFiles(File zip,String path,File[] srcFiles){  
	  FileOutputStream fos = null;
      ZipOutputStream out = null;  
      try{
    	  fos = new FileOutputStream(zip);
    	  out = new ZipOutputStream(fos);  
    	  ZipFiles(out,path,srcFiles);  
      }catch(Exception e){
    	  zip.delete();
    	  logger.error("批量压缩出错");
          e.printStackTrace();  
      }finally{
    	  try{
    		  if(out!=null){
        		  out.close(); 
        	  }
        	  if(fos!=null){
        		  fos.close();
        	  }
        	  delFiles(srcFiles);
    	  }catch(IOException e){
    		  e.printStackTrace();
    	  }
      }
  }
  
  /** 
   * 压缩文件-File 
   * @param zipFile  zip文件 
   * @param srcFiles 被压缩源文件 
   * @author isea533 
   */  
	public static void ZipFiles(ZipOutputStream out, String path,
			File[] srcFiles) throws Exception {
		if (path != "" && path != null) {
			path = path.replaceAll("\\*", "/");
			if (!path.endsWith("/")) {
				path += "/";
			}
		}

		byte[] buf = new byte[1024];
		for (int i = 0; i < srcFiles.length; i++) {
			if (srcFiles[i].isDirectory()) {
				File[] files = srcFiles[i].listFiles();
				String srcPath = srcFiles[i].getName();
				srcPath = srcPath.replaceAll("\\*", "/");
				if (!srcPath.endsWith("/")) {
					srcPath += "/";
				}
				out.putNextEntry(new ZipEntry(srcPath));
				ZipFiles(out, srcPath, files);
			} else {
				FileInputStream in = null;
				try {
					in = new FileInputStream(srcFiles[i]);
					out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				} catch (Exception e) {
					logger.error("批量压缩出错");
					e.printStackTrace();
				} finally {
					try {
						if (out != null) {
							out.closeEntry();
						}
						if (in != null) {
							in.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
  
  public static void delFiles(File[] srcFiles){
	  if(srcFiles!=null){
		  for(int i=0;i<srcFiles.length;i++){  
	          if(srcFiles[i].isDirectory()){  
	              File[] files = srcFiles[i].listFiles();   
	              delFiles(files); 
	              srcFiles[i].delete();
	          }  
	          else{  
	              srcFiles[i].delete();  
	          }
		  }
	  }
  }
  
  public static void unZipFiles(File zipfile, String descDir) {
    try
    {
      ZipFile zf = new ZipFile(zipfile);
      for (Enumeration entries = zf.entries(); entries.hasMoreElements(); )
      {
        ZipEntry entry = (ZipEntry)entries.nextElement();
        String zipEntryName = entry.getName();
        InputStream in = zf.getInputStream(entry);

        OutputStream out = new FileOutputStream(descDir + zipEntryName);
        byte[] buf1 = new byte[1024];
        int len;
        while ((len = in.read(buf1)) > 0)
        {
          out.write(buf1, 0, len);
        }

        in.close();
        out.close();
      }
    } catch (Exception e) {
      logger.error("");
    }
  }
}