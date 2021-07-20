package club.yeyue.mysql.jpa.repo;

import club.yeyue.mysql.base.jpa.repo.AbstractJpaRepo;
import club.yeyue.mysql.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fred
 * @date 2021-07-20 14:37
 */
public interface UserJpaRepo extends AbstractJpaRepo<UserJpaEntity, Long> {

    @Query(value = "select l from UserJpaEntity l where l.id = ?1 and l.deleted = false")
    UserJpaEntity findOne(Long id);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserJpaEntity l set l.username = ?2 where l.id = ?1 and l.deleted = false")
    int updateName(Long id, String name);// 使用update语句更新sql不会刷新修改时间

    List<UserJpaEntity> findByUsername(String username);

    @Query(value = "select l from UserJpaEntity l where l.username = :name and l.age = :age and l.deleted = false")
    List<UserJpaEntity> findByNameAndAge(@Param("name") String param1, @Param("age") Integer param2);
}
