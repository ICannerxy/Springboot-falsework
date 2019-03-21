package com.byavs.frame.core.utli;

import java.util.Random;

/**
 * Created by qibin.long on 2017/4/13.
 */
public final class GlobalIdGeneratorUtil {
    /**
     * 生成新的ID
     *
     * @return 32位ID
     */
    public final static String newId() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 生成新的ID
     *
     * @return 36位ID
     */
    public final static String uuid() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * 生成随机数
     *
     * @param length 随机数长度
     * @return 随机数
     */
    public final static String generateRandom(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(62)]);
        }
        return buffer.toString();
    }
}
