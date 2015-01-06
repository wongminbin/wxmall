package com.wong.core.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月18日 下午12:30:46
 *
 */
public class ImageUtil {
	
	/**
	 * 是否是图片类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isImg(String fileName) {
		Pattern p = Pattern.compile("^(jpeg|jpg|png|gif|bmp|webp)([\\w-./?%&=]*)?$");
		Matcher m = p.matcher(FileUtil.getSuffix(fileName));
		
		return m.find();
	}
	
	 /**
     * 获取图片宽高(不论图片大小，基本恒定时间，在100ms左右)
     * 
     * @param file
     * @param suffix 文件后缀
     * @return
     */
    public static Map<String, Integer> getImageWH(File file, String suffix) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, true);
            map.put("width", reader.getWidth(0));
            map.put("height", reader.getHeight(0));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return map;
    }
	
	/**
	 * 缩略图片
	 * @param filePath	原图地址
	 * @param thumbPath	缩略图地址
	 * @param width		缩放width
	 * @param height	缩放height
	 * @param isforce	是否按比例
	 * @param scale		按百分比缩放
	 * @param quality	图片质量
	 * @param rotate	旋转角度
	 * @return
	 */
	public static String thumb(String filePath, String thumbPath, int width,
			int height, boolean isforce, double scale, double quality,
			double rotate) {
		File img = new File(thumbPath);
		// 如果存在则返回
		if (img.exists()) {
			return img.getPath();
		}
		Builder<File> f = Thumbnails.of(filePath);
		// 只按width缩放
		if (width > 0 && height <= 0) {
			f.width(width).keepAspectRatio(isforce);
		}
		// 只按height缩放
		if (width <= 0 && height > 0) {
			f.height(height).keepAspectRatio(isforce);
		}
		// 按照widthxheight缩放
		if (width > 0 && height > 0) {
			f.size(width, height).keepAspectRatio(isforce);
		}
		//按照比例缩放
		if (scale > 0.0D) {
			f.scale(scale);
		}
		//缩略图质量
		if (quality > 0.0D) {
			f.outputQuality(quality).outputFormat("jpg");
		}
		//旋转角度
		if (rotate > 0.0D) {
			f.rotate(rotate);
		}
		try {
			f.toFile(img);
			return img.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}
}
