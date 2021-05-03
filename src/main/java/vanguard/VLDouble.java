package vanguard;

public class VLDouble implements VLPrimitive{

    private double field;

    public VLDouble(double v){
        field = v;
    }

    public VLDouble(VLDouble src, long flags){
        copy(src, flags);
    }

    public VLDouble(){

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
    public void stringify(StringBuilder src, Object hint){
        src.append("double[");
        src.append(field);
        src.append("]");
    }
}
