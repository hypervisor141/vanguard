package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public class VLVLimited extends VLVCurved{

    protected float low;
    protected float high;
    protected float limitedvalue;

    public VLVLimited(float from, float to, float low, float high, int cycles, Loop loop, Curve curve){
        super(from, to, cycles, loop, curve);
        changeRange(low, high);
    }

    public VLVLimited(float from, float to, float low, float high, int cycles, Curve curve){
        super(from, to, cycles, curve);
        changeRange(low, high);
    }

    public VLVLimited(float from, float to, float low, float high, float changerate, Loop loop, Curve curve){
        super(from, to, changerate, loop, curve);
        changeRange(low, high);
    }

    public VLVLimited(float from, float to, float low, float high, float changerate, Curve curve){
        super(from, to, changerate, curve);
        changeRange(low, high);
    }

    public VLVLimited(VLVLimited src, long flags){
        copy(src, flags);
    }

    protected VLVLimited(){

    }

    private void updateValue(){
        limitedvalue = VLMath.limit(get(), low, high);
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
        updateValue();
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
    public void copy(VLVTypeRunnable src, long flags) {
        super.copy(src, flags);

        VLVLimited target = (VLVLimited)src;

        low = target.low;
        high = target.high;
        limitedvalue = target.limitedvalue;
    }

    @Override
    public VLVLimited duplicate(long flags){
        return new VLVLimited(this, flags);
    }
}
