package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLDouble implements VLPrimitive{

    public double value;

    public VLDouble(double v){
        value = v;
    }

    public VLDouble(VLDouble src, long flags){
        copy(src, flags);
    }

    protected VLDouble(){

    }

    @Override
    public void copy(VLPrimitive src, long flags){
        value = ((VLDouble)src).value;
    }

    @Override
    public VLDouble duplicate(long flags){
        return new VLDouble(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("double[");
        log.append(value);
        log.append("]");
    }
}
