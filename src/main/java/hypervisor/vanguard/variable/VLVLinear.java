package hypervisor.vanguard.variable;

public class VLVLinear extends VLVariable{

    public VLVLinear(float from, float to, int cycles, Loop loop){
        super(from, to, cycles, loop);
    }

    public VLVLinear(float from, float to, int cycles){
        super(from, to, cycles, LOOP_NONE);
    }

    public VLVLinear(float from, float to, float changerate, Loop loop){
        super(from, to, changerate, loop);
    }

    public VLVLinear(float from, float to, float changerate){
        super(from, to, changerate, LOOP_NONE);
    }

    public VLVLinear(VLVLinear src, long flags){
        copy(src, flags);
    }

    public VLVLinear(){

    }

    @Override
    public float convertCyclesToChangeRate(float from, float to, int cycles){
        return (to - from) / cycles;
    }

    @Override
    public int advance(){
        float value = get() + change;

        if(value >= to){
            set(to);
            deactivate();

        }else if(value <= from){
            set(from);
            deactivate();

        }else{
            set(value);
        }

        return 1;
    }

    @Override
    public VLVLinear duplicate(long flags) {
        return new VLVLinear(this, flags);
    }
}
