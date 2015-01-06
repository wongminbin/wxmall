package com.wong.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014-11-18 下午1:54:24
 *
 */
public class FileUtil {
	
	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean exists(String path) {
		return new File(path).exists();
	}
	
	/**
	 * 获取文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}
	
	/**
	 * 获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}


	/**
	 * 创建文件目录
	 * @param dir
	 * @return
	 */
	public static boolean mkDirs(String dir){
		
		if(dir == null || dir.equals(""))
			return false;
		
		File file = new File(dir);
		
		if(!file.exists()){
			
			return file.mkdirs();
			
		}
		return true;
	}
	
	/**
	 * 把一个文件复制到另一个文件
	 * @param src
	 * @param target
	 * @return
	 * @throws IOException
	 * 
	 */
	
	public static boolean copy(InputStream in, OutputStream out) throws IOException{
		return copyFile(in, out);
	}
	
	public static boolean copy(String src, String target) throws IOException{
		
		return copy(new File(src), new File(target));
		
	}
	
	public static boolean copy(File src, File target) throws IOException{
		//判断源文件是否存在
		if(src == null || !src.exists())
			return false;
		// 判断目标文件是否存在
		if(target == null || !target.exists())
			return false;
		
		//源文件是单个文件
		if(src.isFile()){
			
			return copyFile(src, target);
			
		}
		// 源文件是目录
		else {
			
			// 判断目标文件是否是目录，如果不是目录，创建一个目录文件
			if(target.isFile())
				target = target.getParentFile();
			
			return copyDir(src, target);
			
		}
		
	}
	
	private static boolean copyDir(File src, File target) throws IOException {
		// TODO Auto-generated method stub
		File[] files = src.listFiles();
		for (File file : files) {
			if(file.isFile())
				copyFile(file, target);
			else
				copyDir(file, new File(target, file.getName()));
		}
		return false;
	}

	private static boolean copyFile(File src, File target) throws IOException {
		// TODO Auto-generated method stub
		if(target.isDirectory())
			target = new File(target, src.getName());
		
		return copyFile(new FileInputStream(src), new FileOutputStream(target));
		
	}
	
	private static boolean copyFile(InputStream in, OutputStream out) throws IOException{
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(in);
			
			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(out);
			
			// 缓冲数组
			byte[] b = new byte[1024];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
		return true;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否 
	 * @param path
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean delete(String path){
		return delete(new File(path));
	}
	
	public static boolean delete(File file){
		// 判断目录或文件是否存在  
		if(file == null || !file.exists())
			return false;
		if(file.isFile())
			return	file.delete();
		else
			return deleteDir(file);
	}

	private static boolean deleteDir(File file) {
		// TODO Auto-generated method stub
		boolean isDel = true;
		File[] files = file.listFiles();
		for (File f : files) {
			if(f.isFile()){
				isDel = file.delete();
				if (!isDel)
					break;
			}
			else{
				isDel = deleteDir(f);
				if (!isDel)
					break;
			}
		}
		//删除当前目录
		if(isDel){
			return file.delete();
		}
			
		return isDel;
	}
}
