package hypervisor.vanguard.variable;

import hypervisor.vanguard.math.VLMath;

public class VLVCurved extends VLVariable{

    public static final CurveConstant CURVE_LINEAR = new CurveConstant();
    public static final CurveCosAcc CURVE_ACC_COS = new CurveCosAcc();
    public static final CurveSineSqrtAcc CURVE_ACC_SINE_SQRT = new CurveSineSqrtAcc();
    public static final CurveCosAccDec CURVE_ACC_DEC_COS = new CurveCosAccDec();
    public static final CurveCubicAccDec CURVE_ACC_DEC_CUBIC = new CurveCubicAccDec();
    public static final CurveSineDec CURVE_DEC_SINE = new CurveSineDec();
    public static final CurveSineSqrtDec CURVE_DEC_SINE_SQRT = new CurveSineSqrtDec();
    public static final CurveCosSqrtDec CURVE_DEC_COS_SQRT = new CurveCosSqrtDec();

    protected Curve curve;
    protected float tracker;

    public VLVCurved(float from, float to, int cycles, Loop loop, Curve curve){
        super(from, to, cycles, loop);
        this.curve = curve;
    }

    public VLVCurved(float from, float to, int cycles, Curve curve){
        super(from, to, cycles, LOOP_NONE);
        this.curve = curve;
    }

    public VLVCurved(float from, float to, float changerate, Loop loop, Curve curve){
        super(from, to, changerate, loop);
        this.curve = curve;
    }

    public VLVCurved(float from, float to, float changerate, Curve curve){
        super(from, to, changerate, LOOP_NONE);
        this.curve = curve;
    }

    public VLVCurved(VLVCurved src, long flags){
        copy(src, flags);
    }

    public VLVCurved(){

    }

    @Override
    public float convertCyclesToChangeRate(float from, float to, int cycles){
        return 1F / cycles;
    }

    @Override
    public void initialize(float from, float to, float changerate){
        super.initialize(from, to, changerate);
        tracker = change >= 0 ? 0F : 1F;
    }

    @Override
    public int advance(){
        tracker += change;
        value = VLMath.range((float)curve.process(tracker), from, to);

        if(tracker >= 1F){
            value = to;
            tracker = 1;

            deactivate();

        }else if(tracker <= 0F){
            value = from;
            tracker = 0;

            deactivate();
        }

        value *= director;
        return 1;
    }

    @Override
    public void reset(){
        super.reset();
        tracker = change < 0F ? 1F : 0F;
    }

    @Override
    public void finish(){
        super.finish();
        tracker = change < 0F ? 0F : 1F;
    }

    public void setTracker(float tracker){
        this.tracker = tracker;
    }

    public void setCurve(Curve curve){
        this.curve = curve;
    }

    public float getTracker(){
        return tracker;
    }

    public Curve getCurve(){
        return curve;
    }

    @Override
    public void copy(VLVTypeRunnable src, long flags) {
        super.copy(src, flags);
        VLVCurved target = (VLVCurved)src;

        curve = target.curve;
        tracker = target.tracker;
    }

    @Override
    public VLVCurved duplicate(long flags){
        return new VLVCurved(this, flags);
    }

    public abstract static class Curve{

        public abstract double process(float tracker);
    }

    public static final class CurveConstant extends Curve{

        @Override
        public double process(float tracker){
            return Math.abs(tracker);
        }
    }

    public static final class CurveCosAcc extends Curve{

        @Override
        public double process(float tracker){
            return VLMath.interpolateCosAccelerate(tracker);
        }
    }

    public static final class CurveSineSqrtAcc extends Curve{

        @Override
        public double process(float tracker){
            return VLMath.interpolateSinSqrtAccelerate(tracker);
        }
    }

    public static final class CurveCosAccDec extends Curve{

        @Override
        public double process(float tracker){
            return VLMath.interpolateCosAccelerateDecelerate(tracker);
        }
    }

    public static final class CurveCubicAccDec extends Curve{

        @Override
        public double process(float tracker){
            return VLMath.interpolateCubicAccelerateDecelerate(tracker);
        }
    }

    public static final class CurveSineDec extends Curve{

        @Override
        public double process(float tracker){
            return VLMath.interpolateSineDecelerate(tracker);
        }
    }

    public static final class CurveSineSqrtDec extends Curve{

        @Override
        public double process(float tracker){
            return VLMath.interpolateSinSqrtDecelerate(tracker);
        }
    }

    public static final class CurveCosSqrtDec extends Curve{

        @Override
        public double process(float tracker){
            return VLMath.interpolateCosSqrtDecelerate(tracker);
        }
    }
}
