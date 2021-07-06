package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLShort implements VLPrimitive{

    public short value;

    public VLShort(short v){
        value = v;
    }

    public VLShort(VLShort src, long flags){
        copy(src, flags);
    }

    protected VLShort(){

    }

    @Override
    public void copy(VLPrimitive src, long flags){
        value = ((VLShort)src).value;
    }

    @Override
    public VLShort duplicate(long flags){
        return new VLShort(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("short[");
        log.append(value);
        log.append("]");
    }
}

