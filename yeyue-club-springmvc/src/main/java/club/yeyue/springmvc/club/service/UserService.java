package club.yeyue.springmvc.club.service;

import club.yeyue.springmvc.club.domain.UserDomain;
import club.yeyue.springmvc.club.entity.UserEntity;
import club.yeyue.springmvc.club.entity.convert.UserConvert;
import club.yeyue.springmvc.club.mapper.UserMapper;
import club.yeyue.springmvc.club.model.CommonPageResult;
import club.yeyue.springmvc.club.model.CommonPageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-08-27 17:51
 */
@Service
@Slf4j
public class UserService {

    @Resource
    UserMapper mapper;

    public Long insert(UserDomain domain) {
        UserEntity entity = UserConvert.toUserEntity(domain);
        mapper.insert(entity);
        return entity.getId();
    }

    public UserDomain get(Long id) {
        UserEntity entity = mapper.selectById(id);
        return UserConvert.toUserDomain(entity);
    }

    public void modify(UserDomain domain) {
        UserEntity entity = UserConvert.toUserEntity(domain);
        mapper.updateById(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public CommonPageResult<UserDomain> select(CommonPageable pageable) {
        return CommonPageResult.of(mapper.selectPage(pageable.page(), pageable.wrapper()), UserConvert::toUserDomain);
    }

}
