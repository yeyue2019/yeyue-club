package club.yeyue.jpa.club.repository;

import club.yeyue.jpa.base.repository.AbstractRepository;
import club.yeyue.jpa.club.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fred
 * @date 2021-07-29 11:19
 */
public interface UserRepository extends AbstractRepository<UserEntity, Long> {

    @Query(value = "select u from UserEntity u where u.id = ?1 and u.deleted = false")
    UserEntity findOne(Long id);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update UserEntity u set u.username = ?2 where u.id = ?1 and u.deleted = false")
    int updateName(Long id, String name);// 使用update语句更新sql不会刷新修改时间

    List<UserEntity> findByUsername(String username);

    @Query(value = "select u from UserEntity u where u.username = :name and u.age = :age and u.deleted = false")
    List<UserEntity> findByNameAndAge(@Param("name") String param1, @Param("age") Integer param2);
}
