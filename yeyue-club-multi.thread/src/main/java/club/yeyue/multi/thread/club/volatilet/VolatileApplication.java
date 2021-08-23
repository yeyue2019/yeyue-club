package club.yeyue.multi.thread.club.volatilet;

import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fred
 * @date 2021-08-24 00:38
 */
public class VolatileApplication {

    // 加载下游节点
    private Set<EndPoint> loadEndpoints() {
        Set<EndPoint> endpoints = new HashSet<EndPoint>();
        // 模拟从数据库加载以下信息
        endpoints.add(new EndPoint("192.168.101.100", 8080, 3));
        endpoints.add(new EndPoint("192.168.101.101", 8080, 2));
        endpoints.add(new EndPoint("192.168.101.102", 8080, 5));
        endpoints.add(new EndPoint("192.168.101.103", 8080, 7));
        return endpoints;
    }

    // 获取加权轮询负载均衡实例
    private LoadBalancer loadBalancer() throws Exception {
        return WeightedRoundRobinLoadBalancer.getInstance(new Candidate(loadEndpoints()));
    }

    public static void main(String[] args) throws Exception {
        VolatileApplication application = new VolatileApplication();
        // 创建系统调度实例并且设置均衡算法
        ServiceInvoker invoker = ServiceInvoker.getInstance();
        invoker.setLoadBalancer(application.loadBalancer());
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new RequestSender().start();
        }
    }

    // 具体的请求
    static class RequestSender extends Thread {
        private static long id = -1;

        static synchronized long nextId() {
            return ++id;
        }

        @SneakyThrows
        @Override
        public void run() {
            ServiceInvoker rd = ServiceInvoker.getInstance();
            for (int i = 0; i < 100; i++) {
                rd.dispatchRequest(new Request(nextId(), 1));
                Thread.sleep(100);
            }
        }
    }
}
