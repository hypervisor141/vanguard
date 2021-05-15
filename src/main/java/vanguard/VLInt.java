package vanguard;

public class VLInt implements VLPrimitive{

    private int field;

    public VLInt(int v){
        field = v;
    }

    public VLInt(VLInt src, long flags){
        copy(src, flags);
    }

    public VLInt(){

    }

    public void set(int field){
        this.field = field;
    }

    public int get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, long flags){
        field = ((VLInt)src).field;
    }

    @Override
    public VLInt duplicate(long flags){
        return new VLInt(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("int[");
        log.append(field);
        log.append("]");
    }
}
