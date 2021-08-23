package club.yeyue.multi.thread.club.volatilet;

/**
 * 系统调度
 *
 * @author fred
 * @date 2021-08-23 16:56
 */
public class ServiceInvoker {

    // 单例
    private static final ServiceInvoker INSTANCE = new ServiceInvoker();
    // 负载均衡器实例
    private volatile LoadBalancer loadBalancer;// 使用volatile保障可见性

    private ServiceInvoker() {
    }

    // 获取单例对象
    public static ServiceInvoker getInstance() {
        return INSTANCE;
    }

    // 根据指定的负载均衡器派发请求到下游
    public void dispatchRequest(Request request) {
        EndPoint endPoint = getLoadBalancer().nextEndPoint();
        if (endPoint != null) {
            dispatchToDownstream(request, endPoint);
        }
    }

    // 获取负载均衡器实例
    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    // 设置负载均衡器实例
    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    // 模拟下游方法
    private void dispatchToDownstream(Request request, EndPoint endpoint) {
        System.out.println(Thread.currentThread().getName() + "> Dispatch request to " + endpoint + ":" + request);
        // 省略其他代码
    }
}
