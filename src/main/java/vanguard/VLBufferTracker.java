package vanguard;

public class VLBufferTracker<BUFFER> implements VLStringify{

    protected BUFFER buffer;

    protected int offset;
    protected int count;

    public VLBufferTracker(BUFFER buffer, int offset, int count){
        this.buffer = buffer;
        this.offset = offset;
        this.count = count;
    }

    public VLBufferTracker(){

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

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("[");
        src.append(getClass().getSimpleName());
        src.append("] buffer[");
        src.append(buffer.getClass().getSimpleName());
        src.append("] offset[");
        src.append(offset);
        src.append("] count[");
        src.append(count);
        src.append("]");
    }
}
