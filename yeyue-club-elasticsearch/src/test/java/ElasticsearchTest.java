import club.yeyue.elasticsearch.club.ElasticsearchApplication;
import club.yeyue.elasticsearch.club.entity.UserEntity;
import club.yeyue.elasticsearch.club.repository.UserRepository;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author fred
 * @date 2021-08-10 15:36
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ElasticsearchApplication.class}
        // 需要看druid监控时打开
//        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ElasticsearchTest {

    @Resource
    UserRepository repository;

    @Resource
    ElasticsearchRestTemplate template;

    public UserEntity getRandom(long start) {
        return new UserEntity()
                .setId(start)
                .setUsername("夜月")
                .setAge(RandomUtils.nextInt())
                .setGender(RandomStringUtils.random(1, '男', '女'))
                .setBirthday(LocalDate.of(1996, 10, 16))
                .setGrade(BigDecimal.valueOf(135.60))
                .setDescription("愿半生编码，如一生老友");
    }


    @Test
    public void insertTest() {
        UserEntity entity = new UserEntity()
                .setId(2L)
                .setUsername("夜月")
                .setAge(25)
                .setGender("男")
                .setBirthday(LocalDate.of(1996, 10, 16))
                .setGrade(BigDecimal.valueOf(135.60))
                .setDescription("愿半生编码，如一生老友");
        repository.save(entity);
    }

    @Test
    public void saveAllTest() {
        List<UserEntity> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(getRandom(i + 10));
        }
        repository.saveAll(list);
    }

    @Test
    public void updateTest() {
        UserEntity entity = new UserEntity()
                .setId(1L)
                .setUsername("夜月")
                .setAge(25)
                .setGender("未知")
                .setBirthday(LocalDate.of(1996, 10, 16))
                .setGrade(BigDecimal.valueOf(135.60))
                .setDescription("愿半生编码，如一生老友");
        repository.save(entity);
    }

    @Test
    public void deleteTest() {
        repository.deleteById(1L);
    }

    @Test
    public void testSelectById() {
        Optional<UserEntity> optional = repository.findById(2L);
        log.info("testSelectById:{}", JSON.toJSONString(optional.get()));
    }

    @Test
    public void searchTest() {
        // 创建 ES 搜索条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("username", "夜月"))
//                .withQuery(QueryBuilders.rangeQuery("age").from(10).to(1000))
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                .withPageable(PageRequest.of(0, 10));
        NativeSearchQuery query = queryBuilder.build();
        SearchHits<UserEntity> search = template.search(query, UserEntity.class, IndexCoordinates.of("user"));
        log.info("searchTest:{}", JSON.toJSONString(search));
    }


}
