package club.yeyue.task.xxljob.club.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fred
 * @date 2021-08-17 15:14
 */
@Configuration
public class XxlJobConfiguration {

    // TODO: 2021/8/18 目前没有流行的starter版本,可以写成自定义配置,这里为了测试简单没有写
    @Bean
    public XxlJobSpringExecutor executor() {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setAdminAddresses("http://127.0.0.1:8090/xxl-job-admin");
        executor.setAppname("yeyue-task-application");
        executor.setIp("127.0.0.1");
        executor.setPort(9999);
        executor.setLogRetentionDays(3);
        executor.setLogPath("");
        return executor;
    }
}
