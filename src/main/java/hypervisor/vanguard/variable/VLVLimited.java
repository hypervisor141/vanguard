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
        return limitedvalue;
    }

    @Override
    public int next(){
        int changes = super.next();
        updateValue();

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
        limitedvalue = VLMath.limit(value, low, high);
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
