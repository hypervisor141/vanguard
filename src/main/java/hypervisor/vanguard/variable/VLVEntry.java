package hypervisor.vanguard.variable;

import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;

public class VLVEntry implements VLVTypeRunner{

    public static final long FLAG_DUPLICATE_TARGET = 0x1L;
    public static final long FLAG_DUPLICATE_SYNCER_START = 0x2L;
    public static final long FLAG_DUPLICATE_SYNCER_CHANGE = 0x4L;
    public static final long FLAG_DUPLICATE_SYNCER_PAUSE = 0x8L;
    public static final long FLAG_DUPLICATE_SYNCER_DONE = 0x10L;

    public VLVTypeVariable target;

    protected VLSyncType<VLVEntry> syncerOnStart;
    protected VLSyncType<VLVEntry> syncerOnChange;
    protected VLSyncType<VLVEntry> syncerOnPause;
    protected VLSyncType<VLVEntry> syncerOnDone;

    protected int delay;
    protected int delaytracker;

    public VLVEntry(VLVTypeVariable target, int delay){
        this.target = target;
        this.delay = delay;

        delaytracker = 0;
    }

    public VLVEntry(VLVTypeVariable target, int delay, VLSyncType<VLVEntry> syncerOnChange){
        this.target = target;
        this.syncerOnChange = syncerOnChange;
        this.delay = delay;

        delaytracker = 0;
    }

    public VLVEntry(VLVTypeVariable target, int delay, VLSyncType<VLVEntry> syncerOnStart, VLSyncType<VLVEntry> syncerOnChange, VLSyncType<VLVEntry> syncerOnPause, VLSyncType<VLVEntry> syncerOnDone){
        this.target = target;
        this.syncerOnStart = syncerOnStart;
        this.syncerOnChange = syncerOnChange;
        this.syncerOnPause = syncerOnPause;
        this.syncerOnDone = syncerOnDone;
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
        syncOnChange();
    }

    @Override
    public void initialize(float from, float to, float changerate){
        target.initialize(from, to, changerate);
        syncOnChange();
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
        syncOnChange();
    }

    @Override
    public void chain(float changerate, float to){
        target.chain(changerate, to);
        syncOnChange();
    }

    @Override
    public void reverse(){
        target.reverse();
    }

    @Override
    public void reset(){
        target.reset();

        resetDelayTrackers();
        syncOnChange();
    }

    @Override
    public void finish(){
        target.finish();

        syncOnChange();
        syncOnDone();
    }

    @Override
    public void syncerOnStart(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerOnStart = (VLSyncType<VLVEntry>)syncer;
    }

    @Override
    public void syncerOnChange(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerOnChange = (VLSyncType<VLVEntry>)syncer;
    }

    @Override
    public void syncerOnPause(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerOnPause = (VLSyncType<VLVEntry>)syncer;
    }

    @Override
    public void syncerOnDone(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerOnDone = (VLSyncType<VLVEntry>)syncer;
    }

    @Override
    public VLSyncType<VLVEntry> syncerOnStart(){
        return syncerOnStart;
    }

    @Override
    public VLSyncType<VLVEntry> syncerOnChange(){
        return syncerOnChange;
    }

    @Override
    public VLSyncType<VLVEntry> syncerOnPause(){
        return syncerOnPause;
    }

    @Override
    public VLSyncType<VLVEntry> syncerOnDone(){
        return syncerOnDone;
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
        activate();
        syncOnStart();
    }

    @Override
    public void pause(){
        target.deactivate();
        syncOnPause();
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
                syncOnChange();

                if(!target.active()){
                    syncOnPause();
                    syncOnDone();

                    resetDelayTrackers();
                }
            }
        }

        return advancement;
    }

    @Override
    public void syncOnStart(){
        if(syncerOnStart != null){
            syncerOnStart.sync(this);
        }
    }

    @Override
    public void syncOnChange(){
        if(syncerOnChange != null){
            syncerOnChange.sync(this);
        }
    }

    @Override
    public void syncOnPause(){
        if(syncerOnPause != null){
            syncerOnPause.sync(this);
        }
    }

    @Override
    public void syncOnDone(){
        if(syncerOnDone != null){
            syncerOnDone.sync(this);
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
    public void syncOnStartAll(){
        syncOnStart();
    }

    @Override
    public void syncOnChangeAll(){
        syncOnChange();
    }

    @Override
    public void syncOnPauseAll(){
        syncOnPause();
    }

    @Override
    public void syncOnDoneAll(){
        syncOnDone();
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
            syncerOnStart = entry.syncerOnStart;
            syncerOnChange = entry.syncerOnChange;
            syncerOnPause = entry.syncerOnPause;
            syncerOnDone = entry.syncerOnDone;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            target = (VLVTypeVariable)entry.target.duplicate(VLCopyable.FLAG_DUPLICATE);

            if(syncerOnStart != null){
                syncerOnStart = entry.syncerOnStart.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerOnChange != null){
                syncerOnChange = entry.syncerOnChange.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerOnPause != null){
                syncerOnPause = entry.syncerOnPause.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerOnDone != null){
                syncerOnDone = entry.syncerOnDone.duplicate(VLCopyable.FLAG_DUPLICATE);
            }

        }else if((flags & VLCopyable.FLAG_CUSTOM) == VLCopyable.FLAG_CUSTOM){
            if((flags & FLAG_DUPLICATE_TARGET) == FLAG_DUPLICATE_TARGET){
                target = (VLVTypeVariable)entry.target.duplicate(VLCopyable.FLAG_DUPLICATE);

            }else{
                target = entry.target;
            }
            if(syncerOnStart != null){
                if((flags & FLAG_DUPLICATE_SYNCER_START) == FLAG_DUPLICATE_SYNCER_START){
                    syncerOnStart = entry.syncerOnStart.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerOnStart = entry.syncerOnStart;
                }
            }
            if(syncerOnChange != null){
                if((flags & FLAG_DUPLICATE_SYNCER_CHANGE) == FLAG_DUPLICATE_SYNCER_CHANGE){
                    syncerOnChange = entry.syncerOnChange.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerOnChange = entry.syncerOnChange;
                }
            }
            if(syncerOnPause != null){
                if((flags & FLAG_DUPLICATE_SYNCER_PAUSE) == FLAG_DUPLICATE_SYNCER_PAUSE){
                    syncerOnPause = entry.syncerOnPause.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerOnPause = entry.syncerOnPause;
                }
            }
            if(syncerOnDone != null){
                if((flags & FLAG_DUPLICATE_SYNCER_DONE) == FLAG_DUPLICATE_SYNCER_DONE){
                    syncerOnDone = entry.syncerOnDone.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerOnDone = entry.syncerOnDone;
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
        log.append(syncerOnStart == null ? "null" : syncerOnStart.getClass().getSimpleName());
        log.append("] syncerChangeType[");
        log.append(syncerOnChange == null ? "null" : syncerOnChange.getClass().getSimpleName());
        log.append("] syncerPauseType[");
        log.append(syncerOnPause == null ? "null" : syncerOnPause.getClass().getSimpleName());
        log.append("] syncerDoneType[");
        log.append(syncerOnDone == null ? "null" : syncerOnDone.getClass().getSimpleName());
        log.append("] [");

        target.log(log, data);

        log.append("]");
    }
}
