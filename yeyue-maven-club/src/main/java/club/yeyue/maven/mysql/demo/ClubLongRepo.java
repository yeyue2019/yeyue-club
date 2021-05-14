package club.yeyue.maven.mysql.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fred
 * @date 2021-05-13 16:37
 */
public interface ClubLongRepo extends JpaRepository<ClubDefaultLongEntity, Long>, JpaSpecificationExecutor<ClubDefaultLongEntity> {

    @Query(value = "select e from ClubDefaultLongEntity e where e.clubName = :clubName and e.deleted = false")
    List<ClubDefaultLongEntity> findByName(@Param("clubName") String clubName);

    @Query(value = "select e from ClubDefaultLongEntity e where e.clubName = ?1 and e.deleted = false")
    List<ClubDefaultLongEntity> findByName2(String clubName);

    @Modifying
    @Transactional
    @Query(value = "update ClubDefaultLongEntity e set e.clubName =?2 where e.id = ?1 and e.deleted = false")
    int update(Long id, String clubName);
}
