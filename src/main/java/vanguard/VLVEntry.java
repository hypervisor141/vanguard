package vanguard;

public class VLVEntry implements VLVTypeRunner{

    public VLVTypeVariable target;
    public VLSyncType<VLVEntry> syncer;

    protected int delay;
    protected int delaytracker;

    public VLVEntry(VLVTypeVariable target, int delay){
        this.target = target;
        this.delay = delay;

        delaytracker = 0;
    }

    public VLVEntry(VLVTypeVariable target, int delay, VLSyncType<VLVEntry> syncer){
        this.target = target;
        this.syncer = syncer;
        this.delay = delay;

        delaytracker = 0;
    }

    public VLVEntry(VLVEntry src, long flags){
        copy(src, flags);
    }

    @Override
    public void initialize(int cycles){
        target.initialize(cycles);
        sync();
    }

    @Override
    public void initialize(float changerate){
        target.initialize(changerate);
        sync();
    }

    @Override
    public void initializeFixedDirection(int cycles){
        target.initializeFixedDirection(cycles);
        sync();
    }

    @Override
    public void initializeFixedDirection(float changerate){
        target.initializeFixedDirection(changerate);
        sync();
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
        sync();
    }

    @Override
    public void reverse(){
        target.reverse();
    }

    @Override
    public void reset(){
        target.reset();
        resetDelayTrackers();
        sync();
    }

    @Override
    public void finish(){
        target.finish();
        sync();
    }

    @Override
    public void syncer(VLSyncType<? extends VLVTypeRunner> syncer){
        this.syncer = (VLSyncType<VLVEntry>)syncer;
    }

    @Override
    public VLSyncType<VLVEntry> syncer(){
        return syncer;
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
        target.activate();
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
                sync();
            }

        }else{
            resetDelayTrackers();
        }

        return advancement;
    }

    @Override
    public void sync(){
        if(syncer != null){
            syncer.sync(this);
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
    public void syncAll(){
        sync();
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
    public void randomizeCycles(int cyclesmin, int cyclesmax, boolean maintaindirection, boolean excludeinactive){
        if(excludeinactive && !active()){
            return;
        }

        RANDOM.setSeed(System.currentTimeMillis());
        int cycles = cyclesmin + RANDOM.nextInt(cyclesmax - cyclesmin);

        if(!maintaindirection){
            target.initialize(cycles);

        }else{
            target.initializeFixedDirection(cycles);
        }

        sync();
        activate();
    }

    @Override
    public void randomizeChangeRates(float ratemin, float ratemax, boolean maintaindirection, boolean excludeinactive){
        if(excludeinactive && !active()){
            return;
        }

        VLVManager.RANDOM.setSeed(System.currentTimeMillis());
        float changerate = VLMath.range(VLVManager.RANDOM.nextFloat(), ratemin, ratemax);

        if(!maintaindirection){
            target.initialize(changerate);

        }else{
            target.initializeFixedDirection(changerate);
        }

        sync();
        activate();
    }

    @Override
    public void randomizeDelays(int delaymin, int delaymax, boolean offsetdelay, boolean excludeinactive){
        if(excludeinactive && !active()){
            return;
        }

        VLVManager.RANDOM.setSeed(System.currentTimeMillis());

        if(!offsetdelay){
            delay(delaymin + VLVManager.RANDOM.nextInt(delaymax - delaymin));

        }else{
            delayBy(delaymin + VLVManager.RANDOM.nextInt(delaymax - delaymin));
        }

        sync();
        activate();
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        VLVEntry entry = (VLVEntry)src;

        if((flags & FLAG_REFERENCE) == FLAG_REFERENCE){
            target = entry.target;
            syncer = entry.syncer;

        }else if((flags & FLAG_DUPLICATE) == FLAG_DUPLICATE){
            target = (VLVTypeVariable)entry.target.duplicate(FLAG_DUPLICATE);
            syncer = entry.syncer.duplicate(FLAG_DUPLICATE);

        }else if((flags & FLAG_CUSTOM) == FLAG_CUSTOM){
            Helper.throwCustomCopyNotSupported(flags);

        }else{
            Helper.throwMissingBaseFlags();
        }

        delay = entry.delay;
        delaytracker = entry.delaytracker;
    }

    @Override
    public VLVEntry duplicate(long flags){
        return new VLVEntry(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("[");
        src.append(getClass().getSimpleName());
        src.append("] [");
        target.stringify(src, hint);
        src.append("]");
    }
}
