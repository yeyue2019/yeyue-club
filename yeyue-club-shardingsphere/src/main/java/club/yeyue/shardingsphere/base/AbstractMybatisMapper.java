package club.yeyue.shardingsphere.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * @author fred
 * @date 2021-07-20 17:56
 */
public interface AbstractMybatisMapper<T> extends BaseMapper<T> {

    /**
     * 批量保存 批量保存有很多限制条件 参见类说明
     */
    int insertBatchSomeColumn(Collection<T> entities);
}
