package com.telecom.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
  
/** 
 * 压缩算法类 
 * 实现文件压缩，文件夹压缩，以及文件和文件夹的混合压缩 
 * 
 */  
public class ZipUtil {
	
	/** 
     * 完成的结果文件--输出的压缩文件 
     */  
    File targetFile;  
      
    public ZipUtil() {}  
      
    public ZipUtil(File target) {  
        targetFile = target;  
        if (targetFile.exists())  
            targetFile.delete();  
    }  
    
    /**
     * 创建目标目录下的zip并压缩源目录下的文件到其中
     * @param sourcePath 源目录
     * @param zipPath 目标压缩包目录
     */
    public static void createZipFiles(String sourcePath, String zipPath)throws IOException{
    	System.out.println("******************开始压缩********************");
    	File source = new File(sourcePath);  
        File target = new File(zipPath);
        ZipOutputStream out = null;  
        try {  
            out = new ZipOutputStream(new FileOutputStream(target));  
            new ZipUtil().compress(source, out, "");  
            System.out.println("******************压缩完毕："+zipPath+"********************");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null)  
                    out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
    }
  
    /** 
     * 压缩文件 
     *  
     * @param srcfile 
     */  
    public void zipFiles(File srcfile) {  
  
        ZipOutputStream out = null;  
        try {  
            out = new ZipOutputStream(new FileOutputStream(targetFile));  
              
            if(srcfile.isFile()){  
                zipFile(srcfile, out, "");  
            } else{  
                File[] list = srcfile.listFiles();  
                for (int i = 0; i < list.length; i++) {  
                    compress(list[i], out, "");  
                }  
            }  
              
            System.out.println("压缩完毕");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null)  
                    out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
  
    /** 
     * 压缩单个文件 
     *  
     * @param srcfile 
     */  
    public void zipFile(File srcfile, ZipOutputStream out, String basedir) {  
        if (!srcfile.exists())  
            return;  
  
        byte[] buf = new byte[1024];  
        FileInputStream in = null;  
  
        try {  
            int len;  
            in = new FileInputStream(srcfile);  
            out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));  
  
            while ((len = in.read(buf)) > 0) {  
                out.write(buf, 0, len);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null)  
                    out.closeEntry();  
                if (in != null)  
                    in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * 压缩文件夹 
     * @param dir 
     * @param out 
     * @param basedir 
     */  
    public void zipDirectory(File dir, ZipOutputStream out, String basedir) {  
        if (!dir.exists())  
            return;  
  
        File[] files = dir.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            /* 递归 */  
            compress(files[i], out, basedir + dir.getName() + "/");  
        }  
    }  
    
    /** 
     * 压缩文件夹里的文件 
     * 起初不知道是文件还是文件夹--- 统一调用该方法 
     * @param file 
     * @param out 
     * @param basedir 
     */  
    public void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {  
            this.zipDirectory(file, out, basedir);  
        } else {  
            this.zipFile(file, out, basedir);  
        }  
    }

    /**
     * 解压到指定目录
     * @param zipPath 压缩包目录
     * @param descDir 目标解压目录
     */
    public static void unZipFiles(String zipPath, String descDir)throws IOException{
        unZipFiles(new File(zipPath), descDir);
    }
    /**
     * 解压文件到指定目录
     * @param zipFile
     * @param descDir
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile, String descDir)throws IOException {
    	System.out.println("******************开始解压********************");
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
            ;
            //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            //输出文件路径信息
            System.out.println(outPath);

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        zip.close();
        System.out.println("******************解压完毕********************");
    }
    
}
  
