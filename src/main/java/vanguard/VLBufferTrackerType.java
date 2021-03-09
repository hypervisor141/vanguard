package vanguard;

public abstract class VLBufferTrackerType implements VLStringify{

    public int offset;
    public int unitoffset;
    public int unitsize;
    public int stride;
    public int count;

    public VLBufferTrackerType(int offset, int unitoffset, int unitsize, int stride, int count){
        this.offset = offset;
        this.unitoffset = unitoffset;
        this.unitsize = unitsize;
        this.stride = stride;
        this.count = count;
    }

    public VLBufferTrackerType(){

    }

    public abstract void buffer();
}
