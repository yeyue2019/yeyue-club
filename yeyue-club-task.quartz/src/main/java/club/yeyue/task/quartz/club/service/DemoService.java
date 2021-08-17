package club.yeyue.task.quartz.club.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author fred
 * @date 2021-08-17 10:30
 */
@Slf4j
@Service
public class DemoService {

    public void execute() throws InterruptedException {
        Thread.sleep(5000L);
    }
}
