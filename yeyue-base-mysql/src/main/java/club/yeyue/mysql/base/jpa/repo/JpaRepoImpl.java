package club.yeyue.mysql.base.jpa.repo;

import club.yeyue.mysql.base.jpa.entity.AbstractJpaEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 自己去实现Repo
 *
 * @author fred
 * @date 2021-07-20 14:21
 */
public class JpaRepoImpl<T extends AbstractJpaEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements AbstractJpaRepo<T, ID> {

//    private final EntityManager em;

    public JpaRepoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
//        this.em = entityManager;
    }
}

