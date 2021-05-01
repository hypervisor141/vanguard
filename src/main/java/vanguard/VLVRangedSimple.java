package vanguard;

public class VLVRangedSimple extends VLV{

    private static final float[] CACHE = new float[2];

    protected float low, high;

    public VLVRangedSimple(float value, float low, float high){
        super(value);

        this.low = low;
        this.high = high;
    }

    public VLVRangedSimple(VLVRangedSimple src, int depth){
        copy(src, depth);
    }

    public VLVRangedSimple(){

    }

    @Override
    public void set(float s){
        VLMath.wrapOverRange(CACHE, 0, s, low, high);
        value = CACHE[0];
    }

    @Override
    public void copy(VLVTypeRunnable src, int depth){
        super.copy(src, depth);

        VLVRangedSimple target = (VLVRangedSimple)src;
        low = target.low;
        high = target.high;
    }

    @Override
    public VLVRangedSimple duplicate(int depth) {
        return new VLVRangedSimple(this, depth);
    }

    public void push(float amount, int cycles){
        push(amount);
    }

    public void push(float amount){
        set(value + amount);
    }

    public float getLow(){
        return low;
    }

    public float getHigh(){
        return high;
    }
}
