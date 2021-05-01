package vanguard;

public class VLBoolean implements VLPrimitive{

    private boolean field;

    public VLBoolean(boolean v){
        field = v;
    }

    public VLBoolean(VLBoolean src, int depth){
        copy(src, depth);
    }

    public VLBoolean(){

    }

    public void set(boolean field){
        this.field = field;
    }

    public boolean get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, int depth){
        field = ((VLBoolean)src).field;
    }

    @Override
    public VLBoolean duplicate(int depth){
        return new VLBoolean(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("boolean[");
        src.append(field);
        src.append("]");
    }
}