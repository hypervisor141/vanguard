package vanguard;

public class VLBufferTracker<BUFFER> extends VLBufferTrackerType<BUFFER>{

    public VLBufferTracker(BUFFER buffer, int offset, int count){
        super(buffer, offset, count);
        this.buffer = buffer;
    }

    public VLBufferTracker(){

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

