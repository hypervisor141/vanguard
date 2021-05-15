package vanguard;

public class VLBufferTracker implements VLLoggableType, VLCopyable<VLBufferTracker>{

    public int offset;
    public int count;
    public int inputoffest;
    public int unitoffset;
    public int unitsize;
    public int unitsubcount;
    public int stride;
    public int endposition;
    public int typebytesize;

    public VLBufferTracker(int offset, int count, int inputoffest, int unitoffset, int unitsize, int unitsubcount, int stride, int endposition, int typebytesize){
        this.offset = offset;
        this.count = count;
        this.inputoffest = inputoffest;
        this.unitoffset = unitoffset;
        this.unitsize = unitsize;
        this.unitsubcount = unitsubcount;
        this.stride = stride;
        this.endposition = endposition;
        this.typebytesize = typebytesize;
    }

    public VLBufferTracker(int offset, int count, int typebytesize){
        this.offset = offset;
        this.count = count;
        this.inputoffest = -1;
        this.unitoffset = -1;
        this.unitsize = -1;
        this.unitsubcount = -1;
        this.stride = -1;
        this.typebytesize = typebytesize;
        this.endposition = offset + count;
    }

    public VLBufferTracker(VLBufferTracker src, long flags){
        copy(src, flags);
    }

    public VLBufferTracker(){

    }

    @Override
    public void copy(VLBufferTracker src, long flags){
        offset = src.offset;
        count = src.count;
        inputoffest = src.inputoffest;
        unitoffset = src.unitoffset;
        unitsize = src.unitsize;
        unitsubcount = src.unitsubcount;
        stride = src.stride;
        endposition = src.endposition;
        typebytesize = src.typebytesize;
    }

    @Override
    public VLBufferTracker duplicate(long flags) {
        return new VLBufferTracker(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("[");
        log.append(getClass().getSimpleName());
        log.append("] offset[");
        log.append(offset);
        log.append("] count[");
        log.append(count);
        log.append("] unitOffset[");
        log.append(unitoffset);
        log.append("] unitSize[");
        log.append(unitsize);
        log.append("] unitSubCount[");
        log.append(unitsubcount);
        log.append("] stride[");
        log.append(stride);
        log.append("] endPosition[");
        log.append(endposition);
        log.append("] typeByteSize[");
        log.append(typebytesize);
        log.append("]");
    }
}
