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

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data; charset=utf-8");
    public static final MediaType FORM_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

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
    public static byte[] execute(HttpUrl url, Map<String, String> headers, String method, RequestBody body) {
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


    /**
     * 获取请求url
     *
     * @param scheme 协议
     * @param host   地址
     * @param path   请求路径
     * @param params 参数
     * @return url
     */
    public static HttpUrl getUrl(String scheme, String host, String path, Map<String, String> params) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .addPathSegments(path);
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(urlBuilder::addQueryParameter);
        }
        return urlBuilder.build();
    }

    /**
     * 获取请求Url
     *
     * @param url    url地址
     * @param params 参数
     * @return 结果
     */
    public static HttpUrl getUrl(String url, Map<String, String> params) {
        HttpUrl source = HttpUrl.parse(url);
        assert source != null;
        HttpUrl.Builder urlBuilder = source.newBuilder();
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(urlBuilder::addQueryParameter);
        }
        return urlBuilder.build();
    }

    /**
     * 获取请求头
     *
     * @param headers 请求头
     * @return 结果
     */
    public static Map<String, String> getHeaders(Map<String, String> headers, MediaType mediaType) {
        if (Objects.isNull(headers)) {
            headers = new HashMap<>(10);
        }
        headers.putIfAbsent("Accept", "application/json; charset=utf-8");
        if (Objects.equals(MEDIA_TYPE, mediaType)) {
            headers.putIfAbsent("Content-Type", "multipart/form-data; charset=utf-8");
        } else if (Objects.equals(FORM_TYPE, mediaType)) {
            headers.putIfAbsent("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        } else if (Objects.equals(JSON_TYPE, mediaType)) {
            headers.putIfAbsent("Content-Type", "application/json; charset=UTF-8");
        }
        return headers;
    }

    /**
     * 获取请求体
     *
     * @param upload   上传的文件
     * @param formData 提交的数据
     * @return 结果
     */
    public static MultipartBody multipartBody(Map<String, Map<String, byte[]>> upload, Map<String, String> formData) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (!CollectionUtils.isEmpty(upload)) {
            upload.forEach((key, value) -> value.forEach((name, file) -> builder.addFormDataPart(key, name, RequestBody.create(MEDIA_TYPE, file))));
        }
        if (!CollectionUtils.isEmpty(formData)) {
            formData.forEach(builder::addFormDataPart);
        }
        return builder.build();
    }

    /**
     * 获取请求体
     *
     * @param params 提交的参数
     * @return 结果
     */
    public static FormBody formBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(builder::add);
        }
        return builder.build();
    }

    /**
     * 获取请求体
     *
     * @param body 请求体数据
     * @return 结果
     */
    public static RequestBody jsonBody(String body) {
        return RequestBody.create(JSON_TYPE, body);
    }
}
