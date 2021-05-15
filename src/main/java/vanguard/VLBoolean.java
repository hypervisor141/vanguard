package vanguard;

public class VLBoolean implements VLPrimitive{

    private boolean field;

    public VLBoolean(boolean v){
        field = v;
    }

    public VLBoolean(VLBoolean src, long flags){
        copy(src, flags);
    }

    public VLBoolean(){

    }

    public void set(boolean field){
        this.field = field;
    }

    public boolean get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, long flags){
        field = ((VLBoolean)src).field;
    }

    @Override
    public VLBoolean duplicate(long flags){
        return new VLBoolean(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("boolean[");
        log.append(field);
        log.append("]");
    }
}