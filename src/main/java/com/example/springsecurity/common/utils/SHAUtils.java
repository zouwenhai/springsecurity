package com.example.springsecurity.common.utils;


import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Objects;

/**
 * Sha256加密
 *
 * @ClassName SHAUtils
 * @Author zouwenhai
 * @Date 2021/6/17 22:44
 * @Version 1.0
 */
public class SHAUtils {

    public static final String ENCODE_TYPE_HMAC_SHA_256 = "HmacSHA256";
    public static final String ENCODE_UTF_8_LOWER = "utf-8";
    public static final String ENCODE_UTF_8_UPPER = "UTF-8";

    public static String getSHA256Str(String secret, String message) {
        if (Objects.isNull(secret)) {
            return null;
        }
        String encodeStr = null;
        try {
            //HMAC_SHA256 加密
            Mac HMAC_SHA256 = Mac.getInstance(ENCODE_TYPE_HMAC_SHA_256);
            SecretKeySpec secre_spec = new SecretKeySpec(secret.getBytes(ENCODE_UTF_8_UPPER), ENCODE_TYPE_HMAC_SHA_256);
            HMAC_SHA256.init(secre_spec);
            byte[] bytes = HMAC_SHA256.doFinal(message.getBytes(ENCODE_UTF_8_UPPER));
            if (bytes == null && bytes.length < 1) {
                return null;
            }

            //字节转换为16进制字符串
            String SHA256 = byteToHex(bytes);
            if (Objects.isNull(SHA256)) {
                return null;
            }
            //base64
            String BASE64 = Base64.getEncoder().encodeToString(SHA256.getBytes(ENCODE_UTF_8_UPPER));
            if (StringUtils.isEmpty(BASE64)) {
                return null;
            }
            //url encode
            encodeStr = URLEncoder.encode(BASE64, ENCODE_UTF_8_LOWER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byteToHex(byte[] bytes) {

        if (bytes == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xff);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


}
