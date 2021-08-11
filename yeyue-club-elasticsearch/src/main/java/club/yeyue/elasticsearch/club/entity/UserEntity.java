package club.yeyue.elasticsearch.club.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author fred
 * @date 2021-08-10 13:56
 */
@Data
@Accessors(chain = true)
@Document(
        indexName = "user"
)
@Setting(
        shards = 1 //  索引的分片数
        , replicas = 0 // 分区的备份数
        , refreshInterval = "-1" // 索引刷新时间间隔
)
public class UserEntity {

    /**
     * 主键
     */
    @Id
    private Long id;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String username;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String gender;

    private Integer age;

    private BigDecimal grade;

    private String description;

    @Field(type = FieldType.Date, format = {DateFormat.date})
    private LocalDate birthday;
}
