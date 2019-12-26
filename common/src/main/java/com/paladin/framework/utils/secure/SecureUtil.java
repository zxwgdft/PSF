package com.paladin.framework.utils.secure;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.SecureRandom;

public class SecureUtil {

    private static SecureRandom secureRandom = new SecureRandom();
    private static int DEFAULT_NUM_BYTES = 16;

    public static String createSalt() {
        return createSalt(DEFAULT_NUM_BYTES);
    }

    public static String createSalt(int numBytes) {
        byte[] bytes = new byte[numBytes];
        secureRandom.nextBytes(bytes);
        return ByteSource.Util.bytes(bytes).toHex();
    }

    public static String hashByMD5(String password, String salt, int hashIterations) {
        return new SimpleHash("md5", password, ByteSource.Util.bytes(salt), hashIterations).toHex();
    }

}
