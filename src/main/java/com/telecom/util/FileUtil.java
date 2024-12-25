package com.telecom.util;

import com.telecom.bean.FileType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;

public class FileUtil {

	public static void downloadFile(String path, HttpServletResponse response){
        File file = new File(path);
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + path + "\"");
        try {
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();
            byte b[] = new byte[2048];
            int n;
            while ((n = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static String uploadFile(MultipartFile file, String filePath,
			String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file.getBytes());
		out.flush();
		out.close();
		return filePath + fileName;
	}
	
	public static boolean delFile(File file) {
		if (!file.exists()) {
			return false;
		}

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				System.out.println(f.getName());
				delFile(f);
			}
		}
		return file.delete();
	}

	// 截取方式获取文件后缀名
	public static String getFileFormatName(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		return suffix;
	}
	
	// ContentType对照方式获取文件后缀名
	public static String getFileFormatNameByContentType(MultipartFile file) {
		try {
			String contentType = file.getContentType();
			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			MimeType type = allTypes.forName(contentType);
			String ext = type.getExtension();
			return ext;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static FileType getType(String filePath) throws IOException {
		// 获取文件头
		String fileHead = getFileHeader(filePath);

		if (fileHead != null && fileHead.length() > 0) {
			fileHead = fileHead.toUpperCase();
			FileType[] fileTypes = FileType.values();

			for (FileType type : fileTypes) {
				if (fileHead.startsWith(type.getValue())) {
					return type;
				}
			}
		}

		return null;
	}

	public static FileType getType(InputStream inputStream) throws IOException {
		// 获取文件头
		String fileHead = getFileHeader(inputStream);

		if (fileHead != null && fileHead.length() > 0) {
			fileHead = fileHead.toUpperCase();
			System.out.println(fileHead);
			FileType[] fileTypes = FileType.values();

			for (FileType type : fileTypes) {
				if (fileHead.startsWith(type.getValue())) {
					return type;
				}
			}
		}

		return null;
	}

	public static String getFileHeader(String filePath) throws IOException {
		byte[] b = new byte[28];
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(filePath);
			inputStream.read(b, 0, 28);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return bytesToHex(b);
	}

	public static String getFileHeader(InputStream inputStream)
			throws IOException {
		byte[] b = new byte[28];

		try {
			inputStream.read(b, 0, 28);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return bytesToHex(b);
	}

	public static String bytesToHex(byte[] src) {
		StringBuilder builder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		String hv;
		for (int i = 0; i < src.length; i++) {
			// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
			hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}
		return builder.toString();
	}

	/**
	 * 转换文件格式
	 * 
	 * @param mfile
	 * @return
	 */
	public static File convertToFile(MultipartFile mfile) {
		File file = null;
		InputStream ins = null;
		OutputStream os = null;
		try {
			if (mfile.equals("") || mfile.getSize() <= 0) {
				mfile = null;
			} else {
				ins = mfile.getInputStream();
				file = new File(mfile.getOriginalFilename());
			}

			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ins.close();
				os.close();
			} catch (Exception e2) {
			}
		}
		return file;
	}
	
	/**
	 * 判断是否为图片类型文件
	 * @param file
	 * @return
	 */
	public static boolean checkImage(File file){
        try {
            Image image = ImageIO.read(file);
            return image != null;
        } catch(IOException ex) {
            return false;
        }
    }
}