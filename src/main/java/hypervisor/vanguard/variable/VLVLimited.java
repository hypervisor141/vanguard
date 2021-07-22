package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public class VLVLimited extends VLVCurved{

    protected float low;
    protected float high;
    protected float limitedvalue;

    public VLVLimited(float from, float to, int cycles, float low, float high, Loop loop, Curve curve){
        super(from, to, cycles, loop, curve);

        this.low = low;
        this.high = high;
    }

    public VLVLimited(float from, float to, int cycles, float low, float high, Curve curve){
        super(from, to, cycles, curve);

        this.low = low;
        this.high = high;
    }

    public VLVLimited(float from, float to, float changerate, float low, float high, Loop loop, Curve curve){
        super(from, to, changerate, loop, curve);

        this.low = low;
        this.high = high;
    }

    public VLVLimited(float from, float to, float changerate, float low, float high, Curve curve){
        super(from, to, changerate, curve);

        this.low = low;
        this.high = high;
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

    private void updateValue(){
        limitedvalue = VLMath.limit(value, low, high);
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
