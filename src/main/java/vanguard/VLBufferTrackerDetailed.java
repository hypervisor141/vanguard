package vanguard;

public class VLBufferTrackerDetailed extends VLBufferTracker{

    protected int inputoffest;
    protected int unitoffset;
    protected int unitsize;
    protected int unitsubcount;
    protected int stride;
    protected int endposition;
    protected int typebytesize;

    public VLBufferTrackerDetailed(int offset, int inputoffest, int unitoffset, int unitsize, int unitsubcount, int stride, int count, int endposition, int typebytesize){
        super(offset, count);

        this.inputoffest = inputoffest;
        this.unitoffset = unitoffset;
        this.unitsize = unitsize;
        this.unitsubcount = unitsubcount;
        this.stride = stride;
        this.endposition = endposition;
        this.typebytesize = typebytesize;
    }

    public VLBufferTrackerDetailed(){

    }

    public void inputoffest(int inputoffest){
        this.inputoffest = inputoffest;
    }

    public void unitoffset(int unitoffset){
        this.unitoffset = unitoffset;
    }

    public void unitsize(int unitsize){
        this.unitsize = unitsize;
    }

    public void unitsubcount(int unitsubcount){
        this.unitsubcount = unitsubcount;
    }

    public void stride(int stride){
        this.stride = stride;
    }

    public void endposition(int endposition){
        this.endposition = endposition;
    }

    public void typebytesize(int typebytesize){
        this.typebytesize = typebytesize;
    }

    public int inputoffest(){
        return inputoffest;
    }

    public int unitoffset(){
        return unitoffset;
    }

    public int unitsize(){
        return unitsize;
    }

    public int unitsubcount(){
        return unitsubcount;
    }

    public int stride(){
        return stride;
    }

    public int endposition(){
        return endposition;
    }

    public int typebytesize(){
        return typebytesize;
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        super.stringify(src, hint);

        src.append(" unitOffset[");
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
