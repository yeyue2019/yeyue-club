package club.yeyue.maven.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * http请求工具类
 *
 * @author fred
 * @date 2021-05-25 09:17
 */
@Slf4j
public class OkHttpUtils {
    private OkHttpUtils() {
    }

    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder()
            .connectionPool(new ConnectionPool(600, 60, TimeUnit.SECONDS))
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data");

    @SneakyThrows
    public static byte[] sendGet(String url) {
        return sendGet(url, null);
    }

    @SneakyThrows
    public static byte[] sendGet(String url, Map<String, String> headers) {
        return execute(HttpUrl.parse(url), GET, null, defaultFormHeaders(headers));
    }

    @SneakyThrows
    public static byte[] sendGet(String scheme, String host, String path, Map<String, String> params, Map<String, String> headers) {
        return execute(getUrl(scheme, host, path, params), GET, null, defaultFormHeaders(headers));
    }

    @SneakyThrows
    public static byte[] sendPost(String url, String body) {
        return sendPost(url, body, null);
    }

    @SneakyThrows
    public static byte[] sendPost(String url, String body, Map<String, String> headers) {
        return execute(HttpUrl.parse(url), POST, RequestBody.create(JSON_TYPE, body), defaultJsonHeaders(headers));
    }

    @SneakyThrows
    public static byte[] sendPost(String scheme, String host, String path, Map<String, String> params, String body, Map<String, String> headers) {
        return execute(getUrl(scheme, host, path, params), POST, RequestBody.create(JSON_TYPE, body), defaultJsonHeaders(headers));
    }

    @SneakyThrows
    public static byte[] sendPost(String url) {
        return sendPost(url, (String) null);
    }

    @SneakyThrows
    public static byte[] sendPost(String url, Map<String, String> headers) {
        return sendPost(url, (String) null, headers);
    }

    @SneakyThrows
    public static byte[] sendPost(String url, Map<String, String> params, Map<String, String> headers) {
        return execute(HttpUrl.parse(url), POST, formBody(params), defaultFormHeaders(headers));
    }

    @SneakyThrows
    public static byte[] sendPost(String scheme, String host, String path, Map<String, String> params, Map<String, String> headers) {
        return execute(getUrl(scheme, host, path, null), POST, formBody(params), defaultFormHeaders(headers));
    }

    @SneakyThrows
    public static byte[] sendPost(String scheme, String host, String path, Map<String, String> params, Map<String, Map<String, byte[]>> upload, Map<String, String> headers) {
        return sendPost(scheme, host, path, params, upload, null, headers);
    }

    @SneakyThrows
    public static byte[] sendPost(String url, Map<String, Map<String, byte[]>> upload, Map<String, String> form, Map<String, String> headers) {
        return execute(HttpUrl.parse(url), POST, multipartBody(upload, form), headers);
    }

    @SneakyThrows
    public static byte[] sendPost(String scheme, String host, String path, Map<String, String> params, Map<String, Map<String, byte[]>> upload, Map<String, String> form, Map<String, String> headers) {
        return execute(getUrl(scheme, host, path, params), POST, multipartBody(upload, form), headers);
    }


    /**
     * 同步请求通用方法
     *
     * @param url     请求地址
     * @param method  请求方法
     * @param body    请求体
     * @param headers 请求头
     * @return 结果
     */
    @SneakyThrows
    private static byte[] execute(HttpUrl url, String method, RequestBody body, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        builder.url(url).method(method, body);
        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach(builder::addHeader);
        }
        long t1 = System.currentTimeMillis();
        Request request = builder.build();
        try (Response response = CLIENT.newCall(request).execute()) {
            long t2 = System.currentTimeMillis();
            ResponseBody responseBody = response.body();
            byte[] result = null;
            if (Objects.nonNull(responseBody)) {
                result = responseBody.bytes();
            }
            log.info("请求地址:[{}],请求方式:[{}],请求体:[{}],返回状态码:[{}],响应时间:[{}]", url, method, body, response.code(), t2 - t1);
            return result;
        }
    }


    private static MultipartBody multipartBody(Map<String, Map<String, byte[]>> upload, Map<String, String> form) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (!CollectionUtils.isEmpty(upload)) {
            upload.forEach((key, value) -> value.forEach((name, file) -> builder.addFormDataPart(key, name, RequestBody.create(MEDIA_TYPE, file))));
        }
        if (!CollectionUtils.isEmpty(form)) {
            form.forEach(builder::addFormDataPart);
        }
        return builder.build();
    }

    private static FormBody formBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(builder::add);
        }
        return builder.build();
    }

    /**
     * 默认的form提交请求头
     *
     * @param headers 请求头
     * @return 结果
     */
    private static Map<String, String> defaultFormHeaders(Map<String, String> headers) {
        if (Objects.isNull(headers)) {
            headers = new HashMap<>(10);
        }
        headers.putIfAbsent("Accept", "application/json;charset=utf-8");
        headers.putIfAbsent("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }

    /**
     * 默认的Json请求头
     *
     * @param headers 请求头
     * @return 结果
     */
    private static Map<String, String> defaultJsonHeaders(Map<String, String> headers) {
        if (Objects.isNull(headers)) {
            headers = new HashMap<>(10);
        }
        headers.putIfAbsent("Accept", "application/json;charset=utf-8");
        headers.putIfAbsent("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }


    /**
     * 获取请求url
     *
     * @param scheme 协议
     * @param host   地址
     * @param path   请求路径
     * @param params 参数
     * @return url
     */
    private static HttpUrl getUrl(String scheme, String host, String path, Map<String, String> params) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .addPathSegments(path);
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(urlBuilder::addQueryParameter);
        }
        return urlBuilder.build();
    }


}
