/**
 * Id
 */
package com.yuri.ynweb_kj.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * <code>title</code> 时间工具类
 * <p>
 * description
 * <p>
 * example: <blockquote>
 * 
 * <pre>
 * </blockquote>
 * </pre>
 * 
 * @author Author
 * @version Revision Date
 */
public class TimeUtils {
	public static String makeYMDHMSStringFormat(Date date) {
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ymdhms.format(date);
	}

	public static String makeYMDStringFormat(Date date) {
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		return ymd.format(date);
	}

	public static String makeYMStringFormat(Date date) {
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		return ym.format(date);
	}

	public static Date makeYMDHMSDateFormat(String date) {
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return ymdhms.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date makeYMDDateFormat(String date) {
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return ymd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date makeYMDateFormat(String date) {
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		try {
			return ym.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String makeYMDStringFormat(String date) {
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime = makeYMDHMSDateFormat(date);
		return ymd.format(dateTime);
	}

	public static String makeYMStringFormat(String date) {
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		Date dateTime = makeYMDHMSDateFormat(date);
		return ym.format(dateTime);
	}

	public static Date makeYMDHMSDateFormat(Date date) {
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = ymdhms.format(date);
		return makeYMDHMSDateFormat(dateTime);
	}

	public static Date makeYMDDateFormat(Date date) {
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = ymdhms.format(date);
		return makeYMDDateFormat(dateTime);
	}
	
	public static String cal(Integer minute) {
		if (minute == null || minute.toString().equals("0")) {
			return "0";
		} else {
			BigDecimal db = new BigDecimal(minute.floatValue());
			BigDecimal resultBig = db.divide(new BigDecimal("60"));
			return filter(String.valueOf(resultBig.floatValue()));
		}
	}
	
	public static String filter(String src) {
		DecimalFormat df = new DecimalFormat("#0.0");
		// 如果是小数
		if (Pattern.compile("[0-9]*(\\.)[0-9]*").matcher(src).find()) {
			// 判断为float类型
			if (Pattern.compile("\\.*[a-zA-Z]+\\.*").matcher(src).find()) {
				float float_str = Float.valueOf(src);
				String str = df.format(float_str).toString();
				if (str.split("\\.")[1].equals("0")) {
					str = str.split("\\.")[0];
				}
				return str;
			} else {
				double d_str = Double.parseDouble(src);
				String str_1 = df.format(d_str).toString();
				if (str_1.split("\\.")[1].equals("0")) {
					str_1 = str_1.split("\\.")[0];
				}
				return str_1;
			}

		} else {
			float f_strr = Float.valueOf(src);
			String str_3 = df.format(f_strr).toString();
			if (str_3.split("\\.")[1].equals("0")) {
				str_3 = str_3.split("\\.")[0];
			}
			return str_3;
		}
	}

	public static void main(String[] args) {
		System.out.println(makeYMStringFormat("2012-01-01 00:00:00"));
	}
}
