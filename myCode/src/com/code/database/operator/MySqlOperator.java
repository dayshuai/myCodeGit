package com.code.database.operator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code.base.ServiceException;
import com.code.base.SystemConstant;
import com.code.base.SystemConstant.YesOrNo;
import com.code.bean.Column;
import com.code.bean.Database;
import com.code.bean.Table;
import com.code.produce.FreemarkerUtil;

public class MySqlOperator {

	private static final Logger logger = LoggerFactory.getLogger(MySqlOperator.class);

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("初始化mysql驱动异常", e);
		}
	}
	/**
	 * 连接mysql数据库
	 * @param database
	 * @return
	 */
	public static Connection getConnection(Database database) {
		String url = "jdbc:mysql://" + database.getDatabaseIp() + ":" + database.getDatabasePort() + "/" + database.getDatabaseName();
		try {
			return DriverManager.getConnection(url, database.getDatabaseUser(), database.getDatabasePwd());
		} catch (SQLException e) {
			logger.error("获取mysql连接异常," + e.getMessage(), e);
			throw new ServiceException("获取mysql连接失败");
		}
	}

	/**
	 * 执行sql
	 * @param database
	 * @param sql
	 */
	public static void executeSql(Database database, String sql) {
		if (StringUtils.isBlank(sql)) {
			throw new ServiceException("执行SQL不能为空");
		}
		Connection conn = getConnection(database);
		if (conn == null) {
			throw new ServiceException("执行数据库同步失败,获取mysql连接失败");
		}
		String executeSql=sql;
		try {
			sql=sql.replace("\n", "").replace("\t", "");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			logger.error("执行数据库同步表结构失败,sql=" + executeSql + ",message=" + e.getMessage(), e);
			throw new ServiceException("执行数据库同步表结构失败," + e.getMessage());
		}
	}

	/**
	 * 
	 * @param database
	 * @return
	 */
	public static List<Table> queryTableList(Database database) {
		Connection conn = getConnection(database);
		if (conn == null) {
			return null;
		}
		ArrayList<Table> tableList = queryTableList(conn, database.getDatabaseName());
		for (Table table : tableList) {
			List<Column> columnList = queryColumnList(conn, database.getDatabaseName(), table.getTableName());
			if (columnList == null || columnList.size() == 0) {
				continue;
			}
			table.setColumnList(columnList);
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (tableList != null && tableList.size() > 0) {
			return tableList;
		}
		return Collections.emptyList();
	}

	// 获取数据库表
	public static Table queryTableInfo(Database database,String tableName) {
		Connection conn = getConnection(database);
		if (conn == null) {
			return null;
		}
		Table table=new Table(tableName, tableName);
		List<Column> columnList = queryColumnList(conn, database.getDatabaseName(), table.getTableName());
		if (columnList == null || columnList.size() == 0) {
			return null;
		}
		table.setColumnList(columnList);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}
	
	// 获取到所有的 数据库表
	private static ArrayList<Table> queryTableList(Connection conn, String databaseName) {
		String sql = "SELECT TABLE_NAME,TABLE_COMMENT from information_schema.TABLES where TABLE_SCHEMA='" + databaseName + "'";
		ArrayList<Table> tableList = new ArrayList<Table>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Table table = new Table(rs.getString("TABLE_NAME"), rs.getString("TABLE_COMMENT"));
				String tableName = rs.getString("TABLE_NAME");
				table.setTableName(tableName);
				if (tableName.contains("_")) {
					tableName = FreemarkerUtil.tableNameToClassName(tableName);
				}
				table.setMappingName(tableName);
				table.setTableTitle(rs.getString("TABLE_COMMENT"));
				tableList.add(table);
				
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableList;
	}

	// 查询table下的字段列表
	private static ArrayList<Column> queryColumnList(Connection conn, String databaseName, String tableName) {
		String sql = "SELECT COLUMN_DEFAULT,COLUMN_NAME,EXTRA,IS_NULLABLE,DATA_TYPE,COLUMN_TYPE,COLUMN_KEY,COLUMN_COMMENT FROM information_schema.Columns WHERE TABLE_NAME='" + tableName + "' AND TABLE_SCHEMA='" + databaseName + "'";
		ArrayList<Column> columnList = new ArrayList<Column>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Column column = new Column();
				String columnName = rs.getString("COLUMN_NAME");
				String columnZhName = rs.getString("COLUMN_COMMENT");
				column.setColumnName(rs.getString("COLUMN_NAME"));
			
				if (columnName.contains("_")) {
					columnName = FreemarkerUtil.underlineToCamel(columnName);
				}
				column.setMappingName(columnName);
				column.setColumnZhName(columnZhName);
				column.setColumnType(SystemConstant.CONVERT_MAP.get(rs.getString("DATA_TYPE")));
				if (rs.getString("COLUMN_KEY").equals("PRI")) {
					column.setIsPrimary(YesOrNo.YES);
				} else {
					column.setIsPrimary(YesOrNo.NO);
				}
				columnList.add(column);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			return null;
		}
		return columnList;
	}
	
	public static Map<String, String> convertTypeMap(Connection conn) {
		String sql = "SELECT columnType, convertType from ac_databaseconvert";
		Map <String, String> convertMap = new HashMap <String, String> ();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				String columnType = rs.getString("columnType");
				String convertType = rs.getString("convertType");
				convertMap.put(columnType, convertType);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			return null;
		}
		return convertMap;
	}
	
}
