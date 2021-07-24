package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public class VLVLimitedSimple extends VLV{

    protected float low, high;

    public VLVLimitedSimple(float value, float low, float high){
        super(value);
        changeRange(low, high);
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

    private void changeRange(float low, float high){
        if(low < high){
            this.low = low;
            this.high = high;

        }else{
            this.low = high;
            this.high = low;
        }
    }

    public void setLow(float low){
        changeRange(low, high);
    }

    public void setHigh(float high){
        changeRange(low, high);
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