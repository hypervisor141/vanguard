package vanguard;

public class VLLong implements VLPrimitive{

    private long field;

    public VLLong(int v) {
        field = v;
    }

    public VLLong(VLLong src, long flags){
        copy(src, flags);
    }

    public VLLong() {

    }

    public void set(long field) {
        this.field = field;
    }

    public long get() {
        return field;
    }

    @Override
    public void copy(VLPrimitive src, long flags){
        field = ((VLLong)src).field;
    }

    @Override
    public VLLong duplicate(long flags){
        return new VLLong(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("long[");
        src.append(field);
        src.append("]");
    }
}
