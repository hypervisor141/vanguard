package vanguard;

public class VLDouble implements VLStringify{

    private double field;

    public VLDouble(double v){
        field = v;
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
    public void stringify(StringBuilder src, Object hint){
        src.append("double[");
        src.append(field);
        src.append("]");
    }
}
