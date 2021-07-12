package hypervisor.vanguard.variable;

import hypervisor.vanguard.sync.VLSyncType;
import hypervisor.vanguard.utils.VLCopyable;
import hypervisor.vanguard.utils.VLLog;

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
    public void copy(VLVTypeRunnable src, long flags){
        VLVEntry entry = (VLVEntry)src;

        if((flags & VLCopyable.FLAG_REFERENCE) == VLCopyable.FLAG_REFERENCE){
            target = entry.target;
            syncer = entry.syncer;

        }else if((flags & VLCopyable.FLAG_DUPLICATE) == VLCopyable.FLAG_DUPLICATE){
            target = (VLVTypeVariable)entry.target.duplicate(VLCopyable.FLAG_DUPLICATE);

            if(syncer != null){
                syncer = entry.syncer.duplicate(VLCopyable.FLAG_DUPLICATE);
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
        log.append("] [");
        target.log(log, data);
        log.append("]");
    }
}
