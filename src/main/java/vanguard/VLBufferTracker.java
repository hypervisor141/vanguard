package vanguard;

public class VLBufferTracker implements VLStringify{

    public int offset
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

    public VLBufferTracker(){

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
        src.append("] unitOffset[");
        src.append(unitoffset);
        src.append("] unitSize[");
        src.append(unitsize);
        src.append("] unitSubCount[");
        src.append(unitsubcount);
        src.append("] stride[");
        src.append(stride);
        src.append("] endPosition[");
        src.append(endposition);
        src.append("] typeByteSize[");
        src.append(typebytesize);
        src.append("]");
    }
}
