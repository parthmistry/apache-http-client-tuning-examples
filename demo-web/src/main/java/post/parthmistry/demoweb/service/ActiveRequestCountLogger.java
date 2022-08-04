package post.parthmistry.demoweb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveRequestCountLogger extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ActiveRequestCountLogger.class);

    private volatile int activeRequestCount = 0;

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                logger.info("active requests: " + activeRequestCount);
            }
        } catch (Exception e) {
            logger.error("active request count logger failed", e);
        }

    }

    public synchronized void addActiveRequest() {
        activeRequestCount++;
    }

    public synchronized  void removeActiveRequest() {
        activeRequestCount--;
    }

}
