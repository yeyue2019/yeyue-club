package club.yeyue.baomidou.club.mapper;

import club.yeyue.baomidou.club.constant.Constant;
import club.yeyue.baomidou.club.entity.OrderEntity;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;

/**
 * @author fred
 * @date 2021-08-04 16:00
 */
@DS(Constant.DATASOURCE_TWO)
public interface OrderMapper {

    OrderEntity selectById(@Param("id") Integer id);
}
