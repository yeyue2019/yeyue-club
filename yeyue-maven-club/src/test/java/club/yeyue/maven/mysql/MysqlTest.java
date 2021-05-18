package club.yeyue.maven.mysql;

import club.yeyue.maven.YeyueMavenClubApplication;
import club.yeyue.maven.mysql.jpa.demo.*;
import club.yeyue.maven.mysql.mybatis.demo.entity.DefaultMyBatisEntity;
import club.yeyue.maven.mysql.mybatis.demo.entity.DefaultMyBatisMapper;
import club.yeyue.maven.util.JacksonUtils;
import club.yeyue.maven.util.SnowflakeIdUtils;
import club.yeyue.maven.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author fred
 * @date 2021-05-17 09:57
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {YeyueMavenClubApplication.class})
@Import(SpringBeanUtils.class)
public class MysqlTest {

    @Test
    public void jpaTest() {
        DefaultLongRepo defaultLongRepo = SpringBeanUtils.getBean(DefaultLongRepo.class);
        DefaultStringRepo defaultStringRepo = SpringBeanUtils.getBean(DefaultStringRepo.class);
        SequenceLongRepo sequenceLongRepo = SpringBeanUtils.getBean(SequenceLongRepo.class);
        DefaultLongEntity longEntity = new DefaultLongEntity();
        longEntity.setAge(10);
        longEntity.setName("夜月");
        defaultLongRepo.save(longEntity);
        List<DefaultLongEntity> longEntity1 = defaultLongRepo.findByName("夜月");
        int update = defaultLongRepo.updateName(longEntity.getId(), "夜月2");
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

    @Autowired
    private DefaultMyBatisMapper mapper;

    @Test
    public void mybatisPlusTest() {
        DefaultMyBatisEntity entity = new DefaultMyBatisEntity();
        entity.setId(SnowflakeIdUtils.generate());
        entity.setName("夜月");
        entity.setAge(22);
        entity.setCreated(LocalDateTime.now());
        entity.setUpdated(LocalDateTime.now());
        entity.setDeleted(Boolean.FALSE);
        mapper.insert(entity);
    }
}
