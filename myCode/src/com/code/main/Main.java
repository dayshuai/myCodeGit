package com.code.main;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.code.base.SystemConstant;
import com.code.bean.Column;
import com.code.bean.Database;
import com.code.bean.Table;
import com.code.produce.DatebaseUtil;
import com.code.produce.FreemarkerUtil;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);
	
	
	 public static String [] getFileName(String path) {
	        File file = new File(path);
	        String [] fileName = file.list();
	        return fileName;
	 }
	
	public static void main(String[] args) {
		//mygithub testfss
		File projectDir = new File("");
		String templatePath = projectDir.getAbsolutePath() + SystemConstant.JavaFileConstants.TEMPLATE_PATH;
		String [] filesArr = getFileName(templatePath);
		getJavaFile(filesArr);
	}

	public static void getJavaFile(String [] filesArr) {
		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
		String writePathParent = desktopDir.getAbsolutePath() + SystemConstant.JavaFileConstants.WRITE_PATH;
		Database database = new Database("localhost", 3306, "autocode", "root", "");
		String fileName = "";
		String writePath = "";
		try {
			
			DatebaseUtil.databaseOperator(database);
			logger.info("--------------------------------开始读取数据库表-----------------------------------------");
			List<Table> tableList = DatebaseUtil.getTableList(database);
			logger.info("database table size=" + tableList.size());
			logger.info("--------------------------------读取数据库表完成-----------------------------------------");
			File projectDir = new File("");// 设定为当前文件夹
			String templatePath = projectDir.getAbsolutePath() + SystemConstant.JavaFileConstants.TEMPLATE_PATH;
			
			for (String templateName : filesArr) {
				if (templateName.equals("Bean.ftl")) {
					writePath = writePathParent + SystemConstant.JavaFileConstants.BEANPACKAGE;
					for (Table t : tableList) {
						logger.info("table:" + t.getTableName());
						String className = t.getMappingName();
						fileName = className + SystemConstant.JavaFileConstants.JAVA_SUFFIX;
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("className", className);
						map.put("columnList", t.getColumnList());
						
						FreemarkerUtil.WriteFile(writePath, fileName, templatePath, templateName, map);
						logger.info("创建bean:" + className + ".java完成");
					}
					continue;
				}
				if (templateName.equals("DaoImpl.ftl")) {
					writePath = writePathParent + SystemConstant.JavaFileConstants.MAPPERPACKAGE;
					for (Table t : tableList) {
						logger.info("table:" + t.getTableName());
						String className = FreemarkerUtil.tableNameToClassName(t.getTableName());
						fileName = className + SystemConstant.JavaFileConstants.XML_SUFFIX;
						Map <String, Object> map = new HashMap<String, Object>();
						String beanPackage = SystemConstant.JavaFileConstants.BEANPACKAGE;
						beanPackage = beanPackage.replace("\\", ".");
						String mapperPackage = SystemConstant.JavaFileConstants.MAPPERPACKAGE;
						mapperPackage = mapperPackage.replace("\\", ".");
						map.put("mapperPackage", mapperPackage);
						map.put("beanPackage", beanPackage);
						map.put("table", t);
						map.put("columnList", t.getColumnList());
						String databasePrimary = "";
						String javaPrimary = "";
						for (Column c : t.getColumnList()) {
							if (c.getIsPrimary().equals("YES")) {
								databasePrimary = c.getMappingName();
								javaPrimary = c.getColumnName();
							}
						}
						map.put("databasePrimary", databasePrimary);
						map.put("javaPrimary", javaPrimary);
						FreemarkerUtil.WriteFile(writePath, fileName, templatePath, templateName, map);
						logger.info("创建:" + className + "DAO.xml完成");
					}
				}
				
				if (templateName.equals("Controller.ftl")) {
					writePath = writePathParent + "Controller";
					for (Table t : tableList) {
						logger.info("table:" + t.getTableName());
						String className = FreemarkerUtil.tableNameToClassName(t.getTableName());
						fileName = className + "Controller" + SystemConstant.JavaFileConstants.JAVA_SUFFIX;
						Map <String, Object> map = new HashMap<String, Object>();
						String beanPackage = SystemConstant.JavaFileConstants.BEANPACKAGE;
						beanPackage = beanPackage.replace("\\", ".");
						String mapperPackage = SystemConstant.JavaFileConstants.MAPPERPACKAGE;
						mapperPackage = mapperPackage.replace("\\", ".");
						map.put("mapperPackage", mapperPackage);
						map.put("beanPackage", beanPackage);
						map.put("table", t);
						map.put("columnList", t.getColumnList());
						String databasePrimary = "";
						String javaPrimary = "";
						for (Column c : t.getColumnList()) {
							if (c.getIsPrimary().equals("YES")) {
								databasePrimary = c.getMappingName();
								javaPrimary = c.getColumnName();
							}
						}
						map.put("databasePrimary", databasePrimary);
						map.put("javaPrimary", javaPrimary);
						FreemarkerUtil.WriteFile(writePath, fileName, templatePath, templateName, map);
						logger.info("创建:" + className + "Controller.java完成");
					}
					
				}
			}
		} catch (Exception e) {
			logger.info("生成模板文件异常,message=" + e.getMessage(), e);
		}
	}

	
	
}
