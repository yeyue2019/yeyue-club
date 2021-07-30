package shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import club.yeyue.shardingsphere.YeyueShardingSphereClubApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author fred
 * @date 2021-07-22 17:35
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {YeyueShardingSphereClubApplication.class}
        // 需要看druid监控时打开
//        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ShardingSphereTest {
}
