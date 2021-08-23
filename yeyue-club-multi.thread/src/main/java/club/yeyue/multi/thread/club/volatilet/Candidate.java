package club.yeyue.multi.thread.club.volatilet;

import java.util.Iterator;
import java.util.Set;

/**
 * 部件
 *
 * @author fred
 * @date 2021-08-23 17:09
 */
public final class Candidate implements Iterable<EndPoint> {
    // 下游节点
    private final Set<EndPoint> endPoints;

    // 总权重
    public final int totalWeight;

    public Candidate(Set<EndPoint> endPoints) {
        assert !endPoints.isEmpty();
        int sum = 0;
        for (EndPoint endpoint : endPoints) {
            sum += endpoint.weight;
        }
        this.totalWeight = sum;
        this.endPoints = endPoints;
    }

    public int size() {
        return endPoints.size();
    }

    @Override
    public Iterator<EndPoint> iterator() {
        return endPoints.iterator();
    }
}
