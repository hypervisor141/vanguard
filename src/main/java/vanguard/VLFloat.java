package vanguard;

public class VLFloat implements VLPrimitive{

    private float field;

    public VLFloat(float v){
        field = v;
    }

    public VLFloat(VLFloat src, int depth){
        copy(src, depth);
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
    public void copy(VLPrimitive src, int depth){
        field = ((VLFloat)src).field;
    }

    @Override
    public VLFloat duplicate(int depth){
        return new VLFloat(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("float[");
        src.append(field);
        src.append("]");
    }
}
