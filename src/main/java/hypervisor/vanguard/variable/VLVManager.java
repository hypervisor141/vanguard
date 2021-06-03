package hypervisor.vanguard.variable;

import hypervisor.vanguard.list.VLListType;
import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;

@SuppressWarnings("unused")
public class VLVManager<ENTRY extends VLVTypeRunner> implements VLVTypeManager<ENTRY>{

    public static final long FLAG_FORCE_REFERENCE_ENTRIES = 0x1L;
    public static final long FLAG_FORCE_DUPLICATE_ENTRIES = 0x2L;

    protected boolean paused;
    protected boolean isdone;
    protected int endpointindex;

    protected VLListType<ENTRY> entries;
    protected VLSyncType<VLVManager<ENTRY>> syncer;

    public VLVManager(int capacity, int resizer, VLSyncType<VLVManager<ENTRY>> syncer){
        entries = new VLListType<>(capacity, resizer);
        this.syncer = syncer;

        paused = true;
        isdone = true;
        endpointindex = -1;
    }

    public VLVManager(int capacity, int resizer){
        entries = new VLListType<>(capacity, resizer);

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
            }

            sync();

            return count;

        }else{
            return 0;
        }
    }

    @Override
    public void start(){
        paused = false;
        isdone = false;
    }

    @Override
    public void pause(){
        paused = true;
    }

    @Override
    public void sync(){
        if(syncer != null){
            syncer.sync(this);
        }
    }

    @Override
    public void startAll(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).startAll();
        }

        start();
    }

    @Override
    public void pauseAll(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).pauseAll();
        }

        pause();
    }

    @Override
    public void syncAll(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).syncAll();
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
    public void syncer(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncer = (VLSyncType<VLVManager<ENTRY>>)syncer;
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
    public VLSyncType<VLVManager<ENTRY>> syncer(){
        return syncer;
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
            syncer = target.syncer;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            entries = target.entries.duplicate(VLCopyable.FLAG_DUPLICATE);
            syncer = target.syncer.duplicate(VLCopyable.FLAG_DUPLICATE);

        }else if((flags & VLCopyable.FLAG_CUSTOM) == VLCopyable.FLAG_CUSTOM){
            if((flags & FLAG_FORCE_REFERENCE_ENTRIES) == FLAG_FORCE_REFERENCE_ENTRIES){
                entries = target.entries.duplicate(VLCopyable.FLAG_CUSTOM | VLListType.FLAG_FORCE_REFERENCE_ARRAY);

            }else if((flags & FLAG_FORCE_DUPLICATE_ENTRIES) == FLAG_FORCE_DUPLICATE_ENTRIES){
                entries = target.entries.duplicate(VLCopyable.FLAG_CUSTOM | VLListType.FLAG_FORCE_DUPLICATE_ARRAY);

            }else{
                VLCopyable.Helper.throwMissingSubFlags("FLAG_CUSTOM", "FLAG_FORCE_REFERENCE_ENTRIES", "FLAG_FORCE_DUPLICATE_ENTRIES");
            }

            syncer = target.syncer.duplicate(VLCopyable.FLAG_DUPLICATE);

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
        log.append("] syncerType[");
        log.append(syncer.getClass().getSimpleName());
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
