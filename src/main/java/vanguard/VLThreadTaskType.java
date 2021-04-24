package vanguard;

public interface VLThreadTaskType<WORKER extends VLThreadWorker> {

    void run(WORKER worker);
    void destroyRequested();
}
