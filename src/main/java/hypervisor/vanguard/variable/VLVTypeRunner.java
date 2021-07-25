package hypervisor.vanguard.variable;

import hypervisor.vanguard.sync.VLSyncType;

public interface VLVTypeRunner extends VLVTypeRunnable{

    void start();
    void pause();
    void syncStart();
    void syncChange();
    void syncDone();
    void startAll();
    void pauseAll();
    void syncStartAll();
    void syncChangeAll();
    void syncDoneAll();
    void delay(int delay);
    void delayBy(int amount);
    void syncerStart(VLSyncType<? extends VLVTypeRunner> syncer);
    void syncerChange(VLSyncType<? extends VLVTypeRunner> syncer);
    void syncerDone(VLSyncType<? extends VLVTypeRunner> syncer);
    void resetDelayTrackers();
    VLSyncType<? extends VLVTypeRunner> syncerStart();
    VLSyncType<? extends VLVTypeRunner> syncerChange();
    VLSyncType<? extends VLVTypeRunner> syncerDone();
    void length(Length results);
    int delay();
    boolean paused();
    boolean done();

    void endPointIndex(int index);
    void findEndPointIndex();
    void checkForNewEndPoint(int index);
    VLVTypeRunner endPoint();
    int endPointIndex();

    class Length{

        public float length;
        public int index;

        public Length(){

        }
    }
}
