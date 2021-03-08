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

    public VLVEntry(VLVTypeVariable target, VLSyncType<VLVEntry> syncer, int delay){
        this.target = target;
        this.syncer = syncer;
        this.delay = delay;

        delaytracker = 0;
    }

    @Override
    public void initialize(int cycles){
        target.initialize(cycles);
    }

    @Override
    public void initialize(float changerate){
        target.initialize(changerate);
    }

    @Override
    public void initializeFixedDirection(int cycles){
        target.initializeFixedDirection(cycles);
    }

    @Override
    public void initializeFixedDirection(float changerate){
        target.initializeFixedDirection(changerate);
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
    public void reverse(){
        target.reverse();
    }

    @Override
    public void reset(){
        target.reset();
    }

    @Override
    public void finish(){
        target.finish();
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

        activate();
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
