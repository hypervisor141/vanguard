package vanguard;

@SuppressWarnings("unused")
public class VLVManager<ENTRY extends VLVTypeRunner> implements VLVTypeManager<ENTRY>{

    private boolean paused;
    private boolean isdone;
    private int endpointindex;

    private final VLListType<ENTRY> entries;
    private VLSyncType<VLVManager<ENTRY>> syncer;

    public VLVManager(int capacity, int resizer, VLSyncType<VLVManager<ENTRY>> syncer){
        entries = new VLListType<>(capacity, resizer);
        this.syncer = syncer;

        paused = true;
        endpointindex = -1;
    }

    public VLVManager(int capacity, int resizer){
        entries = new VLListType<>(capacity, resizer);

        paused = true;
        endpointindex = -1;
    }

    @Override
    public void initialize(int cycles){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initialize(cycles);
        }

        sync();
    }

    @Override
    public void initialize(float changerate){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initialize(changerate);
        }

        sync();
    }

    @Override
    public void initializeFixedDirection(int cycles){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initializeFixedDirection(cycles);
        }

        sync();
    }

    @Override
    public void initializeFixedDirection(float changerate){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initializeFixedDirection(changerate);
        }

        sync();
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

            }else{
                sync();
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

        sync();
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

        sync();
    }

    @Override
    public void reverse(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).reverse();
        }

        resetDelayTrackers();
    }

    @Override
    public void reset(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).reset();
        }

        resetDelayTrackers();
        sync();
    }

    @Override
    public void finish(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).finish();
        }

        sync();
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
    public void randomizeCycles(int cyclesmin, int cyclesmax, boolean maintaindirection, boolean excludeinactive){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).randomizeCycles(cyclesmin, cyclesmax, maintaindirection, excludeinactive);
        }

        sync();
    }

    @Override
    public void randomizeChangeRates(float ratemin, float ratemax, boolean maintaindirection, boolean excludeinactive){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).randomizeChangeRates(ratemin, ratemax, maintaindirection, excludeinactive);
        }

        sync();
    }

    @Override
    public void randomizeDelays(int delaymin, int delaymax, boolean offsetdelay, boolean excludeinactive){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).randomizeDelays(delaymin, delaymax, offsetdelay, excludeinactive);
        }

        sync();
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
    public void stringify(StringBuilder src, Object hint){
        boolean verbose = (boolean)hint;

        src.append("[");
        src.append(getClass().getSimpleName());
        src.append("] [");
        src.append(entries.size());
        src.append("] paused[");
        src.append(paused);
        src.append("] done[");
        src.append(isdone);
        src.append("] endPointIndex[");
        src.append(endpointindex);
        src.append("] syncerType[");
        src.append(syncer.getClass().getSimpleName());
        src.append("] data[");

        if(verbose){
            src.append("\n");
        }

        src.append("[");
        src.append(getClass().getSimpleName());
        src.append("] size[");
        src.append(size());
        src.append("] entries[");

        int size = entries.size();

        for(int i = 0; i < size; i++){
            src.append("[");
            src.append(i);
            src.append("/");
            src.append(size);
            src.append("] ");

            entries.get(i).stringify(src, hint);
        }

        src.append("]");
    }
}
