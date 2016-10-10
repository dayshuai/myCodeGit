package com.code.produce;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code.base.SystemConstant;
import com.code.bean.Column;
import com.code.bean.Table;

public class ProduceRun extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(ProduceRun.class);
	private String mapperPackage;
	private String templatePath;
	private String writePath;
	private String projectName;
	private String produceType;
	private String fileName;
	private String beanPackage;
	private Object object;
	private String javaPrimary;

	private Map<String, Object> contentMap = new HashMap<String, Object>();

	public ProduceRun(String templatePath, String writePath, String projectName, String produType, String fileName, Object object, String javaPrimary) {

		this.templatePath = templatePath;
		this.writePath = writePath;
		this.projectName = projectName;
		this.produceType = produType;
		this.fileName = fileName;
		this.object = object;
		this.javaPrimary = javaPrimary;
		initPrepare();
	}

	private void initPrepare() {

	}

	private String getPackagePath(String className) {
		String writeClassPath = (String) this.contentMap.get(firstToLower(className) + "Package");
		if (writeClassPath == null)
			writeClassPath = "";
		else {
			writeClassPath = writeClassPath.replace(".", "/");
		}
		return writeClassPath;
	}

	private String getClassName(String className) {
		if (className.contains(".")) {
			className = className.substring(0, className.lastIndexOf("."));
		}
		return className;
	}

	public void run() {
		produceDAOXml("DaoImpl.ftl", this.object, this.javaPrimary);
		try {
			String cmd = "cmd /c start " + this.writePath.replace(this.projectName, "");
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean writeFile(String packagePath, String templateName, Map<String, Object> contentMap) {
		try {
			if (this.produceType.equals("projectProduce"))
				FreemarkerUtil.WriteFile(this.writePath + "/" + packagePath, this.fileName, this.templatePath,
						templateName, contentMap);
			else {
				FreemarkerUtil.WriteFile(this.writePath + "/" + this.projectName, this.fileName, this.templatePath,
						templateName, contentMap);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void produceDAOXml(String templateName, Object object, String javaPrimary) {
		Table t = beanToTable(object, javaPrimary);
		String fileName = "";
		String writePath = "";
		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
		String writePathParent = desktopDir.getAbsolutePath() + SystemConstant.JavaFileConstants.WRITE_PATH;
		File projectDir = new File("");// 设定为当前文件夹
		String templatePath = projectDir.getAbsolutePath() + SystemConstant.JavaFileConstants.TEMPLATE_PATH;

		if (templateName.equals("DaoImpl.ftl")) {
			writePath = writePathParent + SystemConstant.JavaFileConstants.MAPPERPACKAGE;
			logger.info("table:" + t.getTableName());
			String className = FreemarkerUtil.tableNameToClassName(t.getTableName());
			fileName = className + SystemConstant.JavaFileConstants.XML_SUFFIX;
			Map<String, Object> map = new HashMap<String, Object>();
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

	public static String firstToLower(String str) {
		if ((str == null) || (str.equals(""))) {
			return "";
		}
		return String.valueOf(str.charAt(0)).toLowerCase() + str.substring(1, str.length());
	}
	
	public static Table beanToTable(Object o, String javaPrimary) {
		Table table = new Table();
		String tableMappingName = camelToUnderline(firstToLower(o.getClass().getSimpleName()));
		System.out.println(tableMappingName);
		// 数据库表名
		table.setMappingName(tableMappingName);
		table.setTableName(o.getClass().getSimpleName());
		List <Column> columnList = new ArrayList <Column> ();
		String[] fieldNames = getFiledName(o);
		for (String fieldName : fieldNames) {
			if (!fieldName.equals("serialVersionUID")) {
				
				Column c = new Column();
				if (fieldName.equals(javaPrimary)) {
					c.setIsPrimary("YES");
				}
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
