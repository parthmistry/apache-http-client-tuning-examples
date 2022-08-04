package post.parthmistry.democlient.index;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import post.parthmistry.democlient.util.CsvUtil;
import post.parthmistry.democlient.util.RequestCallbackHandler;
import post.parthmistry.democlient.util.DurationTracker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class TunedTest {

    public static void main(String[] args) throws Exception {
        var serverHostname = args[0];
        var serverPort = Integer.parseInt(args[1]);
        var csvFilePath = args[2];

        String requestUrl = "http://%s:%d/api/tuned/airline".formatted(serverHostname, serverPort);

        var jsonMapper = new ObjectMapper();

        var httpClient = HttpAsyncClients.custom().setMaxConnPerRoute(1000).setMaxConnTotal(1000).build();
        httpClient.start();

        List<Map<String, Object>> records = CsvUtil.prepareCsvRecordList(csvFilePath);

        CountDownLatch countDownLatch = new CountDownLatch(records.size());

        var totalDurationTracker = new DurationTracker("total");

        for (int i = 1; i <= records.size(); i++) {
            var recordMap = records.get(i - 1);
            var httpPostRequest = new HttpPost(requestUrl);
            httpPostRequest.addHeader("Content-Type", "application/json");
            httpPostRequest.setEntity(new StringEntity(jsonMapper.writeValueAsString(recordMap)));
            var requestDurationTracker = new DurationTracker("request " + i);
            httpClient.execute(httpPostRequest, new RequestCallbackHandler(requestDurationTracker, i, countDownLatch::countDown));
        }

        countDownLatch.await();

        totalDurationTracker.endAndLogElapsedDuration();

        httpClient.close();
    }

}
