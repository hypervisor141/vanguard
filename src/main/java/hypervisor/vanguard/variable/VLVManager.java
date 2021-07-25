package hypervisor.vanguard.variable;

import hypervisor.vanguard.list.VLListType;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;

@SuppressWarnings("unused")
public class VLVManager<ENTRY extends VLVTypeRunner> implements VLVTypeManager<ENTRY>{

    public static final long FLAG_REFERENCE_ENTRIES = 0x1L;
    public static final long FLAG_DUPLICATE_ENTRIES = 0x2L;
    public static final long FLAG_DUPLICATE_SYNCER_START = 0x4L;
    public static final long FLAG_DUPLICATE_SYNCER_CHANGE = 0x8L;
    public static final long FLAG_DUPLICATE_SYNCER_DONE = 0x10L;

    protected boolean paused;
    protected boolean isdone;
    protected int endpointindex;

    protected VLListType<ENTRY> entries;
    protected VLSyncType<VLVManager<ENTRY>> syncerStart;
    protected VLSyncType<VLVManager<ENTRY>> syncerChange;
    protected VLSyncType<VLVManager<ENTRY>> syncerDone;

    public VLVManager(int capacity, int resizer){
        entries = new VLListType<>(capacity, resizer);

        paused = true;
        isdone = true;
        endpointindex = -1;
    }

    public VLVManager(int capacity, int resizer, VLSyncType<VLVManager<ENTRY>> syncerChange){
        entries = new VLListType<>(capacity, resizer);

        this.syncerChange = syncerChange;

        paused = true;
        isdone = true;
        endpointindex = -1;
    }

    public VLVManager(int capacity, int resizer, VLSyncType<VLVManager<ENTRY>> syncerStart, VLSyncType<VLVManager<ENTRY>> syncerChange, VLSyncType<VLVManager<ENTRY>> syncerDone){
        entries = new VLListType<>(capacity, resizer);

        this.syncerStart = syncerStart;
        this.syncerChange = syncerChange;
        this.syncerDone = syncerDone;

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
                syncDone();

            }else{
                syncChange();
            }

            return count;

        }else{
            return 0;
        }
    }

    @Override
    public void start(){
        paused = false;
        isdone = false;

        syncStart();
    }

    @Override
    public void pause(){
        paused = true;
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
    public void syncStartAll(){
        syncStart();

        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).syncStartAll();
        }
    }

    @Override
    public void syncChangeAll(){
        syncChange();

        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).syncChangeAll();
        }
    }

    @Override
    public void syncDoneAll(){
        syncDone();

        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).syncDoneAll();
        }
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
    }

    @Override
    public void chain(float changerate, float to){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).chain(changerate, to);
        }
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
    }

    @Override
    public void finish(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).finish();
        }

        syncChange();
        syncDone();
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
    public void syncerStart(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerStart = (VLSyncType<VLVManager<ENTRY>>)syncer;
    }

    @Override
    public void syncerChange(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerChange = (VLSyncType<VLVManager<ENTRY>>)syncer;
    }

    @Override
    public void syncerDone(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncerDone = (VLSyncType<VLVManager<ENTRY>>)syncer;
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
    public VLSyncType<VLVManager<ENTRY>> syncerStart(){
        return syncerStart;
    }

    @Override
    public VLSyncType<VLVManager<ENTRY>> syncerChange(){
        return syncerChange;
    }

    @Override
    public VLSyncType<VLVManager<ENTRY>> syncerDone(){
        return syncerDone;
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
            syncerStart = target.syncerStart;
            syncerChange = target.syncerChange;
            syncerDone = target.syncerDone;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            entries = target.entries.duplicate(VLCopyable.FLAG_DUPLICATE);

            if(syncerStart != null){
                syncerStart = target.syncerStart.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerChange != null){
                syncerChange = target.syncerChange.duplicate(VLCopyable.FLAG_DUPLICATE);
            }
            if(syncerDone != null){
                syncerDone = target.syncerDone.duplicate(VLCopyable.FLAG_DUPLICATE);
            }

        }else if((flags & VLCopyable.FLAG_CUSTOM) == VLCopyable.FLAG_CUSTOM){
            if((flags & FLAG_REFERENCE_ENTRIES) == FLAG_REFERENCE_ENTRIES){
                entries = target.entries.duplicate(VLCopyable.FLAG_CUSTOM | VLListType.FLAG_DUPLICATE_ARRAY_BUT_REFERENCE_ELEMENTS);

            }else if((flags & FLAG_DUPLICATE_ENTRIES) == FLAG_DUPLICATE_ENTRIES){
                entries = target.entries.duplicate(VLCopyable.FLAG_CUSTOM | VLListType.FLAG_DUPLICATE_ARRAY_FULLY);

            }else{
                VLCopyable.Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_REFERENCE_ENTRIES", "FLAG_DUPLICATE_ENTRIES");
            }

            if(syncerStart != null){
                if((flags & FLAG_DUPLICATE_SYNCER_START) == FLAG_DUPLICATE_SYNCER_START){
                    syncerStart = target.syncerStart.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerStart = target.syncerStart;
                }
            }
            if(syncerChange != null){
                if((flags & FLAG_DUPLICATE_SYNCER_CHANGE) == FLAG_DUPLICATE_SYNCER_CHANGE){
                    syncerChange = target.syncerChange.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerChange = target.syncerChange;
                }
            }
            if(syncerDone != null){
                if((flags & FLAG_DUPLICATE_SYNCER_DONE) == FLAG_DUPLICATE_SYNCER_DONE){
                    syncerDone = target.syncerDone.duplicate(VLCopyable.FLAG_DUPLICATE);

                }else{
                    syncerDone = target.syncerDone;
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
        log.append("] syncerStartType[");
        log.append(syncerStart == null ? "null" : syncerStart.getClass().getSimpleName());
        log.append("] syncerChangeType[");
        log.append(syncerChange == null ? "null" : syncerChange.getClass().getSimpleName());
        log.append("] syncerDoneType[");
        log.append(syncerDone == null ? "null" : syncerDone.getClass().getSimpleName());
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
