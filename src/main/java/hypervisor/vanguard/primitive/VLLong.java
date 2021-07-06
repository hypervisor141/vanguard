package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLLong implements VLPrimitive{

    public long value;

    public VLLong(int v) {
        value = v;
    }

    public VLLong(VLLong src, long flags){
        copy(src, flags);
    }

    protected VLLong(){

    }

    @Override
    public void copy(VLPrimitive src, long flags){
        value = ((VLLong)src).value;
    }

    @Override
    public VLLong duplicate(long flags){
        return new VLLong(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("long[");
        log.append(value);
        log.append("]");
    }
}
