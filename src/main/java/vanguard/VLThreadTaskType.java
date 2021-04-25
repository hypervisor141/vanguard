package vanguard;

public interface VLThreadTaskType<WORKER extends VLThread> {

    void run(WORKER worker);
    void requestDestruction();
}
