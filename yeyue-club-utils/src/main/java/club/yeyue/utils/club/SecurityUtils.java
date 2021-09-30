package club.yeyue.utils.club;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 加解密工具类
 *
 * @author fred
 * @date 2021-09-01 10:59
 */
public class SecurityUtils {
    private SecurityUtils() {
    }

    public static final String RSA_ALGORITHM = "RSA";
    public static final String AES_ALGORITHM = "AES";
    public static final String EC_ALGORITHM = "EC";
    public static final String SM4_ALGORITHM = "SM4";
    public static final String SM3_ALGORITHM = "SM3";
    public static final String BC_PROVIDER = "BC";
    public static final String SM2_SPEC = "sm2p256v1";

    /**
     * 随机字符串
     *
     * @param algorithm 生成算法
     * @param size      生成的随机字符串位数
     * @return 结果
     */
    public static String randomString(String algorithm, int size) throws Exception {
        // 指定随机字符串位数为偶数
        if (size < 1 || (size & 1) == 1) {
            throw new IllegalArgumentException("Illegal Size: " + size);
        }
        if (StringUtils.isEmpty(algorithm)) {
            throw new NoSuchAlgorithmException("None Algorithm");
        }
        SecureRandom sourceRandom = SecureRandom.getInstance(algorithm);
        byte[] result = new byte[size / 2];
        sourceRandom.nextBytes(result);
        // 十进制转十六进制
        StringBuilder builder = new StringBuilder();
        for (byte byt : result) {
            builder.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return builder.toString();
    }

    /**
     * 获取随机Ascii字符串
     *
     * @param size 位数
     * @return 结果
     */
    public static String randomString(int size) {
        return RandomStringUtils.randomAscii(size);
    }

    /**
     * base64编码
     *
     * @param encode 编码对象
     * @return 结果
     */
    public static String base64Encode(byte[] encode) {
        return Base64.getEncoder().encodeToString(encode);
    }

    /**
     * Base64解码
     *
     * @param decode 解码对象
     * @return 结果
     */
    public static byte[] base64Decode(String decode) {
        return Base64.getDecoder().decode(decode);
    }

    /**
     * md5
     *
     * @param source 加密对象
     * @return 结果
     */
    public static String md5(String source) {
        return DigestUtils.md5Hex(source);
    }

    /**
     * sha-1
     *
     * @param source 加密对象
     * @return 结果
     */
    public static String sha1(String source) {
        return DigestUtils.sha1Hex(source);
    }

    /**
     * sha-256
     *
     * @param source 加密对象
     * @return 结果
     */
    public static String sha256(String source) {
        return DigestUtils.sha256Hex(source);
    }

    /**
     * sm3
     *
     * @param source 加密对象
     * @return 结果
     */
    public static String sm3Hex(String source) {
        return Hex.encodeHexString(sm3(source));
    }

    /**
     * 自定义Key sm3
     *
     * @param source 加密对象
     * @return 结果
     */
    public static String hmacSm3Hex(String key, String source) {
        return Hex.encodeHexString(hmacSm3(key, source));
    }

    /**
     * BC sm3
     *
     * @param source 加密对象
     * @return 结果
     */
    public static String sm3bcHex(String source) throws Exception {
        return Hex.encodeHexString(sm3bc(source));
    }

    /**
     * uuid
     *
     * @return 结果
     */
    public static String uuid() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        UUID uuid = new UUID(random.nextInt(), random.nextInt());
        return uuid.toString();
    }


    /**
     * 获取密钥对
     *
     * @param algorithm 生成算法
     * @param provider  供应商
     * @param keySize   密钥长度
     * @return 返回结果
     */
    public static KeyPair getKeyPair(String algorithm, String provider, Integer keySize) throws Exception {
        KeyPairGenerator keyPairGenerator;
        switch (algorithm) {
            case EC_ALGORITHM:
                keyPairGenerator = KeyPairGenerator.getInstance(EC_ALGORITHM, provider);
                keyPairGenerator.initialize(new ECGenParameterSpec(SM2_SPEC), new SecureRandom());
                return keyPairGenerator.generateKeyPair();
            case RSA_ALGORITHM:
                keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
                keyPairGenerator.initialize(keySize, new SecureRandom());
                return keyPairGenerator.generateKeyPair();
            default:
                throw new IllegalArgumentException("algorithm is not supported ! :" + algorithm);
        }
    }

    /**
     * 获取密钥
     *
     * @param algorithm 生成算法
     * @param keySize   密钥长度
     * @return 返回结果
     */
    public static SecretKey getSecretKey(String algorithm, int keySize) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(keySize, new SecureRandom());
        return keyGenerator.generateKey();
    }

    /**
     * 加密/解密
     *
     * @param mode           模块 1:加密|2:解密
     * @param algorithm      加密方式
     * @param provider       供应商
     * @param key            公钥字符串
     * @param iv             偏移量 16byte
     * @param transformation 加密标准
     * @param source         加密数据
     * @param maxSize        rsa一次分段加密的数据量 密钥位数/8 -11
     * @return 结果
     */
    public static byte[] handle(int mode, String algorithm, String provider, String key, String iv, String transformation, byte[] source, Integer maxSize) throws Exception {
        Cipher cipher = loadCipher(algorithm, provider, key, iv, transformation, mode);
        switch (algorithm) {
            case AES_ALGORITHM:
            case EC_ALGORITHM:
            case SM4_ALGORITHM:
                return cipher.doFinal(source);
            case RSA_ALGORITHM:
                return rsa(cipher, source, maxSize);
            default:
                throw new IllegalArgumentException("algorithm is not supported ! :" + algorithm);
        }
    }

    /**
     * 签名
     *
     * @param algorithm      加密方式
     * @param privateKeyStr  私钥字符串
     * @param transformation 签名标准
     * @param source         签名数据
     * @return 结果
     */
    public static byte[] sign(String algorithm, String privateKeyStr, String transformation, byte[] source) throws Exception {
        PrivateKey privateKey = loadPrivateKey(algorithm, privateKeyStr);
        Signature signature = Signature.getInstance(transformation);
        signature.initSign(privateKey);
        signature.update(source);
        return signature.sign();
    }

    /**
     * 公钥验签
     *
     * @param algorithm      加密方式
     * @param publicKeyStr   共钥字符串
     * @param transformation 签名标准
     * @param source         校验的数据
     * @param sign           签名的数据
     * @return 结果
     */
    public static boolean verify(String algorithm, String publicKeyStr, String transformation, byte[] source, byte[] sign) throws Exception {
        PublicKey publicKey = loadPublicKey(algorithm, publicKeyStr);
        Signature signature = Signature.getInstance(transformation);
        signature.initVerify(publicKey);
        signature.update(source);
        return signature.verify(sign);
    }

    /**
     * 获取加解密的Cipher
     *
     * @param algorithm      加解密算法
     * @param provider       供应商
     * @param key            密钥
     * @param iv             偏移量
     * @param transformation 加解密标准
     * @param mode           模块
     * @return 结果
     */
    public static Cipher loadCipher(String algorithm, String provider, String key, String iv, String transformation, int mode) throws Exception {
        Cipher cipher = StringUtils.isEmpty(provider) ? Cipher.getInstance(transformation) : Cipher.getInstance(transformation, provider);
        switch (algorithm) {
            case AES_ALGORITHM:
            case SM4_ALGORITHM:
                if (StringUtils.isEmpty(iv)) {
                    cipher.init(mode, loadSecretkey(AES_ALGORITHM, key));
                } else {
                    cipher.init(mode, loadSecretkey(AES_ALGORITHM, key), new IvParameterSpec(iv.getBytes()));
                }
                break;
            case RSA_ALGORITHM:
            case EC_ALGORITHM:
                switch (mode) {
                    case Cipher.PUBLIC_KEY:
                        cipher.init(Cipher.PUBLIC_KEY, loadPublicKey(algorithm, key));
                        break;
                    case Cipher.PRIVATE_KEY:
                        cipher.init(Cipher.PRIVATE_KEY, loadPrivateKey(algorithm, key));
                        break;
                    default:
                        throw new IllegalArgumentException("mode is not supported ! :" + mode);
                }
                break;
            default:
                throw new IllegalArgumentException("algorithm is not supported ! :" + algorithm);
        }
        return cipher;
    }

    /**
     * 根据私钥字符串生成私钥
     *
     * @param algorithm     生成算法
     * @param privateKeyStr 私钥字符串
     * @return 返回结果
     */
    public static PrivateKey loadPrivateKey(String algorithm, String privateKeyStr) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(algorithm);
        return factory.generatePrivate(new PKCS8EncodedKeySpec(base64Decode(privateKeyStr)));
    }

    /**
     * 根据公钥字符串生成公钥
     *
     * @param algorithm    生成算法
     * @param publicKeyStr 公钥字符串
     * @return 返回结果
     */
    public static PublicKey loadPublicKey(String algorithm, String publicKeyStr) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(algorithm);
        return factory.generatePublic(new X509EncodedKeySpec(base64Decode(publicKeyStr)));
    }

    /**
     * 根据密钥字符串生成密钥
     *
     * @param algorithm    生成算法
     * @param secretKeyStr 密钥字符串
     * @return 结果
     */
    public static SecretKey loadSecretkey(String algorithm, String secretKeyStr) {
        return new SecretKeySpec(base64Decode(secretKeyStr), algorithm);
    }

    /**
     * rsa加解密 需要分段执行
     *
     * @param cipher  执行器
     * @param source  加/解密数据
     * @param maxSize 一次加密/解密的数据最大值
     * @return 结果
     */
    public static byte[] rsa(Cipher cipher, byte[] source, int maxSize) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int length = source.length;
            int i = 0;
            int offSet = 0;
            byte[] cache;
            while (length - offSet > 0) {
                if (length - offSet > maxSize) {
                    cache = cipher.doFinal(source, offSet, maxSize);
                } else {
                    cache = cipher.doFinal(source, offSet, length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * maxSize;
            }
            return out.toByteArray();
        }
    }

    private static byte[] sm3(String source) {
        byte[] target = source.getBytes(StandardCharsets.UTF_8);
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(target, 0, target.length);
        byte[] hash = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(hash, 0);
        return hash;
    }

    private static byte[] hmacSm3(String key, String source) {
        byte[] target = source.getBytes(StandardCharsets.UTF_8);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(new KeyParameter(key.getBytes(StandardCharsets.UTF_8)));
        mac.update(target, 0, target.length);
        byte[] hash = new byte[mac.getMacSize()];
        mac.doFinal(hash, 0);
        return hash;
    }

    private static byte[] sm3bc(String source) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(SM3_ALGORITHM, BC_PROVIDER);
        return messageDigest.digest(source.getBytes(StandardCharsets.UTF_8));
    }
}
