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
 * @date 2021-05-14 09:31
 */
public interface ClubStringRepo extends JpaRepository<ClubDefaultStringEntity, String>, JpaSpecificationExecutor<ClubDefaultStringEntity> {

    @Query(value = "select e from ClubDefaultStringEntity e where e.clubName = :clubName and e.deleted = false")
    List<ClubDefaultStringEntity> findByName(@Param("clubName") String clubName);

    @Query(value = "select e from ClubDefaultStringEntity e where e.clubName = ?1 and e.deleted = false")
    List<ClubDefaultStringEntity> findByName2(String clubName);

    @Modifying
    @Transactional
    @Query(value = "update ClubDefaultStringEntity e set e.clubName =?2 where e.id = ?1 and e.deleted = false")
    int update(String id, String clubName);
}
