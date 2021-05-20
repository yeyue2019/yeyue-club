package club.yeyue.maven.mysql.jpa.demo.repo;

import club.yeyue.maven.mysql.jpa.demo.entity.LongAutoEntity;
import club.yeyue.maven.mysql.jpa.repo.AbstractJpaRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自定义实体对应的repo
 *
 * @author fred
 * @date 2021-05-19 09:01
 */
public interface LongAutoRepo extends AbstractJpaRepo<LongAutoEntity, Long> {

    @Query(value = "select l from LongAutoEntity l where l.id = ?1 and l.deleted = false")
    LongAutoEntity findOne(Long id);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update LongAutoEntity l set l.name = ?2 where l.id = ?1 and l.deleted = false")
    int updateName(Long id, String name);

    List<LongAutoEntity> findByName(String name);

    @Query(value = "select l from LongAutoEntity l where l.name = :name and l.age = :age")
    List<LongAutoEntity> findByNameAndAge(@Param("name") String param1, @Param("age") Integer param2);


}
