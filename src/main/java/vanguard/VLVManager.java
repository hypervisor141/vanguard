package vanguard;

public final class VLVManager implements VLVTypeManager<VLVEntry>{

    private static final Length CACHE = new Length();

    private boolean pause;
    private boolean directmode;
    private boolean isdone;

    private int endpointindex;

    private VLListType<VLVEntry> entries;
    private VLSyncType<VLVManager> syncer;

    public VLVManager(int capacity, int resizer, VLSyncType<VLVManager> syncer){
        entries = new VLListType<>(capacity, resizer);
        this.syncer = syncer;

        pause = true;
        directmode = false;
        endpointindex = -1;
    }

    public VLVManager(int capacity, int resizer){
        entries = new VLListType<>(capacity, resizer);

        pause = true;
        directmode = false;
        endpointindex = -1;
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
        if(!pause && !directmode){
            return iterate();

        }else{
            return 0;
        }
    }

    public int nextIsolated(long sleep, Object lock, IsolatedControl controller){
        int count = 0;
        long totalsleep = 0;
        int currentcount;

        synchronized(lock){
            directmode = true;
            controller.preLoop(this);
        }

        while(true){
            synchronized(lock){
                controller.preIterate(this);

                currentcount = iterate();

                controller.postIterate(this);
            }

            count += currentcount;

            try{
                Thread.sleep(sleep);
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }

            if(currentcount == 0){
                break;
            }
        }

        synchronized(lock){
            directmode = false;
            controller.postLoop(this);
        }

        return count;
    }

    private int iterate(){
        int count = 0;

        for(int i = 0; i < entries.size(); i++){
            count += entries.get(i).next();
        }

        if(count == 0){
            isdone = true;
            pause();

        }else if(syncer != null){
            syncer.sync(this);
        }

        return count;
    }

    @Override
    public void start(){
        pause = false;
        isdone = false;
    }

    @Override
    public void pause(){
        pause = true;
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
        for(int i = 0; i < count; i++){
            iterate();
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

        resetDelayTrackers();
    }

    @Override
    public void reset(){
        int size = entries.size();

        for(int i = 0; i < size; i++){
            entries.get(i).reset();
        }

        resetDelayTrackers();
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
        length(CACHE);
        endpointindex = CACHE.index;
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
    public void add(VLVEntry entry){
        entries.add(entry);
    }

    @Override
    public VLVEntry set(int index, VLVEntry entry){
        VLVEntry e = entries.get(index);
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
        clear();
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
    public VLSyncType<VLVManager> syncer(){
        return syncer;
    }

    @Override
    public float length(){
        length(CACHE);
        return CACHE.length;
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
    public VLVEntry get(int index){
        return entries.get(index);
    }

    @Override
    public VLListType<VLVEntry> get(){
        return entries;
    }

    @Override
    public int size(){
        return entries.size();
    }

    @Override
    public boolean paused(){
        return pause;
    }

    @Override
    public boolean done(){
        return isdone;
    }

    @Override
    public void stringify(StringBuilder info, Object hint){
        boolean verbose = (boolean)hint;

        info.append("[");
        info.append(getClass().getSimpleName());
        info.append("] [");
        info.append(entries.size());
        info.append("] paused[");
        info.append(pause);
        info.append("] directmode[");
        info.append(directmode);
        info.append("] data[");

        if(verbose){
            info.append("\n");
        }

        info.append("[");

        for(int i = 0; i < entries.size(); i++){
            info.append("[");
            entries.get(i).stringify(info, verbose);
            info.append("]");

            if(i != entries.size() - 1){
                info.append(", ");

                if(verbose){
                    info.append("\n");
                }

            }else{
                info.append("]");
            }
        }

        info.append("]");
    }

    public static interface IsolatedControl{

        void preLoop(VLVManager proc);
        void preIterate(VLVManager proc);
        void postIterate(VLVManager proc);
        void postLoop(VLVManager proc);
    }

}
