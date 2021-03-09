package vanguard;

public class VLBufferTracker<BUFFER extends VLBuffer<?, ?>> extends VLBufferTrackerType{

    public BUFFER buffer;

    public VLBufferTracker(BUFFER buffer, int offset, int unitoffset, int unitsize, int stride, int count){
        super(offset, unitoffset, unitsize, stride, count);
        this.buffer = buffer;
    }

    public VLBufferTracker(){

    }

    public void buffer(){

    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("[");
        src.append(getClass().getSimpleName());
        src.append("] buffer[");
        src.append(buffer.getClass().getSimpleName());
        src.append("] offset[");
        src.append(offset);
        src.append("] unitSize[");
        src.append(unitsize);
        src.append("] unitOffset[");
        src.append(unitoffset);
        src.append("] stride[");
        src.append(stride);
        src.append("] count[");
        src.append(count);
        src.append("] content[ ");

        buffer.stringify(src, hint);

        src.append(" ]");
    }
}

