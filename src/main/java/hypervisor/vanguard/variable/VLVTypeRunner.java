package hypervisor.vanguard.variable;

import hypervisor.vanguard.sync.VLSyncType;

public interface VLVTypeRunner extends VLVTypeRunnable{

    void start();
    void pause();
    void syncOnStart();
    void syncOnChange();
    void syncOnPause();
    void syncDone();
    void startAll();
    void pauseAll();
    void syncOnStartAll();
    void syncOnChangeAll();
    void syncOnPauseAll();
    void syncOnDoneAll();
    void delay(int delay);
    void delayBy(int amount);
    void syncerOnStart(VLSyncType<? extends VLVTypeRunner> syncer);
    void syncerOnChange(VLSyncType<? extends VLVTypeRunner> syncer);
    void syncerOnPause(VLSyncType<? extends VLVTypeRunner> syncer);
    void syncerOnDone(VLSyncType<? extends VLVTypeRunner> syncer);
    void resetDelayTrackers();
    VLSyncType<? extends VLVTypeRunner> syncerOnStart();
    VLSyncType<? extends VLVTypeRunner> syncerOnChange();
    VLSyncType<? extends VLVTypeRunner> syncerOnPause();
    VLSyncType<? extends VLVTypeRunner> syncerOnDone();
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
