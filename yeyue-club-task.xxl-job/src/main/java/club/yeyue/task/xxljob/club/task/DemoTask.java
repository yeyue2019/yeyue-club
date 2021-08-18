package club.yeyue.task.xxljob.club.task;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fred
 * @date 2021-08-17 15:24
 */
@Slf4j
@Component
public class DemoTask {

    public static final AtomicInteger INTEGER = new AtomicInteger(0);

    @XxlJob("yeyueDemoHandler")
    public void demoJobHandler() throws Exception {
        log.info("DemoTask:execute第({})次执行任务", INTEGER.incrementAndGet());
        TimeUnit.SECONDS.sleep(2L);
    }

}
