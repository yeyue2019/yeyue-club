package jpa;

import club.yeyue.mysql.YeyueMysqlClubApplication;
import club.yeyue.mysql.jpa.entity.StudentJpaEntity;
import club.yeyue.mysql.jpa.entity.UserJpaEntity;
import club.yeyue.mysql.jpa.repo.StudentJpaRepo;
import club.yeyue.mysql.jpa.repo.UserJpaRepo;
import club.yeyue.mysql.model.Gender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fred
 * @date 2021-07-20 15:19
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {YeyueMysqlClubApplication.class}
        // 需要看druid监控时打开
//        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class JpaEntityTest {

    @Resource
    UserJpaRepo userJpaRepo;
    @Resource
    StudentJpaRepo studentJpaRepo;
    @PersistenceContext
    EntityManager entityManager;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static UserJpaEntity getRandomUser() {
        UserJpaEntity entity = new UserJpaEntity()
                .setUsername("夜月" + atomicInteger.incrementAndGet())
                .setGender(Gender.男)
                .setAge(RandomUtils.nextInt(10, 50))
                .setDescription("这是我的个人独白")
                .setGrade(new BigDecimal(RandomUtils.nextDouble(1.00, 100.00)));
        return entity;
    }

    public static StudentJpaEntity getRandomStudent() {
        StudentJpaEntity entity = new StudentJpaEntity()
                .setUsername("夜月" + atomicInteger.incrementAndGet());
        return entity;
    }

    @Test
    public void insertTest() {
        UserJpaEntity entity = new UserJpaEntity()
                .setUsername("夜月")
                .setGender(Gender.男)
                .setAge(25)
                .setDescription("这是我的个人独白")
                .setGrade(new BigDecimal("100.305"));
        userJpaRepo.save(entity);
    }

    @Test
    public void updateTest() {
        userJpaRepo.updateName(1L, "夜月update");
    }

    // TODO: 2021/7/20 未解决的问题：mysql自增主键批量操作无效
    @Test
    public void batchInsertTest() throws InterruptedException {
        List<StudentJpaEntity> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            userList.add(getRandomStudent());
        }
        studentJpaRepo.saveAll(userList);
//        Thread.currentThread().join();
    }

    @Test
    public void baseQueryTest() {
        UserJpaEntity v1 = userJpaRepo.findOne(1L);
        log.info("findOne:{}", v1);
        List<UserJpaEntity> v2 = userJpaRepo.findByUsername("夜月2");
        log.info("findByUsername:{}", v2);
        List<UserJpaEntity> v3 = userJpaRepo.findByNameAndAge("夜月3", 12);
        log.info("findByNameAndAge:{}", v3);
    }

    @Test
    public void specificationTest() {
        Page<UserJpaEntity> list = userJpaRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("username"), "夜月1"));
            predicates.add(cb.greaterThan(root.get("age"), 1));
            predicates.add(cb.lessThan(root.get("created"), LocalDateTime.now()));
            // query.where()设置查询条件不需要再返回Predicate
            query.where(predicates.toArray(new Predicate[0]));
            query.orderBy(cb.asc(root.get("age")));
            return null;
        }, PageRequest.of(0, 10));
        log.info("findAll:{}", list.toList());
    }

    @Test
    public void entityManagerTest() {
        // 查询工厂
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // 查询条件
        CriteriaQuery<UserJpaEntity> query = cb.createQuery(UserJpaEntity.class);
        Root<UserJpaEntity> root = query.from(UserJpaEntity.class);
        List<Predicate> predicates = new LinkedList<>();
        predicates.add(cb.lessThan(root.get("created"), LocalDateTime.now()));
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("age")));
        TypedQuery<UserJpaEntity> result = entityManager.createQuery(query);
        log.info("entityManager:{}", result.getResultList());
    }
}
