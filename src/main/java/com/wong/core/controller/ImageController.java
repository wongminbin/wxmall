package com.wong.core.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wong.core.cons.ImageConstants;
import com.wong.core.util.PathUtil;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月29日 下午3:56:33
 *
 */
@Controller
public class ImageController {
	
	private static final String imgRoot;
	
	static {
		imgRoot = PathUtil.tomcat() + ImageConstants.IMG_PATH;
	}

	@RequestMapping("/getimage")
	public void getImage(HttpServletResponse response, String img) throws UnsupportedEncodingException{
		
		if (StringUtils.isNotEmpty(img)) {
			img = URLDecoder.decode(img, "UTF-8");
			File file = new File(imgRoot + img);
			if(file.isFile()){
				try {
					initResponse(response, img);
					
					OutputStream out = response.getOutputStream();
					
					BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
					byte[] b = new byte[1024];
					int len;
					while((len = input.read(b)) != -1){
						out.write(b, 0, len);
					}
					out.close();
					input.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void initResponse(HttpServletResponse response, String fileName){
		response.setCharacterEncoding("UTF-8");
        String contentType = null;
        if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")){
            contentType = "image/jpeg";
        }else if(fileName.endsWith(".png")){
            contentType = "image/png";
        }else if(fileName.endsWith(".dib")){ 
            contentType = "application/x-dib";
        }else if(fileName.endsWith(".gif")){ 
            contentType = "image/gif";
        }else if(fileName.endsWith(".tiff")){ 
            contentType = "image/tiff";
        }else if(fileName.endsWith(".eps")){
            contentType = "application/x-ps";
        }else if(fileName.endsWith(".bmp")){
            contentType = "application/x-bmp";
        }else if(fileName.endsWith(".wpg")){ 
            contentType = "application/x-wpg";
        }else if(fileName.endsWith(".emf")){
            contentType = "application/x-emf";
        }else if(fileName.endsWith(".wmf")){
            contentType = "application/x-wmf";
        } 
        if(contentType != null){
            response.setContentType(contentType);
        }
	}
	
}
