package vanguard;

public class VLBufferTracker implements VLLoggable, VLCopyable<VLBufferTracker>{

    public int srcoffset;
    public int srccount;
    public int offset;
    public int count;
    public int unitoffset;
    public int unitsize;
    public int unitsubcount;
    public int stride;
    public int endposition;
    public int typebytesize;

    public VLBufferTracker(int srcoffset, int srccount, int offset, int count, int inputoffest, int unitoffset, int unitsize,
                           int unitsubcount, int stride, int endposition, int typebytesize){

        this.srcoffset = srcoffset;
        this.srccount = srccount;
        this.offset = offset;
        this.count = count;
        this.unitoffset = unitoffset;
        this.unitsize = unitsize;
        this.unitsubcount = unitsubcount;
        this.stride = stride;
        this.endposition = endposition;
        this.typebytesize = typebytesize;
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
        log.append("] srcOffset[");
        log.append(srcoffset);
        log.append("] srcCount[");
        log.append(srccount);
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
