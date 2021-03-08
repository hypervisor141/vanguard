package vanguard;

public class VLVControl extends VLVLinear{

    public VLVControl(int cycles, Loop loop){
        super(0, cycles, cycles, loop);
    }

    public VLVControl(float changerate, Loop loop){
        super(0, changerate, changerate, loop);
    }

    public VLVControl(){

    }
}
