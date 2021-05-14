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
public interface ClubStringRepo extends JpaRepository<ClubStringEntity, String>, JpaSpecificationExecutor<ClubStringEntity> {

    @Query(value = "select e from ClubStringEntity e where e.clubName = :clubName and e.deleted = false")
    List<ClubStringEntity> findByName(@Param("clubName") String clubName);

    @Query(value = "select e from ClubStringEntity e where e.clubName = ?1 and e.deleted = false")
    List<ClubStringEntity> findByName2(String clubName);

    @Modifying
    @Transactional
    @Query(value = "update ClubStringEntity e set e.clubName =?2 where e.id = ?1 and e.deleted = false")
    int update(String id, String clubName);
}
