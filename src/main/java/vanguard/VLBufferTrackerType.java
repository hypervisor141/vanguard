package vanguard;

public abstract class VLBufferTrackerType<BUFFER> implements VLStringify{

    protected BUFFER buffer;

    protected int offset;
    protected int count;

    public VLBufferTrackerType(BUFFER buffer, int offset, int count){
        this.buffer = buffer;
        this.offset = offset;
        this.count = count;
    }

    public VLBufferTrackerType(){

    }

    public void buffer(BUFFER buffer){
        this.buffer = buffer;
    }

    public BUFFER buffer(){
        return buffer;
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
