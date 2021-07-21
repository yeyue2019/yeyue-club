package club.yeyue.mysql.mybatis.mapper;

import club.yeyue.mysql.base.mybatis.mapper.AbstractMybatisMapper;
import club.yeyue.mysql.mybatis.entity.UserMybatisEntity;
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
public interface UserMybatisMapper extends AbstractMybatisMapper<UserMybatisEntity> {

    // MyBatis 和 MyBatis-Plus的区别

    // insert 无法填充类似雪花算法主键
    //@Insert

    // 使用自己写的sql不会更新刷新时间
    @Update("update user_mybatis_entity set username = #{username}, age = #{age}, gender = #{gender} where id = #{id}")
    int updateV2(UserMybatisEntity entity);

    @Delete("delete from user_mybatis_entity where id = #{id}")
    int deleteByIdV2(@Param("id") Long id);

    @Select("select * from user_mybatis_entity where username = #{username}")
    List<UserMybatisEntity> queryByUsername(@Param("username") String username);

    @Select(value = {"<script>",
            "select * from user_mybatis_entity where id in",
            "<foreach item = 'id' collection = 'ids' separator = ',' open= '(' close = ')' index = ''>",
            "#{id}",
            "</foreach>",
            "</script>"})
    IPage<UserMybatisEntity> queryByIds(@Param("ids") Collection<Long> ids, IPage<UserMybatisEntity> page);


}
