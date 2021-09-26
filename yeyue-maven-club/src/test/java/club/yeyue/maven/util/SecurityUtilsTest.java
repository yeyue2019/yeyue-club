package club.yeyue.maven.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fred
 * @date 2021-06-05 17:11
 */
@Slf4j
public class SecurityUtilsTest {


    private static final String IV_STRING = "F431E6FF9051DA0f";

    private static final String DEFAULT_KEY = "F431E6FF9051DA07201212223333dsfa";

    private static final String RSA_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIU9JKQlghEo/lj2Bjyse/5HFOAmWIJHTv+pEiS/VXHT93vteGYKgPQVFR5/a5/Goj2r8gpJWHfkSPeWze1Xi128KSl0ILgczIdJigTKBbYjrPt7xeAWVeLqTN2Zhwd5ehdv8ZvV/ETa5m9VVcfCZQS/AvAQfY1I0UEY9umkVoxbAgMBAAECgYBQ3zr1UzrudYZksvg4La2ZOsXmBABnGydNMO8tVOFlFPa7xvj0Xt3EyWMnPsoxkdx6OHrSWZCUPQE5HtThgei51S1sSJIhifXY8/alsPlc7DStGTyhZUdk+acYBGWpxfNEuzAxxmWoiruuKV/ZcQ/ZIC/WjQuyPN1bg4Jh9owwCQJBAPFT/5yC2RCL27DYPZeoU7I5JlCxnIvKF3aH5BTUNB3iino1PlXofB6W5hxolbzTNWSyD1R0w8s0q0aSatPflo8CQQCNVtszJZ2i4x+kxSa/dSOLeViTkzUh7/z/0Clr5eC2y+L5gEbUmV4LXrzL68ffz2+G8l3yCJKn93c6Yqh/c/N1AkAKNCYDpRiudWtlj88S4YAgcgAnu0rGMlZJobdIFizZ8qEuR6AKG9lbpb+3BckHAdspFcpGJOW+asyvIDO6H6MLAkAPHjVAGUvtOmIOG9BzNjJhHs+sqVhLkwH60uc7VVFd/B0BFwl8gBMhIDcNkODGPcfuJUqLMwOM19l280qWp25pAkBbuAwY5hm4N1Z5mhC+rQEHSvssRYDLDe4u4nfIc6B1WTeLs/phfBvY04sBIJoGT0LaNdmKeFxhiCMBjkxDOF6p";
    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFPSSkJYIRKP5Y9gY8rHv+RxTgJliCR07/qRIkv1Vx0/d77XhmCoD0FRUef2ufxqI9q/IKSVh35Ej3ls3tV4tdvCkpdCC4HMyHSYoEygW2I6z7e8XgFlXi6kzdmYcHeXoXb/Gb1fxE2uZvVVXHwmUEvwLwEH2NSNFBGPbppFaMWwIDAQAB";

    private static final ExecutorService TEST_EXECUTE = new ThreadPoolExecutor(1000, 1000, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new ThreadFactory() {
        private final AtomicInteger atomicInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "send_thread_" + atomicInteger.getAndIncrement() + "");
        }
    });

    @Test
    public void randomTest() throws NoSuchAlgorithmException {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            String random = SecurityUtils.random(SecurityUtils.RANDOM_ALGORITHM, 16);
            boolean result = set.add(random);
            if (!result) {
                System.out.println("重复的数据:" + random);
                break;
            }
        }
        System.out.println(set.size());
    }

    @Test
    public void randomThreadTest() throws ExecutionException, InterruptedException {
        Set<String> set = new HashSet<>();
        List<CompletableFuture<String>> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return SecurityUtils.random(SecurityUtils.RANDOM_ALGORITHM, 16);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return "aaa";
                }
            }, TEST_EXECUTE));
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
        for (CompletableFuture<String> future : list) {
            String data = future.get();
            if (StringUtils.equals(data, "aaa")) {
                System.out.println("有错误的数据");
                break;
            }
            boolean result = set.add(data);
            if (!result) {
                System.out.println("重复的数据:" + data);
                break;
            }
        }
        System.out.println(set.size());
    }

    @Test
    public void RsaKeyTest() throws NoSuchAlgorithmException {
        SecurityUtils.Keys keys = SecurityUtils.getKeys(SecurityUtils.RSA_ALGORITHM);
        System.out.println(keys.getPrivateKey());
        System.out.println(keys.getPublicKey());
    }

    @Test
    public void rsaEncryptTest() throws Exception {
        String data = "b12a5b485d283aaf2999eb29dec97c5310139f69389c2feaa74534554207cd73de6ccbec841d1494cf9bec0c5cf5e60cd72a59d059ba1ffb45a18b42f51b57a766def6ec803681126f2cc7d8dea54948b82adf5d38f7b616f00fcbe87afc60e4d549133d";
        System.out.println(data);
        String encrypt = SecurityUtils.encrypt(SecurityUtils.RSA_ALGORITHM, RSA_PUBLIC_KEY, null, SecurityUtils.RSA_ECB_PKCS1PADDING, data);
        System.out.println(encrypt);
    }

    @Test
    public void rsaDecryptTest() throws Exception {
        String data = "b12a5b485d283aaf2999eb29dec97c5310139f69389c2feaa74534554207cd73de6ccbec841d1494cf9bec0c5cf5e60cd72a59d059ba1ffb45a18b42f51b57a766def6ec803681126f2cc7d8dea54948b82adf5d38f7b616f00fcbe87afc60e4d549133d";
        String source = "KhDVGuZ9cW/FmRSg44OMW0Qq+tAkZT511Bx4nRenfnBKK+w7qQKc+lGoMXZdi9S1LclZYxizhSStlAIrkKpt+JVAUHvNypGQOdxDBU8W8bqudgZ8eTQExTprcBWAh7AFYPBCbxw5QO8QITzlBstC/DPM4PCAO4NPPnhkVOfz+wJK8UZDwZ1nN7LCM98XNAN3d3hXU/SMJppSQEJaQpUiqRX3vEkRto4iiRSJ/o2PqwY0pO5gNKY/yinyMxCYpQXYufzCDT4YhfV79CnABR40bLDlWES584Voaxun/rjfEt4Lb/lMpJPL8VRcOIkOlFRF5vedBUyCUQvXcyx9CgzKGQ==";
        String decrypt = SecurityUtils.decrypt(SecurityUtils.RSA_ALGORITHM, RSA_PRIVATE_KEY, null, SecurityUtils.RSA_ECB_PKCS1PADDING, source);
        System.out.println(decrypt);
        System.out.println(StringUtils.equals(data, decrypt));
    }

    @Test
    public void rsaSignTest() throws Exception {
        String data = "b12a5b485d283aaf2999eb29dec97c5310139f69389c2feaa74534554207cd73de6ccbec841d1494cf9bec0c5cf5e60cd72a59d059ba1ffb45a18b42f51b57a766def6ec803681126f2cc7d8dea54948b82adf5d38f7b616f00fcbe87afc60e4d549133d";
        String sign = SecurityUtils.sign(SecurityUtils.RSA_ALGORITHM, RSA_PRIVATE_KEY, SecurityUtils.RSA_SHA1, data);
        System.out.println(sign);
    }

    @Test
    public void rsaVerifyTest() throws Exception {
        String sign = "Zrv3DESz8HzkAY7blOLacVg/mLCiIctospsXPq71nXT++o8eVlwlUsdcaHQrRb9dgjSCHfv7wDfWTSJ2SVaaDjolCvRjkbjQzQWNHYZDHaAgqqwtULBPECNfEI1LeG05wrX0oovrQNV2UqQSpDjacWJ7vqESfs+t54EY+9Wu3cw=";
        String data = "b12a5b485d283aaf2999eb29dec97c5310139f69389c2feaa74534554207cd73de6ccbec841d1494cf9bec0c5cf5e60cd72a59d059ba1ffb45a18b42f51b57a766def6ec803681126f2cc7d8dea54948b82adf5d38f7b616f00fcbe87afc60e4d549133d";
        System.out.println(SecurityUtils.verify(SecurityUtils.RSA_ALGORITHM, RSA_PUBLIC_KEY, SecurityUtils.RSA_SHA1, data, sign));
    }

    @Test
    public void aesEncryptTest() throws Exception {
        String data = "b12a5b485d283aaf2999eb29dec97c5310139f69389c2feaa74534554207cd73de6ccbec841d1494cf9bec0c5cf5e60cd72a59d059ba1ffb45a18b42f51b57a766def6ec803681126f2cc7d8dea54948b82adf5d38f7b616f00fcbe87afc60e4d549133d";
        String encrypt = SecurityUtils.encrypt(SecurityUtils.AES_ALGORITHM, DEFAULT_KEY, IV_STRING, SecurityUtils.AES_CBC_PKCS5PADDING, data);
        System.out.println(encrypt);
    }

    @Test
    public void aesDecryptTest() throws Exception {
        String data = "b12a5b485d283aaf2999eb29dec97c5310139f69389c2feaa74534554207cd73de6ccbec841d1494cf9bec0c5cf5e60cd72a59d059ba1ffb45a18b42f51b57a766def6ec803681126f2cc7d8dea54948b82adf5d38f7b616f00fcbe87afc60e4d549133d";
        String source = "lsJJAuVq/mS44CrT2bHNVcNngyG8NO1Ffwa8wLt+n2VthZg1SsucEf9420llT9Az8+cDFMQK0wUqO01hKu47TJ08mHj2sEf6ItYsttywuBmU9VdP/68dobu8lOMbsk+m596LJic/2tfm6yNd5wUnw+/ns4WQPhGmJbpXhrO1/ZDmS1F59RKTdwK8P65BnYK1s62dpitUp4Bd0rPZXEoOdbdo3LLPDWm5cuT44OzBrj7gSn0fNFvSCgx5qjlGE5337gE460dIEmOCVk4hAzUarA==";
        String decrypt = SecurityUtils.decrypt(SecurityUtils.AES_ALGORITHM, DEFAULT_KEY, IV_STRING, SecurityUtils.AES_CBC_PKCS5PADDING, source);
        System.out.println(decrypt);
        System.out.println(StringUtils.equals(data, decrypt));
    }

    @Test
    public void nextIdTest() {
        Set<Long> set = new LinkedHashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            long res = SnowflakeIdUtils.generate(5L, 5L);
            System.out.println(res);
            if (!set.add(res)) {
                System.err.println("出现重复的流水号:" + res);
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间:" + (end - start));
        System.out.println(set.size());
    }

    @Test
    public void nextIdThreadTest() throws ExecutionException, InterruptedException {
        Set<Long> set = new HashSet<>();
        long start = System.currentTimeMillis();
        List<CompletableFuture<Long>> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(CompletableFuture.supplyAsync(() -> SnowflakeIdUtils.generate(5L, 5L), TEST_EXECUTE));
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
        long end = System.currentTimeMillis();
        System.out.println("执行时间:" + (end - start));
        for (CompletableFuture<Long> future : list) {
            long data = future.get();
            boolean result = set.add(data);
            if (!result) {
                System.out.println("重复的数据:" + data);
                break;
            }
        }
        System.out.println(set.size());
    }
}
