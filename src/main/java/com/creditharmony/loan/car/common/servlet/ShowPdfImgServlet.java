package com.creditharmony.loan.car.common.servlet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.creditharmony.dm.service.DmService;

/**
 * 生成pdf图片
 * @version 2014-7-27
 */
@SuppressWarnings("serial")
public class ShowPdfImgServlet extends HttpServlet {
	
	
	public ShowPdfImgServlet() {
		super();
	}
	
	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			long a = System.currentTimeMillis();
			String docId = request.getParameter("docId");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			
			DmService dmService = DmService.getInstance();
			InputStream in = dmService.downloadDocument(docId);
			PDDocument pd = PDDocument.load(in);
			PDFRenderer pdfRenderer = new PDFRenderer(pd);
            BufferedImage combined = null;
		    for(int page = 0; page < pd.getNumberOfPages(); ++page){
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 146, ImageType.RGB);
                if (page == 0) {
                    combined = bim;
                } else {
                    combined = merge(combined, bim);
                }
            }
            pd.close();
            OutputStream out = response.getOutputStream();
    		ImageIO.write(combined, "JPEG", out);
    		long b = System.currentTimeMillis();
    		System.out.print("执行时间："+(b-a));
    		out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static BufferedImage merge(BufferedImage image1, BufferedImage image2) {
        BufferedImage combined = new BufferedImage(
                image1.getWidth(),
                image1.getHeight() + image2.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics g = combined.getGraphics();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, 0, image1.getHeight(), null);
        g.dispose();
        return combined;
    }
}
