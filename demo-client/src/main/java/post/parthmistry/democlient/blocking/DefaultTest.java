package post.parthmistry.democlient.blocking;

import org.apache.http.impl.client.HttpClients;
import post.parthmistry.democlient.util.DurationTracker;

import java.util.concurrent.CountDownLatch;

public class DefaultTest {

    public static void main(String[] args) throws Exception {
        var serverHostname = args[0];
        var serverPort = Integer.parseInt(args[1]);

        final var requestCount = 50;

        var httpClient = HttpClients.createDefault();

        CountDownLatch countDownLatch = new CountDownLatch(requestCount);

        var totalDurationTracker = new DurationTracker("total");

        for (int i = 1; i <= requestCount; i++) {
            var requestSender = new RequestSender(serverHostname, serverPort, httpClient, i, countDownLatch::countDown);
            requestSender.start();
        }

        countDownLatch.await();

        totalDurationTracker.endAndLogElapsedDuration();
    }

}
