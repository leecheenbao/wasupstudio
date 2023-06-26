package com.wasupstudio.util;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
public class CashFlowUtils {
    private Key key;
    private Cipher cipher;

    public void init(String hashkey, String iv) throws Exception {
        byte[] keyBytes = hashkey.getBytes();
        // 如果密鑰不足16位，那麼就補足. 這個if 中的內容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 轉化成JAVA的密鑰格式
        key = new SecretKeySpec(keyBytes, "AES");
        // 初始化cipher
        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
    }

    /**
     * 加密方法
     *
     * @param content 要加密的字符串
     * @return
     */
    public String encrypt(String content, String hashkey, String iv) throws Exception {
        init(hashkey, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv.getBytes()));
        byte[] encryptedText = cipher.doFinal(content.getBytes());
        return new String(Hex.encode(encryptedText)).toUpperCase();
    }

    /**
     * 解密方法
     *
     * @param content 要解密的字符串
     * @return
     */
    public String decrypt(String content, String hashkey, String iv) throws Exception {
        init(hashkey, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv.getBytes()));
        byte[] encryptedText = cipher.doFinal(Hex.decode(content.getBytes()));
        return new String(encryptedText);
    }

    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的進行補0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

//    private static final String hashKey = "BdYLXebBmfD4D4RrXnPBgeBDaBqpfIgH";
//    private static final String hashIV = "PmMNcmmCK3Iap6AC";

    /* 將藍新的response解析 */
    public Map<String, String> getBlueNewData(String data) throws UnsupportedEncodingException {
        String[] dataArray = data.split("&");
        Map<String, String> dataMap = new HashMap<String, String>();
        for (String str : dataArray) {
            String[] d = str.split("=");
            if (d.length > 1) {
                if (d[0].equals("Message")) {
                    d[1] = URLDecoder.decode(d[1], "UTF8");
                }
                dataMap.put(d[0], d[1]);
            }
        }
        return dataMap;
    }

    /* tradeSha */
    public String getTradeSha(String tradeInfo,String hashKey,String hashIV) {
        String result;
        result = "HashKey=" + hashKey + "&" + tradeInfo + "&HashIV=" + hashIV;
        result = getSHA256StrJava(result).toUpperCase();
        return result;
    }

    /* 將dataInfo組成藍新要求的格式 */
    public String getDataInfo(Map<String, Object> map) {
        Set<String> keys = map.keySet();

        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            sb.append(key + "=" + map.get(key) + "&");
        }
        String result = sb.toString();
        if (result.lastIndexOf("&") == result.length() - 1) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

}
