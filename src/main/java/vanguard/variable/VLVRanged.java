package vanguard.variable;

import vanguard.math.VLMath;

public final class VLVRanged extends VLVCurved{

    private static final float[] CACHE = new float[2];

    protected Listener listener;

    protected float high;
    protected float low;
    protected float rangedvalue;
    protected int wrapcounts;

    public VLVRanged(float from, float to, int cycles, float low, float high, Loop loop, Curve curve, Listener listener){
        super(from, to, cycles, loop, curve);

        this.low = low;
        this.high = high;
        this.listener = listener;
    }

    public VLVRanged(float from, float to, int cycles, float low, float high, Curve curve, Listener listener){
        super(from, to, cycles, curve);

        this.low = low;
        this.high = high;
        this.listener = listener;
    }

    public VLVRanged(float from, float to, float changerate, float low, float high, Loop loop, Curve curve, Listener listener){
        super(from, to, changerate, loop, curve);

        this.low = low;
        this.high = high;
        this.listener = listener;
    }

    public VLVRanged(float from, float to, float changerate, float low, float high, Curve curve, Listener listener){
        super(from, to, changerate, curve);

        this.low = low;
        this.high = high;
        this.listener = listener;
    }

    public VLVRanged(VLVRanged src, long flags){
        copy(src, flags);
    }

    protected VLVRanged(){
        
    }

    @Override
    public void initialize(int cycles){
        super.initialize(cycles);
        wrapValue();
    }

    @Override
    public void initialize(float changerate){
        super.initialize(changerate);
        wrapValue();
    }

    @Override
    public void set(float s){
        super.set(s);
        wrapValue();
    }

    @Override
    public float get(){
        return rangedvalue;
    }

    @Override
    public int next(){
        int changes = super.next();

        if(changes >= 0){
            wrapValue();

            if(listener != null){
                if(wrapcounts > listener.prevwrapcount){
                    listener.crossed(this);
                }

                listener.prevwrapcount = wrapcounts;
            }
        }

        return changes;
    }

    public void push(float amount){
        push(amount, 1);
    }

    public void push(float amount, int cycles){
        change = (float)1 / cycles;
        from = value;
        to = value + amount;

        if(cycles < 0){
            value = to;
            wrapValue();
            tracker = 1;

        }else{
            tracker = 0;
        }

        if(listener != null){
            listener.prevwrapcount = 0;
        }
    }

    private void wrapValue(){
        VLMath.wrapOverRange(CACHE, 0, value, low, high);

        rangedvalue = CACHE[0];
        wrapcounts = (int)CACHE[1];
    }

    public void setLow(float low){
        this.low = low;
    }

    public void setHigh(float high){
        this.high = high;
    }

    public float getLow(){
        return low;
    }

    public float getHigh(){
        return high;
    }

    public int getWrapCounts(){
        return wrapcounts;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags) {
        super.copy(src, flags);

        VLVRanged target = (VLVRanged)src;

        high = target.high;
        low = target.low;
        rangedvalue = target.rangedvalue;
        wrapcounts = target.wrapcounts;
        listener = target.listener;
    }

    @Override
    public VLVRanged duplicate(long flags){
        return new VLVRanged(this, flags);
    }

    public static class Listener{

        private float prevwrapcount = 0;

        public Listener(){
            
        }

        public void crossed(VLVRanged v){ }
    }
}
