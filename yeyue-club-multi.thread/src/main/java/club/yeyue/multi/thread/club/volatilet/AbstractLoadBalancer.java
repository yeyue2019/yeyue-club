package club.yeyue.multi.thread.club.volatilet;

import java.util.Random;

/**
 * 负载均衡算法实例
 *
 * @author fred
 * @date 2021-08-23 17:52
 */
public abstract class AbstractLoadBalancer implements LoadBalancer {
    protected volatile Candidate candidate;// volatile变量替代锁
    protected final Random random;
    // 心跳线程
    protected Thread heartThread;

    // 实例化的方法
    public AbstractLoadBalancer(Candidate candidate) {
        if (null == candidate || 0 == candidate.size()) {
            throw new IllegalArgumentException("Invalid candidate " + candidate);
        }
        this.candidate = candidate;
        this.random = new Random();
    }

    // 更新
    @Override
    public void updateCandidate(Candidate candidate) {
        if (null == candidate || 0 == candidate.size()) {
            throw new IllegalArgumentException("Invalid candidate " + candidate);
        }
        this.candidate = candidate;
    }

    // 节点检测
    protected void monitorEndpoints() {
        // 读取volatile变量
        final Candidate currCandidate = candidate;
        // 检测下游部件状态是否正常
        for (EndPoint endpoint : currCandidate) {
            boolean isTheEndpointOnline = endpoint.ifOnline();
            if (doDetect(endpoint) != isTheEndpointOnline) {
                endpoint.setOnline(!isTheEndpointOnline);
            }
        }
    }

    // 随机模拟故障
    private boolean doDetect(EndPoint endpoint) {
        boolean online = true;
        // 模拟待测服务器随机故障
        int rand = random.nextInt(1000);
        if (rand <= 500) {
            online = false;
        }
        return online;
    }

    // 初始化
    public synchronized void init() throws Exception {
        if (null == heartThread) {
            Runnable runnable = () -> {
                try {
                    // 检测节点列表中所有节点是否在线
                    monitorEndpoints();
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {

                }
            };
            heartThread = new Thread(runnable, "heart_beat");
            heartThread.setDaemon(true);
            heartThread.start();
        }
    }
}
