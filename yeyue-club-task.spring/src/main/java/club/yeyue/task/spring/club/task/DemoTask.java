package club.yeyue.task.spring.club.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fred
 * @date 2021-08-16 15:04
 */
@Slf4j
@Component
public class DemoTask {

    public static final Integer[] SLEEP_ARRAY = new Integer[]{
            4, 12, 1, 1, 1, 1, 1, 6, 3, 1
    };

    private static final AtomicInteger FIXED_RATE_COUNT = new AtomicInteger(0);

    public static final AtomicInteger FIXED_DELAY_COUNT = new AtomicInteger(0);

    public static final AtomicInteger CRON_COUNT = new AtomicInteger(0);


    /**
     * 以开始时间计算,每隔2s执行一次,如果中间存在积压的任务,则先执行完积压的任务
     */
    @Scheduled(fixedRate = 5000)
    public void fixedRate() throws InterruptedException {
        int executeTime = SLEEP_ARRAY[FIXED_RATE_COUNT.getAndIncrement() % 10];
        log.info("执行fixedRate任务:{}", executeTime);
        TimeUnit.SECONDS.sleep(executeTime);
    }

    /**
     * 以执行结束时间为准计算间隔
     */
    @Scheduled(fixedDelay = 1000)
    public void fixedDelay() throws InterruptedException {
        int executeTime = SLEEP_ARRAY[FIXED_DELAY_COUNT.getAndIncrement() % 10];
        log.warn("执行fixedDelay任务:{}", executeTime);
        TimeUnit.SECONDS.sleep(executeTime);
    }

    /**
     * 任务执行结束后继续按照表达式执行任务
     */
    @Scheduled(cron = "0/2 * * * * *")
    public void cron() throws InterruptedException {
        int executeTime = SLEEP_ARRAY[CRON_COUNT.getAndIncrement() % 10];
        log.error("执行cron任务:{}", executeTime);
        TimeUnit.SECONDS.sleep(executeTime);
    }
}
