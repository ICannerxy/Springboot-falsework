package com.byavs.frame.core.utli;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by qibin.long on 2017/5/1.
 */
public final class NumberUtil {

    public static BigDecimal add(BigDecimal... decValue) {
        BigDecimal total = BigDecimal.ZERO;
        if (decValue == null) {
            return total;
        }
        for (BigDecimal dec : decValue) {
            if (dec != null) {
                total = total.add(dec);
            }
        }
        return total;
    }

    public static Integer add(Integer... decValue) {
        Integer total = 0;
        if (decValue == null) {
            return total;
        }
        for (Integer dec : decValue) {
            if (dec != null) {
                total += dec;
            }
        }
        return total;
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null) {
            return b == null ? null : b.multiply(new BigDecimal(-1));
        }
        if (b == null) {
            return a;
        }
        return a.subtract(b);
    }

    public static BigDecimal multiply(BigDecimal... decValue) {
        if (decValue == null || decValue.length == 0) {
            return null;
        }
        BigDecimal totalValue = new BigDecimal(1);
        int nullCount = 0;
        for (BigDecimal value : decValue) {
            if (value == null) {
                nullCount++;
                continue;
            }
            if (BigDecimal.ZERO.compareTo(value) == 0) {
                return BigDecimal.ZERO;
            }
            totalValue = totalValue.multiply(value);
        }
        if (nullCount == decValue.length) {
            return null;
        }
        return totalValue;
    }

    public static BigDecimal divide(int a, int b) {
        //默认2位精度
        return divide(new BigDecimal(a), new BigDecimal(b), 2);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        if (BigDecimal.ZERO.compareTo(b) == 0) {
            return a;
        }
        return a.divide(b, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal string2BigDecimal(String str) {
        try {
            return new BigDecimal(str);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 随机生成n位数字
     *
     * @param length 数字长度
     * @return
     */
    public static int genRandomNumber(int length) {
        Random random = new Random();
        int seed = (int) Math.pow(10, length - 1);
        int n = random.nextInt(9 * seed) + seed;
        return n;
    }
}
