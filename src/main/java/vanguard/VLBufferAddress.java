package vanguard;

public class VLBufferAddress<MANAGER extends VLBufferManagerBase> implements VLStringify{

    protected MANAGER manager;

    protected int bufferindex;
    protected int offset;
    protected int unitoffset;
    protected int unitsize;
    protected int stride;
    protected int count;

    public VLBufferAddress(MANAGER manager, int bufferindex, int offset, int unitoffset, int unitsize, int stride, int count){
        this.manager = manager;
        this.bufferindex = bufferindex;
        this.offset = offset;
        this.unitoffset = unitoffset;
        this.unitsize = unitsize;
        this.stride = stride;
        this.count = count;
    }

    public VLBufferAddress(){

    }


    public void fill(MANAGER manager, int bufferindex, int offset, int unitoffset, int unitsize, int stride, int count){
        this.manager = manager;
        this.bufferindex = bufferindex;
        this.offset = offset;
        this.unitoffset = unitoffset;
        this.unitsize = unitsize;
        this.stride = stride;
        this.count = count;
    }

    public MANAGER manager(){
        return manager;
    }

    public VLBufferManagerBase.EntryType target(){
        return manager.get(bufferindex);
    }

    public int bufferIndex(){
        return bufferindex;
    }

    public int unitSize(){
        return unitsize;
    }

    public int unitOffset(){
        return unitoffset;
    }

    public int count(){
        return count;
    }

    public int offset(){
        return offset;
    }

    public int stride(){return stride;}

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("[BufferAddress] managerType[");
        src.append(manager.getClass().getSimpleName());
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

        target().buffer().stringify(src, hint);

        src.append(" ]");
    }
}

