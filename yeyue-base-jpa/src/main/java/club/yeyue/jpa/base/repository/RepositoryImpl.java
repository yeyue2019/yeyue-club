package club.yeyue.jpa.base.repository;

import club.yeyue.jpa.base.entity.AbstractEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 自定义的repo实现方法: 可以自己指定
 *
 * @author fred
 * @date 2021-07-20 14:21
 */
public class RepositoryImpl<T extends AbstractEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements AbstractRepository<T, ID> {

//    private final EntityManager em;

    public RepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
//        this.em = entityManager;
    }
}

