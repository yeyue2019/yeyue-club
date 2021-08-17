package club.yeyue.task.quartz.club.task;

import club.yeyue.task.quartz.club.service.DemoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-08-17 10:38
 */
@Slf4j
public class DemoTask2 extends QuartzJobBean {

    @Resource
    DemoService demoService;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("DemoTask2:executeInternal开始执行任务");
        demoService.execute();
    }


}
