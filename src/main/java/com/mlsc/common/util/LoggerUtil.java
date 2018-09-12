package com.mlsc.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 日志工具类
 */
public class LoggerUtil {

	public static Logger getLogger(String logfilename) {
		Objects.requireNonNull(logfilename);
		return LoggerFactory.getLogger(logfilename);
	}
	public static Logger getLogger(Class<?> clazz) {

		return LoggerFactory.getLogger(clazz);
	}


}
