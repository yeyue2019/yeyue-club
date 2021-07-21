package mybatis;

import club.yeyue.mysql.YeyueMysqlClubApplication;
import club.yeyue.mysql.model.Gender;
import club.yeyue.mysql.mybatis.entity.UserMybatisEntity;
import club.yeyue.mysql.mybatis.mapper.UserMybatisMapper;
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
 * @date 2021-07-21 08:49
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {YeyueMysqlClubApplication.class}
        // 需要看druid监控时打开
//        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class MybatisEntityTest {

    @Resource
    UserMybatisMapper mapper;

    public static UserMybatisEntity getRandomUser() {
        UserMybatisEntity entity = new UserMybatisEntity()
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
        UserMybatisEntity entity = new UserMybatisEntity()
                .setUsername("夜月2")
                .setAge(25)
                .setGender(Gender.男)
                .setGrade(new BigDecimal("100.355"))
                .setDescription("自我介绍");
        int num = mapper.insert(entity);
        log.info("插入的主键Id:{}", entity.getId());
        assert num == 1;
    }

    @Test
    public void insertBatchSomeColumnTest() throws InterruptedException {
        List<UserMybatisEntity> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(getRandomUser());
        }
        int size = mapper.insertBatchSomeColumn(list);
        log.info("打印结果:{}", list);
//        Thread.currentThread().join();
    }

    @Test
    public void updateByIdTest() {
        UserMybatisEntity entity = new UserMybatisEntity();
        entity.setId(1_417_662_575_004_401_665L)
                .setAge(11)
                .setGender(Gender.未知)
                .setUsername("憨憨");
        int size = mapper.updateById(entity);
        assert size == 1;
    }

    @Test
    public void updateV2Test() {
        UserMybatisEntity entity = new UserMybatisEntity();
        entity.setId(1_417_662_575_004_401_665L)
                .setAge(11)
                .setGender(Gender.未知)
                .setUsername("憨憨");
        int size = mapper.updateV2(entity);
        assert size == 1;
    }

    @Test
    public void deleteByIdTest() {
        int size = mapper.deleteById(1_417_662_575_004_401_665L);
        assert size == 1;
    }

    @Test
    public void deleteByIdV2Test() {
        int size = mapper.deleteByIdV2(1_417_662_575_004_401_665L);
        assert size == 1;
    }

    @Test
    public void selectByIdTest() {
        UserMybatisEntity entity = mapper.selectById(1417729849497272322L);
        log.info("查询结果:{}", entity);
    }

    @Test
    public void queryByNameTest() {
        List<UserMybatisEntity> list = mapper.queryByUsername("夜月9");
        log.info("查询结果:{}", list);
    }

    @Test
    public void queryByIdsTest() {
        IPage<UserMybatisEntity> page = new Page<>(1, 10);
        long start = 1417729571687505921L;
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ids.add(i + start);
        }
        page = mapper.queryByIds(ids, page);
        log.info("查询结果:{}", page.getRecords());
    }

    @Test
    public void selectListTest() {
        QueryWrapper<UserMybatisEntity> wrapper = new QueryWrapper<>();
        wrapper.like("username", "夜月9%");
        List<UserMybatisEntity> list = mapper.selectList(wrapper);
        log.info("查询结果:{}", list);
    }

    @Test
    public void selectPageTest() {
        IPage<UserMybatisEntity> page = new Page<>(1, 10);
        page = mapper.selectPage(page, new QueryWrapper<>());
        log.info("查询结果:{}", page.getRecords());
    }
}
