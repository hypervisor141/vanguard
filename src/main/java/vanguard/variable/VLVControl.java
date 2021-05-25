package vanguard.variable;

public class VLVControl extends VLVLinear{

    public VLVControl(int cycles, Loop loop){
        super(0, cycles, cycles, loop);
    }

    public VLVControl(float changerate, Loop loop){
        super(0, changerate, changerate, loop);
    }

    public VLVControl(VLVControl src, long flags){
        copy(src, flags);
    }

    public VLVControl(){

    }

    @Override
    public VLVControl duplicate(long flags){
        return new VLVControl(this, flags);
    }
}
