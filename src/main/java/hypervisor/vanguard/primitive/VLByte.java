package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLByte implements VLPrimitive{

    public byte value;

    public VLByte(byte v){
        value = v;
    }

    public VLByte(VLByte src, long flags){
        copy(src, flags);
    }

    protected VLByte(){

    }

    @Override
    public void copy(VLPrimitive src, long flags){
        value = ((VLByte)src).value;
    }

    @Override
    public VLByte duplicate(long flags){
        return new VLByte(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("byte[");
        log.append(value);
        log.append("]");
    }
}