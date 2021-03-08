package vanguard;

public class VLVManagerRoot implements VLVTypeManager<VLVTypeManager<? extends VLVTypeRunner>>{

    private static final Length LENGTH_CACHE = new Length();

    protected VLListType<VLVTypeManager<? extends VLVTypeRunner>> entries;
    protected VLSyncType<VLVManagerRoot> syncer;
    protected int endpointindex;

    public VLVManagerRoot(int capacity, int resizer, VLSyncType<VLVManagerRoot> syncer){
        entries = new VLListType<>(capacity, resizer);
        endpointindex = -1;

        this.syncer = syncer;
    }

    public VLVManagerRoot(int capacity, int resizer){
        entries = new VLListType<>(capacity, resizer);
        endpointindex = -1;
    }

    @Override
    public void start(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).start();
        }
    }

    @Override
    public void pause(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).pause();
        }
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
    public void initializeFixedDirection(int cycles){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initializeFixedDirection(cycles);
        }
    }

    @Override
    public void initializeFixedDirection(float changerate){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).initializeFixedDirection(changerate);
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
        int size = entries.size();
        int count = 0;

        for(int i = 0; i < size; i++){
            count += entries.get(i).next();
        }

        if(count != 0 && syncer != null){
            syncer.sync(this);
        }

        return count;
    }

    @Override
    public void sync(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).sync();
        }

        if(syncer != null){
            syncer.sync(this);
        }
    }

    @Override
    public void fastForward(int count){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).fastForward(count);
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
    public void delay(int delay){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).delay(delay);
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
    }

    @Override
    public void randomizeChangeRates(float ratemin, float ratemax, boolean maintaindirection, boolean excludeinactive){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).randomizeChangeRates(ratemin, ratemax, maintaindirection, excludeinactive);
        }
    }

    @Override
    public void randomizeDelays(int delaymin, int delaymax, boolean offsetdelay, boolean excludeinactive){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).randomizeDelays(delaymin, delaymax, offsetdelay, excludeinactive);
        }
    }

    @Override
    public void endPointIndex(int index){
        endpointindex = index;
    }

    @Override
    public void findEndPointIndex(){
        length(LENGTH_CACHE);
        endpointindex = LENGTH_CACHE.index;

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
    public void add(VLVTypeManager<? extends VLVTypeRunner> runner){
        entries.add(runner);
    }

    @Override
    public VLVTypeManager<? extends VLVTypeRunner> set(int index, VLVTypeManager<? extends VLVTypeRunner> runner){
        VLVTypeManager<? extends VLVTypeRunner> entry = entries.get(index);
        entries.set(index, runner);

        return entry;
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
        clear();
    }

    @Override
    public VLSyncType<VLVManagerRoot> syncer(){
        return syncer;
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
    public float length(){
        length(LENGTH_CACHE);
        return LENGTH_CACHE.length;
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
    public VLVTypeManager<? extends VLVTypeRunner> get(int index){
        return entries.get(index);
    }

    @Override
    public VLListType<VLVTypeManager<? extends VLVTypeRunner>> get(){
        return entries;
    }

    @Override
    public VLVTypeRunnable endPoint(){
        return entries.get(endpointindex).endPoint();
    }

    @Override
    public int endPointIndex(){
        return endpointindex;
    }

    @Override
    public int size(){
        return entries.size();
    }

    @Override
    public boolean paused(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            if(!entries.get(i).paused()){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean done(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            if(!entries.get(i).done()){
                return false;
            }
        }

        return true;
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
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
