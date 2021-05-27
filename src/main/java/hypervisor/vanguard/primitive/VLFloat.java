package hypervisor.vanguard.primitive;

import hypervisor.vanguard.utils.VLLog;

public class VLFloat implements VLPrimitive{

    protected float field;

    public VLFloat(float v){
        field = v;
    }

    public VLFloat(VLFloat src, long flags){
        copy(src, flags);
    }

    protected VLFloat(){

    }

    public void set(float field){
        this.field = field;
    }

    public float get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, long flags){
        field = ((VLFloat)src).field;
    }

    @Override
    public VLFloat duplicate(long flags){
        return new VLFloat(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("float[");
        log.append(field);
        log.append("]");
    }
}