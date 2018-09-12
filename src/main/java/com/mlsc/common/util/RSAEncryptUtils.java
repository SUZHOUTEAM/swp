package com.mlsc.common.util;

/**
 * Created by yinxl on 2017/7/26.
 */

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * RSA加密解密工具类
 */
@SuppressWarnings("unchecked")
public class RSAEncryptUtils {
    private static String module = "ybhndrU9d3UJzvD8WSS1chQ2ZLcn7Y/ZF0SZFJbdWxkvZYAcICUzfJBF4SecxG+ia9GseNDoQ30q+mSM5Ae1NaAy2bjmR1esS17AyX/TnSvtZIm0ACIVLIM6ShM+ukMj/TEhugarwHXhiHmEd8ZkMrVu4SW2XPgWjX7yPoEKLFs=";
    private static String exponentString = "AQAB";
    private static String delement = "NXVBNhxh5b6GkukadyVbUJg6sgY39qUgiyIKz4ILt5C9FtEUoxA4zNIPMtQkn4pWKOywIHR8mSYatbDgBa5lPxBemwvu5cMHVIh0sD25AL+jXk29alVOIPVTpZ/0TDgy7jd7psYUIX7EO80TnvJIOaNcGUNo060H9qpo19x2iYE=";

    public RSAEncryptUtils() {
    }

    public static ArrayList<byte[]> encryptToByteArrayList(String value, String keyXmlString) {
        try {
            byte[] modulusBytes = Base64.decodeBase64(module);
            byte[] exponentBytes = Base64.decodeBase64(exponentString);
            BigInteger modulus = new BigInteger(1, modulusBytes);
            BigInteger exponent = new BigInteger(1, exponentBytes);
            RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey pubKey = fact.generatePublic(rsaPubKey);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, pubKey);
            int splitLength = 39;
            ArrayList<byte[]> byteArrayList = new ArrayList();
            int i = 0;

            do {
                int Length = (i + 1) * splitLength >= value.length()?value.length() - i * splitLength:splitLength;
                byte[] byteArray = cipher.doFinal(value.substring(i * splitLength, Length).getBytes("UTF-8"));
                byteArrayList.add(byteArray);
                ++i;
            } while(i * splitLength < value.length());

            return byteArrayList;
        } catch (Exception var15) {
            var15.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String value, String keyXmlString) {
        ArrayList<byte[]> byteArrayList = encryptToByteArrayList(value, keyXmlString);
        StringBuilder sb = new StringBuilder();
        Iterator i$ = byteArrayList.iterator();

        while(i$.hasNext()) {
            byte[] byteArray = (byte[])i$.next();
            sb.append(bytesToHexString(byteArray));
            sb.append(",");
        }

        if(sb.length() != 0) {
            sb.delete(sb.length() - 1, sb.length());
        }

        return sb.toString();
    }

    public static String encrypt(String value) {
        return encrypt(value, null);
    }

    public static byte[] Dencrypt(byte[] encrypted) {
        try {
            byte[] expBytes = Base64.decodeBase64(delement);
            byte[] modBytes = Base64.decodeBase64(module);
            BigInteger modules = new BigInteger(1, modBytes);
            BigInteger exponent = new BigInteger(1, expBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance("RSA");
            RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modules, exponent);
            PrivateKey privKey = factory.generatePrivate(privSpec);
            cipher.init(2, privKey);
            byte[] decrypted = cipher.doFinal(encrypted);
            return decrypted;
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public static String decrypt(ArrayList<byte[]> byteArrayList, String keyXmlString) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        Iterator i$ = byteArrayList.iterator();

        while(i$.hasNext()) {
            byte[] byteArray = (byte[])i$.next();
            sb.append(new String(Dencrypt(byteArray), "UTF-8"));
        }

        return sb.toString();
    }

    public static String decrypt(String value, String keyXmlString) throws UnsupportedEncodingException {
        ArrayList<byte[]> byteArrayList = new ArrayList();
        if(StringUtils.isNotBlank(value)) {
            String[] strArray = value.split(",");
            String[] arr$ = strArray;
            int len$ = strArray.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String str = arr$[i$];
                int byteArrayLength = str.length() / 2;
                byte[] byteArray = new byte[byteArrayLength];

                for(int i = 0; i < byteArrayLength; ++i) {
                    try {
                        byteArray[i] = Integer.valueOf(str.substring(i * 2, (i + 1) * 2), 16).byteValue();
                    } catch (NumberFormatException var12) {
                        var12.printStackTrace();
                    }
                }

                byteArrayList.add(byteArray);
            }
        }

        return decrypt(byteArrayList, keyXmlString);
    }

    public static String decrypt(String value) {
        try {
            return decrypt(value, null);
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if(src != null && src.length > 0) {
            for(int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if(hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if(hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for(int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
}