package club.yeyue.maven.mysql.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author fred
 * @date 2021-05-14 17:19
 */
@NoRepositoryBean
public interface JpaRepo<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 获取实体类管理器
     */
    EntityManager entityManager();
}

