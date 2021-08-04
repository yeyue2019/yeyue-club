import club.yeyue.baomidou.club.BaomidouApplication;
import club.yeyue.baomidou.club.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-08-04 16:32
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BaomidouApplication.class}
        // 需要看druid监控时打开
//        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class OrderServiceTest {

    @Resource
    OrderService orderService;

    @Test
    public void testMethod01() {
        orderService.method01();
    }

    @Test
    public void testMethod02() {
        orderService.method02();
    }

    @Test
    public void testMethod03() {
        orderService.method03();
    }

    @Test
    public void testMethod04() {
        orderService.method04();
    }

    @Test
    public void testMethod05() {
        orderService.method05();
    }

}
