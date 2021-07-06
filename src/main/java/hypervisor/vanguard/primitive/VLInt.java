package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLInt implements VLPrimitive{

    public int value;

    public VLInt(int v){
        value = v;
    }

    public VLInt(VLInt src, long flags){
        copy(src, flags);
    }

    protected VLInt(){

    }

    @Override
    public void copy(VLPrimitive src, long flags){
        value = ((VLInt)src).value;
    }

    @Override
    public VLInt duplicate(long flags){
        return new VLInt(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("int[");
        log.append(value);
        log.append("]");
    }
}
