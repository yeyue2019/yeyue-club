package club.yeyue.maven.mysql;

import club.yeyue.maven.YeyueMavenClubApplication;
import club.yeyue.maven.model.GenderEnum;
import club.yeyue.maven.mysql.jpa.demo.entity.LongAutoEntity;
import club.yeyue.maven.mysql.jpa.demo.repo.LongAutoRepo;
import club.yeyue.maven.mysql.mybatis.demo.entity.LongMyBatisEntity;
import club.yeyue.maven.mysql.mybatis.demo.mapper.LongMapper;
import club.yeyue.maven.util.JacksonUtils;
import club.yeyue.maven.util.SpringBeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public void mybatisPlusQueryTest() {
        LongMapper mapper = SpringBeanUtils.getBean(LongMapper.class);
        QueryWrapper<LongMyBatisEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "憨憨");
        LongMyBatisEntity entity = mapper.selectOne(wrapper);
        log.info("查询结果:{}", JacksonUtils.toJsonString(entity));
    }

    @Test
    public void mybatisPlusListTest() {
        LongMapper mapper = SpringBeanUtils.getBean(LongMapper.class);
        List<LongMyBatisEntity> entity = mapper.myQuery("憨憨");
        log.info("查询结果:{}", JacksonUtils.toJsonString(entity));
    }

    @Test
    public void mybatisPlusPageTest() {
        IPage<LongMyBatisEntity> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 10);
        LongMapper mapper = SpringBeanUtils.getBean(LongMapper.class);
        page = mapper.selectPage(page, new QueryWrapper<>());
        log.info("查询结果:{}", JacksonUtils.toJsonString(page));
        page = mapper.pageQuery(page);
        log.info("查询结果:{}", JacksonUtils.toJsonString(page));
    }
}
