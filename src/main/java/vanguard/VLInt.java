package vanguard;

public class VLInt implements VLStringify{

    private int field;

    public VLInt(int v){
        field = v;
    }

    public VLInt(){

    }

    public void set(int field){
        this.field = field;
    }

    public int get(){
        return field;
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("int[");
        src.append(field);
        src.append("]");
    }
}
