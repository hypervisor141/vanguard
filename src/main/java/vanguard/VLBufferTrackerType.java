package vanguard;

public abstract class VLBufferTrackerType<INPUT> implements VLStringify{

    public int offset;
    public int count;

    public VLBufferTrackerType(int offset, int count){
        this.offset = offset;
        this.count = count;
    }

    public VLBufferTrackerType(){

    }
}
