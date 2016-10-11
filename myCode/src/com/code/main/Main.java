package com.code.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code.bean.Database;
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
		Database database = new Database();
		ProduceRun run = new ProduceRun(database, "databaseIp");
		run.start();
	}

	

}
