package vanguard;

public class VLLong implements VLPrimitive{

    protected long field;

    public VLLong(int v) {
        field = v;
    }

    public VLLong(VLLong src, long flags){
        copy(src, flags);
    }

    protected VLLong(){

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
    public void log(VLLog log, Object data){
        log.append("long[");
        log.append(field);
        log.append("]");
    }
}
