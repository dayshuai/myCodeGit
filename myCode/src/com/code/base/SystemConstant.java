package com.code.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量
 * @author lihui.zhanglh
 *
 */
public class SystemConstant {
	public static class JavaFileConstants {
        /**
         * 请求成功
         */
		public static final String JAVA_SUFFIX = ".java";  
		public static final String XML_SUFFIX = ".xml";  
        /**
         * 请求失败
         */
        public static final String TEMPLATE_PATH  = "\\src\\template\\hundsun";
        public static final String WRITE_PATH  = "\\autoJava\\";
        public static final String BEANPACKAGE  = "com\\day\\bean";
        public static final String MAPPERPACKAGE  = "com\\day\\mapper";
    }
	
		
	public static final Map<String, String> CONVERT_MAP = new HashMap <String, String> ();
	static{
		CONVERT_MAP.put("nchar", "String");
		CONVERT_MAP.put("char", "String");
		CONVERT_MAP.put("nvarchar2", "String");
		CONVERT_MAP.put("longtext", "String");
		CONVERT_MAP.put("tinyint", "Integer");
		CONVERT_MAP.put("long", "Long");
		CONVERT_MAP.put("date", "Date");
		CONVERT_MAP.put("float", "Float");
		CONVERT_MAP.put("character", "String");
		CONVERT_MAP.put("timestamp", "Date");
		CONVERT_MAP.put("short", "Integer");
		CONVERT_MAP.put("byte", "Integer");
		CONVERT_MAP.put("money", "Double");
		CONVERT_MAP.put("boolean", "Boolean");
		CONVERT_MAP.put("datetime", "Date");
		CONVERT_MAP.put("nvarchar", "String");
		CONVERT_MAP.put("varchar", "String");
		CONVERT_MAP.put("text", "String");
		CONVERT_MAP.put("bigint", "Long");
		CONVERT_MAP.put("ntext", "String");
		CONVERT_MAP.put("int", "Integer");
		CONVERT_MAP.put("number", "Integer");
		CONVERT_MAP.put("double", "Double");
		CONVERT_MAP.put("decimal", "BigDecimal");
		CONVERT_MAP.put("true_false", "Boolean");
		CONVERT_MAP.put("bit", "Boolean");
		CONVERT_MAP.put("string", "String");
		CONVERT_MAP.put("smallint", "Integer");
		CONVERT_MAP.put("yes_no", "Boolean");

		
	}	
	
	

    public static class AjaxRequest {
        /**
         * 请求成功
         */
        public static final String SUCCESS = "SUCCESS";
        /**
         * 请求失败
         */
        public static final String FAILED  = "FAILED";
    }

    public static class YesOrNo {
        /**
         * 是
         */
        public static final String YES = "YES";
        /**
         * 否
         */
        public static final String NO  = "NO";
    }
    
    public static class TrueOrFalse {
        /**
         * 是
         */
        public static final String TRUE = "true";
        /**
         * 否
         */
        public static final String FALSE  = "false";
    }
    
    public static class DatabaseType {
    	
        public static final String MySql = "MySql";

        public static final String SqlServer  = "SqlServer";
        
        public static final String Oracle  = "Oracle";
    }
    
    public static class RelationType {
    	
        public static final String None = "NONE";

        public static final String OneToOne  = "OneToOne";
        
        public static final String ManyToOne  = "ManyToOne";
        
        public static final String OneToMany  = "OneToMany";
        
        public static final String ManyToMany  = "ManyToMany";
    }
    
    public static class ConfigType {
    	
        public static final String STATIC = "STATIC";

        public static final String DYNAMIC  = "DYNAMIC";
        
        public static final String COPY  = "COPY";
    }
}
