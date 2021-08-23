package club.yeyue.multi.thread.club.volatilet;

import java.util.Objects;

/**
 * 下游节点
 *
 * @author fred
 * @date 2021-08-23 16:59
 */
public class EndPoint {

    public final String host;
    public final int port;
    public final int weight;
    private volatile boolean online = true;// volatile变量作为状态标识

    public EndPoint(String host, int port, int weight) {
        this.host = host;
        this.port = port;
        this.weight = weight;
    }

    public boolean ifOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EndPoint endPoint = (EndPoint) o;
        return port == endPoint.port && weight == endPoint.weight && Objects.equals(host, endPoint.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, weight);
    }

    @Override
    public String toString() {
        return "EndPoint{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", online=" + online +
                '}';
    }
}
