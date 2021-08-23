package club.yeyue.multi.thread.club.volatilet;

import java.io.InputStream;

/**
 * 模拟请求
 *
 * @author fred
 * @date 2021-08-23 17:46
 */
public class Request {

    private final long transactionId;
    private final int transactionType;
    private InputStream in;

    public Request(long transactionId, int transactionType) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    @Override
    public String toString() {
        return "Request{" +
                "transactionId=" + transactionId +
                ", transactionType=" + transactionType +
                '}';
    }
}
