package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLFloat implements VLPrimitive{

    public float value;

    public VLFloat(float v){
        value = v;
    }

    public VLFloat(VLFloat src, long flags){
        copy(src, flags);
    }

    protected VLFloat(){

    }

    @Override
    public void copy(VLPrimitive src, long flags){
        value = ((VLFloat)src).value;
    }

    @Override
    public VLFloat duplicate(long flags){
        return new VLFloat(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("float[");
        log.append(value);
        log.append("]");
    }
}
