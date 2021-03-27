package vanguard;

public class VLBufferTracker implements VLStringify{

    protected int offset;
    protected int count;

    public VLBufferTracker(int offset, int count){
        this.offset = offset;
        this.count = count;
    }

    public VLBufferTracker(){

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

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("[");
        src.append(getClass().getSimpleName());
        src.append("] offset[");
        src.append(offset);
        src.append("] count[");
        src.append(count);
        src.append("]");
    }
}
