package vanguard;

public interface VLThreadTask<WORKER extends VLThreadWorker> {

    void run(WORKER worker);
}
