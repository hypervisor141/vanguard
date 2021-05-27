package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLShort implements VLPrimitive{

    protected short field;

    public VLShort(short v){
        field = v;
    }

    public VLShort(VLShort src, long flags){
        copy(src, flags);
    }

    protected VLShort(){

    }

    public void set(short field){
        this.field = field;
    }

    public short get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, long flags){
        field = ((VLShort)src).field;
    }

    @Override
    public VLShort duplicate(long flags){
        return new VLShort(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("short[");
        log.append(field);
        log.append("]");
    }
}

