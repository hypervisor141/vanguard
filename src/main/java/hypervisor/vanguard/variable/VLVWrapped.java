package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public final class VLVWrapped extends VLVCurved{

    private final float[] CACHE = new float[2];
    protected Listener listener;

    protected float high;
    protected float low;
    protected float wrappedvalue;
    protected int wrapcounts;

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

    private void changeRange(float low, float high){
        if(low < high){
            this.low = low;
            this.high = high;

        }else{
            this.low = high;
            this.high = low;
        }
    }

    private void updateValue(){
        VLMath.wrapOverRange(CACHE, 0, value, low, high);

        wrappedvalue = CACHE[0];
        wrapcounts = (int)CACHE[1];
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

    public float realValue(){
        return value;
    }

    public int wrapsCount(){
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
