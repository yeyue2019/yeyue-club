package club.yeyue.maven.mysql.mybatis.demo.mapper;

import club.yeyue.maven.mysql.mybatis.demo.entity.LongMyBatisEntity;
import club.yeyue.maven.mysql.mybatis.mapper.AbstractMyBatisMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 自定义实体mapper
 *
 * @author fred
 * @date 2021-05-14 18:10
 */
public interface LongMapper extends AbstractMyBatisMapper<LongMyBatisEntity> {


    @Select("select * from mybatis_long_entity where name = #{name}")
    List<LongMyBatisEntity> myQuery(@Param("name") String name);

    @Select("select * from mybatis_long_entity")
    IPage<LongMyBatisEntity> pageQuery(IPage<LongMyBatisEntity> page);
}
