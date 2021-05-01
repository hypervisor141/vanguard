package vanguard;

public class VLShort implements VLPrimitive{

    private short field;

    public VLShort(short v){
        field = v;
    }

    public VLShort(VLShort src, int depth){
        copy(src, depth);
    }

    public VLShort(){

    }

    public void set(short field){
        this.field = field;
    }

    public short get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, int depth){
        field = ((VLShort)src).field;
    }

    @Override
    public VLShort duplicate(int depth){
        return new VLShort(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("short[");
        src.append(field);
        src.append("]");
    }
}

