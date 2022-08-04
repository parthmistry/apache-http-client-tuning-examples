package post.parthmistry.democlient.blocking;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import post.parthmistry.democlient.util.CompleteAction;
import post.parthmistry.democlient.util.DurationTracker;

public class RequestSender extends Thread {

    private final String serverHostname;

    private final int serverPort;

    private final CloseableHttpClient httpClient;

    private final int requestNumber;

    private final CompleteAction completeAction;

    public RequestSender(String serverHostname, int serverPort, CloseableHttpClient httpClient, int requestNumber, CompleteAction completeAction) {
        this.serverHostname = serverHostname;
        this.serverPort = serverPort;
        this.httpClient = httpClient;
        this.requestNumber = requestNumber;
        this.completeAction = completeAction;
    }

    @Override
    public void run() {
        try {
            var httpGetRequest = new HttpGet("http://%s:%d/api/sample/request".formatted(serverHostname, serverPort));
            var requestDurationTracker = new DurationTracker("request " + requestNumber);
            var response = httpClient.execute(httpGetRequest);
            response.close();
            requestDurationTracker.endAndLogElapsedDuration();
        } catch (Exception e) {
            System.out.println("error occurred for request " + requestNumber + ": " + e.getMessage());
        } finally {
            completeAction.execute();
        }
    }

}
