package club.yeyue.springmvc.club.entity.convert;

import club.yeyue.springmvc.club.domain.UserDomain;
import club.yeyue.springmvc.club.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fred
 * @date 2021-08-27 17:50
 */
public class UserConvert {


    private UserConvert() {
        // 无需实现
    }

    public static List<UserDomain> toUserDomainList(List<UserEntity> userEntityList) {
        if (userEntityList == null) {
            return Collections.emptyList();
        }
        List<UserDomain> userDomainList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userDomainList.add(toUserDomain(userEntity));
        }
        return userDomainList;
    }

    public static UserDomain toUserDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDomain userDomain = new UserDomain();
        userDomain.setId(userEntity.getId());
        userDomain.setUsername(userEntity.getUsername());
        userDomain.setGender(userEntity.getGender());
        userDomain.setAge(userEntity.getAge());
        userDomain.setGrade(userEntity.getGrade());
        userDomain.setDescription(userEntity.getDescription());
        userDomain.setCreated(userEntity.getCreated());
        userDomain.setUpdated(userEntity.getUpdated());
// Not mapped FROM fields:
// deleted
        return userDomain;
    }

    public static List<UserEntity> toUserEntityList(List<UserDomain> userDomainList) {
        if (userDomainList == null) {
            return Collections.emptyList();
        }
        List<UserEntity> userEntityList = new ArrayList<>();
        for (UserDomain userDomain : userDomainList) {
            userEntityList.add(toUserEntity(userDomain));
        }
        return userEntityList;
    }

    public static UserEntity toUserEntity(UserDomain userDomain) {
        if (userDomain == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDomain.getId());
        userEntity.setUsername(userDomain.getUsername());
        userEntity.setGender(userDomain.getGender());
        userEntity.setAge(userDomain.getAge());
        userEntity.setGrade(userDomain.getGrade());
        userEntity.setDescription(userDomain.getDescription());
        userEntity.setCreated(userDomain.getCreated());
        userEntity.setUpdated(userDomain.getUpdated());
// Not mapped TO fields:
// deleted
        return userEntity;
    }
}
