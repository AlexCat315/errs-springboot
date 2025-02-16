package com.x.backend.util;

import com.x.backend.exception.ForbiddenException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeUtils {
    /**
     * 给定一个时间戳，返回该时间戳对应的日期字符串
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 返回的日期字符串格式为 yyyy-MM-dd HH:mm:ss
     * </p>
     *
     * @param timestamp 时间戳
     * @return 日期字符串
     */
    public String timestamp2Date(long timestamp) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
    }

    public Date string2Date(String dateStr) {
        try {
            if (dateStr == null) {
                throw new ForbiddenException();
            }
            if (dateStr.length() == 10) {
                // yyyy-MM-dd
                return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            }
            return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (Exception e) {
            throw new ForbiddenException("Invalid date format, should be yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 给定一个日期字符串，返回该日期字符串对应的时间戳
     * <p>
     * 1. 日期字符串格式必须为 yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
     * 2. 如果日期字符串格式不正确，则抛出 ForbiddenException
     * 3. 如果日期字符串格式正确，则返回对应的时间戳
     * 4. 注意：时间戳的单位是毫秒
     * </p>
     *
     * @param dateStr 日期字符串
     * @return 时间戳
     */
    public long date2Timestamp(String dateStr) {
        try {
            if (dateStr == null) {
                throw new ForbiddenException();
            }
            if (dateStr.length() == 10) {
                // yyyy-MM-dd
                java.util.Date date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                return date.getTime();
            }
            java.util.Date date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
            return date.getTime();
        } catch (Exception e) {
            throw new ForbiddenException("Invalid date format, should be yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的毫秒数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @return 时间戳距离当前时间的毫秒数
     */
    public long timestamp2Millis(long timestamp) {
        long currentTimeMillis = System.currentTimeMillis();
        if (timestamp < currentTimeMillis) {
            throw new ForbiddenException("Invalid timestamp, should be greater than current timestamp");
        }
        return timestamp - currentTimeMillis;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的秒数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @return 时间戳距离当前时间的秒数
     */
    public long timestamp2Seconds(long timestamp) {
        long millis = timestamp2Millis(timestamp);
        return millis / 1000;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的分钟数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @return 时间戳距离当前时间的分钟数
     */
    public long timestamp2Minutes(long timestamp) {
        long seconds = timestamp2Seconds(timestamp);
        return seconds / 60;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的小时数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @return 时间戳距离当前时间的小时数
     */
    public long timestamp2Hours(long timestamp) {
        long minutes = timestamp2Minutes(timestamp);
        return minutes / 60;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的天数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @return 时间戳距离当前时间的天数
     */
    public long timestamp2Days(long timestamp) {
        long hours = timestamp2Hours(timestamp);
        return hours / 24;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的周数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @return 时间戳距离当前时间的周数
     */
    public long timestamp2Weeks(long timestamp) {
        long days = timestamp2Days(timestamp);
        return days / 7;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的月数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @return 时间戳距离当前时间的月数
     */
    public long timestamp2Months(long timestamp) {
        long days = timestamp2Days(timestamp);
        return days / 30;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的年数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @return 时间戳距离当前时间的年数
     */
    public long timestamp2Years(long timestamp) {
        long days = timestamp2Days(timestamp);
        return days / 365;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的指定单位的数值
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @param unit      时间单位，支持以下单位：
     *                  "millis"：毫秒
     *                  "seconds"：秒
     *                  "minutes"：分钟
     *                  "hours"：小时
     *                  "days"：天
     *                  "weeks"：周
     *                  "months"：月
     *                  "years"：年
     * @return 时间戳距离当前时间的指定单位的数值
     */
    public long timestamp2Unit(long timestamp, String unit) {
        if (unit.equals("millis")) {
            return timestamp2Millis(timestamp);
        } else if (unit.equals("seconds")) {
            return timestamp2Seconds(timestamp);
        } else if (unit.equals("minutes")) {
            return timestamp2Minutes(timestamp);
        } else if (unit.equals("hours")) {
            return timestamp2Hours(timestamp);
        } else if (unit.equals("days")) {
            return timestamp2Days(timestamp);
        } else if (unit.equals("weeks")) {
            return timestamp2Weeks(timestamp);
        } else if (unit.equals("months")) {
            return timestamp2Months(timestamp);
        } else if (unit.equals("years")) {
            return timestamp2Years(timestamp);
        } else {
            throw new ForbiddenException("Invalid unit, should be millis, seconds, minutes, hours, days, weeks, months, years");
        }
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的指定单位的数值字符串
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @param unit      时间单位，支持以下单位：
     *                  "millis"：毫秒
     *                  "seconds"：秒
     *                  "minutes"：分钟
     *                  "hours"：小时
     *                  "days"：天
     *                  "weeks"：周
     *                  "months"：月
     *                  "years"：年
     * @return 时间戳距离当前时间的指定单位的数值字符串
     */
    public String timestamp2UnitStr(long timestamp, String unit) {
        long value = timestamp2Unit(timestamp, unit);
        if (unit.equals("millis")) {
            return value + " millis";
        } else if (unit.equals("seconds")) {
            return value + " seconds";
        } else if (unit.equals("minutes")) {
            return value + " minutes";
        } else if (unit.equals("hours")) {
            return value + " hours";
        } else if (unit.equals("days")) {
            return value + " days";
        } else if (unit.equals("weeks")) {
            return value + " weeks";
        } else if (unit.equals("months")) {
            return value + " months";
        } else if (unit.equals("years")) {
            return value + " years";
        } else {
            throw new ForbiddenException("Invalid unit, should be millis, seconds, minutes, hours, days, weeks, months, years");
        }
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的指定单位的数值字符串，精确到指定位数
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @param unit      时间单位，支持以下单位：
     *                  "millis"：毫秒
     *                  "seconds"：秒
     *                  "minutes"：分钟
     *                  "hours"：小时
     *                  "days"：天
     *                  "weeks"：周
     *                  "months"：月
     *                  "years"：年
     * @param precision 精确度，支持以下精确度：
     *                  "full"：完整精度，例如：1000 millis
     *                  "short"：简化精度，例如：1000 millis -> 1s
     * @return 时间戳距离当前时间的指定单位的数值字符串，精确到指定位数
     */
    public String timestamp2UnitStr(long timestamp, String unit, String precision) {
        long value = timestamp2Unit(timestamp, unit);
        if (unit.equals("millis")) {
            if (precision.equals("full")) {
                return value + " millis";
            } else if (precision.equals("short")) {
                return value / 1000 + "s";
            } else {
                throw new ForbiddenException("Invalid precision, should be full or short");
            }
        } else if (unit.equals("seconds")) {
            if (precision.equals("full")) {
                return value + " seconds";
            } else if (precision.equals("short")) {
                return value / 60 + "m";
            } else {
                throw new ForbiddenException("Invalid precision, should be full or short");
            }
        } else if (unit.equals("minutes")) {
            if (precision.equals("full")) {
                return value + " minutes";
            } else if (precision.equals("short")) {
                return value / 60 + "h";
            } else {
                throw new ForbiddenException("Invalid precision, should be full or short");
            }
        } else if (unit.equals("hours")) {
            if (precision.equals("full")) {
                return value + " hours";
            } else if (precision.equals("short")) {
                return value / 24 + "d";
            } else {
                throw new ForbiddenException("Invalid precision, should be full or short");
            }
        } else if (unit.equals("days")) {
            if (precision.equals("full")) {
                return value + " days";
            } else if (precision.equals("short")) {
                return value / 7 + "w";
            } else {
                throw new ForbiddenException("Invalid precision, should be full or short");
            }
        } else if (unit.equals("weeks")) {
            if (precision.equals("full")) {
                return value + " weeks";
            } else if (precision.equals("short")) {
                return value / 4 + "m";
            } else {
                throw new ForbiddenException("Invalid precision, should be full or short");
            }
        } else if (unit.equals("months")) {
            if (precision.equals("full")) {
                return value + " months";
            } else if (precision.equals("short")) {
                return value / 12 + "y";
            } else {
                throw new ForbiddenException("Invalid precision, should be full or short");
            }
        } else if (unit.equals("years")) {
            if (precision.equals("full")) {
                return value + " years";
            } else if (precision.equals("short")) {
                return value + "y";
            } else {
                throw new ForbiddenException("Invalid precision, should be full or short");
            }
        } else {
            throw new ForbiddenException("Invalid unit, should be millis, seconds, minutes, hours, days, weeks, months, years");
        }
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @param unit      时间单位，支持以下单位：
     *                  "millis"：毫秒
     *                  "seconds"：秒
     *                  "minutes"：分钟
     *                  "hours"：小时
     *                  "days"：天
     *                  "weeks"：周
     *                  "months"：月
     *                  "years"：年
     * @param precision 精确度，支持以下精确度：
     *                  "full"：完整精度，例如：1000 millis
     *                  "short"：简化精度，例如：1000 millis -> 1s
     * @param unitStr   单位字符串，例如："ms"
     * @return 时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位
     */
    public String timestamp2UnitStrWithUnit(long timestamp, String unit, String precision, String unitStr) {
        String valueStr = timestamp2UnitStr(timestamp, unit, precision);
        return valueStr + " " + unitStr;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位，并格式化为指定格式
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @param unit      时间单位，支持以下单位：
     *                  "millis"：毫秒
     *                  "seconds"：秒
     *                  "minutes"：分钟
     *                  "hours"：小时
     *                  "days"：天
     *                  "weeks"：周
     *                  "months"：月
     *                  "years"：年
     * @param precision 精确度，支持以下精确度：
     *                  "full"：完整精度，例如：1000 millis
     *                  "short"：简化精度，例如：1000 millis -> 1s
     * @param unitStr   单位字符串，例如："ms"
     * @param format    格式化字符串，例如："yyyy-MM-dd HH:mm:ss"
     * @return 时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位，并格式化为指定格式
     */
    public String timestamp2UnitStrWithUnit(long timestamp, String unit, String precision, String unitStr, String format) {
        String valueStr = timestamp2UnitStr(timestamp, unit, precision);
        return new java.text.SimpleDateFormat(format).format(new java.util.Date(timestamp)) + " " + valueStr + " " + unitStr;
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位，并格式化为指定格式，并对齐到指定宽度
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @param unit      时间单位，支持以下单位：
     *                  "millis"：毫秒
     *                  "seconds"：秒
     *                  "minutes"：分钟
     *                  "hours"：小时
     *                  "days"：天
     *                  "weeks"：周
     *                  "months"：月
     *                  "years"：年
     * @param precision 精确度，支持以下精确度：
     *                  "full"：完整精度，例如：1000 millis
     *                  "short"：简化精度，例如：1000 millis -> 1s
     * @param unitStr   单位字符串，例如："ms"
     * @param format    格式化字符串，例如："yyyy-MM-dd HH:mm:ss"
     * @param width     宽度，例如：10
     * @return 时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位，并格式化为指定格式，并对齐到指定宽度
     */
    public String timestamp2UnitStrWithUnit(long timestamp, String unit, String precision, String unitStr, String format, int width) {
        String valueStr = timestamp2UnitStr(timestamp, unit, precision);
        String formatStr = "%" + width + "s";
        return String.format(formatStr, new java.text.SimpleDateFormat(format).format(new java.util.Date(timestamp)) + " " + valueStr + " " + unitStr);
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位，并格式化为指定格式，并对齐到指定宽度，并添加颜色
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @param unit      时间单位，支持以下单位：
     *                  "millis"：毫秒
     *                  "seconds"：秒
     *                  "minutes"：分钟
     *                  "hours"：小时
     *                  "days"：天
     *                  "weeks"：周
     *                  "months"：月
     *                  "years"：年
     * @param precision 精确度，支持以下精确度：
     *                  "full"：完整精度，例如：1000 millis
     *                  "short"：简化精度，例如：1000 millis -> 1s
     * @param unitStr   单位字符串，例如："ms"
     * @param format    格式化字符串，例如："yyyy-MM-dd HH:mm:ss"
     * @param width     宽度，例如：10
     * @param color     颜色，例如："red"
     * @return 时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位，并格式化为指定格式，并对齐到指定宽度，并添加颜色
     */
    public String timestamp2UnitStrWithUnit(long timestamp, String unit, String precision, String unitStr, String format, int width, String color) {
        String valueStr = timestamp2UnitStr(timestamp, unit, precision);
        String formatStr = "%" + width + "s";
        return String.format(formatStr, "<font color=\"" + color + "\">" + new java.text.SimpleDateFormat(format).format(new java.util.Date(timestamp)) + " " + valueStr + " " + unitStr + "</font>");
    }

    /**
     * 给定一个时间戳，返回该时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位，并格式化为指定格式，并对齐到指定宽度，并添加颜色，并添加前缀
     * <p>
     * 1. 时间戳的单位是毫秒
     * 2. 如果时间戳小于当前时间戳，则抛出 ForbiddenException
     * </p>
     *
     * @param timestamp 时间戳
     * @param unit      时间单位，支持以下单位：
     *                  "millis"：毫秒
     *                  "seconds"：秒
     *                  "minutes"：分钟
     *                  "hours"：小时
     *                  "days"：天
     *                  "weeks"：周
     *                  "months"：月
     *                  "years"：年
     * @param precision 精确度，支持以下精确度：
     *                  "full"：完整精度，例如：1000 millis
     *                  "short"：简化精度，例如：1000 millis -> 1s
     * @param unitStr   单位字符串，例如："ms"
     * @param format    格式化字符串，例如："yyyy-MM-dd HH:mm:ss"
     * @param width     宽度，例如：10
     * @param color     颜色，例如："red"
     * @param prefix    前缀，例如："Last updated: "
     * @return 时间戳距离当前时间的指定单位的数值字符串，精确到指定位数，并附带单位，并格式化为指定格式，并对齐到指定宽度，并添加颜色，并添加前缀
     */
    public String timestamp2UnitStrWithUnit(long timestamp, String unit, String precision, String unitStr, String format, int width, String color, String prefix) {
        String valueStr = timestamp2UnitStr(timestamp, unit, precision);
        String formatStr = "%" + width + "s";
        return String.format(formatStr, "<font color=\"" + color + "\">" + prefix + new java.text.SimpleDateFormat(format).format(new java.util.Date(timestamp)) + " " + valueStr + " " + unitStr + "</font>");
    }

}
