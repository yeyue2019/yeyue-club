package club.yeyue.maven.mysql.jpa.repo;

import club.yeyue.maven.mysql.jpa.entity.AbstractJpaEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author fred
 * @date 2021-05-14 17:26
 */
public class JpaRepoImpl<T extends AbstractJpaEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements AbstractJpaRepo<T, ID> {

    private final EntityManager entityManager;

    public JpaRepoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager entityManager() {
        return this.entityManager;
    }
}
