package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLBoolean implements VLPrimitive{

    public boolean field;

    public VLBoolean(boolean v){
        field = v;
    }

    public VLBoolean(VLBoolean src, long flags){
        copy(src, flags);
    }

    protected VLBoolean(){

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