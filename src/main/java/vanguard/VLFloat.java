package vanguard;

public class VLFloat implements VLPrimitive{

    private float field;

    public VLFloat(float v){
        field = v;
    }

    public VLFloat(VLFloat src, long flags){
        copy(src, flags);
    }

    public VLFloat(){

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
    public void stringify(StringBuilder src, Object hint){
        src.append("float[");
        src.append(field);
        src.append("]");
    }
}
