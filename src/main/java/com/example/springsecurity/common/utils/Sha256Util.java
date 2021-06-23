package com.example.springsecurity.common.utils;

import lombok.SneakyThrows;

import java.security.MessageDigest;

/**
 * Sha256
 * @ClassName Sha256Util
 * @Author zouwenhai
 * @Date 2021/6/17 22:47
 * @Version 1.0
 */
public class Sha256Util {


    /**
     * 利用java原生的类实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    @SneakyThrows
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes("UTF-8"));
        String encodeStr = byte2Hex(messageDigest.digest());
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
