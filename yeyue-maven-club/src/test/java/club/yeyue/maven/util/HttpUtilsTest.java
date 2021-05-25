package club.yeyue.maven.util;

import club.yeyue.maven.YeyueMavenClubApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fred
 * @date 2021-05-25 14:48
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {YeyueMavenClubApplication.class})
public class HttpUtilsTest {

    @Test
    public void getTest() {
        Map<String, String> param = new HashMap<>();
        param.put("access_token", "aaa");
        param.put("openid", "sss");
        param.put("lang", "zh_CN");
        byte[] result = OkHttpUtils.sendGet("https", "api.weixin.qq.com", "cgi-bin/user/info", param, null);
        System.out.println(new String(result));
    }

    @Test
    public void getDownTest() throws IOException {
        String ticket = "gQF98TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyQmI5eThTd2tjY0UxeU1MS053MW4AAgQQqaxgAwSghgEA";
        byte[] result = OkHttpUtils.sendGet("https", "mp.weixin.qq.com", "cgi-bin/showqrcode", Collections.singletonMap("ticket", ticket), null);
        File file = new File("/Users/yeyue/Downloads/测.jpg");
        FileUtils.writeByteArrayToFile(file, result);
    }

}
