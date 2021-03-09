package vanguard;

public class VLBTrackerInterleaved<INPUT, BUFFER extends VLB<?, ?>> extends VLBTracker<INPUT, BUFFER>{

    public int inputoffest;
    public int unitoffset;
    public int unitsize;
    public int unitsubcount;
    public int stride;

    public VLBTrackerInterleaved(BUFFER buffer, int offset, int inputoffest, int count, int unitoffset, int unitsize, int unitsubcount, int stride){
        super(buffer, offset, count);

        this.inputoffest = inputoffest;
        this.unitoffset = unitoffset;
        this.unitsize = unitsize;
        this.unitsubcount = unitsubcount;
        this.stride = stride;
    }

    public VLBTrackerInterleaved(){

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
        src.append("] unitOffset[");
        src.append(unitoffset);
        src.append("] unitSize[");
        src.append(unitsize);
        src.append("] unitSubCount[");
        src.append(unitsubcount);
        src.append("] stride[");
        src.append(stride);
        src.append("] content[ ");

        buffer.stringify(src, hint);

        src.append(" ]");
    }
}
