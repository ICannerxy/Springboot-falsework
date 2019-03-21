package com.byavs.frame.core.utli;

/**
 * Created by zemo.wang on 2017/5/19.
 */
public class SaltUtil {
    /**
     * 字符串连接
     *
     * @param strs
     * @return
     */
    public static String linkString(String... strs) {
        if (strs == null) return null;
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            if (str != null) {
                builder.append(str);
            }
        }
        return builder.toString();
    }
}