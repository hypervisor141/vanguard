package vanguard;

public class VLShort implements VLPrimitive{

    private short field;

    public VLShort(short v){
        field = v;
    }

    public VLShort(VLShort src, long flags){
        copy(src, flags);
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
    public void copy(VLPrimitive src, long flags){
        field = ((VLShort)src).field;
    }

    @Override
    public VLShort duplicate(long flags){
        return new VLShort(this, flags);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("short[");
        src.append(field);
        src.append("]");
    }
}

