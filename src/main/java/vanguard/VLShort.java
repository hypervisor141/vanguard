package vanguard;

public class VLShort implements VLStringify{

    private short field;

    public VLShort(short v){
        field = v;
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
    public void stringify(StringBuilder src, Object hint){
        src.append("short[");
        src.append(field);
        src.append("]");
    }
}

