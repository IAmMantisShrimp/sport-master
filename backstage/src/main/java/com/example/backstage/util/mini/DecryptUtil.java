package com.example.backstage.util.mini;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;

/**
 * 微信小程序解密
 * @author ajie
 * @createTime 2021年06月20日 19:51:00
 */
public class DecryptUtil {

    public static boolean initialized = false;

    public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIv(ivByte));
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | InvalidParameterSpecException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    /**
     * 生成IV秘钥
     * @param iv
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidParameterSpecException
     */
    public static AlgorithmParameters generateIv(byte[] iv) throws NoSuchAlgorithmException, InvalidParameterSpecException {
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(iv));
        return parameters;
    }
}
