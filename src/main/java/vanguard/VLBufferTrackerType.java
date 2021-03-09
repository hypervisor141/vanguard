package vanguard;

public abstract class VLBufferTrackerType implements VLStringify{

    protected int offset;
    protected int count;

    public VLBufferTrackerType(int offset, int count){
        this.offset = offset;
        this.count = count;
    }

    public VLBufferTrackerType(){

    }

    public void offset(int offset){
        this.offset = offset;
    }

    public void count(int count){
        this.count = count;
    }

    public int offset(){
        return offset;
    }

    public int count(){
        return count;
    }
}
