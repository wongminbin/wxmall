package com.wong.core.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wong.core.cons.ImageConstants;
import com.wong.core.util.FileUtil;
import com.wong.core.util.PathUtil;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月29日 下午2:42:49
 *
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

	private static final String imgRoot;
	private static final String imgUrl;
	private static final ObjectMapper objectMapper;
	private static final SimpleDateFormat sdf;

	static {
		imgRoot = PathUtil.tomcat() + ImageConstants.IMG_PATH;
		imgUrl = ImageConstants.IMG_URL;
		objectMapper = new ObjectMapper();
		sdf = new SimpleDateFormat("yyyyMMdd");
	}

	@RequestMapping(value = "/webupload", method = RequestMethod.POST)
	public void webupload(MultipartFile imageFile, HttpServletResponse response) throws IOException, FileUploadException {

		PrintWriter writer = initWriter(response);
		// 创建文件夹
		String ymd = createFolder();

		Map<String, Object> msg = new HashMap<String, Object>();

		String fileExt = FileUtil.getSuffix(imageFile.getOriginalFilename());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;

		try {

			imageFile.transferTo(new File(imgRoot + ymd, name));
			msg.put("error", 0);
			msg.put("url", imgUrl + URLEncoder.encode(ymd + "/" + name, "UTF-8"));

		} catch (Exception e) {
			msg.put("error", 1);
		}

		writer.println(objectMapper.writeValueAsString(msg));
	}

	@RequestMapping(value = "/keditor", method = RequestMethod.POST)
	public void kindeditor(HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException {

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		// 最大文件大小
		long maxSize = 1000000;

		PrintWriter writer = initWriter(response);

		if (!ServletFileUpload.isMultipartContent(request)) {
			writer.println(objectMapper.writeValueAsString(getError("请选择文件。")));
			return;

		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			writer.println(objectMapper.writeValueAsString(getError("目录名不正确。")));
			return;
		}

		// 创建文件夹
		String ymd = createFolder();

		Iterator<FileItem> itr = getIterator(request);
		Map<String, Object> msg = new HashMap<String, Object>();

		while (itr.hasNext()) {
			FileItem item = itr.next();
			String fileName = item.getName();

			try {
				BufferedImage image = ImageIO.read(item.getInputStream());
				if (image != null) {
					msg.put("width", image.getWidth() + "");
					msg.put("height", image.getHeight() + "");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (!item.isFormField()) {
				// 检查文件大小
				if (item.getSize() > maxSize) {
					writer.println(objectMapper
							.writeValueAsString(getError("上传文件大小超过限制。")));
					return;
				}
				// 检查扩展名
				String fileExt = FileUtil.getSuffix(fileName);
				if (!extMap.get(dirName).contains(fileExt)) {
					writer.println(objectMapper
							.writeValueAsString(getError("上传文件扩展名是不允许的扩展名。\n只允许"
									+ extMap.get(dirName) + "格式。")));
					return;
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String name = df.format(new Date()) + "_"
						+ new Random().nextInt(1000) + "." + fileExt;
				try {
					item.write(new File(imgRoot + ymd, name));
				} catch (Exception e) {
					writer.println(objectMapper
							.writeValueAsString(getError("上传文件失败。")));
				}

				msg.put("error", 0);
				msg.put("url",
						imgUrl + URLEncoder.encode(ymd + "/" + name, "UTF-8"));
				writer.println(objectMapper.writeValueAsString(msg));
			}
		}
		return;
	}

	private Map<String, Object> getError(String message) {
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 1);
		msg.put("message", message);
		return msg;
	}

	private PrintWriter initWriter(HttpServletResponse response) throws IOException {
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		return response.getWriter();
	}

	private Iterator<FileItem> getIterator(HttpServletRequest request)
			throws FileUploadException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");

		List<FileItem> items = upload.parseRequest(request);
		return items.iterator();
	}

	private String createFolder() {
		String ymd = sdf.format(new Date());
		File dirFile = new File(imgRoot + ymd);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		return ymd;
	}
}
