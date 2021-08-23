package club.yeyue.multi.thread.club.volatilet;

/**
 * 负载均衡器
 *
 * @author fred
 * @date 2021-08-23 16:58
 */
public interface LoadBalancer {
    void updateCandidate(final Candidate candidate);
    EndPoint nextEndPoint();
}
