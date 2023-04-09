package com.ssang.gtd.utils.crypto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;


@Component
public class RSACrypto {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 공개키와 개인키 한 쌍 생성
     * @param
     * @return
     * 사용법
     */
    public HashMap<String, String> createKeypairAsString() {
        HashMap<String, String> stringKeypair = new HashMap<>();

        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair(); // 키 생성

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String stringPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded()); // Base64 인코딩
            String stringPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded()); // Base64 인코딩

            stringKeypair.put("publicKey", stringPublicKey);
            stringKeypair.put("privateKey", stringPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringKeypair;
    }

    /**
     * 암호화 : 공개키로 진행
     * @param
     * @return
     *
     */
    public String encrypt(String plainText, String stringPublicKey) {
        String encryptedText = null;

        try {
            // 평문으로 전달받은 공개키를 사용하기 위해 공개키 객체 생성
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // byte형태로 공개키를 디코딩한다.
            byte[] bytePublicKey = Base64.getDecoder().decode(stringPublicKey.getBytes());
            // X509EncodedKeySpec 스펙을 사용하여 공개키를 ...
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
            // keyFactory 공개키 생성?
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // 만들어진 공개키 객체로 암호화 설정
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedText;
    }

    /**
     * 복호화 : 개인키로 진행
     * @param
     * @return
     *
     */
    public String decrypt(String encryptedText, String stringPrivateKey) {
        String decryptedText = null;

        byte[] decryptedBytes = new byte[0];
        try {

            // 평문으로 전달받은 공개키를 사용하기 위해 공개키 객체 생성
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytePrivateKey = Base64.getDecoder().decode(stringPrivateKey.getBytes());
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // 만들어진 공개키 객체로 복호화 설정
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 암호문을 평문화하는 과정
            decryptedText = new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedText;
    }
}