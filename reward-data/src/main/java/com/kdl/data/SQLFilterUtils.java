package com.kdl.data;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.kdl.data.exception.DaoValidatorException;


public class SQLFilterUtils {
	
	public static String sqlFilter(String str) {
		if(StringUtils.isBlank(str)) {
			return null;
		}
		//去掉 ' " ; --
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "--", "");
		//转换成小写
		str = str.toLowerCase();
		//非法字符
		String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};
		//判断是否包含非法字符
		if(ArrayUtils.contains(keywords, str)) {
			throw new DaoValidatorException("sql语句有注入风险");
		}
		return str;
	}

}
