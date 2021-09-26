import club.yeyue.utils.club.SecurityUtils;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.Security;

/**
 * @author fred
 * @date 2021-09-24 13:42
 */
public class SecurityUtilsTest {

    public final String source = "它用图片通俗易懂地解释了，\"数字签名\"（digital signature）和\"数字证书\"（digital certificate）到底是什么。\n" +
            "\n" +
            "我对这些问题的理解，一直是模模糊糊的，很多细节搞不清楚。读完这篇文章后，发现思路一下子就理清了。为了加深记忆，我把文字和图片都翻译出来了。\n" +
            "\n" +
            "文中涉及的密码学基本知识，可以参见我以前的笔记。";

    // 生成rsa密钥
    @Test
    public void keyPairTest() throws Exception {
        KeyPair keyPair = SecurityUtils.getKeyPair("RSA", null, 2048);
        String privateKey = SecurityUtils.base64Encode(keyPair.getPrivate().getEncoded());
        String publicKey = SecurityUtils.base64Encode(keyPair.getPublic().getEncoded());
        System.out.println("public Key is -> \n" + publicKey);
        System.out.println("private Key is -> \n" + privateKey);
    }

    public static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoHOKrFg2dQLh9nhY9T4mZAdnJJYU1+6TfzQ/230kbDyG6OMo7q8i+bZuoDugoEQSTBpkEgZc4rfOVS95HIgHNjPkv+1oZ9vQr32d152garIGH5BQz/ztFNgp5HUVWR8y3nWb3+LtymJo2M67BpC6BYa/HQJDMXvhpZPbseQCMe4ibPzlW0/OXCsr/3p4Ythqen7Tbu+TyZLC6NrHRg04+qJsZYGReNCVDy/z/RsbTbNK7yknYVfpGsUMoNLjRJk9UheBiE0yBaukvuY9fNURPlCAqhyd04CArC9yTkl3ZvpxjWkuz9+QYcNo6V4FeENj1AJU5hepcUTdDKhonVYPrQIDAQAB";
    public static final String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgc4qsWDZ1AuH2eFj1PiZkB2cklhTX7pN/ND/bfSRsPIbo4yjuryL5tm6gO6CgRBJMGmQSBlzit85VL3kciAc2M+S/7Whn29CvfZ3XnaBqsgYfkFDP/O0U2CnkdRVZHzLedZvf4u3KYmjYzrsGkLoFhr8dAkMxe+Glk9ux5AIx7iJs/OVbT85cKyv/enhi2Gp6ftNu75PJksLo2sdGDTj6omxlgZF40JUPL/P9GxtNs0rvKSdhV+kaxQyg0uNEmT1SF4GITTIFq6S+5j181RE+UICqHJ3TgICsL3JOSXdm+nGNaS7P35Bhw2jpXgV4Q2PUAlTmF6lxRN0MqGidVg+tAgMBAAECggEAS3cMehLjShWZAbuI24/nrpRsxqBwHT9w0BtbDXjGcMHnEhIFZn2pDZnoXiStX7Okh3bCKlgjx957hUVWyTyRZ8TwifS3bmHMvT1vx8GzeXtRtfbCi5CCUZN54KHQ8KHj4w2dyqeI/+9ZMN/Kx0O6um6t5I7z24pQjvgEFJ8RCTog/0c64nOU4iwnl5jQSRaeTOwEZa8+EbF7yrdpK/rzPiuBLsOJDmNtWQVRy3Fng+XjqyqTGEA6bNdRokLil2mj/y3WbacqOCenjB/KyRe7iIevscDVXT9uJtqyQpWkIuk38+wQAzVXnF+BgVe3k7Yqz1BvGWK1booi+kEFfr/VAQKBgQDMfd5C+n6d4u6l4+DSTzcFLNAVsV62ZlPPRR+1FdiFlFeZqd2sHqXPhziaoJswo43BVpeI1rDNJsU0jLNLrEemA+Dqh5QYfaiZGWakgRH8Py3CDZLTsBBSmnEXqjGyao3WKU8ktoXetwrFrb1itEhxqK29Z/awuL3DJlU/lvOwxQKBgQDI3dfKGKyV4d+fqB7/jtKRUy2RRt7nFJUlxKjEbF4quDmvxAoEsl1NgBqbMuFttculOhpv8UcUIyiV47dfCLP2gnzWsQNQ8wiyDTguiVQV/ba1XTcCsj+EMSnY+CR4cMki9LZHGT1My0j3eVd5zxhCUaPvtlLQDFB5NQZsEQmByQKBgQCquwgfyb5/GeIAVRL25DZetryA8BbQmPyuV9sSOMwPMvw0ipNOSkRL0W0cBK2tXVBUNHTORdlVX2JU7ogfzRYA8MoPZeTq0bc7I+8Dt9OJVEJi+yw0W5I1NUbo6CsX+Ei1nsW6ND3u5cozo0w0paq6Yoe0VKGtiIC+9zlDn0eUtQKBgQC1t4SVz3aq7bnZ/UV12yaB1Gb/68aR9RvtHBm2AXe7XAjMCyUpFJe2GjN3vjMUhofiRUtPhE7SV/BKkcT9k6kDVQ9NRryCSivOsIjoCNLqRio5r0eqB8nQdI2wPOT5lMdR+WLBtYKwa6PqRnsttn3y2Vre7OnET6+px5+0ukEIiQKBgA7STF+xuDxEePiTkV/nZiFq58z+6uzkDr/+p3C3yKnYYl3s3eNJDFJZvLeo/MM2QCa/+UVxUCF6u+7nAde7eDnol25u7/ipZVDm9DnBYuGq0mUMARldO4CUFMildsmRsPIak9bMRrpqFVQyKbJ4xRUCyK53somjTzdBx/jIFKI3";

    // rsa公钥加密
    @Test
    public void rsaEncryptTest() throws Exception {
        byte[] target = source.getBytes(StandardCharsets.UTF_8);
        byte[] result = SecurityUtils.handle(1, "RSA", null, publicKey, null, "RSA/ECB/PKCS1Padding", target, 245);
        System.out.println(SecurityUtils.base64Encode(result));
    }

    private final String rsaEncryptStr = "HqRYmImrqkwyVNDV15wAnTCqeERxJ/2rI4NqvdaJV7h7wgroLfOGplkiUb+YYIjoR5YhO1CXkPKY2hESGzLYn/eUslExFiQiKM/6jKuD12F89QHKl1/12O/WgVvgYecVmunADDwVeYw8wG5MIGnpU9zMvVbs7BnGpLvjCmPzf45R4oJ/sv4+1NSgcDlpyj2zNTLLwGdpKjPUd6Js9WsOanceNT1dXtvuTeUtlZjzLagHKWuJnN4Rrei4GSvLuCDzNVGD2xEI97cDHBxfMUsQVD7Tj1hf2gVBynq39GHprdEn8pxypSEypvFOgg1iDsmpgNI0GZZRIQBUULyAEOmtVAL4njgEUmyR0ihFSfc5JSwVUritYbjjs+NAGWafRaH1B+LV9+klDwgRH+CbvjjpHr+3TKdgZGSj7Gm6hWlKAWTo/5mUu8Q0UkBavzww15swTDC+LLmiqkkorV0/ZTmB4RykeLFl++6Z0+MU+IR8J1oRdzJDE2kg6ALTgGlL1V3+Jp4Lri52TaS8tZsori37MZ3ie1aUu+sArZcAH0SXTuXi+QgS6DhbT4vvCOn+NuY2QBovsHaPRUz2ZxMqWJYe1cK9CCW/un10QhTQQX7rQWlHBDXPLjm1c9an/g6vBmLCNFoLoTjEuPYw2ko4gcpyaK7/EoVUEmp+O+bhh3l5Mjk=";

    // rsa私钥解密
    @Test
    public void rsaDecryptTest() throws Exception {
        byte[] target = SecurityUtils.base64Decode(rsaEncryptStr);
        byte[] result = SecurityUtils.handle(2, "RSA", null, privateKey, null, "RSA/ECB/PKCS1Padding", target, 256);
        System.out.println(new String(result, StandardCharsets.UTF_8));
    }

    // rsa签名
    @Test
    public void rsaSignTest() throws Exception {
        byte[] signRes = SecurityUtils.sign("RSA", privateKey, "SHA256WithRSA", source.getBytes(StandardCharsets.UTF_8));
        System.out.println(SecurityUtils.base64Encode(signRes));
    }

    private static final String rsaSignStr = "JSWqbTQHTcjVZTYRPRlMMQNV3LhrpV8cBoPQzxAJ6ZdQkNybtHmN3M4V8XskoFMsE8Lgvq+BTBY1Zt/mLa7VxjilnMyHDIFtffIwwVhyZecBbwYCMOii7JNtH9OKGd63JYPKdrTLVHrMmIsrw0ndko0Tp9nywp6G4Kz7yCgp32FdoAT0/SOHO6ixvJ9BbtjmjfrEeg3noFmj9VYJIDepdheOVqaEFFauLu5PbMm4SInycDLYPF2mFgZiolJyfRYm/Gnc5WFur3HCtbWA6jv/f7BHj3GWZEe83WzRPiFVuQ8V+xaK0qsuYslqpPjtXdu1ZJeXsnXrh4mvXLhRD8WFjQ==";

    // rsa验签
    @Test
    public void rsaVerifyTest() throws Exception {
        boolean verify = SecurityUtils.verify("RSA", publicKey, "SHA256WithRSA", source.getBytes(StandardCharsets.UTF_8), SecurityUtils.base64Decode(rsaSignStr));
        System.out.println(verify);
    }

    // 生成aes密钥
    @Test
    public void aesKeyTest() throws Exception {
        SecretKey key = SecurityUtils.getSecretKey("AES", 256);
        System.out.println(SecurityUtils.base64Encode(key.getEncoded()));
    }

    public static final String aesKey = "H08dxCb8Ua5bxZ68+gQE2urvMgVUTjAe4CI9oJQ95ZU=";

    // 随机偏移量
    @Test
    public void randStringTest() throws Exception {
//        System.out.println(SecurityUtils.randomString("SHA1PRNG", 32));
        System.out.println(SecurityUtils.randomString(16));
    }

    public static final String aesIv = "rCv&})$fa&SMJH@}";

    // aes加密
    @Test
    public void aesEncryptTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] target = source.getBytes(StandardCharsets.UTF_8);
        byte[] result = SecurityUtils.handle(1, "AES", "BC", aesKey, aesIv, "AES/CBC/PKCS7Padding", target, null);
        System.out.println(SecurityUtils.base64Encode(result));
    }

    private static final String aesEncrypt = "aNLUqCCe44l0rkpmt+riT/psd1i6FMUaBmBhhTPkeV0QeDgSJ7XYTeDi+wlLyeo2iz0AQYqskwpoS2paxdYRS4Y7+Y4WXdtP1mCmI5XLSIDUxOm5rh2SgY2Vok6Hu8sNGiho9d2kUebJ4b+XFWEm9SLsty2mYGZx0+IOTIGdEJnMPHsGy1dio56gL1627100xn4JRZEMl+ipY8HW90nGM1PqQTq0vIJxrigxgDcBYODfeT7MKXfsz6j1pNROrjvxGdQJEf/g/ZmuMe5JJEyqGFzqdC86Yo4LDSYQ41N2WHY5QYdsoDcHRYX4LWHZyE2avWYVK8lRX0y4gWCS09QIlQIs/72ioIRrlvv0+tnQdHSn4092mpCFCLSgUBzS4lIh/IJ5bRbY9VN/3sJJHb0wVtkRxYeyTk6LiR7DxfJepmkBqa4H5ipxgUgaGUcBb+smK23g9tGq3euNq6Xn58RgPM9jZ8sO8BV6pSmmb1Ku4jysPVcPt03K+RtVtNsKp8JBCLZAW2m4M0D7ENLRxOQCWIR0ZcXDtiVCmRE909pCJpQe+M9iFzm05wC6CCOyVF0q";

    // aes解密
    @Test
    public void aesDecryptTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] target = SecurityUtils.base64Decode(aesEncrypt);
        byte[] result = SecurityUtils.handle(2, "AES", "BC", aesKey, aesIv, "AES/CBC/PKCS7Padding", target, null);
        System.out.println(new String(result, Charset.defaultCharset()));
    }

    // 获取sm2密钥
    @Test
    public void sm2KeyPairTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyPair keyPair = SecurityUtils.getKeyPair("EC", "BC", null);
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();
        System.out.println("privateKey:\n" + SecurityUtils.base64Encode(privateKey.getEncoded()));
        System.out.println("publicKey:\n" + SecurityUtils.base64Encode(publicKey.getEncoded()));
    }

    public static final String sM2PrivateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg3nOOyfmoKYIF8zWLCEa4Mf5xscIVPVJ4t4RJtHuwn32gCgYIKoEcz1UBgi2hRANCAAQPzIEzT6gFJFbkO+A0QpmfDxe9Wb/eGXa5l76ThT5FX8zvZwOA7BbV/ccYa93xyqh1t+EPZ3P4Ec+een4E7XLj";
    public static final String sm2PublicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAED8yBM0+oBSRW5DvgNEKZnw8XvVm/3hl2uZe+k4U+RV/M72cDgOwW1f3HGGvd8cqodbfhD2dz+BHPnnp+BO1y4w==";


    // sm2签名
    @Test
    public void sm2SignTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] signRes = SecurityUtils.sign("EC", sM2PrivateKey, "SM3withSM2", source.getBytes(StandardCharsets.UTF_8));
        System.out.println(SecurityUtils.base64Encode(signRes));
    }

    public static final String sm2SignStr = "MEQCIGbxXhTg/Jvsre7OhGSlDjLt1+UdmjysGqfu/TKpBFNPAiAGQlNDEUV7pTxo5nW897s/3v3VYwcpidAyU3PZcm4zHQ==";

    // sm2验签
    @Test
    public void sm2VerifyTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        boolean verify = SecurityUtils.verify("EC", sm2PublicKey, "SM3withSM2", source.getBytes(StandardCharsets.UTF_8), SecurityUtils.base64Decode(sm2SignStr));
        System.out.println(verify);
    }

    // sm2公钥加密
    @Test
    public void sm2EncryptTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] target = source.getBytes(StandardCharsets.UTF_8);
        byte[] result = SecurityUtils.handle(1, "EC", "BC", sm2PublicKey, null, "SM2", target, null);
        System.out.println(SecurityUtils.base64Encode(result));
    }

    private final String sm2EncryptStr = "BDjGQBgZaMptupIfXbibRISl9fMvAfYOzDuDY/Kyy0MhmKrXg06VfskpPeKrQx4CHZVM6zWfHCT8DDHh8auWeovuLXS2Bsf/6AsDtMgVANb9rbZnrCb8MemkKw5N18MAVpTCFIGSyHIcyEmb20lAY5a/8Cu7d5WV6q2UM1HToF5UAlfl71uaibzjSwbeTxHEPGN+1XXWmhksinVkSoY4vAFlhjeVRU0F/rDpdU8Lv0InmmNqma79eNaV8v/uSJMFg4CtRLicDmqxXrLYWTDVmarvZooWwtb5YgplpTT3EIM1eCwY5M3vnMvQqDDUC8mlD7PbBFBzLN+Krtn947IbKDhT9PzWmWG+K7Wm2flxTqydgiXiIq21xiTrz8CHwKKB9jlMCskwekxkJr67PKTUy9lm/sF54zHIDDmC0fKJKN/zzrAyKS2Wu2y2RnoHdXtZoatxtlvLkJ27JmBMQJozqacz681ZV/UJ7opnA/DIqrij/Kgnogaj+VcrZN6VOWV/5L3PWFjdvGf7txRvUV5XP+EOsrOT1cEBMnUZPAgsdCow8vh0vXFRlOvVxMa+0flKO8dpU5biQ7uxq/DUbQJMRO80L910vgJagIjilJ1V3gGanO6R9uIVWRQnIC3kqpBcF7xkhUfMPW42w1Q2ze/kGUUCGZcDcf9f8V95jm8D5/7WwDbV";

    // sm2私钥解密
    @Test
    public void sm2DecryptTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] target = SecurityUtils.base64Decode(sm2EncryptStr);
        byte[] result = SecurityUtils.handle(2, "EC", "BC", sM2PrivateKey, null, "SM2", target, 256);
        System.out.println(new String(result, StandardCharsets.UTF_8));
    }

    @Test
    public void hashTest() throws Exception {
        System.out.println(SecurityUtils.md5(source));
        System.out.println(SecurityUtils.sha1(source));
        System.out.println(SecurityUtils.sha256(source));
        System.out.println(SecurityUtils.sm3Hex(source));
        System.out.println(SecurityUtils.hmacSm3Hex(SecurityUtils.randomString(32), source));
    }

    // 生成sm4密钥
    @Test
    public void sm4KeyTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        SecretKey key = SecurityUtils.getSecretKey("SM4", 128);
        System.out.println(SecurityUtils.base64Encode(key.getEncoded()));
    }

    public static final String SM4Key = "gf/mirDfRw/0nOPJnDiU+w==";
    public static final String SM4Iv = "IN|\\g[D,>LD\\X(92";

    // sm4加密
    @Test
    public void sm4EncryptTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] target = source.getBytes(StandardCharsets.UTF_8);
        byte[] result = SecurityUtils.handle(1, "SM4", "BC", SM4Key, SM4Iv, "SM4/CBC/PKCS5PADDING", target, null);
        System.out.println(SecurityUtils.base64Encode(result));
    }

    private static final String sm4Encrypt = "VFru4aSfwcPKsTPvXXB+WInkayFfdm9Q+TqAOz10oIn+Xx660BU42Q3AdTsyEYO5VGRc0vJ9UFMTA9e9svF8D+P9uOA5PUOxjrbCaASalIGE0uMEsUjp2Ie2yRxf9U6sG+dFX/XHdDtXf4WGFA5rWXdSIDgDmEtZv9RqGvTxFTxkEcsiW5NCKcSn99pjJLoJfEVEaXy0NgoCe08DXvkuGsUpcraN1LkJR3tgtfI2EDh3nqrzOSt1jf28qF4yfsauTA//xfn/g8vtJ80fpOZBcEvaaLV8OzL2TzIy3ki+1M04jyb+YFOLCa7Y5UJK/uvm4vvycwJF9fRcTgpawlWdrGwgDyM94ganONkPp9aOc3JsP+hS0SSJ66qizCxTLOKtsD41TtL5q/hZ5D4kyNqwaUs5foEnUt/rVu7tiRxCtjBr5yI+Urwo2xkxyPn2Dqs28kP4mb1V+bESPx50LVY0FT+EZmwB7eOzbyX4fo6wz6Pvs1C1u2WZ3VDvPW/YVJ1J2sK6tIVejgihZUbdt2NiCdFsdYqmpoCD3kAz8nK0BDqor55RW9SvAzMJlX7m63if";

    // sm4解密
    @Test
    public void sm4DecryptTest() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        byte[] target = SecurityUtils.base64Decode(sm4Encrypt);
        byte[] result = SecurityUtils.handle(2, "SM4", "BC", SM4Key, SM4Iv, "SM4/CBC/PKCS5PADDING", target, null);
        System.out.println(new String(result, Charset.defaultCharset()));
    }
}
