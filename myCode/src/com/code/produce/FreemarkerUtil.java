package com.code.produce;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code.base.ServiceException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreemarkerUtil {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);
	public static final char UNDERLINE='_';
	
	public static void WriteFile(String writePath, String fileName, String templatePath, String templateName,
			Map<String, Object> contentMap) {
		try {
			File f = new File(writePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			if ((fileName.contains(".")) && (fileName.split("\\.").length > 1)) {
				if (fileName.split("\\.")[1].equals("java")) {
					fileName = firstToUpper(fileName);
				}
				if (fileName.split("\\.")[1].equals("jsp")) {
					fileName = firstLetterToLower(fileName);
				}
			}
			String encoding = "utf-8";
			Configuration config = new Configuration();
			File file = new File(templatePath);

			config.setDirectoryForTemplateLoading(file);

			config.setObjectWrapper(new DefaultObjectWrapper());

			Template template = config.getTemplate(templateName, encoding);

			Writer out = new OutputStreamWriter(new FileOutputStream(new File(writePath + "/" + fileName)), encoding);

			template.process(contentMap, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public static String firstToUpper(String str) {
		return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1, str.length());
	}

	public static String firstLetterToLower(String str) {
		return String.valueOf(str.charAt(0)).toLowerCase() + str.substring(1, str.length());
	}
	public static String underlineToCamel(String param){  
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       int len=param.length();  
	       StringBuilder sb=new StringBuilder(len);  
	       for (int i = 0; i < len; i++) {  
	           char c=param.charAt(i);  
	           if (c==UNDERLINE){  
	              if (++i<len){  
	                  sb.append(Character.toUpperCase(param.charAt(i)));  
	              }  
	           }else{  
	               sb.append(c);  
	           }  
	       }  
	       return sb.toString();  
	}
	public static String tableNameToClassName(String param){
		return firstToUpper(underlineToCamel(param));
	}
	
	
}
