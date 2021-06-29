package club.yeyue.maven.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 雪花算法Id获取
 *
 * @author fred
 * @date 2021-05-14 10:09
 */
@Slf4j
public class SnowflakeIdUtils {

    private SnowflakeIdUtils() {
    }

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private static final long TWEPOCH = 1288834974657L;

    /**
     * 并发控制
     */
    private static Long SEQUENCE = 0L;

    /**
     * 时间戳
     */
    private static Long LAST_TIME_STAMP = -1L;

    /**
     * 获取下一个Id
     *
     * @param datacenterId 数据中心编码 0-31
     * @param workerId     机器编码 0-31
     * @return 结果
     */
    public static synchronized long generate(long datacenterId, long workerId) {
        datacenterId = datacenterId % 32;
        workerId = workerId % 32;
        long timestamp = System.currentTimeMillis();
        if (timestamp < LAST_TIME_STAMP) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", LAST_TIME_STAMP - timestamp));
        }
        if (LAST_TIME_STAMP == timestamp) {
            // 当前毫秒内，则+1
            SEQUENCE = ((SEQUENCE + 1L) & 4095);
            if (SEQUENCE == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                do {
                    timestamp = System.currentTimeMillis();
                } while (timestamp <= LAST_TIME_STAMP);
            }
        } else {
            SEQUENCE = 0L;
        }
        LAST_TIME_STAMP = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        return ((timestamp - TWEPOCH) << 22)
                | (datacenterId << 17)
                | (workerId << 12) | SEQUENCE;
    }

}
