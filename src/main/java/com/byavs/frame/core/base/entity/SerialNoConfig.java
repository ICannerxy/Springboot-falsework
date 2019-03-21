package com.byavs.frame.core.base.entity;

/**
 * Created by qibin.long on 2017/4/30.
 */
public class SerialNoConfig {
    /**
     * 种子名
     */
    private String seed;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 从几开始
     */
    private int start = 0;
    /**
     * 最大序号
     */
    private int max = 99999999;
    /**
     * 预生成数量
     */
    private int prepare = 10;
    /**
     * 标准长度
     */
    private int length = 6;
    /**
     * 每日自动重置
     */
    private boolean autoreset = true;

    public SerialNoConfig(String seed, String prefix) {
        this.seed = seed;
        this.prefix = prefix;
    }

    public SerialNoConfig(String seed, String prefix, int length) {
        this.seed = seed;
        this.prefix = prefix;
        this.length = length;
    }

    public SerialNoConfig(String seed, String prefix, int start, int max, int prepare, int length, boolean autoreset) {
        this.seed = seed;
        this.prefix = prefix;
        this.start = start;
        this.max = max;
        this.prepare = prepare;
        this.length = length;
        this.autoreset = autoreset;
    }

    public boolean isAutoreset() {
        return autoreset;
    }

    public int getLength() {
        return length;
    }

    public int getPrepare() {
        return prepare;
    }

    public int getMax() {
        return max;
    }

    public int getStart() {
        return start;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSeed() {
        return seed;
    }

}
