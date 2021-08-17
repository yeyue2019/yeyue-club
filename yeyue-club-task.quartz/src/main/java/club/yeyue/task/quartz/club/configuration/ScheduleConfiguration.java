package club.yeyue.task.quartz.club.configuration;

import club.yeyue.task.quartz.club.task.DemoTask1;
import club.yeyue.task.quartz.club.task.DemoTask2;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过自动配置的方式去执行定时任务
 *
 * @author fred
 * @date 2021-08-17 10:28
 */
@Configuration
public class ScheduleConfiguration {

    public static class Demo1TaskConfiguration {

        @Bean
        public JobDetail task1() {
            return JobBuilder.newJob(DemoTask1.class)
                    .withIdentity("task1")
                    .storeDurably() // 没有Trigger 关联时任务进行保留
                    .build();
        }

        @Bean
        public Trigger task1Trigger() {
            // 创建一个简单调度器
            SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(3)
                    .repeatForever();
            return TriggerBuilder.newTrigger()
                    .forJob(task1())
                    .withIdentity("task1Trigger")
                    .withSchedule(builder)
                    .build();
        }
    }

    public static class Demo2TaskConfiguration {

        @Bean
        public JobDetail task2() {
            return JobBuilder.newJob(DemoTask2.class)
                    .withIdentity("task2")
                    .storeDurably() // 没有Trigger 关联时任务进行保留
                    .build();
        }

        @Bean
        public Trigger task2Trigger() {
            // 创建一个简单调度器
            CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/3 * * * * ? *");

            return TriggerBuilder.newTrigger()
                    .forJob(task2())
                    .withIdentity("task2Trigger")
                    .withSchedule(builder)
                    .build();
        }
    }
}
