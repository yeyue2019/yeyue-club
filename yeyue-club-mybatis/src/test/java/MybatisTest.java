import club.yeyue.mybatis.club.MybatisApplication;
import club.yeyue.mybatis.club.entity.UserEntity;
import club.yeyue.mybatis.club.mapper.UserMapper;
import club.yeyue.mybatis.club.model.Gender;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fred
 * @date 2021-08-01 22:21
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MybatisApplication.class}
        // 需要看druid监控时打开
//        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class MybatisTest {

    @Resource
    UserMapper mapper;

    public static UserEntity getRandomUser() {
        UserEntity entity = new UserEntity()
                .setUsername("夜月" + RandomUtils.nextInt(1, 100))
                .setAge(RandomUtils.nextInt(1, 100))
                .setGender(Gender.男)
                .setGrade(new BigDecimal(RandomUtils.nextDouble(0.00, 100.00)))
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
        int num = mapper.insert(entity);
        log.info("插入的主键Id:{}", entity.getId());
    }

    @Test
    public void insertBatchSomeColumnTest() throws InterruptedException {
        List<UserEntity> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(getRandomUser());
        }
        int size = mapper.insertBatchSomeColumn(list);
        log.info("打印结果:{}", list);
//        Thread.currentThread().join();
    }

    @Test
    public void updateByIdTest() {
        UserEntity entity = new UserEntity();
        entity.setId(1421841022828183553L)
                .setAge(11)
                .setGender(Gender.未知)
                .setUsername("憨憨");
        int size = mapper.updateById(entity);
        log.info("updateByIdTest:更新数量:{}", size);
    }

    @Test
    public void updateV2Test() {
        UserEntity entity = new UserEntity();
        entity.setId(1421841022828183553L)
                .setAge(21)
                .setGender(Gender.女)
                .setUsername("憨憨");
        int size = mapper.updateV2(entity);
        log.info("updateV2Test:更新的数量:{}", size);
    }

    @Test
    public void deleteByIdTest() {
        int size = mapper.deleteById(1421841156412571650L);
        log.info("deleteByIdTest:删除的数量:{}", size);
    }

    @Test
    public void deleteByIdV2Test() {
        int size = mapper.deleteByIdV2(1421841156412571650L);
        log.info("deleteByIdV2Test:删除的数量:{}", size);
    }

    @Test
    public void selectByIdTest() {
        UserEntity entity = mapper.selectById(1421841156425154564L);
        log.info("查询结果:{}", entity);
    }

    @Test
    public void queryByNameTest() {
        List<UserEntity> list = mapper.queryByUsername("夜月9");
        log.info("查询结果:{}", list);
    }

    @Test
    public void queryByIdsTest() {
        IPage<UserEntity> page = new Page<>(1, 10);
        long start = 1421841156425154565L;
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ids.add(i + start);
        }
        page = mapper.queryByIds(ids, page);
        log.info("查询结果:{}", page.getRecords());
    }

    @Test
    public void selectListTest() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.like("username", "夜月9%");
        List<UserEntity> list = mapper.selectList(wrapper);
        log.info("查询结果:{}", list);
    }

    @Test
    public void selectPageTest() {
        IPage<UserEntity> page = new Page<>(1, 10);
        page = mapper.selectPage(page, new QueryWrapper<>());
        log.info("查询结果:{}", page.getRecords());
    }
}
