package com.creditharmony.loan.app.web;


import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
public class SamllImage {
	
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		// targetW，targetH分别表示目标长和宽    
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		//这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放    
		//则将下面的if else语句注释即可    
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { //handmade    
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		//smoother than exlax:    
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight) throws Exception {
		BufferedImage srcImage;
		// String ex = fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());    
		String imgType = "JPEG";
		if (fromFileStr.toLowerCase().endsWith(".jpg")) {
			imgType = "jpg";
		}
		// System.out.println(ex);    
		File saveFile = new File(saveToFileStr);
		File fromFile = new File(fromFileStr);
		if(fromFile.exists()){
			srcImage = ImageIO.read(fromFile);
			if (width > 0 || hight > 0) {
				srcImage = resize(srcImage, width, hight);
			}
				ImageIO.write(srcImage, imgType, saveFile);
		}

	}
	
	public static String joinImages(String firstSrcImagePath,String secondSrcImagePath, String imageFormat, String toPath) throws Exception {
		File fileOne = new File(firstSrcImagePath);
		InputStream in = new FileInputStream(fileOne);
		
		File fileTwo = new File(secondSrcImagePath);
		InputStream in2 = new FileInputStream(fileTwo);
		
		OutputStream fos=new FileOutputStream(toPath);
		
		long len = 0; 
		byte buf[]=new byte[512];
		int n=0; //记录事迹读取到的字节数
		//循环读取
		while((n=in.read(buf))!=-1)
		{
			len = len + buf.length;
			//输出到指定文件夹
			fos.write(buf);
		}
		String Biglen=String.valueOf(len);
//		System.out.println("大图长度:"+len);
		buf = new byte[1024];
		n=0; //记录事迹读取到的字节数
		len = 0;
		//循环读取
		while((n=in2.read(buf))!=-1)
		{
			len = len + buf.length;
			//输出到指定文件夹
			fos.write(buf);
		}
//		System.out.println("小图长度:"+len);
		in.close();
		in2.close();
		fos.close();
		
		return Biglen;
	}


}
