package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public class VLVWrappedSimple extends VLV{

    private final float[] CACHE = new float[2];
    protected float low;
    protected float high;

    public VLVWrappedSimple(float value, float low, float high){
        super(value);

        this.low = low;
        this.high = high;
    }

    public VLVWrappedSimple(VLVWrappedSimple src, long flags){
        copy(src, flags);
    }

    protected VLVWrappedSimple(){

    }

    @Override
    public void set(float value){
        super.set(value);

        VLMath.wrapOverRange(CACHE, 0, value, low, high);
        this.value = CACHE[0];
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        super.copy(src, flags);

        VLVWrappedSimple target = (VLVWrappedSimple)src;
        low = target.low;
        high = target.high;
    }

    @Override
    public VLVWrappedSimple duplicate(long flags) {
        return new VLVWrappedSimple(this, flags);
    }

    public float getLow(){
        return low;
    }

    public float getHigh(){
        return high;
    }
}
