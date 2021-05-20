package club.yeyue.maven.mysql;

import club.yeyue.maven.YeyueMavenClubApplication;
import club.yeyue.maven.model.MyEnum;
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
    public void jpaAutoLongInsertTest() {
        LongAutoRepo repo = SpringBeanUtils.getBean(LongAutoRepo.class);
        LongAutoEntity entity = new LongAutoEntity();
        entity.setName("笨笨");
        entity.setMyEnum(MyEnum.C);
        entity.setAge(23);
        repo.save(entity);
    }

    @Test
    public void jpaAutoLongUpdateTest() {
        LongAutoRepo repo = SpringBeanUtils.getBean(LongAutoRepo.class);
        int num = repo.updateName(1L, "菜菜");
        log.info("更新数量:{}", num);
    }

    @Test
    public void jpaAutoLongQueryTest() {
        LongAutoRepo repo = SpringBeanUtils.getBean(LongAutoRepo.class);
        LongAutoEntity v1 = repo.findOne(1L);
        log.info("findOne查询结果:{}", JacksonUtils.toJsonString(v1));
        List<LongAutoEntity> v2 = repo.findByName("菜菜");
        log.info("findByName查询结果:{}", JacksonUtils.toJsonString(v2));
        List<LongAutoEntity> v3 = repo.findByNameAndAge("笨笨", 23);
        log.info("findByNameAndAge查询结果:{}", JacksonUtils.toJsonString(v3));
    }

    @Test
    public void jpaAutoLongSpeQueryTest() {
        LongAutoRepo repo = SpringBeanUtils.getBean(LongAutoRepo.class);
        Page<LongAutoEntity> list = repo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
//            predicates.add(cb.like(root.get("name"), "%" + "菜" + "%"));
//            predicates.add(cb.equal(root.get("age"), 20));
            predicates.add(cb.lessThan(root.get("created"), LocalDateTime.now()));
            // query.where()设置查询条件不需要再返回Predicate
            query.where(predicates.toArray(new Predicate[0]));
            query.orderBy(cb.asc(root.get("age")));
            return null;
        }, PageRequest.of(0, 10));
        log.info("分页查询结果:{}", JacksonUtils.toJsonString(list));
        EntityManager entityManager = repo.entityManager();
        //查询工厂
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // 查询条件
        CriteriaQuery<LongAutoEntity> query = cb.createQuery(LongAutoEntity.class);
        Root<LongAutoEntity> root = query.from(LongAutoEntity.class);
        List<Predicate> predicates = new LinkedList<>();
        predicates.add(cb.lessThan(root.get("created"), LocalDateTime.now()));
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("age")));
        TypedQuery<LongAutoEntity> result = entityManager.createQuery(query);
        log.info("使用原生查询工厂查询结果:{}", JacksonUtils.toJsonString(result.getResultList()));
    }

    @Test
    public void mybatisPlusInsertTest() {
        LongMapper mapper = SpringBeanUtils.getBean(LongMapper.class);
        LongMyBatisEntity entity = new LongMyBatisEntity();
        entity.setName("憨憨");
        entity.setAge(22);
        entity.setMyEnum(MyEnum.B);
        int num = mapper.insert(entity);
        assert num == 1;
    }

    @Test
    public void mybatisPlusUpdateTest() {
        LongMapper mapper = SpringBeanUtils.getBean(LongMapper.class);
        LongMyBatisEntity entity = new LongMyBatisEntity();
        entity.setId(1395249044414017538L);
        entity.setName("夜月");
        entity.setAge(12);
        entity.setMyEnum(MyEnum.A);
        int size = mapper.updateById(entity);
        assert size == 1;
    }

    @Test
    public void mybatisPlusDeleteTest() {
        LongMapper mapper = SpringBeanUtils.getBean(LongMapper.class);
        int size = mapper.deleteById(1395249044414017538L);
        assert size == 1;
    }

    @Test
    public void mybatisPlusGetTest() {
        LongMapper mapper = SpringBeanUtils.getBean(LongMapper.class);
        LongMyBatisEntity entity = mapper.selectById(1395248539419840513L);
        log.info("查询结果:{}", JacksonUtils.toJsonString(entity));
    }

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
