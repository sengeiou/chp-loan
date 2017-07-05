/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsImageUtils.java
 * @Create By 王彬彬
 * @Create In 2016年4月24日 下午3:07:15
 */
package com.creditharmony.loan.common.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

/**
 * 图片工具类
 * 
 * @Class Name ImageUtils
 * @author 王彬彬
 * @Create In 2016年4月24日
 */
public class ImageUtils {
	/**
     * 左右合成图片 2016年4月24日 By 王彬彬
     * 
     * @param filePath1
     *            需要合同的图片1
     * @param filePath2
     *            需要合成的图片2
     * @param outFilePath
     *            合成图片
     */
    public static void xPic(String input1, String input2,
            String out) {// 横向处理图片
        try {
            /*处理第一张图片*/
            File file1= new File(input1);
            BufferedImage imageFirst = ImageIO.read(file1);
            int width = imageFirst.getWidth();// 图片宽度
            int height = imageFirst.getHeight();// 图片高度
            int[] imageArrayFirst = new int[width * height];// 从图片中读取RGB
            imageArrayFirst = imageFirst.getRGB(0, 0, width, height,
                    imageArrayFirst, 0, width);

            /* 1 对第二张图片做相同的处理 */
            File file2= new File(input2);
            BufferedImage imageSecond = ImageIO.read(file2);

            int width2 = imageSecond.getWidth();// 图片宽度
            int height2 = imageSecond.getHeight();// 图片高度

            int[] imageArraySecond = new int[width2 * height2];
            imageArraySecond = imageSecond.getRGB(0, 0, width2, height2,
                    imageArraySecond, 0, width2);

            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width + width2,
                    height > height2 ? height : height2,
                    BufferedImage.TYPE_INT_RGB);
            imageResult.setRGB(0, 0, width, height, imageArrayFirst, 0, width);// 设置左半部分的RGB
            imageResult.setRGB(width, 0, width2, height2, imageArraySecond, 0,
                    width2);// 设置右半部分的RGB
            File file3 = new File(out);
            ImageIO.write(imageResult, "jpg", file3);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * 左右合成图片 2016年4月24日 By 王彬彬
	 * 
	 * @param filePath1
	 *            需要合同的图片1
	 * @param filePath2
	 *            需要合成的图片2
	 * @param outFilePath
	 *            合成图片
	 */
	public static void xPic(InputStream input1, InputStream input2,
			OutputStream out) {// 横向处理图片
		try {
		    /*处理第一张图片*/
			BufferedImage imageFirst = ImageIO.read(input1);
			int width = imageFirst.getWidth();// 图片宽度
			int height = imageFirst.getHeight();// 图片高度
			int[] imageArrayFirst = new int[width * height];// 从图片中读取RGB
			imageArrayFirst = imageFirst.getRGB(0, 0, width, height,
					imageArrayFirst, 0, width);

			/* 1 对第二张图片做相同的处理 */
			BufferedImage imageSecond = ImageIO.read(input2);

			int width2 = imageSecond.getWidth();// 图片宽度
			int height2 = imageSecond.getHeight();// 图片高度

			int[] imageArraySecond = new int[width2 * height2];
			imageArraySecond = imageSecond.getRGB(0, 0, width2, height2,
					imageArraySecond, 0, width2);

			// 生成新图片
			BufferedImage imageResult = new BufferedImage(width + width2,
					height > height2 ? height : height2,
					BufferedImage.TYPE_INT_RGB);
			imageResult.setRGB(0, 0, width, height, imageArrayFirst, 0, width);// 设置左半部分的RGB
			imageResult.setRGB(width, 0, width2, height2, imageArraySecond, 0,
					width2);// 设置右半部分的RGB
			ImageIO.write(imageResult, "jpg", out);// 写图片
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上下合成图片 2016年4月24日 By 王彬彬
	 * 
	 * @param filePath1
	 *            需要合同的图片1
	 * @param filePath2
	 *            需要合成的图片2
	 * @param outFilePath
	 *            合成图片
	 */
	public static void yPic(InputStream input1, InputStream input2,
	        OutputStream out) {// 纵向处理图片
		try {
			/* 1 读取第一张图片 */
			BufferedImage imageFirst = ImageIO.read(input1);
			int width = imageFirst.getWidth();// 图片宽度
			int height = imageFirst.getHeight();// 图片高度
			int[] imageArrayFirst = new int[width * height];// 从图片中读取RGB
			imageArrayFirst = imageFirst.getRGB(0, 0, width, height,
					imageArrayFirst, 0, width);

			/* 1 对第二张图片做相同的处理 */
			BufferedImage imageSecond = ImageIO.read(input2);

			int width2 = imageSecond.getWidth();// 图片宽度
			int height2 = imageSecond.getHeight();// 图片高度

			int[] imageArraySecond = new int[width2 * height2];
			imageArraySecond = imageSecond.getRGB(0, 0, width2, height2,
					imageArraySecond, 0, width2);

			// 生成新图片
			BufferedImage imageResult = new BufferedImage(
					width > width2 ? width : width2, height + height2,
					BufferedImage.TYPE_INT_RGB);

			imageResult.setRGB(0, 0, width, height, imageArrayFirst, 0, width);// 设置上半部分的RGB

			imageResult.setRGB(0, height, width2, height2, imageArraySecond,
					0, width2);// 设置下半部分的RGB
			ImageIO.write(imageResult, "jpg", out);// 写图片
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图片压缩
	 * 2016年6月11日
	 * By 王彬彬
	 * @param srcFilePath 需要压缩的文件
	 * @param descFilePath 压缩后的文件
	 * @return
	 * @throws IOException
	 */
	public static boolean compressPic(String srcFilePath, String descFilePath)
			throws IOException {
		File file = null;
		BufferedImage src = null;
		FileOutputStream out = null;
		ImageWriter imgWrier;
		ImageWriteParam imgWriteParams;

		// 指定写图片的方式为 jpg
		imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
		imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
				null);
		// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
		imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
		ColorModel colorModel = ImageIO.read(new File(srcFilePath))
				.getColorModel();// ColorModel.getRGBdefault();
		// 指定压缩时使用的色彩模式
		imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
				colorModel, colorModel.createCompatibleSampleModel(16, 16)));
		imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
				colorModel, colorModel.createCompatibleSampleModel(16, 16)));
		try {
			if (isBlank(srcFilePath)) {
				return false;
			} else {
				file = new File(srcFilePath);
				long fileLength = file.length();
				float p = 80/(fileLength/1024);
				System.out.println(p);
				// 这里指定压缩的程度，参数qality是取值0~1范围内，
				imgWriteParams.setCompressionQuality(p);
				
				src = ImageIO.read(file);
				out = new FileOutputStream(descFilePath);

				imgWrier.reset();
				// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
				// OutputStream构造
				imgWrier.setOutput(ImageIO.createImageOutputStream(out));
				// 调用write方法，就可以向输入流写图片
				imgWrier.write(null, new IIOImage(src, null, null),
						imgWriteParams);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 判定文件是否为空
	 * 2016年6月11日
	 * By 王彬彬
	 * @param string
	 * @return
	 */
	public static boolean isBlank(String string) {
		if (string == null || string.length() == 0 || string.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
//		xPic("C:\\Users\\Josiah\\Desktop\\01.jpg",
//				"C:\\Users\\Josiah\\Desktop\\01.jpg",
//				"C:\\Users\\Josiah\\Desktop\\0101.jpg");
		try {
			compressPic("D:\\IMG_0045.JPG","D:\\ss.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("OK");
	}
}
