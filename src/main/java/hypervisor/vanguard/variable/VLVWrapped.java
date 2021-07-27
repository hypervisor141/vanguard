package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public final class VLVWrapped extends VLVCurved{

    protected final float[] CACHE = new float[2];
    protected Listener listener;

    protected float high;
    protected float low;
    protected float wrappedvalue;
    protected int wrapscount;

    public VLVWrapped(float from, float to, float low, float high, int cycles, Loop loop, Curve curve, Listener listener){
        super(from, to, cycles, loop, curve);

        changeRange(low, high);
        this.listener = listener;
    }

    public VLVWrapped(float from, float to, float low, float high, int cycles, Curve curve, Listener listener){
        super(from, to, cycles, curve);

        changeRange(low, high);
        this.listener = listener;
    }

    public VLVWrapped(float from, float to, float low, float high, float changerate, Loop loop, Curve curve, Listener listener){
        super(from, to, changerate, loop, curve);

        changeRange(low, high);
        this.listener = listener;
    }

    public VLVWrapped(float from, float to, float low, float high, float changerate, Curve curve, Listener listener){
        super(from, to, changerate, curve);

        changeRange(low, high);
        this.listener = listener;
    }

    public VLVWrapped(VLVWrapped src, long flags){
        copy(src, flags);
    }

    public VLVWrapped(){

    }

    @Override
    public int next(){
        int changes = super.next();

        if(changes > 0){
            checkValue();
        }

        return changes;
    }

    private void checkValue(){
        VLMath.wrapOverRange(CACHE, 0, get(), low, high);

        wrappedvalue = CACHE[0];
        wrapscount = (int)CACHE[1];

        if(listener != null){
            if(wrapscount > listener.prevwrapscount){
                listener.wrapped(this);
            }

            listener.prevwrapscount = wrapscount;
        }
    }

    private void changeRange(float low, float high){
        if(low < high){
            this.low = low;
            this.high = high;

        }else{
            this.low = high;
            this.high = low;
        }
    }

    @Override
    public void set(float value){
        super.set(value);
        checkValue();
    }

    @Override
    public float get(){
        return wrappedvalue;
    }

    public float getReal(){
        return value;
    }

    public void low(float low){
        changeRange(low, high);
    }

    public void high(float high){
        changeRange(low, high);
    }

    public float low(){
        return low;
    }

    public float high(){
        return high;
    }

    public int wrapsCount(){
        return wrapscount;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags) {
        super.copy(src, flags);

        VLVWrapped target = (VLVWrapped)src;

        high = target.high;
        low = target.low;
        wrappedvalue = target.wrappedvalue;
        wrapscount = target.wrapscount;
        listener = target.listener;
    }

    @Override
    public VLVWrapped duplicate(long flags){
        return new VLVWrapped(this, flags);
    }

    public static class Listener{

        private float prevwrapscount = 0;

        public Listener(){
            
        }

        public void wrapped(VLVWrapped v){ }
    }
}
