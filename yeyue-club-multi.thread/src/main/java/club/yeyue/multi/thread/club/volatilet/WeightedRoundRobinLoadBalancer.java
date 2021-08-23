package club.yeyue.multi.thread.club.volatilet;

/**
 * 加权轮询负载均衡算法实现
 *
 * @author fred
 * @date 2021-08-24 00:24
 */
public class WeightedRoundRobinLoadBalancer extends AbstractLoadBalancer {

    // 构造器私有化
    private WeightedRoundRobinLoadBalancer(Candidate candidate) {
        super(candidate);
    }

    // 静态方法初始化
    public static WeightedRoundRobinLoadBalancer getInstance(Candidate candidate) throws Exception {
        WeightedRoundRobinLoadBalancer loadBalancer = new WeightedRoundRobinLoadBalancer(candidate);
        loadBalancer.init();
        return loadBalancer;
    }

    // 算法实现
    @Override
    public EndPoint nextEndPoint() {
        EndPoint selectedEndpoint = null;
        int subWeight = 0;
        int dynamicTotoalWeight;
        final double rawRnd = super.random.nextDouble();
        int rand;
        // 读取volatile变量candidate
        final Candidate candiate = super.candidate;
        dynamicTotoalWeight = candiate.totalWeight;
        for (EndPoint endpoint : candiate) {
            // 选取节点以及计算总权重时跳过非在线节点
            if (!endpoint.ifOnline()) {
                dynamicTotoalWeight -= endpoint.weight;
                continue;
            }
            rand = (int) (rawRnd * dynamicTotoalWeight);
            subWeight += endpoint.weight;
            if (rand <= subWeight) {
                selectedEndpoint = endpoint;
                break;
            }
        }
        return selectedEndpoint;
    }
}
