package com.code.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code.bean.Column;
import com.code.bean.Table;
import com.code.bean.TemplateConfig;
import com.code.produce.FreemarkerUtil;
import com.code.produce.ProduceRun;

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
	/*	Database database = new Database();
		System.out.println(database.getClass().getSimpleName());

		System.out.println(database.getClass().getCanonicalName());

		String[] fieldNames = getFiledName(database);
		for (String string : fieldNames) {
			System.out.println(string);
		}*/
		//beanToTable(new TemplateConfig());
		/*String[] fieldNames = getFiledName(new TemplateConfig());
		for (String fieldName : fieldNames) {
			Column c = new Column();
			//实体属性名
			//c.setColumnName(fieldName);
			System.out.println(fieldName + " " + camelToUnderline(fieldName));
			//数据库表字段名
			//c.setMappingName(camelToUnderline(fieldName));
		}*/
	}

	public static Table beanToTable(Object o) {
		Table table = new Table();
		String tableMappingName = camelToUnderline(ProduceRun.firstToLower(o.getClass().getSimpleName()));
		System.out.println(tableMappingName);
		// 数据库表名
		table.setMappingName(tableMappingName);
		table.setTableName(o.getClass().getSimpleName());
		List <Column> columnList = new ArrayList <Column> ();
		String[] fieldNames = getFiledName(o);
		for (String fieldName : fieldNames) {
			if (!fieldName.equals("serialVersionUID")) {
				Column c = new Column();
				//实体属性名
				c.setColumnName(fieldName);
				//数据库表字段名
				c.setMappingName(camelToUnderline(fieldName));
			}
			
		}
		table.setColumnList(columnList);

		return table;
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
	public static final char UNDERLINE = '_';

	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
