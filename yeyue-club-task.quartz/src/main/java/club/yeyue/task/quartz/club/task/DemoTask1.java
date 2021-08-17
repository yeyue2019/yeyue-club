package club.yeyue.task.quartz.club.task;

import club.yeyue.task.quartz.club.service.DemoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-08-17 10:29
 */
@Slf4j
// 不允许并发执行的实例
@DisallowConcurrentExecution
// 设置参数传递
@PersistJobDataAfterExecution
public class DemoTask1 extends QuartzJobBean {

    @Resource
    DemoService demoService;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String key = "DemoTask1";
        int res = 0;
        if (dataMap.containsKey(key)) {
            res = dataMap.getInt(key);
        }
        dataMap.put(key, res + 1);
        log.info("DemoTask1:executeInternal开始执行任务,获取到的参数:{}", res);
        demoService.execute();
    }
}
