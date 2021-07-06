package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLBoolean implements VLPrimitive{

    public boolean value;

    public VLBoolean(boolean v){
        value = v;
    }

    public VLBoolean(VLBoolean src, long flags){
        copy(src, flags);
    }

    protected VLBoolean(){

    }

    @Override
    public void copy(VLPrimitive src, long flags){
        value = ((VLBoolean)src).value;
    }

    @Override
    public VLBoolean duplicate(long flags){
        return new VLBoolean(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("boolean[");
        log.append(value);
        log.append("]");
    }
}