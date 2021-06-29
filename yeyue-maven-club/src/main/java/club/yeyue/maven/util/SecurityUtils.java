package club.yeyue.maven.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * 加解密工具类
 *
 * @author fred
 * @date 2021-06-29 09:01
 */
@Slf4j
public class SecurityUtils {
    private SecurityUtils() {
    }

    /**
     * 非对称加密算法 RSA
     */
    public static final String RSA_ALGORITHM = "RSA";


    public static final String AES_ALGORITHM = "AES";

    /**
     * 签名标准
     */
    public static final String RANDOM_ALGORITHM = "SHA1PRNG";

    /**
     * 加密填充方式, 该属性安卓有效, Java使用RSA加密后, 安卓使用该属性解密, 因安卓和 Java RSA 协议不同
     */
    public static final String RSA_ECB_PKCS1PADDING = "RSA/ECB/PKCS1Padding";

    /**
     * AES加密填充方式
     */
    public static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 签名标准
     */
    public static final String RSA_SHA1 = "SHA1WithRSA";

    /**
     * 密钥的长度为64的倍数， 1024位的密钥基本安全 2048位的密钥极其安全
     */
    public static final int KEY_SIZE = 1024;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static final GenericKeyedObjectPool<String, KeyFactory> KEY_POOL;
    public static final GenericKeyedObjectPool<CipherSource, Cipher> CIPHER_POOL;

    static {
        GenericKeyedObjectPoolConfig<KeyFactory> keyConfig = new GenericKeyedObjectPoolConfig<>();
        keyConfig.setMaxTotalPerKey(1000);
        KEY_POOL = new GenericKeyedObjectPool<>(new KeyPoolFactory(), keyConfig);
        GenericKeyedObjectPoolConfig<Cipher> cipherConfig = new GenericKeyedObjectPoolConfig<>();
        cipherConfig.setMaxTotalPerKey(1000);
        CIPHER_POOL = new GenericKeyedObjectPool<>(new CipherPoolFactory(), cipherConfig);
    }

    /**
     * 获取随机字符串
     *
     * @param size 字符串大小:限定偶数
     * @return 结果
     */
    public static String random(String algorithm, int size) throws NoSuchAlgorithmException {
        if (size < 1) {
            throw new IllegalArgumentException("Illegal Size: " + size);
        }
        assert StringUtils.isNotEmpty(algorithm);
        SecureRandom secureRandom = SecureRandom.getInstance(algorithm);
        byte[] result = new byte[size / 2];
        secureRandom.nextBytes(result);
        // 将十进制数转换成十六进制(使用&运算，正数部分没变，负数部分二进制从右往左第9位及以上都为0
        StringBuilder builder = new StringBuilder();
        for (byte num : result) {
            builder.append(Integer.toString((num & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString();
    }

    /**
     * 获取密码对
     *
     * @return 结果
     */
    public static Keys getKeys(String algorithm) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        return Keys.getInstance(Base64.getEncoder().encodeToString(publicKey.getEncoded()), Base64.getEncoder().encodeToString(privateKey.getEncoded()));
    }

    /**
     * 使用模和指数生成密码对
     * 注意：此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding
     *
     * @param modulus  模
     * @param exponent 指数
     * @return 结果
     */
    public static Keys getKeys(String algorithm, String modulus, String exponent) throws Exception {
        BigInteger b1 = new BigInteger(modulus);
        BigInteger b2 = new BigInteger(exponent);
        KeyFactory factory = null;
        try {
            factory = KEY_POOL.borrowObject(algorithm);
            return Keys.getInstance(Base64.getEncoder().encodeToString(factory.generatePublic(new RSAPublicKeySpec(b1, b2)).getEncoded()), Base64.getEncoder().encodeToString(factory.generatePrivate(new RSAPrivateKeySpec(b1, b2)).getEncoded()));
        } finally {
            if (null != factory) {
                KEY_POOL.returnObject(algorithm, factory);
            }
        }
    }

    /**
     * 获取公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return 结果
     */
    public static PublicKey loadPublicKey(String algorithm, String publicKeyStr) throws Exception {
        KeyFactory factory = null;
        try {
            factory = KEY_POOL.borrowObject(algorithm);
            return factory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)));
        } finally {
            if (null != factory) {
                KEY_POOL.returnObject(algorithm, factory);
            }
        }
    }

    /**
     * 获取私钥
     *
     * @param privateKeyStr 私钥字符串
     * @return 结果
     */
    public static PrivateKey loadPrivateKey(String algorithm, String privateKeyStr) throws Exception {
        KeyFactory factory = null;
        try {
            factory = KEY_POOL.borrowObject(algorithm);
            return factory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr)));
        } finally {
            if (null != factory) {
                KEY_POOL.returnObject(algorithm, factory);
            }
        }
    }


    /**
     * 消息加密
     *
     * @param algorithm      加密方式
     * @param key            公钥字符串
     * @param iv             偏移量 16byte
     * @param transformation 加密标准
     * @param source         加密数据
     * @return 结果
     */
    public static String encrypt(String algorithm, String key, String iv, String transformation, String source) throws Exception {
        CipherSource instance = CipherSource.getInstance(algorithm, key, iv, transformation, Cipher.ENCRYPT_MODE);
        Cipher cipher = null;
        try {
            cipher = CIPHER_POOL.borrowObject(instance);
            switch (instance.getAlgorithm()) {
                case RSA_ALGORITHM:
                    byte[] target = source.getBytes();
                    // RSA分段加密
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                        rsaHandle(cipher, target, out, MAX_ENCRYPT_BLOCK);
                        return Base64.getEncoder().encodeToString(out.toByteArray());
                    }
                case AES_ALGORITHM:
                    return Base64.getEncoder().encodeToString(cipher.doFinal(source.getBytes()));
                default:
                    throw new IllegalArgumentException("algorithm is not supported ! :" + instance.getAlgorithm());
            }
        } finally {
            if (Objects.nonNull(cipher)) {
                CIPHER_POOL.returnObject(instance, cipher);
            }
        }
    }

    /**
     * 消息解密
     *
     * @param algorithm      加密方式
     * @param key            私钥字符串
     * @param iv             偏移量 16byte
     * @param transformation 解密标准
     * @param source         解密数据
     * @return 结果
     */
    public static String decrypt(String algorithm, String key, String iv, String transformation, String source) throws Exception {
        CipherSource instance = CipherSource.getInstance(algorithm, key, iv, transformation, Cipher.DECRYPT_MODE);
        Cipher cipher = null;
        try {
            cipher = CIPHER_POOL.borrowObject(instance);
            switch (instance.getAlgorithm()) {
                case RSA_ALGORITHM:
                    byte[] target = Base64.getDecoder().decode(source);
                    // RSA分段加密
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                        rsaHandle(cipher, target, out, MAX_DECRYPT_BLOCK);
                        return out.toString();
                    }
                case AES_ALGORITHM:
                    return new String(cipher.doFinal(Base64.getDecoder().decode(source)));
                default:
                    throw new IllegalArgumentException("algorithm is not supported ! :" + instance.getAlgorithm());
            }
        } finally {
            if (Objects.nonNull(cipher)) {
                CIPHER_POOL.returnObject(instance, cipher);
            }
        }
    }

    /**
     * 私钥签名
     *
     * @param algorithm      加密方式
     * @param privateKeyStr  私钥字符串
     * @param transformation 签名标准
     * @param source         签名数据
     * @return 结果
     */
    public static String sign(String algorithm, String privateKeyStr, String transformation, String source) throws Exception {
        PrivateKey privateKey = loadPrivateKey(algorithm, privateKeyStr);
        Signature signature = Signature.getInstance(transformation);
        signature.initSign(privateKey);
        signature.update(source.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * 公钥验签
     *
     * @param algorithm      加密方式
     * @param publicKeyStr   共钥字符串
     * @param transformation 签名标准
     * @param source         校验的数据
     * @param sign           签名
     * @return 结果
     */
    public static boolean verify(String algorithm, String publicKeyStr, String transformation, String source, String sign) throws Exception {
        PublicKey publicKey = loadPublicKey(algorithm, publicKeyStr);
        Signature signature = Signature.getInstance(transformation);
        signature.initVerify(publicKey);
        signature.update(source.getBytes());
        return signature.verify(Base64.getDecoder().decode(sign));
    }

    private static void rsaHandle(Cipher cipher, byte[] target, ByteArrayOutputStream out, int maxSize) throws IllegalBlockSizeException, BadPaddingException {
        int length = target.length;
        int i = 0;
        int offSet = 0;
        byte[] cache;
        while (length - offSet > 0) {
            if (length - offSet > maxSize) {
                cache = cipher.doFinal(target, offSet, maxSize);
            } else {
                cache = cipher.doFinal(target, offSet, length - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxSize;
        }
    }

    private static class KeyPoolFactory extends BaseKeyedPooledObjectFactory<String, KeyFactory> {

        @Override
        public KeyFactory create(String key) throws Exception {
            return KeyFactory.getInstance(key);
        }

        @Override
        public PooledObject<KeyFactory> wrap(KeyFactory value) {
            return new DefaultPooledObject<>(value);
        }

        @Override
        public void destroyObject(String key, PooledObject<KeyFactory> p, DestroyMode mode) throws Exception {
            super.destroyObject(key, p, mode);
            key = null;
            p = null;
        }
    }

    /**
     * 密钥对
     */
    @Data
    public static class Keys implements Serializable {
        private static final long serialVersionUID = 6622623060572535394L;

        private String publicKey;

        private String privateKey;

        public static Keys getInstance(String publicKey, String privateKey) {
            Keys instance = new Keys();
            instance.publicKey = publicKey;
            instance.privateKey = privateKey;
            return instance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Keys keys = (Keys) o;
            return StringUtils.equals(publicKey, keys.publicKey) && StringUtils.equals(privateKey, keys.privateKey);
        }

        @Override
        public int hashCode() {
            return Objects.hash(publicKey, privateKey);
        }
    }

    public static class CipherPoolFactory extends BaseKeyedPooledObjectFactory<CipherSource, Cipher> {

        @Override
        public Cipher create(CipherSource key) throws Exception {
            Cipher cipher = Cipher.getInstance(key.getTransformation());
            switch (key.getAlgorithm()) {
                case AES_ALGORITHM:
                    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getKey().getBytes(), AES_ALGORITHM);
                    if (StringUtils.isNotEmpty(key.getIv())) {
                        cipher.init(key.getMode(), secretKeySpec, new IvParameterSpec(key.getIv().getBytes()));
                    } else {
                        cipher.init(key.getMode(), secretKeySpec);
                    }
                    break;
                case RSA_ALGORITHM:
                    switch (key.getMode()) {
                        case Cipher.PUBLIC_KEY:
                            cipher.init(Cipher.PUBLIC_KEY, loadPublicKey(RSA_ALGORITHM, key.getKey()));
                            break;
                        case Cipher.PRIVATE_KEY:
                            cipher.init(Cipher.PRIVATE_KEY, loadPrivateKey(RSA_ALGORITHM, key.getKey()));
                            break;
                        default:
                            throw new IllegalArgumentException("mode is not supported ! :" + key.getMode());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("algorithm is not supported ! :" + key.getAlgorithm());
            }
            return cipher;
        }

        @Override
        public PooledObject<Cipher> wrap(Cipher value) {
            return new DefaultPooledObject<>(value);
        }

        @Override
        public void destroyObject(CipherSource key, PooledObject<Cipher> p, DestroyMode mode) throws Exception {
            super.destroyObject(key, p, mode);
            key = null;
            p = null;
        }
    }

    /**
     * Cipher参数
     */
    @Data
    public static class CipherSource implements Serializable {
        private static final long serialVersionUID = 7114097150806542152L;

        /**
         * 加密方式
         */
        private String algorithm;

        /**
         * 密钥
         */
        private String key;

        /**
         * 偏移量 AES加密时使用
         */
        private String iv;

        /**
         * 加密标准
         */
        private String transformation;

        /**
         * 模块
         */
        private int mode;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CipherSource that = (CipherSource) o;
            return StringUtils.equals(key, that.key) && StringUtils.equals(iv, that.iv) && StringUtils.equals(algorithm, that.algorithm) && StringUtils.equals(transformation, that.transformation) && mode == that.mode;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, iv, algorithm, transformation, mode);
        }

        public static CipherSource getInstance(String algorithm, String key, String iv, String transformation, int mode) {
            CipherSource instance = new CipherSource();
            instance.algorithm = algorithm;
            instance.key = key;
            instance.iv = iv;
            instance.transformation = transformation;
            instance.mode = mode;
            return instance;
        }
    }
}
