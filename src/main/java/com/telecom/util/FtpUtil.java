package com.telecom.util;

import com.telecom.config.WebConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.TimeZone;

/**
 * FTP工具处理类
 *
 */

@Component
@Resource(name="ftpUtil")
public class FtpUtil {
	
	private static Logger logger = Logger.getLogger(FtpUtil.class);
	
	@Autowired	
	private WebConfig webConfig;
	
	/**
	 * 创建FTP服务器连接
	 **/
	public FTPClient ftpLogin() {
		FTPClient ftpClient = new FTPClient();
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		ftpClient.setControlEncoding("GBK");
		ftpClient.configure(ftpClientConfig);
		try {
			
			if (webConfig.getFtpPort() > 0) {
				ftpClient.connect(webConfig.getFtpHost(), webConfig.getFtpPort());
			} else {
				ftpClient.connect(webConfig.getFtpHost());
			}
			// FTP服务器连接回答
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.error("登录FTP服务失败！");
				return null;
			}
			ftpClient.login(webConfig.getFtpUserName(), webConfig.getFtpPassword());
			// 设置传输协议
			ftpClient.enterLocalActiveMode();
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			logger.info("恭喜" + webConfig.getFtpUserName() + "成功登陆FTP服务器");
			ftpClient.setBufferSize(1024 * 2);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setDataTimeout(30 * 1000);
			return ftpClient;
		} catch (Exception e) {
			logger.error(webConfig.getFtpUserName() + "登录FTP服务失败！" + e.getMessage());
			return null;
		}
	}

	/**
	 * 退出关闭服务器连接
	 **/
	public void ftpLogOut(FTPClient ftpClient) {
		if (null != ftpClient && ftpClient.isConnected()) {
			try {
				boolean reuslt = ftpClient.logout();// 退出FTP服务器
				if (reuslt) {
					logger.info("成功退出服务器");
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.warn("退出FTP服务器异常！" + e.getMessage());
			} finally {
				try {
					ftpClient.disconnect();// 关闭FTP服务器的连接
				} catch (IOException e) {
					e.printStackTrace();
					logger.warn("关闭FTP服务器的连接异常！");
				}
			}
		}
	}

	/***
	 * 上传Ftp文件
	 * 
	 * @param localFile
	 *            当地文件
	 * @param romotUpLoadePath上传服务器路径
	 *            - 应该以/结束
	 * */
	public boolean uploadFile(FTPClient ftpClient, File localFile, String romotUpLoadePath, String createDirPath) {
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			if(StringUtils.isNotEmpty(createDirPath)){
				ftpClient.makeDirectory(createDirPath);
			}
			ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			logger.info(localFile.getName() + "开始上传.....");
			success = ftpClient.storeFile(localFile.getName(), inStream);

			if (success == true) {
				logger.info(localFile.getName() + "上传成功");
				return success;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(localFile + "未找到");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	
	/***
	 * 上传Ftp文件
	 * 
	 * @param localFile
	 *            当地文件
	 * @param romotUpLoadePath上传服务器路径
	 *            - 应该以/结束
	 * */
	public boolean uploadFile(FTPClient ftpClient, File localFile, String fileName, String romotUpLoadePath, String createDirPath) {
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			if(StringUtils.isNotEmpty(createDirPath)){
				ftpClient.makeDirectory(createDirPath);
			}
			ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			logger.info(fileName + "开始上传.....");
			success = ftpClient.storeFile(fileName, inStream);

			if (success == true) {
				logger.info(fileName + "上传成功");
				return success;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(localFile + "未找到");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	
	/***
	 * 上传Ftp文件(MultipartFile版本)
	 * 
	 * @param localFile
	 *            当地文件
	 * @param romotUpLoadePath上传服务器路径
	 *            - 应该以/结束
	 * */
	public boolean uploadFile(FTPClient ftpClient, MultipartFile localFile, String fileName, String romotUpLoadePath, String createDirPath) {
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			if(StringUtils.isNotEmpty(createDirPath)){
				ftpClient.makeDirectory(createDirPath);
			}
			ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径
			inStream = new BufferedInputStream(localFile.getInputStream());
			logger.info(fileName + "开始上传.....");
			success = ftpClient.storeFile(fileName, inStream);

			if (success == true) {
				logger.info(fileName + "上传成功");
				return success;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(fileName + "未找到");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/***
	 * 上传文件夹
	 * 
	 * @param localDirectory
	 *            当地文件夹
	 * @param remoteDirectoryPath
	 *            Ftp 服务器路径 以目录"/"结束
	 * */
	public boolean uploadDirectory(FTPClient ftpClient, String localDirectory,
			String remoteDirectoryPath) {
		File src = new File(localDirectory);
		try {
			remoteDirectoryPath = remoteDirectoryPath + src.getName() + "/";
			ftpClient.makeDirectory(remoteDirectoryPath);
			// ftpClient.listDirectories();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(remoteDirectoryPath + "目录创建失败");
			return false;
		}
		File[] allFile = src.listFiles();
		// 遍历所有不是文件夹的file，直接执行上传
		for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
			if (!allFile[currentFile].isDirectory()) {
				String srcName = allFile[currentFile].getPath().toString();
				// 执行本次上传
				if (!uploadFile(ftpClient, new File(srcName), remoteDirectoryPath, null)) {
					return false;
				}
			}
		}
		// 遍历所有是文件夹的file，递归执行上传
		for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
			if (allFile[currentFile].isDirectory()) {
				// 递归
				if (!uploadDirectory(ftpClient, allFile[currentFile].getPath().toString(),
						remoteDirectoryPath)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/***
	 * 下载文件
	 * 
	 * @param remoteFileName
	 *            待下载文件名称
	 * @param localDires
	 *            下载到当地那个路径下
	 * @param remoteDownLoadPath
	 *            remoteFileName所在的路径
	 * */

	public boolean downloadFile(FTPClient ftpClient, String remoteFileName, String localDires,
			String remoteDownLoadPath) {
		String strFilePath = localDires + remoteFileName;
		BufferedOutputStream outStream = null;
		boolean flag = false;
		try {
			ftpClient.changeWorkingDirectory(remoteDownLoadPath);
			outStream = new BufferedOutputStream(new FileOutputStream(
					strFilePath));
			logger.info(remoteFileName + "开始下载....");
			flag = ftpClient.retrieveFile(remoteFileName, outStream);
			if (flag == true) {
				logger.info(remoteFileName + "成功下载到" + strFilePath);
				return flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(remoteFileName + "下载失败");
		} finally {
			if (null != outStream) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (flag == false) {
			logger.error(remoteFileName + "下载失败!!!");
		}
		return flag;
	}


	/***
	 * 下载文件夹
	 * 
	 * @param localDirectoryPath本地地址
	 * @param remoteDirectory
	 *            远程文件夹
	 * */
	public boolean downLoadDirectory(FTPClient ftpClient, String localDirectoryPath,
			String remoteDirectory) {
		try {
			String fileName = new File(remoteDirectory).getName();
			localDirectoryPath = localDirectoryPath + fileName + "//";
			new File(localDirectoryPath).mkdirs();
			FTPFile[] allFile = ftpClient.listFiles(remoteDirectory);
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					downloadFile(ftpClient, allFile[currentFile].getName(),
							localDirectoryPath, remoteDirectory);
				}
			}
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (allFile[currentFile].isDirectory()) {
					String strremoteDirectoryPath = remoteDirectory + "/"
							+ allFile[currentFile].getName();
					downLoadDirectory(ftpClient, localDirectoryPath,
							strremoteDirectoryPath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("下载文件夹失败");
			return false;
		}
		return true;
	}

}