import club.yeyue.activemq.club.ActivemqApplication;
import club.yeyue.activemq.club.producer.ActivemqProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author fred
 * @date 2021-08-19 13:37
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ActivemqApplication.class})
public class ActivemqTest {

    @Resource
    ActivemqProducer producer;

    @Test
    public void mock() throws InterruptedException {
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void syncSendTest() throws InterruptedException {
        String data = "测试发送同步消息";
        producer.syncSend(data);
        new CountDownLatch(1).await();
    }

    @Test
    public void delaySendTest() throws InterruptedException {
        String data = "测试发送延时消息";
        producer.delaySend(data, 5 * 1000L);
        new CountDownLatch(1).await();
    }

    @Test
    public void asyncSendTest() throws InterruptedException {
        String data = "测试发送异步消息";
        producer.asyncSend(data).addCallback(new ListenableFutureCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("消息发送失败", throwable);
            }

            @Override
            public void onSuccess(Void unused) {
                log.info("消息发送成功");
            }
        });
        new CountDownLatch(1).await();
    }

    @Test
    public void testClusterSyncSend() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            producer.syncCluterSend("集群测试发送同步消息");
        }
        new CountDownLatch(1).await();
    }

    @Test
    public void testBroadcastSyncSend() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            producer.syncBroadcastSend("广播测试发送同步消息");
        }
        new CountDownLatch(1).await();
    }
}
