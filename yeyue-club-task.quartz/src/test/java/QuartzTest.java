import club.yeyue.task.quartz.club.TaskQuartzApplication;
import club.yeyue.task.quartz.club.task.DemoTask1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.quartz.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-08-17 11:24
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TaskQuartzApplication.class}
        // 需要看druid监控时打开
//        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class QuartzTest {

    @Resource
    Scheduler scheduler;

    @Test
    public void taskTest() throws SchedulerException {
        // 创建 JobDetail
        JobDetail jobDetail = JobBuilder.newJob(DemoTask1.class)
                .withIdentity("task")
                .storeDurably()
                .build();
        // 创建 Trigger
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(3)
                .repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("trigger")
                .withSchedule(scheduleBuilder)
                .build();
        // 添加调度任务
        scheduler.scheduleJob(jobDetail, trigger);
    }
}

