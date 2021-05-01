package vanguard;

public class VLLong implements VLPrimitive{

    private long field;

    public VLLong(int v) {
        field = v;
    }

    public VLLong(VLLong src, int depth){
        copy(src, depth);
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
    public void copy(VLPrimitive src, int depth){
        field = ((VLLong)src).field;
    }

    @Override
    public VLLong duplicate(int depth){
        return new VLLong(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("long[");
        src.append(field);
        src.append("]");
    }
}
