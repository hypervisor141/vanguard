package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public class VLVWrappedSimple extends VLV{

    protected final float[] CACHE = new float[2];
    protected float low;
    protected float high;
    protected float wrappedvalue;

    public VLVWrappedSimple(float value, float low, float high){
        super(value);
        changeRange(low, high);
    }

    public VLVWrappedSimple(VLVWrappedSimple src, long flags){
        copy(src, flags);
    }

    protected VLVWrappedSimple(){

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

        VLMath.wrapOverRange(CACHE, 0, value, low, high);
        wrappedvalue = CACHE[0];
    }

    public void low(float low){
        changeRange(low, high);
    }

    public void high(float high){
        changeRange(low, high);
    }

    @Override
    public float get(){
        return wrappedvalue;
    }

    public float low(){
        return low;
    }

    public float high(){
        return high;
    }

    public float getReal(){
        return super.get();
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags){
        super.copy(src, flags);

        VLVWrappedSimple target = (VLVWrappedSimple)src;
        low = target.low;
        high = target.high;
        wrappedvalue = target.wrappedvalue;
    }

    @Override
    public VLVWrappedSimple duplicate(long flags) {
        return new VLVWrappedSimple(this, flags);
    }
}
