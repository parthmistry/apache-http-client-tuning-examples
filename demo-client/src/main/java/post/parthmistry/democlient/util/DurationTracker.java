package post.parthmistry.democlient.util;

public class DurationTracker {

    private final String name;

    private final long start;

    private long end;

    public DurationTracker(String name) {
        this.name = name;
        this.start = System.currentTimeMillis();
    }

    public void end() {
        this.end = System.currentTimeMillis();
    }

    public void endAndLogElapsedDuration() {
        this.end();
        this.logElapsedDuration();
    }

    public long getElapsedTimeMillis() {
        return this.end - this.start;
    }

    public void logElapsedDuration() {
        System.out.println(this.name + " duration: " + this.getElapsedTimeMillis());
    }

}
