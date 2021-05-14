package club.yeyue.maven.mysql.jpa.demo;

import club.yeyue.maven.mysql.jpa.repo.JpaRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fred
 * @date 2021-05-14 17:35
 */
public interface DefaultLongRepo extends JpaRepo<DefaultLongEntity, Long> {

    @Query(value = "select e from DefaultLongEntity e where e.name = :name")
    List<DefaultLongEntity> queryByName(@Param("name") String name);

    @Query(value = "select e from DefaultLongEntity e where e.name = ?1")
    List<DefaultLongEntity> findByName(String name);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update DefaultLongEntity e set e.name = :name where e.id = :id")
    int updateName(@Param("id") Long id, @Param("name") String name);
}
