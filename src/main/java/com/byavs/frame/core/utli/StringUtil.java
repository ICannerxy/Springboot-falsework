package com.byavs.frame.core.utli;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created by qibin.long on 2017/4/16.
 */
public final class StringUtil {
    /**
     * 字符串为空
     *
     * @param strs
     * @return
     */
    public static boolean isEmpty(Object... strs) {
        boolean flag = false;
        if (strs == null) {
            return true;
        }
        for (Object obj : strs) {
            if (obj == null) {
                flag = true;
                continue;
            }
            String s = obj.toString();
            if ("".equals(s) || s.length() == 0 || "".equals(s.trim())) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 是否相等
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isEqual(Object obj1, Object obj2) {
        if (isEmpty(obj1) && isEmpty(obj2)) {
            return true;
        }
        if (!isEmpty(obj1) && !isEmpty(obj2)) {
            return obj1.toString().equals(obj2);
        }
        return false;
    }

    /**
     * 正则验证手机号 是否合法
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        Pattern p = compile("^1\\d{10}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static boolean isTelNo(String telNo) {
        Pattern p = compile("^(\\d{3,4}-)?\\d{7,8}((-||转)*\\d{1,4})?$");
        Matcher m = p.matcher(telNo);
        return m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */

    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 字符串合并，分隔符默认为逗号","
     *
     * @param obj
     * @return
     */
    public static String join(List<String> obj) {
        return join(obj, ",");
    }

    /**
     * 字符串合并
     *
     * @param obj
     * @param separator
     * @return
     */
    public static String join(List<String> obj, String separator) {
        if (null == obj || obj.isEmpty()) {
            return null;
        }
        if (separator == null) {
            separator = ",";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : obj) {
            sb.append(separator);
            sb.append(str);
        }
        return sb.length() > 0 ? sb.substring(1) : null;
    }

    /**
     * 字符是否在列表中
     *
     * @param obj
     * @param strs
     * @return
     */
    public static boolean in(String obj, List<String> strs) {
        if (obj == null || strs == null) {
            return false;
        }
        for (String s : strs) {
            if (obj.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * null转为空字符
     *
     * @param obj
     * @returns
     */
    public static String null2Empty(Object obj) {
        return null == obj ? "" : obj.toString().trim();
    }
}
