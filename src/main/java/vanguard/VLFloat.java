package vanguard;

public class VLFloat implements VLStringify{

    private float field;

    public VLFloat(float v){
        field = v;
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
    public void stringify(StringBuilder src, Object hint){
        src.append("float[");
        src.append(field);
        src.append("]");
    }
}
