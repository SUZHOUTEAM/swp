package com.mlsc.yunxin.common;

/**
 * Created by yxl on 2017/5/3.
 */

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class LoggerUtil {
    private static final long serialVersionUID = 5454405123156820674L; // 8745934908040027998L;

    private final Logger logDebug = (Logger) LoggerFactory.getLogger("fw.debug");
    private final Logger logInfo  = (Logger) LoggerFactory.getLogger("fw.info");
    private final Logger logError = (Logger) LoggerFactory.getLogger("fw.error");
    private final Logger logTrace = (Logger) LoggerFactory.getLogger("fw.trace");
    private final Logger logWarn  = (Logger) LoggerFactory.getLogger("fw.warn");


    public void setLogLevel(org.slf4j.event.Level level) {

    }

    public void trace(String msg) {
        logTrace.trace(msg);
    }

    public  void trace(String format, Object arg) {
        logTrace.trace(format, arg);
    }

    public  void trace(String format, Object arg1, Object arg2) {
        logTrace.trace(format, arg1, arg2);
    }

    public  void trace(String format, Object... argArray) {
        logTrace.trace(format, argArray);
    }

    public  void trace(String msg, Throwable t) {
        logTrace.trace(msg, t);
    }

    public  void trace(Marker marker, String msg) {
        logTrace.trace(marker, msg);
    }

    public  void trace(Marker marker, String format, Object arg) {
        logTrace.trace(marker, format, arg);
    }

    public  void trace(Marker marker, String format, Object arg1, Object arg2) {
        logTrace.trace(marker, format, arg1, arg2);
    }

    public  void trace(Marker marker, String format, Object... argArray) {
        logTrace.trace(marker, format, argArray);
    }

    public  void trace(Marker marker, String msg, Throwable t) {
        logTrace.trace(marker, msg, t);
    }

    public  void debug(String msg) {
        logDebug.debug(msg);
    }

    public  void debug(String format, Object arg) {
        logDebug.debug(format, arg);
    }

    public  void debug(String format, Object arg1, Object arg2) {
        logDebug.debug(format, arg1, arg2);
    }

    public  void debug(String format, Object... argArray) {
        logDebug.debug(format, argArray);
    }

    public  void debug(String msg, Throwable t) {
        logDebug.debug(msg, t);
    }

    public  void debug(Marker marker, String msg) {
        logDebug.debug(marker, msg);
    }

    public  void debug(Marker marker, String format, Object arg) {
        logDebug.debug(marker, format, arg);
    }

    public  void debug(Marker marker, String format, Object arg1, Object arg2) {
        logDebug.debug(marker, format, arg1, arg2);
    }

    public  void debug(Marker marker, String format, Object... argArray) {
        logDebug.debug(marker, format, argArray);
    }

    public  void debug(Marker marker, String msg, Throwable t) {
        logDebug.debug(marker, msg, t);
    }

    public  void error(String msg) {
        logError.error(msg);
    }

    public  void error(String format, Object arg) {
        logError.error(format, arg);
    }

    public  void error(String format, Object arg1, Object arg2) {
        logError.error(format, arg1, arg2);
    }

    public  void error(String format, Object... argArray) {
        logError.error(format, argArray);
    }

    public  void error(String msg, Throwable t) {
        logError.error(msg, t);
    }

    public  void error(Marker marker, String msg) {
        logError.error(marker, msg);
    }

    public  void error(Marker marker, String format, Object arg) {
        logError.error(marker, format, arg);
    }

    public  void error(Marker marker, String format, Object arg1, Object arg2) {
        logError.error(marker, format, arg1, arg2);
    }

    public  void error(Marker marker, String format, Object... argArray) {
        logError.error(marker, format, argArray);
    }

    public  void error(Marker marker, String msg, Throwable t) {
        logError.error(marker, msg, t);
    }

    public  void info(String msg) {
        logInfo.info(msg);
    }

    public  void info(String format, Object arg) {
        logInfo.info(format, arg);
    }

    public  void info(String format, Object arg1, Object arg2) {
        logInfo.info(format, arg1, arg2);
    }

    public  void info(String format, Object... argArray) {
        logInfo.info(format, argArray);
    }

    public  void info(String msg, Throwable t) {
        logInfo.info(msg, t);
    }

    public  void info(Marker marker, String msg) {
        logInfo.info(marker, msg);
    }

    public  void info(Marker marker, String format, Object arg) {
        logInfo.info(marker, format, arg);
    }

    public  void info(Marker marker, String format, Object arg1, Object arg2) {
        logInfo.info(marker, format, arg1, arg2);
    }

    public  void info(Marker marker, String format, Object... argArray) {
        logInfo.info(marker, format, argArray);
    }

    public  void info(Marker marker, String msg, Throwable t) {
        logInfo.info(marker, msg, t);
    }


    public  void warn(String msg) {
        logWarn.warn(msg);
    }

    public  void warn(String msg, Throwable t) {
        logWarn.warn(msg, t);
    }

    public  void warn(String format, Object arg) {
        logWarn.warn(format, arg);
    }

    public  void warn(String format, Object arg1, Object arg2) {
        logWarn.warn(format, arg1, arg2);
    }

    public  void warn(String format, Object... argArray) {
        logWarn.warn(format, argArray);
    }

    public  void warn(Marker marker, String msg) {
        logWarn.warn(marker, msg);
    }

    public  void warn(Marker marker, String format, Object arg) {
        logWarn.warn(marker, format, arg);
    }

    public  void warn(Marker marker, String format, Object... argArray) {
        logWarn.warn(marker, format, argArray);
    }

    public  void warn(Marker marker, String format, Object arg1, Object arg2) {
        logWarn.warn(marker, format, arg1, arg2);
    }

    public  void warn(Marker marker, String msg, Throwable t) {
        logWarn.warn(marker, msg, t);
    }

}
