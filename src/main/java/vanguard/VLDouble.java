package vanguard;

public class VLDouble implements VLPrimitive{

    private double field;

    public VLDouble(double v){
        field = v;
    }

    public VLDouble(VLDouble src, int depth){
        copy(src, depth);
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
    public void copy(VLPrimitive src, int depth){
        field = ((VLDouble)src).field;
    }

    @Override
    public VLDouble duplicate(int depth){
        return new VLDouble(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("double[");
        src.append(field);
        src.append("]");
    }
}
