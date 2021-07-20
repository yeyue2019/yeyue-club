package club.yeyue.mysql.base.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author fred
 * @date 2021-07-20 14:18
 */
@NoRepositoryBean
public interface AbstractJpaRepo<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 获取实体类管理器
     */
//    EntityManager entityManager();
}
