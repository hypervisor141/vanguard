package hypervisor.vanguard.variable;

import hypervisor.vanguard.list.arraybacked.VLListType;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;

@SuppressWarnings("unused")
public class VLVManager<ENTRY extends VLVTypeRunner> implements VLVTypeManager<ENTRY>{

    public static final long FLAG_REFERENCE_ENTRIES = 0x1L;
    public static final long FLAG_DUPLICATE_ENTRIES = 0x2L;
    public static final long FLAG_DUPLICATE_SYNCER_START = 0x4L;
    public static final long FLAG_DUPLICATE_SYNCER_CHANGE = 0x8L;
    public static final long FLAG_DUPLICATE_SYNCER_PAUSE = 0x10L;
    public static final long FLAG_DUPLICATE_SYNCER_DONE = 0x20L;

    protected boolean paused;
    protected boolean isdone;
    protected int endpointindex;

    protected VLListType<ENTRY> entries;
    protected VLSyncType<VLVManager<ENTRY>> syncerOnStart;
    protected VLSyncType<VLVManager<ENTRY>> syncerOnChange;
    protected VLSyncType<VLVManager<ENTRY>> syncerOnPause;
    protected VLSyncType<VLVManager<ENTRY>> syncerOnDone;

    public VLVManager(int capacity, int resizeoverhead){
        entries = new VLListType<>(capacity, resizeoverhead);

        paused = true;
        isdone = true;
        endpointindex = -1;
    }

    public VLVManager(int capacity, int resizeoverhead, VLSyncType<VLVManager<ENTRY>> syncerOnChange){
        entries = new VLListType<>(capacity, resizeoverhead);

        this.syncerOnChange = syncerOnChange;

        paused = true;
        isdone = true;
        endpointindex = -1;
    }

    public VLVManager(int capacity, int resizeoverhead, VLSyncType<VLVManager<ENTRY>> syncerOnStart, VLSyncType<VLVManager<ENTRY>> syncerOnChange, VLSyncType<VLVManager<ENTRY>> syncerOnPause, VLSyncType<VLVManager<ENTRY>> syncerOnDone){
        entries = new VLListType<>(capacity, resizeoverhead);

        this.syncerOnStart = syncerOnStart;
        this.syncerOnChange = syncerOnChange;
        this.syncerOnPause = syncerOnPause;
        this.syncerOnDone = syncerOnDone;

        paused = true;
        isdone = true;
        endpointindex = -1;
    }

    public VLVManager(VLVManager<ENTRY> src, long flags){
        copy(src, flags);
    }

    protected VLVManager(){

    }

    @Override
    public void initialize(int cycles){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initialize(cycles);
        }
    }

    @Override
    public void initialize(float changerate){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initialize(changerate);
        }
    }

    @Override
    public void initialize(float from, float to, int cycles){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initialize(from, to, cycles);
        }
    }

    @Override
    public void initialize(float from, float to, float changerate){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initialize(from, to, changerate);
        }
    }

    @Override
    public void activate(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).activate();
        }
    }

    @Override
    public void deactivate(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).deactivate();
        }
    }

    @Override
    public int next(){
        if(!paused){
            int count = 0;

            for(int i = 0; i < entries.size(); i++){
                count += entries.get(i).next();
            }

            if(count == 0){
                isdone = true;

                pause();
                syncOnDone();

            }else{
                syncOnChange();
            }

            return count;

        }else{
            return 0;
        }
    }

    @Override
    public void start(){
        isdone = false;

        if(paused){
            paused = false;
            syncOnStart();
        }
    }

    @Override
    public void pause(){
        if(!paused){
            syncOnPause();
            paused = true;
        }
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

        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).startAll();
        }
    }

    @Override
    public void pauseAll(){
        pause();

        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).pauseAll();
        }
    }

    @Override
    public void syncOnStartAll(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).syncOnStartAll();
        }

        syncOnStart();
    }

    @Override
    public void syncOnChangeAll(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).syncOnChangeAll();
        }

        syncOnChange();
    }

    @Override
    public void syncOnPauseAll(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).syncOnPauseAll();
        }

        syncOnPause();
    }

    @Override
    public void syncOnDoneAll(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).syncOnDoneAll();
        }

        syncOnDone();
    }

    @Override
    public void fastForward(int count){
        for(int i = 0; i < count; i++){
            next();
        }
    }

    @Override
    public void chain(int cycles, float to){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).chain(cycles, to);
        }

        syncOnChange();
    }

    @Override
    public void chain(float changerate, float to){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).chain(changerate, to);
        }

        syncOnChange();
    }

    @Override
    public void reverse(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).reverse();
        }
    }

    @Override
    public void reset(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).reset();
        }

        syncOnChange();
    }

    @Override
    public void finish(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).finish();
        }

        syncOnChange();
        syncOnDone();
    }

    @Override
    public void delayBy(int amount){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).delayBy(amount);
        }
    }

    @Override
    public void delay(int delay){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).delay(delay);
        }
    }

    @Override
    public void resetDelayTrackers(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).resetDelayTrackers();
        }
    }

    @Override
    public void endPointIndex(int index){
        endpointindex = index;
    }

    @Override
    public void findEndPointIndex(){
        Length length = new Length();
        length(length);
        endpointindex = length.index;

        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).findEndPointIndex();
        }
    }

    @Override
    public void checkForNewEndPoint(int index){
        float length = endPoint().length();
        float newlength = entries.get(index).length();

        if(newlength > length){
            endpointindex = index;
        }
    }

    @Override
    public void syncerOnStart(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerOnStart = (VLSyncType<VLVManager<ENTRY>>)syncer;
    }

    @Override
    public void syncerOnChange(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerOnChange = (VLSyncType<VLVManager<ENTRY>>)syncer;
    }

    @Override
    public void syncerOnPause(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerOnChange = (VLSyncType<VLVManager<ENTRY>>)syncer;
    }

    @Override
    public void syncerOnDone(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerOnDone = (VLSyncType<VLVManager<ENTRY>>)syncer;
    }

    @Override
    public void add(ENTRY entry){
        entries.add(entry);
    }

    @Override
    public ENTRY set(int index, ENTRY entry){
        ENTRY e = entries.get(index);
        entries.set(index, entry);

        return e;
    }

    @Override
    public void remove(int index){
        entries.remove(index);
    }

    @Override
    public void nullify(){
        entries.nullify();
    }

    @Override
    public void clear(){
        entries.virtualSize(0);
    }

    @Override
    public void purge(){
        nullify();
        entries.virtualSize(0);
    }

    @Override
    public void length(Length results){
        int size = entries.size();
        float result = -Float.MAX_VALUE;
        float length;
        int index = -1;

        for(int i = 0; i < size; i++){
            length = entries.get(i).length();

            if(result < length){
                result = length;
                index = i;
            }
        }

        results.length = result;
        results.index = index;
    }

    @Override
    public VLSyncType<VLVManager<ENTRY>> syncerOnStart(){
        return syncerOnStart;
    }

    @Override
    public VLSyncType<VLVManager<ENTRY>> syncerOnChange(){
        return syncerOnChange;
    }

    @Override
    public VLSyncType<VLVManager<ENTRY>> syncerOnPause(){
        return syncerOnPause;
    }

    @Override
    public VLSyncType<VLVManager<ENTRY>> syncerOnDone(){
        return syncerOnDone;
    }

    @Override
    public float length(){
        Length length = new Length();
        length(length);
        return length.length;
    }

    @Override
    public int delay(){
        int size = entries.size();
        int min = Integer.MAX_VALUE;
        int delay = 0;

        for(int i = 0; i < size; i++){
            delay = entries.get(i).delay();

            if(min > delay){
                min = delay;
            }
        }

        return delay;
    }

    @Override
    public boolean active(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            if(entries.get(i).active()){
                return true;
            }
        }

        return false;
    }

    @Override
    public VLVTypeRunner endPoint(){
        return entries.get(endpointindex).endPoint();
    }

    @Override
    public int endPointIndex(){
        return endpointindex;
    }

    @Override
    public ENTRY get(int index){
        return entries.get(index);
    }

    @Override
    public VLListType<ENTRY> get(){
        return entries;
    }

    @Override
    public int size(){
        return entries.size();
    }

    @Override
    public boolean paused(){
        return paused;
    }

    @Override
    public boolean done(){
        return isdone;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        VLVManager<ENTRY> target = (VLVManager<ENTRY>)src;

        if((flags & VLCopyable.FLAG_REFERENCE) == VLCopyable.FLAG_REFERENCE){
            entries = target.entries;
            syncerOnStart = target.syncerOnStart;
            syncerOnChange = target.syncerOnChange;
            syncerOnPause = target.syncerOnPause;
            syncerOnDone = target.syncerOnDone;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            entries = target.entries.duplicate(VLCopyable.FLAG_DUPLICATE);

            if(syncerOnStart != null){
                syncerOnStart = target.syncerOnStart.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerOnChange != null){
                syncerOnChange = target.syncerOnChange.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerOnPause != null){
                syncerOnPause = target.syncerOnPause.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerOnDone != null){
                syncerOnDone = target.syncerOnDone.duplicate(VLCopyable.FLAG_DUPLICATE);
            }

        }else if((flags & VLCopyable.FLAG_CUSTOM) == VLCopyable.FLAG_CUSTOM){
            if((flags & FLAG_REFERENCE_ENTRIES) == FLAG_REFERENCE_ENTRIES){
                entries = target.entries.duplicate(VLCopyable.FLAG_REFERENCE);

            }else if((flags & FLAG_DUPLICATE_ENTRIES) == FLAG_DUPLICATE_ENTRIES){
                entries = target.entries.duplicate(VLCopyable.FLAG_DUPLICATE);

            }else{
                VLCopyable.Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_REFERENCE_ENTRIES", "FLAG_DUPLICATE_ENTRIES");
            }

            if(syncerOnStart != null){
                if((flags & FLAG_DUPLICATE_SYNCER_START) == FLAG_DUPLICATE_SYNCER_START){
                    syncerOnStart = target.syncerOnStart.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerOnStart = target.syncerOnStart;
                }
            }
            if(syncerOnChange != null){
                if((flags & FLAG_DUPLICATE_SYNCER_CHANGE) == FLAG_DUPLICATE_SYNCER_CHANGE){
                    syncerOnChange = target.syncerOnChange.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerOnChange = target.syncerOnChange;
                }
            }
            if(syncerOnPause != null){
                if((flags & FLAG_DUPLICATE_SYNCER_PAUSE) == FLAG_DUPLICATE_SYNCER_PAUSE){
                    syncerOnPause = target.syncerOnPause.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerOnPause = target.syncerOnPause;
                }
            }
            if(syncerOnDone != null){
                if((flags & FLAG_DUPLICATE_SYNCER_DONE) == FLAG_DUPLICATE_SYNCER_DONE){
                    syncerOnDone = target.syncerOnDone.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerOnDone = target.syncerOnDone;
                }
            }

        }else{
            VLCopyable.Helper.throwMissingAllFlags();
        }

        paused = target.paused;
        isdone = target.isdone;
        endpointindex = target.endpointindex;
    }

    @Override
    public VLVManager<ENTRY> duplicate(long flags){
        return new VLVManager<>(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        boolean verbose = (boolean) data;

        log.append("[");
        log.append(getClass().getSimpleName());
        log.append("] [");
        log.append(entries.size());
        log.append("] paused[");
        log.append(paused);
        log.append("] done[");
        log.append(isdone);
        log.append("] endPointIndex[");
        log.append(endpointindex);
        log.append("] syncerOnStartType[");
        log.append(syncerOnStart == null ? "null" : syncerOnStart.getClass().getSimpleName());
        log.append("] syncerOnChangeType[");
        log.append(syncerOnChange == null ? "null" : syncerOnChange.getClass().getSimpleName());
        log.append("] syncerOnPauseType[");
        log.append(syncerOnPause == null ? "null" : syncerOnPause.getClass().getSimpleName());
        log.append("] syncerOnDoneType[");
        log.append(syncerOnDone == null ? "null" : syncerOnDone.getClass().getSimpleName());
        log.append("] data[");

        if(verbose){
            log.append("\n");
        }

        log.append("[");
        log.append(getClass().getSimpleName());
        log.append("] size[");
        log.append(size());
        log.append("] entries[");

        int size = entries.size();

        for(int i = 0; i < size; i++){
            log.append("[");
            log.append(i);
            log.append("/");
            log.append(size);
            log.append("] ");

            entries.get(i).log(log, data);
        }

        log.append("]");
    }
}
