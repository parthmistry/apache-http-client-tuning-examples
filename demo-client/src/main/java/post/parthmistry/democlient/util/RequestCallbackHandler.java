package post.parthmistry.democlient.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.io.IOException;

public class RequestCallbackHandler implements FutureCallback<HttpResponse>  {

    private final DurationTracker durationTracker;

    private final int requestNumber;

    private final CompleteAction completeAction;

    public RequestCallbackHandler(DurationTracker durationTracker, int requestNumber, CompleteAction completeAction) {
        this.durationTracker = durationTracker;
        this.requestNumber = requestNumber;
        this.completeAction = completeAction;
    }

    @Override
    public void completed(HttpResponse result) {
        if (result instanceof CloseableHttpResponse response) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        durationTracker.endAndLogElapsedDuration();
        completeAction.execute();
    }

    @Override
    public void failed(Exception e) {
        System.out.println("error occurred for request " + requestNumber + ": " + e.getMessage());
        completeAction.execute();
    }

    @Override
    public void cancelled() {
        completeAction.execute();
    }

}
