package com.byavs.frame.core.utli;

import java.io.File;

/**
 * Created by qibin.long on 2017/4/14.
 */
public class FileUtil {
    /**
     * 取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 取不带扩展名的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameWithoutEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 获取路径目录
     *
     * @param filepath
     * @return
     */
    public static String getPathDir(String filepath) {
        return new File(filepath).getParent();
    }

    /**
     * 创建文件，如果目录不存在则自动创建
     *
     * @param dir
     * @param filename
     * @return
     */
    public static File create(String dir, String filename) {
        File file = new File(dir, filename);
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    /**
     * 文件或路径是否存在
     *
     * @param filepath
     * @return
     */
    public static boolean exists(String filepath) {
        if (StringUtil.isEmpty(filepath))
            return false;
        ;
        return new File(filepath).exists();
    }
}
