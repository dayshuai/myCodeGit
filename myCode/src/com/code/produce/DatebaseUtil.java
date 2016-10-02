package com.code.produce;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code.base.ServiceException;
import com.code.bean.Database;
import com.code.bean.Table;
import com.code.database.operator.MySqlOperator;

public class DatebaseUtil {

	private static final Logger logger = LoggerFactory.getLogger(DatebaseUtil.class);
	
	
	/**
	 * 验证数据库信息
	 * @param database
	 * @throws Exception
	 */
	public static void databaseOperator(Database database) throws Exception {
		
		boolean bool = checkConnection(database);
		if (!bool) {
			logger.info("database信息有误,请仔细检查...");
			return;
		}
		logger.info("验证数据库信息通过...");
	}

	
	/**
	 * 获得数据库中表列表
	 * @param database
	 * @return
	 */
	public static List<Table> getTableList(Database database) {
		try {
			return MySqlOperator.queryTableList(database);
		} catch (Exception e) {
			logger.error("获取数据数据库表异常," + e.getMessage(), e);
			throw new ServiceException("获取数据数据库表异常," + e.getMessage());
		}
	}

	/**
	 * 检查数据库连接
	 * @param database
	 * @return
	 */
	public static boolean checkConnection(Database database) {
		if (database == null) {
			throw new ServiceException("数据库信息不能为空");
		}
		try {
			MySqlOperator.getConnection(database);
			return true;
		} catch (Exception e) {
			logger.error("验证数据信息失败," + e.getMessage(), e);
			return false;
		}
	}
}
