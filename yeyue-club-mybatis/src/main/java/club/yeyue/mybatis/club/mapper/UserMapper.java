package club.yeyue.mybatis.club.mapper;

import club.yeyue.mybatis.base.mapper.AbstractMapper;
import club.yeyue.mybatis.club.entity.UserEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;
import java.util.List;

/**
 * @author fred
 * @date 2021-07-21 08:46
 */
public interface UserMapper extends AbstractMapper<UserEntity> {

    // MyBatis 和 MyBatis-Plus的区别

    // insert 无法填充类似雪花算法主键
    //@Insert

    // 使用自己写的sql不会更新刷新时间
    @Update("update club_mybatis_user set username = #{username}, age = #{age}, gender = #{gender}, updated = now() where id = #{id}")
    int updateV2(UserEntity entity);

    @Delete("delete from club_mybatis_user where id = #{id}")
    int deleteByIdV2(@Param("id") Long id);

    @Select("select * from club_mybatis_user where username = #{username}")
    List<UserEntity> queryByUsername(@Param("username") String username);

    @Select(value = {"<script>",
            "select * from club_mybatis_user where id in",
            "<foreach item = 'id' collection = 'ids' separator = ',' open= '(' close = ')' index = ''>",
            "#{id}",
            "</foreach>",
            "</script>"})
    IPage<UserEntity> queryByIds(@Param("ids") Collection<Long> ids, IPage<UserEntity> page);


}
