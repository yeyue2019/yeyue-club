package club.yeyue.springmvc.club.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author fred
 * @date 2021-08-30 10:49
 */
@Data
@Accessors
public class CommonPageResult<T> implements Serializable {
    private static final long serialVersionUID = 7613168025749272524L;

    // 当前页数
    private Long current;

    // 分页查询数据量
    private Long size;

    // 符合条件的总数量
    private Long total;

    // 查询结果
    private List<T> list = Collections.emptyList();

    private CommonPageResult() {
    }

    public static <T, E> CommonPageResult<T> of(IPage<E> source, Function<E, T> function) {
        CommonPageResult<T> result = new CommonPageResult<>();
        result.setCurrent(source.getCurrent());
        result.setSize(source.getSize());
        result.setTotal(source.getTotal());
        List<E> records = source.getRecords();
        if (!records.isEmpty()) {
            List<T> target = new ArrayList<>(records.size());
            for (E record : records) {
                target.add(function.apply(record));
            }
            result.setList(target);
        }
        return result;
    }
}
