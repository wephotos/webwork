package com.github.wephotos.webwork.config.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.lang.Nullable;

import com.github.wephotos.webwork.logging.LoggerFactory;

/**
 * 数据库工具类
 * 
 * @author TQ
 *
 */
public final class DBUtils {

	// 日志
	private static final Logger log = LoggerFactory.getLogger(DBUtils.class);
	
	private static final Set<Class<?>> primitiveClassSet = new HashSet<>();
	
	static {
		primitiveClassSet.add(Byte.class);
		primitiveClassSet.add(Short.class);
		primitiveClassSet.add(Integer.class);
		primitiveClassSet.add(Long.class);
		primitiveClassSet.add(Float.class);
		primitiveClassSet.add(Double.class);
		primitiveClassSet.add(Boolean.class);
		primitiveClassSet.add(Character.class);
	}
	
	// 配置信息
	private DataSourceProperties properties;

	/**
	 * 获取操作实例对象
	 * @param properties 数据库配置信息
	 * @return 操作实例
	 */
	public static DBUtils getInstance(DataSourceProperties properties) {
		try {
			Class.forName(properties.getDriverClassName());
		} catch (ClassNotFoundException e) {
			log.error("加载数据库驱动 类失败:{}", e.getMessage());
		}
		DBUtils instance = new DBUtils();
		instance.properties = properties;
		return instance;
	}

	/**
	 * 打开数据库连接
	 * 
	 * @throws SQLException
	 */
	private Connection openConnection() throws SQLException {
		return DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
	}

	/**
	 * 执行查询语句
	 * @param sql 查询语句
	 * @param clazz 返回数据类型
	 * @return 数据集合
	 */
	@Nullable
	public <T> List<T> selectSql(String sql, Class<T> clazz) {
		try (Connection connection = openConnection()) {
			List<T> result = new ArrayList<>();
			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = pstmt.executeQuery()) {
					while (resultSet.next()) {
						result.add(resultSetToObject(resultSet, clazz));
					}
				}
			}
			return result;
		} catch (SQLException e) {
			log.error("查询数据库失败:{}", e.getMessage());
			return Collections.emptyList();
		}

	}

	/**
	 * 将结果集转换成Bean
	 * 
	 * @param rs
	 * @param typeClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T resultSetToObject(ResultSet rs, Class<T> typeClass) {
		try {
			// 基本类型返回第一列
			if (typeClass.isPrimitive() || primitiveClassSet.contains(typeClass)) {
				return (T)getColumnValueByClass(rs, 1, typeClass);
			}
			T object;
			Field[] fields = null;
			if (Map.class == typeClass) {
				object = (T) new HashMap<Object, Object>();
			} else {
				object = typeClass.newInstance();
				if (!(object instanceof Map)) {
					fields = typeClass.getDeclaredFields();
				}
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnLabel(i);
				if (object instanceof Map) {
					Object value;
					if ("[B".equals(rsmd.getColumnClassName(i))) {
						value = "byte[]";
					} else {
						value = rs.getObject(i);
					}
					String[] fullName = { rsmd.getTableName(i), rsmd.getColumnName(i), columnName };
					String key = StringUtils.join(fullName, ".");
					((Map<String, Object>) object).put(key, value);
					continue;
				}
				for (Field field : fields) {
					String fieldName = field.getName();
					if (!columnFieldNameEq(columnName, fieldName)) {
						continue;
					}
					PropertyDescriptor descr;
					try {
						descr = new PropertyDescriptor(fieldName, typeClass);
					} catch (IntrospectionException e) {
						log.warn(e.getMessage());
						continue;
					}
					Method writeMethod = descr.getWriteMethod();
					if (writeMethod.getParameterCount() != 1) {
						log.error(writeMethod.getName() + "'s parameter count ne 1.");
						continue;
					}
					Class<?> parameterType = writeMethod.getParameterTypes()[0];
					Object fieldValue = getColumnValueByClass(rs, i, parameterType);
					// log.debug("parameterType ->{}, fieldValue ->{}", parameterType, fieldValue);
					if (writeMethod != null) {
						writeMethod.invoke(object, fieldValue);
						break;
					}
				}

			}
			return object;
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			log.error(e.getMessage());
			return null;
		}

	}

	/**
	 * 判断 列名和字段名是否相同，列名经过下划线转骆驼命名比较也算相同
	 * 
	 * @param column
	 * @param field
	 * @return
	 */
	private static boolean columnFieldNameEq(String column, String field) {
		if (column.equalsIgnoreCase(field)) {
			return true;
		}
		if (underline2CamelCase(column).equalsIgnoreCase(field)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据类型获取ResultSet中的值
	 * 
	 * @param rs
	 * @param columnIndex
	 * @param typeClass
	 * @return
	 * @throws SQLException
	 */
	private static Object getColumnValueByClass(ResultSet rs, int columnIndex, Class<?> typeClass) throws SQLException {
		if (boolean.class == typeClass || Boolean.class == typeClass) {
			return rs.getBoolean(columnIndex);
		} else if (byte.class == typeClass || Byte.class == typeClass) {
			return rs.getByte(columnIndex);
		} else if (short.class == typeClass || Short.class == typeClass) {
			return rs.getShort(columnIndex);
		} else if (int.class == typeClass || Integer.class == typeClass) {
			return rs.getInt(columnIndex);
		} else if (long.class == typeClass || Long.class == typeClass) {
			return rs.getLong(columnIndex);
		} else if (float.class == typeClass || Float.class == typeClass) {
			return rs.getFloat(columnIndex);
		} else if (double.class == typeClass || Double.class == typeClass) {
			return rs.getDouble(columnIndex);
		} else if (BigDecimal.class == typeClass) {
			return rs.getBigDecimal(columnIndex);
		} else if (char.class == typeClass || Character.class == typeClass) {
			String value = rs.getString(columnIndex);
			if (value == null) {
				return '\0';
			}
			if (value.length() > 1) {
				throw new ClassCastException("java.lang.String cannot be cast to java.lang.Character");
			} else {
				if (value.length() == 1) {
					return value.toCharArray()[0];
				} else {
					return '\0';
				}
			}
		} else if (String.class == typeClass) {
			return rs.getString(columnIndex);
		} else if (Date.class == typeClass) {
			java.sql.Date date = rs.getDate(columnIndex);
			return new Date(date.getTime());
		} else if (java.sql.Date.class == typeClass) {
			return rs.getDate(columnIndex);
		} else if (Timestamp.class == typeClass) {
			return rs.getTimestamp(columnIndex);
		} else if (Time.class == typeClass) {
			return rs.getTime(columnIndex);
		}
		return rs.getObject(columnIndex);
	}

	/**
	 * 下划线命名转骆驼命名
	 * 
	 * @param name
	 * @return
	 */
	private static String underline2CamelCase(String name) {
		if (name == null) {
			throw new NullPointerException();
		}
		if (!name.contains("_")) {
			return name;
		}
		String[] words = name.split("_");
		StringBuilder camelCase = new StringBuilder();
		camelCase.append(words[0].toLowerCase());
		for (int i = 1; i < words.length; i++) {
			if (!StringUtils.isBlank(words[i])) {
				String lowerCase = words[i].toLowerCase();
				char first = lowerCase.charAt(0);
				if (first >= 97 && first <= 122) {
					first -= 32;
				}
				camelCase.append(first);
				camelCase.append(lowerCase.substring(1));
			}
		}
		return camelCase.toString();
	}

}
