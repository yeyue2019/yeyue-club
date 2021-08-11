package club.yeyue.elasticsearch.club.repository;

import club.yeyue.elasticsearch.club.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author fred
 * @date 2021-08-10 15:00
 */
public interface UserRepository extends ElasticsearchRepository<UserEntity, Long> {
}
