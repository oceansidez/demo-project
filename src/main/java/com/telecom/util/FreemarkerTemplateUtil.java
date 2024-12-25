package com.telecom.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

@Component
@Resource(name="freemarkerTemplateUtil")
public class FreemarkerTemplateUtil {

	@Autowired
	@Qualifier("writerFileConfig")
	private Configuration configuration;
	
	/**
	 * 生成
	 * @param templateFolder 模版文件夹路径
	 * @param templateFile 模版路径
	 * @param targetPath 生成文件路径
	 * @param map 数据值
	 * @throws Exception 
	 */
	public void generateFile(String templateFile, String targetFolderPath, String targetFile, Map<String, Object> map) throws Exception{
		Template template = configuration.getTemplate(templateFile);
		// 将文件生成到指定目录
		File targetFolder = new File(targetFolderPath);
		if(!targetFolder.exists()){
			targetFolder.mkdirs();
		}
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(targetFolderPath + File.separator + targetFile), "utf-8");
			template.process(map, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭输入流
			writer.close();
		}
	}
	
}
