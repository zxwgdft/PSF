package com.paladin.framework.utils.secure;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author TontoZhou
 * @since 2020/1/17
 */
public class AESEncryptUtil {

    //bMFmZY9W1FRdkbqqAi9JWQ

    public static String encrypt(String content, String key, boolean urlSafe) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); //"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return urlSafe ? Base64.encodeBase64URLSafeString(encrypted) : Base64.encodeBase64String(encrypted);
    }


    // 解密
    public static String decrypt(String content, String key) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encrypted1 = new Base64().decode(content);//先用base64解密
        try {
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }
}
