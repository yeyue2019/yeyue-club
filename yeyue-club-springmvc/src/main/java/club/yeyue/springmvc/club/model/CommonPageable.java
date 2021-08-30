package club.yeyue.springmvc.club.model;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询条件
 *
 * @author fred
 * @date 2021-08-30 10:03
 */
@Data
public class CommonPageable implements Serializable {
    private static final long serialVersionUID = -8763976379925676278L;

    // 当前查询页数
    protected Long current;

    // 当前页数据量
    protected Long size;

    // 指定排序字段信息
    protected List<SortItem> sortItems = new ArrayList<>(0);

    @Data
    public static class SortItem implements Serializable {
        private static final long serialVersionUID = 80787023329989994L;

        protected String column;

        protected boolean asc;
    }

    public <T> IPage<T> page() {
        if (current == null) {
            current = 1L;
        }
        if (size == null) {
            size = 20L;
        }
        Page<T> result = new Page<>(current, size);
        if (sortItems != null && !sortItems.isEmpty()) {
            List<OrderItem> orderItems = new ArrayList<>();
            for (SortItem sortItem : sortItems) {
                orderItems.add(new OrderItem(sortItem.getColumn(), sortItem.isAsc()));
            }
            result.addOrder(orderItems);
        }
        return result;
    }

    public <T> Wrapper<T> wrapper() {
        return new QueryWrapper<>();
    }
}
