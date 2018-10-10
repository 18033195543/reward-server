package com.kdl.common.util.jdkUtil;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public final class StringUtil extends StringUtils {

	private static final String EMAIL_REG = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
	private static final String PHONE_REG = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
	private static final String HASNUMBER_REG = "^[^0-9]+$";

	private static final String EMOJI_REG = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
	private static final String CHN_REG = "^[\\u4e00-\\u9fa5]+$";
	private static final String URL_REG = "^(http|https)://[\\w\\.\\-]+(?:/|(?:/[\\w\\.\\-]+)*)?$";;

	private static final char[] WORDS = { '0', '1', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };

	/**
	 * 验证邮箱
	 *
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (isBlank(email)) {
			return false;
		}
		return email.matches(EMAIL_REG);
	}

	/**
	 * 验证电话号码
	 *
	 * @param telPhone
	 * @return
	 */
	public static boolean isTelPhone(String telPhone) {
		if (isBlank(telPhone)) {
			return false;
		}
		return telPhone.matches(PHONE_REG);
	}

	/**
	 * 是否包含数字
	 *
	 * @param value
	 * @return
	 */
	public static boolean notHasNumber(String value) {
		if (isBlank(value)) {
			return false;
		}
		return value.matches(HASNUMBER_REG);
	}

	/**
	 * 是否是表情
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isEmoji(char ch) {
		return !((ch == 0x0) || (ch == 0x9) || (ch == 0xA) || (ch == 0xD) || ((ch >= 0x20) && (ch <= 0xD7FF))
				|| ((ch >= 0xE000) && (ch <= 0xFFFD)) || ((ch >= 0x10000) && (ch <= 0x10FFFF)));
	}

	/**
	 * 清除一个字符串中的emoji表情字符
	 */
	public static String cleanEmoji(String str) {
		if (isBlank(str)) {
			return null;
		}
		StringBuilder builder = new StringBuilder(str);
		for (int i = 0; i < builder.length(); i++) {
			if (isEmoji(builder.charAt(i))) {
				builder.deleteCharAt(i);
				builder.insert(i, "");// 比字符串中直接替换字符要好，那样会产生很多字符串对象
			}
		}
		return builder.toString().trim();
	}

	public static String filterEmoji(String source) {
		if (source != null) {
			Pattern emoji = Pattern.compile(EMOJI_REG, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			if (emojiMatcher.find()) {
				source = emojiMatcher.replaceAll("*");
				return source;
			}
			return source;
		}

		return source;
	}

	public static boolean containsEmoji(String source) {
		if (source != null) {
			Pattern emoji = Pattern.compile(EMOJI_REG, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			if (emojiMatcher.find()) {
				// source = emojiMatcher.replaceAll("*");
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 是否是中文
	 *
	 * @param value
	 * @return
	 */
	public static boolean isChn(String value) {
		if (isBlank(value)) {
			return false;
		}
		return value.matches(CHN_REG);
	}

	/**
	 * 是否是URL
	 *
	 * @param value
	 * @return
	 */
	public static boolean isUrl(String value) {
		if (isBlank(value)) {
			return false;
		}
		return value.matches(URL_REG);
	}

	public static String getUUid() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

	public static String toJSONString(Object obj) {
		if (obj == null) {
			return "";
		}
		return JSON.toJSONString(obj);
	}

	public static String randomInt(int length) {
		StringBuilder code = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			code.append(RandomUtils.nextInt(0, 10));
		}
		return code.toString();
	}

}
