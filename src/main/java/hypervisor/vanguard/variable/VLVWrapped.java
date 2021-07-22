package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public final class VLVWrapped extends VLVCurved{

    private final float[] CACHE = new float[2];
    protected Listener listener;

    protected float high;
    protected float low;
    protected float wrappedvalue;
    protected int wrapcounts;

    public VLVWrapped(float from, float to, int cycles, float low, float high, Loop loop, Curve curve, Listener listener){
        super(from, to, cycles, loop, curve);

        this.low = low;
        this.high = high;
        this.listener = listener;
    }

    public VLVWrapped(float from, float to, int cycles, float low, float high, Curve curve, Listener listener){
        super(from, to, cycles, curve);

        this.low = low;
        this.high = high;
        this.listener = listener;
    }

    public VLVWrapped(float from, float to, float changerate, float low, float high, Loop loop, Curve curve, Listener listener){
        super(from, to, changerate, loop, curve);

        this.low = low;
        this.high = high;
        this.listener = listener;
    }

    public VLVWrapped(float from, float to, float changerate, float low, float high, Curve curve, Listener listener){
        super(from, to, changerate, curve);

        this.low = low;
        this.high = high;
        this.listener = listener;
    }

    public VLVWrapped(VLVWrapped src, long flags){
        copy(src, flags);
    }

    protected VLVWrapped(){
        
    }

    @Override
    public void initialize(float from, float to, float changerate){
        super.initialize(from, to, changerate);
        updateValue();
    }

    @Override
    public void set(float value){
        super.set(value);
        updateValue();
    }

    @Override
    public float get(){
        return wrappedvalue;
    }

    @Override
    public int next(){
        int changes = super.next();

        if(changes >= 0){
            updateValue();

            if(listener != null){
                if(wrapcounts > listener.prevwrapcount){
                    listener.crossed(this);
                }

                listener.prevwrapcount = wrapcounts;
            }
        }

        return changes;
    }

    private void updateValue(){
        VLMath.wrapOverRange(CACHE, 0, value, low, high);

        wrappedvalue = CACHE[0];
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

        VLVWrapped target = (VLVWrapped)src;

        high = target.high;
        low = target.low;
        wrappedvalue = target.wrappedvalue;
        wrapcounts = target.wrapcounts;
        listener = target.listener;
    }

    @Override
    public VLVWrapped duplicate(long flags){
        return new VLVWrapped(this, flags);
    }

    public static class Listener{

        private float prevwrapcount = 0;

        public Listener(){
            
        }

        public void crossed(VLVWrapped v){ }
    }
}
