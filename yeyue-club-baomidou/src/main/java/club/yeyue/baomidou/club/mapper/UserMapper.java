package club.yeyue.baomidou.club.mapper;

import club.yeyue.baomidou.club.constant.Constant;
import club.yeyue.baomidou.club.entity.UserEntity;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;

/**
 * @author fred
 * @date 2021-08-04 15:59
 */
@DS(Constant.DATASOURCE_ONE)
public interface UserMapper {

    UserEntity selectById(@Param("id") Integer id);
}
