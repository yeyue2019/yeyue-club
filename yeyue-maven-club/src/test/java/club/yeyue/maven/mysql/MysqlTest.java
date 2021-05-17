package club.yeyue.maven.mysql;

import club.yeyue.maven.mysql.jpa.demo.*;
import club.yeyue.maven.util.JacksonUtils;
import club.yeyue.maven.util.SnowflakeIdUtils;
import club.yeyue.maven.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * @author fred
 * @date 2021-05-17 09:57
 */
@Slf4j
@SpringBootTest
@Import(SpringBeanUtils.class)
public class MysqlTest {

    @Test
    public void jpaTest() {
        DefaultLongRepo defaultLongRepo = SpringBeanUtils.getBean(DefaultLongRepo.class);
        DefaultStringRepo defaultStringRepo = SpringBeanUtils.getBean(DefaultStringRepo.class);
        SequenceLongRepo sequenceLongRepo = SpringBeanUtils.getBean(SequenceLongRepo.class);
        DefaultLongEntity longEntity = new DefaultLongEntity();
        longEntity.setAge(10);
        longEntity.setName("杨杰");
        defaultLongRepo.save(longEntity);
        List<DefaultLongEntity> longEntity1 = defaultLongRepo.findByName("杨杰");
        int update = defaultLongRepo.updateName(longEntity.getId(), "夜月");
        log.info("获取到实体:{},更新的数量:{}", JacksonUtils.toJsonStringFormat(longEntity1), update);
        DefaultStringEntity stringEntity = new DefaultStringEntity();
        stringEntity.setAge(20);
        stringEntity.setName("憨憨");
        defaultStringRepo.save(stringEntity);
        SequenceLongEntity sequenceLongEntity = new SequenceLongEntity();
        sequenceLongEntity.setId(SnowflakeIdUtils.generate());
        sequenceLongEntity.setName("菜菜");
        sequenceLongEntity.setAge(59);
        sequenceLongRepo.save(sequenceLongEntity);
    }
}
