package hypervisor.vanguard.variable;

public abstract class VLVariable extends VLV{

    public static final LoopForward LOOP_FORWARD = new LoopForward();
    public static final LoopForwardBackward LOOP_FORWARD_BACKWARD = new LoopForwardBackward();
    public static final LoopReturnOnce LOOP_RETURN_ONCE = new LoopReturnOnce();
    public static final LoopReturning LOOP_RETURNING = new LoopReturning();
    public static final LoopNone LOOP_NONE = new LoopNone();

    protected float from;
    protected float to;
    protected float change;
    protected float director;
    protected boolean active;

    protected Loop loop;

    public VLVariable(float from, float to, int cycles, Loop loop){
        this.loop = loop;
        initialize(from, to, cycles);
        active = false;
    }

    public VLVariable(float from, float to, float changerate, Loop loop){
        this.loop = loop;
        initialize(from, to, changerate);
        active = false;
    }

    public VLVariable(VLVariable src, long flags){
        copy(src, flags);
    }

    public VLVariable(){

    }

    @Override
    public void initialize(int cycles){
        initialize(from, to, cycles);
    }

    @Override
    public void initialize(float changerate){
        initialize(from, to, changerate);
    }

    @Override
    public void initialize(float from, float to, int cycles){
        initialize(from, to, convertCyclesToChangeRate(from, to, cycles));
    }

    @Override
    public void initialize(float from, float to, float changerate){
        if(from > to){
            this.from = -from;
            this.to = -to;
            director = -1;

        }else{
            this.from = from;
            this.to = to;
            director = 1;
        }

        change = changerate;
        value = (changerate >= 0 ? from : to);

        loop.initialized(this);
    }

    @Override
    public abstract float convertCyclesToChangeRate(float from, float to, int cycles);

    @Override
    public void activate(){
        active = true;
    }

    @Override
    public void deactivate(){
        active = false;
    }

    public void setLoop(Loop loop){
        this.loop = loop;
    }

    @Override
    public void changeRate(float changerate){
        change = changerate;
    }

    @Override
    public int next(){
        if(active){
            int changes = advance();

            if(!active){
                loop.process(this);
            }

            return changes;
        }

        return 0;
    }

    @Override
    public void fastForward(int count){
        for(int i = 0; i < count; i++){
            next();
        }
    }

    @Override
    public void finish(){
        value = (change >= 0 ? to : from) * director;
        deactivate();
    }

    @Override
    public void reverse(){
        change = -change;
    }

    @Override
    public void reset(){
        value = (change >= 0 ? from : to) * director;
        loop.reseted(this);
    }

    @Override
    public void chain(int cycles, float to){
        initialize(value, to, cycles);
    }

    @Override
    public void chain(float changerate, float to){
        initialize(value, to, changerate);
    }

    public Loop loop(){
        return loop;
    }

    public float from(){
        return from;
    }

    public float to(){
        return to;
    }

    public float director(){
        return director;
    }

    @Override
    public float changeRate(){
        return change;
    }

    @Override
    public float length(){
        return Math.abs(change / (to - from));
    }

    @Override
    public boolean isIncreasing(){
        return change > 0;
    }

    @Override
    public boolean reversed(){
        return director >= 0 ? change < 0 : change > 0;
    }

    @Override
    public boolean active(){
        return active;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags) {
        super.copy(src, flags);

        VLVariable target = (VLVariable)src;

        from = target.from;
        to = target.to;
        change = target.change;
        active = target.active;
        loop = target.loop;
    }

    @Override
    public VLVariable duplicate(long flags){
        throw new UnsupportedOperationException("Can't duplicate this class directly");
    }

    public abstract static class Loop{

        public abstract void initialized(VLVariable var);
        public abstract void process(VLVariable var);
        public abstract void reseted(VLVariable var);
    }

    public static class LoopNone extends Loop{

        @Override
        public void initialized(VLVariable var){

        }

        @Override
        public void process(VLVariable var){

        }

        @Override
        public void reseted(VLVariable var){

        }
    }

    public static class LoopForward extends Loop{

        @Override
        public void initialized(VLVariable var){

        }

        @Override
        public void process(VLVariable var){
            var.reset();
            var.activate();
        }

        @Override
        public void reseted(VLVariable var){

        }
    }

    public static class LoopForwardBackward extends Loop{

        @Override
        public void initialized(VLVariable var){

        }

        @Override
        public void process(VLVariable var){
            var.reverse();
            var.reset();
            var.activate();
        }

        @Override
        public void reseted(VLVariable var){

        }
    }

    public static class LoopReturnOnce extends Loop{

        @Override
        public void initialized(VLVariable var){

        }

        @Override
        public void process(VLVariable var){
            var.reverse();
            var.reset();
            var.activate();
            var.setLoop(LOOP_RETURNING);
        }

        @Override
        public void reseted(VLVariable var){

        }
    }

    public static class LoopReturning extends Loop{

        @Override
        public void initialized(VLVariable var){
            var.reverse();
            var.setLoop(LOOP_RETURN_ONCE);
        }

        @Override
        public void process(VLVariable var){
            initialized(var);
        }

        @Override
        public void reseted(VLVariable var){
            initialized(var);
        }
    }
}
