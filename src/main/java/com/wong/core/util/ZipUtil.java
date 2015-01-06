package com.wong.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014-5-4 下午3:22:50
 * 
 *          利用apache提供的ant.jar,提供对单个文件与目录的压缩，并支持是否需要创建压缩源目录、中文路径
 * @Title：
 * @Description：ZipCompress
 * @Version 1.2
 */
public class ZipUtil {

	private boolean root = true;// 是否创建源目录

	public ZipUtil() {

	}

	/**
	 * 对文件夹或者文件进行压缩
	 * 
	 * @Time 2012-3-9 上午09:32:35 create
	 * @param src  需要压缩的文件的路径
	 * @param archive 压缩后保存的文件路径
	 * @throws IOException
	 */
	
	public void compress(String src, String archive) throws IOException {
		// ----压缩文件：
		compress(new File(src), new FileOutputStream(archive));
		
	}
	
	/**
	 * 
	 * @param src 需要压缩的文件的路径
	 * @param f 压缩后形成的输出流
	 * @throws IOException
	 */
	public void compress(String src, OutputStream f) throws IOException {
		// ----压缩文件：
		compress(new File(src), f);
		
	}
	
	public void compress(File src, OutputStream output) throws IOException {
		
		if (!src.exists() || (src.isDirectory() && src.list().length == 0)) {
			
			throw new FileNotFoundException("File must exist and  ZIP file must have at least one entry.");
		}
		
		// 使用指定校验和创建输出流

		ZipOutputStream zos = new ZipOutputStream(new CheckedOutputStream(output, new CRC32()));
		
		// 支持中文
		zos.setEncoding("GBK");
		// 设置压缩包注释
		// zos.setComment(comment);
		// 启用压缩
		zos.setMethod(ZipOutputStream.DEFLATED);
		// 压缩级别为最强压缩，但时间要花得多一点
		zos.setLevel(Deflater.BEST_COMPRESSION);


		BufferedOutputStream out = new BufferedOutputStream(zos);
		// 开始压缩
		writeRecursive(zos, out, src);
		out.close();
	}

	/**
	 * 使用 org.apache.tools.zip.ZipFile 解压文件，它与 java 类库中的 java.util.zip.ZipFile
	 * 使用方式是一新的，只不过多了设置编码方式的 接口。
	 * 
	 * 注，apache 没有提供 ZipInputStream 类，所以只能使用它提供的ZipFile 来读取压缩文件。
	 * 
	 * @param archive
	 *            压缩包路径
	 * @param decompressDir
	 *            解压路径
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ZipException
	 */
	
	public void decompress(String archive, String decompressDir) throws IOException {
		
		readRecursive(new File(archive), decompressDir);
	}

	/**
	 * 递归解压
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	private void readRecursive(File src, String decompress) throws IOException{

		ZipFile zf = new ZipFile(src, "GBK");// 支持中文

		Enumeration e = zf.getEntries();
		
		while (e.hasMoreElements()) {
			
			ZipEntry ze = (ZipEntry) e.nextElement();
			
			//输出的文件全路径
			String path = decompress + File.separator + ze.getName();
			
			if (ze.isDirectory()) {
				//创建目录
				FileUtil.mkDirs(path);
				
			} else {
				//创建目录
				String dir = path.substring(0, path.lastIndexOf(File.separator));
				FileUtil.mkDirs(dir);
				
				FileUtil.copy(zf.getInputStream(ze), new FileOutputStream(path));
				
			}

		}
		
		zf.close();
	}
	
	/**
	 * 递归压缩
	 * 
	 * 使用 org.apache.tools.zip.ZipOutputStream 类进行压缩，它的好处就是支持中文路径， 而Java类库中的
	 * java.util.zip.ZipOutputStream 压缩中文文件名时压缩包会出现乱码。 使用 apache 中的这个类与 java
	 * 类库中的用法是一新的，只是能设置编码方式了。
	 * 
	 * @param zos
	 * @param bo
	 * @param srcFile
	 * @param prefixDir
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void writeRecursive(ZipOutputStream zos, BufferedOutputStream bo, File src) throws IOException, FileNotFoundException {
		
		ZipEntry zipEntry;
		String entryName = src.getName();
		
		if (src.isDirectory()) {
			if (!root) {
				// 如果是目录，则需要在写目录后面加上 /
				zipEntry = new ZipEntry(entryName + File.separator);
				zos.putNextEntry(zipEntry);
			}
			root = false;
			
			for (File file : src.listFiles()) {
				writeRecursive(zos, bo, file);
			}
			
		} else {

			// 开始写入新的ZIP文件条目并将流定位到条目数据的开始处
			zipEntry = new ZipEntry(entryName);
			zos.putNextEntry(zipEntry);
			
			BufferedInputStream bi = new BufferedInputStream(new FileInputStream(src));
			byte[] buffer = new byte[1024];
			int readCount = 0;

			while ((readCount = bi.read(buffer) )!= -1) {
				bo.write(buffer, 0, readCount);
			}
			// 注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新一把，不
			// 然可能有的内容就会存入到后面条目中去了
			bo.flush();
			// 文件读完后关闭
			bi.close();
		}
		
	}
	
}