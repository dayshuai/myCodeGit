package com.code.main;

import java.io.File;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code.base.SystemConstant;
import com.code.produce.FreemarkerUtil;
import com.code.produce.ProduceUtil;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

	public static void main(String[] args) {
		/*
		 * File projectDir = new File(""); String templatePath =
		 * projectDir.getAbsolutePath() +
		 * SystemConstant.JavaFileConstants.TEMPLATE_PATH; String [] filesArr =
		 * ProduceUtil.getFileName(templatePath);
		 * ProduceUtil.getJavaFile(filesArr);
		 */
		String str = "aaBBcC";
		str = camelToUnderline(str);
		System.out.println(str);

	}

	public static String[] getFiledName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}
	
	/**
	 * 驼峰转化
	 */
	public static final char UNDERLINE='_';  
	public static String camelToUnderline(String param){  
	    if (param==null||"".equals(param.trim())){  
	    return "";  
	    }  
	    int len=param.length();  
	    StringBuilder sb=new StringBuilder(len);  
	    for (int i = 0; i < len; i++) {  
	        char c=param.charAt(i);  
	        if (Character.isUpperCase(c)){  
	            sb.append(UNDERLINE);  
	            sb.append(Character.toLowerCase(c));  
	        }else{  
	            sb.append(c);  
	        }  
	    }  
	    return sb.toString();  
	} 

}
