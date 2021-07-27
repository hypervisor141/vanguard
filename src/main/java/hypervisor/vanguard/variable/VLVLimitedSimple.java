package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public class VLVLimitedSimple extends VLV{

    protected float low;
    protected float high;
    protected float limitedvalue;

    public VLVLimitedSimple(float value, float low, float high){
        super(value);
        changeRange(low, high);
    }

    public VLVLimitedSimple(VLVLimitedSimple src, long flags){
        copy(src, flags);
    }

    public VLVLimitedSimple(){

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
        limitedvalue = VLMath.limit(value, low, high);
    }

    @Override
    public float get(){
        return limitedvalue;
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

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        super.copy(src, flags);

        VLVLimitedSimple target = (VLVLimitedSimple)src;
        low = target.low;
        high = target.high;
        limitedvalue = target.limitedvalue;
    }

    @Override
    public VLVLimitedSimple duplicate(long flags) {
        return new VLVLimitedSimple(this, flags);
    }
}
