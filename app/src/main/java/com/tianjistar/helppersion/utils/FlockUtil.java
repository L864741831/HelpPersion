package com.tianjistar.helppersion.utils;




import com.tianjistar.helppersion.app.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;


/**
 * Created by 李超凡 on 2018/1/26.
 */

public class FlockUtil {
    /**
     * 加密 用后台的公钥加密 针对RSA
     * str 所要加密的字符串
     *
     * @return
     */
    public static String jiami(String str) {
        //获取公钥
        PublicKey publicKey = RSAUtil.keyStrToPublicKey(Constants.SERVER_PUBLIC_KEY);
        String publicEncryptedResult = RSAUtil.encryptDataByPublicKey(str.getBytes(), publicKey);
        try {
            return URLEncoder.encode(publicEncryptedResult, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解密 用自己的私钥解密 针对RSA
     * str 所要解密的字符串
     *
     * @return
     */
    public static String jiemi(String str) {
        //获取私钥
        PrivateKey privateKey = RSAUtil.keyStrToPrivate(Constants.CLIEND_PRIVATE_KEY);
        //私钥解密结果
        String privateDecryptedResult = "";
        try {
            privateDecryptedResult = RSAUtil.decryptedToStrByPrivate(URLDecoder.decode(str, "utf-8"), privateKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return privateDecryptedResult;
    }
}
