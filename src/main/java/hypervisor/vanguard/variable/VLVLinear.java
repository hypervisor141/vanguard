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
    public void initialize(float from, float to, int cycles){
        super.initialize(from, to, (this.to - this.from) / cycles);
        value = cycles >= 0 ? this.from : this.to;
    }

    @Override
    public void initialize(float from, float to, float changerate){
        super.initialize(from, to, changerate);
        value = change >= 0 ? this.from : this.to;
    }

    @Override
    protected int advance(){
        value += change;

        if(change >= 0){
            if(value > to){
                value = to;
                deactivate();
            }

        }else{
            if(value < from){
                value = from;
                deactivate();
            }
        }

        return 1;
    }

    @Override
    public VLVLinear duplicate(long flags) {
        return new VLVLinear(this, flags);
    }
}
