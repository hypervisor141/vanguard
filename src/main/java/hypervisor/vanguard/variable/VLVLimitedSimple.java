package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public class VLVLimitedSimple extends VLV{

    protected float low, high;

    public VLVLimitedSimple(float value, float low, float high){
        super(value);

        this.low = low;
        this.high = high;
    }

    public VLVLimitedSimple(VLVLimitedSimple src, long flags){
        copy(src, flags);
    }

    protected VLVLimitedSimple(){

    }

    @Override
    public void set(float value){
        super.set(value);
        this.value = VLMath.limit(value, low, high);
    }

    public float getLow(){
        return low;
    }

    public float getHigh(){
        return high;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        super.copy(src, flags);

        VLVLimitedSimple target = (VLVLimitedSimple)src;
        low = target.low;
        high = target.high;
    }

    @Override
    public VLVLimitedSimple duplicate(long flags) {
        return new VLVLimitedSimple(this, flags);
    }
}
