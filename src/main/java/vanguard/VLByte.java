package vanguard;

public class VLByte implements VLPrimitive{

    private byte field;

    public VLByte(byte v){
        field = v;
    }

    public VLByte(VLByte src, int depth){
        copy(src, depth);
    }

    public VLByte(){

    }

    public void set(byte field){
        this.field = field;
    }

    public byte get(){
        return field;
    }

    @Override
    public void copy(VLPrimitive src, int depth){
        field = ((VLByte)src).field;
    }

    @Override
    public VLByte duplicate(int depth){
        return new VLByte(this, depth);
    }

    @Override
    public void stringify(StringBuilder src, Object hint){
        src.append("byte[");
        src.append(field);
        src.append("]");
    }
}