package  com.hdsx.lh.maptiles.utils;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * Created by 黄聪 on 2017/10/16.
 */
public class MD5Utils {

    /**
     * 对字符串MD5加密处理
     * @param s
     * @return
     */
    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    public static void main(String[] args){
        System.out.println(MD5("123"));
        System.out.println(MD5("123"));
    }
}
