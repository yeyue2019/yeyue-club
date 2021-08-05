package shardingsphere;

import club.yeyue.shardingsphere.club.ShardingSphereApplication;
import club.yeyue.shardingsphere.club.entity.Gender;
import club.yeyue.shardingsphere.club.entity.UserEntity;
import club.yeyue.shardingsphere.club.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fred
 * @date 2021-07-22 17:35
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShardingSphereApplication.class}
        // 需要看druid监控时打开
//        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ShardingSphereTest {

    @Resource
    UserMapper userMapper;

    public UserEntity getRandomUser() {
        UserEntity entity = new UserEntity()
                .setUsername("夜月" + RandomUtils.nextInt(1, 100))
                .setAge(RandomUtils.nextInt(1, 100))
                .setGender(Gender.男)
                .setGrade(BigDecimal.valueOf(RandomUtils.nextDouble(0.00, 100.00)).setScale(2, RoundingMode.HALF_DOWN))
                .setDescription(RandomUtils.nextBoolean() ? null : "自我备忘:" + System.currentTimeMillis());
        entity.setDeleted(false);
        return entity;
    }

    @Test
    public void insertTest() {
        UserEntity entity = new UserEntity()
                .setUsername("夜月2")
                .setAge(25)
                .setGender(Gender.男)
                .setGrade(new BigDecimal("100.355"))
                .setDescription("自我介绍");
        int num = userMapper.insert(entity);
        log.info("插入的主键Id:{}", entity.getId());
    }

    @Test
    public void insertForOtherTest() {
        for (int i = 0; i < 10; i++) {
            userMapper.insert(getRandomUser());
        }
    }

    @Test
    public void insertBatchSomeColumnTest() throws InterruptedException {
        List<UserEntity> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(getRandomUser());
        }
        int size = userMapper.insertBatchSomeColumn(list);
        log.info("打印结果:{}", list);
//        Thread.currentThread().join();
    }

    @Test
    public void updateByIdTest() {
        UserEntity entity = new UserEntity();
        entity.setId(1423105107620376577L)
                .setAge(11)
                .setGender(Gender.未知)
                .setUsername("憨憨");
        int size = userMapper.updateById(entity);
        log.info("updateByIdTest:更新数量:{}", size);
    }

    @Test
    public void updateV2Test() {
        UserEntity entity = new UserEntity();
        entity.setId(1423105107620376577L)
                .setAge(21)
                .setGender(Gender.女)
                .setUsername("憨憨2");
        int size = userMapper.updateV2(entity);
        log.info("updateV2Test:更新的数量:{}", size);
    }

    @Test
    public void deleteByIdTest() {
        int size = userMapper.deleteById(1423115264173576208L);
        log.info("deleteByIdTest:删除的数量:{}", size);
    }

    @Test
    public void deleteByIdV2Test() {
        int size = userMapper.deleteByIdV2(1423115264173576208L);
        log.info("deleteByIdV2Test:删除的数量:{}", size);
    }

    @Test
    public void selectByIdTest() {
        UserEntity entity = userMapper.selectById(1423115264181964818L);
        log.info("查询结果:{}", entity);
    }

    @Test
    public void queryByNameTest() {
        List<UserEntity> list = userMapper.queryByUsername("夜月9");
        log.info("查询结果:{}", list);
    }

    @Test
    public void queryByIdsTest() {
        IPage<UserEntity> page = new Page<>(1, 10);
        long start = 1423109035774709761L;
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ids.add(i + start);
        }
        page = userMapper.queryByIds(ids, page);
        log.info("查询结果:{}", page.getRecords());
    }

    @Test
    public void selectListTest() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.like("username", "夜月9%");
        List<UserEntity> list = userMapper.selectList(wrapper);
        log.info("查询结果:{}", list);
    }

    @Test
    public void selectPageTest() {
        IPage<UserEntity> page = new Page<>(1, 10);
        page = userMapper.selectPage(page, new QueryWrapper<>());
        log.info("查询结果:{}", page.getRecords());
    }
}
