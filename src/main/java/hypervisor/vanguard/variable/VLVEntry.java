package hypervisor.vanguard.variable;

import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;

public class VLVEntry implements VLVTypeRunner{

    public static final long FLAG_DUPLICATE_TARGET = 0x1L;
    public static final long FLAG_DUPLICATE_SYNCER_START = 0x2L;
    public static final long FLAG_DUPLICATE_SYNCER_CHANGE = 0x4L;
    public static final long FLAG_DUPLICATE_SYNCER_DONE = 0x8L;

    public VLVTypeVariable target;

    protected VLSyncType<VLVEntry> syncerStart;
    protected VLSyncType<VLVEntry> syncerChange;
    protected VLSyncType<VLVEntry> syncerDone;
    protected int delay;
    protected int delaytracker;

    public VLVEntry(VLVTypeVariable target, int delay){
        this.target = target;
        this.delay = delay;

        delaytracker = 0;
    }

    public VLVEntry(VLVTypeVariable target, int delay, VLSyncType<VLVEntry> syncerChange){
        this.target = target;
        this.syncerChange = syncerChange;
        this.delay = delay;

        delaytracker = 0;
    }

    public VLVEntry(VLVTypeVariable target, int delay, VLSyncType<VLVEntry> syncerStart, VLSyncType<VLVEntry> syncerChange, VLSyncType<VLVEntry> syncerDone){
        this.target = target;
        this.syncerStart = syncerStart;
        this.syncerChange = syncerChange;
        this.syncerDone = syncerDone;
        this.delay = delay;

        delaytracker = 0;
    }

    public VLVEntry(VLVEntry src, long flags){
        copy(src, flags);
    }

    protected VLVEntry(){

    }

    @Override
    public void initialize(float from, float to, int cycles){
        target.initialize(from, to, cycles);
    }

    @Override
    public void initialize(float from, float to, float changerate){
        target.initialize(from, to, changerate);
    }

    @Override
    public void activate(){
        target.activate();
    }

    @Override
    public void deactivate(){
        target.deactivate();
    }

    @Override
    public void fastForward(int count){
        for(int i = 0; i < count; i++){
            next();
        }
    }

    @Override
    public void chain(int cycles, float to){
        target.chain(cycles, to);
    }

    @Override
    public void chain(float changerate, float to){
        target.chain(changerate, to);
    }

    @Override
    public void reverse(){
        target.reverse();
    }

    @Override
    public void reset(){
        target.reset();
        resetDelayTrackers();
    }

    @Override
    public void finish(){
        target.finish();
        syncChange();
        syncDone();
    }

    @Override
    public void syncerStart(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerStart = (VLSyncType<VLVEntry>)syncer;
    }

    @Override
    public void syncerChange(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerChange = (VLSyncType<VLVEntry>)syncer;
    }

    @Override
    public void syncerDone(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerDone = (VLSyncType<VLVEntry>)syncer;
    }

    @Override
    public VLSyncType<VLVEntry> syncerStart(){
        return syncerStart;
    }

    @Override
    public VLSyncType<VLVEntry> syncerChange(){
        return syncerChange;
    }

    @Override
    public VLSyncType<VLVEntry> syncerDone(){
        return syncerDone;
    }

    @Override
    public float length(){
        return target.length();
    }

    @Override
    public int delay(){
        return delay;
    }

    @Override
    public boolean active(){
        return target.active();
    }

    @Override
    public void start(){
        syncStart();
        activate();
    }

    @Override
    public void pause(){
        target.deactivate();
    }

    @Override
    public int next(){
        int advancement = 0;

        if(target.active()){
            if(delaytracker < delay){
                delaytracker++;
                advancement = 1;

            }else{
                advancement = target.next();
                syncChange();

                if(!target.active()){
                    syncDone();
                    resetDelayTrackers();
                }
            }
        }

        return advancement;
    }

    @Override
    public void syncStart(){
        if(syncerStart != null){
            syncerStart.sync(this);
        }
    }

    @Override
    public void syncChange(){
        if(syncerChange != null){
            syncerChange.sync(this);
        }
    }

    @Override
    public void syncDone(){
        if(syncerDone != null){
            syncerDone.sync(this);
        }
    }

    @Override
    public void startAll(){
        start();
    }

    @Override
    public void pauseAll(){
        pause();
    }

    @Override
    public void syncStartAll(){
        syncStart();
    }

    @Override
    public void syncChangeAll(){
        syncChange();
    }

    @Override
    public void syncDoneAll(){
        syncDone();
    }

    @Override
    public void delay(int delay){
        this.delay = delay;
        resetDelayTrackers();
    }

    @Override
    public void delayBy(int amount){
        this.delay += delay;
        resetDelayTrackers();
    }

    @Override
    public void resetDelayTrackers(){
        delaytracker = 0;
    }

    @Override
    public void length(Length results){
        results.length = target.length();
        results.index = -1;
    }

    @Override
    public boolean paused(){
        return !target.active();
    }

    @Override
    public boolean done(){
        return !target.active();
    }

    @Override
    public void endPointIndex(int index){

    }

    @Override
    public void findEndPointIndex(){

    }

    @Override
    public void checkForNewEndPoint(int index){

    }

    @Override
    public VLVTypeRunner endPoint(){
        return this;
    }

    @Override
    public int endPointIndex(){
        return 0;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        VLVEntry entry = (VLVEntry)src;

        if((flags & VLCopyable.FLAG_REFERENCE) == VLCopyable.FLAG_REFERENCE){
            target = entry.target;
            syncerStart = entry.syncerStart;
            syncerChange = entry.syncerChange;
            syncerDone = entry.syncerDone;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            target = (VLVTypeVariable)entry.target.duplicate(VLCopyable.FLAG_DUPLICATE);

            if(syncerStart != null){
                syncerStart = entry.syncerStart.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerChange != null){
                syncerChange = entry.syncerChange.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerDone != null){
                syncerDone = entry.syncerDone.duplicate(VLCopyable.FLAG_DUPLICATE);
            }

        }else if((flags & VLCopyable.FLAG_CUSTOM) == VLCopyable.FLAG_CUSTOM){
            if((flags & FLAG_DUPLICATE_TARGET) == FLAG_DUPLICATE_TARGET){
                target = (VLVTypeVariable)entry.target.duplicate(VLCopyable.FLAG_DUPLICATE);

            }else{
                target = entry.target;
            }
            if(syncerStart != null){
                if((flags & FLAG_DUPLICATE_SYNCER_START) == FLAG_DUPLICATE_SYNCER_START){
                    syncerStart = entry.syncerStart.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerStart = entry.syncerStart;
                }
            }
            if(syncerChange != null){
                if((flags & FLAG_DUPLICATE_SYNCER_CHANGE) == FLAG_DUPLICATE_SYNCER_CHANGE){
                    syncerChange = entry.syncerChange.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerChange = entry.syncerChange;
                }
            }
            if(syncerDone != null){
                if((flags & FLAG_DUPLICATE_SYNCER_DONE) == FLAG_DUPLICATE_SYNCER_DONE){
                    syncerDone = entry.syncerDone.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerDone = entry.syncerDone;
                }
            }

        }else{
            VLCopyable.Helper.throwMissingDefaultFlags();
        }

        delay = entry.delay;
        delaytracker = entry.delaytracker;
    }

    @Override
    public VLVEntry duplicate(long flags){
        return new VLVEntry(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("[");
        log.append(getClass().getSimpleName());
        log.append("] syncerStartType[");
        log.append(syncerStart == null ? "null" : syncerStart.getClass().getSimpleName());
        log.append("] syncerChangeType[");
        log.append(syncerChange == null ? "null" : syncerChange.getClass().getSimpleName());
        log.append("] syncerDoneType[");
        log.append(syncerDone == null ? "null" : syncerDone.getClass().getSimpleName());
        log.append("] [");

        target.log(log, data);

        log.append("]");
    }
}
