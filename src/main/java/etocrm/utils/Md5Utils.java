package etocrm.utils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

    /**
     * 对字符串 MD5 加盐值加密
     *
     * @param plainText
     * 传入要加密的字符串
     * @param saltValue
     * 传入要加的盐值
     * @return MD5加密后生成32位(小写字母 + 数字)字符串
     */
    private static final String key = "e4c3a30b-935d-439b-8bfa-856f10bbd777";

    protected static String encode(String plainText, String saltValue) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            md.update(plainText.getBytes());
            md.update(saltValue.getBytes());

            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值。1 固定值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encode(String password) {
        return encode(password, key);
    }
}

