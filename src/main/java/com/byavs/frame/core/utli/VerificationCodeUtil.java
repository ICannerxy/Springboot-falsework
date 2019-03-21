package com.byavs.frame.core.utli;

/**
 * Created by zemo.wang on 2017/11/1.
 */
public class VerificationCodeUtil {
    /**
     * 生成验证码
     *
     * @return
     */
    public static String genRandomCode() {
        return String.valueOf(NumberUtil.genRandomNumber(6));
    }
}
