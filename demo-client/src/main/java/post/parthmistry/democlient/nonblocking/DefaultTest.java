package post.parthmistry.democlient.nonblocking;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import post.parthmistry.democlient.util.RequestCallbackHandler;
import post.parthmistry.democlient.util.DurationTracker;

import java.util.concurrent.CountDownLatch;

public class DefaultTest {

    public static void main(String[] args) throws Exception {
        var serverHostname = args[0];
        var serverPort = Integer.parseInt(args[1]);

        final var requestCount = 50;

        var httpClient = HttpAsyncClients.createDefault();
        httpClient.start();

        CountDownLatch countDownLatch = new CountDownLatch(requestCount);

        var totalDurationTracker = new DurationTracker("total");

        for (int i = 1; i <= requestCount; i++) {
            var httpGetRequest = new HttpGet("http://%s:%d/api/sample/request".formatted(serverHostname, serverPort));
            var requestDurationTracker = new DurationTracker("request " + i);
            httpClient.execute(httpGetRequest, new RequestCallbackHandler(requestDurationTracker, i, countDownLatch::countDown));
        }

        countDownLatch.await();

        totalDurationTracker.endAndLogElapsedDuration();

        httpClient.close();
    }

}
