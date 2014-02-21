package com.alipay.faultinject.attach.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */

/**
 * 
 * @author yimingwym 
 * @version $Id: ClassUtils.java, v 0.1 2014-2-20 上午11:26:04 yimingwym Exp $
 */
public class ClassUtils {
    private static final Logger logger = LogManager.getLogger(ClassUtils.class.getName());

    public static String getPackagePath(String className) {
        String packagePath = "";
        String[] list = className.split("\\/");
        for (int i = 0; i < list.length - 1; i++) {
            packagePath += list[i] + "/";
        }
        return packagePath;

    }

    /**
     * className is separated by dot(".") : com.alipay.example
     * 
     * @param className
     * @return
     */
    public static String getLocalPath(String prefix, String className) {
        if (!prefix.endsWith("/")) {
            prefix += "/";
        }
        return prefix + className.replace(".", "/") + ".class";
    }

    /**
     * 保存class文件
     * 
     * @param path
     * @param className className with slash : com/alipay/example
     * @param classFile
     * 
     */
    public static void storeClassFile(String path, String className, byte[] classFile) {
        try {
            if (!path.endsWith("/")) {
                path += "/";
            }
            File dir = new File(path + getPackagePath(className));
            if (dir.exists() && dir.isFile()) {
                logger.info("dir is exist");
            } else {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(path + className + ".class");
                FileOutputStream stream = new FileOutputStream(file);
                stream.write(classFile);
                stream.close();
            }
        } catch (Exception e) {
            logger.error("store class encounter Exception", e);
        }
    }

    public static void main(String[] args) {
        String className = "com/alipay/lock/processor/policy/HasLockPolicy";
        System.out.println(ClassUtils.getPackagePath(className));
        //ClassUtils.storeOriClassFile("/Users/yimingwym/Downloads", className, );
    }
}
