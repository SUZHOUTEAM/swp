package com.mlsc.common.util;

import com.alibaba.druid.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 字符串帮助类
 * 
 * @author liww
 * 
 */
public class StringUtils {

	/**
	 * 默认字符编码
	 */
	public final static String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 将指定字符串首个字符大写
	 * 
	 * @param str
	 *            待转换字符串
	 * @return
	 */
	public static String firstToUpper(String str) {
		if (StringUtils.isNullOrEmpty(str)) {
			return str;
		} else {
			return str.trim().substring(0, 1).toUpperCase() + str.trim().substring(1);
		}
	}

	/**
	 * 将指定字符串首个字符小写
	 * 
	 * @param str
	 *            待转换字符串
	 * @return
	 */
	public static String firstToLower(String str) {
		if (StringUtils.isNullOrEmpty(str)) {
			return str;
		} else {
			return str.trim().substring(0, 1).toLowerCase() + str.trim().substring(1);
		}
	}

	/**
	 * 将字符串转化为Boolean值。<br />
	 * 字符串为 “1” 或 “true”时返回true。 否则返回false。
	 * 
	 * @param value
	 *            待转换字符串
	 * @return
	 */
	public static boolean toBoolean(String value) {
		if (value != null && value.equals("0"))
			return false;
		if (value != null && value.equals("1"))
			return true;
		return Boolean.valueOf(value);
	}

	/**
	 * 获取字符串字节长度。 <br />
	 * 字母长度为1， 汉字长度为2。
	 * 
	 * @param s
	 *            字符串
	 * @return 字符串长度
	 */
	public static int getByteLength(String s) {
		int byteLength = 0;
		char[] charArray = s.toCharArray();
		for (char c : charArray) {
			if ((int) c > 255)
				byteLength += 2;
			else
				byteLength++;
		}
		return byteLength;
	}

	/**
	 * 将指定字符串转化为Base64编码字符串
	 * 
	 * @param s
	 *            待转换字符串
	 * @return Base64字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String toBase64String(String s) throws UnsupportedEncodingException {
		return toBase64String(s, StringUtils.DEFAULT_ENCODING);
	}

	/**
	 * 将指定字符串转化为Base64编码字符串
	 * 
	 * @param s
	 *            待转换字符串
	 * @param encoding
	 *            字符编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String toBase64String(String s, String encoding) throws UnsupportedEncodingException {
		return Base64.byteArrayToBase64(s.getBytes(encoding));
	}

	/**
	 * 从Base64字符串转换为字符串
	 * 
	 * @param s
	 *            待转换Base64字符串
	 * @param encodingType
	 *            字符编码
	 * @return 转换后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String formBase64String(String s, String encodingType) throws UnsupportedEncodingException {
		byte[] bytes = Base64.base64ToByteArray(s);
		return new String(bytes, encodingType);
	}

	/**
	 * 从Base64字符串转换为字符串
	 * 
	 * @param s
	 *            待转换Base64字符串
	 * @return 转换后的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String formBase64String(String s) throws UnsupportedEncodingException {
		return formBase64String(s, "UTF-8");
	}

	/**
	 * 获取字符串字节流
	 * 
	 * @param s
	 *            字符串
	 * @param encodingType
	 *            字符编码
	 * @return 字节流
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBytes(String s, String encodingType) throws UnsupportedEncodingException {
		return s.getBytes(encodingType);
	}

	/**
	 * 获取字符串字节流
	 * 
	 * @param s
	 *            字符串
	 * @return 字节流
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBytes(String s) throws UnsupportedEncodingException {
		return getBytes(s, "UTF-8");
	}

	/**
	 * 获取字符串Base64字节流
	 * 
	 * @param s
	 *            字符串
	 * @param encodingType
	 *            字符编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBase64Bytes(String s, String encodingType) throws UnsupportedEncodingException {
		return getBytes(toBase64String(s, encodingType), encodingType);
	}

	/**
	 * 获取字符串Base64字节流
	 * 
	 * @param s
	 *            字符串
	 * @return Base64字节流
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBase64Bytes(String s) throws UnsupportedEncodingException {
		return getBase64Bytes(s, "UTF-8");
	}

	/**
	 * 在指定位置插入给定字符串
	 * 
	 * @param s
	 *            源字符串
	 * @param index
	 *            索引位置
	 * @param subStr
	 *            要插入的字符串
	 * @return 新的字符串
	 */
	public static String insert(String s, int index, String subStr) {
		s = s.substring(0, index) + subStr + s.substring(index, s.length());
		return s;
	}

	/**
	 * 从指定位置开始删除指定数目的字符
	 * 
	 * @param s
	 *            源字符串
	 * @param begin
	 *            索引位置
	 * @param end
	 *            删除的字符个数
	 * @return 新的字符串
	 */
	public static String delete(String s, int begin, int end) {
		StringBuilder builder = new StringBuilder(s);
		builder.delete(begin, begin + end);
		return builder.toString();
	}

	/**
	 * 使用指定分隔符分隔字符串
	 * 
	 * @param s
	 *            字符串
	 * @param separator
	 *            分隔符
	 * @return 字符串数组
	 */
	public static String[] split(String s, String separator) {
		String[] result = null;
		if (!StringUtils.isNullOrEmpty(s)) {
			if (!StringUtils.isNullOrEmpty(separator)) {
				try {
					result = s.split(separator);
				} catch (Exception e) {
					result = s.split("\\" + separator);
				}
			} else {
				result = new String[1];
				result[0] = s;
			}
		}
		return result;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isNotNullOrEmpty(String str) {
		return str != null && str.trim().length() != 0;
	}

	/**
	 * sql模糊查询(like)的查询条件进行转换. 实际代码必须加上"ESCAPE '/'".
	 * 
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public static String convertSqlWildcard(String condition) {
		if (condition != null) {

			return condition.replace("/", "//").replace("%", "/%").replace("_", "/_");
		}

		return null;
	}

}
