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

    public SnowflakeIdUtils(long datacenterId, long workerId) {
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private static final long TWEPOCH = 1288834974657L;
    /**
     * 机器标识位数
     */
    private static final long WORKER_ID_BITS = 5L;
    /**
     * 数据中心标识位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;
    /**
     * 机器ID最大值
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /**
     * 数据中心ID最大值
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    /**
     * 毫秒内自增位
     */
    public static final long SEQUENCE_BITS = 12L;
    /**
     * 机器ID偏左移12位
     */
    public static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据中心ID左移17位
     */
    public static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 时间毫秒左移22位
     */
    public static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    /**
     * 自增位最大值
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 上次生产Id的时间戳
     */
    private long lastTimeStamp = -1L;
    /**
     * 并发控制
     */
    private long sequence = 0L;

    /**
     * 工作机器0～31
     */
    private long datacenterId;
    /**
     * 数据中心Id0～31
     */
    private long workerId;

    private static final SnowflakeIdUtils INSTANCE;

    static {
        INSTANCE = new SnowflakeIdUtils();
        INSTANCE.datacenterId = 0L;
        INSTANCE.workerId = 0L;
    }


    /**
     * 获取下一个Id
     *
     * @return 结果
     */
    private synchronized long nextId() {
        datacenterId = datacenterId % (MAX_DATA_CENTER_ID + 1);
        workerId = workerId % (MAX_WORKER_ID + 1);
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimeStamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimeStamp - timestamp));
        }
        if (lastTimeStamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                do {
                    timestamp = System.currentTimeMillis();
                } while (timestamp <= lastTimeStamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimeStamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        return ((timestamp - TWEPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    public static long generate() {
        return INSTANCE.nextId();
    }

    public static long generate(SnowflakeIdUtils instance) {
        return instance.nextId();
    }
}
