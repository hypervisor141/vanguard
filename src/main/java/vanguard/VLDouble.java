package vanguard;

public class VLDouble implements VLPrimitive{

    protected double field;

    public VLDouble(double v){
        field = v;
    }

    public VLDouble(VLDouble src, long flags){
        copy(src, flags);
    }

    protected VLDouble(){

    }

    public void set(double field){
        this.field = field;
    }

    public double get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, long flags){
        field = ((VLDouble)src).field;
    }

    @Override
    public VLDouble duplicate(long flags){
        return new VLDouble(this, flags);
    }

    @Override
    public void log(VLLog log, Object data){
        log.append("double[");
        log.append(field);
        log.append("]");
    }
}
