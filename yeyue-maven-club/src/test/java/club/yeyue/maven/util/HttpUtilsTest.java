package club.yeyue.maven.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fred
 * @date 2021-05-25 14:48
 */
@Slf4j
public class HttpUtilsTest {

    @Test
    public void getTest() {
        Map<String, String> param = new HashMap<>();
        param.put("openid", "sss");
        param.put("lang", "zh_CN");
        byte[] result = OkHttpUtils.execute(OkHttpUtils.getUrl("https://api.weixin.qq.com/cgi-bin/user/info?accessToken=aaaaa", param), OkHttpUtils.getHeaders(null, null), OkHttpUtils.GET, null);
        System.out.println(new String(result));
    }

    @Test
    public void postTest() {
        Map<String, String> param = new HashMap<>();
        param.put("accessToken", "aaaa");
        Map<String, String> map = new HashMap<>();
        map.put("appid", "aaaaa");
        byte[] result = OkHttpUtils.execute(OkHttpUtils.getUrl("https://api.weixin.qq.com/wxa/getuserriskrank", param), OkHttpUtils.getHeaders(null, OkHttpUtils.JSON_TYPE), OkHttpUtils.POST, OkHttpUtils.jsonBody(JacksonUtils.toJsonString(map)));
        System.out.println(new String(result));
    }

}
