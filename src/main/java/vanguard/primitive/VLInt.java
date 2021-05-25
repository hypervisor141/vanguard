package vanguard.primitive;

import vanguard.utils.VLLog;

public class VLInt implements VLPrimitive{

    protected int field;

    public VLInt(int v){
        field = v;
    }

    public VLInt(VLInt src, long flags){
        copy(src, flags);
    }

    protected VLInt(){

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
