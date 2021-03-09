package vanguard;

public class VLBTracker<INPUT, BUFFER extends VLB<?, ?>> extends VLBufferTrackerType<INPUT>{

    public BUFFER buffer;

    public VLBTracker(BUFFER buffer, int offset, int count){
        super(offset, count);
        this.buffer = buffer;
    }

    public VLBTracker(){

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
        src.append("] content[ ");

        buffer.stringify(src, hint);

        src.append(" ]");
    }
}

